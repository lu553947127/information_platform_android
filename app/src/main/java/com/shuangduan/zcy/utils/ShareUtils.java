package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.wxapi.WeChatUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import java.util.ArrayList;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/06/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ShareUtils {

    private static final int THUMB_SIZE = 120;
    public static int FRIEND = SendMessageToWX.Req.WXSceneSession;
    public static int FRIEND_CIRCLE = SendMessageToWX.Req.WXSceneTimeline;
    public static final String app_id_qq = "101795774";

    public static void shareWeChat( int shareTo, String url, String title, String description, Bitmap bitmap){
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = title;
        msg.description = description;
        if (bitmap!=null){
            LogUtils.i(bitmap);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
            msg.thumbData = WeChatUtils.bmpToByteArray(thumbBmp, true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webPage");
        req.message = msg;
        req.scene = shareTo;
        AppConfig.iwxapi.sendReq(req);
    }

    public static void shareQQ(Activity context, Tencent tencent, IUiListener listener, String url, String title, String des, String img){
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  des);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, img);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  context.getString(R.string.app_name));

        tencent.shareToQQ(context, params , listener);
    }

    public static void shareQQStone(Activity context, Tencent tencent, IUiListener listener, String url, String title, String des, String img){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(img);
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, des);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrayList);

        tencent.shareToQzone(context, params , listener);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
