package com.shuangduan.zcy.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 11:22
 * @change
 * @chang time
 * @class describe
 */
public class LocusReadAdapter extends BaseQuickAdapter<TrackBean.ListBean, BaseViewHolder> {
    public LocusReadAdapter(int layoutResId, @Nullable List<TrackBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrackBean.ListBean item) {
        helper.setText(R.id.tv_time, item.getCreate_time())
                .setGone(R.id.view_line_top, helper.getAdapterPosition() != 0)
                .setGone(R.id.view_line_bottom, helper.getAdapterPosition() != getData().size() - 1)
                .setGone(R.id.iv_pic_first, item.getImage() != null && item.getImage().size() >= 1)
                .setGone(R.id.iv_pic_second, item.getImage() != null && item.getImage().size() >= 2)
                .setGone(R.id.iv_pic_third, item.getImage() != null && item.getImage().size() >= 3)
                .setGone(R.id.tv_more, item.getImage() != null && item.getImage().size() >= 4)
                .setVisible(R.id.iv_sgs, item.getCardStatus() == 2)
                .addOnClickListener(R.id.iv_pic_first)
                .addOnClickListener(R.id.iv_pic_second)
                .addOnClickListener(R.id.iv_pic_third)
                .addOnClickListener(R.id.tv_more)
                .addOnClickListener(R.id.iv_mark);

        TextView tvContent = helper.getView(R.id.tv_content);
        SpanUtils spanUtils = SpanUtils.with(tvContent);
        spanUtils.append(item.getRemarks());
        if (!TextUtils.isEmpty(item.getName())) {
            spanUtils.append("，")
                    .append(mContext.getString(R.string.visitor))
                    .append(":")
                    .append(item.getName());
        }
        if (!TextUtils.isEmpty(item.getTel())) {
            spanUtils
                    .append("，")
                    .append(item.getTel());
        }
        spanUtils.create();

        if (item.getImage() != null) {
            if (item.getImage().size() >= 1) {
                ImageView ivFirst = helper.getView(R.id.iv_pic_first);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImage().get(0).getThumbnail()).placeholder(R.drawable.wuzhi_default).errorPic(R.drawable.wuzhi_default).imageView(ivFirst).build());
            }
            if (item.getImage().size() >= 2) {
                ImageView ivFirst = helper.getView(R.id.iv_pic_second);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImage().get(1).getThumbnail()).placeholder(R.drawable.wuzhi_default).errorPic(R.drawable.wuzhi_default).imageView(ivFirst).build());
            }
            if (item.getImage().size() >= 3) {
                ImageView ivFirst = helper.getView(R.id.iv_pic_third);
                ImageLoader.load(mContext, new ImageConfig.Builder().url(item.getImage().get(2).getThumbnail()).placeholder(R.drawable.wuzhi_default).errorPic(R.drawable.wuzhi_default).imageView(ivFirst).build());
            }
        }

        CircleImageView ivHead = helper.getView(R.id.iv_mark);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getAvatar())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(ivHead)
                .build());

    }
}
