package com.shuangduan.zcy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.view.login.LoginActivity;

import java.util.List;

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
     * 检测登录，未登录则跳转登录界面
     *
     * @return
     */
    public static boolean checkLogin() {
        String token = SPUtils.getInstance().getString(SpConfig.TOKEN);
        if (StringUtils.isTrimEmpty(token)) {
            ActivityUtils.startActivity(LoginActivity.class);
            return false;
        }
        return true;
    }

    /**
     * 是否登录
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
}
