package com.shuangduan.zcy.view.adminManage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: DeviceGroupFragment
 * @Description: 设备管理集团列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/24 17:11
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/24 17:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceGroupFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView tvName;

    public static DeviceGroupFragment newInstance() {
        Bundle args = new Bundle();
        DeviceGroupFragment fragment = new DeviceGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_device_group;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View view) {
        tvName.setText("设备名称");
    }

    @Override
    protected void initDataFromService() {

    }
}
