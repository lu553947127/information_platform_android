package com.shuangduan.zcy.adapter;


import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;

import java.util.List;


public class ReceivingAddressAdapter extends BaseQuickAdapter<ReceivingAddressBean.Address, BaseViewHolder> {


    public ReceivingAddressAdapter(int layoutResId, @Nullable List<ReceivingAddressBean.Address> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceivingAddressBean.Address item) {
        helper.setText(R.id.tv_name, item.name)
                .setText(R.id.tv_phone, item.phone)
                .setText(R.id.tv_company, item.company)
                .setText(R.id.tv_address, item.province + item.city + item.address)
                .setVisible(R.id.iv_state, item.state == 1)
                .setGone(R.id.tv_company, !StringUtils.isEmpty(item.company))
                .addOnClickListener(R.id.iv_edit);
    }
}
