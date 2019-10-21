package com.shuangduan.zcy.listener;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2019/01/11
 *     desc   : qq分享监听
 *     version: 1.0
 * </pre>
 */

public class BaseUiListener implements IUiListener {

    @Override
    public void onComplete(Object o) {
        LogUtils.i(o);
        ToastUtils.showShort("分享成功");
    }

    @Override
    public void onError(UiError e) {
        LogUtils.i("onError:", "code:" + e.errorCode + ", msg:"
                + e.errorMessage + ", detail:" + e.errorDetail);
    }

    @Override
    public void onCancel() {
        LogUtils.i("onCancel", "");
        ToastUtils.showShort("取消分享");
    }

}
