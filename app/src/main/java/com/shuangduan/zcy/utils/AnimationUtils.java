package com.shuangduan.zcy.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: AnimationUtils
 * @Description: 动画工具类
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/10 16:57
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/10 16:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AnimationUtils {

    //显示fab动画
    public static void showFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(400).start();
    }

    //隐藏fab的动画
    public static void hideFABAnimation(View view){
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(400).start();
    }
}
