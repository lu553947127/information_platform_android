package com.shuangduan.zcy.callback;

import com.shuangduan.zcy.R;
import com.kingja.loadsir.callback.Callback;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/19
 *     desc   : 请求超时界面
 *     version: 1.0
 * </pre>
 */

public class TimeOutCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_time_out;
    }
}
