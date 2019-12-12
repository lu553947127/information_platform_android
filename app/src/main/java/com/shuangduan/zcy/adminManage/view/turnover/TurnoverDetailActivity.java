package com.shuangduan.zcy.adminManage.view.turnover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.TurnoverImageAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailBean;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
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
 * @Package: com.shuangduan.zcy.adminManage.view
 * @ClassName: TurnoverDetailActivity
 * @Description: 周转材料详情
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/30 14:48
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/30 14:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverDetailActivity extends BaseActivity {
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
    @BindView(R.id.rv_photo)
    RecyclerView rvImage;

    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_enter_time)
    TextView tvEnterTime;
    @BindView(R.id.tv_exit_time)
    TextView tvExitTime;
    @BindView(R.id.tv_amortize)
    TextView tvAmortize;
    @BindView(R.id.tv_original)
    TextView tvOriginal;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_entry_person)
    TextView tvEntryPerson;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_material_photo)
    TextView tvMaterialPhoto;
    @BindView(R.id.tv_putaway_time_key)
    TextView tvPutawayTimeKey;
    @BindView(R.id.tv_supply_method_key)
    TextView tvSupplyMethodKey;
    @BindView(R.id.group)
    Group group;
    @BindView(R.id.tv_project_value)
    TextView tvProjectValue;
    @BindView(R.id.tv_vulnerable_value)
    TextView tvVulnerableValue;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_turnover_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint({"NewApi", "SetTextI18n"})
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.admin_turnover_material_details);

        int constructionId = getIntent().getIntExtra(CustomConfig.CONSTRUCTION_ID, 0);

        TurnoverVm turnoverDetailVm = ViewModelProviders.of(this).get(TurnoverVm.class);
        turnoverDetailVm.getTurnoverDetail(constructionId);
        turnoverDetailVm.turnoverDetailLiveData.observe(this, turnover -> {
            tvTitle.setText(turnover.materialIdName);
            tvCategory.setText(turnover.categoryName);
            tvStockNum.setText(turnover.stock+turnover.unitName);
            tvGuidePrice.setText(turnover.guidancePrice+"/"+turnover.unitName);
            tvSpec.setText(turnover.spec);
            tvUseStatus.setText(StringUtils.isTrimEmpty(turnover.useStatusName) ? "无" : turnover.useStatusName);
            tvMaterialStatus.setText(StringUtils.isTrimEmpty(turnover.materialStatusName) ? "无" : turnover.materialStatusName);
            tvPutAddress.setText(turnover.provinceName + turnover.cityName + turnover.address);
            tvName.setText(turnover.personLiable);
            tvVulnerableValue.setText(turnover.rapidWear);
            tvTel.setText(turnover.tel);
            tvPutaway.setText(turnover.isShelfName);
            String shelf = turnover.shelfType == 1 ? "到期自动公开" : "到期自动下架";
            tvPutawayTime.setText(getString(R.string.format_admin_shelf_time, turnover.shelfStartTime, turnover.shelfEndTime, shelf));
            tvSupplyMethod.setText(turnover.method == 1 ? "出租" : "出售");
            tvProjectValue.setText(turnover.unitIdName);


            tvPlan.setText(StringUtils.isTrimEmpty(turnover.planName) ? "—" : turnover.planName);
            tvNum.setText(turnover.useCount.equals("0") ? "—" : turnover.useCount);
            tvStartTime.setText(StringUtils.isTrimEmpty(turnover.startDate) ? "—" : turnover.startDate);
            tvEnterTime.setText(StringUtils.isTrimEmpty(turnover.entryTime) ? "—" : turnover.entryTime);
            tvExitTime.setText(StringUtils.isTrimEmpty(turnover.exitTime) ? "—" : turnover.exitTime);
            tvAmortize.setText(turnover.accumulatedAmortization.equals("0") ? "—" : turnover.accumulatedAmortization);
            tvOriginal.setText(turnover.originalPrice.equals("0") ? "—" : turnover.originalPrice);
            tvValue.setText(turnover.netWorth.equals("0") ? "—" : turnover.netWorth);

            tvEntryPerson.setText(StringUtils.isTrimEmpty(turnover.username) ? "—" : turnover.username);

            tvRemark.setText(StringUtils.isTrimEmpty(turnover.remark) ? "—" : turnover.remark);

            if (turnover.images != null && turnover.images.size() > 0) {
                tvMaterialPhoto.setVisibility(View.VISIBLE);
                rvImage.setVisibility(View.VISIBLE);
                rvImage.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                rvImage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15_15));
                TurnoverImageAdapter adapter = new TurnoverImageAdapter(R.layout.item_turnover_image, null);
                adapter.setOnItemChildClickListener((adapter1, view, position) -> {
                    List<String> images = new ArrayList<>();
                    for (TurnoverDetailBean.Images image : turnover.images) {
                        images.add(image.url);
                    }
                    PictureEnlargeUtils.getPictureEnlargeList(this, images, position);
                });
                rvImage.setAdapter(adapter);
                adapter.setNewData(turnover.images);
            }

            if (turnover.isShelfName.equals("未上架")) {
                tvPutawayTimeKey.setVisibility(View.GONE);
                tvPutawayTime.setVisibility(View.GONE);
                tvSupplyMethodKey.setVisibility(View.GONE);
                tvSupplyMethod.setVisibility(View.GONE);
                group.setVisibility(View.GONE);
            }

            if (turnover.isShelfName.equals("公开上架")) {
                tvPutawayTimeKey.setVisibility(View.GONE);
                tvPutawayTime.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }
}
