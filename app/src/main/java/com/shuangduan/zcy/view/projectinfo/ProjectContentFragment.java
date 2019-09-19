package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ContactAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendApplyCountBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.event.RefreshViewLocusEvent;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.RichText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  概况
 * @time 2019/7/15 15:30
 * @change
 * @chang time
 * @class describe
 */
public class ProjectContentFragment extends BaseFragment {

    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.tv_stage)
    TextView tvStage;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_cycle)
    TextView tvCycle;
    @BindView(R.id.tv_acreage)
    TextView tvAcreage;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_detail)
    RichText tvDetail;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.rv_contact)
    RecyclerView rvContact;
    @BindView(R.id.tv_read_detail)
    TextView tvReadDetail;
    private ProjectDetailVm projectDetailVm;
    private ContactAdapter contactAdapter;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;
    private static int project_id;

    public static ProjectContentFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        project_id=id;
        ProjectContentFragment fragment = new ProjectContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_content;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View v) {

        rvContact.setLayoutManager(new LinearLayoutManager(mContext));
        rvContact.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        contactAdapter = new ContactAdapter(R.layout.item_contact, null);
        contactAdapter.setEmptyView(R.layout.layout_loading_top, rvContact);
        rvContact.setAdapter(contactAdapter);

        //基本信息设置
        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.detailLiveData.observe(this, projectDetailBean -> {
            ProjectDetailBean.DetailBean detail = projectDetailBean.getDetail();
            projectDetailVm.titleLiveData.postValue(detail.getTitle());
            projectDetailVm.locationLiveData.postValue(detail.getProvince() + detail.getCity());
            projectDetailVm.latitudeLiveData.postValue(new LatLng(detail.getLatitude(), detail.getLongitude()));
            projectDetailVm.introLiveData.postValue(detail.getIntro());
            projectDetailVm.materialLiveData.postValue(detail.getMaterials());
            projectDetailVm.collectionLiveData.postValue(projectDetailBean.getDetail().getCollection());
            projectDetailVm.subscribeLiveData.postValue(projectDetailBean.getDetail().getWarrant_status());

            tvUpdateTime.setText(String.format(getString(R.string.format_update), detail.getUpdate_time()));
            tvStage.setText(String.format(getString(R.string.format_stage), detail.getPhases()));
            tvType.setText(String.format(getString(R.string.format_type), detail.getType()));
            tvCycle.setText(String.format(getString(R.string.format_cycle), detail.getCycle()));
            tvAcreage.setText(String.format(getString(R.string.format_acreage), detail.getAcreage()));
            tvPrice.setText(String.format(getString(R.string.format_valuation), detail.getValuation()));
            tvDetail.setGlide(Glide.with(this));
            tvDetail.setHtml(detail.getIntro());
            tvMaterial.setText(detail.getMaterials());
            tvReadDetail.setVisibility(detail.getIs_pay() == 1? View.GONE: View.VISIBLE);

            setEmpty();
            contactAdapter.setNewData(projectDetailBean.getContact());
        });

       initPay();
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm.getDetail();
    }

    private void setEmpty() {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_top, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_contact));
        contactAdapter.setEmptyView(empty);
    }

    @OnClick({R.id.tv_read_detail})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_read_detail:
                addDialog(new CustomDialog(mActivity)
                        .setTip(String.format(getString(R.string.format_pay_price), projectDetailVm.detailLiveData.getValue().getDetail().getDetail_price()))
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
                        .showDialog());
                break;
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

        coinPayVm = ViewModelProviders.of(mActivity).get(CoinPayVm.class);
        coinPayVm.projectId = mActivity.getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);
        coinPayVm.contentPayLiveData.observe(this, coinPayResultBean -> {
            if (coinPayResultBean.getPay_status() == 1){
                //加入工程圈讨论组（群聊）
                getJoinGroup();
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

    //加入讨论组
    private void getJoinGroup() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_JOIN_GROUP)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("id",project_id)
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            IMFriendApplyCountBean bean=new Gson().fromJson(response.body(), IMFriendApplyCountBean.class);
                            if (bean.getCode().equals("200")){
                                ToastUtils.showShort(getString(R.string.buy_success));
                                projectDetailVm.getDetail();
                            }else if (bean.getCode().equals("-1")){
                                ToastUtils.showShort(bean.getMsg());
                                LoginUtils.getExitLogin(getActivity());
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    /**
     * 去支付
     */
    private void goToPay(){
        addDialog(new PayDialog(mActivity)
                .setSingleCallBack((item, position) -> {
                    coinPayVm.payProject(item);
                })
                .showDialog());
    }
}
