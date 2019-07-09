package com.shuangduan.zicaicloudplatform.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;

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
    protected void initDataAndEvent() {
        //设置状态栏颜色模式
        BarUtils.setStatusBarLightMode(this, true);
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
