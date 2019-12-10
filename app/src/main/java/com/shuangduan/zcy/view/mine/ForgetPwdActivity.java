package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.vm.LoginVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class 修改密码
 * @time 2019/7/10 13:49
 * @change
 * @chang time
 * @class describe
 */
public class ForgetPwdActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_account)
    AppCompatEditText edtAccount;
    @BindView(R.id.edt_SMS_code)
    AppCompatEditText edtSMSCode;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    @BindView(R.id.edt_pwd_again)
    AppCompatEditText edtPwdAgain;

    private LoginVm loginVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.forget_pwd_title));

        loginVm = ViewModelProviders.of(this).get(LoginVm.class);
        loginVm.timeLiveDataLiveData.observe(this, aLong -> {
            if (aLong == -1) {
                //重新获取
                tvSendVerificationCode.setText(getString(R.string.send_again));
                tvSendVerificationCode.setClickable(true);
                tvSendVerificationCode.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tvSendVerificationCode.setTextColor(getResources().getColor(R.color.colorFFF));
            }else {
                tvSendVerificationCode.setText(String.format(getString(R.string.format_get_verification_code_again), aLong));
                tvSendVerificationCode.setClickable(false);
                tvSendVerificationCode.setBackgroundColor(getResources().getColor(R.color.color_DDDDDD));
                tvSendVerificationCode.setTextColor(getResources().getColor(R.color.color_999999));
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_send_verification_code, R.id.tv_confirm})
    void onClick(View view){
        String mobile;
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_send_verification_code:
                if (TextUtils.isEmpty(edtAccount.getText())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                mobile = edtAccount.getText().toString();
                loginVm.smsCode(mobile, CustomConfig.SMS_FORGET_PWD);
                loginVm.smsDataLiveData.observe(this, o -> {
                    loginVm.sendVerificationCode();
                });
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(edtAccount.getText())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                if (TextUtils.isEmpty(edtSMSCode.getText())){
                    ToastUtils.showShort(getString(R.string.hint_SMS_verification_code));
                    return;
                }
                if (TextUtils.isEmpty(edtPwd.getText())){
                    ToastUtils.showShort(getString(R.string.hint_pwd));
                    return;
                }
                if (TextUtils.isEmpty(edtPwdAgain.getText())){
                    ToastUtils.showShort(getString(R.string.hint_new_pwd));
                    return;
                }
                mobile = edtAccount.getText().toString();
                String code = edtSMSCode.getText().toString();
                String pwdAgain = edtPwdAgain.getText().toString();
                String pwd = edtPwd.getText().toString();
                if (!pwd.equals(pwdAgain)){
                    ToastUtils.showShort(getString(R.string.pwd_no_same));
                    return;
                }
                loginVm.resetPwd(mobile, code, pwd);
                loginVm.resetPwdLiveData.observe(this, reSetPwdBean -> {
                    ToastUtils.showShort(getString(R.string.pwd_reset_success));
                    finish();
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
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyboardUtil.showSoftInputFromWindow(this, edtAccount);
    }
}
