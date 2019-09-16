package com.shuangduan.zcy.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.FragmentAdapter;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.rongyun.view.CircleFragment;
import com.shuangduan.zcy.view.login.UserInfoInputActivity;
import com.shuangduan.zcy.vm.AuthenticationVm;
import com.shuangduan.zcy.vm.IMConnectVm;
import com.shuangduan.zcy.weight.NoScrollViewPager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

import static com.shuangduan.zcy.app.AppConfig.APP_ID;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    List<Fragment> mFragments;
    FragmentAdapter fragmentAdapter;

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
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        regToWx();
        setAlias();
        initBottomNavigation();
        checkInfoState();
        //初始实名状态查询
        AuthenticationVm authenticationVm = ViewModelProviders.of(this).get(AuthenticationVm.class);
        authenticationVm.authenticationStatusLiveData.observe(this, authenBean -> {
            SPUtils.getInstance().put(SpConfig.IS_VERIFIED, authenBean.getCard_status());
        });
        authenticationVm.pageStateLiveData.observe(this, this::showPageState);
        authenticationVm.authentication();

        if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) == 1){
            //初始化，融云链接服务器
            IMConnectVm imConnectVm = ViewModelProviders.of(this).get(IMConnectVm.class);
            imConnectVm.connect(SPUtils.getInstance().getString(SpConfig.IM_TOKEN));
        }
    }

    //底部标签栏点击切换
    public void initBottomNavigation() {
        //fragment布局初始化
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(PeopleFragment.newInstance());
        mFragments.add(CircleFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),this,mFragments);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String statueCar = intent.getStringExtra("statueCar");
        if (!TextUtils.isEmpty(statueCar)) {
            viewPager.setCurrentItem(2);
        }
    }

    /**
     * 检测信息是否录入过
     */
    private void checkInfoState(){
        if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) != 1){
            ActivityUtils.startActivity(UserInfoInputActivity.class);
        }
    }

    private boolean mIsExit = false;

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
//                AppConfig.mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
                ActivityUtils.finishAllActivities();
            } else {
                ToastUtils.showShort("再按一次退出");
                mIsExit = true;
                new Handler().postDelayed(() -> mIsExit = false, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    /**
     * 极光别名
     */
    private void setAlias(){
        int userId = SPUtils.getInstance().getInt(SpConfig.USER_ID, 0);
        int aliasStatus = SPUtils.getInstance().getInt(SpConfig.ALIAS_STATUS, 0);
        if (userId != 0 && aliasStatus != 1){
            LogUtils.i("别名设置", userId);
            JPushInterface.setAlias(this, userId, String.valueOf(userId));
        }
    }
}
