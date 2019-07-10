package com.shuangduan.zcy.dialog;

import android.animation.Animator;
import android.app.Activity;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.shuangduan.zcy.R;

/**
 * <pre>
 *     author : nwq
 *     time   : 2019/07/06
 *     desc   : 单确定按钮
 *     version: 1.0
 * </pre>
 */
public class ConfirmDialog extends BaseDialog {
    public ConfirmDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_confirm;
    }

    @Override
    void initData() {
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);

        TextView tvConfirm = findViewById(R.id.tv_confirm);
        tvConfirm.setText("看着不错");
    }

    @Override
    void initEvent() {

    }

    @Override
    public void show() {
        super.show();
        DialogUtils.enterCustomAnim(getWindow().getDecorView().getRootView());
    }

    @Override
    public void cancel() {
        DialogUtils.exitCustomAnim(getWindow().getDecorView().getRootView())
                .addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isShowing()){
                            superCancel();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (isShowing()){
                            superCancel();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void superCancel(){
        super.cancel();
    }

}
