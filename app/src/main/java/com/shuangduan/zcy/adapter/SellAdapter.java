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
 * @author 宁文强 QQ:858777523
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
                .setText(R.id.tv_stock, "库存: " + item.getStock() + "吨")
                .setText(R.id.tv_spec, "规格: " + item.getSpec())
                .setText(R.id.tv_owner, "供应商: " + item.getMaterialSupplie())
                .setText(R.id.tv_price, item.getGuidancePrice())
                .setText(R.id.tv_sold_num, "销量: " + item.getSalesVolume() + "吨")
                .setText(R.id.tv_address, "存放地: " + item.getAddress())
                .setGone(R.id.tv_bought, item.getIs_order() == 1);

        try {
            String thumb = item.getImages().get(0).headeUrl;

            ImageView ivIcon = helper.getView(R.id.iv_icon);
            ImageLoader.load(mContext, new ImageConfig.Builder()
                    .url(thumb)
                    .imageView(ivIcon)
                    .placeholder(R.drawable.default_pic)
                    .errorPic(R.drawable.default_pic)
                    .build());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
