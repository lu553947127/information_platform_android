package com.shuangduan.zcy.view.mine.set;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.SupplierUtils;
import com.shuangduan.zcy.view.WebViewActivity;
import com.shuangduan.zcy.vm.ExitVm;
import com.shuangduan.zcy.vm.SupplierVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class describe  设置
 * @time 2019/7/10 11:54
 * @change
 * @chang time
 * @class describe
 */
public class SetActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ExitVm exitVm;
    private int supplier_status,id;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_set;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.set));

        exitVm = ViewModelProviders.of(this).get(ExitVm.class);
        SupplierVm supplierVm = ViewModelProviders.of(this).get(SupplierVm.class);

        //退出登录返回结果
        exitVm.exitLiveData.observe(this, o -> {
            LoginUtils.getExitLogin();
            finish();
        });
        exitVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_ERROR:
                case PageState.PAGE_SERVICE_ERROR:
                case PageState.PAGE_LOAD_SUCCESS:
                    hideLoading();
                    break;
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
            }
        });

        //获取供应商审核状态
        supplierVm.supplierStatusLiveData.observe(this,supplierStatusBean -> {
            SPUtils.getInstance().put(CustomConfig.SUPPLIER_STATUS, supplierStatusBean.getStatus());
            supplier_status = supplierStatusBean.getStatus();
            id = supplierStatusBean.getId();
        });
        supplierVm.supplierStatus();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_update_pwd,R.id.tv_pwd_pay,R.id.rl_third_login
            ,R.id.tv_receiving_address,R.id.tv_supplier_msg,R.id.tv_feedback
            ,R.id.tv_register_agreement,R.id.tv_privacy_text, R.id.tv_about_ours, R.id.tv_exit})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_update_pwd://修改登录密码
                ActivityUtils.startActivity(UpdatePwdActivity.class);
                break;
            case R.id.tv_pwd_pay://支付密码
                ActivityUtils.startActivity(PwdPayActivity.class);
                break;
            case R.id.rl_third_login://微信登录账号绑定
                ActivityUtils.startActivity(ThirdLoginActivity.class);
                break;
            case R.id.tv_receiving_address://收货地址管理
                break;
            case R.id.tv_supplier_msg://供应商信息
                SupplierUtils.SupplierCustom(this,supplier_status,id);
                break;
            case R.id.tv_feedback://意见反馈
                ActivityUtils.startActivity(FeedbackActivity.class);
                break;
            case R.id.tv_register_agreement://用户协议
                bundle.putString("register", "register");
                ActivityUtils.startActivity(bundle, WebViewActivity.class);
                break;
            case R.id.tv_privacy_text://隐私协议
                bundle.putString("register", "privacy");
                ActivityUtils.startActivity(bundle, WebViewActivity.class);
                break;
            case R.id.tv_about_ours://关于
                ActivityUtils.startActivity(AboutOursActivity.class);
                break;
            case R.id.tv_exit://退出登录
                new CustomDialog(SetActivity.this)
                        .setTip(getString(R.string.exit_confirm))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                exitVm.exit();
                            }
                        }).showDialog();
                break;
        }
    }
}
