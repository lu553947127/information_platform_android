package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.MaterialOrderAdapter;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;
import com.shuangduan.zcy.view.material.LeaseFragment;
import com.shuangduan.zcy.view.material.MaterialActivity;
import com.shuangduan.zcy.view.material.SellFragment;
import com.shuangduan.zcy.vm.MaterialVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

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

    //物资大分类 0：公开物资 1：内定物资
    private int materialFlag;

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
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.my_material));

        toolbar.setVisibility(View.VISIBLE);
        toolbarMaterial.setVisibility(View.GONE);

        Fragment[] fragments = new Fragment[]{
                MineMaterialsFragment.newInstance(),
                MineEquipmentFragment.newInstance()
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

    @OnClick({R.id.iv_bar_back, R.id.iv_back, R.id.tv_open, R.id.tv_default})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_open:
                if (materialFlag == 0) return;
                tvOpen.setTextSize(18);
                tvDefault.setTextSize(14);
                //TODO 后续修改接口
                materialVm.sellList(materialVm.materialId, materialVm.specification, materialVm.supplierId);
                materialVm.leaseList(materialVm.materialId, materialVm.specification, materialVm.supplierId);
                materialFlag = 0;
                break;
            case R.id.tv_default:
                if (materialFlag == 1) return;
                tvOpen.setTextSize(14);
                tvDefault.setTextSize(18);
                //TODO 后续修改接口
                materialVm.sellList(materialVm.materialId, materialVm.specification, materialVm.supplierId);
                materialVm.leaseList(materialVm.materialId, materialVm.specification, materialVm.supplierId);
                materialFlag = 1;
                break;
        }
    }

}
