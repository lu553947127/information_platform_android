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
 * @ClassName: TurnoverSecondAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/30 10:22
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/30 10:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverSecondAdapter extends BaseQuickAdapter<TurnoverCategoryBean, BaseViewHolder> {
    public TurnoverSecondAdapter(int layoutResId, @Nullable List<TurnoverCategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverCategoryBean item) {
        helper.setText(R.id.tv_title,item.getCatname());
    }
}
