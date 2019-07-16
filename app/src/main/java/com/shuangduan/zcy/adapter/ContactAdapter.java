package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectContentBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 11:00
 * @change
 * @chang time
 * @class describe
 */
public class ContactAdapter extends BaseQuickAdapter<ProjectContentBean, BaseViewHolder> {
    public ContactAdapter(int layoutResId, @Nullable List<ProjectContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectContentBean item) {
        helper.setText(R.id.tv_unit, String.format(mContext.getResources().getString(R.string.format_unit), "滨州市*****单位"))
                .setText(R.id.tv_principal, String.format(mContext.getResources().getString(R.string.format_principal), "王女士"))
                .setText(R.id.tv_phone, String.format(mContext.getResources().getString(R.string.format_phone), "151****1232 "))
                .setText(R.id.tv_address, String.format(mContext.getResources().getString(R.string.format_address), "山东省济南市莱芜区"));
    }
}
