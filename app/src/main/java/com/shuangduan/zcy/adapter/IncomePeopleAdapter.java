package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.model.bean.IncomePeopleBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/9 15:57
 * @change
 * @chang time
 * @class describe
 */
public class IncomePeopleAdapter extends BaseQuickAdapter<IncomePeopleBean.ListBean, BaseViewHolder> {
    private int type;

    public IncomePeopleAdapter(int layoutResId, @Nullable List<IncomePeopleBean.ListBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomePeopleBean.ListBean item) {
        helper.setText(R.id.tv_name, StringUtils.isTrimEmpty(item.getUsername()) ? "" : item.getUsername())
                .setText(R.id.tv_time, StringUtils.isTrimEmpty(item.getCreate_time()) ? "" : item.getCreate_time())
                .setText(R.id.tv_price, item.getPrice() + "元")
                .setVisible(R.id.iv_sgs, item.getCardStatus() == 2);

        if (type == CustomConfig.FIRST_DEGREE) {
            helper.setVisible(R.id.cb_add, false);
        } else {
            helper.setChecked(R.id.cb_add, item.getApplyStatus() == 1);
            helper.setText(R.id.cb_add, item.getApplyStatus() == 1 ? "加好友" : "已添加");
            helper.addOnClickListener(R.id.cb_add);
            helper.setEnabled(R.id.cb_add, item.getApplyStatus() == 1);
        }

        ImageView ivHead = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getImage())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(ivHead)
                .build());
    }
}
