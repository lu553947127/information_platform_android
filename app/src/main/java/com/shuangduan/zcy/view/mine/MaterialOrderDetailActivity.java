package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.MaterialOrderAdapter;
import com.shuangduan.zcy.adapter.MaterialOrderAddressAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.MaterialVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MaterialOrderDetailActivity$
 * @class describe
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

    private MaterialVm materialVm;

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
        int orderId = getIntent().getIntExtra(CustomConfig.ORDER_ID, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.my_material);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        MaterialOrderAddressAdapter adapter = new MaterialOrderAddressAdapter(R.layout.item_material_details_address, null);
        adapter.setEmptyView(R.layout.layout_empty, rv);
        rv.setAdapter(adapter);

        materialVm = ViewModelProviders.of(this).get(MaterialVm.class);
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
            tvPrice.setText(Html.fromHtml("<b>商品单价:<font color=#EF583E>" + item.price + "<font/>/ " + item.unit + "</b>"));
            tvSpec.setText("规格: " + item.spec);
            tvSupplier.setText("供应商: " + item.supplier);
            tvUnit.setText("单位: " + item.unit);
            adapter.setNewData(item.addressList);
            tvBuyerValue.setText(item.user);
            tvContactValue.setText(item.realName);
            tvContactTelValue.setText(item.tel);
            tvOrderNumberValue.setText(item.orderNumber);
            tvOrderDateValue.setText(item.createTime);
            tvCompanyNameValue.setText(item.company);
            tvOrderAddressValue.setText(item.address);
            tvIntroduceValue.setText(item.remark);
        });


        materialVm.materialOrderDetail(orderId);
    }
}
