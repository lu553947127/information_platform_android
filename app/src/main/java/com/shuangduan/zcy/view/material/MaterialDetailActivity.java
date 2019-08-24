package com.shuangduan.zcy.view.material;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.MaterialOrderDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.AuthenticationActivity;
import com.shuangduan.zcy.view.mine.WithdrawActivity;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.vm.WithdrawVm;

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
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_bought)
    TextView tvBought;
    @BindView(R.id.tv_owner)
    TextView tvOwner;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_sold_num)
    TextView tvSoldNum;
    @BindView(R.id.tv_suit_num)
    TextView tvSuitNum;
    @BindView(R.id.tv_suit_t)
    TextView tvSuitT;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    private MaterialDetailVm materialDetailVm;
    private WithdrawVm withdrawVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_detail;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.product_detail));

        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);
        materialDetailVm.detailLiveData.observe(this, materialDetailBean -> {
            if (materialDetailBean == null) return;
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(materialDetailBean.getImage())
                    .imageView(ivIcon)
                    .placeholder(R.drawable.default_pic)
                    .errorPic(R.drawable.default_pic)
                    .build());
            tvTitle.setText(materialDetailBean.getName());
            tvContent.setText(String.format(getString(R.string.format_provide), materialDetailBean.getAmount()));
            tvBought.setText(String.format(getString(R.string.format_bought), materialDetailBean.getName()));
            tvOwner.setText(String.format(getString(R.string.format_supplier), materialDetailBean.getAgent_name()));
            tvPrice.setText(materialDetailBean.getPrice());
            tvSoldNum.setText(String.format(getString(R.string.format_sold), materialDetailBean.getSell_amount()));
            tvOrderNum.setText(String.format(getString(R.string.format_order_num), materialDetailBean.getOrder_count()));
            tvIntro.setText(materialDetailBean.getIntro());
        });
        materialDetailVm.suitNumLiveData.observe(this, integer -> {
            MaterialDetailBean value = materialDetailVm.detailLiveData.getValue();
            if (value == null) return;
            int agent_id = value.getAgent_id();
            double price = Double.parseDouble(value.getPrice());
            tvSuitNum.setText(String.valueOf(integer));
            tvSuitT.setText(String.valueOf(integer * agent_id));
            tvNum.setText(String.valueOf(integer));
            tvTotalPrice.setText(String.valueOf(price * integer * agent_id));
        });
        materialDetailVm.orderLiveData.observe(this, o -> {
            ActivityUtils.startActivity(MaterialOrderSuccessActivity.class);
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

        withdrawVm = ViewModelProviders.of(this).get(WithdrawVm.class);
        withdrawVm.authenticationLiveData.observe(this, authenBean -> {
            switch (authenBean.getCard_status()){
                case 1:
                    ToastUtils.showShort("审核中，请等待审核成功后进入");
                    break;
                case 2:
                    SPUtils.getInstance().put(SpConfig.IS_VERIFIED, authenBean.getCard_status());
                    new MaterialOrderDialog(this)
                            .setName(tvTitle.getText().toString())
                            .setSuitNum(tvSuitNum.getText().toString())
                            .setSuitT(tvSuitT.getText().toString())
                            .setAmount(tvTotalPrice.getText().toString())
                            .setSingleCallBack((item, position) -> {
                                materialDetailVm.pre();
                            }).showDialog();
                    break;
                default:
                    //去认证
                    Bundle bundle = new Bundle();
                    bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                    ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                    finish();
                    break;
            }
        });
        withdrawVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        materialDetailVm.getDetail();
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_add, R.id.iv_less, R.id.tv_pre})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_add:
                materialDetailVm.add();
                break;
            case R.id.iv_less:
                materialDetailVm.less();
                break;
            case R.id.tv_pre:
                Integer suitNum = materialDetailVm.suitNumLiveData.getValue();
                if (suitNum == null || suitNum == 0){
                    ToastUtils.showShort(getString(R.string.select_suit_num));
                    return;
                }

                int isVerified = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED);
                if (isVerified == 2){
                    //认证成功
                    new MaterialOrderDialog(this)
                            .setName(tvTitle.getText().toString())
                            .setSuitNum(String.valueOf(suitNum))
                            .setSuitT(tvSuitT.getText().toString())
                            .setAmount(tvTotalPrice.getText().toString())
                            .setSingleCallBack((item, position) -> {
                                materialDetailVm.pre();
                            }).showDialog();
                }else {
                    withdrawVm.authentication();
                }
                break;
        }
    }
}
