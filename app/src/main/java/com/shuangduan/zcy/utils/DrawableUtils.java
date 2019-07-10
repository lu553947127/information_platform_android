package com.shuangduan.zcy.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/10/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class DrawableUtils {

    public static void setDrawableLeft(Context context, TextView attention, int drawableId) {

        Drawable drawable = context.getResources().getDrawable(drawableId);

        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        attention.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setDrawableRight(Context context, TextView attention, int drawableId) {

        Drawable drawable = context.getResources().getDrawable(drawableId);

        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        attention.setCompoundDrawables(null, null, drawable, null);
    }

    public static void setDrawableBottom(Context context, TextView attention, int drawableId) {

        Drawable drawable = context.getResources().getDrawable(drawableId);

        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        attention.setCompoundDrawables(null, null, null, drawable);
    }

}
