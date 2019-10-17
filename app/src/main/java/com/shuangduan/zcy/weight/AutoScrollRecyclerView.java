package com.shuangduan.zcy.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.weight
 * @ClassName: AutoScrollRecyclerView
 * @Description: 带跑马灯滚动的列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/14 14:34
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/14 14:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AutoScrollRecyclerView extends RecyclerView {

    private Disposable mAutoTask;

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //流式滑动
    public void start() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
        }
        mAutoTask = Observable.interval(1000, 100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> smoothScrollBy(0, 20));
    }

    //停滞item滑动
    public void startLine() {
        if (mAutoTask!= null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
        }
        LinearSmoothScroller mScroller = new LinearSmoothScroller(getContext()){
            //将移动的置顶显示
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
            //控制速度，这里注意当速度过慢的时候可能会形成流式的效果，因为这里是代表移动像素的速度，
            // 当定时器中每隔的2秒之内正好或者还未移动一个item的高度的时候会出现，前一个还没移动完成又继续移动下一个了，就形成了流滚动的效果了
            // 这个问题后续可通过重写另外一个方法来进行控制，暂时就先这样了
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 3f / displayMetrics.density;
            }
        };
        mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            //滚动到指定item
            mScroller.setTargetPosition(aLong.intValue());
            Objects.requireNonNull(getLayoutManager()).startSmoothScroll(mScroller);
        });
    }

    public void stop() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
            mAutoTask = null;
        }
    }

//    //禁止手动滑动
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_UP:
//                return super.dispatchTouchEvent(ev);
//        }
//        return false;
//    }
}
