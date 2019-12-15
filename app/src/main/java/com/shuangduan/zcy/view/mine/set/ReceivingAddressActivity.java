package com.shuangduan.zcy.view.mine.set;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: ReceivingAddressActivity
 * @Description: 收货地址页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/15 15:14
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/15 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ReceivingAddressActivity extends BaseActivity {
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_receiving_address;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }
}
