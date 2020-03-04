package com.zcy.baselibrary.navigationBar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcy.baselibrary.utils.StringUtils;

/**
 * @ProjectName: information_platform_android
 * @Package: com.zcy.baselibrary.navigationBar
 * @ClassName: AbsNavigationBar
 * @Description: java类作用描述
 * @Author: 徐玉
 * @CreateDate: 2020/3/4 11:45
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {
    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }


    public void setText(int viewId, String text) {
        TextView tv = mNavigationView.findViewById(viewId);
        if (null != tv && !StringUtils.isTrimEmpty(text)) {
            setVisibility(tv);

            tv.setText(text);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = mNavigationView.findViewById(viewId);
        if (null != view) {
            view.setOnClickListener(listener);
        }
    }


    public void setIcon(int viewId, int iconRes) {
        ImageView view = findViewById(viewId);
        if (null != view) {
            setVisibility(view);
            view.setImageResource(iconRes);
        }
    }


    public void setIcon(int viewId, Drawable drawable) {
        ImageView view = findViewById(viewId);
        if (null != null) {
            view.setImageDrawable(drawable);
        }
    }


    public <T extends View> T findViewById(int viewId) {
        return mNavigationView.findViewById(viewId);
    }

    /**
     * 创建和绑定View
     */
    private void createAndBindView() {
        if (null == mParams.mParent) {
            //获取Activity的根布局
            ViewGroup activityRoot = (ViewGroup) ((Activity) mParams.mContext).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }

        if (null == mParams.mParent) {
            return;
        }

        //创建View
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);

        //添加头布局
        mParams.mParent.addView(mNavigationView, 0);

        applyView();
    }


    private void setVisibility(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 构建者
     */
    public abstract static class Builder {

        public Builder(Context context, ViewGroup parent) {

        }

        public abstract AbsNavigationBar builder();

        /**
         * 导航栏中需要的所有参数信息
         */
        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }
    }


}

