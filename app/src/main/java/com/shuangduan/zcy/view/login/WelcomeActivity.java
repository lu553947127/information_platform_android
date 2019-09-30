package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

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
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
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
