package com.zcy.framelibrary.dialog.listener;

import android.view.View;

import androidx.appcompat.app.AppCompatDialog;

import com.zcy.framelibrary.dialog.AlertDialog;

/**
 * @ProjectName: information_platform_android
 * @Package: com.zcy.framelibrary.dialog.listener
 * @ClassName: OnClickListenerWrapper
 * @Description: java类作用描述
 * @Author: 徐玉
 * @CreateDate: 2020/3/5 14:44
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public abstract class OnClickListenerWrapper implements View.OnClickListener {


    private AppCompatDialog mDialog;

    public abstract void onClickCall(View v);


    @Override
    public void onClick(View v) {
        onClickCall(v);
        mDialog.dismiss();
    }

    public View.OnClickListener setDialog(AlertDialog dialog) {
        this.mDialog = dialog;
        return this;
    }
}
