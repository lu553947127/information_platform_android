package com.shuangduan.zcy.adminManage.adapter;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.utils.DrawableUtils;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.adapter$
 * @class AdminOrderListAdapter$
 * @class describe
 * @time 2019/11/12 17:20
 * @change
 * @class describe
 */
public class AdminOrderListAdapter extends BaseQuickAdapter<AdminOrderBean.OrderList, BaseViewHolder> {

    public AdminOrderListAdapter(int layoutResId, @Nullable List<AdminOrderBean.OrderList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdminOrderBean.OrderList item) {
        helper.setText(R.id.tv_material_name, item.materialIdName)
                .setText(R.id.tv_category, mContext.getString(R.string.format_admin_category, item.categoryName))
                .setText(R.id.tv_order_number, mContext.getString(R.string.format_admin_order_number, item.orderNumber));

        if (item.status.equals("驳回")){
            helper.setText(R.id.tv_order_state, item.status);
        }else {
            helper.setText(R.id.tv_order_state, item.phases);
        }

        Drawable drawable = DrawableUtils.getDrawable(mContext.getResources().getColor(R.color.color_EFEEFD), 3);
        helper.getView(R.id.tv_order_state).setBackground(drawable);
    }
}
