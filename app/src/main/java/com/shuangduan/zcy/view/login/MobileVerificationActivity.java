package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.view.mine.UpdateEmailActivity;
import com.shuangduan.zcy.view.mine.UpdateMobileActivity;
import com.shuangduan.zcy.vm.LoginVm;
import com.shuangduan.zcy.vm.UserInfoVm;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  手机邮箱验证界面
 * @time 2019/7/8 11:21
 * @change
 * @chang time
 * @class describe
 */
public class MobileVerificationActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private String type;
    private LoginVm loginVm;
    private UserInfoVm userInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mobile_verification;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        type = getIntent().getStringExtra(CustomConfig.UPDATE_TYPE);

        tvBarTitle.setText(getString(R.string.mobile_verification));
        switch (type){
            case CustomConfig.updateTypePhone:
                tvBarTitle.setText(getString(R.string.mobile_verification));
                tvTip.setText(String.format(getString(R.string.format_update_mobile_tip), SPUtils.getInstance().getString(SpConfig.MOBILE)));
                edtPwd.setHint(getString(R.string.hint_SMS_verification_code));
                break;
            case CustomConfig.updateTypeEmail:
                tvBarTitle.setText(getString(R.string.email_verification));
                tvTip.setText(String.format(getString(R.string.format_update_email_tip), SPUtils.getInstance().getString(SpConfig.EMAIL)));
                edtPwd.setHint(getString(R.string.hint_email_verification_code));
                break;
        }

        loginVm = ViewModelProviders.of(this).get(LoginVm.class);
        loginVm.smsDataLiveData.observe(this, o -> {
            loginVm.sendVerificationCode();
        });
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

        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.checkTelLiveData.observe(this, o -> {
            Bundle bundle = new Bundle();
            bundle.putString(CustomConfig.VERIFICATION_CODE, Objects.requireNonNull(edtPwd.getText()).toString());
            ActivityUtils.startActivity(bundle, UpdateMobileActivity.class);
            finish();
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
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
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_send_verification_code:
                switch (type){
                    case CustomConfig.updateTypePhone:
                        loginVm.smsCode(SPUtils.getInstance().getString(SpConfig.MOBILE), CustomConfig.SMS_UPDATE_PHONE);
                        break;
                    case CustomConfig.updateTypeEmail:

                        break;
                }
                break;
            case R.id.tv_confirm:
                String verificationCode = Objects.requireNonNull(edtPwd.getText()).toString();
                switch (type){
                    case CustomConfig.updateTypePhone:
                        if (TextUtils.isEmpty(edtPwd.getText())){
                            ToastUtils.showShort(getString(R.string.hint_SMS_verification_code));
                            return;
                        }
                       userInfoVm.checkTel(verificationCode);
                        break;
                    case CustomConfig.updateTypeEmail:
                        if (TextUtils.isEmpty(edtPwd.getText())){
                            ToastUtils.showShort(getString(R.string.hint_email_verification_code));
                            return;
                        }
                        ActivityUtils.startActivity(UpdateEmailActivity.class);
                        break;
                }
                break;
        }
    }
}
