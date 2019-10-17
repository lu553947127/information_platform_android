package com.shuangduan.zcy.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import com.shuangduan.zcy.app.AppConfig;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.WxLoginEvent;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.SharesUtils;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.WebViewActivity;
import com.shuangduan.zcy.view.mine.ForgetPwdActivity;
import com.shuangduan.zcy.vm.IMConnectVm;
import com.shuangduan.zcy.vm.LoginVm;
import com.shuangduan.zcy.weight.XEditText;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class 登录
 * @time 2019/7/4 15:23
 * @change
 * @chang time
 * @class describe
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login)
    AppCompatTextView tvLogin;
    @BindView(R.id.tv_register)
    AppCompatTextView tvRegister;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.ll_register)
    LinearLayout llRegister;

    @BindView(R.id.tv_login_account)
    AppCompatTextView tvLoginAccount;
    @BindView(R.id.edt_account)
    AppCompatEditText edtAccount;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    @BindView(R.id.edt_verification_code)
    AppCompatEditText edtVerificationCode;
    @BindView(R.id.tv_send_verification_code)
    AppCompatTextView tvSendVerificationCode;
    @BindView(R.id.rl_verification_code)
    RelativeLayout rlVerification_code;
    @BindView(R.id.cb_keep_user)
    CheckBox cbKeepUser;

    @BindView(R.id.edt_mobile)
    AppCompatEditText edtMobile;
    @BindView(R.id.edt_verification_code_register)
    AppCompatEditText edtVerificationCodeRegister;
    @BindView(R.id.tv_send_verification_code_register)
    AppCompatTextView tvSendVerificationCodeRegister;
    @BindView(R.id.edt_pwd_register)
    XEditText edtPwdRegister;
    @BindView(R.id.edt_mobile_invite)
    AppCompatEditText edtMobileInvite;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;

    private LoginVm loginVm;
    private IMConnectVm imConnectVm;
    private final int LOGIN = 1;//登录
    private final int REGISTER = 2;//注册
    private int loginStyle = LOGIN;//默认显示登录页面
    private int isAccount=0;
    private SharesUtils sharesUtils;
    private String openid,unionid;
    private int isAgreement=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        sharesUtils=new SharesUtils(this);
        loginVm = ViewModelProviders.of(this).get(LoginVm.class);

        //登录验证码获取成功返回结果
        loginVm.timeLiveDataLiveData.observe(this, aLong -> getCode(aLong,tvSendVerificationCode));

        //注册验证码获取成功返回结果
        loginVm.timeLiveDataLiveDataRegister.observe(this,aLong -> getCode(aLong,tvSendVerificationCodeRegister));

        //初始化，融云链接服务器
        imConnectVm = ViewModelProviders.of(this).get(IMConnectVm.class);
        imConnectVm.tokenLiveData.observe(this, imTokenBean -> {
            String token = imTokenBean.getToken();
            SPUtils.getInstance().put(SpConfig.IM_TOKEN, token);
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        });
        imConnectVm.pageStateLiveData.observe(this, this::showPageState);

        //记住用户名
        cbKeepUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switch (buttonView.getId()){
                case R.id.cb_keep_user:
                    if(isChecked){
                        isAccount=1;
                    }else {
                        isAccount=0;
                    }
                    break;
            }
        });

        //同意隐私协议和用户注册协议
        cbAgreement.setChecked(true);
        cbAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switch (buttonView.getId()){
                case R.id.cb_agreement:
                    if(isChecked){
                        isAgreement=1;
                    }else {
                        isAgreement=0;
                    }
                    break;
            }
        });

        //记住用户名 获取是否已经记忆
        if (!sharesUtils.getShared("isAccount","login").equals("")){
            cbKeepUser.setChecked(true);
            edtAccount.setText(sharesUtils.getShared(SpConfig.ACCOUNT,"login"));
        }else {
            cbKeepUser.setChecked(false);
        }

        //微信验证是否绑定过返回结果
        loginVm.wxLoginVerificationBeanMutableLiveData.observe(this,wxLoginVerificationBean -> {
            //判断是否绑定
            if (wxLoginVerificationBean.getWechat_status()==1){

                SPUtils.getInstance().put(SpConfig.USER_ID, wxLoginVerificationBean.getUser_id(), true);
                SPUtils.getInstance().put(SpConfig.TOKEN, wxLoginVerificationBean.getToken(), true);
                SPUtils.getInstance().put(SpConfig.MOBILE, wxLoginVerificationBean.getTel(), true);
                SPUtils.getInstance().put(SpConfig.INFO_STATUS, wxLoginVerificationBean.getInfo_status(), true);

                //判断是否完善信息
                if (wxLoginVerificationBean.getInfo_status()==1){
                    imConnectVm.userId = wxLoginVerificationBean.getUser_id();
                    imConnectVm.getToken();
                }else {
                    ActivityUtils.startActivity(UserInfoInputActivity.class);
                }
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("open_id",openid);
                bundle.putString("union_id",unionid);
                ActivityUtils.startActivity(bundle,WeChatBindingActivity.class);
            }
        });
    }

    @OnClick({R.id.tv_login,R.id.tv_register,R.id.tv_login_account,R.id.tv_send_verification_code,R.id.tv_login_home,R.id.tv_forget_pwd
            ,R.id.tv_send_verification_code_register, R.id.tv_register_home,R.id.tv_privacy_text,R.id.tv_register_text,R.id.iv_wechat})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_login://切换登录按钮
                changeLoginStyle(LOGIN);
                break;
            case R.id.tv_register://切换注册按钮
                changeLoginStyle(REGISTER);
                break;
            case R.id.tv_login_account://账号登陆/验证码登录切换
                if (tvLoginAccount.getText().toString().equals(getString(R.string.login_account))){
                    tvLoginAccount.setText(getString(R.string.login_verification_code));
                    edtPwd.setVisibility(View.VISIBLE);
                    rlVerification_code.setVisibility(View.GONE);
                }else if (tvLoginAccount.getText().toString().equals(getString(R.string.login_verification_code))){
                    tvLoginAccount.setText(getString(R.string.login_account));
                    edtPwd.setVisibility(View.GONE);
                    rlVerification_code.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_send_verification_code://获取验证码
                smsCode(tvSendVerificationCode,edtAccount);
                break;
            case R.id.tv_login_home://登录
                login();
                break;
            case R.id.tv_forget_pwd://忘记密码
                ActivityUtils.startActivity(ForgetPwdActivity.class);
                break;
            case R.id.tv_send_verification_code_register://注册获取验证码
                smsCode(tvSendVerificationCodeRegister,edtMobile);
                break;
            case R.id.tv_register_home://注册
                register();
                break;
            case R.id.tv_privacy_text://隐私协议
                bundle.putString("register", "privacy");
                ActivityUtils.startActivity(bundle, WebViewActivity.class);
                break;
            case R.id.tv_register_text://用户注册协议
                bundle.putString("register", "register");
                ActivityUtils.startActivity(bundle,WebViewActivity.class);
                break;
            case R.id.iv_wechat://微信登录
                if (LoginUtils.isWeixinAvilible(this)){
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_shuangduan_zcy";
                    AppConfig.iwxapi.sendReq(req);
                }else {
                    ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
                }
                break;
        }
    }

    //获取验证码
    private void smsCode(AppCompatTextView textView,AppCompatEditText editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        if (loginStyle==LOGIN){
            loginVm.smsCode(editText.getText().toString(), CustomConfig.SMS_LOGIN);
        }else {
            loginVm.smsCode(editText.getText().toString(), CustomConfig.SMS_REGISTER);
        }
        loginVm.smsDataLiveData.observe(this, o -> {
            textView.setClickable(false);
            textView.setBackgroundColor(getResources().getColor(R.color.color_DDDDDD));
            textView.setTextColor(getResources().getColor(R.color.color_999999));
            if (loginStyle==LOGIN){
                loginVm.sendVerificationCode();
            }else {
                loginVm.sendVerificationCodeRegister();
            }
        });
    }

    //验证码操作
    private void getCode(Long aLong,AppCompatTextView textView) {
        if (aLong == -1) {
            //重新获取
            textView.setText(getString(R.string.send_again));
            textView.setClickable(true);
            textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            textView.setTextColor(getResources().getColor(R.color.colorFFF));
        } else {
            textView.setText(String.format(getString(R.string.format_get_verification_code_again), aLong));
            textView.setClickable(false);
            textView.setBackgroundColor(getResources().getColor(R.color.color_DDDDDD));
            textView.setTextColor(getResources().getColor(R.color.color_999999));
        }
    }

    //登录
    private void login() {
        if (TextUtils.isEmpty(edtAccount.getText())) {
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        String account = Objects.requireNonNull(edtAccount.getText()).toString();//手机号
        String pwd = Objects.requireNonNull(edtPwd.getText()).toString();//密码
        String verificationCode = Objects.requireNonNull(edtVerificationCode.getText()).toString();//验证码
        if (tvLoginAccount.getText().toString().equals(getString(R.string.login_verification_code))){
            if (TextUtils.isEmpty(edtPwd.getText())) {
                ToastUtils.showShort(R.string.pwd_empty);
                return;
            }
            loginVm.accountLogin(account, pwd, DeviceUtils.getAndroidID());
        } else {
            if (TextUtils.isEmpty(edtVerificationCode.getText())) {
                ToastUtils.showShort(getString(R.string.sms_error));
                return;
            }
            loginVm.codeLogin(account, verificationCode, DeviceUtils.getAndroidID());
        }
        loginVm.accountLoginLiveData.observe(this, loginBean -> {
            SPUtils.getInstance().put(SpConfig.USER_ID, loginBean.getUser_id(), true);
            SPUtils.getInstance().put(SpConfig.TOKEN, loginBean.getToken(), true);
            SPUtils.getInstance().put(SpConfig.MOBILE, loginBean.getTel(), true);
            SPUtils.getInstance().put(SpConfig.INFO_STATUS, loginBean.getInfo_status(), true);

            //记住用户名 记忆开始
            if (isAccount==1){
                sharesUtils.addShared(SpConfig.ACCOUNT, edtAccount.getText().toString(),"login");
                sharesUtils.addShared("isAccount", String.valueOf(isAccount),"login");
            }else {
                sharesUtils.clearShared("login");
            }

            LogUtils.i(loginBean.getInfo_status());
            if (loginBean.getInfo_status() == 1) {
                imConnectVm.userId = loginBean.getUser_id();
                imConnectVm.getToken();
            } else {
                ActivityUtils.startActivity(UserInfoInputActivity.class);
            }
        });
        loginVm.pageStateLiveData.observe(this, s -> {
            LogUtils.i(s);
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    //注册
    private void register() {
        if (TextUtils.isEmpty(edtMobile.getText())){
            ToastUtils.showShort(getString(R.string.mobile_error));
            return;
        }
        if (TextUtils.isEmpty(edtVerificationCodeRegister.getText())){
            ToastUtils.showShort(getString(R.string.sms_error));
            return;
        }
        if (TextUtils.isEmpty(edtPwdRegister.getText())){
            ToastUtils.showShort(getString(R.string.pwd_empty));
            return;
        }
        if (isAgreement==0){
            ToastUtils.showShort("请您同意隐私协议和用户注册协议");
            return;
        }
        //注册
        loginVm.register(edtMobile.getText().toString(), edtVerificationCodeRegister.getText().toString(), edtPwdRegister.getText().toString(), Objects.requireNonNull(edtMobileInvite.getText()).toString());
        loginVm.registerLiveData.observe(this, registerBean -> {
            //注册成功走一遍登录
            loginVm.accountLogin(edtMobile.getText().toString(), edtPwdRegister.getText().toString(), DeviceUtils.getAndroidID());
            loginVm.accountLoginLiveData.observe(LoginActivity.this, loginBean -> {
                SPUtils.getInstance().put(SpConfig.USER_ID, loginBean.getUser_id(),true);
                SPUtils.getInstance().put(SpConfig.TOKEN, loginBean.getToken(),true);
                SPUtils.getInstance().put(SpConfig.MOBILE, loginBean.getTel(),true);
                SPUtils.getInstance().put(SpConfig.INFO_STATUS, loginBean.getInfo_status(),true);

                if (loginBean.getInfo_status() == 1){
                    imConnectVm.userId = loginBean.getUser_id();
                    imConnectVm.getToken();
                }else {
                    ActivityUtils.startActivity(UserInfoInputActivity.class);
                }
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

    /**
     * 切换登录和注册
     * @param type
     */
    private void changeLoginStyle(int type) {
        if (loginStyle == type) return;
        switch (type) {
            case LOGIN:
                tvLogin.setTextColor(getResources().getColor(R.color.colorTv));
                tvRegister.setTextColor(getResources().getColor(R.color.colorTvHint));
                tvLogin.setTextSize(19);
                tvRegister.setTextSize(13);
                rlLogin.setVisibility(View.VISIBLE);
                llRegister.setVisibility(View.GONE);
                break;
            case REGISTER:
                tvLogin.setTextColor(getResources().getColor(R.color.colorTvHint));
                tvRegister.setTextColor(getResources().getColor(R.color.colorTv));
                tvLogin.setTextSize(13);
                tvRegister.setTextSize(19);
                rlLogin.setVisibility(View.GONE);
                llRegister.setVisibility(View.VISIBLE);
                break;
        }
        loginStyle = type;
    }

    @Subscribe
    public void onEventWxLogin(WxLoginEvent event) {
        openid=event.getOpenId();
        unionid=event.getUnionId();
        loginVm.getWeChatVerification(unionid,openid);
    }
}
