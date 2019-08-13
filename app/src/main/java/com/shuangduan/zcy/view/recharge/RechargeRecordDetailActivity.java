package com.shuangduan.zcy.view.recharge;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.RechargeRecordVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recharge
 * @class describe  充值记录详情
 * @time 2019/8/13 17:37
 * @change
 * @chang time
 * @class describe
 */
public class RechargeRecordDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_trans_order_number)
    TextView tvTransOrderNumber;
    @BindView(R.id.tv_pay_style)
    TextView tvPayStyle;
    @BindView(R.id.tv_state)
    TextView tvState;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recharge_record_detail;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.recharge_record));

        RechargeRecordVm rechargeRecordVm = ViewModelProviders.of(this).get(RechargeRecordVm.class);
        rechargeRecordVm.detailLiveData.observe(this, rechargeRecordDetailBean -> {
            tvAmount.setText(String.format(getString(R.string.format_amount_bi), rechargeRecordDetailBean.getCoin()));
            tvPrice.setText(String.format(getString(R.string.format_amount), rechargeRecordDetailBean.getPrice()));
            tvTime.setText(rechargeRecordDetailBean.getCreate_time());
            tvTransOrderNumber.setText(rechargeRecordDetailBean.getOrder_sn());
            tvPayStyle.setText(rechargeRecordDetailBean.getPay_method());
            tvState.setText(getResources().getStringArray(R.array.success_fail)[0]);
        });
        rechargeRecordVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        rechargeRecordVm.getDetail(getIntent().getIntExtra(CustomConfig.RECHARGE_ID, 0));
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
