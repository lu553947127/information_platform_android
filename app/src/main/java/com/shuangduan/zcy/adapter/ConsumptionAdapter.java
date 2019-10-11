package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

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
public class ConsumptionAdapter extends BaseQuickAdapter<ConsumeBean.ListBean, BaseViewHolder> {
    public ConsumptionAdapter(int layoutResId, @Nullable List<ConsumeBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsumeBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getUsername())
                .setVisible(R.id.iv_sgs, item.getCardStatus() == 2)
                .setText(R.id.tv_amount, String.format(mContext.getString(R.string.format_consumption), item.getCount()));

        ImageView ivHead = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getImage())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(ivHead)
                .build());
    }
}
