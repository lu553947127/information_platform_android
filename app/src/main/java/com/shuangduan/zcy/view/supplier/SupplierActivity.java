package com.shuangduan.zcy.view.supplier;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SupplierAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.SupplierVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.supplier
 * @class describe  优质供应商
 * @time 2019/8/12 9:37
 * @change
 * @chang time
 * @class describe
 */
public class SupplierActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    TextView tvBarRight;
    @BindView(R.id.iv_bar_right)
    ImageView ivBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;
    private int supplier_status;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_supplier;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.supplier));
        tvBarRight.setVisibility(View.GONE);
        ivBarRight.setImageResource(R.drawable.add_white);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        SupplierAdapter adapter = new SupplierAdapter(R.layout.item_supplier, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            SupplierBean.ListBean listBean = adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_read:
                    coinPayVm.supplierId = listBean.getId();
                    addDialog(new CustomDialog(this)
                            .setTip(String.format(getString(R.string.format_pay_price), listBean.getDetail_price()))
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
                    break;
            }
        });
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            SupplierBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.SUPPLIER_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, SupplierDetailActivity.class);
        });

        SupplierVm supplierVm = ViewModelProviders.of(this).get(SupplierVm.class);
        supplierVm.supplierLiveData.observe(this, supplierBean -> {
            if (supplierBean.getPage() == 1) {
                adapter.setNewData(supplierBean.getList());
                adapter.setEmptyView(R.layout.layout_empty, rv);
            } else {
                adapter.addData(supplierBean.getList());
            }
            setNoMore(supplierBean.getPage(), supplierBean.getCount());
        });

        //获取供应商审核状态
        supplierVm.supplierStatusLiveData.observe(this,supplierStatusBean -> {
            SPUtils.getInstance().put(CustomConfig.SUPPLIER_STATUS, supplierStatusBean.getStatus());
            supplier_status = supplierStatusBean.getStatus();
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                supplierVm.getMoreSupplier();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                supplierVm.getSupplier();
                supplierVm.supplierStatus();
            }
        });
        supplierVm.getSupplier();
        supplierVm.supplierStatus();


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
        coinPayVm.supplierPayLiveData.observe(this, coinPayResultBean -> {
            if (coinPayResultBean.getPay_status() == 1) {
                ToastUtils.showShort(getString(R.string.buy_success));
                supplierVm.getSupplier();
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
        });
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

    private void setNoMore(int page, int count) {
        if (page == 1) {
            if (page * 10 >= count) {
                if (refresh.getState() == RefreshState.None) {
                    refresh.setNoMoreData(true);
                } else {
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                refresh.finishRefresh();
            }
        } else {
            if (page * 10 >= count) {
                refresh.finishLoadMoreWithNoMoreData();
            } else {
                refresh.finishLoadMore();
            }
        }
    }

    /**
     * 去支付
     */
    private void goToPay() {
        addDialog(new PayDialog(this)
                .setSingleCallBack((item, position) -> {
                    coinPayVm.paySupplier(item);
                })
                .showDialog());
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                supplier_status = 3;
                switch (supplier_status){
                    case 0://未申请
                        ActivityUtils.startActivity(SupplierJoinActivity.class);
                        break;
                    case 1://未通过
                        new CustomDialog(this)
                                .setTipLeftIcon(R.drawable.icon_error,"抱歉，您的审核未通过，\n请核对后重新提交！",R.color.colorTv)
                                .setOk("重新申请")
                                .setCallBack(new BaseDialog.CallBack() {
                                    @Override
                                    public void cancel() {

                                    }

                                    @Override
                                    public void ok(String s) {
                                        ActivityUtils.startActivity(SupplierJoinActivity.class);
                                    }
                                }).showDialog();
                        break;
                    case 2://已通过
                        new CustomDialog(this)
                                .setTipLeftIcon(R.drawable.icon_success,"恭喜，您已成为供应商！",R.color.colorTv)
                                .setOk("查看")
                                .setCallBack(new BaseDialog.CallBack() {
                                    @Override
                                    public void cancel() {

                                    }

                                    @Override
                                    public void ok(String s) {

                                    }
                                }).showDialog();
                        break;
                    case 3://驳回
                        new CustomDialog(this)
                                .setTipLeftIcon(R.drawable.icon_reject_supplier,"我们正在加急核对您的申请，\n请耐心等待！",R.color.colorTv)
                                .setOk("确定")
                                .setCallBack(new BaseDialog.CallBack() {
                                    @Override
                                    public void cancel() {

                                    }

                                    @Override
                                    public void ok(String s) {

                                    }
                                }).showDialog();
                        break;
                }
                break;
        }
    }
}
