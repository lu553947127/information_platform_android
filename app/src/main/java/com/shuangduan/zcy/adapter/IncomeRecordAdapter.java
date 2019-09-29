package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IncomeRecordBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/14 11:41
 * @change
 * @chang time
 * @class describe
 */
public class IncomeRecordAdapter extends BaseQuickAdapter<IncomeRecordBean.ListBean, BaseViewHolder> {
    public IncomeRecordAdapter(int layoutResId, @Nullable List<IncomeRecordBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeRecordBean.ListBean item) {
        helper.setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_price, item.getPrice() + "紫金币");
    }
}
