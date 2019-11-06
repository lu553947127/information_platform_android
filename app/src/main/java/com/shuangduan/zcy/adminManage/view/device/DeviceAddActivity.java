package com.shuangduan.zcy.adminManage.view.device;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view.device
 * @ClassName: DeviceAddActivity
 * @Description: 设备管理添加
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 16:15
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 16:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceAddActivity extends BaseActivity {
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_device_add;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }
}
