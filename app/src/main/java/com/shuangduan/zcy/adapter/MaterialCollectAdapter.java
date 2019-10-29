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
import com.shuangduan.zcy.utils.TextViewUtils;
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
        TextView tvTitle = helper.getView(R.id.tv_title);

//        if (item. == 1) {
//            TextViewUtils.addDrawableInEnd(tvTitle, mContext.getResources().getDrawable(R.drawable.icon_bought), item.getMaterialName());
//        } else {
//            tvTitle.setText(item.getMaterialName());
//        }
//
//          .setText(R.id.tv_price, item.getMethod() == 2 ?
//                String.format(mContext.getString(R.string.format_material_price), item.getGuidancePrice(), item.getUnit()) :
//                String.format(mContext.getString(R.string.format_material_price), item.getGuidancePrice(), "天"))
//        .setText(R.id.tv_supply_method, item.getMethod() == 1 ? "出租" : "出售")
//         .setText(R.id.tv_browse_num, String.format(mContext.getString(R.string.format_visitors_num), item.getBrowseCount()))

        tvTitle.setText(item.catname);

        helper.setText(R.id.tv_stock, "库存: " + item.stock + item.unit)
                .setText(R.id.tv_spec, "规格: " + item.spec)
                .setText(R.id.tv_supplier, "供应商: " + item.company)
                .setText(R.id.tv_price, String.format(mContext.getString(R.string.format_material_price), item.guidance_price, item.unit))
                .setText(R.id.tv_address, "存放地: " + "")
                .setGone(R.id.tv_supply_method, false);
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
