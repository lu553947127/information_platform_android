package com.shuangduan.zcy.wxapi;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.AppConfig;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/05/25
 *     desc   : 微信分享
 *     version: 1.0
 * </pre>
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static IWXAPI iwxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, AppConfig.APP_ID, true);
        iwxapi.registerApp(AppConfig.APP_ID);
        iwxapi.handleIntent(getIntent(), this);
        //透明状态栏
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(Utils.getApp(), R.color.colorPrimary), true);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.i(baseReq.getType());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.i(baseResp.errCode);
        LogUtils.i(baseResp.errStr);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (baseResp.getType() == 2){
                    //分享
                    ToastUtils.showShort(getString(R.string.share_cancel));
                }else if (baseResp.getType() == 1){
                    //登录
//                    ToastUtils.showShort(getString(R.string.login_cancel));
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == 2){
                    //分享
                    ToastUtils.showShort(getString(R.string.share_success));
                    finish();
                }else if (baseResp.getType() == 1){
                    //登录
                    String code = ((SendAuth.Resp) baseResp).code;
                    String state = ((SendAuth.Resp) baseResp).state;
                    if ("wechat_sdk_bind_diaochong".equals(state)){
                        //绑定微信
//                        EventBus.getDefault().post(new BindWxRefresh(code));
                    } else {
                        //登录
//                        EventBus.getDefault().post(new WxLoginEvent(code));
                    }
                    finish();
                }
                break;
        }
    }

}
