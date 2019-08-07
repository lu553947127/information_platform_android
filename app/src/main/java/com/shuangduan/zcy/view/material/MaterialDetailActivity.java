package com.shuangduan.zcy.view.material;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.vm.MaterialDetailVm;

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

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_detail;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.product_detail));

        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.detailLiveData.observe(this, materialDetailBean -> {
            if (materialDetailBean == null) return;
//            ImageLoader.load(this, new ImageConfig.Builder()
//                    .url(materialDetailBean.geti)
//                    .build());
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
        materialDetailVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    LogUtils.i("隐藏");
                    hideLoading();
                    break;
            }
        });
        materialDetailVm.getDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_add, R.id.iv_less, R.id.tv_pre, R.id.cl_order_num})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_add:
                Integer value = materialDetailVm.suitNumLiveData.getValue();
                value++;
                materialDetailVm.suitNumLiveData.postValue(value);
                break;
            case R.id.iv_less:
                Integer num = materialDetailVm.suitNumLiveData.getValue();
                if (num <= 0) return;
                num--;
                materialDetailVm.suitNumLiveData.postValue(num);
                break;
            case R.id.tv_pre:

                break;
            case R.id.cl_order_num:

                break;
        }
    }
}
