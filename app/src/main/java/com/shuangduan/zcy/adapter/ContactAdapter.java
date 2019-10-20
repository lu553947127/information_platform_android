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
    private int isPay;
    // 0 ：工程列表 1：我的工程
    private int type;

    public ContactAdapter(int layoutResId, @Nullable List<ProjectDetailBean.ContactBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectDetailBean.ContactBean item) {
        helper.setText(R.id.tv_principal, String.format(mContext.getResources().getString(R.string.format_principal), item.getName()))
                .setText(R.id.tv_unit, String.format(mContext.getResources().getString(R.string.format_unit), item.getCompany()))
                .setText(R.id.tv_type, String.format(mContext.getResources().getString(R.string.format_contacts_type), item.getPhone_type()))
                .setText(R.id.tv_address, String.format(mContext.getResources().getString(R.string.format_address), item.getProvince() + item.getCity()));
        switch (type) {
            case 0:
                helper.setTextColor(R.id.tv_principal, mContext.getResources().getColor(R.color.color_646464))
                        .setText(R.id.tv_phone, isPay == 1 ? String.format(mContext.getResources().getString(R.string.format_phone), item.getTel()) :
                                String.format(mContext.getResources().getString(R.string.format_phone_look), item.getTel()))
                        .addOnClickListener(R.id.tv_phone);
                break;
            case 1:
                helper.setTextColor(R.id.tv_principal, mContext.getResources().getColor(R.color.colorTv))
                        .setTextColor(R.id.tv_phone, mContext.getResources().getColor(R.color.color_646464))
                        .setText(R.id.tv_phone, item.getTel());
                break;
        }

    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }
}
