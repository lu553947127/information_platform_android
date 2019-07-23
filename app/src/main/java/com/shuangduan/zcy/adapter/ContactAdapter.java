package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectContentBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;

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
public class ContactAdapter extends BaseQuickAdapter<ProjectDetailBean.ContactBean, BaseViewHolder> {
    public ContactAdapter(int layoutResId, @Nullable List<ProjectDetailBean.ContactBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectDetailBean.ContactBean item) {
        helper.setText(R.id.tv_unit, String.format(mContext.getResources().getString(R.string.format_unit), item.getCompany()))
                .setText(R.id.tv_principal, String.format(mContext.getResources().getString(R.string.format_principal), item.getName()))
                .setText(R.id.tv_phone, String.format(mContext.getResources().getString(R.string.format_phone), item.getTel()))
                .setText(R.id.tv_address, String.format(mContext.getResources().getString(R.string.format_address), item.getProvince() + item.getCity()));
    }
}
