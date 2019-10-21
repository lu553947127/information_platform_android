package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.adapter
 * @class describe
 * @time 2019/7/10 16:00
 * @change
 * @chang time
 * @class describe
 */
public class ReadHistoryAdapter extends BaseQuickAdapter<ReadHistoryBean, BaseViewHolder> {
    public ReadHistoryAdapter(int layoutResId, @Nullable List<ReadHistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadHistoryBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_read_num), item.getCreate_time()));
    }
}
