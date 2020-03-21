package com.shuangduan.zcy.rongyun.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.view.MainActivity;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

import static android.content.Context.NOTIFICATION_SERVICE;

public class SealNotificationReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage message) {
//        LogUtils.e("推送数据", "onNotificationMessageArrived....点击了");
//        LogUtils.e("推送数据", "message"+message);
//        LogUtils.e("推送数据", "message"+message.getTargetId());
//        LogUtils.e("推送数据", "message"+message.getTargetUserName());
//        LogUtils.e("推送数据", "message"+message.getPushContent());
//        LogUtils.e("推送数据", "message"+message.getPushTitle());
//        //1.获取系统通知的管理者
//        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        //2.初始化一个notification的对象
//        Notification.Builder mBuilder =new Notification.Builder(context);
//        //android 8.0 适配     需要配置 通知渠道NotificationChannel
//        NotificationChannel b;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            b = new NotificationChannel("1","11111",NotificationManager. IMPORTANCE_MIN);
//            nm.createNotificationChannel(b);
//            mBuilder.setChannelId("1");
//        }
//        //添加自定义视图  activity_notification
////        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification_message);
////        //主要设置setContent参数  其他参数 按需设置
////        mBuilder.setContent(mRemoteViews);
//        mBuilder.setContentTitle(message.getTargetUserName());
//        mBuilder.setContentText(message.getPushContent());
//        //小图标
//        mBuilder.setSmallIcon(R.drawable.app_logo);
//        mBuilder.setOngoing(true);
//        //大图标
//        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.app_logo));
//        mBuilder.setAutoCancel(true);
//
//        //点击通知跳转
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("statueCar", "1");
//        PendingIntent pi = PendingIntent.getActivity(context, 0, intent,PendingIntent.FLAG_ONE_SHOT);
//        mBuilder.setContentIntent(pi);
//        //更新Notification
//        nm.notify(1,mBuilder .build()) ;
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage message) {
//        LogUtils.json(LogUtils.E, message);
        return false;
    }

    @Override
    public void onThirdPartyPushState(PushType pushType, String action, long resultCode) {
        super.onThirdPartyPushState(pushType, action, resultCode);
    }
}
