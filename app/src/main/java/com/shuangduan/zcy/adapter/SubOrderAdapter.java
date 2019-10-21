package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.OrderSubBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/13 13:31
 * @change
 * @chang time
 * @class describe
 */
public class SubOrderAdapter extends BaseQuickAdapter<OrderSubBean.ListBean, BaseViewHolder> {

    public SubOrderAdapter(int layoutResId, @Nullable List<OrderSubBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderSubBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_amount_sub, String.format(mContext.getString(R.string.format_amount_sub), item.getPrice()))
                .setText(R.id.tv_amount_return, String.format(mContext.getString(R.string.format_amount_return), item.getSell_price()))
                .setText(R.id.tv_time, item.getCreate_time());
    }
}
