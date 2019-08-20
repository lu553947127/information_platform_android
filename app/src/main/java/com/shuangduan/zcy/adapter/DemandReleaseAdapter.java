package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/20 15:16
 * @change
 * @chang time
 * @class describe
 */
public class DemandReleaseAdapter extends BaseQuickAdapter<DemandRelationshipBean.ListBean, BaseViewHolder> {
    public DemandReleaseAdapter(int layoutResId, @Nullable List<DemandRelationshipBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandRelationshipBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_commission, String.format(mContext.getString(R.string.format_commission), item.getPrice()))
                .setText(R.id.tv_status, item.getStatus());
    }
}
