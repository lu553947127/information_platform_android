package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.RegisterBean;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.vm.LoginVm;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  注册
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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        loginVm = ViewModelProviders.of(this).get(LoginVm.class);
        loginVm.timeLiveDataLiveData.observe(this, aLong -> {
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
                smsCode();
                break;
            case R.id.tv_register:
                register();
                break;
        }
    }

    private void register() {
        if (TextUtils.isEmpty(edtMobile.getText())){
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        if (TextUtils.isEmpty(edtVerificationCode.getText())){
            ToastUtils.showShort(getString(R.string.sms_error));
            return;
        }
        if (TextUtils.isEmpty(edtPwd.getText())){
            ToastUtils.showShort(getString(R.string.pwd_empty));
            return;
        }
        //注册
        loginVm.register(edtMobile.getText().toString(), edtVerificationCode.getText().toString(), edtPwd.getText().toString(), Objects.requireNonNull(edtMobileInvite.getText()).toString());
        loginVm.registerLiveData.observe(this, registerBean -> {
            //注册成功走一遍登录
            loginVm.accountLogin(edtMobile.getText().toString(), edtPwd.getText().toString(), DeviceUtils.getAndroidID());
            loginVm.accountLoginLiveData.observe(RegisterActivity.this, loginBean -> {
                SPUtils.getInstance().put(SpConfig.USER_ID, loginBean.getUser_id());
                SPUtils.getInstance().put(SpConfig.TOKEN, loginBean.getToken());
                SPUtils.getInstance().put(SpConfig.MOBILE, loginBean.getTel());
                SPUtils.getInstance().put(SpConfig.INFO_STATUS, loginBean.getInfo_status());
                ActivityUtils.startActivity(MainActivity.class);
                finish();
            });
        });
        loginVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    private void smsCode(){
        if (TextUtils.isEmpty(edtMobile.getText())){
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        loginVm.smsCode(edtMobile.getText().toString(), CustomConfig.SMS_REGISTER);
        loginVm.smsDataLiveData.observe(this, o -> {
            tvSendVerificationCode.setClickable(false);
            loginVm.sendVerificationCode();
            ToastUtils.showShort(getString(R.string.send_verification_code_success));
        });
    }

}
