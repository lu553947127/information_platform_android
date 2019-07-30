package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ContactBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/30 18:17
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseContactAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
    public ReleaseContactAdapter(int layoutResId, @Nullable List<ContactBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        helper.addOnClickListener(R.id.iv_del)
                .addOnClickListener(R.id.tv_type)
                .addOnClickListener(R.id.tv_address);
    }
}
