package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.model.bean.SearchBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/23 11:04
 * @change
 * @chang time
 * @class describe
 */
public class SearchAdapter extends BaseQuickAdapter<SearchBean.ListBean, BaseViewHolder> {
    public SearchAdapter(int layoutResId, @Nullable List<SearchBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchBean.ListBean item) {

    }
}
