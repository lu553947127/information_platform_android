package com.shuangduan.zcy.adminManage.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.weight.NoScrollViewPager;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: OrderTurnoverFragment
 * @Description: 周转材料订单管理列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 13:30
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 13:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderTurnoverFragment extends BaseLazyFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    NoScrollViewPager vp;

    public static OrderTurnoverFragment newInstance() {
        Bundle args = new Bundle();
        OrderTurnoverFragment fragment = new OrderTurnoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_turnover;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        Fragment[] fragments = {OrderTurnoverGroupFragment.newInstance(),
                OrderTurnoverChildrenFragment.newInstance()};
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        vp.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments, getResources().getStringArray(R.array.turnover_material)));
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    protected void initDataFromService() {

    }
}
