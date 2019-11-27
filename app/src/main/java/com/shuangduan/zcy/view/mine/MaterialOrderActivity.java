package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.SupplierCliqueBean;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.vm.MaterialVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MaterialOrderActivity$
 * @class 物资预定
 * @time 2019/9/25 13:41
 * @change
 * @class describe
 */
public class MaterialOrderActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
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
    private MaterialVm materialVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
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

        if (supplierClique.getSupplier_status() == 2 || supplierClique.getSupplier_status() == 3) {
            BarUtils.addMarginTopEqualStatusBarHeight(toolbarMaterial);
            toolbar.setVisibility(View.GONE);
            toolbarMaterial.setVisibility(View.VISIBLE);
            for (SupplierCliqueBean.SupplierIdBean item : supplierClique.getSupplier_id()) {
                materialVm.authGroup.auth_group.add(item.getId());
            }
        } else {
            BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
            tvBarTitle.setText(getString(R.string.my_material));
            toolbar.setVisibility(View.VISIBLE);
            toolbarMaterial.setVisibility(View.GONE);
        }

        Fragment[] fragments = new Fragment[]{
                MineMaterialsFragment.newInstance(),
                MineEquipmentFragment.newInstance()
        };
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

    @OnClick({R.id.iv_bar_back, R.id.iv_back, R.id.tv_open, R.id.tv_default})
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
                materialVm.orderList();
                materialVm.getEquipmentOrder();
                break;
            case R.id.tv_default:
                if (materialVm.materialFlag == 3) return;
                tvOpen.setTextSize(14);
                tvDefault.setTextSize(18);
                materialVm.materialFlag = 3;
                materialVm.orderList();
                materialVm.getEquipmentOrder();
                break;
        }
    }
}
