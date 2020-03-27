package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.DEMAND_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_BUYER_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_RELATIONSHIP_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_SUBSTANCE_TYPE;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class 需求资讯查看更多
 * @time 2019/8/16 17:13
 * @change
 * @chang time
 * @class describe
 */
public class DemandActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private int type;

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
        type = getIntent().getIntExtra(DEMAND_TYPE, FIND_RELATIONSHIP_TYPE);
//        Fragment[] fragments = new Fragment[]{
//                FindRelationshipFragment.newInstance(),
//                FindSubstanceFragment.newInstance(),
//                FindBuyerFragment.newInstance()
//        };
//
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        // 加载当前显示的Fragment
//        transaction.replace(R.id.fl_content, fragments[type]);
//        transaction.commit(); // 提交创建Fragment请求

        switch (type){
            case 0:
                tvBarTitle.setText(getString(R.string.find_relationship));
                break;
            case 1:
                tvBarTitle.setText(getString(R.string.find_substance));
                break;
            case 2:
                tvBarTitle.setText(getString(R.string.find_buyer));
                break;
        }

        Fragment[] fragments = {FindRelationshipFragment.newInstance(),
                FindSubstanceFragment.newInstance(),
                FindBuyerFragment.newInstance()};
        String[] titles = getResources().getStringArray(R.array.demand);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(vp);
        vp.setCurrentItem(type);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tvBarTitle.setText(getString(R.string.find_relationship));
                        break;
                    case 1:
                        tvBarTitle.setText(getString(R.string.find_substance));
                        break;
                    case 2:
                        tvBarTitle.setText(getString(R.string.find_buyer));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_release})
    void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_release:
                if (vp.getCurrentItem() == FIND_RELATIONSHIP_TYPE) {
                    bundle.putString("type", "0");
                    ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                } else if (vp.getCurrentItem() == FIND_SUBSTANCE_TYPE) {
                    bundle.putString("type", "1");
                    ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                } else if (vp.getCurrentItem() == FIND_BUYER_TYPE) {
                    bundle.putString("type", "2");
                    ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                }
                break;
        }
    }
}
