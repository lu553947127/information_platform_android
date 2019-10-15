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
 * @time 2019/8/20 14:19
 * @change
 * @chang time
 * @class describe
 */
public class FindRelationshipAdapter extends BaseQuickAdapter<DemandRelationshipBean.ListBean, BaseViewHolder> {
    public FindRelationshipAdapter(int layoutResId, @Nullable List<DemandRelationshipBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandRelationshipBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_price, String.format(mContext.getString(R.string.format_amount_bi), item.getPrice()));
    }

    @Nullable
    @Override
    public DemandRelationshipBean.ListBean getItem(int position) {
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
        if (getData().size()>0){
            return Integer.MAX_VALUE;
        }else {
            return getData().size();
        }
    }
}
