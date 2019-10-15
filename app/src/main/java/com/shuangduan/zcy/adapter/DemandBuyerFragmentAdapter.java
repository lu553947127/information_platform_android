package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adapter$
 * @class DemandBuyerFragmentAdapter$
 * @class describe
 * @time 2019/10/15 9:54
 * @change
 * @class describe
 */
public class DemandBuyerFragmentAdapter extends BaseQuickAdapter<DemandBuyerBean.ListBean, BaseViewHolder> {
    public DemandBuyerFragmentAdapter(int layoutResId, @Nullable List<DemandBuyerBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandBuyerBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getMaterial_name())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_demand_num, String.format(mContext.getString(R.string.format_demand_num), item.getCount()));
        if (item.getAcceptance_price().equals("面议")){
            helper.setText(R.id.tv_price,  item.getAcceptance_price());
        }else {
            helper.setText(R.id.tv_price, String.format(mContext.getString(R.string.format_amount), item.getAcceptance_price()));
        }
    }
}