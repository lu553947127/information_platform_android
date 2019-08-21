package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.SubstanceDetailBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/21 13:19
 * @change
 * @chang time
 * @class describe
 */
public class SubstanceDetailAdapter extends BaseQuickAdapter<SubstanceDetailBean.ListBean, BaseViewHolder> {
    public SubstanceDetailAdapter(int layoutResId, @Nullable List<SubstanceDetailBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubstanceDetailBean.ListBean item) {
        helper.setText(R.id.tv_material_name, item.getMaterial_name())
                .setText(R.id.tv_supply_type, mContext.getString(item.getWay() == 1? R.string.sell: R.string.lease))
                .setText(R.id.tv_supply_num, item.getCount())
                .setText(R.id.tv_price_supply, String.format(mContext.getString(R.string.format_amount), item.getAcceptance_price()))
                .setText(R.id.tv_owner, item.getReal_name())
                .setText(R.id.tv_contact, item.getTel())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period_less), item.getStart_time(), item.getEnd_time()))
                .setText(R.id.tv_supply_address, item.getAddress())
                .setVisible(R.id.tv_read_detail, item.getIs_pay() != 1)
                .addOnClickListener(R.id.tv_read_detail);
    }
}
