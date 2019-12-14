package com.shuangduan.zcy.wxapi;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.model.bean.WXLoginBean;
import com.shuangduan.zcy.model.bean.WXUserInfoBean;
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
                if (baseResp.getType() == 2){
                    //分享
                    ToastUtils.showShort(getString(R.string.share_cancel));
                }else if (baseResp.getType() == 1){
                    //登录
                    ToastUtils.showShort("登录取消");
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
                    switch (state){
                        case "we_chat_login"://微信登录
                            getWeChatLogin(code);
                            break;
                        case "we_chat_set"://微信设置绑定
                            ToastUtils.showShort("绑定微信开始了");
                            break;
                    }
                }
                break;
        }
    }

    //微信登录获取openid
    private void getWeChatLogin(String code) {
        OkGo.<String>post(Common.WECHAT_LOGIN)
                .tag(this)
                .params("appid", AppConfig.APP_ID)//微信的appId
                .params("secret",AppConfig.AppSecret)//微信的appSecret
                .params("code",code)//微信登录返回的code
                .params("grant_type","authorization_code")
                .execute(new com.lzy.okgo.callback.StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.getException());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            WXLoginBean bean=new Gson().fromJson(response.body(), WXLoginBean.class);
                            if (bean!=null){
                                getWeChatLoginUserInfo(bean.getOpenid(),bean.getAccess_token());
                                LogUtils.i(bean.getOpenid());
                                LogUtils.i(bean.getUnionid());
                                LogUtils.i(bean.getAccess_token());
                                EventBus.getDefault().post(new WxLoginEvent(bean.getUnionid(),bean.getOpenid()));
                                finish();

                            }else {
                                ToastUtils.showShort("微信登录失败");
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    //获取微信用户的详情信息
    private void getWeChatLoginUserInfo(String openid,String access_token) {

        OkGo.<String>post(Common.WECHAT_LOGIN_USER_INFO)
                .tag(this)
                .params("access_token", access_token)
                .params("openid",openid)
                .execute(new com.lzy.okgo.callback.StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.getException());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            WXUserInfoBean bean=new Gson().fromJson(response.body(), WXUserInfoBean.class);
                            if (bean!=null){
                                LogUtils.i(bean.getNickname());
                                LogUtils.i(bean.getHeadimgurl());
                                SPUtils.getInstance().put(SpConfig.NICKNAME, bean.getNickname(), true);
                                SPUtils.getInstance().put(SpConfig.HEAD_IMG_URL, bean.getHeadimgurl(), true);
                            }else {
                                ToastUtils.showShort("获取微信用户信息失败");
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }
}
