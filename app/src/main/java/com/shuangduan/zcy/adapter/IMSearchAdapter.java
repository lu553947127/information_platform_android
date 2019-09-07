package com.shuangduan.zcy.adapter;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.weight.CircleImageView;

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
public class IMSearchAdapter extends BaseQuickAdapter<IMFriendSearchBean.DataBean.FriendBean, BaseViewHolder> {
    public IMSearchAdapter(int layoutResId, @Nullable List<IMFriendSearchBean.DataBean.FriendBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMFriendSearchBean.DataBean.FriendBean item) {
        helper.setText(R.id.tv_name, item.getName());
        CircleImageView ivHead = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getPortraitUri())
                .imageView(ivHead)
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .build());
        ivHead.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.UID, Integer.parseInt(item.getUserId()));
            ActivityUtils.startActivity(bundle, UserInfoActivity.class);
        });
    }
}
