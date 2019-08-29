package com.shuangduan.zcy.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.rongyun.view.CircleFragment;
import com.shuangduan.zcy.view.login.UserInfoInputActivity;
import com.shuangduan.zcy.vm.AuthenticationVm;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

import static com.shuangduan.zcy.app.AppConfig.APP_ID;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private Fragment[] fragments = new Fragment[4];

    private String[] tags = {
            "home",
            "people",
            "circle",
            "mine"
    };

    /**
     * 默认选中
     */
    private int currentChecked = 0;
    /**
     * 上一次选中
     */
    private int oldChecked = -1;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragments[0] == null && fragment instanceof HomeFragment)
            fragments[0] = fragment;
        if (fragments[1] == null && fragment instanceof PeopleFragment)
            fragments[1] = fragment;
        if (fragments[2] == null && fragment instanceof CircleFragment)
            fragments[2] = fragment;
        if (fragments[3] == null && fragment instanceof MineFragment)
            fragments[3] = fragment;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        regToWx();
        setAlias();
        if (fragments[0] == null) {
            fragments[0] = HomeFragment.newInstance();
        }
        if (fragments[1] == null) {
            fragments[1] = PeopleFragment.newInstance();
        }
        if (fragments[2] == null) {
            fragments[2] = CircleFragment.newInstance();
        }
        if (fragments[3] == null) {
            fragments[3] = MineFragment.newInstance();
        }

        //初始化选中首页
        rgMain.check(R.id.rb_home);
        switchFragment(currentChecked);

        rgMain.setOnCheckedChangeListener((group, checkedId) -> {

            oldChecked = currentChecked;

            switch (checkedId) {
                case R.id.rb_home:
                    currentChecked = 0;
                    break;
                case R.id.rb_people:
                    currentChecked = 1;
                    break;
                case R.id.rb_circle:
                    currentChecked = 2;
                    break;
                case R.id.rb_mine:
                    currentChecked = 3;
                    break;
            }
            //选中项不变不做处理
            if (oldChecked != currentChecked) {
                switchFragment(currentChecked);
            }

        });

        checkInfoState();
        //初始实名状态查询
        AuthenticationVm authenticationVm = ViewModelProviders.of(this).get(AuthenticationVm.class);
        authenticationVm.authenticationStatusLiveData.observe(this, authenBean -> {
            SPUtils.getInstance().put(SpConfig.IS_VERIFIED, authenBean.getCard_status());
        });
        authenticationVm.pageStateLiveData.observe(this, this::showPageState);
        authenticationVm.authentication();
    }

    /**
     * 切换fragment
     * @param position
     */
    private void switchFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (!fragments[position].isAdded()) {
            //存在上一个界面，先隐藏
            if (oldChecked == -1) {
                transaction.add(R.id.frame_main, fragments[position], tags[position])
                        .commit();
            } else {
                transaction.add(R.id.frame_main, fragments[position], tags[position])
                        .hide(fragments[oldChecked])
                        .commit();
            }
        } else {
            FragmentTransaction show = transaction.show(fragments[position]);
            if (oldChecked != -1) {
                show.hide(fragments[oldChecked]);
            }
            show.commit();
        }

    }

    /**
     * 检测信息是否录入过
     */
    private void checkInfoState(){
        if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) == 0){
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
