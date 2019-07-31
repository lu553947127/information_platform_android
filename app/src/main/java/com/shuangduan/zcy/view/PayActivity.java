package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.pay.PayResult;
import com.shuangduan.zcy.view.projectinfo.GoToSubActivity;
import com.shuangduan.zcy.view.projectinfo.SubOrderActivity;
import com.shuangduan.zcy.wxapi.PayVm;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.lang.ref.WeakReference;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
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
    private PayVm payVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.pay));

        tvPrice.setText("请支付1000元！");
        payVm = ViewModelProviders.of(this).get(PayVm.class);
        payVm.id = getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);
        payVm.payInfoLiveData.observe(this, payInfoBean -> {
            if (StringUtils.isTrimEmpty(payInfoBean.getAlipay())){
                PayReq request = new PayReq();
                request.appId = payInfoBean.getAppid();
                request.partnerId = payInfoBean.getPartnerid();
                request.prepayId= payInfoBean.getPrepayid();
                request.packageValue = "Sign=WXPay";
                request.nonceStr= payInfoBean.getNoncestr();
                request.timeStamp= String.valueOf(payInfoBean.getTimestamp());
                request.sign= payInfoBean.getSign();
                request.signType = payInfoBean.getSign_type();
                AppConfig.iwxapi.sendReq(request);
            }else {
                aliPay(payInfoBean.getAlipay());
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_ali, R.id.tv_wechat})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_ali:
                payVm.setType(1);
                payVm.getInfo();
                break;
            case R.id.tv_wechat:
                payVm.setType(2);
                payVm.getInfo();
                break;
        }
    }

    public void aliPay(String orderInfo){
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(PayActivity.this);
            Map<String,String> result = alipay.payV2(orderInfo,true);

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

    public static class PayHandler extends Handler{
        private WeakReference<AppCompatActivity> activity;

        public PayHandler(AppCompatActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            PayResult result = new PayResult((Map<String, String>) msg.obj);
            if (result.getResultStatus() != null){
                switch (result.getResultStatus()){
                    case "9000":
                        ToastUtils.showShort(activity.get().getString(R.string.pay_success));
                        LogUtils.i(result.getResult());
                        ActivityUtils.finishActivity(GoToSubActivity.class);
                        ActivityUtils.finishActivity(SubOrderActivity.class);
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
