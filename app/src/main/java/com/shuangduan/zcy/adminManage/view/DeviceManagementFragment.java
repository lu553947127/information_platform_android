package com.shuangduan.zcy.adminManage.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.weight.NoScrollViewPager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: DeviceManagementFragment
 * @Description: 设备管理列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 14:38
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 14:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceManagementFragment extends BaseLazyFragment {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    NoScrollViewPager vp;

    public static DeviceManagementFragment newInstance() {
        Bundle args = new Bundle();
        DeviceManagementFragment fragment = new DeviceManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_device_management;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.device_management));
        Fragment[] fragments = {DeviceGroupFragment.newInstance(),
                DeviceChildrenFragment.newInstance()};
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        vp.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments, getResources().getStringArray(R.array.turnover_material)));
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    protected void initDataFromService() {

    }

    //后台管理权限判断
    private void getAdminEntrance(int son) {
        if (son==0){
            tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAdminEntrance(SPUtils.getInstance().getInt(CustomConfig.SON_LIST,0));
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        Objects.requireNonNull(getActivity()).finish();
    }
}
