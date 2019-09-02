package com.shuangduan.zcy.view.recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.RechargeSuccessEvent;
import com.shuangduan.zcy.vm.RechargeVm;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recharge
 * @class describe  充值结果
 * @time 2019/8/13 15:52
 * @change
 * @chang time
 * @class describe
 */
public class RechargeResultActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_pay_style)
    TextView tvPayStyle;
    @BindView(R.id.tv_pay_result)
    TextView tvPayResult;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    private RechargeVm rechargeVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recharge_result;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.pay));

        rechargeVm = ViewModelProviders.of(this).get(RechargeVm.class);
        rechargeVm.resultLiveData.observe(this, rechargeResultBean -> {
            tvPayResult.setText(rechargeResultBean.getStatus() == 1? getString(R.string.recharge_success):getString(R.string.recharge_fail));
            tvPayStyle.setText(getIntent().getIntExtra(CustomConfig.PAY_STYLE, 2) == CustomConfig.PAY_STYLE_ALIPAY? getString(R.string.ali_pay):getString(R.string.wechat_pay));
            tvAmount.setText(String.format(getString(R.string.format_amount_rmb), getIntent().getStringExtra(CustomConfig.RECHARGE_AMOUNT)));
            if (rechargeResultBean.getStatus() == 1){
                EventBus.getDefault().post(new RechargeSuccessEvent());
            }
        });
        rechargeVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        rechargeVm.getResult(getIntent().getStringExtra(CustomConfig.ORDER_SN));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_done})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_done:
                finish();
                break;
        }
    }
}
