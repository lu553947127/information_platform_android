package com.shuangduan.zicaicloudplatform.dialog;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.shuangduan.zicaicloudplatform.R;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.shuangduan.zicaicloudplatform.dialog
 * @class describe  订阅类型选择
 * @time 2019/7/9 8:35
 * @change
 * @chang time
 * @class describe
 */
public class SubscriptionTypeDialog extends BaseDialog {
    public SubscriptionTypeDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_subscription_type;
    }

    @Override
    void initData() {

    }

    @Override
    void initEvent() {

    }
}
