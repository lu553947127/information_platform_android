package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IncomeLocusBean;
import com.shuangduan.zcy.model.bean.IncomeReleaseBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/9 15:31
 * @change
 * @chang time
 * @class describe
 */
public class IncomeLocusAdapter extends BaseQuickAdapter<IncomeLocusBean.ListBean, BaseViewHolder> {
    public IncomeLocusAdapter(int layoutResId, @Nullable List<IncomeLocusBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeLocusBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_price, item.getPrice() + "元");
    }
}
