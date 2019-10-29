package com.shuangduan.zcy.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.utils.TextViewUtils;
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
public class EquipmentAdapter extends BaseQuickAdapter<MaterialBean.ListBean, BaseViewHolder> {
    public EquipmentAdapter(int layoutResId, @Nullable List<MaterialBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialBean.ListBean item) {

        TextView tvTitle = helper.getView(R.id.tv_title);

        if (item.getIs_order() == 1) {
            TextViewUtils.addDrawableInEnd(tvTitle, mContext.getResources().getDrawable(R.drawable.icon_bought), item.getEquipmentName());
        } else {
            tvTitle.setText(item.getEquipmentName());
        }

        helper.setText(R.id.tv_stock, "库存: " + item.getStock() + item.getUnit())
                .setText(R.id.tv_spec, "规格: " + item.getSpec())
                .setText(R.id.tv_supplier, "供应商: " + item.getEquipmentSupplier())
                .setText(R.id.tv_price, item.getMethod() == 2 ?
                        String.format(mContext.getString(R.string.format_material_price), item.getGuidancePrice(), item.getUnit()) :
                        String.format(mContext.getString(R.string.format_material_price), item.getGuidancePrice(), "天"))
                .setText(R.id.tv_address, "存放地: " + item.getAddress())
                .setText(R.id.tv_supply_method, item.getMethod() == 1 ? "出租" : "出售")
                .setText(R.id.tv_browse_num, String.format(mContext.getString(R.string.format_visitors_num), item.getBrowseCount()));
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
