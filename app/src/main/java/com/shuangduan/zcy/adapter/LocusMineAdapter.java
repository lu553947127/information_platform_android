package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.LocusMineBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 20:30
 * @change
 * @chang time
 * @class describe
 */
public class LocusMineAdapter extends BaseQuickAdapter<LocusMineBean.ListBean, BaseViewHolder> {
    public LocusMineAdapter(int layoutResId, @Nullable List<LocusMineBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocusMineBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_intro, item.getPhases())
                .setText(R.id.tv_time, item.getCreate_time());
    }
}
