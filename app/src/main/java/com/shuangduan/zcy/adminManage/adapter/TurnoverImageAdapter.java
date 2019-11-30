package com.shuangduan.zcy.adminManage.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailBean;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adapter$
 * @class LocusImageAdapter$
 * @class describe
 * @time 2019/10/21 11:18
 * @change
 * @class describe
 */
public class TurnoverImageAdapter extends BaseQuickAdapter<TurnoverDetailBean.Images, BaseViewHolder> {
    public TurnoverImageAdapter(int layoutResId, @Nullable List<TurnoverDetailBean.Images> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,TurnoverDetailBean.Images item) {
        Glide.with(mContext).load(item.heade_url).into(helper.<ImageView>getView(R.id.iv));
        helper.addOnClickListener(R.id.iv);
    }
}
