package com.shuangduan.zcy.adminManage.view.device;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;

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
public class DeviceGroupFragment extends BaseLazyFragment {

    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_grounding)
    AppCompatTextView tvGrounding;
    @BindView(R.id.tv_use_statue)
    AppCompatTextView tvUseStatueTop;
    @BindView(R.id.tv_depositing_place)
    AppCompatTextView tvDepositingPlace;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("设备名称");

        if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ADD,0) ==1){
            ivAdd.setVisibility(View.VISIBLE);
        }else {
            ivAdd.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initDataFromService() {

    }
}
