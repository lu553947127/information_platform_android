package com.shuangduan.zcy.view.material;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.event.AddressEvent;
import com.shuangduan.zcy.model.event.MaterialDetailEvent;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.release.ReleaseAreaSelectActivity;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import org.greenrobot.eventbus.Subscribe;
import butterknife.BindView;
import butterknife.OnClick;
import fj.edittextcount.lib.FJEditTextCount;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.material
 * @ClassName: MaterialPlaceOrderActivity
 * @Description: 物资提交预定页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 13:25
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 13:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("SetTextI18n")
public class MaterialEquipmentPlaceOrderActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_images)
    ImageView ivImages;
    @BindView(R.id.tv_material_category)
    TextView tvMaterialCategory;
    @BindView(R.id.tv_guidance_price)
    TextView tvGuidancePrice;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_remark)
    FJEditTextCount etRemark;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    @BindView(R.id.tv_supply_method)
    TextView tvSupplyMethod;
    @BindView(R.id.tv_address_star)
    TextView tvAddressStar;
    @BindView(R.id.tv_material_id)
    TextView tvMaterialId;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.ll_lease)
    LinearLayout llLease;

    private MaterialDetailVm materialDetailVm;
    int province, city, material_id, materialId, guidance_price, supplier_id, day;
    String material_name, unit;
    private int num, price;
    private MaterialDetailBean materialDetail;
    //租期开始时间  ,租期结束时间
    private String leaseStartTime, leaseEndTime;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_equipment_material_place_order;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.material_place_order));

        tvAddressStar.setVisibility(View.INVISIBLE);
        tvUnit.setVisibility(View.INVISIBLE);

        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);
        materialDetailVm.detailLiveData.observe(this, materialDetailBean -> {
            this.materialDetail = materialDetailBean;
            if (materialDetailBean.getImages() != null && materialDetailBean.getImages().size() != 0) {
                if (!TextUtils.isEmpty(materialDetailBean.getImages().get(0).getUrl())) {
                    ImageLoader.load(this, new ImageConfig.Builder()
                            .url(materialDetailBean.getImages().get(0).getUrl())
                            .placeholder(R.drawable.no_banner)
                            .errorPic(R.drawable.no_banner)
                            .imageView(ivImages)
                            .build());
                }
            }
            tvMaterialCategory.setText(materialDetailBean.getMaterial_category());
            tvSupplyMethod.setText(materialDetailBean.getMethod() == 1 ? "出租" : "出售");


            guidance_price = materialDetailBean.getGuidance_price();
            tvGuidancePrice.setText(materialDetailBean.getMethod() == 1 ?
                    String.format(getString(R.string.format_material_price), String.valueOf(guidance_price), "天") :
                    String.format(getString(R.string.format_material_price), String.valueOf(guidance_price), materialDetailBean.getUnit()));

            tvSpec.setText(String.format(getString(R.string.format_spec), materialDetailBean.getSpec()));
            unit = materialDetailBean.getUnit();
            tvUnit.setText("单位：" + materialDetailBean.getUnit());
            tvCompany.setText("供应商：" + materialDetailBean.getCompany());
            material_id = materialDetailBean.getMaterial_id();
            supplier_id = materialDetailBean.getSupplier_id();

            tvMaterialId.setText(materialDetail.getAddress());

            if (materialDetail.getMethod() == 1) {
                llLease.setVisibility(View.VISIBLE);
            } else {
                llLease.setVisibility(View.GONE);
            }

        });

        //预定订单提交成功返回结果
        materialDetailVm.mutableLiveAddOrder.observe(this, materialAddBean -> {
            Bundle bundle = new Bundle();
            bundle.putString("order_id", materialAddBean.getOrder_id());
            bundle.putInt(CustomConfig.MATERIALS_TYPE,CustomConfig.EQUIPMENT);
            ActivityUtils.startActivity(bundle, MaterialOrderSuccessActivity.class);
            finish();
        });

        etNum.addTextChangedListener(new TextWatcherWrapper() {

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isTrimEmpty(s.toString())) {
                    num = 0;
                    return;
                }
                num = Integer.valueOf(s.toString());
                if (materialDetail.getMethod() == 1) {
                    price = num * day * guidance_price;
                    tvNumber.setText("共租赁" + day + "天，共计");
                    tvPrice.setText(String.valueOf(price));
                } else {
                    price = num * guidance_price;
                    tvNumber.setText("共采购" + num + "套，共计");
                    tvPrice.setText(String.valueOf(price));
                }
            }
        });

        materialDetailVm.getEquipmentDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
    }


    @OnClick({R.id.iv_bar_back, R.id.tv_province, R.id.tv_submission,R.id.tv_time_start,R.id.tv_time_end})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_province://选择地区
                bundle.putInt(CustomConfig.PROJECT_ADDRESS, 2);
                ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                break;
            case R.id.tv_submission://提交预订单
                if (TextUtils.isEmpty(etRealName.getText().toString())) {
                    ToastUtils.showShort("联系人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etTel.getText().toString())) {
                    ToastUtils.showShort("电话不能为空");
                    return;
                }

                if (TextUtils.isEmpty(etCompany.getText().toString())) {
                    ToastUtils.showShort("公司名称不能为空");
                    return;
                }

                if (city == 0) {
                    ToastUtils.showShort("收货省市不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etAddress.getText().toString())) {
                    ToastUtils.showShort("详细地址不能为空");
                    return;
                }

                if (num == 0) {
                    ToastUtils.showShort("数量不能为0");
                    return;
                }

                if (materialDetail.getMethod() == 1 &&StringUtils.isTrimEmpty(leaseStartTime)) {
                    ToastUtils.showShort("请选择租赁开始时间");
                    return;
                }
                if (materialDetail.getMethod() == 1 &&StringUtils.isTrimEmpty(leaseEndTime)) {
                    ToastUtils.showShort("请选择租赁结束时间");
                    return;
                }

                if (materialDetail.getMethod() == 1 &&day <= 0) {
                    ToastUtils.showShort("开始时间必须小于结束时间");
                    return;
                }
                
                materialDetailVm.getAddEquipmentOrder(materialDetail.getId(), etRealName.getText().toString(), etTel.getText().toString()
                        , etCompany.getText().toString(), province, city, etAddress.getText().toString(), etRemark.getText(),
                        materialDetail.getMethod(),  num, materialDetail.getCategory(),leaseStartTime,leaseEndTime);
                break;
            case R.id.tv_time_start:
                showTimeDialog(tvTimeStart, 0);
                break;
            case R.id.tv_time_end:
                showTimeDialog(tvTimeEnd, 1);
                break;
        }
    }

    /**
     * 时间选择器
     */
    private void showTimeDialog(TextView tv, int type) {
        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            tv.setText(time);
            if (type == 0) leaseStartTime = time;
            else leaseEndTime = time;

            if (!StringUtils.isTrimEmpty(leaseStartTime) && !StringUtils.isTrimEmpty(leaseEndTime)) {
                day = DateUtils.getGapCount(leaseStartTime, leaseEndTime);

                if (materialDetail.getMethod() == 1) {
                    price = num * day * guidance_price;
                    tvNumber.setText("共租赁" + day + "天，共计");
                    tvPrice.setText(String.valueOf(price));
                } else {
                    price = num * guidance_price;
                    tvNumber.setText("共采购" + num + "套，共计");
                    tvPrice.setText(String.valueOf(price));
                }
            }
        }, "yyyy-MM-dd", "2010-01-01", "2040-12-31");
        customDatePicker.showSpecificTime(false);
        customDatePicker.show(TimeUtils.getNowString());
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventAddressEvent(AddressEvent event) {
        province = event.getProvinceId();
        city = event.getCityId();
        tvProvince.setText(event.getProvince() + event.getCity());
        tvProvince.setTextColor(ContextCompat.getColor(this, R.color.colorTv));
    }

    @Subscribe
    public void onEventUpdateMaterialDetail(MaterialDetailEvent event) {
        materialId = event.material_id;
        material_name = event.material_name;
    }
}
