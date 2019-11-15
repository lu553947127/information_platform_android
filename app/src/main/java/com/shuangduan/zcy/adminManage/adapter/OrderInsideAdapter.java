package com.shuangduan.zcy.adminManage.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.OrderSearchBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: OrderInsideAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/15 10:11
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/15 10:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderInsideAdapter extends BaseQuickAdapter<OrderSearchBean.InsideBean, BaseViewHolder> {
    private int is_select;
    public OrderInsideAdapter(int layoutResId, @Nullable List<OrderSearchBean.InsideBean> data) {
        super(layoutResId, data);
    }

    public void setIsSelect(int is_select) {
        this.is_select = is_select;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderSearchBean.InsideBean item) {
        ImageView ivSelect=helper.getView(R.id.iv_select);
        if (is_select!=0){
            if (is_select==item.getId()){
                helper.setText(R.id.tv_city, item.getName())
                        .setTextColor(R.id.tv_city,mContext.getResources().getColor(R.color.color_5C54F4));
                ivSelect.setVisibility(View.VISIBLE);
            }else {
                helper.setText(R.id.tv_city, item.getName())
                        .setTextColor(R.id.tv_city,mContext.getResources().getColor(R.color.color_666666));
                ivSelect.setVisibility(View.GONE);
            }
        }else {
            helper.setText(R.id.tv_city, item.getName())
                    .setTextColor(R.id.tv_city,mContext.getResources().getColor(R.color.color_666666));
            ivSelect.setVisibility(View.GONE);
        }
    }
}
