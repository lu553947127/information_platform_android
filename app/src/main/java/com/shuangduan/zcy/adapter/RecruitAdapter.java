package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.RecruitBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/12 9:03
 * @change
 * @chang time
 * @class describe
 */
public class RecruitAdapter extends BaseQuickAdapter<RecruitBean.ListBean, BaseViewHolder> {
    public RecruitAdapter(int layoutResId, @Nullable List<RecruitBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_area, item.getProvince() + item.getCity())
                .setText(R.id.tv_time, item.getCreate_time());
    }
}
