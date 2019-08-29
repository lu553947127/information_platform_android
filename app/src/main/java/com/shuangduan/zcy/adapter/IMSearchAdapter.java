package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/29 14:41
 * @change
 * @chang time
 * @class describe
 */
public class IMSearchAdapter extends BaseQuickAdapter<IMFriendSearchBean.ListBean, BaseViewHolder> {
    public IMSearchAdapter(int layoutResId, @Nullable List<IMFriendSearchBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMFriendSearchBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getUsername());
        ImageView ivHead = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getImage())
                .imageView(ivHead)
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .build());
    }
}
