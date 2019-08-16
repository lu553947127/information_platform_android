package com.shuangduan.zcy.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.TransRecordBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/16 9:04
 * @change
 * @chang time
 * @class describe
 */
public class TransRecordAdapter extends BaseQuickAdapter<TransRecordBean.ListBean, BaseViewHolder> {
    public TransRecordAdapter(int layoutResId, @Nullable List<TransRecordBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransRecordBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getRemark())
                .setText(R.id.tv_content, item.getFlow_type())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_trans_time), item.getCreate_time()));
        TextView tvPrice = helper.getView(R.id.tv_price);
        if (item.getType() == 1){
            //收入
            tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_EF583E));
            tvPrice.setText(String.format("+%1$s币", item.getPrice()));
        }else if (item.getType() == 2){
            tvPrice.setTextColor(mContext.getResources().getColor(R.color.colorTv));
            tvPrice.setText(String.format("-%1$s币", item.getPrice()));
        }
    }
}
