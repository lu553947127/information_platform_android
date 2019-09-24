package com.shuangduan.zcy.view.material;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.MaterialEvent;
import com.shuangduan.zcy.model.event.SupplierEvent;
import com.shuangduan.zcy.vm.MaterialVm;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
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
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.over)
    View over;
    private MaterialVm materialVm;

    private TextView tvMaterial;
    private TextView tvSupplier;
    private EditText etSpecification;


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
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.material_base));
        tvBarRight.setText(getString(R.string.filter));

        Fragment[] fragments = new Fragment[]{
                SellFragment.newInstance(),
                LeaseFragment.newInstance()
        };
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.infrastructure)));
        tabLayout.setupWithViewPager(vp);

        materialVm = ViewModelProviders.of(this).get(MaterialVm.class);


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

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.over})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                initPop();
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


    private void initPop() {
        if (popupWindowCategory == null) {
            popupWindowCategory = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.dialog_material_filter)
                    .setOutsideTouchable(false)
                    .setBackGroundLevel(1f)
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setViewOnclickListener((view, layoutResId) -> {
                        //商品名称
                        tvMaterial = view.findViewById(R.id.tv_material_value);
                        tvMaterial.setOnClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt(CustomConfig.SEARCH_MATERIAL_TYPE, CustomConfig.MATERIAL_TYPE);
                            ActivityUtils.startActivity(bundle, MaterialSearchActivity.class);
                        });

                        //规格
                        etSpecification = view.findViewById(R.id.et_specification);

                        //供应商
                        tvSupplier = view.findViewById(R.id.tv_supplier_value);
                        tvSupplier.setOnClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt(CustomConfig.SEARCH_MATERIAL_TYPE, CustomConfig.SUPPLIER_TYPE);
                            ActivityUtils.startActivity(bundle, MaterialSearchActivity.class);
                        });


                        view.findViewById(R.id.tv_negative).setOnClickListener(item -> {
                            materialVm.materialId = 0;
                            materialVm.specification = "";
                            materialVm.supplierId = 0;
                            tvMaterial.setText("");
                            tvSupplier.setText("");
                            etSpecification.setText("");
                        });
                        view.findViewById(R.id.tv_positive).setOnClickListener(item -> {
                            String spec = etSpecification.getText().toString();
                            materialVm.specification = spec;
                            materialVm.sellList(materialVm.materialId, materialVm.specification, materialVm.supplierId);
                            materialVm.leaseList(materialVm.materialId, materialVm.specification, materialVm.supplierId);
                            popupWindowCategory.dismiss();
                            over.setVisibility(View.GONE);
                        });
                    })
                    .create();
            popupWindowCategory.setFocusable(true);

            popupWindowCategory.setOnDismissListener(() -> {
                over.setVisibility(View.GONE);
            });
        }
        if (!popupWindowCategory.isShowing()) {
            popupWindowCategory.showAsDropDown(toolbar, 0, 0);
            over.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe
    public void onEventUpdateMaterial(MaterialEvent event) {
        materialVm.materialId = event.id;

        tvMaterial.setText(event.material);
    }

    @Subscribe
    public void onEventUpdateSupplier(SupplierEvent event) {
        materialVm.supplierId = event.id;
        tvSupplier.setText(event.supplier);
    }
}
