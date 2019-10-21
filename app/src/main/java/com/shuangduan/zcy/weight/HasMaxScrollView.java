package com.shuangduan.zcy.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/11/21
 *     desc   : 可以设置最大高度的scrollview
 *     version: 1.0
 * </pre>
 */

public class HasMaxScrollView extends ScrollView {

    private int maxHeight;

    public HasMaxScrollView(Context context) {
        super(context);
        maxHeight = ConvertUtils.dp2px(100);
    }

    public HasMaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        maxHeight = ConvertUtils.dp2px(100);
    }

    public HasMaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        maxHeight = ConvertUtils.dp2px(100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置最大高度
     * @param maxHeight
     */
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
