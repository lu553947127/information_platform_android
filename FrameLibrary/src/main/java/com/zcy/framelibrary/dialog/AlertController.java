package com.zcy.framelibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @ProjectName: information_platform_android
 * @Package: com.zcy.framelibrary.dialog
 * @ClassName: AlterController
 * @Description: Dialog的具体构建器
 * @Author: 徐玉
 * @CreateDate: 2020/3/3 14:21
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public class AlertController {

    private AlertDialog mDialog;

    private Window mWindow;

    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }


    /**
     * 获取Dialog
     *
     * @return
     */
    public AlertDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取Window
     *
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }


    public void setViewHelper(DialogViewHelper helper) {
        this.mViewHelper = helper;
    }


    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }


    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId, listener);
    }

    public <T extends View> T getView(int id) {
        return mViewHelper.getView(id);
    }


    /**
     * 存放Dialog中所有的参数信息 并设置部分公用参数功能
     */
    public static class AlertParams {

        public Context mContext;

        public int mThemeResId;

        //点击空白是否能够取消
        public boolean mCancelable = true;

        //dialog cancel and dismiss  监听
        public DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        };
        public DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        };
        //dialog 所有的点击按钮监听
        public DialogInterface.OnKeyListener mOnKeyListener;

        //dialog 布局View
        public View mView;
        //dialog 布局Id
        public int mViewLayoutResId;

        //存放View所有文本
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //存放View所有点击事件
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        //宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //动画
        public int mAnimation = 0;
        //弹窗位置
        public int mGravity = Gravity.CENTER;


        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }


        /**
         * 绑定和设置参数
         *
         * @param alert
         */
        public void apply(AlertController alert) {

            //布局
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            //非空判断 提示设置布局
            if (null == viewHelper) {
                throw new IllegalArgumentException("请设置布局 setView()");
            }

            //设置Controller
            alert.setViewHelper(viewHelper);

            alert.getDialog().setContentView(viewHelper.getContentView());

            //设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }
            //设置点击
            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                viewHelper.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //配置自定义效果
            Window window = alert.getWindow();
            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);

        }
    }


}
