package com.shuangduan.zcy.dialog;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.dialog
 * @class describe
 * @time 2019/7/10 16:54
 * @change
 * @chang time
 * @class describe
 */
public class LoadDialog extends BaseDialog{
    public LoadDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_load;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(100));
        setHeight(ConvertUtils.dp2px(100));
        setCancelOutside(false);
        DialogUtils.enterCustomAnim(this);
    }

    @Override
    void initEvent() {

    }
}
