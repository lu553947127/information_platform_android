package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class 微信登录绑定手机号页
 * @time 2019/7/4 10:29
 * @change
 * @chang time
 * @class describe
 */
public class WeChatBindingActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText("身份认证");
    }

    @OnClick({R.id.tv_send_verification_code, R.id.tv_binding_phone})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_send_verification_code:

                break;
            case R.id.tv_binding_phone:

                break;
        }
    }
}
