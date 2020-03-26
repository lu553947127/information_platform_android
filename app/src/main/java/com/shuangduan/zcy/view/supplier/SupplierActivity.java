package com.shuangduan.zcy.view.supplier;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
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
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.utils.SupplierUtils;
import com.shuangduan.zcy.view.mine.set.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.SupplierVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.XEditText;

import java.util.Objects;

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
public class SupplierActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {
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
    @BindView(R.id.edt_keyword)
    XEditText edtKeyword;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;
    private SupplierVm supplierVm;
    private int supplier_status,id;

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

        //筛选条件列表为空
        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_supplier, R.string.see_all, this);

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

        supplierVm = getViewModel(SupplierVm.class);
        supplierVm.supplierLiveData.observe(this, supplierBean -> {
            adapter.setKeyword(edtKeyword.getText().toString());
            if (supplierBean.getPage() == 1) {
                adapter.setNewData(supplierBean.getList());
                adapter.setEmptyView(emptyView);
            } else {
                adapter.addData(supplierBean.getList());
            }
            setNoMore(supplierBean.getPage(), supplierBean.getCount());
        });

        //获取供应商审核状态
        supplierVm.supplierStatusLiveData.observe(this,supplierStatusBean -> {
            SPUtils.getInstance().put(CustomConfig.SUPPLIER_STATUS, supplierStatusBean.getStatus());
            supplier_status = supplierStatusBean.getStatus();
            id = supplierStatusBean.getId();
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                supplierVm.getMoreSupplier(edtKeyword.getText().toString());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                supplierVm.getSupplier(edtKeyword.getText().toString());
                supplierVm.supplierStatus();
            }
        });
        supplierVm.getSupplier(edtKeyword.getText().toString());
        supplierVm.supplierStatus();

        edtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                supplierVm.getSupplier(edtKeyword.getText().toString());
            }
        });

        edtKeyword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(edtKeyword.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(SupplierActivity.this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                supplierVm.getSupplier(edtKeyword.getText().toString());
                return true;
            }
            return false;
        });


        //支付密码状态查询
        updatePwdPayVm = getViewModel(UpdatePwdPayVm.class);
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

        coinPayVm = getViewModel(CoinPayVm.class);
        coinPayVm.supplierPayLiveData.observe(this, coinPayResultBean -> {
            if (coinPayResultBean.getPay_status() == 1) {
                ToastUtils.showShort(getString(R.string.buy_success));
                supplierVm.getSupplier(edtKeyword.getText().toString());
            } else {
                //余额不足
                addDialog(new CustomDialog(this)
                        .setIcon(R.drawable.icon_error)
                        .setTip(getString(R.string.no_balance))
                        .setOk(getString(R.string.recharge_dialog))
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
//                supplier_status = 0;
                SupplierUtils.SupplierCustom(this,supplier_status,id,"supplier");
                break;
        }
    }

    @Override
    public void onEmptyClick() {
        edtKeyword.getText().clear();
        supplierVm.getSupplier("");
    }
}
