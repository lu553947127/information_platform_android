package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CoinPayResultBean;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.DemandReleaseVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  发布找关系支付界面
 * @time 2019/8/21 16:16
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseRelationshipOrderActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private DemandReleaseVm demandReleaseVm;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_relationship_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.release));

        initPay();
        demandReleaseVm = ViewModelProviders.of(this).get(DemandReleaseVm.class);
        demandReleaseVm.relationshipOrderLiveData.observe(this, relationshipOrderBean -> {
            tvTitle.setText(relationshipOrderBean.getTitle());
            tvCommission.setText(String.format(getString(R.string.format_amount_bi), relationshipOrderBean.getPrice()));
            tvTime.setText(String.format(getString(R.string.format_validity_period_less), relationshipOrderBean.getStart_time(), relationshipOrderBean.getEnd_time()));
            tvDes.setText(relationshipOrderBean.getIntro());
            String price = "<font>共计支付</font><font color = '#EF583E'>" + relationshipOrderBean.getPrice() +"</font><font>元</font>";
            tvPrice.setText(Html.fromHtml(price));
        });
        demandReleaseVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        demandReleaseVm.relationshipReleaseOrder(getIntent().getIntExtra(CustomConfig.FIND_RELATIONSHIP_ID, 0));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_pay})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_pay:
                coinPayVm.releaseRelationshipId = getIntent().getIntExtra(CustomConfig.FIND_RELATIONSHIP_ID, 0);
                showPayDialog(demandReleaseVm.relationshipOrderLiveData.getValue().getPrice());
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
        coinPayVm.releaseRelationshipPayLiveData.observe(this, this::payResult);
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
                .setTip(String.format(getString(R.string.format_pay_price_release), price))
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
                    coinPayVm.payRelationshipRelease(item);
                })
                .showDialog());
    }

    private void payResult(CoinPayResultBean coinPayResultBean){
        if (coinPayResultBean.getPay_status() == 1){
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
