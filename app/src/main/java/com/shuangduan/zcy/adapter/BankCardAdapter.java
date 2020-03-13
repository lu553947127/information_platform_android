package com.shuangduan.zcy.adapter;

import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CornerImageView;
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
public class BankCardAdapter extends BaseQuickAdapter<BankCardBean, BaseViewHolder> {
    public BankCardAdapter(int layoutResId, @Nullable List<BankCardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardBean item) {
        helper.setText(R.id.tv_name, item.getType_name())
                .setText(R.id.tv_card_number, item.getCard_num());
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getIcon())
                .placeholder(R.drawable.wuzhi_default)
                .errorPic(R.drawable.wuzhi_default)
                .imageView(ivIcon)
                .build());
        CornerImageView ivBg = helper.getView(R.id.iv_bg);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getBackground())
                .imageView(ivBg)
                .build());
    }
}
