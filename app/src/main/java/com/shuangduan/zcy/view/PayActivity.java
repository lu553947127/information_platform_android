package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.pay.PayResult;
import com.shuangduan.zcy.view.recharge.RechargeResultActivity;
import com.shuangduan.zcy.wxapi.PayVm;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.lang.ref.WeakReference;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view
 * @class describe  支付
 * @time 2019/7/16 15:54
 * @change
 * @chang time
 * @class describe
 */
public class PayActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    private int ALIPAY_MSG = 1;
    public PayVm payVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_pay;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.pay));

        tvPrice.setText(String.format(getString(R.string.format_pay), getIntent().getStringExtra(CustomConfig.RECHARGE_AMOUNT)));
        payVm = getViewModel(PayVm.class);
        payVm.amount = getIntent().getStringExtra(CustomConfig.RECHARGE_AMOUNT);
        payVm.payInfoLiveData.observe(this, payInfoBean -> {
            if (StringUtils.isTrimEmpty(payInfoBean.getAlipay())) {
                PayReq request = new PayReq();
                request.appId = payInfoBean.getAppid();
                request.partnerId = payInfoBean.getPartnerid();
                request.prepayId = payInfoBean.getPrepayid();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = payInfoBean.getNoncestr();
                request.timeStamp = String.valueOf(payInfoBean.getTimestamp());
                request.sign = payInfoBean.getSign();
                request.signType = payInfoBean.getSign_type();
                request.extData = getIntent().getStringExtra(CustomConfig.RECHARGE_AMOUNT) + "," + payInfoBean.getOrder_sn();
                AppConfig.iwxapi.sendReq(request);
            } else {
                aliPay(payInfoBean.getAlipay());
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_ali, R.id.tv_wechat})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_ali:
                payVm.setType(CustomConfig.PAY_STYLE_ALIPAY);
                payVm.getInfo();
                break;
            case R.id.tv_wechat:
                payVm.setType(CustomConfig.PAY_STYLE_WECHAT);
                payVm.getInfo();
                break;
        }
    }

    public void aliPay(String orderInfo) {
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(PayActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);

            Message msg = new Message();
            msg.what = ALIPAY_MSG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(ALIPAY_MSG);
        super.onDestroy();
    }

    private PayHandler mHandler = new PayHandler(this);

    public static class PayHandler extends Handler {
        private WeakReference<PayActivity> activity;

        public PayHandler(PayActivity activity) {
            this.activity = new WeakReference(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            PayResult result = new PayResult((Map<String, String>) msg.obj);

            if (!TextUtils.isEmpty(result.getResult())) {
                switch (result.getResultStatus()) {
                    case "9000":
                        ToastUtils.showShort(activity.get().getString(R.string.pay_success));
                        PayResult.ResultBean resultBean = new Gson().fromJson(result.getResult(), PayResult.ResultBean.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(CustomConfig.PAY_STYLE, CustomConfig.PAY_STYLE_ALIPAY);
                        LogUtils.i(resultBean.getAlipay_trade_app_pay_response().getOut_trade_no(), resultBean.getAlipay_trade_app_pay_response().getTotal_amount());
                        bundle.putString(CustomConfig.ORDER_SN, resultBean.getAlipay_trade_app_pay_response().getOut_trade_no());
                        bundle.putString(CustomConfig.RECHARGE_AMOUNT, resultBean.getAlipay_trade_app_pay_response().getTotal_amount());
                        ActivityUtils.startActivity(bundle, RechargeResultActivity.class);
                        activity.get().finish();
                        break;
                    case "4000":
                        ToastUtils.showShort(activity.get().getString(R.string.pay_failed));
                        break;
                    case "6001":
                        ToastUtils.showShort(activity.get().getString(R.string.pay_cancle));
                        break;
                }
            }
        }
    }
}
