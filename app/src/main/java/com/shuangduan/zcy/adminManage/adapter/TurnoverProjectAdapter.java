package com.shuangduan.zcy.adminManage.adapter;

import android.view.View;
import android.widget.ImageView;

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
    private int is_select;
    public TurnoverProjectAdapter(int layoutResId, @Nullable List<TurnoverNameBean> data) {
        super(layoutResId, data);
    }

    public void setIsSelect(int is_select) {
        this.is_select = is_select;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverNameBean item) {
        ImageView ivSelect=helper.getView(R.id.iv_select);
        if (is_select!=0){
            if (is_select==item.id){
                helper.setText(R.id.tv_city, item.name)
                        .setTextColor(R.id.tv_city,mContext.getResources().getColor(R.color.color_5C54F4));
                ivSelect.setVisibility(View.VISIBLE);
            }else {
                helper.setText(R.id.tv_city, item.name)
                        .setTextColor(R.id.tv_city,mContext.getResources().getColor(R.color.color_666666));
                ivSelect.setVisibility(View.GONE);
            }
        }else {
            helper.setText(R.id.tv_city, item.name)
                    .setTextColor(R.id.tv_city,mContext.getResources().getColor(R.color.color_666666));
            ivSelect.setVisibility(View.GONE);
        }
    }
}
