package com.shuangduan.zcy.wxapi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.utils.LoginUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.ByteArrayOutputStream;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/06/07
 *     desc   : 微信登录工具
 *     version: 1.0
 * </pre>
 */

public class WeChatUtils {

    //Bitmap图片转换
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //微信登陆
    public static void getWeChatLogin(Activity activity,String state) {
        if (LoginUtils.isWeiXinInstall(activity)) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = state;
            AppConfig.iwxapi.sendReq(req);
        } else {
            ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
        }
    }
}
