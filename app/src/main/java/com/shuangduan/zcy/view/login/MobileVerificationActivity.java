package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.view.mine.UpdateEmailActivity;
import com.shuangduan.zcy.view.mine.UpdateMobileActivity;
import com.shuangduan.zcy.vm.LoginVm;

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

    @OnClick({R.id.iv_bar_back, R.id.tv_send_verification_code, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_send_verification_code:
                loginVm.sendVerificationCode();
                switch (type){
                    case CustomConfig.updateTypePhone:
                        loginVm.sendVerificationCode();
                        break;
                    case CustomConfig.updateTypeEmail:
                        loginVm.sendVerificationCode();
                        break;
                }
                break;
            case R.id.tv_confirm:
                String verificationCode = edtPwd.getText().toString();
                switch (type){
                    case CustomConfig.updateTypePhone:
                        if (StringUtils.isTrimEmpty(verificationCode)){
                            ToastUtils.showShort(getString(R.string.hint_SMS_verification_code));
                            return;
                        }
                        ActivityUtils.startActivity(UpdateMobileActivity.class);
                        break;
                    case CustomConfig.updateTypeEmail:
                        if (StringUtils.isTrimEmpty(verificationCode)){
                            ToastUtils.showShort(getString(R.string.hint_email_verification_code));
                            return;
                        }
                        ActivityUtils.startActivity(UpdateEmailActivity.class);
                        break;
                }
                finish();
                break;
        }
    }
}
