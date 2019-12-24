package com.shuangduan.zcy.wxapi;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.model.event.WxBindEvent;
import com.shuangduan.zcy.model.event.WxLoginEvent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/05/25
 *     desc   : 微信分享和登录返回结果页
 *     version: 1.0
 * </pre>
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConfig.iwxapi.handleIntent(getIntent(), this);
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
                switch (baseResp.getType()){
                    case 1://微信绑定/登录
                        ToastUtils.showShort("登录取消");
                        break;
                    case 2://微信分享
                        ToastUtils.showShort(getString(R.string.share_cancel));
                        break;
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()){
                    case 1://微信绑定/登录
                        String code = ((SendAuth.Resp) baseResp).code;
                        String state = ((SendAuth.Resp) baseResp).state;
                        LogUtils.i(code);
                        switch (state){
                            case "we_chat_login"://微信登录
                                EventBus.getDefault().post(new WxLoginEvent(code));
                                break;
                            case "we_chat_set"://微信内部绑定
                                EventBus.getDefault().post(new WxBindEvent(code));
                                break;
                        }
                        finish();
                        break;
                    case 2://微信分享
                        ToastUtils.showShort(getString(R.string.share_success));
                        break;
                }
                finish();
                break;
        }
    }
}
