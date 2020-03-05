package com.zcy.framelibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zcy.framelibrary.dialog.listener.OnClickListenerWrapper;

import java.lang.ref.WeakReference;

/**
 * @ProjectName: information_platform_android
 * @Package: com.zcy.framelibrary.dialog
 * @ClassName: DialogViewHelper
 * @Description: Dialog视图帮助类
 * @Author: 徐玉
 * @CreateDate: 2020/3/3 14:26
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public class DialogViewHelper {

    private View mContentView = null;

    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }


    public DialogViewHelper(Context context, int layoutResId) {
        this();
        this.mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }

    /**
     * 文字赋值
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);

        if (null != tv) {
            tv.setText(text);
        }
    }


    /**
     * 资源文件赋值
     *
     * @param viewId
     * @param resId
     */
    public void setText(int viewId, int resId) {
        TextView tv = getView(viewId);

        if (null != tv) {
            tv.setText(resId);
        }
    }

    public void setOnClickListener(AlertDialog dialog, int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (null != view) {
            //判断是否使用了自己监听包装类
            if (listener instanceof OnClickListenerWrapper) {
                View.OnClickListener listenerWrapper = ((OnClickListenerWrapper) listener).setDialog(dialog);
                view.setOnClickListener(listenerWrapper);
                return;
            }
            view.setOnClickListener(listener);
        }
    }


    public void setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        View view = getView(viewId);

        if (null != view) {
            if (view instanceof CompoundButton) {
                ((CompoundButton) view).setOnCheckedChangeListener(listener);
            }
        }
    }

    public <T extends View> T getView(int id) {
        WeakReference<View> viewReference = mViews.get(id);
        View view = null;

        if (null != viewReference) {
            view = viewReference.get();
        }

        if (null == view) {
            view = mContentView.findViewById(id);
            if (null != view) {
                mViews.put(id, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置Dialog布局
     *
     * @param view
     */
    public void setContentView(View view) {
        this.mContentView = view;
    }

    /**
     * 获取Dialog布局
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }


}
