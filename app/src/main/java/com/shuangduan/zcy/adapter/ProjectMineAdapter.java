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
public class ProjectMineAdapter extends BaseQuickAdapter<ProjectMineBean.ListBean, BaseViewHolder> {
    public ProjectMineAdapter(int layoutResId, @Nullable List<ProjectMineBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectMineBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_phases, String.format(mContext.getString(R.string.format_project_phases), item.getPhases()))
                .setText(R.id.tv_valuation, String.format(mContext.getString(R.string.format_project_valuation), item.getValuation()))
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_release_time), item.getCreate_time()));

        switch (item.getStatus()) {
            case 0://待审核
                helper.setText(R.id.tv_state, R.string.audit)
                .setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.color_EF583E));
                break;
            case 1://审核成功
                helper.setText(R.id.tv_state, R.string.pass)
                        .setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.color_F88037));
                break;
            case 2://审核失败
                helper.setText(R.id.tv_state, R.string.failure)
                        .setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.text2));
                break;
        }
    }
}
