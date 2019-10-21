package com.shuangduan.zcy.adapter;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMGroupListBean;
import com.shuangduan.zcy.rongyun.view.IMGroupDetailsActivity;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/9/9 11:23
 * @change
 * @chang time
 * @class describe
 */
public class IMGroupListAdapter extends BaseQuickAdapter<IMGroupListBean.DataBean.ListBean, BaseViewHolder> {

    public IMGroupListAdapter(int layoutResId, @Nullable List<IMGroupListBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMGroupListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getGroup_name());
        helper.setText(R.id.tv_type, item.getProvince()+item.getCity());
        TextView avatar_tv= helper.getView(R.id.iv_avatar_tv);
        RelativeLayout relativeLayout=helper.getView(R.id.rl);
        if(!TextUtils.isEmpty(item.getGroup_name())){
            String name=item.getGroup_name();
            avatar_tv.setText(name.substring(0,1));
        }
        relativeLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("group_id", item.getGroup_id());
            ActivityUtils.startActivity(bundle, IMGroupDetailsActivity.class);
        });
    }
}
