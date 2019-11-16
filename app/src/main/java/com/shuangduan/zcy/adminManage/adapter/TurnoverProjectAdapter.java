package com.shuangduan.zcy.adminManage.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: TurnoverProjectAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/12 16:33
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/12 16:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverProjectAdapter extends BaseQuickAdapter<TurnoverNameBean, BaseViewHolder> {
    private int is_select,maxEms;
    public TurnoverProjectAdapter(int layoutResId, @Nullable List<TurnoverNameBean> data,int maxEms) {
        super(layoutResId, data);
        this.maxEms = maxEms;
    }

    public void setIsSelect(int is_select) {
        this.is_select = is_select;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverNameBean item) {
        ImageView ivSelect=helper.getView(R.id.iv_select);
        TextView textView=helper.getView(R.id.tv_city);
        LinearLayout linearLayout=helper.getView(R.id.ll_more);
        TextView tvCityLong=helper.getView(R.id.tv_city_long);
        TextView tv_more=helper.getView(R.id.tv_more);

        //判断项目名称过长显示全部
        if (item.name.length()>maxEms){
            linearLayout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            tvCityLong.setText(item.name);
            tv_more.setOnClickListener(v -> {
                linearLayout.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(item.name);
            });
        }else {
            linearLayout.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(item.name);
        }

        if (is_select!=0){
            if (is_select==item.id){
                textView.setTextColor(mContext.getResources().getColor(R.color.color_5C54F4));
                tvCityLong.setTextColor(mContext.getResources().getColor(R.color.color_5C54F4));
                ivSelect.setVisibility(View.VISIBLE);
            }else {
                textView.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                tvCityLong.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                ivSelect.setVisibility(View.GONE);
            }
        }else {
            textView.setTextColor(mContext.getResources().getColor(R.color.color_666666));
            tvCityLong.setTextColor(mContext.getResources().getColor(R.color.color_666666));
            ivSelect.setVisibility(View.GONE);
        }
    }
}
