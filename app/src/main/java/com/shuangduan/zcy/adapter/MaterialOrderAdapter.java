package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonObject;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adapter$
 * @class MaterialOrderAdapter$
 * @class describe
 * @time 2019/9/25 14:15
 * @change
 * @class describe
 */
public class MaterialOrderAdapter extends BaseQuickAdapter<MaterialOrderBean.ListBean, BaseViewHolder> {
    public MaterialOrderAdapter(int layoutResId, @Nullable List<MaterialOrderBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialOrderBean.ListBean item) {

        ImageView ivIcon = helper.getView(R.id.iv_icon);
        try {
            String thumb = item.images;
            ImageLoader.load(mContext, new ImageConfig.Builder()
                    .url(thumb)
                    .imageView(ivIcon)
                    .placeholder(R.drawable.wuzhi_default)
                    .errorPic(R.drawable.wuzhi_default)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            ivIcon.setImageResource(R.drawable.wuzhi_default);
        }

        helper.setText(R.id.tv_title, item.materialName)
                .setText(R.id.tv_category, "分类:" + item.cateName)
                .setText(R.id.tv_supplier, "供应商:" + item.supplier)
                .setText(R.id.tv_order_num, "订单号:" + item.orderSn);

        setStateInfo(helper, R.id.tv_state, item.status);
    }

    private void setStateInfo(BaseViewHolder helper, int view, int status) {
        switch (status) {
            case 1:
                helper.setText(view, "提交订单");
                break;
            case 2:
                helper.setText(view, "客户经理");
                break;
            case 3:
                helper.setText(view, "沟通确认");
                break;
            case 4:
                helper.setText(view, "投标报价");
                break;
            case 5:
                helper.setText(view, "签订合同");
                break;
            case 6:
                helper.setText(view, "执行合同");
                break;
            case 7:
                helper.setText(view, "结束");
                break;
            case 8:
                helper.setText(view, "取消订单");
                break;
            case 9:
                helper.setText(view, "驳回订单");
                break;
            default:
                helper.setText(view, "");
                break;
        }
    }
}
