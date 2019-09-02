package com.shuangduan.zcy.view.projectinfo;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.model.event.RefreshViewLocusEvent;
import com.shuangduan.zcy.view.PayActivity;
import com.shuangduan.zcy.view.PhotoViewActivity;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  轨迹
 * @time 2019/7/15 15:32
 * @change
 * @chang time
 * @class describe
 */
public class ProjectLocusFragment extends BaseLazyFragment {
    @BindView(R.id.tv_filter)
    TextView tvFilter;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv_locus)
    RecyclerView rvLocus;
    private ProjectDetailVm projectDetailVm;
    private LocusAdapter locusAdapter;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;

    public static ProjectLocusFragment newInstance() {

        Bundle args = new Bundle();

        ProjectLocusFragment fragment = new ProjectLocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_locus;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvFilter.setText(getString(R.string.release_by_me));
        rvLocus.setLayoutManager(new LinearLayoutManager(mContext));
        locusAdapter = new LocusAdapter(R.layout.item_locus, null){
            @Override
            public void readDetail(int position, String price) {
                TrackBean.ListBean listBean = locusAdapter.getData().get(position);
                coinPayVm.locusId = listBean.getId();
                new CustomDialog(mActivity)
                        .setTip(String.format(getString(R.string.format_pay_price), price))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
                                if (status == 1){
                                    goToPay();
                                }else {
                                    //查询是否设置支付密码
                                    updatePwdPayVm.payPwdState();
                                }
                            }
                        })
                        .showDialog();
            }
        };
        locusAdapter.setEmptyView(R.layout.layout_loading_top, rvLocus);
        rvLocus.setAdapter(locusAdapter);
        locusAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TrackBean.ListBean listBean = locusAdapter.getData().get(position);
            switch (view.getId()){
                case R.id.iv_pic_first:
                    showPic(listBean, 0, view);
                    break;
                case R.id.iv_pic_second:
                    showPic(listBean, 1, view);
                    break;
                case R.id.iv_pic_third:
                    showPic(listBean, 2, view);
                    break;
                case R.id.tv_more:
                    showPic(listBean, 2, view);
                    break;
                case R.id.iv_mark:
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.UID, listBean.getUser_id());
                    ActivityUtils.startActivity(bundle, LocusOwnerDetailActivity.class);
                    break;
            }
        });

        refresh.setOnLoadMoreListener(refreshLayout -> projectDetailVm.getMoreViewTrack());

        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.locusTypeLiveData.observe(this, type -> {
            if (type == 1){
                tvFilter.setText(getString(R.string.release_by_me));
            }else {
                tvFilter.setText(getString(R.string.all));
            }
        });
        projectDetailVm.trackLiveData.observe(this, trackBean -> {
            isInited = true;
            if (trackBean.getPage() == 1) {
                locusAdapter.setNewData(trackBean.getList());
                setEmpty();
            }else {
                locusAdapter.addData(trackBean.getList());
            }
            setNoMore(trackBean.getPage(), trackBean.getCount());
        });

        initPay();
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm.getTrack();
    }

    private void setEmpty() {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_locus));
        locusAdapter.setEmptyView(empty);
    }

    /**
     *  图片预览
     */
    private void showPic(TrackBean.ListBean item, int position, View view){
        ArrayList<String> list = new ArrayList<>();
        for (TrackBean.ListBean.ImageBean img: item.getImage()) {
            list.add(img.getSource());
        }

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putStringArrayList(CustomConfig.PHOTO_VIEW_URL_LIST, list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //共享shareElement这个View
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, "shareElement");
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class, activityOptionsCompat.toBundle());
        } else {
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class);
        }
    }

    @OnClick({R.id.tv_filter})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                projectDetailVm.switchLocusList();
                break;
        }
    }

    private void setNoMore(int page, int count){
        if (page == 1){
            if (page * 10 >= count){
                if (refresh.getState() == RefreshState.None){
                    refresh.setNoMoreData(true);
                }else {
                    refresh.finishRefreshWithNoMoreData();
                }
            }else {
                refresh.finishRefresh();
            }
        }else {
            if (page * 10 >= count){
                refresh.finishLoadMoreWithNoMoreData();
            }else {
                refresh.finishLoadMore();
            }
        }
    }

    private void initPay(){
        //支付密码状态查询
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.stateLiveData.observe(this, pwdPayStateBean -> {
            int status = pwdPayStateBean.getStatus();
            SPUtils.getInstance().put(SpConfig.PWD_PAY_STATUS, status);
            if (status == 1){
                goToPay();
            }else {
                ActivityUtils.startActivity(SetPwdPayActivity.class);
            }
        });
        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        //紫金币支付
        coinPayVm = ViewModelProviders.of(this).get(CoinPayVm.class);
        coinPayVm.locusPayLiveData.observe(this, coinPayResultBean -> {
            if (coinPayResultBean.getPay_status() == 1){
                ToastUtils.showShort(getString(R.string.buy_success));
                projectDetailVm.getTrack();
                //刷新已查看列表
                EventBus.getDefault().post(new RefreshViewLocusEvent());
            }else {
                //余额不足
                addDialog(new CustomDialog(mActivity)
                        .setIcon(R.drawable.icon_error)
                        .setTip("余额不足")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                ActivityUtils.startActivity(RechargeActivity.class);
                            }
                        })
                        .showDialog());
            }
        });
        coinPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    /**
     * 去支付
     */
    private void goToPay(){
        addDialog(new PayDialog(mActivity)
                .setSingleCallBack((item, position) -> {
                    coinPayVm.payLocus(item);
                })
                .showDialog());
    }
}
