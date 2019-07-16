package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ConsumptionBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 13:43
 * @change
 * @chang time
 * @class describe
 */
public class ConsumptionAdapter extends BaseQuickAdapter<ConsumptionBean, BaseViewHolder> {
    public ConsumptionAdapter(int layoutResId, @Nullable List<ConsumptionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsumptionBean item) {
        helper.setText(R.id.tv_title, "王汪汪")
                .setText(R.id.tv_content, "高级会员")
                .setText(R.id.tv_amount, String.format(mContext.getString(R.string.format_consumption), 2))
                .setImageResource(R.id.iv_header, R.drawable.default_head);
    }
}
