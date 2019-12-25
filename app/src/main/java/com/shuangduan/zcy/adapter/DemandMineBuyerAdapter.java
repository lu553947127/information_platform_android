package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/20 14:48
 * @change
 * @chang time
 * @class describe
 */
public class DemandMineBuyerAdapter extends BaseQuickAdapter<DemandBuyerBean.ListBean, BaseViewHolder> {
    public DemandMineBuyerAdapter(int layoutResId, @Nullable List<DemandBuyerBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandBuyerBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getMaterial_name())
                .setText(R.id.tv_acceptance_price,item.getAcceptance_price().equals("面议") ? item.getAcceptance_price() : String.format(mContext.getString(R.string.format_price_accept), item.getAcceptance_price()))
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_count, String.format(mContext.getString(R.string.format_demand_num), item.getCount()))
                .setText(R.id.tv_way, item.getWay() == 1 ? "出售" : "出租");

        switch (item.getStatus()){
            case 1:
                helper.setText(R.id.tv_status, "审核中")
                        .setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.color_F88037));
                break;
            case 2:
                helper.setText(R.id.tv_status, "审核通过")
                        .setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.color_EF583E));
                break;
            case 3:
                helper.setText(R.id.tv_status, "驳回")
                        .setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.colorTvHint));
                break;
            case 4:
                helper.setText(R.id.tv_status, "已取消")
                        .setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.colorTvHint));
                break;
            case 5:
                helper.setText(R.id.tv_status, "失效")
                        .setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.colorTvHint));
                break;
        }
    }
}
