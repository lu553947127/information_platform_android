package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.SmsCodeVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe 忘记支付密码
 * @time 2019/8/13 18:05
 * @change
 * @chang time
 * @class describe
 */
public class ForgetPwdPayActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.edt_SMS_code)
    AppCompatEditText edtSMSCode;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    private UpdatePwdPayVm updatePwdPayVm;
    private SmsCodeVm smsCodeVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_pwd_pay_set;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.update_pwd));
        tvAccount.setText(SPUtils.getInstance().getString(SpConfig.MOBILE));

        smsCodeVm = ViewModelProviders.of(this).get(SmsCodeVm.class);
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.forgetPwdLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.pwd_pay_set_success));
            finish();
        });
        smsCodeVm.smsDataLiveData.observe(this, o -> {
            smsCodeVm.sendVerificationCode();
        });
        smsCodeVm.timeLiveDataLiveData.observe(this, aLong -> {
            if (aLong == -1) {
                //重新获取
                tvSendVerificationCode.setText(getString(R.string.send_again));
                tvSendVerificationCode.setClickable(true);
            }else {
                tvSendVerificationCode.setText(String.format(getString(R.string.format_get_verification_code_again), aLong));
            }
        });
        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        smsCodeVm.pageStateLiveData.observe(this, s -> {
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

    @OnClick({R.id.iv_bar_back, R.id.tv_send_verification_code, R.id.tv_confirm})
    void onClick(View view){
        String mobile;
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_send_verification_code:
                if (TextUtils.isEmpty(tvAccount.getText())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                mobile = tvAccount.getText().toString();
                smsCodeVm.smsCode(mobile, CustomConfig.SMS_PWD_PAY);
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(tvAccount.getText())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                if (TextUtils.isEmpty(edtSMSCode.getText())){
                    ToastUtils.showShort(getString(R.string.hint_SMS_verification_code));
                    return;
                }
                if (TextUtils.isEmpty(edtPwd.getText())){
                    ToastUtils.showShort(getString(R.string.hint_pwd_pay));
                    return;
                }
                mobile = tvAccount.getText().toString();
                String code = edtSMSCode.getText().toString();
                String pwd = edtPwd.getText().toString();
                updatePwdPayVm.forgetPwdPay(mobile, code, pwd);
                break;
        }
    }
}
