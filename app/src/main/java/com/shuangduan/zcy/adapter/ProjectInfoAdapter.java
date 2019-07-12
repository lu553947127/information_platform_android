package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/12 19:38
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoAdapter extends BaseQuickAdapter<ProjectInfoBean, BaseViewHolder> {
    public ProjectInfoAdapter(int layoutResId, @Nullable List<ProjectInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectInfoBean item) {
        helper.setText(R.id.tv_title, "山东省济南市莱芜区影城电子信息产业园一期")
                .setText(R.id.tv_content, "项目简介项目简介项目简介项目简介项目简介项目简介项目简介项目简介")
                .setText(R.id.tv_type, "勘察设计")
                .setText(R.id.tv_readers, String.format(mContext.getString(R.string.format_num_of_readers), 12))
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_update_time), "2018-6-9 15:55"));
    }
}
