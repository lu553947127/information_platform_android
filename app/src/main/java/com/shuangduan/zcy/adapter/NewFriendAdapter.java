package com.shuangduan.zcy.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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

    @Override
    protected void convert(BaseViewHolder helper, IMFriendApplyListBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getUsername())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_msg, item.getApply_user_msg())
                .setVisible(R.id.tv_refuse, item.getApply_status() == 1);

        TextView tvAccept = helper.getView(R.id.tv_accept);
        switch (item.getApply_status()){
            case 1:
                helper.addOnClickListener(R.id.tv_accept)
                        .addOnClickListener(R.id.tv_refuse);
                break;
            case 2:
                tvAccept.setText("已接受");
                tvAccept.setBackgroundColor(mContext.getResources().getColor(R.color.color_F4F2F7));
                break;
            case 3:
                tvAccept.setText("已拒绝");
                tvAccept.setBackgroundColor(mContext.getResources().getColor(R.color.color_F4F2F7));
                break;
        }
    }
}
