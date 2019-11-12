package com.shuangduan.zcy.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.TextView;

/**
 * <pre>
 *     author : 徐玉
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


    public static GradientDrawable getDrawable(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(DensityUtil.dp2px(radius));//设置4个角的弧度
        drawable.setColor(color);// 设置颜色
        return drawable;
    }

    /**
     * 产生shape类型的drawable
     *
     * @param solidColor
     * @param strokeColor
     * @param strokeWidth
     * @param radius
     * @return
     */
    public static GradientDrawable getDrawable(int solidColor, int strokeColor, int strokeWidth, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(solidColor);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static StateListDrawable createSelectorDrawable(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);// 按下显示的图片
        stateListDrawable.addState(new int[]{}, normalDrawable);// 抬起显示的图片
        return stateListDrawable;
    }
}
