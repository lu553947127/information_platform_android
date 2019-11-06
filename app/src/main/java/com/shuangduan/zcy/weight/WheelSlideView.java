package com.shuangduan.zcy.weight;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.contrarywind.view.WheelView;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.weight$
 * @class WheelSlideView$
 * @class describe
 * @time 2019/11/4 17:56
 * @change
 * @class describe
 */
public class WheelSlideView extends WheelView {

    public WheelSlideView(Context context) {
        super(context);
    }

    public WheelSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setItemsVisibleCount(int visibleCount) {
        super.setItemsVisibleCount(visibleCount);
        invalidate();
    }

    //事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求所有父控件及祖宗控件不要拦截事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
