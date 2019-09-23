package com.shuangduan.zcy.rongyun.receiver;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class SealNotificationReceiver extends PushMessageReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
//        LogUtils.e("推送数据", pushNotificationMessage.getPushContent());
//        //1.获取系统通知的管理者
//        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        //2.初始化一个notification的对象
//        Notification.Builder mBuilder =new Notification.Builder(context);
//        //android 8.0 适配     需要配置 通知渠道NotificationChannel
//        NotificationChannel b;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            b = new NotificationChannel("1","乱七八糟的其他信息",         NotificationManager. IMPORTANCE_MIN);
//            nm.createNotificationChannel(b);
//            mBuilder.setChannelId("1");
//        }
//        //添加自定义视图  activity_notification
//        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification_message);
//        //主要设置setContent参数  其他参数 按需设置
//        mBuilder.setContent(mRemoteViews);
//        mBuilder.setSmallIcon(R.drawable.logo_about);
//        mBuilder.setOngoing(true);
//        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_about));
//        mBuilder.setAutoCancel(true);
//        //更新Notification
//        nm.notify(1,mBuilder .build()) ;
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage message) {
        return false;
    }
}
