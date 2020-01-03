package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;
import com.shuangduan.zcy.model.bean.SmartDesignOrderBean;

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
public class SmartDesignOrderAdapter extends BaseQuickAdapter<SmartDesignOrderBean.SmartDesignOrder, BaseViewHolder> {
    public SmartDesignOrderAdapter(int layoutResId, @Nullable List<SmartDesignOrderBean.SmartDesignOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmartDesignOrderBean.SmartDesignOrder item) {
        helper.setText(R.id.tv_title, item.automateName)
                .setText(R.id.tv_order_num, mContext.getString(R.string.format_order_number, item.orderNo))
                .setText(R.id.tv_state, item.status)
                .setTextColor(R.id.tv_state, item.status.equals("取消") ? mContext.getResources().getColor(R.color.text2) : mContext.getResources().getColor(R.color.text1));
    }
}
