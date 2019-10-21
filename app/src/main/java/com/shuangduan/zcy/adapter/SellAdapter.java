package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/7 10:49
 * @change
 * @chang time
 * @class describe
 */
public class SellAdapter extends BaseQuickAdapter<MaterialBean.ListBean, BaseViewHolder> {
    public SellAdapter(int layoutResId, @Nullable List<MaterialBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getMaterialName())
                .setText(R.id.tv_stock, "库存: " + item.getStock() + item.getUnit())
                .setText(R.id.tv_spec, "规格: " + item.getSpec())
                .setText(R.id.tv_owner, "供应商: " + item.getMaterialSupplie())
                .setText(R.id.tv_price, item.getGuidancePrice())
                .setText(R.id.tv_sold_num, "销量: " + item.getSalesVolume() + item.getUnit())
                .setText(R.id.tv_address, "存放地: " + item.getAddress())
                .setText(R.id.tv_price_unit, String.format(mContext.getString(R.string.format_price_unit), item.getUnit()))
                .setGone(R.id.tv_bought, item.getIs_order() == 1);
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        try {
            String thumb = item.getImages().get(0).headeUrl;

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

    }
}
