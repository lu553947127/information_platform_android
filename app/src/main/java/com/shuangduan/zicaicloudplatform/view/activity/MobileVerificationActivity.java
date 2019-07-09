package com.shuangduan.zicaicloudplatform.view.activity;

import android.view.View;
import android.widget.TextView;

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
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        type = getIntent().getStringExtra(CustomConfig.UPDATE_TYPE);

        tvBarTitle.setText(getString(R.string.mobile_verification));
        switch (type){
            case CustomConfig.updateTypePhone:
                tvBarTitle.setText(getString(R.string.mobile_verification));
                tvTip.setText(String.format(getString(R.string.format_update_mobile_tip), "13333333333"));
                edtPwd.setHint(getString(R.string.hint_SMS_verification_code));
                break;
            case CustomConfig.updateTypeEmail:
                tvBarTitle.setText(getString(R.string.email_verification));
                tvTip.setText(String.format(getString(R.string.format_update_email_tip), "shuangduan@163.com"));
                edtPwd.setHint(getString(R.string.hint_email_verification_code));
                break;
        }

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
                switch (type){
                    case CustomConfig.updateTypePhone:
                        break;
                    case CustomConfig.updateTypeEmail:
                        break;
                }
                break;
            case R.id.tv_confirm:
                switch (type){
                    case CustomConfig.updateTypePhone:
                        ActivityUtils.startActivity(UpdateMobileActivity.class);
                        break;
                    case CustomConfig.updateTypeEmail:
                        ActivityUtils.startActivity(UpdateEmailActivity.class);
                        break;
                }
                break;
        }
    }
}
