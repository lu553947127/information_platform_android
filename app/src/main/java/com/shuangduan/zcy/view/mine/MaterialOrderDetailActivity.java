package com.shuangduan.zcy.view.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.OrderSearchBean;
import com.shuangduan.zcy.adminManage.vm.OrderTurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.weight.FlowViewHorizontal;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.fh_progress)
    FlowViewHorizontal flowViewHorizontal;
    @BindView(R.id.tv_reserve_num)
    TextView tvResrveNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private MaterialDetailVm materialVm;
    private OrderTurnoverVm orderVm;
    private int type,inside,method;
    private String phases;
    private List<OrderSearchBean.OrderPhasesBean> orderPhasesBeanList = new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_order_details;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        //订单Id
        int orderId = getIntent().getIntExtra(CustomConfig.ORDER_ID, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.my_material);

        type = getIntent().getIntExtra(CustomConfig.MATERIALS_TYPE, 0);

        tvUnit.setVisibility(type == CustomConfig.EQUIPMENT ? View.INVISIBLE : View.VISIBLE);


        materialVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialVm.orderDetailLiveData.observe(this, item -> {
            if (item == null) {
                return;
            }
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
            tvTitle.setText(item.materialName);
            if (type == CustomConfig.FRP) {
                tvPrice.setText(item.method == 1 ?
                        Html.fromHtml("商品单价：<font color=#EF583E>¥" + item.price + "<font/>/天") :
                        Html.fromHtml("商品单价：<font color=#EF583E>¥" + item.price + "<font/>/" + item.unit));

                tvResrveNum.setText(item.number + item.unit);
            } else if (type == CustomConfig.EQUIPMENT) {
                tvPrice.setText(item.method == 1 ?
                        Html.fromHtml("商品单价：<font color=#EF583E>¥" + item.price + "<font/>/天") :
                        Html.fromHtml("商品单价：<font color=#EF583E>¥" + item.price + "<font/>"));
                tvResrveNum.setText(item.number);
            }

            tvSpec.setText("规格：" + item.spec);
            tvSupplier.setText("供应商：" + item.supplierCompany);
            tvUnit.setText("单位：" + item.unit);

            tvAddress.setText("存放地：" + item.scienceAddress);

            tvBuyerValue.setText(item.user);
            tvContactValue.setText(item.realName);
            tvContactTelValue.setText(item.tel);
            tvOrderNumberValue.setText(item.orderNumber);
            tvOrderDateValue.setText(item.createTime);
            tvCompanyNameValue.setText(item.company);
            tvOrderAddressValue.setText(item.address);
            tvIntroduceValue.setText(item.remark);
            tvState.setText(item.phases);
            phases = item.phases;
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

            orderVm.orderSearch();
            inside = item.inside;
            method = item.method;
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

        orderVm = ViewModelProviders.of(this).get(OrderTurnoverVm.class);
        //获取搜索筛选条件数据
        orderVm.orderSearchLiveData.observe(this, orderSearchBean -> {
            List<String> list = new ArrayList<>();

            //非公开出售物资 没有投标报价
            if (inside == 1 && method == 2){
                orderPhasesBeanList = orderSearchBean.getOrder_phases();
            }else {
                orderPhasesBeanList = orderSearchBean.getOrder_phases();
                orderPhasesBeanList.remove(2);
            }

            //获取当前名称字符串列表
            int index = 0;
            for (int i = 0; i < orderPhasesBeanList.size(); i++) {
                list.add(orderPhasesBeanList.get(i).getName());
                //获取当前订单状态的角标值
                if (phases.equals(orderPhasesBeanList.get(i).getName())) {
                    index = i;
                }
            }
            //转换成数组
            String[] array = list.toArray(new String[list.size()]);
            flowViewHorizontal.setProgress(index + 1, orderPhasesBeanList.size(), array);
        });
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
            materialVm.materialOrderCancel(order.orderId);
        } else if (type == CustomConfig.EQUIPMENT) {
            materialVm.cancelEquipmentOrder(order.orderId);
        }
    }
}
