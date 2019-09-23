package com.shuangduan.zcy.view.mine;

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
import com.shuangduan.zcy.vm.TransRecordVm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  交易记录详情
 * @time 2019/8/16 8:42
 * @change
 * @chang time
 * @class describe
 */
public class TransRecordDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_in_out)
    TextView tvInOut;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.tv_trans_order_number)
    TextView tvTransOrderNumber;
    @BindView(R.id.tv_extra)
    TextView tvExtra;
    private TransRecordVm transRecordVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_trans_record_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.transaction_record));

        transRecordVm = ViewModelProviders.of(this).get(TransRecordVm.class);
        transRecordVm.detailLiveData.observe(this, transRecordDetailBean -> {
            if (transRecordDetailBean!=null){
                if (transRecordDetailBean.getType() == 1){
                    //收入
                    tvAmount.setTextColor(getResources().getColor(R.color.color_EF583E));
                    tvAmount.setText(String.format("+%1$s紫金币", transRecordDetailBean.getPrice()));
                    tvInOut.setText("收入");
                }else if (transRecordDetailBean.getType() == 2){
                    tvAmount.setTextColor(getResources().getColor(R.color.colorTv));
                    tvAmount.setText(String.format("-%1$s紫金币", transRecordDetailBean.getPrice()));
                    tvInOut.setText("支出");
                }
                tvTitle.setText(transRecordDetailBean.getTitle());
                tvType.setText(transRecordDetailBean.getType_arr());
                tvSource.setText(transRecordDetailBean.getFlow_types());
                tvTransOrderNumber.setText(transRecordDetailBean.getOrder_sn());
                tvExtra.setText(transRecordDetailBean.getRemark());
            }
        });
        transRecordVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        transRecordVm.idLiveData.postValue(getIntent().getIntExtra(CustomConfig.TRANS_RECORD_ID, 0));
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
