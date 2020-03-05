package com.zcy.framelibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;

import com.zcy.framelibrary.R;
import com.zcy.framelibrary.dialog.listener.OnClickListenerWrapper;

/**
 * @ProjectName: information_platform_android
 * @Package: com.zcy.framelibrary.dialog
 * @ClassName: AlterDialog
 * @Description: 自定义全局可配置的Dialog
 * @Author: 徐玉
 * @CreateDate: 2020/3/3 13:44
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public class AlertDialog extends AppCompatDialog {

    /**
     * 使用方法
     * <p>
     * AlertDialog dialog = new AlertDialog.Builder(mContext)
     * .setView(R.layout.dialog_area) //Dialog View
     * .setCancelable(false) //点击空白是否取消
     * .setText(R.id.tv_tip, "测试")//控件的文本
     * .setText(R.id.xx, "测试")//控件的文本
     * .setText(R.id.xx, "测试")//控件的文本
     * .setOnClickListener(R.id.tv_negative, view -> {
     * <p>
     * }) //控件的点击事件
     * .setOnClickListener(R.id.tv_negative, view -> {
     * <p>
     * }) //控件的点击事件
     * .setOnClickListener(R.id.tv_negative, view -> {
     * <p>
     * }) //控件的点击事件
     * .setOnKeyListener()//键盘监听事件
     * .setGravity(Gravity.CENTER)//弹窗显示位置 默认居中
     * .setWidthAndHeigh(100, 100)//设置dialog宽高 ： 可不设置
     * .fullWidth()//全屏
     * .setAnimation(R.style.admin)//设置动画
     * .addDefaultAnimation()//默认动画
     * .show();
     */


    private AlertController mAlert;


    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }


    /**
     * 适配Android Dialog的样式
     *
     * @param context
     * @param resId
     * @return
     */
    static int resolveDialogTheme(@NonNull Context context, @StyleRes int resId) {
        // Check to see if this resourceId has a valid package ID.
        if (((resId >>> 24) & 0x000000ff) >= 0x00000001) {   // start of real resource IDs.
            return resId;
        } else {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.alertDialogTheme, outValue, true);
            return outValue.resourceId;
        }
    }

    /**
     * 设置控件文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId, text);
    }

    public void setText(int viewId, int resId) {
        mAlert.setText(viewId, resId);
    }


    /**
     * 设置控件的监听事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, OnClickListenerWrapper listener) {
        mAlert.setOnClickListener(viewId, listener);
    }




    /**
     * 选择框的点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        mAlert.setOnCheckedChangeListener(viewId, listener);
    }


    /**
     * 获取Dialog 控件
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {
        return mAlert.getView(id);
    }

    /**
     * Dialog 构造器 规范弹窗的创建
     * <p>
     */
    public static class Builder {

        private final AlertController.AlertParams P;


        /**
         * Creates a builder for an alert dialog that uses the default alert
         * dialog theme.
         * <p>
         * The default alert dialog theme is defined by
         * {@link android.R.attr#alertDialogTheme} within the parent
         * {@code context}'s theme.
         *
         * @param context the parent context
         */
        public Builder(Context context) {
            this(context, resolveDialogTheme(context, 0));
        }

        /**
         * Creates a builder for an alert dialog that uses an explicit theme
         * resource.
         *
         * @param context    the parent context
         * @param themeResId the resource ID of the theme against which to inflate
         *                   this dialog, or {@code 0} to use the parent
         *                   {@code context}'s default alert dialog theme
         */
        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }

        /**
         * Creates an {@link android.app.AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public AlertDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final AlertDialog dialog = new AlertDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }


        /**
         * Set a custom view resource to be the contents of the Dialog. The
         * resource will be inflated, adding all top-level views to the screen.
         *
         * @param layoutResId Resource ID to be inflated.
         * @return this Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * Sets a custom view to be the contents of the alert dialog.
         * <p>
         * When using a pre-Holo theme, if the supplied view is an instance of
         * a {@link View} then the light background will be used.
         * <p>
         * <strong>Note:</strong> To ensure consistent styling, the custom view
         * should be inflated or constructed using the alert dialog's themed
         * context obtained via {@link #getContext()}.
         *
         * @param view the view to use as the contents of the alert dialog
         * @return this Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(android.content.DialogInterface.OnDismissListener) setOnDismissListener}.</p>
         *
         * @return This Builder object to allow for chaining of calls to set methods
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(android.content.DialogInterface.OnDismissListener)
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置View显示文本
         *
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }


        public Builder setText(int viewId, int resId) {
            P.mTextResIdArray.put(viewId, resId);
            return this;
        }

        /**
         * 设置View点击事件
         *
         * @param viewId
         * @param listener
         * @return
         */
        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            if(null == listener){
                listener = new OnClickListenerWrapper() {
                    @Override
                    public void onClickCall(View v) {
                    }
                };
            }
            P.mClickArray.put(viewId, listener);
            return this;
        }


        /**
         * 设置CheckBox的选择事件
         *
         * @param viewId
         * @param listener
         * @return
         */
        public Builder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
            P.mCheckedClickArray.put(viewId, listener);
            return this;
        }


        /**
         * 设置Dialog全屏
         *
         * @return
         */
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出 没有动画
         *
         * @return
         */
        public Builder formBottom() {
            formBottom(false);
            return this;
        }

        /**
         * 从底部弹出 有动画
         *
         * @param isAnimation true 有动画
         * @return
         */
        public Builder formBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置弹窗显示位置
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            P.mGravity = gravity;
            return this;
        }

        /**
         * 设置Dialog的宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 设置默认动画
         *
         * @return
         */
        public Builder addDefaultAnimation() {
            P.mAnimation = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置弹出动画
         *
         * @param animation
         * @return
         */
        public Builder setAnimation(int animation) {
            P.mAnimation = animation;
            return this;
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
