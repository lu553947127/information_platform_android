package com.shuangduan.zcy.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.model.bean.MaterialCollectBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adapter$
 * @class MaterialCollectAdapter$
 * @class 基建物资收藏
 * @time 2019/9/25 9:08
 * @change
 * @class describe
 */
public class MaterialCollectAdapter extends BaseQuickAdapter<MaterialCollectBean.ListBean, BaseViewHolder> {
    public MaterialCollectAdapter(int layoutResId, @Nullable List<MaterialCollectBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialCollectBean.ListBean item) {
        String method = item.method == 1 ? "租赁" : "售卖";
        helper.setText(R.id.tv_title, item.catname)
                .setText(R.id.tv_stock, "分类: " + item.category)
                .setText(R.id.tv_spec, "方式: " + method)
                .setText(R.id.tv_owner, "供应商: " + item.company)
                .setText(R.id.tv_price, item.guidance_price)
                .setText(R.id.tv_price_unit, String.format(mContext.getString(R.string.format_price_unit), item.unit))
                .setText(R.id.tv_sold_num, "库存: " + item.stock + item.unit);
        TextView tvStatus = helper.getView(R.id.tv_status);
        if (item.status == 1) {

            helper.setGone(R.id.tv_price, true)
                    .setGone(R.id.tv_price_unit, true);
            tvStatus.setVisibility(View.GONE);
        } else {
            helper.setGone(R.id.tv_price, false)
                    .setGone(R.id.tv_price_unit, false);
            tvStatus.setVisibility(View.VISIBLE);
        }
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
    }
}
