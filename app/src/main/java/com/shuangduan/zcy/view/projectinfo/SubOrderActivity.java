package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CoinPayResultBean;
import com.shuangduan.zcy.model.bean.ProjectSubConfirmBean;
import com.shuangduan.zcy.model.event.WarrantSuccessEvent;
import com.shuangduan.zcy.view.PayActivity;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  认购订单
 * @time 2019/7/16 15:34
 * @change
 * @chang time
 * @class describe
 */
public class SubOrderActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_sub_amount)
    TextView tvSubAmount;
    @BindView(R.id.tv_sub_person)
    TextView tvSubPerson;
    @BindView(R.id.tv_contact_info)
    TextView tvContactInfo;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_sub_cycle)
    TextView tvSubCycle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;
    private ProjectSubConfirmBean confirmBean;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_sub_order;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.sub_order));
        confirmBean = getIntent().getParcelableExtra(CustomConfig.ORDER);
        tvProjectName.setText(confirmBean.getTitle());
        tvSubAmount.setText(confirmBean.getPrice());
        tvSubPerson.setText(SPUtils.getInstance().getString(SpConfig.USERNAME));
        tvContactInfo.setText(SPUtils.getInstance().getString(SpConfig.MOBILE));
        tvOrderNum.setText(confirmBean.getOrder_sn());
        tvSubCycle.setText(confirmBean.getTime());
        String price = "<font>共计支付</font><font color = '#EF583E'>" + confirmBean.getPrice() +"</font><font>元</font>";
        tvPrice.setText(Html.fromHtml(price));

        initPay();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_pay})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_pay:
                coinPayVm.projectId = getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);
                coinPayVm.orderId = confirmBean.getOrder_id();
                showPayDialog(confirmBean.getPrice());
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


        coinPayVm = ViewModelProviders.of(this).get(CoinPayVm.class);
        coinPayVm.warrantPayLiveData.observe(this, this::payResult);
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

    private void showPayDialog(String price){
        addDialog(new CustomDialog(this)
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
                .showDialog());
    }

    /**
     * 去支付
     */
    private void goToPay(){
        addDialog(new PayDialog(this)
                .setSingleCallBack((item, position) -> {
                    coinPayVm.payWarrant(item);
                })
                .showDialog());
    }

    private void payResult(CoinPayResultBean coinPayResultBean){
        if (coinPayResultBean.getPay_status() == 1){
            ToastUtils.showShort(getString(R.string.pay_success));
            EventBus.getDefault().post(new WarrantSuccessEvent());
            ActivityUtils.finishActivity(GoToSubActivity.class);
            finish();
        }else {
            //余额不足
            addDialog(new CustomDialog(this)
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
    }
}
