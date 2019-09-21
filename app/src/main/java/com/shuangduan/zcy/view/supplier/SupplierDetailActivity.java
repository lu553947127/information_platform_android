package com.shuangduan.zcy.view.supplier;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.SupplierDetailBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.PhotoViewActivity;
import com.shuangduan.zcy.vm.SupplierVm;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.ArrayList;

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
    @BindView(R.id.tv_company)
    AppCompatTextView tvCompany;
    @BindView(R.id.tv_address_detail)
    AppCompatTextView tvAddressDetail;
    @BindView(R.id.tv_scale)
    AppCompatTextView tvScale;
    @BindView(R.id.tv_company_website)
    AppCompatTextView tvCompanyWebsite;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_mobile)
    AppCompatTextView tvMobile;
    @BindView(R.id.tv_business_area)
    AppCompatTextView tvBusinessArea;
    @BindView(R.id.tv_production)
    TextView tvProduction;
    @BindView(R.id.iv_pic_first)
    ImageView ivOne;
    @BindView(R.id.iv_pic_two)
    ImageView ivTwo;
    @BindView(R.id.iv_pic_three)
    ImageView ivThree;
    @BindView(R.id.iv_authorization)
    ImageView ivAuthorization;
    SupplierDetailBean listBean;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_supplier_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));

        SupplierVm supplierVm = ViewModelProviders.of(this).get(SupplierVm.class);
        supplierVm.detailLiveData.observe(this, supplierDetailBean -> {
            listBean = supplierDetailBean;
            tvCompany.setText(supplierDetailBean.getCompany());
            tvAddressDetail.setText(supplierDetailBean.getAddress());
            tvScale.setText(supplierDetailBean.getScale() > 0 ? getResources().getStringArray(R.array.scale_list)[supplierDetailBean.getScale() - 1] + "人": "");
            tvCompanyWebsite.setText(supplierDetailBean.getCompany_website());
            tvName.setText(supplierDetailBean.getName());
            tvMobile.setText(supplierDetailBean.getTel());
            tvBusinessArea.setText(supplierDetailBean.getServe_address());
            tvProduction.setText(supplierDetailBean.getProduct());
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(supplierDetailBean.getHeadimg())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
            if (supplierDetailBean.getImages_json()!=null){
                if (supplierDetailBean.getImages_json().size() >= 1){
                    ivOne.setVisibility(View.VISIBLE);
                    ImageLoader.load(this, new ImageConfig.Builder().url(supplierDetailBean.getImages_json().get(0).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivOne).build());
                }
                if (supplierDetailBean.getImages_json().size() >= 2){
                    ivTwo.setVisibility(View.VISIBLE);
                    ImageLoader.load(this, new ImageConfig.Builder().url(supplierDetailBean.getImages_json().get(1).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivTwo).build());
                }
                if (supplierDetailBean.getImages_json().size() >= 3){
                    ivThree.setVisibility(View.VISIBLE);
                    ImageLoader.load(this, new ImageConfig.Builder().url(supplierDetailBean.getImages_json().get(2).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivThree).build());
                }
            }
            ImageLoader.load(this, new ImageConfig.Builder().url(supplierDetailBean.getAuthorization()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivAuthorization).build());
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

    private void showPic(SupplierDetailBean item, int position, View view){
        ArrayList<String> list = new ArrayList<>();
        for (SupplierDetailBean.ImagesJsonBean img: item.getImages_json()) {
            list.add(img.getSource());
        }

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putStringArrayList(CustomConfig.PHOTO_VIEW_URL_LIST, list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //共享shareElement这个View
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "shareElement");
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class, activityOptionsCompat.toBundle());
        } else {
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class);
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_pic_first,R.id.iv_pic_two,R.id.iv_pic_three,R.id.iv_authorization})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_pic_first:
                showPic(listBean,0,ivOne);
                break;
            case R.id.iv_pic_two:
                showPic(listBean,1,ivTwo);
                break;
            case R.id.iv_pic_three:
                showPic(listBean,2,ivThree);
                break;
            case R.id.iv_authorization:

                break;

        }
    }
}
