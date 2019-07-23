package com.shuangduan.zcy.adapter;

import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.model.bean.LocusBean;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.PhotoViewActivity;
import com.shuangduan.zcy.view.release.ReleaseProjectActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 11:22
 * @change
 * @chang time
 * @class describe
 */
public abstract class LocusAdapter extends BaseQuickAdapter<TrackBean.ListBean, BaseViewHolder> {
    public LocusAdapter(int layoutResId, @Nullable List<TrackBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrackBean.ListBean item) {
        helper.setText(R.id.tv_time, item.getCreate_time())
                .setGone(R.id.view_line, helper.getAdapterPosition() != getData().size() - 1)
                .setImageResource(R.id.iv_mark, helper.getLayoutPosition() == getData().size() - 1?R.drawable.locus_marker_first:R.drawable.locus_marker_normal)
                .setVisible(R.id.iv_pic_first, item.getImage() != null && item.getImage().size() >= 1)
                .setVisible(R.id.iv_pic_second, item.getImage() != null && item.getImage().size() >= 2)
                .setVisible(R.id.iv_pic_third, item.getImage() != null && item.getImage().size() >= 3)
                .setVisible(R.id.tv_more, item.getImage() != null && item.getImage().size() >= 4)
                .addOnClickListener(R.id.iv_pic_first)
                .addOnClickListener(R.id.iv_pic_second)
                .addOnClickListener(R.id.iv_pic_third)
                .addOnClickListener(R.id.tv_more);

        if (item.getIs_pay() == 1){
            helper.setText(R.id.tv_content, item.getRemarks());
        }else {
            TextView tvContent = helper.getView(R.id.tv_content);
            SpanUtils.with(tvContent)
                    .append(item.getRemarks()).appendSpace(1)
                    .append(mContext.getString(R.string.read_detail))
                    .setFontSize(ConvertUtils.sp2px(13))
                    .setForegroundColor(mContext.getResources().getColor(R.color.colorPrimary))
                    .setClickSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            readDetail(helper.getLayoutPosition(), item.getPrice());
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {

                        }
                    }).create();
        }

        if (item.getImage() != null){
            if (item.getImage().size() >= 1){
                ImageView ivFirst = helper.getView(R.id.iv_pic_first);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImage().get(0).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivFirst).build());
            }
            if (item.getImage().size() >= 2){
                ImageView ivFirst = helper.getView(R.id.iv_pic_second);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImage().get(1).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivFirst).build());
            }
            if (item.getImage().size() >= 3){
                ImageView ivFirst = helper.getView(R.id.iv_pic_third);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImage().get(2).getThumbnail()).placeholder(R.drawable.default_pic).errorPic(R.drawable.default_pic).imageView(ivFirst).build());
            }
        }

    }

    public abstract void readDetail(int position, String price);
}
