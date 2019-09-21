package com.shuangduan.zcy.view.material;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MaterialCategoryBean;
import com.shuangduan.zcy.vm.MaterialVm;

import java.util.List;

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

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
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
        materialVm.categoryFirstLiveData.observe(this, materialCategoryBean -> {
            initPop();
        });
        materialVm.categoryLiveData.observe(this, materialCategoryBean -> {
            //设置默认的二级子类ID
            materialCategoryBean.get(0).isSelect = 1;
            materialVm.categoryId = materialCategoryBean.get(0).getId();
            flProduct.removeAllViews();
            for (int i = 0; i < materialCategoryBean.size(); i++) {
                addItemCategory(materialCategoryBean, i);
            }
        });
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
                if (materialVm.categoryFirstLiveData.getValue() == null) {
                    materialVm.getCategoryFirst();
                } else {
                    initPop();
                }
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
    private FlexboxLayout flMaterial;
    private FlexboxLayout flProduct;

    private void initPop() {
        if (popupWindowCategory == null) {
            popupWindowCategory = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.dialog_material_filter)
                    .setOutsideTouchable(false)
                    .setBackGroundLevel(1f)
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setViewOnclickListener((view, layoutResId) -> {
                        flMaterial = view.findViewById(R.id.fl_material);
                        flProduct = view.findViewById(R.id.fl_product);
                        view.findViewById(R.id.tv_negative).setOnClickListener(item -> {
                            popupWindowCategory.dismiss();
                            over.setVisibility(View.GONE);
                        });
                        view.findViewById(R.id.tv_positive).setOnClickListener(item -> {
                            materialVm.sellList();
                            materialVm.leaseList();
                            popupWindowCategory.dismiss();
                            over.setVisibility(View.GONE);
                        });
                    })
                    .create();
        }
        if (!popupWindowCategory.isShowing()) {
            List<MaterialCategoryBean> categoryFirst = materialVm.categoryFirstLiveData.getValue();
            flMaterial.removeAllViews();
            for (int i = 0; i < categoryFirst.size(); i++) {
                addItemFirst(categoryFirst, i);
            }

            popupWindowCategory.showAsDropDown(toolbar, 0, 0);
            over.setVisibility(View.VISIBLE);
        }
    }

    private void addItemFirst(List<MaterialCategoryBean> categoryList, int i) {
        final TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_box, flMaterial, false);
        MaterialCategoryBean bean = categoryList.get(i);
        tv.setText(bean.getCatname());
        tv.setSelected(bean.getIsSelect() == 1);
        tv.setOnClickListener(v -> {
            if (tv.isSelected()) {
                //如果本身就是选中状态，则变为未选中并移除二级分类的所有子控件
                flProduct.removeAllViews();
                //一级分类ID
                materialVm.categoryFirstId = 0;
                //二级分类ID
                materialVm.categoryId = 0;
            } else {
                //开启二级分类
                materialVm.categoryFirstId = bean.getId();
                materialVm.getCategory();
            }
            bean.setIsSelect(tv.isSelected() ? 0 : 1);
            tv.setSelected(!tv.isSelected());
            for (int j = 0; j < categoryList.size(); j++) {
                if (j != i) {
                    categoryList.get(j).setIsSelect(0);
                    View child = flMaterial.getChildAt(j);
                    if (child instanceof TextView) {
                        ((TextView) child).setSelected(false);
                    }
                }
            }
        });
        flMaterial.addView(tv);
    }

    private void addItemCategory(List<MaterialCategoryBean> categoryList, int i) {
        final TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_box, flProduct, false);
        MaterialCategoryBean bean = categoryList.get(i);
        tv.setText(bean.getCatname());
        tv.setSelected(bean.getIsSelect() == 1);
        tv.setOnClickListener(v -> {
            if (tv.isSelected()) {
                materialVm.categoryId = 0;
            } else {
                materialVm.categoryId = bean.getId();
            }
            bean.setIsSelect(tv.isSelected() ? 0 : 1);
            tv.setSelected(!tv.isSelected());
            for (int j = 0; j < categoryList.size(); j++) {
                if (j != i) {
                    categoryList.get(j).setIsSelect(0);
                    View child = flProduct.getChildAt(j);
                    if (child instanceof TextView) {
                        ((TextView) child).setSelected(false);
                    }
                }
            }
        });
        flProduct.addView(tv);
    }
}
