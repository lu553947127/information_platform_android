package com.shuangduan.zcy.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.TrackDateilBean;

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
public class LocusImageAdapter extends BaseQuickAdapter<TrackDateilBean.ImageJson, BaseViewHolder> {
    public LocusImageAdapter(int layoutResId, @Nullable List<TrackDateilBean.ImageJson> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrackDateilBean.ImageJson item) {
        Glide.with(mContext).load(item.thumbnail).into(helper.<ImageView>getView(R.id.iv));
        helper.addOnClickListener(R.id.iv);
    }
}
