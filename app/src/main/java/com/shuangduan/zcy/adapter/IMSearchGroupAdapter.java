package com.shuangduan.zcy.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import java.util.List;

public class IMSearchGroupAdapter extends BaseQuickAdapter<IMFriendSearchBean.DataBean.GroupBean, BaseViewHolder> {
    public IMSearchGroupAdapter(int layoutResId, @Nullable List<IMFriendSearchBean.DataBean.GroupBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMFriendSearchBean.DataBean.GroupBean item) {
        helper.setText(R.id.tv_name, item.getGroup_name());
        TextView avatar_tv= helper.getView(R.id.iv_avatar_tv);
        if(!TextUtils.isEmpty(item.getGroup_name())){
            String name=item.getGroup_name();
            avatar_tv.setText(name.substring(0,1));
        }
    }
}
