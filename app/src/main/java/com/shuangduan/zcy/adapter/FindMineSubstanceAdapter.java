package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/20 14:35
 * @change
 * @chang time
 * @class describe
 */
public class FindMineSubstanceAdapter extends BaseQuickAdapter<DemandSubstanceBean.ListBean, BaseViewHolder> {
    public FindMineSubstanceAdapter(int layoutResId, @Nullable List<DemandSubstanceBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandSubstanceBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getMaterial_name())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_demand_num, String.format(mContext.getString(R.string.format_demand_num), item.getCount()))
                .setText(R.id.tv_price, String.format(mContext.getString(R.string.format_price_accept), item.getAcceptance_price()));
    }
}
