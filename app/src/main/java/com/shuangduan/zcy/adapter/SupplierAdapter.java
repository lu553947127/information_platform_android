package com.shuangduan.zcy.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/12 9:45
 * @change
 * @chang time
 * @class describe
 */
public class SupplierAdapter extends BaseQuickAdapter<SupplierBean.ListBean, BaseViewHolder> {
    private String keyword;
    public SupplierAdapter(int layoutResId, @Nullable List<SupplierBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SupplierBean.ListBean item) {
        helper.setText(R.id.tv_company, setSpan(item.getCompany()))
                .setText(R.id.tv_mobile, setSpan(item.getTel()))
                .setText(R.id.tv_area, String.format(mContext.getString(R.string.format_supplier_service_area), item.getServe_address()))
                .setText(R.id.tv_production, setSpan(String.format(mContext.getString(R.string.format_supplier_product), item.getProduct())))
                .setGone(R.id.iv_pic_first, item.getImages_json() != null && item.getImages_json().size() >= 1)
                .setGone(R.id.iv_pic_second, item.getImages_json() != null && item.getImages_json().size() >= 2)
                .setGone(R.id.iv_pic_third, item.getImages_json() != null && item.getImages_json().size() >= 3)
                .setGone(R.id.tv_more, item.getImages_json() != null && item.getImages_json().size() >= 4)
//                .setGone(R.id.tv_read, item.getIs_pay() != 1)
                .addOnClickListener(R.id.iv_header)
                .addOnClickListener(R.id.tv_read);

        CircleImageView ivHead = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getHeadimg())
                .placeholder(R.drawable.no_supplier_logo)
                .errorPic(R.drawable.no_supplier_logo)
                .imageView(ivHead)
                .build());

        if (item.getImages_json() != null){
            if (item.getImages_json().size() >= 1){
                ImageView ivFirst = helper.getView(R.id.iv_pic_first);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImages_json().get(0).getThumbnail()).placeholder(R.drawable.wuzhi_default).errorPic(R.drawable.wuzhi_default).imageView(ivFirst).build());
            }
            if (item.getImages_json().size() >= 2){
                ImageView ivFirst = helper.getView(R.id.iv_pic_second);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImages_json().get(1).getThumbnail()).placeholder(R.drawable.wuzhi_default).errorPic(R.drawable.wuzhi_default).imageView(ivFirst).build());
            }
            if (item.getImages_json().size() >= 3){
                ImageView ivFirst = helper.getView(R.id.iv_pic_third);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImages_json().get(2).getThumbnail()).placeholder(R.drawable.wuzhi_default).errorPic(R.drawable.wuzhi_default).imageView(ivFirst).build());
            }
        }

        ArrayList<String> list = new ArrayList<>();
        for (SupplierBean.ListBean.ImageBean bean : item.getImages_json()) {
            list.add(bean.getSource());
        }
        helper.getView(R.id.iv_pic_first).setOnClickListener(v -> PictureEnlargeUtils.getPictureEnlargeList((Activity) mContext,list,0));
        helper.getView(R.id.iv_pic_second).setOnClickListener(v -> PictureEnlargeUtils.getPictureEnlargeList((Activity) mContext,list,1));
        helper.getView(R.id.iv_pic_third).setOnClickListener(v -> PictureEnlargeUtils.getPictureEnlargeList((Activity) mContext,list,2));
        helper.getView(R.id.tv_more).setOnClickListener(v -> PictureEnlargeUtils.getPictureEnlargeList((Activity) mContext,list,2));
    }

    /**
     * 设置高亮
     *
     * @param text
     * @return
     */
    private SpannableString setSpan(String text) {
        SpannableString sp = new SpannableString(text);
        // 遍历要显示的文字
        for (int i = 0; i < text.length(); i++) {
            // 得到单个文字
            String s1 = text.charAt(i) + "";
            // 判断字符串是否包含高亮显示的文字
            if (keyword.contains(s1)) {
                // 循环查找字符串中所有该文字并高亮显示
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#6A5FF8"));
                sp.setSpan(colorSpan, i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return sp;
    }

    /**
     * 设置关键字
     *
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
