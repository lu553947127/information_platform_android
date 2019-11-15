package com.shuangduan.zcy.adminManage.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

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

    private int construction_order_edit, manage_status;

    public AdminOrderListAdapter(int layoutResId, @Nullable List<AdminOrderBean.OrderList> data, int construction_order_edit, int manage_status) {
        super(layoutResId, data);
        this.construction_order_edit = construction_order_edit;
        this.manage_status = manage_status;
    }

    @Override
    protected void convert(BaseViewHolder helper, AdminOrderBean.OrderList item) {
        helper.setText(R.id.tv_material_name, item.materialIdName)
                .setText(R.id.tv_category, mContext.getString(R.string.format_admin_category, item.categoryName))
                .setText(R.id.tv_project, mContext.getString(R.string.format_admin_project, item.unitName))
                .setText(R.id.tv_company, mContext.getString(R.string.format_admin_company, item.company))
                .setText(R.id.tv_order_number, mContext.getString(R.string.format_admin_order_number, item.orderNumber))
                .setVisible(R.id.tv_inside, item.inside == 3)
                .setVisible(R.id.tv_progress, item.statusUpdate == 1);


        if (item.status.equals("驳回订单")) {
            helper.setText(R.id.tv_order_state, item.status);
            helper.setGone(R.id.ll_edit, false);
        } else {
            helper.setText(R.id.tv_order_state, item.phases);
        }

        Drawable drawable = DrawableUtils.getDrawable(mContext.getResources().getColor(R.color.color_EFEEFD), 3);
        helper.getView(R.id.tv_order_state).setBackground(drawable);



        helper.addOnClickListener(R.id.tv_reject, R.id.tv_progress);

        //权限判断
        LinearLayout linearLayout = helper.getView(R.id.ll_edit);
        switch (manage_status) {
            case 1://普通供应商
            case 2://子公司
                helper.setGone(R.id.tv_company,false);
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case 3://集团
            case 5://集团子账号
                helper.setGone(R.id.tv_company,true);
                linearLayout.setVisibility(View.GONE);
                break;
            case 4://子公司子账号
                helper.setGone(R.id.tv_company,false);
                if (construction_order_edit == 1) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                }
                break;
        }

        //当订单没有驳回功能和修改进度功能是 隐藏
        if (helper.getView(R.id.tv_inside).getVisibility() == View.INVISIBLE && helper.getView(R.id.tv_progress).getVisibility() == View.INVISIBLE) {
            helper.setGone(R.id.ll_edit, false);
        }
    }
}
