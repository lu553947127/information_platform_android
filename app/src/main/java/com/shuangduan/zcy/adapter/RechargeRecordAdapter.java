package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.RechargeRecordBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/13 16:48
 * @change
 * @chang time
 * @class describe
 */
public class RechargeRecordAdapter extends BaseQuickAdapter<RechargeRecordBean.ListBean, BaseViewHolder> {
    public RechargeRecordAdapter(int layoutResId, @Nullable List<RechargeRecordBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeRecordBean.ListBean item) {
        helper.setText(R.id.tv_title, "充值")
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_amount, "+" + item.getPrice() + "紫金币");
    }
}
