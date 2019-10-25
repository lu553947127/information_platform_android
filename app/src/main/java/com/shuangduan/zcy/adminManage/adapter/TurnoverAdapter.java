package com.shuangduan.zcy.adminManage.adapter;

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
    public TurnoverAdapter(int layoutResId, @Nullable List<TurnoverBean.ListBean> data) {
        super(layoutResId, data);
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
        helper.setText(R.id.tv_material, item.getMaterial_id())
                .setText(R.id.tv_company ,"子公司："+item.getCompany())
                .setText(R.id.tv_category ,"分类："+item.getCategory()+"      是否上架："+item.getIs_shelf())
                .setText(R.id.tv_address ,"存放地点："+item.getAddress());
    }
}
