package com.shuangduan.zcy.adminManage.view.order;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
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
 * @ClassName: OrderManagementFragment
 * @Description: 订单管理列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 14:40
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 14:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderManagementFragment extends BaseLazyFragment {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_turnover)
    AppCompatTextView tvTurnover;
    @BindView(R.id.tv_device)
    AppCompatTextView tvDevice;
    @BindView(R.id.ll_bar_title)
    LinearLayout llBarTitle;
    @BindView(R.id.toolbar_admin_manage)
    Toolbar toolbarAdminManage;
    @BindView(R.id.vp)
    NoScrollViewPager vp;

    public static OrderManagementFragment newInstance() {
        Bundle args = new Bundle();
        OrderManagementFragment fragment = new OrderManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_management;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbarAdminManage);
        Fragment[] fragments = {OrderTurnoverFragment.newInstance(),
                OrderDeviceFragment.newInstance()};
        vp.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));
    }

    @Override
    protected void initDataFromService() {
        getAdminEntrance(SPUtils.getInstance().getInt(CustomConfig.EQIPMENT_ORDER_LIST,0)
                ,SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_LIST,0));
    }

    //后台管理权限判断
    private void getAdminEntrance(int equipment_order,int construction_order) {
        if (equipment_order==1&&construction_order==1){
            llBarTitle.setVisibility(View.VISIBLE);
            tvBarTitle.setVisibility(View.GONE);
            vp.setCurrentItem(0);
        }else if (equipment_order==1&&construction_order==0){
            llBarTitle.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.VISIBLE);
            tvBarTitle.setText(getString(R.string.device_management));
            vp.setCurrentItem(1);
        }else if (equipment_order==0&&construction_order==1){
            llBarTitle.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.VISIBLE);
            tvBarTitle.setText(getString(R.string.turnover_material));
            vp.setCurrentItem(0);
        }
    }

    @OnClick({R.id.iv_bar_back,R.id.tv_turnover,R.id.tv_device})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                Objects.requireNonNull(getActivity()).finish();
                break;
            case R.id.tv_turnover:
                tvTurnover.setTextSize(18);
                tvDevice.setTextSize(14);
                vp.setCurrentItem(0);
                break;
            case R.id.tv_device:
                tvTurnover.setTextSize(14);
                tvDevice.setTextSize(18);
                vp.setCurrentItem(1);
                break;
        }
    }
}
