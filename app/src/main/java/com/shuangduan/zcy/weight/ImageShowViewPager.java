package com.shuangduan.zcy.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight
 * @class describe  解决图片查看的bug
 * @time 2019/8/23 10:56
 * @change
 * @chang time
 * @class describe
 */
public class ImageShowViewPager extends ViewPager {
    public ImageShowViewPager(@NonNull Context context) {
        super(context);
    }

    public ImageShowViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写onInterceptTouchEvent()方法来解决图片点击缩小时候的Crash问题
     *
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }
        return false ;
    }
}
