package com.shuangduan.zcy.view.activity;

import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.vm.LoginVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe
 * @time 2019/7/4 14:27
 * @change
 * @chang time
 * @class describe
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edt_mobile)
    AppCompatEditText edtMobile;
    @BindView(R.id.edt_verification_code)
    AppCompatEditText edtVerificationCode;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    @BindView(R.id.edt_mobile_invite)
    AppCompatEditText edtMobileInvite;

    private LoginVm loginVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void initDataAndEvent() {
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

    @OnClick({R.id.tv_send_verification_code, R.id.tv_register})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_send_verification_code:
                tvSendVerificationCode.setClickable(false);
                loginVm.sendVerificationCode();
                break;
            case R.id.tv_register:
                break;
        }
    }

}
