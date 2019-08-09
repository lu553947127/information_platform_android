package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IncomePeopleBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/9 15:57
 * @change
 * @chang time
 * @class describe
 */
public class IncomePeopleAdapter extends BaseQuickAdapter<IncomePeopleBean.ListBean, BaseViewHolder> {
    public IncomePeopleAdapter(int layoutResId, @Nullable List<IncomePeopleBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomePeopleBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getUsername())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_price, item.getPrice());
        ImageView ivHead = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getImage())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(ivHead)
                .build());
    }
}
