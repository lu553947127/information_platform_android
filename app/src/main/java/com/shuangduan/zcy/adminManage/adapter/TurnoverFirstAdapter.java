package com.shuangduan.zcy.adminManage.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: TurnoverFirstAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/30 9:34
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/30 9:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverFirstAdapter extends BaseQuickAdapter<TurnoverCategoryBean, BaseViewHolder> {
    public TurnoverFirstAdapter(int layoutResId, @Nullable List<TurnoverCategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverCategoryBean item) {
        ImageView imageView = helper.getView(R.id.iv_attn);
        helper.setText(R.id.tv_title,item.getCatname());
        switch (item.getId()){
            case 1://支架及附件
                imageView.setBackgroundResource(R.drawable.icon_turnover_type1);
                break;
            case 37://贝雷及其附属
                imageView.setBackgroundResource(R.drawable.icon_turnover_type2);
                break;
            case 346://滑移模架
                imageView.setBackgroundResource(R.drawable.icon_turnover_type3);
                break;
            case 53://型钢
                imageView.setBackgroundResource(R.drawable.icon_turnover_type4);
                break;
            case 59://钢轨
                imageView.setBackgroundResource(R.drawable.icon_turnover_type5);
                break;
            case 65://管材
                imageView.setBackgroundResource(R.drawable.icon_turnover_type6);
                break;
            case 80://板材
                imageView.setBackgroundResource(R.drawable.icon_turnover_type7);
                break;
            case 111://木材
                imageView.setBackgroundResource(R.drawable.icon_turnover_type8);
                break;
            case 124://电料
                imageView.setBackgroundResource(R.drawable.icon_turnover_type9);
                break;
            case 140://模板
                imageView.setBackgroundResource(R.drawable.icon_turnover_type10);
                break;
            case 244://配件
                imageView.setBackgroundResource(R.drawable.icon_turnover_type11);
                break;
            case 264://其他
                imageView.setBackgroundResource(R.drawable.icon_turnover_type12);
                break;
        }
    }
}
