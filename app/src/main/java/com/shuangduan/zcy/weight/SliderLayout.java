package com.shuangduan.zcy.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : nwq
 *     time   : 2019/06/18
 *     desc   : 背景滑块
 *     version: 1.0
 * </pre>
 */

public class SliderLayout extends LinearLayout {
    private static final String TAG = "SliderLayout";
    private Drawable bg_img;
    private ImageView mSlider;
    private WeakReference<TabLayout> mTabLayoutRef;
    private int totalNum;

    public SliderLayout(Context context) {
        this(context, null);
    }

    public SliderLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SliderLayout);
        bg_img = array.getDrawable(R.styleable.SliderLayout_slider_drawable);
        if (bg_img == null) {
            bg_img = context.getResources().getDrawable(R.drawable.shape_bg_tab);
        }
        array.recycle();
        init(context);
    }

    private void init(Context context){
        mSlider = new ImageView(context);
        mSlider.setImageDrawable(bg_img);
        addView(mSlider, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 重新设置滑块大小
     */
    public void resetSlider(int position){
        if (getOrientation() == HORIZONTAL){
            resetHorizontalSlider(position);
        }
    }

    private void resetHorizontalSlider(int position){
        if (mTabLayoutRef == null) return;
        TabLayout tabLayout = mTabLayoutRef.get();
        if (tabLayout == null) return;
        LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
        totalNum = mTabStrip.getChildCount();
        if (totalNum > 0) {
            View firstView = mTabStrip.getChildAt(position);
            int width = firstView.getWidth();
            resetSliderImg(width);
            Log.i(TAG, "resetHorizontalSlider: " + width);
        }
    }

    private void resetSliderImg(int width){
        if (width == 0) return;
        LayoutParams params = (LayoutParams) mSlider.getLayoutParams();
        params.width = width;
        params.height = getHeight()/2;
        params.gravity = Gravity.CENTER_VERTICAL;
        mSlider.setPadding(width/10, 0, width/10, 0);
        mSlider.setLayoutParams(params);
        Log.i(TAG, "resetSlider: " + width + "total" + totalNum);
    }

    public void setmTabLayoutRef(TabLayout tabLayout) {
        this.mTabLayoutRef = new WeakReference<TabLayout>(tabLayout);
        Log.i(TAG, "setmTabLayoutRef: init");
    }

    public static class SliderOnPageChangeListener  extends TabLayout.TabLayoutOnPageChangeListener{

        private WeakReference<SliderLayout> mSliderLayoutRef;

        public SliderOnPageChangeListener(TabLayout tabLayout, SliderLayout sliderLayout) {
            super(tabLayout);
            mSliderLayoutRef = new WeakReference<SliderLayout>(sliderLayout);
            sliderLayout.setmTabLayoutRef(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            SliderLayout sliderLayout = mSliderLayoutRef.get();
            if (sliderLayout != null) {
                sliderLayout.setScrollPosition(position, positionOffset);
            }
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mSliderLayoutRef.get().resetSlider(position);
        }
    }

    private void setScrollPosition(int position, float positionOffset){
        int roundPosition = Math.round(position + positionOffset);
        if (roundPosition < 0 || roundPosition > totalNum){
            return;
        }
        int scrollX = calculateScrollXForTab(position, positionOffset);
        scrollTo(scrollX, 0);
    }

    private int calculateScrollXForTab(int position, float positionOffset){
        TabLayout tabLayout = mTabLayoutRef.get();
        if (tabLayout == null) return 0;
        LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
        if (mTabStrip == null) return 0;
        View selectedChild = mTabStrip.getChildAt(position);
        View nextChild = position + 1 < mTabStrip.getChildCount() ? mTabStrip.getChildAt(position + 1) : null;
        int selectedWidth = selectedChild != null ? selectedChild.getWidth() : 0;
        int nextWidth = nextChild != null ? nextChild.getWidth() : 0;
        int left = selectedChild != null ? selectedChild.getLeft() : 0;
        int scrollX = -(left + (int)((selectedWidth + nextWidth) * positionOffset * 0.5f));
        if (tabLayout.getTabMode() == TabLayout.MODE_SCROLLABLE) {
            scrollX += tabLayout.getScrollX();
        }

        return scrollX;
    }

}
