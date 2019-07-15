package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.maps.MapView;
import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  工程信息详情
 * @time 2019/7/12 16:26
 * @change
 * @chang time
 * @class describe
 */
public class ProjectDetailActivity extends BaseActivity {
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    TextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.iv_subscribe)
    ImageView ivSubscribe;
    @BindView(R.id.tv_subscribe)
    TextView tvSubscribe;

    private Fragment[] fragments;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_detail;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.message_detail));
        ivBarRight.setImageResource(R.drawable.icon_share);
        tvBarRight.setVisibility(View.GONE);

        tvTitle.setText("山东省济南市莱芜区影城电子信息产业园一期");
        tvLocation.setText("山东省济南市莱芜区");

        fragments = new Fragment[4];
        fragments[0] = ProjectContentFragment.newInstance();
        fragments[1] = ProjectLocusFragment.newInstance();
        fragments[2] = ProjectReadFragment.newInstance();
        fragments[3] = ProjectConsumptionFragment.newInstance();

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.project_detail));
        vp.setAdapter(adapter);
        tabLayout.setupWithViewPager(vp);
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.fl_collect, R.id.fl_error, R.id.fl_subscription})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                break;
            case R.id.fl_collect:
                break;
            case R.id.fl_error:
                break;
            case R.id.fl_subscription:
                break;
        }
    }
}
