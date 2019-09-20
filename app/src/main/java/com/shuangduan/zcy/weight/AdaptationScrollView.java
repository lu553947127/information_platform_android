package com.shuangduan.zcy.weight;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.weight
 * @ClassName: AdaptationScrollView
 * @Description: 重写滑动布局 适配api23以上滑动监听滑动的距离
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/20 19:55
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/20 19:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AdaptationScrollView extends NestedScrollView {

    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public AdaptationScrollView(Context context) {
        super(context);
    }

    public AdaptationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptationScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener{
        void onScroll(int scrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            listener.onScroll(t);
        }
    }
}
