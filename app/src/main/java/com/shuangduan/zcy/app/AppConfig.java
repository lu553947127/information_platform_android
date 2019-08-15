package com.shuangduan.zcy.app;

import android.Manifest;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.callback.EmptyCallback;
import com.shuangduan.zcy.callback.ErrorCallback;
import com.shuangduan.zcy.callback.LoadingCallback;
import com.shuangduan.zcy.callback.TimeOutCallback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

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
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.transparent, R.color.colorPrimary);//全局设置主题颜色
                return new MaterialHeader(context).setColorSchemeResources(R.color.colorPrimary);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20).setAccentColorId(R.color.colorPrimary);
            }
        });
    }

    public static void init(Application context) {
        initUtils(context);

        initLoadSir();

        initWX(context);

//        initBugly(context);

        if (BuildConfig.IS_DEBUG){
            initCrash(context);
        }

        initJPush(context);
    }

    /**
     * 初始化Utils工具
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
     * @param context
     */
    private static void initCrash(final Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            CrashUtils.init(externalStorageDirectory.getPath() + "/happy_poetry_school/crash/");
        }
    }

    /**
     * 极光推送初始化配置
     */
    private static void initJPush(Context context){
        JPushInterface.setDebugMode(BuildConfig.IS_DEBUG);
        JPushInterface.init(context);
    }

}
