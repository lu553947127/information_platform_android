package com.shuangduan.zcy.view.material;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.SupplierCliqueBean;
import com.shuangduan.zcy.model.event.MaterialEvent;
import com.shuangduan.zcy.model.event.SupplierEvent;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.vm.MaterialVm;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.material
 * @class describe  基建物资
 * @time 2019/8/7 10:25
 * @change
 * @chang time
 * @class describe
 */
public class MaterialActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_material)
    Toolbar toolbarMaterial;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_default)
    TextView tvDefault;


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.over)
    View over;
    @BindView(R.id.cl_filter)
    ConstraintLayout clFilter;

    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_spec)
    LinearLayout llSpec;
    @BindView(R.id.ll_supplier)
    LinearLayout llSupplier;
    @BindView(R.id.ll_supplier_method)
    LinearLayout llSupplierMethod;

    @BindView(R.id.cb_filter_name)
    CheckBox cbFilterName;
    @BindView(R.id.cb_filter_spec)
    CheckBox cbFilterSpec;
    @BindView(R.id.cb_filter_supplier)
    CheckBox cbFilterSupplier;
    @BindView(R.id.cb_filter_supplier_method)
    CheckBox cbFilterSupplierMethod;

    private MaterialVm materialVm;
    private EditText etMaterial;
    private TextView tvSupplier;
    private EditText etSpecification;
    private RadioGroup radioGroup;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        //获取内定物资显示权限
        HomeVm homeVm = ViewModelProviders.of(this).get(HomeVm.class);
        homeVm.supplierCliqueLiveData.observe(this, supplierCliqueBean -> {
            LogUtils.json(LogUtils.E, supplierCliqueBean);
            initViewAndData(supplierCliqueBean);
        });
        homeVm.getSupplierClique();
    }


    private void initViewAndData(SupplierCliqueBean supplierClique) {

        materialVm = ViewModelProviders.of(this).get(MaterialVm.class);

        SellFragment sellFragment = SellFragment.newInstance();
        EquipmentFragment leaseFragment = EquipmentFragment.newInstance();

        if (supplierClique.getSupplier_status() == 2 || supplierClique.getSupplier_status() == 3) {
            BarUtils.addMarginTopEqualStatusBarHeight(toolbarMaterial);
            toolbar.setVisibility(View.GONE);
            toolbarMaterial.setVisibility(View.VISIBLE);
            for (SupplierCliqueBean.SupplierIdBean item : supplierClique.getSupplier_id()) {
                materialVm.authGroup.auth_group.add(item.getId());
            }
            //基建物资订阅推送 默认打开内定物资
            if (getIntent().getIntExtra("notice", 0) == 1) {
                tvOpen.setTextSize(14);
                tvDefault.setTextSize(18);
                materialVm.materialFlag = 3;
                materialVm.sellList(materialVm.materialFlag);
                materialVm.equipmentList(materialVm.materialFlag);
            }
        } else {
            BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
            tvBarTitle.setText(getString(R.string.material_base));
            toolbar.setVisibility(View.VISIBLE);
            toolbarMaterial.setVisibility(View.GONE);
        }

        Fragment[] fragments = new Fragment[]{sellFragment, leaseFragment};

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.infrastructure)));
        tabLayout.setupWithViewPager(vp);

        materialVm.pageStateLiveData.observe(this, s -> {
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

    @OnClick({R.id.iv_bar_back, R.id.iv_back, R.id.tv_open, R.id.tv_default, R.id.over, R.id.ll_name, R.id.ll_spec, R.id.ll_supplier, R.id.ll_supplier_method})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_open:
                if (materialVm.materialFlag == 1) return;
                tvOpen.setTextSize(18);
                tvDefault.setTextSize(14);
                materialVm.materialFlag = 1;
                materialVm.sellList(materialVm.materialFlag);
                materialVm.equipmentList(materialVm.materialFlag);
                break;
            case R.id.tv_default:
                if (materialVm.materialFlag == 3) return;
                tvOpen.setTextSize(14);
                tvDefault.setTextSize(18);
                materialVm.materialFlag = 3;
                materialVm.sellList(materialVm.materialFlag);
                materialVm.equipmentList(materialVm.materialFlag);
                break;
            case R.id.ll_name:
                initPop(0);
                break;
            case R.id.ll_spec:
                initPop(1);
                break;
            case R.id.ll_supplier:
                initPop(2);
                break;
            case R.id.ll_supplier_method:
                initPop(3);
                break;
            case R.id.over:
                if (popupWindowCategory != null) {
                    over.setVisibility(View.GONE);
                    popupWindowCategory.dismiss();
                }
                break;
        }
    }



    private CommonPopupWindow popupWindowCategory;

    private void initPop(int filterType) {
        popupWindowCategory = new CommonPopupWindow.Builder(this)
                .setView(R.layout.dialog_material_filter)
                .setOutsideTouchable(false)
                .setBackGroundLevel(1f)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setViewOnclickListener((view, layoutResId) -> {
                    //商品名称
                    etMaterial = view.findViewById(R.id.et_filter_name);

                    //规格
                    etSpecification = view.findViewById(R.id.et_filter_spec);

                    //供应商
                    tvSupplier = view.findViewById(R.id.tv_filter_supplier);
                    tvSupplier.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CustomConfig.SEARCH_MATERIAL_TYPE, CustomConfig.SUPPLIER_TYPE);
                        bundle.putInt(CustomConfig.MATERIALS_TYPE, vp.getCurrentItem() == 0 ? CustomConfig.FRP : CustomConfig.EQUIPMENT);
                        ActivityUtils.startActivity(bundle, MaterialSearchActivity.class);
                    });
                    //供应方式
                    radioGroup = view.findViewById(R.id.rg_filter_supply_method);
                    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                        switch (checkedId) {
                            case R.id.rb_filter_sell:
                                materialVm.supplierMethod = getResources().getString(R.string.sell);
                                materialVm.supplierMethodId = 2;
                                break;
                            case R.id.rb_filter_lease:
                                materialVm.supplierMethod = getResources().getString(R.string.lease);
                                materialVm.supplierMethodId = 1;
                                break;
                        }
                    });

                    //重置
                    view.findViewById(R.id.tv_negative).setOnClickListener(item -> {
                        switch (filterType) {
                            case 0:
                                materialVm.materialName = "";
                                etMaterial.setText("");
                                break;
                            case 1:
                                materialVm.specification = "";
                                etSpecification.setText("");
                                break;
                            case 2:
                                materialVm.supplierId = 0;
                                materialVm.supplier = "";
                                tvSupplier.setText("");
                                break;
                            case 3:
                                materialVm.supplierMethod = "";
                                materialVm.supplierMethodId = 0;
                                break;
                        }

                        materialVm.sellList(materialVm.materialFlag);
                        materialVm.equipmentList(materialVm.materialFlag);
                        popupWindowCategory.dismiss();
                        over.setVisibility(View.GONE);

                        updateFilterStyle();
                    });

                    //确定
                    view.findViewById(R.id.tv_positive).setOnClickListener(item -> {
                        String spec = etSpecification.getText().toString();
                        String materialName = etMaterial.getText().toString();
                        materialVm.materialName = materialName;
                        materialVm.specification = spec;
                        materialVm.sellList(materialVm.materialFlag);
                        materialVm.equipmentList(materialVm.materialFlag);
                        popupWindowCategory.dismiss();
                        over.setVisibility(View.GONE);
                        updateFilterStyle();
                    });

                })
                .create();

        popupWindowCategory.setFocusable(true);

        popupWindowCategory.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindowCategory.setOnDismissListener(() -> {
            updateFilterStyle();
            over.setVisibility(View.GONE);
        });

        if (!popupWindowCategory.isShowing()) {
            over.setVisibility(View.VISIBLE);
            showPopView(popupWindowCategory.getContentView(), filterType);
            popupWindowCategory.showAsDropDown(clFilter, 0, DensityUtil.dp2px(10));
        }
    }

    public void updateFilterStyle() {
        //筛选框文字
        cbFilterName.setText(StringUtils.isEmpty(materialVm.materialName) ?
                getResources().getString(R.string.filter_name) : materialVm.materialName);

        cbFilterSpec.setText(StringUtils.isEmpty(materialVm.specification) ?
                getResources().getString(R.string.filter_spec) : materialVm.specification);

        cbFilterSupplier.setText(StringUtils.isEmpty(materialVm.supplier) ?
                getResources().getString(R.string.filter_supplier) : materialVm.supplier);

        cbFilterSupplierMethod.setText(StringUtils.isEmpty(materialVm.supplierMethod) ?
                getResources().getString(R.string.filter_supplier_method) : materialVm.supplierMethod);

        //筛选框字体颜色
        cbFilterName.setChecked(!StringUtils.isEmpty(materialVm.materialName));
        cbFilterSpec.setChecked(!StringUtils.isEmpty(materialVm.specification));
        cbFilterSupplier.setChecked(!StringUtils.isEmpty(materialVm.supplier));
        cbFilterSupplierMethod.setChecked(!StringUtils.isEmpty(materialVm.supplierMethod));

    }

    private void showPopView(View view, int type) {
        etMaterial.setText(materialVm.materialName);
        etSpecification.setText(materialVm.specification);
        tvSupplier.setText(materialVm.supplier);

        if (materialVm.supplierMethod.equals(getResources().getString(R.string.sell))) {
            radioGroup.check(R.id.rb_filter_sell);
        } else if (materialVm.supplierMethod.equals(getResources().getString(R.string.lease))) {
            radioGroup.check(R.id.rb_filter_lease);
        } else {
            radioGroup.clearCheck();
        }
        switch (type) {
            case 0:
                cbFilterName.setChecked(true);
                //筛选框字体
                cbFilterSpec.setChecked(!StringUtils.isEmpty(materialVm.specification));
                cbFilterSupplier.setChecked(!StringUtils.isEmpty(materialVm.supplier));
                cbFilterSupplierMethod.setChecked(!StringUtils.isEmpty(materialVm.supplierMethod));
                filterViewShow(view, View.VISIBLE, View.GONE, View.GONE, View.GONE);
                break;
            case 1:
                cbFilterSpec.setChecked(true);
                //筛选框字体
                cbFilterName.setChecked(!StringUtils.isEmpty(materialVm.materialName));
                cbFilterSupplier.setChecked(!StringUtils.isEmpty(materialVm.supplier));
                cbFilterSupplierMethod.setChecked(!StringUtils.isEmpty(materialVm.supplierMethod));
                filterViewShow(view, View.GONE, View.VISIBLE, View.GONE, View.GONE);
                break;
            case 2:
                cbFilterSupplier.setChecked(true);
                //筛选框字体
                cbFilterName.setChecked(!StringUtils.isEmpty(materialVm.materialName));
                cbFilterSpec.setChecked(!StringUtils.isEmpty(materialVm.specification));
                cbFilterSupplierMethod.setChecked(!StringUtils.isEmpty(materialVm.supplierMethod));
                filterViewShow(view, View.GONE, View.GONE, View.VISIBLE, View.GONE);
                break;
            case 3:
                cbFilterSupplierMethod.setChecked(true);
                //筛选框字体
                cbFilterName.setChecked(!StringUtils.isEmpty(materialVm.materialName));
                cbFilterSupplier.setChecked(!StringUtils.isEmpty(materialVm.supplier));
                cbFilterSpec.setChecked(!StringUtils.isEmpty(materialVm.specification));
                filterViewShow(view, View.GONE, View.GONE, View.GONE, View.VISIBLE);
                break;
        }
    }

    private void filterViewShow(View view, int nameVisible, int specVisible, int supplierVisible, int supplyMethodVisible) {
        view.findViewById(R.id.rl_filter_name).setVisibility(nameVisible);
        view.findViewById(R.id.rl_filter_spec).setVisibility(specVisible);
        view.findViewById(R.id.rl_filter_supplier).setVisibility(supplierVisible);
        view.findViewById(R.id.rl_filter_supply_method).setVisibility(supplyMethodVisible);
    }



    @Subscribe
    public void onEventUpdateSupplier(SupplierEvent event) {
        materialVm.supplierId = event.id;
        materialVm.supplier = event.supplier;
        tvSupplier.setText(materialVm.supplier);
    }
}
