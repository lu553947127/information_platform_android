package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/12 19:38
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoAdapter extends BaseQuickAdapter<ProjectInfoBean.ListBean, BaseViewHolder> {
    public ProjectInfoAdapter(int layoutResId, @Nullable List<ProjectInfoBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectInfoBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getIntro())
                .setText(R.id.tv_type, item.getPhases())
                .setText(R.id.tv_readers, String.format(mContext.getString(R.string.format_num_of_readers), item.getSubscription_num()))
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_update_time), item.getUpdate_time()))
                .setVisible(R.id.iv_subscribe, item.getWarrant_status() != 1);
    }
}
