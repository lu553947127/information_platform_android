package com.shuangduan.zcy.adapter;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.List;

/**
 * @author 徐玉 QQ++++++++++++:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/9/9 13:30
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendListAdapter extends BaseQuickAdapter<IMFriendListBean.ListBean, BaseViewHolder> {

    public IMFriendListAdapter(int layoutResId, @Nullable List<IMFriendListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMFriendListBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_type, item.getCompany());
        helper.setVisible(R.id.iv_sgs, item.getCardStatus() == 2);
        CircleImageView ivHead = helper.getView(R.id.iv_avatar);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getPortraitUri())
                .imageView(ivHead)
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .build());
        ivHead.setOnClickListener(view -> {
            if (item.getUserId().equals("18"))return;
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.UID, Integer.parseInt(item.getUserId()));
            ActivityUtils.startActivity(bundle, UserInfoActivity.class);
        });
    }
}
