package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.HeadlinesBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/15 15:11
 * @change
 * @chang time
 * @class describe
 */
public class HeadlinesListAdapter extends BaseQuickAdapter<HeadlinesBean.ListBean, BaseViewHolder> {
    public HeadlinesListAdapter(int layoutResId, @Nullable List<HeadlinesBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HeadlinesBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getSummary())
                .setText(R.id.tv_time, item.getCreate_time());
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getImage())
                .placeholder(R.drawable.default_pic)
                .errorPic(R.drawable.default_pic)
                .imageView(ivIcon)
                .build());
    }
}
