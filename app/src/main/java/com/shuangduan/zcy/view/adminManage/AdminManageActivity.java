package com.shuangduan.zcy.view.adminManage;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.FragmentAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.weight.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: AdminManageActivity
 * @Description: 后台管理页
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 13:41
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 13:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AdminManageActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    List<Fragment> mFragments;
    FragmentAdapter fragmentAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_admin_manage;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        initBottomNavigation();
    }

    //底部标签栏点击切换
    public void initBottomNavigation() {
        //fragment布局初始化
        mFragments = new ArrayList<>();
        mFragments.add(TurnoverMaterialFragment.newInstance());
        mFragments.add(DeviceManagementFragment.newInstance());
        mFragments.add(OrderManagementFragment.newInstance());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this, mFragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2);
        //判断加载哪个页
        if (getIntent().getIntExtra(CustomConfig.IS_ADMIN_MANAGE,0)==1){
            viewPager.setCurrentItem(0);
            tvBarTitle.setText(getString(R.string.turnover_material));
            navigation.getMenu().getItem(0).setChecked(true);
        }else if (getIntent().getIntExtra(CustomConfig.IS_ADMIN_MANAGE,0)==2){
            viewPager.setCurrentItem(1);
            tvBarTitle.setText(getString(R.string.device_management));
            navigation.getMenu().getItem(1).setChecked(true);
        }else {
            viewPager.setCurrentItem(2);
            tvBarTitle.setText(getString(R.string.order_management));
            navigation.getMenu().getItem(2).setChecked(true);
        }
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_turnover_material:
                    viewPager.setCurrentItem(0);
                    tvBarTitle.setText(getString(R.string.turnover_material));
                    break;
                case R.id.menu_device_management:
                    viewPager.setCurrentItem(1);
                    tvBarTitle.setText(getString(R.string.device_management));
                    break;
                case R.id.menu_order_management:
                    viewPager.setCurrentItem(2);
                    tvBarTitle.setText(getString(R.string.order_management));
                    break;
                default:
                    break;
            }
            // 这里注意返回true,否则点击失效
            return true;
        });
        //viewpager+fragment联合navigation使用多页面间切换监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //注意这个方法滑动时会调用多次，下面是参数解释：
                //position当前所处页面索引,滑动调用的最后一次绝对是滑动停止所在页面
                //positionOffset:表示从位置的页面偏移的[0,1]的值。
                //positionOffsetPixels:以像素为单位的值，表示与位置的偏移
            }

            @Override
            public void onPageSelected(int position) {
                //该方法只在滑动停止时调用，position滑动停止所在页面位置
                //当滑动到某一位置，导航栏对应位置被按下
                navigation.getMenu().getItem(position).setChecked(true);
                //这里使用navigation.setSelectedItemId(position);无效，
                //setSelectedItemId(position)的官网原句：Set the selected
                //menu item ID. This behaves the same as tapping on an item
                //未找到原因
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //这个方法在滑动是调用三次，分别对应下面三种状态
                //这个方法对于发现用户何时开始拖动，
                //何时寻呼机自动调整到当前页面，或何时完全停止/空闲非常有用。
                //state表示新的滑动状态，有三个值：
                //SCROLL_STATE_IDLE：开始滑动（空闲状态->滑动），实际值为0
                //SCROLL_STATE_DRAGGING：正在被拖动，实际值为1
                //SCROLL_STATE_SETTLING：拖动结束,实际值为2
            }
        });
    }

    //后台管理权限判断
    private void getAdminEntrance(int construction,int equipment,int equipment_order,int construction_order) {
        if (construction==0&&equipment==0&&equipment_order==0&&construction_order==0){
            navigation.setVisibility(View.GONE);
        }else {
            if (construction==1){
                navigation.getMenu().findItem(R.id.menu_turnover_material).setVisible(true);
            }else {
                navigation.getMenu().findItem(R.id.menu_turnover_material).setVisible(false);
            }
            if (equipment==1){
                navigation.getMenu().findItem(R.id.menu_device_management).setVisible(true);
            }else {
                navigation.getMenu().findItem(R.id.menu_device_management).setVisible(false);
            }
            if (equipment_order==1||construction_order==1){
                navigation.getMenu().findItem(R.id.menu_order_management).setVisible(true);
            }else {
                navigation.getMenu().findItem(R.id.menu_order_management).setVisible(false);
            }
            if (construction+equipment+equipment_order+construction_order<2){
                navigation.setVisibility(View.GONE);
            }else {
                navigation.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAdminEntrance(SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_LIST,0)
                ,SPUtils.getInstance().getInt(CustomConfig.EQIPMENT_LIST,0)
                ,SPUtils.getInstance().getInt(CustomConfig.EQIPMENT_ORDER_LIST,0)
                ,SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_LIST,0));
    }
}
