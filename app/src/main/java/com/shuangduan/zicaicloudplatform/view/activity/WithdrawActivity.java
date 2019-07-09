package com.shuangduan.zicaicloudplatform.view.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;

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
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.tv_withdrawal_amount)
    TextView tvWithdrawalAmount;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        tvWithdrawalAmount.setText(String.format(getString(R.string.format_withdraw_now), 10000));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                break;
            case R.id.tv_bar_right:
                break;
            case R.id.tv_confirm:
                break;
        }
    }

}
