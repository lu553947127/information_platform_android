package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.SubscribeOrderBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CornerImageView;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: SubscribeOrderAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/7 11:26
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/7 11:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SubscribeNeedAdapter extends BaseQuickAdapter<SubscribeOrderBean.ListBean, BaseViewHolder> {
    public SubscribeNeedAdapter(int layoutResId, @Nullable List<SubscribeOrderBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubscribeOrderBean.ListBean item) {
        helper.setText(R.id.tv_time, item.getSend_time())
                .setVisible(R.id.tv_read, item.getIs_view()==0)
                .setText(R.id.tv_title, StringUtils.isTrimEmpty(item.getMaterialName()) ? "—" : item.getMaterialName())
                .setText(R.id.tv_spec, "规格："+item.getSpec())
                .setText(R.id.tv_address, "合计数量："+item.getTotalNumber()+"， 合计价格："+item.getTotalPrice());

        CornerImageView ivIcon = helper.getView(R.id.iv_attn);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getMaterialImage())
                .imageView(ivIcon)
                .placeholder(R.drawable.wuzhi_default)
                .errorPic(R.drawable.wuzhi_default)
                .build());
    }
}
