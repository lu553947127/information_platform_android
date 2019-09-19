package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.shuangduan.zcy.R;

/**
 * <pre>
 *     author : nwq
 *     time   : 2019/07/06
 *     desc   : 相机相册dialog
 *     version: 1.0
 * </pre>
 */
public class PhotoDialog extends BaseDialog {

    public PhotoDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_photo;
    }

    @Override
    void initData() {
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setGravity(Gravity.BOTTOM);
        DialogUtils.enterBottomAnim(this);
    }

    @Override
    void initEvent() {
        findViewById(R.id.tv_photo).setOnClickListener(v -> {
            photoCallBack.camera();
            hideDialog();
        });
        findViewById(R.id.tv_select_pic).setOnClickListener(v -> {
            photoCallBack.album();
            hideDialog();
        });
        findViewById(R.id.tv_cancel).setOnClickListener(v -> hideDialog());
    }
}
