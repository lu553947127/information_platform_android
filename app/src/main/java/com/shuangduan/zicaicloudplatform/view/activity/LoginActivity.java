package com.shuangduan.zicaicloudplatform.view.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;
import com.shuangduan.zicaicloudplatform.vm.LoginVm;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe
 * @time 2019/7/4 15:23
 * @change
 * @chang time
 * @class describe
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login_account)
    AppCompatTextView tvLoginAccount;
    @BindView(R.id.tv_login_verification_code)
    AppCompatTextView tvLoginVerificationCode;
    @BindView(R.id.edt_account)
    AppCompatEditText edtAccount;
    @BindView(R.id.tv_account_null)
    AppCompatTextView tvAccountNull;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;
    @BindView(R.id.tv_forget_pwd)
    AppCompatTextView tvForgetPwd;

    private LoginVm loginVm;
    /*登录方式：1、账号登录，2、验证码登录*/
    private final int LOGIN_ACCOUNT = 1;
    private final int LOGIN_VERIFICATION_CODE = 2;
    /*默认账号登录*/
    private int loginStyle = LOGIN_ACCOUNT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDataAndEvent() {
        loginVm = ViewModelProviders.of(this).get(LoginVm.class);
        loginVm.getChangeData().observe(this, integer -> {

        });
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

    @OnClick({R.id.tv_login_account, R.id.tv_login_verification_code, R.id.tv_send_verification_code, R.id.tv_login})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_account:
                changeLoginStyle(LOGIN_ACCOUNT);
                break;
            case R.id.tv_login_verification_code:
                changeLoginStyle(LOGIN_VERIFICATION_CODE);
                break;
            case R.id.tv_send_verification_code:
                tvSendVerificationCode.setClickable(false);
                loginVm.sendVerificationCode();
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(Objects.requireNonNull(edtAccount.getText()).toString())) {
                    ToastUtils.showShort("手机号错误");
                    return;
                }
                ActivityUtils.startActivity(UserInfoInputActivity.class);
                break;
        }
    }

    private void changeLoginStyle(int type){
        if (loginStyle == type) return;
        switch (type) {
            case LOGIN_ACCOUNT:
                tvLoginAccount.setTextColor(getResources().getColor(R.color.colorTv));
                tvLoginVerificationCode.setTextColor(getResources().getColor(R.color.colorTvHint));
                tvLoginAccount.setTextSize(19);
                tvLoginVerificationCode.setTextSize(13);
                edtPwd.setHint(getString(R.string.hint_pwd));
                tvSendVerificationCode.setVisibility(View.INVISIBLE);
                edtPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tvForgetPwd.setVisibility(View.VISIBLE);
                break;
            case LOGIN_VERIFICATION_CODE:
                tvLoginAccount.setTextColor(getResources().getColor(R.color.colorTvHint));
                tvLoginVerificationCode.setTextColor(getResources().getColor(R.color.colorTv));
                tvLoginAccount.setTextSize(13);
                tvLoginVerificationCode.setTextSize(19);
                edtPwd.setHint(getString(R.string.hint_SMS_verification_code));
                edtPwd.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                tvSendVerificationCode.setVisibility(View.VISIBLE);
                tvForgetPwd.setVisibility(View.INVISIBLE);
                break;
        }
        loginStyle = type;
    }

}
