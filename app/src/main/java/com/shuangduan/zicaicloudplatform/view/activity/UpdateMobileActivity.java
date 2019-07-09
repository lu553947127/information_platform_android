package com.shuangduan.zicaicloudplatform.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.app.CustomConfig;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;
import com.shuangduan.zicaicloudplatform.vm.LoginVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  修改手机号
 * @time 2019/7/8 10:52
 * @change
 * @chang time
 * @class describe
 */
public class UpdateMobileActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_account)
    AppCompatEditText edtAccount;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;

    private LoginVm loginVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_mobile;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.update_mobile));

        loginVm = ViewModelProviders.of(this).get(LoginVm.class);
        loginVm.getTimeLiveData().observe(this, aLong -> {
            if (aLong == -1) {
                //重新获取
                tvSendVerificationCode.setText(getString(R.string.send_again));
                tvSendVerificationCode.setClickable(true);
            }else {
                tvSendVerificationCode.setText(String.format(getString(R.string.format_get_verification_code_again), aLong));
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_send_verification_code, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_send_verification_code:
                loginVm.sendVerificationCode();
                break;
            case R.id.tv_confirm:
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypePhone);
                ActivityUtils.startActivity(bundle, UpdateResultActivity.class);
                finish();
                break;
        }
    }
}
