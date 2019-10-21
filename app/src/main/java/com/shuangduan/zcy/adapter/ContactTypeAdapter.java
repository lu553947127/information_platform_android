package com.shuangduan.zcy.adapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ContactTypeBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/31 8:53
 * @change
 * @chang time
 * @class describe
 */
public class ContactTypeAdapter extends BaseQuickAdapter<ContactTypeBean, BaseViewHolder> {

    public ContactTypeAdapter(int layoutResId, @Nullable List<ContactTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactTypeBean item) {
        helper.setText(R.id.tv_type, item.getType_name());
        TextView type = helper.getView(R.id.tv_type);
        type.setSelected(item.getIsSelect() == 1);
    }
}
