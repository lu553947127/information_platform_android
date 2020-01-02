package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.login.LoginActivity;
import com.shuangduan.zcy.view.material.MaterialDetailActivity;
import com.shuangduan.zcy.view.material.MaterialEquipmentDetailActivity;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/01
 *     desc   : 检测是否登录
 *     version: 1.0
 * </pre>
 */

public class LoginUtils {

    /**
     * 检测是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        String token = SPUtils.getInstance().getString(SpConfig.TOKEN);
        if (StringUtils.isTrimEmpty(token)) {
            return false;
        }
        return true;
    }

    /**
     * 检测是否第一次打开app
     *
     * @return
     */
    public static boolean isFirstApp() {
        int first = SPUtils.getInstance().getInt(SpConfig.FIRST_APP);
        LogUtils.i(first);
        if (first == -1) {
            return false;
        }
        return true;
    }

    /**
     * token出错账号顶下线
     *
     * @return
     */
    public static void getExitLogin() {
        SPUtils.getInstance().clear(true);
        SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
        ActivityUtils.startActivity(LoginActivity.class);
        ActivityUtils.finishOtherActivities(LoginActivity.class);
        RongIM.getInstance().logout();
    }

    /**
     * 获取从js跳转原生app的参数
     *
     * @return
     */
    public static void getJavaScriptData(Activity activity) {
        //我们怎样在StartActivity 中获取传过来的参数
        Intent intent=activity.getIntent();
        //获取跳转的协议
        Uri uri=intent.getData();
        //跳转协会为空，说明应用是正常启动
        //总结：对协议的书写要求比较高，一定要注意大小写，和规则规范，和前端调用保持一致
        if(uri!=null) {
            LogUtils.e(uri);
            String id=uri.getQueryParameter("id");
            String type=uri.getQueryParameter("type");
            LogUtils.e(id);
            LogUtils.e(type);
            if (!TextUtils.isEmpty(type)&&!TextUtils.isEmpty(id)){
                Bundle bundle = new Bundle();
                switch (type){
                    case "1"://周转材料
                        bundle.putInt(CustomConfig.MATERIAL_ID, Integer.parseInt(id));
                        ActivityUtils.startActivity(bundle, MaterialDetailActivity.class);
                        break;
                    case "2"://设备
                        bundle.putInt(CustomConfig.MATERIAL_ID, Integer.parseInt(id));
                        ActivityUtils.startActivity(bundle, MaterialEquipmentDetailActivity.class);
                        break;
                }
            }else {
                ActivityUtils.startActivity(MainActivity.class);
            }
        }else {
            ActivityUtils.startActivity(MainActivity.class);
        }
    }

    /**
     * 判断是否安装微信
     *
     * @return
     */
    public static boolean isWeiXinInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packageManager
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        for (int i = 0; i < packageInfoList.size(); i++) {
            String pn = packageInfoList.get(i).packageName;
            if (pn.equals("com.tencent.mm")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context){
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                LogUtils.e("pn = " + pn);
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
