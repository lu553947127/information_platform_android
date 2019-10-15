package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adapter$
 * @class FindRelationshipFragmentAdapter$
 * @class describe
 * @time 2019/10/15 9:50
 * @change
 * @class describe
 */
public class FindRelationshipFragmentAdapter extends BaseQuickAdapter<DemandRelationshipBean.ListBean, BaseViewHolder> {
    public FindRelationshipFragmentAdapter(int layoutResId, @Nullable List<DemandRelationshipBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandRelationshipBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_price, String.format(mContext.getString(R.string.format_amount_bi), item.getPrice()));
    }
}
