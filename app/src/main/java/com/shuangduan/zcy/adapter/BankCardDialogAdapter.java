package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/6 15:49
 * @change
 * @chang time
 * @class describe
 */
public class BankCardDialogAdapter extends BaseQuickAdapter<BankCardBean, BaseViewHolder> {
    public BankCardDialogAdapter(int layoutResId, @Nullable List<BankCardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardBean item) {
        helper.setText(R.id.tv_name, item.getType_name()+"("+item.getLast_num()+")");
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getIcon())
                .imageView(ivIcon)
                .placeholder(R.drawable.default_pic)
                .errorPic(R.drawable.default_pic)
                .build());
    }
}
