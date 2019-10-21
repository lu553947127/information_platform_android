package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.HomeListBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/11 15:24
 * @change
 * @chang time
 * @class describe
 */
public class IncomeStatementAdapter extends BaseQuickAdapter<HomeListBean.ExplainBean, BaseViewHolder> {
    public IncomeStatementAdapter(int layoutResId, @Nullable List<HomeListBean.ExplainBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean.ExplainBean item) {
        helper.setText(R.id.tv_title, helper.getPosition()+1+"、"+item.getTitle())
        .setText(R.id.tv_des,item.getContent());
    }
}
