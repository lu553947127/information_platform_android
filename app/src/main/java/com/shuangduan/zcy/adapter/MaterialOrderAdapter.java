package com.shuangduan.zcy.adapter;

import android.text.TextPaint;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.TextViewUtils;
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

        TextView tvTitle = helper.getView(R.id.tv_title);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) helper.getView(R.id.tv_title).getLayoutParams();

        if (item.inside == 3) {
            lp.rightMargin = DensityUtil.dp2px(10);

            if (item.materialName.length() > 9) {
                TextViewUtils.addDrawableInEnd(tvTitle, mContext.getResources().getDrawable(R.drawable.icon_mine_default_material), item.materialName.substring(0,9) + "...");
            } else {
                TextViewUtils.addDrawableInEnd(tvTitle, mContext.getResources().getDrawable(R.drawable.icon_mine_default_material), item.materialName);
            }
            tvTitle.setLayoutParams(lp);
        } else {
            lp.rightMargin = DensityUtil.dp2px(10);
            tvTitle.setText(item.materialName);
            tvTitle.setLayoutParams(lp);
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

        helper.setText(R.id.tv_category, "分类:" + item.cateName)
                .setText(R.id.tv_supplier, "供应商:" + item.supplier)
                .setText(R.id.tv_order_num, "订单号:" + item.orderSn)
                .setText(R.id.tv_supply_method, item.method == 1 ? "出租" : "出售");

        if (item.status.equals("驳回")) {
            helper.setTextColor(R.id.tv_state, mContext.getResources().getColor(R.color.text2));
            helper.setText(R.id.tv_state, item.status);
        } else {
            helper.setTextColor(R.id.tv_state, mContext.getResources().getColor(R.color.text1));
            helper.setText(R.id.tv_state, item.phases);
        }
    }

    /**
     * 获取textview一行最大能显示几个字(需要在TextView测量完成之后)
     *
     * @param text     文本内容
     * @param paint    textview.getPaint()
     * @param maxWidth textview.getMaxWidth()/或者是指定的数值,如200dp
     */
    private int getLineMaxNumber(String text, TextPaint paint, int maxWidth) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        //得到文本内容总体长度
        float textWidth = paint.measureText(text);
        // textWidth
        float width = textWidth / text.length();
        return (int) (maxWidth / width);
    }
}
