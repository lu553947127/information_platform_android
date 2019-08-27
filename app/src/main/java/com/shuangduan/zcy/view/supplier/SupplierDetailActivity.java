package com.shuangduan.zcy.view.supplier;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.SupplierVm;
import com.shuangduan.zcy.weight.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.supplier
 * @class describe  供应商个人详情
 * @time 2019/8/12 13:59
 * @change
 * @chang time
 * @class describe
 */
public class SupplierDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_sex)
    AppCompatTextView tvSex;
    @BindView(R.id.tv_mobile)
    AppCompatTextView tvMobile;
    @BindView(R.id.tv_company)
    AppCompatTextView tvCompany;
    @BindView(R.id.tv_office)
    AppCompatTextView tvOffice;
    @BindView(R.id.tv_business_area)
    AppCompatTextView tvBusinessArea;
    @BindView(R.id.tv_business_exp)
    AppCompatTextView tvBusinessExp;
    @BindView(R.id.tv_production)
    TextView tvProduction;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_supplier_detail;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));

        SupplierVm supplierVm = ViewModelProviders.of(this).get(SupplierVm.class);
        supplierVm.detailLiveData.observe(this, supplierDetailBean -> {
            tvName.setText(supplierDetailBean.getName());
            tvMobile.setText(supplierDetailBean.getTel());
            tvCompany.setText(supplierDetailBean.getCompany());
            tvOffice.setText(supplierDetailBean.getPosition());
            tvBusinessArea.setText(supplierDetailBean.getServe_address());
            tvBusinessExp.setText(supplierDetailBean.getExperience() > 0 ? getResources().getStringArray(R.array.experience_list)[supplierDetailBean.getExperience() - 1] + "年": "");
            tvProduction.setText(supplierDetailBean.getProduct());
            switch (supplierDetailBean.getSex()){
                case 0:
                    tvSex.setText(getString(R.string.select_not));
                    break;
                case 1:
                    tvSex.setText(getString(R.string.man));
                    break;
                case 2:
                    tvSex.setText(getString(R.string.woman));
                    break;
            }
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(supplierDetailBean.getHeadimg())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
        });
        supplierVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        supplierVm.getDetail(getIntent().getIntExtra(CustomConfig.SUPPLIER_ID, 0));
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){ finish(); }
}
