package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.mine.UpdatePwdActivity;
import com.shuangduan.zcy.vm.IMConnectVm;
import com.shuangduan.zcy.vm.LoginVm;

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
    @BindView(R.id.edt_verification_code)
    AppCompatEditText edtVerificationCode;
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
    private IMConnectVm imConnectVm;

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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        loginVm = ViewModelProviders.of(this).get(LoginVm.class);

        loginVm.timeLiveDataLiveData.observe(this, aLong -> {
            if (aLong == -1) {
                //重新获取
                tvSendVerificationCode.setText(getString(R.string.send_again));
                tvSendVerificationCode.setClickable(true);
            }else {
                tvSendVerificationCode.setText(String.format(getString(R.string.format_get_verification_code_again), aLong));
                tvSendVerificationCode.setClickable(false);
            }
        });

        //初始化，融云链接服务器
        imConnectVm = ViewModelProviders.of(this).get(IMConnectVm.class);
        imConnectVm.tokenLiveData.observe(this, imTokenBean -> {
            String token = imTokenBean.getToken();
            SPUtils.getInstance().put(SpConfig.IM_TOKEN, token);
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        });
        imConnectVm.pageStateLiveData.observe(this, this::showPageState);
    }

    @OnClick({R.id.tv_login_account, R.id.tv_login_verification_code, R.id.tv_send_verification_code, R.id.tv_login, R.id.tv_forget_pwd})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_account:
                changeLoginStyle(LOGIN_ACCOUNT);
                break;
            case R.id.tv_login_verification_code:
                changeLoginStyle(LOGIN_VERIFICATION_CODE);
                break;
            case R.id.tv_send_verification_code:
                smsCode();
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_forget_pwd:
                ActivityUtils.startActivity(UpdatePwdActivity.class);
                break;
        }
    }

    private void smsCode(){
        if (TextUtils.isEmpty(edtAccount.getText())){
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        loginVm.smsCode(edtAccount.getText().toString(), CustomConfig.SMS_LOGIN);
        loginVm.smsDataLiveData.observe(this, o -> {
            tvSendVerificationCode.setClickable(false);
            loginVm.sendVerificationCode();
        });
    }

    private void login() {
        if (TextUtils.isEmpty(edtAccount.getText())) {
            ToastUtils.showShort(getString(loginStyle == LOGIN_ACCOUNT ? R.string.account_error:R.string.mobile_error));
            return;
        }
        String account = Objects.requireNonNull(edtAccount.getText()).toString();
        String pwd = Objects.requireNonNull(edtPwd.getText()).toString();
        String verificationCode = Objects.requireNonNull(edtVerificationCode.getText()).toString();
        if (loginStyle == LOGIN_ACCOUNT){
            if (TextUtils.isEmpty(edtPwd.getText())) {
                ToastUtils.showShort(R.string.pwd_error);
                return;
            }
            loginVm.accountLogin(account, pwd, DeviceUtils.getAndroidID());
        }else {
            if (TextUtils.isEmpty(edtVerificationCode.getText())) {
                ToastUtils.showShort(getString(R.string.sms_error));
                return;
            }
            loginVm.codeLogin(account, verificationCode, DeviceUtils.getAndroidID());
        }
        loginVm.accountLoginLiveData.observe(this, loginBean -> {
            SPUtils.getInstance().put(SpConfig.USER_ID, loginBean.getUser_id());
            SPUtils.getInstance().put(SpConfig.TOKEN, loginBean.getToken());
            SPUtils.getInstance().put(SpConfig.MOBILE, loginBean.getTel());
            SPUtils.getInstance().put(SpConfig.INFO_STATUS, loginBean.getInfo_status());

            imConnectVm.userId = loginBean.getUser_id();
            imConnectVm.getToken();
        });
        loginVm.pageStateLiveData.observe(this, s -> {
            LogUtils.i(s);
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

    /**
     * 切换登录方式
     * @param type
     */
    private void changeLoginStyle(int type){
        if (loginStyle == type) return;
        switch (type) {
            case LOGIN_ACCOUNT:
                tvLoginAccount.setTextColor(getResources().getColor(R.color.colorTv));
                tvLoginVerificationCode.setTextColor(getResources().getColor(R.color.colorTvHint));
                tvLoginAccount.setTextSize(19);
                tvLoginVerificationCode.setTextSize(13);
                edtPwd.setVisibility(View.VISIBLE);
                edtVerificationCode.setVisibility(View.INVISIBLE);
                tvSendVerificationCode.setVisibility(View.INVISIBLE);
                tvForgetPwd.setVisibility(View.VISIBLE);
                break;
            case LOGIN_VERIFICATION_CODE:
                tvLoginAccount.setTextColor(getResources().getColor(R.color.colorTvHint));
                tvLoginVerificationCode.setTextColor(getResources().getColor(R.color.colorTv));
                tvLoginAccount.setTextSize(13);
                tvLoginVerificationCode.setTextSize(19);
                edtPwd.setVisibility(View.INVISIBLE);
                edtVerificationCode.setVisibility(View.VISIBLE);
                tvSendVerificationCode.setVisibility(View.VISIBLE);
                tvForgetPwd.setVisibility(View.INVISIBLE);
                break;
        }
        loginStyle = type;
    }

}
