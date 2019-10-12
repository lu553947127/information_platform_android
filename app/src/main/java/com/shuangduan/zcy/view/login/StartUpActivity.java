package com.shuangduan.zcy.view.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.view.MainActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
        if (LoginUtils.isFirstApp()){
            if (SPUtils.getInstance().getInt(SpConfig.INFO_STATUS) == 1){
                getIntoActivity();
            }else {
                ActivityUtils.startActivity(LoginActivity.class);
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

    }

    private void getIntoActivity() {
        if (LoginUtils.isLogin()){
            //初始化，融云链接服务器
            String token = SPUtils.getInstance().getString(SpConfig.IM_TOKEN);
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                }
                @Override
                public void onSuccess(String userid) {
                    LogUtils.i("融云连接成功" + userid);
                    LogUtils.i("融云--token--" + token);
                    ActivityUtils.startActivity(MainActivity.class);
                    StartUpActivity.this.finish();
                }
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.i("融云--token--" + token);
                    LogUtils.i("融云连接失败" + errorCode);
                    ActivityUtils.startActivity(LoginActivity.class);
                    finish();
                }
            });
        }else {
            ActivityUtils.startActivity(LoginActivity.class);
            finish();
        }
        SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
    }
}
