package com.shuangduan.zicaicloudplatform.callback;

import com.shuangduan.zicaicloudplatform.R;
import com.kingja.loadsir.callback.Callback;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class LoadingCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }
}
