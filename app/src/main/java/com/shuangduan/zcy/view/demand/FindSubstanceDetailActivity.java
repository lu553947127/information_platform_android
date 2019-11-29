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
import com.shuangduan.zcy.adapter.SubstanceDetailAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CoinPayResultBean;
import com.shuangduan.zcy.model.bean.SubstanceDetailBean;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.DemandSubstanceVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  找物资详情
 * @time 2019/8/21 8:49
 * @change
 * @chang time
 * @class describe
 */
public class FindSubstanceDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_material_name)
    TextView tvMaterialName;
    @BindView(R.id.tv_demand_num)
    TextView tvDemandNum;
    @BindView(R.id.tv_demand_project)
    TextView tvDemandProject;
    @BindView(R.id.tv_project_address)
    TextView tvProjectAddress;
    @BindView(R.id.tv_price_accept)
    TextView tvPriceAccept;
    @BindView(R.id.tv_owner)
    TextView tvOwner;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_read_detail)
    TextView tvReadDetail;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_details_content)
    TextView tvDetailsContent;

    private DemandSubstanceVm demandSubstanceVm;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_substance_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_recommend_substance_info, 0, null);
        tvBarTitle.setText(getString(R.string.find_substance_detail));

        initPay();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        SubstanceDetailAdapter subOrderAdapter = new SubstanceDetailAdapter(R.layout.item_substance_detail, null);
        subOrderAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(subOrderAdapter);
        subOrderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SubstanceDetailBean.ListBean listBean = subOrderAdapter.getData().get(position);
            coinPayVm.findBuyerId = listBean.getId();
            demandSubstanceVm.currentPay = 2;
            showPayDialog(listBean.getPrice());
        });

        demandSubstanceVm = ViewModelProviders.of(this).get(DemandSubstanceVm.class);
        demandSubstanceVm.id = getIntent().getIntExtra(CustomConfig.DEMAND_ID, 0);
        demandSubstanceVm.detailLiveData.observe(this, substanceDetailBean -> {
            SubstanceDetailBean.InfoBean info = substanceDetailBean.getInfo();
            tvMaterialName.setText(info.getMaterial_name());
            tvDemandNum.setText(info.getCount());
            tvDemandProject.setText(info.getProject_name());
            tvProjectAddress.setText(info.getAddress());
            if (info.getAcceptance_price().equals("面议")) {
                tvPriceAccept.setText(info.getAcceptance_price());
            } else {
                tvPriceAccept.setText(info.getAcceptance_price() + "元");
            }
            tvOwner.setText(info.getReal_name());
            tvContact.setText(info.getTel());
            tvReadDetail.setVisibility(info.getIs_pay() != 1 ? View.VISIBLE : View.INVISIBLE);
            tvTime.setText(String.format(getString(R.string.format_validity_period_less), info.getStart_time(), info.getEnd_time()));
            tvDetailsContent.setText(info.getRemark());
            subOrderAdapter.setNewData(substanceDetailBean.getList());
            subOrderAdapter.setEmptyView(emptyView);
        });

        demandSubstanceVm.getDetail();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_read_detail})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_read_detail:
                coinPayVm.findSubstanceId = getIntent().getIntExtra(CustomConfig.DEMAND_ID, 0);
                demandSubstanceVm.currentPay = 1;
                showPayDialog(demandSubstanceVm.detailLiveData.getValue().getInfo().getPrice());
                break;
        }
    }

    private void initPay() {
        //支付密码状态查询
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.stateLiveData.observe(this, pwdPayStateBean -> {
            int status = pwdPayStateBean.getStatus();
            SPUtils.getInstance().put(SpConfig.PWD_PAY_STATUS, status);
            if (status == 1) {
                goToPay();
            } else {
                ActivityUtils.startActivity(SetPwdPayActivity.class);
            }
        });
        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
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

    private void showPayDialog(String price) {
        addDialog(new CustomDialog(this)
                .setTip(String.format(getString(R.string.format_pay_price), price))
                .setCallBack(new BaseDialog.CallBack() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok(String s) {
                        int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
                        if (status == 1) {
                            goToPay();
                        } else {
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
    private void goToPay() {
        addDialog(new PayDialog(this)
                .setSingleCallBack((item, position) -> {
                    if (demandSubstanceVm.currentPay == 1) {
                        coinPayVm.payFindSubstance(item);
                    } else if (demandSubstanceVm.currentPay == 2) {
                        coinPayVm.payFindBuyer(item);
                    }
                })
                .showDialog());
    }

    private void payResult(CoinPayResultBean coinPayResultBean) {
        if (coinPayResultBean.getPay_status() == 1) {
            ToastUtils.showShort(getString(R.string.buy_success));
            demandSubstanceVm.getDetail();
        } else {
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
