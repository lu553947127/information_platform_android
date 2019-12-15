package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.view.release.ReleaseProjectActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  动态
 * @time 2019/7/15 15:32
 * @change
 * @chang time
 * @class describe
 */
public class ProjectLocusFragment extends BaseFragment {

    @BindView(R.id.tv_filter)
    TextView tvFilter;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv_locus)
    RecyclerView rvLocus;

    private LocusAdapter locusAdapter;



    private ProjectDetailActivity activity;

    public static ProjectLocusFragment newInstance() {
        ProjectLocusFragment fragment = new ProjectLocusFragment();
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
    protected void initDataAndEvent(Bundle savedInstanceState, View view) {
        tvFilter.setText(getString(R.string.release_by_me));

        activity = (ProjectDetailActivity) mActivity;




        rvLocus.setLayoutManager(new LinearLayoutManager(mContext));
        locusAdapter = new LocusAdapter(R.layout.item_locus, null) {
            @Override
            public void readDetail(int position, String price) {
                TrackBean.ListBean listBean = locusAdapter.getData().get(position);
                activity.coinPayVm.locusId = listBean.getId();
                new CustomDialog(mActivity)
                        .setTip(String.format(getString(R.string.format_pay_price), price))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
                                if (status == 1) {
                                   activity.payLocus();
                                } else {
                                    //查询是否设置支付密码
                                    activity.updatePwdPayVm.payPwdState();
                                }
                            }
                        })
                        .showDialog();
            }
        };
        locusAdapter.setEmptyView(R.layout.layout_loading_top, rvLocus);
        rvLocus.setAdapter(locusAdapter);
        locusAdapter.setOnItemChildClickListener((adapter, v, position) -> {
            TrackBean.ListBean listBean = locusAdapter.getData().get(position);
            ArrayList<String> list = new ArrayList<>();
            for (TrackBean.ListBean.ImageBean img : listBean.getImage()) {
                list.add(img.getSource());
            }
            switch (v.getId()) {
                case R.id.iv_pic_first:
                    PictureEnlargeUtils.getPictureEnlargeList(getActivity(), list, 0);
                    break;
                case R.id.iv_pic_second:
                    PictureEnlargeUtils.getPictureEnlargeList(getActivity(), list, 1);
                    break;
                case R.id.iv_pic_third:
                case R.id.tv_more:
                    PictureEnlargeUtils.getPictureEnlargeList(getActivity(), list, 2);
                    break;
                case R.id.iv_mark:
                    if (listBean.getUser_id() != SPUtils.getInstance().getInt(SpConfig.USER_ID)) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CustomConfig.UID, listBean.getUser_id());
                        ActivityUtils.startActivity(bundle, LocusOwnerDetailActivity.class);
                    }
                    break;
            }
        });

        refresh.setOnLoadMoreListener(refreshLayout -> activity.projectDetailVm.getMoreViewTrack());

        activity.projectDetailVm.locusTypeLiveData.observe(this, type -> {
            if (type == 1) {
                tvFilter.setText(getString(R.string.release_by_me));
            } else {
                tvFilter.setText(getString(R.string.all));
            }
        });

        activity.projectDetailVm.trackLiveData.observe(this, trackBean -> {
            if (trackBean.getPage() == 1) {
                locusAdapter.setNewData(trackBean.getList());
                setEmpty();
            } else {
                locusAdapter.addData(trackBean.getList());
            }
            setNoMore(trackBean.getPage(), trackBean.getCount());
        });

    }

    @Override
    protected void initDataFromService() {
        activity.projectDetailVm.getTrack();
    }

    private void setEmpty() {

        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_top, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        TextView tvGo = empty.findViewById(R.id.tv_goto);
        tvTip.setText(getString(R.string.empty_locus));
        tvGo.setVisibility(View.VISIBLE);
        tvGo.setText(R.string.go_release);
        empty.findViewById(R.id.tv_goto).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.RELEASE_TYPE, 2);
            bundle.putInt(CustomConfig.PROJECT_ID, activity.coinPayVm.projectId );
            bundle.putString(CustomConfig.PROJECT_NAME, activity.projectDetailVm.titleLiveData.getValue());
            ActivityUtils.startActivity(bundle, ReleaseProjectActivity.class);
        });
        locusAdapter.setEmptyView(empty);
    }

    @OnClick({R.id.tv_filter})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                activity.projectDetailVm.switchLocusList();
                break;
        }
    }

    private void setNoMore(int page, int count) {
        if (page == 1) {
            if (page * 10 >= count) {
                if (refresh.getState() == RefreshState.None) {
                    refresh.setNoMoreData(true);
                } else {
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                refresh.finishRefresh();
            }
        } else {
            if (page * 10 >= count) {
                refresh.finishLoadMoreWithNoMoreData();
            } else {
                refresh.finishLoadMore();
            }
        }
    }


}
