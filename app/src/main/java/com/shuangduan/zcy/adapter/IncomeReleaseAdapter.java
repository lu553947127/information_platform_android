package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IncomeReleaseBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/9 15:31
 * @change
 * @chang time
 * @class describe
 */
public class IncomeReleaseAdapter extends BaseQuickAdapter<IncomeReleaseBean.ListBean, BaseViewHolder> {
    public IncomeReleaseAdapter(int layoutResId, @Nullable List<IncomeReleaseBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeReleaseBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_price, item.getPrice() + "元");
    }
}
