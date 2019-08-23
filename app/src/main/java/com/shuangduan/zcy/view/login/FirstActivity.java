package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.login.LoginActivity;
import com.shuangduan.zcy.view.login.RegisterActivity;

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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        //设置状态栏颜色模式
        BarUtils.setStatusBarLightMode(this, true);
        LogUtils.i("启动");

        if (LoginUtils.isLogin()){
            ActivityUtils.startActivity(MainActivity.class);
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
