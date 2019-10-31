package com.shuangduan.zcy.adminManage.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: TurnoverAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 15:30
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 15:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverAdapter extends BaseQuickAdapter<TurnoverBean.ListBean, BaseViewHolder> {

    private int construction_edit,construction_delete,is_children;
    public TurnoverAdapter(int layoutResId, @Nullable List<TurnoverBean.ListBean> data,int construction_edit,int construction_delete,int is_children) {
        super(layoutResId, data);
        this.construction_edit = construction_edit;
        this.construction_delete = construction_delete;
        this.is_children = is_children;
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverBean.ListBean item) {
        String use_status=item.getUse_status();
        TextView tvUseStatus=helper.getView(R.id.tv_use_status);
        tvUseStatus.setText(use_status);
        if (use_status.equals("在用")){
            tvUseStatus.setBackgroundResource(R.drawable.shape_green);
        }else if (use_status.equals("闲置")){
            tvUseStatus.setBackgroundResource(R.drawable.shape_gray);
        }else if (use_status.equals("出租")){
            tvUseStatus.setBackgroundResource(R.drawable.shape_yellow);
        }else {
            tvUseStatus.setBackgroundResource(R.drawable.shape_red);
        }

        LinearLayout linearLayout=helper.getView(R.id.ll_edit);
        TextView tvEdit=helper.getView(R.id.tv_edit);
        TextView tvDelete=helper.getView(R.id.tv_delete);
        TextView tvSplit=helper.getView(R.id.tv_split);
        if (is_children==0){
            linearLayout.setVisibility(View.VISIBLE);
            if (construction_edit==0&&construction_delete==0){
                linearLayout.setVisibility(View.GONE);
            }else if (construction_edit==1&&construction_delete==0){
                tvEdit.setVisibility(View.VISIBLE);
                tvSplit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.GONE);
            }else if (construction_edit==0&&construction_delete==1){
                tvEdit.setVisibility(View.GONE);
                tvSplit.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
            }
            helper.setText(R.id.tv_material, item.getMaterial_id())
                    .setText(R.id.tv_company ,"材料类别："+item.getCategory())
                    .setText(R.id.tv_category ,"是否上架："+item.getIs_shelf()+"      库存数量："+item.getStock())
                    .setText(R.id.tv_address ,"存放地点："+item.getAddress())
                    .addOnClickListener(R.id.tv_edit)
                    .addOnClickListener(R.id.tv_delete);
        }else {
            linearLayout.setVisibility(View.GONE);
            helper.setText(R.id.tv_material, item.getMaterial_id())
                    .setText(R.id.tv_company ,"子公司："+item.getCompany())
                    .setText(R.id.tv_category ,"分类："+item.getCategory()+"      是否上架："+item.getIs_shelf())
                    .setText(R.id.tv_address ,"存放地点："+item.getAddress())
                    .addOnClickListener(R.id.tv_edit)
                    .addOnClickListener(R.id.tv_delete);
        }
    }
}
