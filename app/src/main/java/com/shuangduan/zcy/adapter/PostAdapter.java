package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.PostBean;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adapter$
 * @class PostAdapter$
 * @class 职位Adapter
 * @time 2019/9/23 20:22
 * @change
 * @class describe
 */
public class PostAdapter extends BaseQuickAdapter<PostBean, BaseViewHolder> {
    public PostAdapter(int layoutResId, @Nullable List<PostBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostBean item) {
        helper.setText(R.id.cb_post, item.name);

        helper.setChecked(R.id.cb_post, item.isSelector == 0 ? false : true);

    }
}
