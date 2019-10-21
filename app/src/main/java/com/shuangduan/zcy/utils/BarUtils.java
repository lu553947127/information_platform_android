package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import static com.blankj.utilcode.util.ActivityUtils.getActivityByView;
import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.utils
 * @class describe  BarUtils补充
 * @time 2019/7/5 15:41
 * @change
 * @chang time
 * @class describe
 */
public class BarUtils {

    /**
     * Set the status bar's color. 修改为drawable， 可以设置渐变色
     *
     * @param fakeStatusBar The fake status bar view.
     * @param drawable         The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @DrawableRes final int drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) return;
        transparentStatusBar(activity);
        fakeStatusBar.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundResource(drawable);
    }

    /**
     * Set the status bar's color.
     *
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColorRes(@NonNull final View fakeStatusBar, @ColorInt final int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) return;
        transparentStatusBar(activity);
        fakeStatusBar.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(color);
    }

    private static void transparentStatusBar(final Activity activity) {
        com.blankj.utilcode.util.BarUtils.setStatusBarColor(activity, activity.getResources().getColor(android.R.color.transparent));
    }

}
