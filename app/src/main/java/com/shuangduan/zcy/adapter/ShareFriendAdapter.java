package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: ShareFriendAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/31 17:20
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/31 17:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ShareFriendAdapter extends BaseQuickAdapter<IMFriendListBean.ListBean, BaseViewHolder> {

    public ShareFriendAdapter(int layoutResId,@Nullable List<IMFriendListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMFriendListBean.ListBean item) {
        if (item.getName()!=null){
            helper.setText(R.id.tv_name, item.getName());
            helper.setVisible(R.id.iv_sgs, item.getCardStatus() == 2);
            CircleImageView ivHead = helper.getView(R.id.iv_icon);
            if (item.getName().equals("更多")){
                ImageLoader.load(mContext, new ImageConfig.Builder()
                        .url("")
                        .imageView(ivHead)
                        .placeholder(R.drawable.icon_friend_more)
                        .errorPic(R.drawable.icon_friend_more)
                        .build());
            }else {
                ImageLoader.load(mContext, new ImageConfig.Builder()
                        .url(item.getPortraitUri())
                        .imageView(ivHead)
                        .placeholder(R.drawable.default_head)
                        .errorPic(R.drawable.default_head)
                        .build());
            }
        }
    }
}
