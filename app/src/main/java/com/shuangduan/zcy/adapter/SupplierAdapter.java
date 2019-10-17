package com.shuangduan.zcy.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.view.PhotoViewActivity;
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

        helper.getView(R.id.iv_pic_first).setOnClickListener(v -> {
//            showPic(item.getImages_json(), 0, helper.getView(R.id.iv_pic_first));
            PictureEnlargeUtils.getPictureEnlargeList((Activity) mContext,list,0);
        });
        helper.getView(R.id.iv_pic_second).setOnClickListener(v -> {
            showPic(item.getImages_json(), 1, helper.getView(R.id.iv_pic_second));
        });
        helper.getView(R.id.iv_pic_third).setOnClickListener(v -> {
            showPic(item.getImages_json(), 2, helper.getView(R.id.iv_pic_third));
        });
        helper.getView(R.id.tv_more).setOnClickListener(v -> {
            showPic(item.getImages_json(), 2, helper.getView(R.id.tv_more));
        });
    }

    /**
     *  图片预览
     */
    private void showPic(List<SupplierBean.ListBean.ImageBean> item, int position, View view){
        ArrayList<String> list = new ArrayList<>();
        for (SupplierBean.ListBean.ImageBean img: item) {
            list.add(img.getSource());
        }

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putStringArrayList(CustomConfig.PHOTO_VIEW_URL_LIST, list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //共享shareElement这个View
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityUtils.getTopActivity(), view, "shareElement");
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class, activityOptionsCompat.toBundle());
        } else {
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class);
        }
    }
}
