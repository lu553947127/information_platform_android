package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/15 10:01
 * @change
 * @chang time
 * @class describe
 */
public class HomeHeadlinesAdapter extends BaseQuickAdapter<HomeListBean.HeadlinesBean, BaseViewHolder> {
    public HomeHeadlinesAdapter(int layoutResId, @Nullable List<HomeListBean.HeadlinesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean.HeadlinesBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, item.getCreate_time());
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getImage())
                .placeholder(R.drawable.wuzhi_default)
                .errorPic(R.drawable.wuzhi_default)
                .imageView(ivIcon)
                .build());
    }
}
