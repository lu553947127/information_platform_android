package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectCollectBean;
import com.shuangduan.zcy.model.bean.ProjectSubBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 20:42
 * @change
 * @chang time
 * @class describe
 */
public class ProjectCollectAdapter extends BaseQuickAdapter<ProjectCollectBean.ListBean, BaseViewHolder> {
    public ProjectCollectAdapter(int layoutResId, @Nullable List<ProjectCollectBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectCollectBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getIntro())
                .setText(R.id.tv_phases, item.getPhases());
    }
}
