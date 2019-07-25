package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

//import com.tencent.connect.share.QQShare;
//import com.tencent.connect.share.QzoneShare;
//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/06/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ShareUtils {

    private static final int THUMB_SIZE = 150;
//    public static int FRIEND = SendMessageToWX.Req.WXSceneSession;
//    public static int FRIEND_CIRCLE = SendMessageToWX.Req.WXSceneTimeline;

    public static final String app_id_qq = "1107824707";

    public static void shareWeChat(Context context, int shareTo, String url, String title, String description, Bitmap bitmap){
//        WXWebpageObject webPage = new WXWebpageObject();
//        webPage.webpageUrl = url;
//        WXMediaMessage msg = new WXMediaMessage(webPage);
//        msg.title = title;
//        msg.description = description;
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//        msg.thumbData = WXUtils.bmpToByteArray(thumbBmp, true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webPage");
//        req.message = msg;
//        req.scene = shareTo;
//        AppConfig.iwxapi.sendReq(req);
    }

//    public static void shareQQ(Activity context, Tencent tencent, IUiListener listener, String url, String title, String des, String img){
//        Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  des);
//        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  url);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, img);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  context.getString(R.string.app_name));
//
//        tencent.shareToQQ(context, params , listener);
//    }

//    public static void shareQQStone(Activity context, Tencent tencent, IUiListener listener, String url, String title, String des, String img){
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add(img);
//        Bundle params = new Bundle();
//        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
//        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
//        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, des);//选填
//        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrayList);
//
//        tencent.shareToQzone(context, params , listener);
//    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**通过图片url生成Bitmap对象
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * bitmap对象转为图片文件
     * @param bitmap
     * @param path
     * @return
     */
    public static File saveBitmapFile(Bitmap bitmap, String path) {
        File file=new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}
