package com.shuangduan.zcy.annotation;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shuangduan.zcy.R;

public class ViewUtils {
    public static void inject(final Activity activity) {
        Class clazz = activity.getClass();
        OnNetwork annotation = (OnNetwork) clazz.getAnnotation(OnNetwork.class);
        if (annotation != null && !isNetWorkAvailable(activity)) {
            activity.setContentView(R.layout.layout_time_out);
            return;
        }
    }


    /**
     * 是否有网络
     *
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo[] networkInfos = manager.getAllNetworkInfo();
        if (networkInfos != null) {
            for (NetworkInfo info : networkInfos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
