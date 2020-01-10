package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
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

import java.io.File;
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

    public static final String PACKAGE_WECHAT = "com.tencent.mm"; //微信
    public static final String PACKAGE_MOBILE_QQ = "com.tencent.mobileqq";//qq
    public static final String AUTHORITY = "com.shuangduan.zcy.FileProvider";

    public static void shareWeChat(int shareTo, String url, String title, String description, Bitmap bitmap){
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

    /**
     * 直接分享纯文本内容至QQ好友
     * @param mContext
     * @param content
     */
    public static void shareQQ(Context mContext, String content) {
        if (!LoginUtils.isQQClientAvailable(mContext)) {
            ToastUtils.showShort("您还没有安装QQ，请先安装QQ客户端");
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
        mContext.startActivity(intent);
    }

    /**
     * 分享图片给QQ好友
     * @param mContext
     * @param bitmap
     */
    public static void shareImageToQQ(Context mContext, Bitmap bitmap) {
        if (!LoginUtils.isQQClientAvailable(mContext)) {
            ToastUtils.showShort("您还没有安装QQ，请先安装QQ客户端");
            return;
        }
        try {
            Uri uriToImage = Uri.parse(MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bitmap, null, null));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("image/*");
            // 遍历所有支持发送图片的应用。找到需要的应用
            ComponentName componentName = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
            shareIntent.setComponent(componentName);
            // mContext.startActivity(shareIntent);
            mContext.startActivity(Intent.createChooser(shareIntent, "Share"));
        } catch (Exception e) {
            ToastUtils.showShort("分享图片到QQ失败");
        }
    }

    /**
     * 直接分享图片到微信好友
     * @param context
     * @param picFile
     */
    public static void shareWechatFriend(Context context, File picFile){
        if (!LoginUtils.isWeiXinInstall(context)) {
            ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
            return;
        }
        Intent intent = new Intent();
        ComponentName cop = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(cop);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (picFile != null) {
            if (picFile.isFile() && picFile.exists()) {
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(context, AUTHORITY, picFile);
                } else {
                    uri = Uri.fromFile(picFile);
                }
                intent.putExtra(Intent.EXTRA_STREAM, uri);
            }
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "Share"));
    }

    /**
     * 直接分享文本到微信好友
     * @param context
     * @param context 上下文
     */
    public void shareWechatFriend(Context context, String content) {
        if (!LoginUtils.isWeiXinInstall(context)) {
            ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
            return;
        }
        Intent intent = new Intent();
        ComponentName cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(cop);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("android.intent.extra.TEXT", content);
//            intent.putExtra("sms_body", content);
        intent.putExtra("Kdescription", !TextUtils.isEmpty(content) ? content : "");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 直接分享文本和图片到微信朋友圈
     * @param context
     * @param picFile
     */
    public static void shareWechatMoment(Context context, File picFile) {
        if (!LoginUtils.isWeiXinInstall(context)) {
            ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
            return;
        }
        Intent intent = new Intent();
        //分享精确到微信的页面，朋友圈页面，或者选择好友分享页面
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
//            intent.setAction(Intent.ACTION_SEND_MULTIPLE);// 分享多张图片时使用
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        //添加Uri图片地址--用于添加多张图片
        //ArrayList<Uri> imageUris = new ArrayList<>();
        //intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        if (picFile != null) {
            if (picFile.isFile() && picFile.exists()) {
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(context, AUTHORITY, picFile);
                } else {
                    uri = Uri.fromFile(picFile);
                }
                intent.putExtra(Intent.EXTRA_STREAM, uri);
            }
        }
        // 微信现不能进行标题同时分享
        // intent.putExtra("Kdescription", !TextUtils.isEmpty(content) ? content : "");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
