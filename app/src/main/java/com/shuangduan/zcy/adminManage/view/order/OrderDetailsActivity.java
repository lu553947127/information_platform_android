package com.shuangduan.zcy.adminManage.view.order;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.OrderDetailsBean;
import com.shuangduan.zcy.adminManage.vm.OrderDeviceVm;
import com.shuangduan.zcy.adminManage.vm.OrderTurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.KeyboardUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.view.order$
 * @class OrderDetails$
 * @class describe
 * @time 2019/11/15 10:46
 * @change
 * @class describe
 */
public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_supply_method)
    TextView tvSupplyMethod;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_lease_time)
    TextView tvLeaseTime;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;
    @BindView(R.id.tv_reserve_num)
    TextView tvReserveNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_subsidiary)
    TextView tvSubsidiary;
    @BindView(R.id.tv_subsidiary_name)
    TextView tvSubsidiaryName;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_order_info)
    TextView tvOrderInfo;
    @BindView(R.id.iv_default)
    ImageView ivDefault;
    @BindView(R.id.tv_replication)
    TextView tvReplication;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_number_value)
    TextView tvOrderNumberValue;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_create_time_value)
    TextView tvCreateTimeValue;
    @BindView(R.id.tv_order_phases)
    TextView tvOrderPhases;
    @BindView(R.id.tv_order_phases_value)
    TextView tvOrderPhasesValue;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_contact_value)
    TextView tvContactValue;
    @BindView(R.id.tv_contact_tel)
    TextView tvContactTel;
    @BindView(R.id.tv_contact_tel_value)
    TextView tvContactTelValue;
    @BindView(R.id.tv_buy_company)
    TextView tvBuyCompany;
    @BindView(R.id.tv_buy_company_value)
    TextView tvBuyCompanyValue;
    @BindView(R.id.tv_use_address)
    TextView tvUseAddress;
    @BindView(R.id.tv_use_address_value)
    TextView tvUseAddressValue;
    @BindView(R.id.tv_buy_remark)
    TextView tvBuyRemark;
    @BindView(R.id.tv_buy_remark_value)
    TextView tvBuyRemarkValue;
    @BindView(R.id.tv_reject)
    CheckBox tvReject;

    @BindView(R.id.group_visible)
    Group groupVisible;
    //订单ID
    private int orderId;
    //账号角色ID
    private int manageStatus;

    //订单类型 0:周转物资 1：设备
    private int orderType;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_admin_order_details;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.admin_order_details));

        orderId = getIntent().getIntExtra(CustomConfig.ADMIN_ORDER_ID, 0);
        manageStatus = getIntent().getIntExtra("manage_status", 0);
        orderType = getIntent().getIntExtra("order_type", 0);


        if (orderType == 0) {
            OrderTurnoverVm vm = ViewModelProviders.of(this).get(OrderTurnoverVm.class);
            vm.constructionOrderDetail(orderId);

            vm.orderDetailsLiveData.observe(this, orderItem -> {
                initViewData(orderItem);
            });
        } else {
            OrderDeviceVm vm = ViewModelProviders.of(this).get(OrderDeviceVm.class);
            vm.equipmentOrderDetail(orderId);
            vm.orderDetailsLiveData.observe(this, orderItem -> {
                initViewData(orderItem);
            });
        }
    }

    private void initViewData(OrderDetailsBean orderItem) {
        Glide.with(this).load(orderItem.images.get(0).url).into(ivIcon);
        tvTitle.setText(orderItem.materialIdName);
        if (orderItem.method == 1) {
            tvPrice.setText(Html.fromHtml("指导单价：<font color=\"#EF583E\">¥" + orderItem.price + "</font>/天"));
        } else {

            Spanned turnoverPrice = Html.fromHtml("指导单价：<font color=\"#EF583E\">¥" + orderItem.price + "</font>/" + orderItem.unit);

            Spanned devicePrice = Html.fromHtml("指导单价：<font color=\"#EF583E\">¥" + orderItem.price + "</font>");
            //周转材料显示单位，设备不显示单位
            tvPrice.setText(orderType==0?turnoverPrice:devicePrice);

        }
        tvSpec.setText(getString(R.string.format_admin_spec, orderItem.spec));
        tvCategory.setText(getString(R.string.format_admin_category, orderItem.categoryName));
        tvSupplyMethod.setText(orderItem.method == 1 ? getString(R.string.admin_turnover_add_lease) : getString(R.string.admin_turnover_add_sell));

        tvLeaseTime.setVisibility(orderItem.method ==1?View.VISIBLE:View.GONE);

        if (!StringUtils.isTrimEmpty(orderItem.leaseStartTime) && !StringUtils.isTrimEmpty(orderItem.leaseEndTime)) {
            tvLeaseTime.setText(getString(R.string.format_admin_lease_time, orderItem.leaseStartTime, orderItem.leaseEndTime));
        }
        //周转材料显示单位，设备不显示单位
        tvReserveNum.setText(orderType==0?orderItem.number + orderItem.unit:String.valueOf(orderItem.number));

        tvAddress.setText(getString(R.string.format_admin_address, orderItem.province + orderItem.city + orderItem.address));

        if (manageStatus == 3 || manageStatus == 5) {
            groupVisible.setVisibility(View.VISIBLE);
            tvSubsidiaryName.setText(orderItem.company);
        } else {
            groupVisible.setVisibility(View.GONE);
        }
        tvProjectName.setText(orderItem.unitName);
        tvOrderNumberValue.setText(orderItem.orderNumber);
        tvCreateTimeValue.setText(orderItem.createTime);

        tvOrderPhasesValue.setText(orderItem.phases);
        tvContactValue.setText(orderItem.realName);
        tvContactTelValue.setText(orderItem.tel);
        tvBuyCompanyValue.setText(orderItem.orderCompany);
        tvUseAddressValue.setText(orderItem.orderProvince + orderItem.orderCity + orderItem.orderAddress);
        tvBuyRemarkValue.setText(orderItem.remark);
        tvReject.setVisibility(orderItem.status == 3 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_replication})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_replication:
                KeyboardUtil.copyString(this, tvOrderNumberValue.getText().toString());
                ToastUtils.showShort(R.string.replication_success);
                break;
        }
    }
}
