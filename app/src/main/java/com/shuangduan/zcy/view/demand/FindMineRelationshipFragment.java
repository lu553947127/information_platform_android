package com.shuangduan.zcy.view.demand;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的需求，找关系
 * @time 2019/8/13 10:31
 * @change
 * @chang time
 * @class describe
 */
public class FindMineRelationshipFragment extends BaseLazyFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    public static FindMineRelationshipFragment newInstance() {
        Bundle args = new Bundle();
        FindMineRelationshipFragment fragment = new FindMineRelationshipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_project;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        Fragment[] fragments = new Fragment[]{
                DemandMineReleaseFragment.newInstance(),
                DemandMineAcceptFragment.newInstance()
        };
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        vp.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments, getResources().getStringArray(R.array.demand_relationship)));
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    protected void initDataFromService() {

    }
}
