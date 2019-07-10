package com.shuangduan.zcy.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;

import com.shuangduan.zcy.R;

/**
 * <pre>
 *     author : nwq
 *     time   : 2019/07/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DialogUtils {

    public static void enterCustomAnim(View view){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.setDuration(300);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
    }

    public static AnimatorSet exitCustomAnim(View view){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.setDuration(300);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
        return set;
    }

    public static void enterCustomAnim(Dialog dialog){
        Window window = dialog.getWindow();
        if (window != null){
            window.setWindowAnimations(R.style.DialogAnimCustom);
        }
    }

    public static void enterBottomAnim(Dialog dialog){
        Window window = dialog.getWindow();
        if (window != null){
            window.setWindowAnimations(R.style.DialogAnimBottom);
        }
    }

}
