package com.shuangduan.zcy.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.weight
 * @ClassName: AutoScrollRecyclerView
 * @Description: java类作用描述
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

    public void start() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
        }
        mAutoTask = Observable.interval(2000, 100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> smoothScrollBy(0, 20));
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
