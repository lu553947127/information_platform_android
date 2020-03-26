package com.shuangduan.zcy.adminManage.view.device;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.DeviceImageAdapter;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailBean;
import com.shuangduan.zcy.adminManage.vm.DeviceVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view.device
 * @ClassName: DeviceDetailActivity
 * @Description: 设备管理详情
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 16:10
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 16:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_stock_num)
    TextView tvStockNum;
    @BindView(R.id.tv_guide_price)
    TextView tvGuidePrice;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_use_status)
    TextView tvUseStatus;
    @BindView(R.id.tv_material_status)
    TextView tvMaterialStatus;
    @BindView(R.id.tv_device_coding_value)
    TextView tvDeviceCoding;
    @BindView(R.id.tv_put_address_value)
    TextView tvPutAddress;
    @BindView(R.id.tv_name_value)
    TextView tvName;
    @BindView(R.id.tv_tel_value)
    TextView tvTel;
    @BindView(R.id.tv_putaway_value)
    TextView tvPutaway;
    @BindView(R.id.tv_putaway_time_value)
    TextView tvPutawayTime;
    @BindView(R.id.tv_supply_method_value)
    TextView tvSupplyMethod;
    @BindView(R.id.tv_putaway_time_key)
    TextView tvPutawayTimeKey;
    @BindView(R.id.tv_supply_method_key)
    TextView tvSupplyMethodKey;
    @BindView(R.id.tv_material_photo)
    TextView tvMaterialPhoto;
    @BindView(R.id.rv_photo)
    RecyclerView rvImage;

    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.tv_main_params)
    TextView tvMainParams;
    @BindView(R.id.tv_power)
    TextView tvPower;
    @BindView(R.id.tv_original_price)
    TextView tvOriginalPrice;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_enter_time)
    TextView tvEnterTime;
    @BindView(R.id.tv_exit_time)
    TextView tvExitTime;
    @BindView(R.id.tv_material_status_name)
    TextView tvMaterialStatusName;
    @BindView(R.id.tv_entry_person)
    TextView tvEntryPerson;
    @BindView(R.id.tv_use_month_count)
    TextView tvUseMonthCount;
    @BindView(R.id.tv_technology_detail)
    TextView tvTechnologyDetail;
    @BindView(R.id.tv_equipment_time)
    TextView tvEquipmentTime;
    @BindView(R.id.tv_operator_name)
    TextView tvOperatorName;
    @BindView(R.id.tv_project_value)
    TextView tvProject;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_device_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.admin_device_material_details);

        DeviceVm deviceVm = getViewModel(DeviceVm.class);
        deviceVm.equipmentDetail(getIntent().getIntExtra(CustomConfig.EQIPMENT_ID, 0));
        deviceVm.deviceDetailLiveData.observe(this,deviceDetailBean -> {
            tvTitle.setText(deviceDetailBean.getMaterial_name());
            tvCategory.setText(deviceDetailBean.getCategory_name());
            tvStockNum.setText(deviceDetailBean.getStock());
            tvGuidePrice.setText(deviceDetailBean.getMethod() == 1 ?
                    String.format(getString(R.string.format_material_price), deviceDetailBean.getGuidance_price(), "天"):
                    String.format(getString(R.string.format_material_price_no_unit), deviceDetailBean.getGuidance_price()));
            tvSpec.setText(deviceDetailBean.getSpec());
            tvUseStatus.setText(StringUtils.isTrimEmpty(deviceDetailBean.getUse_status_name()) ? "无" : deviceDetailBean.getUse_status_name());
            tvMaterialStatus.setText(StringUtils.isTrimEmpty(deviceDetailBean.getMaterial_status_name()) ? "无" : deviceDetailBean.getMaterial_status_name());
            tvDeviceCoding.setText(deviceDetailBean.getEncoding());
            tvPutAddress.setText(deviceDetailBean.getProvince_name() + deviceDetailBean.getCity_name() + deviceDetailBean.getAddress());
            tvName.setText(deviceDetailBean.getPerson_liable());
            tvTel.setText(deviceDetailBean.getTel());
            tvPutaway.setText(deviceDetailBean.getIs_shelf_name());
            String shelf = deviceDetailBean.getShelf_type() == 1 ? "到期自动公开" : "到期自动下架";
            tvPutawayTime.setText(getString(R.string.format_admin_shelf_time, deviceDetailBean.getShelf_start_time(), deviceDetailBean.getShelf_end_time(), shelf));
            tvSupplyMethod.setText(deviceDetailBean.getMethod() == 1 ? "出租" : "出售");

            if (deviceDetailBean.getImages() != null && deviceDetailBean.getImages().size() > 0) {
                tvMaterialPhoto.setVisibility(View.VISIBLE);
                rvImage.setVisibility(View.VISIBLE);
                rvImage.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                rvImage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15_15));
                DeviceImageAdapter adapter = new DeviceImageAdapter(R.layout.item_turnover_image, null);
                adapter.setOnItemChildClickListener((adapter1, view, position) -> {
                    List<String> images = new ArrayList<>();
                    for (DeviceDetailBean.ImagesBean image : deviceDetailBean.getImages()) {
                        images.add(image.getUrl());
                    }
                    PictureEnlargeUtils.getPictureEnlargeList(this, images, position);
                });
                rvImage.setAdapter(adapter);
                adapter.setNewData(deviceDetailBean.getImages());
            }

            if (deviceDetailBean.getIs_shelf_name().equals("未上架")) {
                tvPutawayTimeKey.setVisibility(View.GONE);
                tvPutawayTime.setVisibility(View.GONE);
                tvSupplyMethodKey.setVisibility(View.GONE);
                tvSupplyMethod.setVisibility(View.GONE);
                tvGuidePrice.setVisibility(View.GONE);
            }

            if (deviceDetailBean.getIs_shelf_name().equals("公开上架")) {
                tvPutawayTimeKey.setVisibility(View.GONE);
                tvPutawayTime.setVisibility(View.GONE);
            }
             tvProject.setText( deviceDetailBean.getUnit_id_name());


             tvPlan.setText(StringUtils.isTrimEmpty(deviceDetailBean.getPlan()) ? "—" : deviceDetailBean.getPlan());
             tvBrand.setText(StringUtils.isTrimEmpty(deviceDetailBean.getBrand()) ? "—" : deviceDetailBean.getBrand());
             tvMainParams.setText(StringUtils.isTrimEmpty(deviceDetailBean.getMain_params()) ? "—" : deviceDetailBean.getMain_params());
             tvPower.setText(StringUtils.isTrimEmpty(deviceDetailBean.getPower()) ? "—" : deviceDetailBean.getPower());
             tvOriginalPrice.setText(StringUtils.isTrimEmpty(deviceDetailBean.getOriginal_price()) ? "—" : deviceDetailBean.getOriginal_price());
             tvStartTime.setText(StringUtils.isTrimEmpty(deviceDetailBean.getStart_date()) ? "—" : deviceDetailBean.getStart_date());
             tvEnterTime.setText(StringUtils.isTrimEmpty(deviceDetailBean.getEntry_time()) ? "—" : deviceDetailBean.getEntry_time());
             tvExitTime.setText(StringUtils.isTrimEmpty(deviceDetailBean.getExit_time()) ? "—" : deviceDetailBean.getExit_time());
             tvMaterialStatusName.setText(StringUtils.isTrimEmpty(deviceDetailBean.getMaterial_status_name()) ? "—" : deviceDetailBean.getMaterial_status_name());
             tvEntryPerson.setText(StringUtils.isTrimEmpty(deviceDetailBean.getUsername()) ? "—" : deviceDetailBean.getUsername());
             tvUseMonthCount.setText(StringUtils.isTrimEmpty(deviceDetailBean.getUse_month_count()) ? "—" : deviceDetailBean.getUse_month_count());
             tvTechnologyDetail.setText(StringUtils.isTrimEmpty(deviceDetailBean.getTechnology_detail()) ? "—" : deviceDetailBean.getTechnology_detail());
             tvEquipmentTime.setText(StringUtils.isTrimEmpty(deviceDetailBean.getEquipment_time()) ? "—" : deviceDetailBean.getEquipment_time());
            tvOperatorName.setText(StringUtils.isTrimEmpty(deviceDetailBean.getOperator_name()) ? "—" : deviceDetailBean.getOperator_name());
        });
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }
}
