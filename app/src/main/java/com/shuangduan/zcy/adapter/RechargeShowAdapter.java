package com.shuangduan.zcy.adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.RechargeShowBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/13 14:55
 * @change
 * @chang time
 * @class describe
 */
public class RechargeShowAdapter extends BaseQuickAdapter<RechargeShowBean.ListBean, BaseViewHolder> {
    public RechargeShowAdapter(int layoutResId, @Nullable List<RechargeShowBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeShowBean.ListBean item) {
        helper.setText(R.id.tv_num, item.getCoin() + "紫金币")
                .setText(R.id.tv_price, String.format(mContext.getString(R.string.format_price), item.getPrice()))
                .setTextColor(R.id.tv_num, mContext.getResources().getColor(item.getIsSelect() == 1? R.color.colorPrimary: R.color.colorTvHint))
                .setTextColor(R.id.tv_price, mContext.getResources().getColor(item.getIsSelect() == 1? R.color.colorPrimary: R.color.colorTvHint));
        LinearLayout view = helper.getView(R.id.ll_recharge);
        view.setSelected(item.getIsSelect() == 1);
    }
}
