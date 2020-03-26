package com.shuangduan.zcy.view.mine.wallet;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.WithdrawVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 提现记录详情页
 * @time 2019/8/6 17:48
 * @change
 * @chang time
 * @class describe
 */
public class WithdrawDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_trans_order_number)
    TextView tvTransOrderNumber;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_state)
    TextView tvState;
    private WithdrawVm withdrawVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_withdraw_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.withdraw_record));

        withdrawVm = getViewModel(WithdrawVm.class);
        withdrawVm.recordDetailLiveData.observe(this, detailBean -> {
            tvAmount.setText(String.format(getString(R.string.format_amount), detailBean.getPrice()));
            tvTime.setText(detailBean.getCreate_time());
            tvTransOrderNumber.setText(detailBean.getOrder_sn());
            tvAccount.setText(detailBean.getBankcard_type());
            tvState.setText(getResources().getStringArray(R.array.withdraw_state)[detailBean.getStatus() - 1]);
        });
        withdrawVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        withdrawVm.getDetail(getIntent().getIntExtra(CustomConfig.WITHDRAW_RECORD_ID, 0));
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
