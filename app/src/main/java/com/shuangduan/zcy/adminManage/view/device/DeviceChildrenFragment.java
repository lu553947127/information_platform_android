package com.shuangduan.zcy.adminManage.view.device;

import android.os.Bundle;
import android.widget.TextView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: DeviceChildrenFragment
 * @Description: 设备管理子公司列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/24 17:12
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/24 17:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceChildrenFragment extends BaseLazyFragment {

    @BindView(R.id.tv_name)
    TextView tvName;

    public static DeviceChildrenFragment newInstance() {
        Bundle args = new Bundle();
        DeviceChildrenFragment fragment = new DeviceChildrenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_device_children;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("设备名称");
    }

    @Override
    protected void initDataFromService() {

    }
}
