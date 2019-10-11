package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.AuthenticationUtils;

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
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_loose_change_withdraw, R.id.tv_bank_card, R.id.tv_withdraw_record})
    void onClick(View view) {
        //验证身份信息
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_loose_change_withdraw://提现
                if (!AuthenticationUtils.Authentication(CustomConfig.BALANCE_CASH)) return;
                ActivityUtils.startActivity(WithdrawActivity.class);
                break;
            case R.id.tv_bank_card://银行卡
                ActivityUtils.startActivity(BankCardListActivity.class);
                break;
            case R.id.tv_withdraw_record://提现记录
                ActivityUtils.startActivity(WithdrawRecordActivity.class);
                break;
        }
    }
}
