package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/06/08
 *     desc   : 今日头条提供的屏幕适配方案
 *     version: 1.0
 * </pre>
 */

public class AutoUtils {

    private static float sNoCompatDensity;
    private static float sNoCompatScaleDensity;

    private static final float width = 375;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application){

        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoCompatDensity == 0){
            sNoCompatDensity = appDisplayMetrics.density;
            sNoCompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0){
                        sNoCompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / width;
        final float targetScaleDensity = targetDensity * (sNoCompatScaleDensity / sNoCompatDensity);
        final int targetDensityDpi = (int) (targetDensity * 160);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

    }

}
