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
import com.shuangduan.zcy.utils.AuthenticationUtils;
import com.shuangduan.zcy.vm.AuthenticationVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 余额
 * @time 2019/8/6 14:02
 * @change
 * @chang time
 * @class describe
 */
public class BalanceActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AuthenticationVm authenticationVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_balance;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.balance));

        authenticationVm = ViewModelProviders.of(this).get(AuthenticationVm.class);
        authenticationVm.authenticationStatusLiveData.observe(this, authenBean -> {
            switch (authenBean.getCard_status()) {
                case 1:
                    ToastUtils.showShort("审核中，请等待审核成功后进入");
                    break;
                case 2:
                    SPUtils.getInstance().put(SpConfig.IS_VERIFIED, authenBean.getCard_status());
                    ActivityUtils.startActivity(WithdrawActivity.class);
                    break;
                default:
                    //去认证
                    Bundle bundle = new Bundle();
                    bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                    ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                    finish();
                    break;
            }
        });
        authenticationVm.pageStateLiveData.observe(this, s -> {
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

    @OnClick({R.id.iv_bar_back, R.id.tv_loose_change_withdraw, R.id.tv_bank_card, R.id.tv_withdraw_record})
    void onClick(View view) {
        //验证身份信息
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_loose_change_withdraw:
                if (AuthenticationUtils.Authentication(CustomConfig.BALANCE_CASH)) {
                    ActivityUtils.startActivity(WithdrawActivity.class);
                }
                break;
            case R.id.tv_bank_card:
                if (AuthenticationUtils.Authentication(CustomConfig.BALANCE_BANK)) {
                    ActivityUtils.startActivity(BankCardListActivity.class);
                }
                break;
            case R.id.tv_withdraw_record:
                if (AuthenticationUtils.Authentication(CustomConfig.BALANCE_CASH_RECORD)) {
                    ActivityUtils.startActivity(WithdrawRecordActivity.class);
                }
                break;
        }

    }
}
