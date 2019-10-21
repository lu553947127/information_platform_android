package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.OrderListBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/13 11:07
 * @change
 * @chang time
 * @class describe
 */
public class LocusOrderAdapter extends BaseQuickAdapter<OrderListBean.ListBean, BaseViewHolder> {
    public LocusOrderAdapter(int layoutResId, @Nullable List<OrderListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getRemarks() + ", 负责人: " + item.getName() + " " + item.getTel())
                .setText(R.id.tv_content, item.getProject_title())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_amount, String.format(mContext.getString(R.string.format_pay_amount), item.getPrice()));
    }
}
