package com.shuangduan.zcy.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.shuangduan.zcy.R;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.weight
 * @ClassName: XTabItem
 * @Description: 带滑动动画的tab
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/14 14:43
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/14 14:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class XTabItem extends View {

    public final CharSequence mText;
    public final Drawable mIcon;
    public final int mCustomLayout;

    public XTabItem(Context context) {
        this(context, null);
    }

    public XTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.XTabItem, 0, 0);
        mText = a.getText(R.styleable.XTabItem_android_text);
        mIcon = a.getDrawable(R.styleable.XTabItem_android_icon);
        mCustomLayout = a.getResourceId(R.styleable.XTabItem_android_layout, 0);
        a.recycle();
    }
}
