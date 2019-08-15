package com.shuangduan.zcy.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/04/20
 *     desc   : 自定义JPush广播接收器
 *     version: 1.0
 * </pre>
 */

public class LHBroadcastReceiver extends BroadcastReceiver {

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        LogUtils.i("onReceive - " + intent.getAction() );

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtils.i("JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.i("接受到推送下来的自定义消息");

            // Push Talk messages are push down by custom message format
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.i("接受到推送下来的通知");

            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.i("用户点击打开了通知");

            openNotification(context,bundle);

        } else {
            LogUtils.i("Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtils.i(" title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogUtils.i("message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.i("extras : " + extras);

        try {
//            JPushBean jPushBean = new Gson().fromJson(extras, JPushBean.class);
//            switch (jPushBean.getType()){
//                case CHALLENGE_PK:
//                    break;
//                case JOIN_TO_CLASS:
//                    break;
//                case JOIN_SUCCESS:
//                    EventBus.getDefault().post(new BjListRefreshEvent());
//                    break;
//            }
        }catch (Exception e){
            LogUtils.i(e.getCause(), e.getMessage());
        }
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        try {
//            JPushBean jPushBean = new Gson().fromJson(extras, JPushBean.class);
//            Bundle bundleResult = new Bundle();
//            switch (jPushBean.getType()){
//                case CHALLENGE_PK:
//                    if (ActivityUtils.isActivityExistsInStack(ChallengeSplashActivity.class)){
//                        ToastUtils.showShort(context.getString(R.string.not_join_when_gaming));
//                        return;
//                    }
//                    if (ActivityUtils.isActivityExistsInStack(ChallengeActivity.class)){
//                        ToastUtils.showShort(context.getString(R.string.not_join_when_gaming));
//                        return;
//                    }
//                    if (ActivityUtils.isActivityExistsInStack(PKHouseActivity.class)){
//                        ToastUtils.showShort(context.getString(R.string.not_join_when_gaming));
//                        return;
//                    }
//                    bundleResult.putString(CustomConfig.HOUSE_ID, jPushBean.getRoomNo());
//                    ActivityUtils.startActivity(bundleResult, PKHouseActivity.class);
//                    break;
//                case JOIN_TO_CLASS:
//                    bundle.putInt(CustomConfig.BJ_ID, jPushBean.getClassId());
//                    bundle.putString(CustomConfig.BJ_NUMBER, jPushBean.getRoomNum());
//                    ActivityUtils.startActivity(bundle, BjMemberManageActivity.class);
//                    break;
//                case JOIN_SUCCESS:
//                    if (!AppUtils.isAppForeground()){
//                        moveAppTop(context);
//                    }
//                    break;
//            }
//        }catch (Exception e){
//            LogUtils.i(e.getCause(), e.getMessage());
//        }
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        if (StringUtils.isEmpty(title)) {
            LogUtils.i("Unexpected: empty title (friend). Give up");
            return;
        }

    }

//    private void moveAppTop(Context context){
//        //获取ActivityManager
//        ActivityManager mAm = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        //获得当前运行的task
//        if (mAm != null){
//            List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
//            for(ActivityManager.RunningTaskInfo rti : taskList) {
//                //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
//                if(rti.topActivity.getPackageName().equals(context.getPackageName())) {
//                    Intent launchIntent = new Intent(Intent.ACTION_MAIN);
//                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    ComponentName cn = new ComponentName(AppUtils.getAppPackageName(),rti.topActivity.getClassName());
//                    launchIntent.setComponent(cn);
//                    context.startActivity(launchIntent);
//                    break;
//                }
//            }
//        }
//
//    }

}
