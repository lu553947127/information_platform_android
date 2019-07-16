package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectMineBean;

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
public class ProjectMineAdapter extends BaseQuickAdapter<ProjectMineBean, BaseViewHolder> {
    public ProjectMineAdapter(int layoutResId, @Nullable List<ProjectMineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectMineBean item) {
        helper.setText(R.id.tv_title, "山东省济南市莱芜区影城电子信息产业园一期")
                .setText(R.id.tv_content, "项目简介项目简介项目简介项目简介项目简介项目简介")
                .setText(R.id.tv_time, "2019-06-20");
    }
}
