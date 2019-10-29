package com.shuangduan.zcy.adminManage.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverHistoryBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: TurnoverHistoryAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/29 16:26
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/29 16:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverHistoryAdapter extends BaseQuickAdapter<TurnoverHistoryBean, BaseViewHolder> {
    public TurnoverHistoryAdapter(int layoutResId, @Nullable List<TurnoverHistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverHistoryBean item) {
        helper.setText(R.id.tv_title,item.getCatname());
    }
}
