package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.RankListBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: RankActiveAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/14 9:43
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/14 9:43
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class RankActiveAdapter extends BaseQuickAdapter<RankListBean.ListBean, BaseViewHolder> {
    public RankActiveAdapter(int layoutResId, @Nullable List<RankListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankListBean.ListBean item) {
        helper.setText(R.id.tv_num, String.valueOf(helper.getPosition()+4))
                .setText(R.id.tv_name, item.getUsername())
                .setText(R.id.tv_number, "发布工程信息：" + item.getCount());
        ImageView ivHead = helper.getView(R.id.iv_avatar);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getAvatar())
                .placeholder(R.drawable.icon_no_image)
                .errorPic(R.drawable.icon_no_image)
                .imageView(ivHead)
                .build());
    }
}
