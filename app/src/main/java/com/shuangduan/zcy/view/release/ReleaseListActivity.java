package com.shuangduan.zcy.view.release;

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
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  发布列表
 * @time 2019/7/16 20:03
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseListActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_release_list;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.my_project));

        Fragment[] fragments = {ProjectInfoFragment.newInstance(),
                LocusFragment.newInstance()};
        String[] titles = getResources().getStringArray(R.array.release_list);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(vp);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_release})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_release:
                Bundle bundle = new Bundle();
                if (vp.getCurrentItem()==0){
                    bundle.putInt(CustomConfig.RELEASE_TYPE, 0);
                    ActivityUtils.startActivity(bundle,ReleaseProjectActivity.class);
                }else if (vp.getCurrentItem()==1){
                    bundle.putInt(CustomConfig.RELEASE_TYPE, 1);
                    ActivityUtils.startActivity(bundle,ReleaseProjectActivity.class);
                }
                break;
        }
    }
}
