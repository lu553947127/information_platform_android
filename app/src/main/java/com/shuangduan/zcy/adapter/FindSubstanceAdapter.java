package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/20 14:35
 * @change
 * @chang time
 * @class describe
 */
public class FindSubstanceAdapter extends BaseQuickAdapter<DemandSubstanceBean.ListBean, BaseViewHolder> {
    public FindSubstanceAdapter(int layoutResId, @Nullable List<DemandSubstanceBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandSubstanceBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getMaterial_name())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_demand_num, String.format(mContext.getString(R.string.format_demand_num), item.getCount()));

        if (item.getAcceptance_price().equals("面议")){
            helper.setText(R.id.tv_price,  item.getAcceptance_price());
        }else {
            helper.setText(R.id.tv_price, String.format(mContext.getString(R.string.format_amount), item.getAcceptance_price()));
        }
    }

    @Nullable
    @Override
    public DemandSubstanceBean.ListBean getItem(int position) {
        int newPosition = position % getData().size();
        return getData().get(newPosition);
    }

    @Override
    public int getItemViewType(int position) {
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        int count = getHeaderLayoutCount() + getData().size();
        if (count <= 0) {
            count = 1;
        }
        int newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

    @Override
    public int getItemCount() {
        if (getData().size()>2){
            return Integer.MAX_VALUE;
        }else {
            return getData().size();
        }
    }
}
