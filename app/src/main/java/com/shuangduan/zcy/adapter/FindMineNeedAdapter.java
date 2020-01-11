package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;
import com.shuangduan.zcy.model.bean.NeedBean;
import com.shuangduan.zcy.model.bean.NeedInfoBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/20 14:35
 * @change
 * @chang time
 * @class describe
 */
public class FindMineNeedAdapter extends BaseQuickAdapter<NeedInfoBean, BaseViewHolder> {
    private int type;

    public FindMineNeedAdapter(int layoutResId, @Nullable List<NeedInfoBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, NeedInfoBean item) {
        switch (type) {
            case 1:
                break;
            case 2:
                helper.setText(R.id.tv_title, item.materialName)
                        .setText(R.id.tv_status, item.state)
                        .setGone(R.id.tv_acceptance_price, false)
                        .setText(R.id.tv_count, String.format(mContext.getString(R.string.format_materials_num_unit), item.materialCount,item.unit))
                        .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.startTime, item.endTime));
                break;
            case 3:
                helper.setText(R.id.tv_title, item.remark)
                        .setText(R.id.tv_status, item.state)
                        .setGone(R.id.tv_acceptance_price, false)
                        .setGone(R.id.tv_count, false)
                        .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_validity_period), item.startTime, item.endTime));
                break;
        }

        if (item.state.equals("已提交")) {
            helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.color_F88037));
        } else if (item.state.equals("已完成")) {
            helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.color_EF583E));
        } else {
            helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.colorTvHint));
        }

    }
}
