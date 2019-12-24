package com.shuangduan.zcy.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.FragmentAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.SupplierRoleBean;
import com.shuangduan.zcy.rongyun.fragment.CircleFragment;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.vm.IMConnectVm;
import com.shuangduan.zcy.weight.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.Conversation;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: MainActivity
 * @Description: 首页
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/24 17:50
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/24 17:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    List<Fragment> mFragments;
    FragmentAdapter fragmentAdapter;
    private HomeVm homeVm;
    private IMAddVm imAddVm;
    private IUnReadMessageObserver observer;
    private int manage_status,order_turnover,order_device;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (getSwipeBackLayout() != null) {
            getSwipeBackLayout().setEnableGesture(false);
        }
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        //初始化，融云链接服务器
        if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) == 1) {
            IMConnectVm imConnectVm = ViewModelProviders.of(this).get(IMConnectVm.class);
            imConnectVm.connect(SPUtils.getInstance().getString(SpConfig.IM_TOKEN));
        }

        setJPushAlias();
        initBottomNavigation();
        getBadgeViewInitView();
    }

    //底部标签栏点击切换
    public void initBottomNavigation() {
        //fragment布局初始化
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(PeopleFragment.newInstance());
        mFragments.add(CircleFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this, mFragments);
        viewPager.setAdapter(fragmentAdapter);
        //设置默认首页为第一个fragment
        viewPager.setCurrentItem(0);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.menu_people:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.menu_circle:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.menu_mime:
                    viewPager.setCurrentItem(3);
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

    //设置底部消息提醒数字布局
    private RelativeLayout relativeLayout;
    private TextView number;
    private void getBadgeViewInitView() {
        //底部标题栏右上角标设置
        //获取整个的NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，设置在标题栏的位置
        View tab = menuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.layout_apply_count, menuView, false);
        //添加到Tab上
        itemView.addView(badge);
        //显示角标数字
        relativeLayout = badge.findViewById(R.id.rl);
        //显示/隐藏整个视图
        number = badge.findViewById(R.id.number);

        getApplyCounts();
    }

    //设置角标数量
    private void getApplyCounts() {

        homeVm = ViewModelProviders.of(this).get(HomeVm.class);
        imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);

        //后台管理权限
        homeVm.supplierRoleLiveData.observe(this, supplierRoleBean -> {
            //获取用户身份 0普通用户 1普通供应商 2子公司 3集团 4子账号
            manage_status = supplierRoleBean.getManage_status();
            //获取订单权限
            for (SupplierRoleBean.RoleBean bean : supplierRoleBean.getRole()) {
                switch (bean.getMenu()){
                    case CustomConfig.EQIPMENT_ORDER_LIST://设备订单列表
                        order_device = bean.getStatus();
                        break;
                    case CustomConfig.CONSTRUCTION_ORDER_LIST://周转材料订单列表
                        order_turnover = bean.getStatus();
                        break;
                }
            }
        });

        //获取角标数量接口
        imAddVm.applyCountData.observe(this, friendApplyCountBean -> {
            //获取用户身份 0普通用户 1普通供应商 2子公司 3集团 4子账号
            int counts = 0;
            switch (manage_status){
                case 0://普通用户
                    counts = imAddVm.count+friendApplyCountBean.getCount()+friendApplyCountBean.getSubscribe();
                    break;
                case 1://普通供应商
                    counts = imAddVm.count+friendApplyCountBean.getCount()+friendApplyCountBean.getSubscribe()+friendApplyCountBean.getOrder();
                    break;
                case 2://子公司
                    counts = imAddVm.count+friendApplyCountBean.getCount()+friendApplyCountBean.getSubscribe()+friendApplyCountBean.getMaterial()+friendApplyCountBean.getOrder();
                    break;
                case 4://子公司子账号
                    if (order_device == 1 || order_turnover == 1){
                        counts = imAddVm.count+friendApplyCountBean.getCount()+friendApplyCountBean.getSubscribe()+friendApplyCountBean.getMaterial()+friendApplyCountBean.getOrder();
                    }else {
                        counts = imAddVm.count+friendApplyCountBean.getCount()+friendApplyCountBean.getSubscribe()+friendApplyCountBean.getMaterial();
                    }
                    break;
                case 3://集团
                case 5://集团子账号
                    counts = imAddVm.count+friendApplyCountBean.getCount()+friendApplyCountBean.getSubscribe()+friendApplyCountBean.getMaterial();
                    break;
            }

            //设置底部角标显示状态
            if (counts < 1) {
                relativeLayout.setVisibility(View.GONE);
            } else if (counts < 100) {
                relativeLayout.setVisibility(View.VISIBLE);
                number.setTextSize(11);
                number.setText(String.valueOf(counts));
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
                number.setTextSize(9);
                number.setText("99+");
            }
        });
    }

    //极光推送别名设置
    private void setJPushAlias() {
        int userId = SPUtils.getInstance().getInt(SpConfig.USER_ID, 0);
        int aliasStatus = SPUtils.getInstance().getInt(SpConfig.ALIAS_STATUS, 0);
        if (userId != 0 && aliasStatus != 1) {
            LogUtils.i("别名设置", userId);
            JPushInterface.setAlias(this, userId, String.valueOf(userId));
        }
    }

    //推出群聊后重新跳转到工程圈页
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String statueCar = intent.getStringExtra("statueCar");
        if (!TextUtils.isEmpty(statueCar)) {
            viewPager.setCurrentItem(2);
        }
    }

    //重写返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //实现只在冷启动时显示启动页，类似微信效果
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //实现只在冷启动时显示启动页，即点击返回键与点击HOME键退出效果一致
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        homeVm.getSupplierRole();
        observer = i -> {
            // i 是未读数量
            imAddVm.count = i;
            imAddVm.applyCount();
        };
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
    }
}
