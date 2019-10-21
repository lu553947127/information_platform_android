package com.shuangduan.zcy.app;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.callback.EmptyCallback;
import com.shuangduan.zcy.callback.ErrorCallback;
import com.shuangduan.zcy.callback.LoadingCallback;
import com.shuangduan.zcy.callback.TimeOutCallback;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.utils.LoginUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;
import okhttp3.OkHttpClient;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/17
 *     desc   : application初始化
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
        //只支持5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initOkGo(context);
        }
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
    public static final String AppSecret = "53002bc09c67aa244de725a30b51dddd";
    public static IWXAPI iwxapi;

    private static void initWX(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }

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
        RongIM.init(context, RetrofitHelper.RONG_YUN_APP_KEY);
        //融云推送初始化
        PushConfig config = new PushConfig
                .Builder()
                .enableHWPush(true)
                .enableMiPush("2882303761518171760", "5861817164760")
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
                    LoginUtils.getExitLogin();
                    break;
            }
        });

        //设置消息回执的会话类型
        Conversation.ConversationType[] types = new Conversation.ConversationType[] {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP,
                Conversation.ConversationType.DISCUSSION
        };
        RongIM.getInstance().setReadReceiptConversationTypeList(types);
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : Objects.requireNonNull(activityManager).getRunningAppProcesses()) {
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

    //配置okgo网络框架
    private static void initOkGo(Application context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(context)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }
}
