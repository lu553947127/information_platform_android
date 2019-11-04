package com.shuangduan.zcy.adminManage.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: MaterialStatusAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/4 13:48
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/4 13:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialStatusAdapter extends BaseQuickAdapter<TurnoverTypeBean.MaterialStatusBean, BaseViewHolder> {
    private int is_select;
    public MaterialStatusAdapter(int layoutResId, @Nullable List<TurnoverTypeBean.MaterialStatusBean> data) {
        super(layoutResId, data);
    }

    public void setIsSelect(int is_select) {
        this.is_select = is_select;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverTypeBean.MaterialStatusBean item) {
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
