package com.shuangduan.zcy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.view.PayActivity;
import com.shuangduan.zcy.view.projectinfo.GoToSubActivity;
import com.shuangduan.zcy.view.projectinfo.SubOrderActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, AppConfig.APP_ID);
        api.registerApp(AppConfig.APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.i(baseReq.getType());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.i("onPayFinish, errCode = " + baseResp.errCode);

        LogUtils.i(baseResp.getType());
        LogUtils.i(baseResp.errStr);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            LogUtils.i("");
        }

        if (baseResp.errCode == 0){
            ToastUtils.showShort(getString(R.string.pay_success));
            ActivityUtils.finishActivity(GoToSubActivity.class);
            ActivityUtils.finishActivity(SubOrderActivity.class);
            ActivityUtils.finishActivity(PayActivity.class);
        }else if (baseResp.errCode == -1){
            ToastUtils.showShort(getString(R.string.pay_failed));
        }else if (baseResp.errCode == -2){
            ToastUtils.showShort("支付取消");
        }

        finish();

    }

}
