package com.shuangduan.zcy.view.mine;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的收藏
 * @time 2019/8/5 14:32
 * @change
 * @chang time
 * @class describe
 */
public class MineCollectionActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_tab_vp;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.my_collection));

        Fragment[] fragments = new Fragment[]{
                ProjectCollectFragment.newInstance(),
                RecruitCollectFragment.newInstance(),
                MaterialCollectFragment.newInstance(),
                MaterialCollectFragment.newInstance()
        };
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.mine_collect)));
        tabLayout.setupWithViewPager(vp);
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }
}
