package com.shuangduan.zcy.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.WithdrawRecordBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/7 9:07
 * @change
 * @chang time
 * @class describe
 */
public class WithdrawRecordAdapter extends BaseQuickAdapter<WithdrawRecordBean.ListBean, BaseViewHolder> {
    private String[] states = {"审核中", "成功", "驳回"};
    private String[] statesColor = {"#EF813E", "#EF583E", "#B1B1B1"};
    public WithdrawRecordAdapter(int layoutResId, @Nullable List<WithdrawRecordBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawRecordBean.ListBean item) {
        helper.setText(R.id.tv_bank, item.getBankcard_type())
                .setText(R.id.tv_time, item.getUpdate_time())
                .setText(R.id.tv_amount, item.getPrice());
        TextView tvState = helper.getView(R.id.tv_state);
        switch (item.getStatus()){
            case 1:
            case 2:
            case 3:
                tvState.setText(states[item.getStatus() - 1]);
                tvState.setTextColor(Color.parseColor(statesColor[item.getStatus() - 1]));
                break;
        }
    }
}
