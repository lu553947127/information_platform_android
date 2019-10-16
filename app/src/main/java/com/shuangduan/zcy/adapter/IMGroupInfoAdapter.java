package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMGroupInfoBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.List;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/9/11 15:33
 * @change
 * @chang time
 * @class describe
 */
public class IMGroupInfoAdapter extends BaseQuickAdapter<IMGroupInfoBean.DataBean.ListBean, BaseViewHolder> {

    public IMGroupInfoAdapter(int layoutResId,@Nullable List<IMGroupInfoBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMGroupInfoBean.DataBean.ListBean item) {
        if (item.getUsername()!=null){
            helper.setText(R.id.tv_name, item.getUsername());
            helper.setVisible(R.id.iv_sgs, item.getCardStatus() == 2);
            CircleImageView ivHead = helper.getView(R.id.iv_icon);
            if (item.getUsername().equals("更多")){
                ImageLoader.load(mContext, new ImageConfig.Builder()
                        .url("")
                        .imageView(ivHead)
                        .placeholder(R.drawable.icon_friend_more)
                        .errorPic(R.drawable.icon_friend_more)
                        .build());
            }else {
                ImageLoader.load(mContext, new ImageConfig.Builder()
                        .url(item.getImage())
                        .imageView(ivHead)
                        .placeholder(R.drawable.default_head)
                        .errorPic(R.drawable.default_head)
                        .build());
            }
        }
    }
}
