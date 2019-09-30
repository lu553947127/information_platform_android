package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.AuthenticationUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  需求广场列表
 * @time 2019/8/16 17:13
 * @change
 * @chang time
 * @class describe
 */
public class DemandActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_demand;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.release_demand));
        tvBarRight.setText(getString(R.string.release));

        Fragment[] fragments = new Fragment[]{
                FindRelationshipFragment.newInstance(),
                FindSubstanceFragment.newInstance(),
                FindBuyerFragment.newInstance()
        };
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.demand)));
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setupWithViewPager(vp);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                if (!AuthenticationUtils.Authentication()) return;
                ActivityUtils.startActivity(DemandReleaseActivity.class);
                break;
        }
    }
}
