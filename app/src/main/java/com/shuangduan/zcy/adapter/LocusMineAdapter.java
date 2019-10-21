package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.LocusMineBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 20:30
 * @change
 * @chang time
 * @class describe
 */
public class LocusMineAdapter extends BaseQuickAdapter<LocusMineBean.ListBean, BaseViewHolder> {
    public LocusMineAdapter(int layoutResId, @Nullable List<LocusMineBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocusMineBean.ListBean item) {
        helper.setText(R.id.tv_remarks, item.getRemarks())
                .setText(R.id.tv_title, String.format(mContext.getResources().getString(R.string.format_project_intro), item.getTitle()))
                .setText(R.id.tv_time, String.format(mContext.getResources().getString(R.string.format_release_time), item.getCreate_time()));

        switch (item.getStatus()) {
            case 0://待审核
                helper.setText(R.id.tv_state, R.string.audit)
                        .setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.color_F88037));
                break;
            case 1://审核成功
                helper.setText(R.id.tv_state, R.string.pass)
                        .setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.color_EF583E));
                break;
            case 2://审核失败
                helper.setText(R.id.tv_state, R.string.reject)
                        .setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.text2));
                break;
        }
    }
}
