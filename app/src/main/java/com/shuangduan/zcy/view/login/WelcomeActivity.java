package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.SharesUtils;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.vm.IMConnectVm;


import butterknife.BindView;
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

    @BindView(R.id.ll_welcome)
    LinearLayout linearLayout;
    private SharesUtils shareUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
        if (LoginUtils.isFirstApp()){
            getIntoActivity();
        }else {
            ActivityUtils.startActivity(FirstStartActivity.class);
            finish();
        }
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
        shareUtils=new SharesUtils(this);
    }

    private void getIntoActivity() {
        if (shareUtils.getShared("info_status","login").equals("1")){
            if (LoginUtils.isLogin()){
                linearLayout.setVisibility(View.INVISIBLE);
                ActivityUtils.startActivity(MainActivity.class);
                //这里连一遍融云
                if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) == 1){
                    //初始化，融云链接服务器
                    IMConnectVm imConnectVm = ViewModelProviders.of(this).get(IMConnectVm.class);
                    imConnectVm.connect(SPUtils.getInstance().getString(SpConfig.IM_TOKEN));
                }
            }else {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
        SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
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
