package com.shuangduan.zcy.adapter;


import androidx.annotation.Nullable;

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

        helper.addOnClickListener(R.id.iv_edit);
    }
}
