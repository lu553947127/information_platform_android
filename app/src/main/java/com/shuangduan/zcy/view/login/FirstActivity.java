package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.event.BaseEvent;
import com.shuangduan.zcy.model.event.CityEvent;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.login.LoginActivity;
import com.shuangduan.zcy.view.login.RegisterActivity;
import com.shuangduan.zcy.vm.IMConnectVm;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe
 * @time 2019/7/4 10:29
 * @change
 * @chang time
 * @class describe
 */
public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_first;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        //设置状态栏颜色模式
        BarUtils.setStatusBarLightMode(this, true);
        LogUtils.i("启动");

        if (LoginUtils.isLogin()){
            ActivityUtils.startActivity(MainActivity.class);

            //这里连一遍融云
            if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) == 1){
                //初始化，融云链接服务器
                IMConnectVm imConnectVm = ViewModelProviders.of(this).get(IMConnectVm.class);
                imConnectVm.connect(SPUtils.getInstance().getString(SpConfig.IM_TOKEN));
            }
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_login:
                ActivityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.tv_register:
                ActivityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }

}
