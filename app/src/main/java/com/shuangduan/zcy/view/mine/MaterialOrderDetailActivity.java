package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.MaterialOrderAddressAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MaterialOrderDetailActivity$
 * @class 物资预定详情
 * @time 2019/9/25 20:02
 * @change
 * @class describe
 */
public class MaterialOrderDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_buyer_value)
    TextView tvBuyerValue;
    @BindView(R.id.tv_contact_value)
    TextView tvContactValue;
    @BindView(R.id.tv_contact_tel_value)
    TextView tvContactTelValue;
    @BindView(R.id.tv_order_number_value)
    TextView tvOrderNumberValue;
    @BindView(R.id.tv_order_date_value)
    TextView tvOrderDateValue;
    @BindView(R.id.tv_company_name_value)
    TextView tvCompanyNameValue;
    @BindView(R.id.tv_order_address_value)
    TextView tvOrderAddressValue;
    @BindView(R.id.tv_introduce_value)
    TextView tvIntroduceValue;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.iv_default)
    ImageView ivDefault;
    @BindView(R.id.tv_supply_method)
    TextView tvSupplyMethod;

    @BindView(R.id.iv_overrule)
    ImageView ivOverrule;
    @BindView(R.id.tv_lease_time)
    TextView tvLeaseTime;

    private MaterialDetailVm materialVm;
    private int orderId;
    private int type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_order_details;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        //订单Id
        orderId = getIntent().getIntExtra(CustomConfig.ORDER_ID, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.my_material);

        type = getIntent().getIntExtra(CustomConfig.MATERIALS_TYPE, 0);

        tvUnit.setVisibility(type == CustomConfig.EQUIPMENT ? View.INVISIBLE : View.VISIBLE);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        MaterialOrderAddressAdapter adapter = new MaterialOrderAddressAdapter(R.layout.item_material_details_address, null,type);
        adapter.setEmptyView(R.layout.layout_empty, rv);
        rv.setAdapter(adapter);

        materialVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialVm.orderDetailLiveData.observe(this, item -> {
            try {
                ImageLoader.load(this, new ImageConfig.Builder()
                        .url(item.images)
                        .imageView(ivIcon)
                        .placeholder(R.drawable.wuzhi_default)
                        .errorPic(R.drawable.wuzhi_default)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
                ivIcon.setImageResource(R.drawable.wuzhi_default);
            }
            tvTitle.setText(item.category);


            if (type == CustomConfig.FRP) {
                tvPrice.setText(item.method == 1 ?
                        Html.fromHtml("商品单价：<font color=#EF583E>" + item.price + "<font/>/天") :
                        Html.fromHtml("商品单价：<font color=#EF583E>" + item.price + "<font/>/" + item.unit));
            } else if (type == CustomConfig.EQUIPMENT) {
                tvPrice.setText(item.method == 1 ?
                        Html.fromHtml("商品单价：<font color=#EF583E>" + item.price + "<font/>/天") :
                        Html.fromHtml("商品单价：<font color=#EF583E>" + item.price + "<font/>" ));
            }

            tvSpec.setText("规格：" + item.spec);
            tvSupplier.setText("供应商：" + item.supplier);
            tvUnit.setText("单位：" + item.unit);
            adapter.setUnit(item.unit);
            adapter.setNewData(item.addressList);
            tvBuyerValue.setText(item.user);
            tvContactValue.setText(item.realName);
            tvContactTelValue.setText(item.tel);
            tvOrderNumberValue.setText(item.orderNumber);
            tvOrderDateValue.setText(item.createTime);
            tvCompanyNameValue.setText(item.company);
            tvOrderAddressValue.setText(item.address);
            tvIntroduceValue.setText(item.remark);
            tvState.setText("状态：" + item.phases);
            if (item.isClose == 0) {
                tvCancel.setEnabled(false);
            } else {
                tvCancel.setEnabled(true);
            }
            tvSupplyMethod.setText(item.method == 1 ? "出租" : "出售");

            ivDefault.setVisibility(item.inside == 3 ? View.VISIBLE : View.GONE);
            ivOverrule.setVisibility(item.status.equals("驳回") ? View.VISIBLE : View.INVISIBLE);

            tvLeaseTime.setVisibility(item.method == 1 ? View.VISIBLE : View.GONE);
            tvLeaseTime.setText(String.format(getResources().getString(R.string.format_material_lease_time), item.leaseStartTime, item.leaseEndTime));
        });

        materialVm.mutableLiveDataCancel.observe(this, item -> {
            ToastUtils.showShort("订单取消成功");
            tvCancel.setEnabled(false);
        });


        if (type == CustomConfig.FRP) {
            materialVm.materialOrderDetail(orderId);
        } else if (type == CustomConfig.EQUIPMENT) {
            materialVm.equipmentOrderDetail(orderId);
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_replication, R.id.tv_cancel})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_replication:
                KeyboardUtil.copyString(MaterialOrderDetailActivity.this, tvOrderNumberValue.getText().toString());
                ToastUtils.showShort(R.string.replication_success);
                break;
            case R.id.tv_cancel:
                new CustomDialog(this)
                        .setTip("是否取消预定订单")
                        .setOk("确认")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                cancelOrder();
                            }
                        }).showDialog();
                break;
        }
    }

    //取消预定订单
    private void cancelOrder() {
        MaterialOrderBean.ListBean order = materialVm.orderDetailLiveData.getValue();

        if (type == CustomConfig.FRP) {
            materialVm.materialOrderCancel(order.id);
        } else if (type == CustomConfig.EQUIPMENT) {
            materialVm.cancelEquipmentOrder(order.id);
        }

    }
}
