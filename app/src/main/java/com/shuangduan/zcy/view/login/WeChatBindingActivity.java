package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.vm.IMConnectVm;
import com.shuangduan.zcy.vm.LoginVm;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class 微信登录绑定手机号页
 * @time 2019/7/4 10:29
 * @change
 * @chang time
 * @class describe
 */
public class WeChatBindingActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_mobile)
    AppCompatEditText edtMobile;
    @BindView(R.id.edt_verification_code)
    AppCompatEditText edtVerificationCode;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;
    @BindView(R.id.edt_mobile_invite)
    AppCompatEditText edtMobileInvite;
    private LoginVm loginVm;
    private IMConnectVm imConnectVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_we_chat_binding;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText("绑定手机号");
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

    @OnClick({R.id.iv_bar_back,R.id.tv_send_verification_code, R.id.tv_binding_phone})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_send_verification_code://获取验证码
                smsCode();
                break;
            case R.id.tv_binding_phone://绑定手机号
                weChatLogin();
                break;
        }
    }

    //微信登录绑定手机号
    private void weChatLogin() {
        if (TextUtils.isEmpty(edtMobile.getText())){
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        if (TextUtils.isEmpty(edtVerificationCode.getText())){
            ToastUtils.showShort(getString(R.string.sms_error));
            return;
        }
        //绑定微信
        loginVm.getWeChatBinding(getIntent().getStringExtra("union_id"),getIntent().getStringExtra("open_id")
                ,edtMobile.getText().toString(), edtVerificationCode.getText().toString(), Objects.requireNonNull(edtMobileInvite.getText()).toString());
        //绑定微信返回结果
        loginVm.wxLoginBindingBeanMutableLiveData.observe(this,wxLoginBindingBean -> {

            SPUtils.getInstance().put(SpConfig.USER_ID, wxLoginBindingBean.getUser_id(), true);
            SPUtils.getInstance().put(SpConfig.TOKEN, wxLoginBindingBean.getToken(), true);
            SPUtils.getInstance().put(SpConfig.MOBILE, wxLoginBindingBean.getTel(), true);
            SPUtils.getInstance().put(SpConfig.INFO_STATUS, wxLoginBindingBean.getInfo_status(), true);
            SPUtils.getInstance().put(SpConfig.IS_VERIFIED, wxLoginBindingBean.getCard_status(),true);

            //判断是否完善信息
            if (wxLoginBindingBean.getInfo_status()==1){
                imConnectVm.userId = wxLoginBindingBean.getUser_id();
                imConnectVm.getToken();
            }else {
                ActivityUtils.startActivity(UserInfoInputActivity.class);
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
    }

    //获取验证码
    private void smsCode(){
        if (TextUtils.isEmpty(edtMobile.getText())){
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        loginVm.smsCode(edtMobile.getText().toString(), CustomConfig.SMS_WECHAT_LOGIN);
        loginVm.smsDataLiveData.observe(this, o -> {
            tvSendVerificationCode.setClickable(false);
            tvSendVerificationCode.setBackgroundColor(getResources().getColor(R.color.color_DDDDDD));
            tvSendVerificationCode.setTextColor(getResources().getColor(R.color.color_999999));
            loginVm.sendVerificationCode();
            ToastUtils.showShort(getString(R.string.send_verification_code_success));
        });
    }
}
