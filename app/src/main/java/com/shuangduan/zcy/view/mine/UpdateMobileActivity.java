package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

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
import com.shuangduan.zcy.model.event.MobileEvent;
import com.shuangduan.zcy.vm.LoginVm;
import com.shuangduan.zcy.vm.UserInfoVm;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

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
    private UserInfoVm userInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_mobile;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.update_mobile));

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
        loginVm.smsDataLiveData.observe(this, o -> loginVm.sendVerificationCode());

        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.updateTelLiveData.observe(this, o -> {
            SPUtils.getInstance().put(SpConfig.MOBILE, Objects.requireNonNull(edtAccount.getText()).toString());
            EventBus.getDefault().post(new MobileEvent(Objects.requireNonNull(edtAccount.getText()).toString()));
            finish();
        });

        loginVm.pageStateLiveData.observe(this, s -> showHideLoading(s));
        userInfoVm.pageStateLiveData.observe(this, s -> showHideLoading(s));
    }

    private void showHideLoading(String s){
        switch (s){
            case PageState.PAGE_LOADING:
                showLoading();
                break;
            default:
                hideLoading();
                break;
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_send_verification_code, R.id.tv_confirm})
    void onClick(View view){
        String mobile;
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_send_verification_code:
                mobile = Objects.requireNonNull(edtAccount.getText()).toString();
                if (TextUtils.isEmpty(edtAccount.getText())){
                    ToastUtils.showShort(getString(R.string.hint_new_mobile));
                    return;
                }
                loginVm.smsCode(mobile, CustomConfig.SMS_UPDATE_PHONE);
                break;
            case R.id.tv_confirm:
                mobile = Objects.requireNonNull(edtAccount.getText()).toString();
                if (TextUtils.isEmpty(edtAccount.getText())){
                    ToastUtils.showShort(getString(R.string.hint_new_mobile));
                    return;
                }
                String verificationCode = Objects.requireNonNull(edtPwd.getText()).toString();
                if (TextUtils.isEmpty(edtPwd.getText())){
                    ToastUtils.showShort(getString(R.string.hint_SMS_verification_code));
                    return;
                }
                userInfoVm.updateTel(mobile, verificationCode, getIntent().getStringExtra(CustomConfig.VERIFICATION_CODE));
                break;
        }
    }
}
