package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;

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
public class ProjectContentFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

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
    TextView tvDetail;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.rv_contact)
    RecyclerView rvContact;
    @BindView(R.id.tv_read_detail)
    TextView tvReadDetail;
    @BindView(R.id.ll_material)
    LinearLayout llMaterial;

    @BindView(R.id.iv_attn)
    ImageView ivAttn;

    private ProjectDetailVm projectDetailVm;
    private ContactAdapter contactAdapter;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;
    private static int project_id;

    public static ProjectContentFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        project_id = id;
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
        contactAdapter = new ContactAdapter(R.layout.item_contact, null);
        contactAdapter.setEmptyView(R.layout.layout_loading_top, rvContact);
        contactAdapter.setOnItemChildClickListener(this);

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

            tvAcreage.setText(detail.getAcreage().equals("未确定") ? String.format(getString(R.string.format_no_acreage), detail.getAcreage()) :
                    String.format(getString(R.string.format_acreage), detail.getAcreage()));

            tvPrice.setText(String.format(getString(R.string.format_valuation), detail.getValuation()));
//            tvDetail.setGlide(Glide.with(this));


            String introStr = detail.getIntro().replace("\\n", "\n");
            String materialStr = detail.getMaterials();

            if (detail.getIs_pay() == 1) {
                tvDetail.setText(introStr);
                tvMaterial.setText(materialStr);
            } else {
                tvDetail.setHighlightColor(getResources().getColor(R.color.color_6a5ff8));
                SpannableString detailInfo = new SpannableString(introStr.trim() + " 查看详情");
                detailInfo.setSpan(new Clickable(v1 -> addPayDialog()), introStr.length(), detailInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvDetail.setText(detailInfo);
                tvDetail.setMovementMethod(LinkMovementMethod.getInstance());

                tvMaterial.setHighlightColor(getResources().getColor(R.color.color_6a5ff8));
                SpannableString materialInfo = new SpannableString(materialStr.trim() + " 查看详情");
                materialInfo.setSpan(new Clickable(v1 -> addPayDialog()), materialStr.length(), materialInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvMaterial.setText(materialInfo);
                tvMaterial.setMovementMethod(LinkMovementMethod.getInstance());
            }


            llMaterial.setVisibility(StringUtils.isTrimEmpty(detail.getMaterials()) ? View.GONE : View.VISIBLE);


//            tvReadDetail.setVisibility(detail.getIs_pay() == 1 ? View.GONE : View.VISIBLE);

            setEmpty();
            contactAdapter.setIsPay(detail.getIs_pay());
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
                addPayDialog();
                break;
        }
    }

    private void initPay() {
        //支付密码状态查询
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.stateLiveData.observe(this, pwdPayStateBean -> {
            int status = pwdPayStateBean.getStatus();
            SPUtils.getInstance().put(SpConfig.PWD_PAY_STATUS, status);
            if (status == 1) {
                goToPay();
            } else {
                ActivityUtils.startActivity(SetPwdPayActivity.class);
            }
        });
        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
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
            if (coinPayResultBean.getPay_status() == 1) {
                //加入工程圈讨论组（群聊）
                getJoinGroup();
            } else {
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
            switch (s) {
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

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL + Common.WECHAT_JOIN_GROUP)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("id", project_id)
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
                            IMFriendApplyCountBean bean = new Gson().fromJson(response.body(), IMFriendApplyCountBean.class);
                            if (bean.getCode().equals("200")) {
                                ToastUtils.showShort(getString(R.string.buy_success));
                                projectDetailVm.getDetail();
                            } else if (bean.getCode().equals("-1")) {
                                ToastUtils.showShort(bean.getMsg());
                                LoginUtils.getExitLogin();
                            } else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        } catch (JsonSyntaxException | IllegalStateException ignored) {
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    /**
     * 去支付
     */
    private void goToPay() {
        addDialog(new PayDialog(mActivity)
                .setSingleCallBack((item, position) -> {
                    coinPayVm.payProject(item);
                })
                .showDialog());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_phone:
                addPayDialog();
                break;
        }
    }


    private void addPayDialog() {
        addDialog(new CustomDialog(mActivity)
                .setTip(String.format(getString(R.string.format_pay_price), projectDetailVm.detailLiveData.getValue().getDetail().getDetail_price()))
                .setCallBack(new BaseDialog.CallBack() {
                    @Override
                    public void cancel() {
                    }

                    @Override
                    public void ok(String s) {
                        int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
                        if (status == 1) {
                            goToPay();
                        } else {
                            //查询是否设置支付密码
                            updatePwdPayVm.payPwdState();
                        }
                    }
                })
                .showDialog());
    }


    /**
     * 查看详情的点击事件
     */
    class Clickable extends ClickableSpan {

        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        /*** 重写父类点击事件*/

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        /*** 重写父类updateDrawState方法 我们可以给TextView设置字体颜色,背景颜色等等...*/
        @Override

        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.color_6a5ff8));
        }//可点击文字的颜色

    }

}
