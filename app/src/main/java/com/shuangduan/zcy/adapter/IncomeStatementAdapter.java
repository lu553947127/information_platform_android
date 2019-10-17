package com.shuangduan.zcy.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.HomeListBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/11 15:24
 * @change
 * @chang time
 * @class describe
 */
public class IncomeStatementAdapter extends BaseQuickAdapter<HomeListBean.ExplainBean, BaseViewHolder> {
    public IncomeStatementAdapter(int layoutResId, @Nullable List<HomeListBean.ExplainBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean.ExplainBean item) {
        helper.setText(R.id.tv_title, helper.getPosition()+1+"、"+item.getTitle());

        TextView tvDes=helper.getView(R.id.tv_des);
        if (helper.getPosition()==0){
            tvDes.setText("购买时间：每日0:00到24:之...");
        }else if (helper.getPosition()==1){
            tvDes.setText("当工程信息有人付费查看详情时...");
        }else if (helper.getPosition()==2){
            tvDes.setText("工程动态可多次查看，可多人付...");
        }else if (helper.getPosition()==3){
            tvDes.setText("当你推荐好友注册成为平台新用...");
        }
    }
}
