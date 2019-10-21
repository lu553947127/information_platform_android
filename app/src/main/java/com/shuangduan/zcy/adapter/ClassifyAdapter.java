package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ClassifyBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/11 14:57
 * @change
 * @chang time
 * @class describe
 */
public class ClassifyAdapter extends BaseQuickAdapter<ClassifyBean, BaseViewHolder> {

    public ClassifyAdapter(@Nullable List<ClassifyBean> data) {
        super(R.layout.item_classify, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyBean item) {
        helper.setText(R.id.tv_classify, item.getTitle())
                .setImageResource(R.id.iv_icon, item.getImg());
    }
}
