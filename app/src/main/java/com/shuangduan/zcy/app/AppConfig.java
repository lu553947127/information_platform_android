package com.shuangduan.zcy.app;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.os.Process;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.callback.EmptyCallback;
import com.shuangduan.zcy.callback.ErrorCallback;
import com.shuangduan.zcy.callback.LoadingCallback;
import com.shuangduan.zcy.callback.TimeOutCallback;
import com.shuangduan.zcy.view.login.WelcomeActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.File;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AppConfig {

    //初始化刷新工具
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(android.R.color.transparent, R.color.colorPrimary);//全局设置主题颜色
            return new MaterialHeader(context).setColorSchemeResources(R.color.colorPrimary);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20).setAccentColorId(R.color.colorPrimary);
        });
    }

    public static void init(Application context) {
        initUtils(context);

        initLoadSir();

        initWX(context);

//        initBugly(context);

        if (BuildConfig.IS_DEBUG) {
            initCrash(context);
        }

        initJPush(context);

        initRongYun(context);
    }

    /**
     * 初始化Utils工具
     *
     * @param context
     */
    private static void initUtils(Application context) {
        Utils.init(context);

        LogUtils.getConfig().setGlobalTag(context.getString(R.string.app_name)).setLogSwitch(BuildConfig.IS_SHOW_LOG);

        ToastUtils.setGravity(Gravity.BOTTOM, 0, ConvertUtils.dp2px(40));
        ToastUtils.setMsgTextSize(13);
        ToastUtils.setMsgColor(Color.parseColor("#ffffff"));
        ToastUtils.setBgResource(R.drawable.shape_bg_toast);
    }

    /**
     * 初始化状态页工具
     */
    private static void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeOutCallback())
                .setDefaultCallback(SuccessCallback.class)//设置默认状态页
                .commit();
    }

    /**
     * 微信初始化配置
     */
    public static final String APP_ID = "wx2e2f0d4ccdf3e52f";
    public static IWXAPI iwxapi;

    private static void initWX(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }

    /*private static void initBugly(Context context) {
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Bugly.init(context, "52396056dc", BuildConfig.IS_DEBUG);
    }*/

    /**
     * 测试版本收集崩溃日志
     *
     * @param context
     */
    private static void initCrash(final Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            CrashUtils.init(externalStorageDirectory.getPath() + "/zcy/crash/");
        }
    }

    /**
     * 极光推送初始化配置
     */
    private static void initJPush(Context context) {
        JPushInterface.setDebugMode(BuildConfig.IS_DEBUG);
        JPushInterface.init(context);
    }

    private static void initRongYun(Context context) {

        //初始化小米push推送服务
//        if (shouldInit(context)) {
//            MiPushClient.registerPush(context, "2882303761517473625", "5451747338625");
//        }


        //初始化OPPO推送 ，此功能需要在初始化融云之前
//        PushConfig.Builder builder = new PushConfig.Builder();
//        builder.enableOppoPush("6bbb2e614c374489b2aca7016c4cbceb", "7e187275fb0645aea6fd3a3e85aedcb5");
//        RongPushClient.setPushConfig(builder.build());

        //融云初始化
        RongIM.init(context, "pwe86ga5p43i6");
        //融云推送初始化
        PushConfig config = new PushConfig
                .Builder()
                .enableHWPush(true)
                .enableMiPush("2882303761517473625", "5451747338625")
                .enableMeiZuPush("112988", "2fa951a802ac4bd5843d694517307896")
                .enableVivoPush(true)
                .enableOppoPush("6bbb2e614c374489b2aca7016c4cbceb", "7e187275fb0645aea6fd3a3e85aedcb5")
                .enableFCM(true)
                .build();
        RongPushClient.setPushConfig(config);

        //设置融云全局链接状态监听
        RongIMClient.setConnectionStatusListener(connectionStatus -> {
            switch (connectionStatus) {
                //融云账号在其他设备上进行登录
                case KICKED_OFFLINE_BY_OTHER_CLIENT:
                    SPUtils.getInstance().clear();
                    SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
                    ActivityUtils.startActivity(WelcomeActivity.class);
                    ActivityUtils.finishAllActivitiesExceptNewest();
                    break;
            }
        });
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    //判断类型
    private static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
