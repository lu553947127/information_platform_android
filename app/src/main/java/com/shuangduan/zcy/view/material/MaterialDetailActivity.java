package com.shuangduan.zcy.view.material;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.event.MaterialDetailEvent;
import com.shuangduan.zcy.utils.image.GlideImageLoader;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.material
 * @class describe  基建物质详情
 * @time 2019/8/7 11:39
 * @change
 * @chang time
 * @class describe
 */
public class MaterialDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_material_category)
    TextView tvMaterialCategory;
    @BindView(R.id.tv_enclosure)
    TextView tvEnclosure;
    @BindView(R.id.tv_unit_price)
    TextView tvUnitPrice;
    @BindView(R.id.tv_stock)
    TextView tvStock;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_tel)
    AppCompatTextView tvTel;
    @BindView(R.id.tv_address_list)
    AppCompatTextView tvAddressList;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_supplie_address)
    TextView tvSupplieAddress;
    @BindView(R.id.tv_company_website)
    TextView tvCompanyWebsite;
    @BindView(R.id.tv_serve_address)
    TextView tvServeAddress;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.iv_collect)
    ImageView ivCollection;
    @BindView(R.id.ll_collect)
    LinearLayout llCollection;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;
    private MaterialDetailVm materialDetailVm;
    private String phone,is_collect,enclosure;
    private List<String> pics;
    int material_id;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.product_detail));

        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);
        materialDetailVm.detailLiveData.observe(this, materialDetailBean -> {
            if (materialDetailBean == null) return;
            pics = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (MaterialDetailBean.ImagesBean bean : materialDetailBean.getImages()) {
                pics.add(bean.getUrl());
                titles.add("");
            }
            initBanner(pics, titles);
            tvMaterialCategory.setText(materialDetailBean.getMaterial_category());
            String str="<font color=\"#EF583E\">"+materialDetailBean.getGuidance_price()+"</font>元/吨";
            tvUnitPrice.setText(Html.fromHtml(str));
            tvStock.setText(String.format(getString(R.string.format_stock), materialDetailBean.getStock()));
            tvSalesVolume.setText(String.format(getString(R.string.format_sales_volume), materialDetailBean.getSales_volume()));
            tvSpec.setText(String.format(getString(R.string.format_spec), materialDetailBean.getSpec()));
            tvCompany.setText("供应商："+materialDetailBean.getCompany());
            tvAddressList.setText(materialDetailBean.getAddress());
            tvCompanyName.setText(materialDetailBean.getCompany());
            tvSupplieAddress.setText(materialDetailBean.getSupplie_address());
            tvCompanyWebsite.setText(materialDetailBean.getCompany_website());
            tvServeAddress.setText(materialDetailBean.getServe_address());
            tvProduct.setText(materialDetailBean.getProduct());
            material_id=materialDetailBean.getMaterial_id();
            phone=materialDetailBean.getTel();
            enclosure=materialDetailBean.getEnclosure();
            if (materialDetailBean.getIs_collection().equals("1")){
                is_collect=materialDetailBean.getIs_collection();
                ivCollection.setBackgroundResource(R.drawable.icon_new_collectioned);
            }else {
                is_collect=materialDetailBean.getIs_collection();
                ivCollection.setBackgroundResource(R.drawable.icon_new_collection);
            }
        });

        materialDetailVm.collectedLiveData.observe(this, o -> {
            llCollection.setClickable(true);
            is_collect="1";
            ivCollection.setBackgroundResource(R.drawable.icon_new_collectioned);
        });
        materialDetailVm.collectLiveData.observe(this, o -> {
            llCollection.setClickable(true);
            is_collect="0";
            ivCollection.setBackgroundResource(R.drawable.icon_new_collection);
        });
        materialDetailVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        materialDetailVm.getDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
    }

    @OnClick({R.id.iv_bar_back,R.id.tv_enclosure, R.id.tv_tel, R.id.tv_address_list, R.id.ll_collect,R.id.tv_reserve})
    void onClick(View v){
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_enclosure:
                if (TextUtils.isEmpty(enclosure)){
                    ToastUtils.showShort(getString(R.string.no_enclosure));
                    return;
                }
                new CustomDialog(this)
                        .setTip(enclosure)
                        .setOk("复制")
                        .setTitle("建议复制到电脑端打开")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                //获取剪贴板管理器：
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                //创建普通字符型ClipData
                                ClipData mClipData = ClipData.newPlainText("Label", enclosure);
                                //将ClipData内容放到系统剪贴板里。
                                Objects.requireNonNull(cm).setPrimaryClip(mClipData);
                                ToastUtils.showShort(getString(R.string.replication_success));
                            }
                        }).showDialog();
                break;
            case R.id.tv_tel:
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showShort(getString(R.string.no_tel));
                    return;
                }
                new CustomDialog(this)
                        .setTip(phone)
                        .setOk("打电话")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                getCallPhone();
                            }
                        }).showDialog();
                break;
            case R.id.tv_address_list:
                bundle.putInt(CustomConfig.MATERIAL_ID, material_id);
                ActivityUtils.startActivity(bundle, DepositingPlaceActivity.class);
                break;
            case R.id.ll_collect:
                llCollection.setClickable(false);
                if (is_collect!=null&&is_collect.equals("1")){
                    materialDetailVm.getCollect();//取消收藏
                }else {
                    materialDetailVm.getCollected();//收藏
                }
                break;
            case R.id.tv_reserve:
                bundle.putInt(CustomConfig.MATERIAL_ID, getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
                ActivityUtils.startActivity(bundle, MaterialPlaceOrderActivity.class);
                break;
        }
    }

    @Subscribe
    public void onEventUpdateMaterialDetail(MaterialDetailEvent event) {
        LogUtils.i(event.material_id);
        materialDetailVm.getDetail(event.material_id);
    }

    //拨打电话方法
    private void getCallPhone() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private void initBanner(List<String> list, ArrayList<String> titles){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //轮播图查看
        banner.setOnBannerListener(position -> PictureEnlargeUtils.getPictureEnlargeList(this,list,position));
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }
}
