package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.model.bean.IncomeProductRightsBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/9 15:42
 * @change
 * @chang time
 * @class describe
 */
public class IncomeProductRightsAdapter extends BaseQuickAdapter<IncomeProductRightsBean, BaseViewHolder> {
    public IncomeProductRightsAdapter(int layoutResId, @Nullable List<IncomeProductRightsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeProductRightsBean item) {

    }
}
