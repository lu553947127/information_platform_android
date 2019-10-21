package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.AuthenticationUtils;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.NumberAnimTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 我的钱包
 * @time 2019/8/6 14:02
 * @change
 * @chang time
 * @class describe
 */
public class MineWalletActivity extends BaseActivity {

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.tv_balance)
    NumberAnimTextView tvBalance;
    private UserInfoVm userInfoVm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_wallet;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.getInfoLiveData.observe(this, userInfoBean -> {
            tvBalance.setNumberString(userInfoBean.getCoin());
            tvBalance.setDuration(500);
        });
        refresh.setEnableLoadMore(false);
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                userInfoVm.userInfo();
                refreshLayout.finishRefresh(1000);
            }
        });
        userInfoVm.userInfo();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_withdraw_record, R.id.tv_loose_change_withdraw,R.id.tv_recharge, R.id.tv_bank_card,R.id.tv_transaction_record})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back://返回
                finish();
                break;
            case R.id.tv_withdraw_record://提现记录
                ActivityUtils.startActivity(WithdrawRecordActivity.class);
                break;
            case R.id.tv_loose_change_withdraw://提现
                if (!AuthenticationUtils.AuthenticationCustom(this,CustomConfig.BALANCE_CASH)) return;
                ActivityUtils.startActivity(WithdrawActivity.class);
                break;
            case R.id.tv_recharge://充值
                ActivityUtils.startActivity(RechargeActivity.class);
                break;
            case R.id.tv_bank_card://银行卡
                if (!AuthenticationUtils.AuthenticationCustom(this,CustomConfig.BALANCE_BANK)) return;
                ActivityUtils.startActivity(BankCardListActivity.class);
                break;
            case R.id.tv_transaction_record://交易记录
                ActivityUtils.startActivity(TransRecordActivity.class);
                break;
        }
    }
}
