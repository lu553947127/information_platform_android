package com.shuangduan.zcy.adminManage.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: SelectorCategoryFirstAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/4 10:40
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/4 10:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SelectorCategoryFirstAdapter extends BaseQuickAdapter<TurnoverCategoryBean, BaseViewHolder> {
    private int is_select;
    public SelectorCategoryFirstAdapter(int layoutResId, @Nullable List<TurnoverCategoryBean> data) {
        super(layoutResId, data);
    }

    public void setIsSelect(int is_select) {
        this.is_select = is_select;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverCategoryBean item) {
        if (is_select!=0){
            if (is_select==item.getId()){
                helper.setText(R.id.tv_province, item.getCatname())
                        .setTextColor(R.id.tv_province,mContext.getResources().getColor(R.color.color_5C54F4));
            }else {
                helper.setText(R.id.tv_province, item.getCatname())
                        .setTextColor(R.id.tv_province,mContext.getResources().getColor(R.color.color_666666));
            }
        }else {
            helper.setText(R.id.tv_province, item.getCatname())
                    .setTextColor(R.id.tv_province,mContext.getResources().getColor(R.color.color_666666));
        }
    }
}
