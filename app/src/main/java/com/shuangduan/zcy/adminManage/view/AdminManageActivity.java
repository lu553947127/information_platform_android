package com.shuangduan.zcy.adminManage.view;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.view.device.DeviceManagementFragment;
import com.shuangduan.zcy.adminManage.view.order.OrderManagementFragment;
import com.shuangduan.zcy.adminManage.view.turnover.TurnoverMaterialFragment;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    List<Fragment> mFragments;
    private int lastFragment;//用于记录上个选择的Fragment

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
        initFragment();
    }

    //初始化fragment和fragment数组
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(TurnoverMaterialFragment.newInstance());
        mFragments.add(DeviceManagementFragment.newInstance());
        mFragments.add(OrderManagementFragment.newInstance());
        lastFragment=0;
        //判断加载哪个页
        if (getIntent().getIntExtra(CustomConfig.IS_ADMIN_MANAGE,0)==1){
            switchFragment(lastFragment,0);lastFragment=0;
            navigation.getMenu().getItem(0).setChecked(true);
        }else if (getIntent().getIntExtra(CustomConfig.IS_ADMIN_MANAGE,0)==2){
            switchFragment(lastFragment,1);lastFragment=1;
            navigation.getMenu().getItem(1).setChecked(true);
        }else {
            switchFragment(lastFragment,2);lastFragment=2;
            navigation.getMenu().getItem(2).setChecked(true);
        }
        //判断选择的菜单
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_turnover_material://周转材料
                    if(lastFragment!=0)switchFragment(lastFragment,0);lastFragment=0;
                    break;
                case R.id.menu_device_management://设备管理
                    if(lastFragment!=1)switchFragment(lastFragment,1);lastFragment=1;
                    break;
                case R.id.menu_order_management://订单管理
                    if(lastFragment!=2)switchFragment(lastFragment,2);lastFragment=2;
                    break;
                default:
                    break;
            }
            // 这里注意返回true,否则点击失效
            return true;
        });
    }

    //切换Fragment
    private void switchFragment(int lastFragment,int index) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments.get(lastFragment));//隐藏上个Fragment
        if(!mFragments.get(index).isAdded()) {
            transaction.add(R.id.fragment,mFragments.get(index));
        }
        transaction.show(mFragments.get(index)).commitAllowingStateLoss();
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

            //判断底部tab栏只有一个功能模块时隐藏
            if (construction==1&&equipment==0&&equipment_order==0&&construction_order==0){
                navigation.setVisibility(View.GONE);
            }else if (construction==0&&equipment==1&&equipment_order==0&&construction_order==0){
                navigation.setVisibility(View.GONE);
            }else if (construction==0&&equipment==0&&equipment_order==1&&construction_order==0){
                navigation.setVisibility(View.GONE);
            }else if (construction==0&&equipment==0&&equipment_order==0&&construction_order==1){
                navigation.setVisibility(View.GONE);
            }else if (construction==0&&equipment==0&&equipment_order==1&&construction_order==1){
                navigation.setVisibility(View.GONE);
            }else {
                navigation.setVisibility(View.VISIBLE);
            }
        }
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
