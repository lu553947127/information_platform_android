package com.shuangduan.zcy.adapter;

import android.app.Activity;
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
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/12 9:45
 * @change
 * @chang time
 * @class describe
 */
public class SupplierAdapter extends BaseQuickAdapter<SupplierBean.ListBean, BaseViewHolder> {

    public SupplierAdapter(int layoutResId, @Nullable List<SupplierBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SupplierBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_company, item.getCompany())
                .setText(R.id.tv_mobile, item.getTel())
                .setText(R.id.tv_area, String.format(mContext.getString(R.string.format_supplier_service_area), item.getServe_address()))
                .setText(R.id.tv_production, String.format(mContext.getString(R.string.format_supplier_product), item.getProduct()))
                .setGone(R.id.iv_pic_first, item.getImages_json() != null && item.getImages_json().size() >= 1)
                .setGone(R.id.iv_pic_second, item.getImages_json() != null && item.getImages_json().size() >= 2)
                .setGone(R.id.iv_pic_third, item.getImages_json() != null && item.getImages_json().size() >= 3)
                .setGone(R.id.tv_more, item.getImages_json() != null && item.getImages_json().size() >= 4)
                .setGone(R.id.tv_read, item.getIs_pay() != 1)
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
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImages_json().get(0).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivFirst).build());
            }
            if (item.getImages_json().size() >= 2){
                ImageView ivFirst = helper.getView(R.id.iv_pic_second);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImages_json().get(1).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivFirst).build());
            }
            if (item.getImages_json().size() >= 3){
                ImageView ivFirst = helper.getView(R.id.iv_pic_third);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImages_json().get(2).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivFirst).build());
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
}
