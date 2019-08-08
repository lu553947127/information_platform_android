package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BankCardDialog;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.event.BankcardUpdateEvent;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.vm.WithdrawVm;
import com.shuangduan.zcy.weight.MoneyTextWatcher;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  提现
 * @time 2019/7/8 9:01
 * @change
 * @chang time
 * @class describe
 */
public class WithdrawActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.tv_withdrawal_amount)
    TextView tvWithdrawalAmount;
    @BindView(R.id.tv_bank_card)
    TextView tvBankCard;
    private WithdrawVm withdrawVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.withdraw));
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        edtMoney.addTextChangedListener(new MoneyTextWatcher(edtMoney).setDigits(2));

        withdrawVm = ViewModelProviders.of(this).get(WithdrawVm.class);
        withdrawVm.withdrawLiveData.observe(this, withdrawBean -> {
            tvWithdrawalAmount.setText(String.format(getString(R.string.format_withdraw_now), withdrawBean.getCoin()));
        });
        withdrawVm.bankcardLiveData.observe(this, bankCardBeans -> {
            if (bankCardBeans == null || bankCardBeans.size() == 0){
                tvBankCard.setText(getString(R.string.please_bind_bank_card));
            }else {
                tvBankCard.setText(bankCardBeans.get(0).getType_name());
                withdrawVm.withdrawBankcardId = bankCardBeans.get(0).getId();
            }
        });
        withdrawVm.operateLiveData.observe(this, o -> {
            ToastUtils.showShort("提现申请已提交审核");
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
        withdrawVm.withdrawMsg();
        withdrawVm.bankcardList();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bank_card, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bank_card:
                List<BankCardBean> bankCardBeans = withdrawVm.bankcardLiveData.getValue();
                if (bankCardBeans == null || bankCardBeans.size() == 0){
                    //去绑定
                    ActivityUtils.startActivity(BindBankCardActivity.class);
                }else {
                    //展示选择弹窗
                    addDialog(new BankCardDialog(this)
                            .setList(bankCardBeans)
                            .setSingleCallBack((item, position) -> {
                                tvBankCard.setText(item);
                                withdrawVm.withdrawBankcardId = bankCardBeans.get(position).getId();
                            })
                            .showDialog());
                }
                break;
            case R.id.tv_confirm:
                if (StringUtils.isTrimEmpty(edtMoney.getText().toString())){
                    ToastUtils.showShort("输入具体提现金额");
                    return;
                }
                withdrawVm.withdraw(edtMoney.getText().toString());
                break;
        }
    }

    @Subscribe()
    public void updateBankcard(BankcardUpdateEvent event){
        withdrawVm.bankcardList();
    }

}
