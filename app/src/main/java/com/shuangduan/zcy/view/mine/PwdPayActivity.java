package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

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
import com.shuangduan.zcy.model.event.PwdPaySetEvent;
import com.shuangduan.zcy.vm.AuthenticationVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  支付密码
 * @time 2019/8/13 17:58
 * @change
 * @chang time
 * @class describe
 */
public class PwdPayActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private UpdatePwdPayVm updatePwdPayVm;
    private AuthenticationVm authenticationVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_pwd_pay;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.pwd_pay));

        authenticationVm = ViewModelProviders.of(this).get(AuthenticationVm.class);
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.stateLiveData.observe(this, pwdPayStateBean -> {
            SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, pwdPayStateBean.getStatus());
            updatePwdPayVm.status = pwdPayStateBean.getStatus();
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
        authenticationVm.authenticationStatusLiveData.observe(this, authenBean -> {
            SPUtils.getInstance().put(SpConfig.IS_VERIFIED, authenBean.getCard_status());
            if (authenBean.getCard_status() == 2){
                //认证过了
                updatePwdPayVm.payPwdState();
            }else {
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                finish();
            }
        });
        authenticationVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
        if (status == 1){
            updatePwdPayVm.status = status;
        }else {
            int isVerified = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED, 0);
            if (isVerified == 2){
                updatePwdPayVm.payPwdState();
            }else {
                authenticationVm.authentication();
            }
        }
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_pwd_set, R.id.tv_pwd_update, R.id.tv_pwd_forget})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_pwd_set:
                if (updatePwdPayVm.status == 1){
                    ToastUtils.showShort(getString(R.string.pwd_pay_done));
                    return;
                }
                ActivityUtils.startActivity(SetPwdPayActivity.class);
                break;
            case R.id.tv_pwd_update:
                if (updatePwdPayVm.status != 1){
                    ToastUtils.showShort(getString(R.string.pwd_pay_not_set));
                    return;
                }
                ActivityUtils.startActivity(UpdatePwdPayActivity.class);
                break;
            case R.id.tv_pwd_forget:
                if (updatePwdPayVm.status != 1){
                    ToastUtils.showShort(getString(R.string.pwd_pay_not_set));
                    return;
                }
                ActivityUtils.startActivity(ForgetPwdPayActivity.class);
                break;
        }
    }

    @Subscribe
    public void onEventStateUpdate(PwdPaySetEvent event){
        updatePwdPayVm.status = 1;
    }
}
