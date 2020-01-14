package com.shuangduan.zcy.adapter;

import android.text.Html;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.RankListBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CornerImageView;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: RankRecommendAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/14 10:35
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/14 10:35
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class RankRecommendAdapter extends BaseQuickAdapter<RankListBean.ListBean, BaseViewHolder> {
    public RankRecommendAdapter(int layoutResId, @Nullable List<RankListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankListBean.ListBean item) {
        String str="成交量：<font color=\"#EF583E\">"+item.getCount()+"</font>";
        helper.setText(R.id.tv_num, String.valueOf(helper.getPosition()+1))
                .setText(R.id.tv_name, item.getUsername())
                .setText(R.id.tv_company, "供应商：" + item.getCompany())
                .setText(R.id.tv_number, Html.fromHtml(str));
        TextView tvNum = helper.getView(R.id.tv_num);
        switch (helper.getPosition()){
            case 0:
                tvNum.setBackgroundResource(R.drawable.icon_gold_medal);
                break;
            case 1:
                tvNum.setBackgroundResource(R.drawable.icon_silver_medal);
                break;
            case 2:
                tvNum.setBackgroundResource(R.drawable.icon_bronze_medal);
                break;
            default:
                tvNum.setBackgroundResource(R.drawable.icon_last_medal);
                break;
        }
        CornerImageView ivHead = helper.getView(R.id.iv_avatar);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getAvatar())
                .placeholder(R.drawable.no_banner)
                .errorPic(R.drawable.no_banner)
                .imageView(ivHead)
                .build());
    }
}
