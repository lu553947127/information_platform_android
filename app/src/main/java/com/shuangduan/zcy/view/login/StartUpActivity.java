package com.shuangduan.zcy.view.login;

import android.os.Bundle;

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

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.login
 * @ClassName: StartUpActivity
 * @Description: 启动页
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/26 13:36
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/26 13:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StartUpActivity extends BaseActivity {

    private SharesUtils shareUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
        if (LoginUtils.isFirstApp()){
            if (shareUtils.getShared("info_status","login").equals("1")){
                getIntoActivity();
            }else {
                ActivityUtils.startActivity(WelcomeActivity.class);
                finish();
            }
        }else {
            ActivityUtils.startActivity(FirstStartActivity.class);
            finish();
        }
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_start_up;
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
        if (LoginUtils.isLogin()){
            ActivityUtils.startActivity(MainActivity.class);
            //这里连一遍融云
            if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) == 1){
                //初始化，融云链接服务器
                IMConnectVm imConnectVm = ViewModelProviders.of(this).get(IMConnectVm.class);
                imConnectVm.connect(SPUtils.getInstance().getString(SpConfig.IM_TOKEN));
            }
        }else {
            ActivityUtils.startActivity(WelcomeActivity.class);
            finish();
        }
        SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
    }
}
