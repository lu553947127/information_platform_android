package com.zcy.framelibrary.app;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * @ProjectName: information_platform_android
 * @Package: com.zcy.framelibrary.app
 * @ClassName: ActivityManager
 * @Description: java类作用描述
 * @Author: 徐玉
 * @CreateDate: 2020/3/7 14:42
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ActivityManager {

    private static ActivityManager sActivityManager;

    private WeakReference<Activity> sCurrentActivityWeakRef;


    //单例
    static {
        sActivityManager = new ActivityManager();
    }

    public static ActivityManager getInstance() {
        return sActivityManager;
    }


    private ActivityManager() {
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null && sCurrentActivityWeakRef.get() != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<>(activity);
    }


}
