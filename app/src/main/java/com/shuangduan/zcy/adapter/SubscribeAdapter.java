package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.SubscribeBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 20:42
 * @change
 * @chang time
 * @class describe
 */
public class SubscribeAdapter extends BaseQuickAdapter<SubscribeBean.ListBean, BaseViewHolder> {
    public SubscribeAdapter(int layoutResId, @Nullable List<SubscribeBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubscribeBean.ListBean item) {
        helper.setText(R.id.tv_time, item.getSend_time())
                .setText(R.id.tv_title, item.getTitle())
                .setVisible(R.id.tv_is_view, item.getType()==1&&item.getIs_view()==0)
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent());
    }
}
