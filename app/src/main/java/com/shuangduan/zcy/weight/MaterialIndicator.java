package com.shuangduan.zcy.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.shuangduan.zcy.R;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.weight
 * @ClassName: MaterialIndicator
 * @Description: 导航页圆点画工具类
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/17 9:21
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/17 9:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialIndicator extends View implements ViewPager.OnPageChangeListener {

    private static final String TAG = MaterialIndicator.class.getSimpleName();
    private static final int UNDEFINED_PADDING = -1;
    private final Interpolator interpolator = new FastOutSlowInInterpolator();
    private final Paint indicatorPaint;
    private final Paint selectedIndicatorPaint;
    private final float indicatorRadius;
    private final float indicatorPadding;

    private final RectF selectorRect;
    private int count;
    private int selectedPage = 0;
    private float deselectedAlpha = 0.2f;
    private float offset;

    public MaterialIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//被选中的指示点  //是使位图抗锯齿的标志
        indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//指示点
        indicatorPaint.setColor(Color.BLACK);//指示点的颜色
        indicatorPaint.setAlpha((int) (deselectedAlpha * 255)); //指示点设置透明度
        selectorRect = new RectF();
        if (isInEditMode()) {
            count = 3;
        }
        //获得我们所定义的自定义样式属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialIndicator, 0, R.style.MaterialIndicator);
        try {
            //获取指示点半径
            indicatorRadius = typedArray.getDimension(R.styleable.MaterialIndicator_mi_indicatorRadius, 0);
            //获取指示点间距
            indicatorPadding = typedArray.getDimension(R.styleable.MaterialIndicator_mi_indicatorPadding, UNDEFINED_PADDING);
            //获取颜色,并赋值给被选中的
            selectedIndicatorPaint.setColor(typedArray.getColor(R.styleable.MaterialIndicator_mi_indicatorColor, 0));
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        selectedPage = position;
        offset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        selectedPage = position;
        offset = 0;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setAdapter(PagerAdapter adapter) {
        this.count = adapter.getCount();
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (getLayoutParams().width == ViewPager.LayoutParams.WRAP_CONTENT) {
            width = getSuggestedMinimumWidth();
        }
        setMeasuredDimension(width, getSuggestedMinimumHeight());
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) (indicatorDiameter() * count + getInternalPadding());
    }

    private float getInternalPadding() {
        if (indicatorPadding == UNDEFINED_PADDING || indicatorPadding == 0 || count == 0) {
            return 0;
        }
        return indicatorPadding * (count - 1);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return getPaddingTop() + getPaddingBottom() + (int) indicatorDiameter();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float gap = getGapBetweenIndicators();
        for (int i = 0; i < count; i++) {
            float position = indicatorStartX(gap, i);
            canvas.drawCircle(position + indicatorRadius, midY(), indicatorRadius, indicatorPaint);
        }
        float extenderStart = indicatorStartX(gap, selectedPage) + Math.max(gap * (interpolatedOffset() - 0.5f) * 2, 0);
        float extenderEnd = indicatorStartX(gap, selectedPage) + indicatorDiameter() + Math.min(gap * interpolatedOffset() * 2, gap);
        selectorRect.set(extenderStart, midY() - indicatorRadius, extenderEnd, midY() + indicatorRadius);
        canvas.drawRoundRect(selectorRect, indicatorRadius, indicatorRadius, selectedIndicatorPaint);
    }

    private float getGapBetweenIndicators() {
        if (indicatorPadding == UNDEFINED_PADDING) {
            return (getWidth() - indicatorDiameter()) / (count + 1);
        } else {
            return indicatorPadding;
        }
    }

    private float indicatorStartX(float gap, int page) {
        return ViewCompat.getPaddingStart(this) + gap * page + indicatorRadius;
    }

    private float interpolatedOffset() {
        return interpolator.getInterpolation(offset);
    }

    private float indicatorDiameter() {
        return indicatorRadius * 2;
    }

    private float midY() {
        return getHeight() / 2f;
    }
}
