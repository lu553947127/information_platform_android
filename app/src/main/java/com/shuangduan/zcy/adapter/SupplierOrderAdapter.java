package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.OrderListBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/13 11:07
 * @change
 * @chang time
 * @class describe
 */
public class SupplierOrderAdapter extends BaseQuickAdapter<OrderListBean.ListBean, BaseViewHolder> {
    public SupplierOrderAdapter(int layoutResId, @Nullable List<OrderListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_content, item.getCompany())
                .setText(R.id.tv_mobile, item.getTel())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_amount, String.format(mContext.getString(R.string.format_pay_amount), item.getPrice()));
        ImageView ivHeader = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getHeadimg())
                .imageView(ivHeader)
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .build());
    }
}
