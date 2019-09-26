package com.shuangduan.zcy.view.demand;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.BuyerDetailAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.BuyerDetailBean;
import com.shuangduan.zcy.model.bean.CoinPayResultBean;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.DemandBuyerVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  找买家详情
 * @time 2019/8/21 17:17
 * @change
 * @chang time
 * @class describe
 */
public class FindBuyerDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_material_name)
    TextView tvMaterialName;
    @BindView(R.id.tv_supply_num)
    TextView tvSupplyNum;
    @BindView(R.id.tv_supply_style)
    TextView tvSupplyStyle;
    @BindView(R.id.tv_supply_price)
    TextView tvSupplyPrice;
    @BindView(R.id.tv_owner)
    TextView tvOwner;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_read_detail)
    TextView tvReadDetail;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_supply_address)
    TextView tvSupplyAddress;
    @BindView(R.id.rv)
    RecyclerView rv;
    private DemandBuyerVm demandBuyerVm;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_buyer_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.find_buyer_detail));

        initPay();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        BuyerDetailAdapter buyerAdapter = new BuyerDetailAdapter(R.layout.item_buyer_detail, null);
        buyerAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(buyerAdapter);
        buyerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BuyerDetailBean.ListBean listBean = buyerAdapter.getData().get(position);
            coinPayVm.findSubstanceId = listBean.getId();
            demandBuyerVm.currentPay = 1;
            showPayDialog(listBean.getPrice());
        });

        demandBuyerVm = ViewModelProviders.of(this).get(DemandBuyerVm.class);
        demandBuyerVm.id = getIntent().getIntExtra(CustomConfig.DEMAND_ID, 0);
        demandBuyerVm.detailLiveData.observe(this, buyerDetailBean -> {
            BuyerDetailBean.InfoBean info = buyerDetailBean.getInfo();
            tvMaterialName.setText(info.getMaterial_name());
            tvSupplyNum.setText(info.getCount());
            tvSupplyStyle.setText(info.getWay() == 1? getString(R.string.sell): getString(R.string.lease));
            tvSupplyPrice.setText(info.getAcceptance_price()+"万元");
            tvOwner.setText(info.getReal_name());
            tvContact.setText(info.getTel());
            tvReadDetail.setVisibility(info.getIs_pay() != 1? View.VISIBLE: View.INVISIBLE);
            tvTime.setText(String.format(getString(R.string.format_validity_period_less), info.getStart_time(), info.getEnd_time()));
            tvSupplyAddress.setText(info.getAddress());
            buyerAdapter.setNewData(buyerDetailBean.getList());
            buyerAdapter.setEmptyView(R.layout.layout_empty, rv);
        });

        demandBuyerVm.getDetail();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_read_detail})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_read_detail:
                coinPayVm.findBuyerId = getIntent().getIntExtra(CustomConfig.DEMAND_ID, 0);
                demandBuyerVm.currentPay = 2;
                showPayDialog(demandBuyerVm.detailLiveData.getValue().getInfo().getPrice());
                break;
        }
    }

    private void initPay(){
        //支付密码状态查询
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.stateLiveData.observe(this, pwdPayStateBean -> {
            int status = pwdPayStateBean.getStatus();
            SPUtils.getInstance().put(SpConfig.PWD_PAY_STATUS, status);
            if (status == 1){
                goToPay();
            }else {
                ActivityUtils.startActivity(SetPwdPayActivity.class);
            }
        });
        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });


        coinPayVm = ViewModelProviders.of(this).get(CoinPayVm.class);
        coinPayVm.findSubstancePayLiveData.observe(this, this::payResult);
        coinPayVm.findBuyerPayLiveData.observe(this, this::payResult);
        coinPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    private void showPayDialog(String price){
        addDialog(new CustomDialog(this)
                .setTip(String.format(getString(R.string.format_pay_price), price))
                .setCallBack(new BaseDialog.CallBack() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok(String s) {
                        int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
                        if (status == 1){
                            goToPay();
                        }else {
                            //查询是否设置支付密码
                            updatePwdPayVm.payPwdState();
                        }
                    }
                })
                .showDialog());
    }

    /**
     * 去支付
     */
    private void goToPay(){
        addDialog(new PayDialog(this)
                .setSingleCallBack((item, position) -> {
                    if (demandBuyerVm.currentPay == 1){
                        coinPayVm.payFindSubstance(item);
                    }else if (demandBuyerVm.currentPay == 2){
                        coinPayVm.payFindBuyer(item);
                    }
                })
                .showDialog());
    }

    private void payResult(CoinPayResultBean coinPayResultBean){
        if (coinPayResultBean.getPay_status() == 1){
            ToastUtils.showShort(getString(R.string.buy_success));
            demandBuyerVm.getDetail();
        }else {
            //余额不足
            addDialog(new CustomDialog(this)
                    .setIcon(R.drawable.icon_error)
                    .setTip("余额不足")
                    .setCallBack(new BaseDialog.CallBack() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void ok(String s) {
                            ActivityUtils.startActivity(RechargeActivity.class);
                        }
                    })
                    .showDialog());
        }
    }
}
