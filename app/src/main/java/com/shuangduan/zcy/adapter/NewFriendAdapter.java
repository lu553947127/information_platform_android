package com.shuangduan.zcy.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/29 18:10
 * @change
 * @chang time
 * @class describe
 */
public class NewFriendAdapter extends BaseQuickAdapter<IMFriendApplyListBean.ListBean, BaseViewHolder> {
    public NewFriendAdapter(int layoutResId, @Nullable List<IMFriendApplyListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("NewApi")
    @Override
    protected void convert(BaseViewHolder helper, IMFriendApplyListBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getUsername())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_msg, item.getApply_user_msg())
                .setVisible(R.id.tv_refuse, item.getApply_status() == 1)
                .setVisible(R.id.iv_sgs, item.getCardStatus() == 2);
        CircleImageView ivHead = helper.getView(R.id.civ_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getImage())
                .imageView(ivHead)
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .build());
        TextView tvAccept = helper.getView(R.id.tv_accept);
        switch (item.getApply_status()) {
            case 1:
                helper.addOnClickListener(R.id.tv_accept)
                        .addOnClickListener(R.id.tv_refuse);
                tvAccept.setText("接受");
                tvAccept.setBackground(mContext.getDrawable(R.drawable.selector_btn_confirm));
                tvAccept.setTextColor(ContextCompat.getColor(mContext, R.color.colorFFF));
                break;
            case 2:
                tvAccept.setText("已接受");
                tvAccept.setBackground(mContext.getDrawable(R.drawable.shape_btn_confirm_gray_20));
                tvAccept.setTextColor(ContextCompat.getColor(mContext, R.color.color_646464));
                break;
            case 3:
                tvAccept.setText("已拒绝");
                tvAccept.setBackground(mContext.getDrawable(R.drawable.shape_btn_confirm_gray_20));
                tvAccept.setTextColor(ContextCompat.getColor(mContext, R.color.color_646464));
                break;
        }
    }
}
