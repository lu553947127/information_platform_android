package com.shuangduan.zcy.adminManage.view;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view
 * @ClassName: OrderDeviceFragment
 * @Description: 设备管理订单管理列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 14:11
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 14:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderDeviceFragment extends BaseLazyFragment {

    public static OrderDeviceFragment newInstance() {
        Bundle args = new Bundle();
        OrderDeviceFragment fragment = new OrderDeviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_device;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected void initDataFromService() {

    }
}
