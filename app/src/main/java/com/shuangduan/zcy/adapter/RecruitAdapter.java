package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.RecruitBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/12 9:03
 * @change
 * @chang time
 * @class describe
 */
public class RecruitAdapter extends BaseQuickAdapter<RecruitBean, BaseViewHolder> {
    public RecruitAdapter(int layoutResId, @Nullable List<RecruitBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitBean item) {
        helper.setText(R.id.tv_title, "山东省济南市莱芜去影视城电子信息产业园一期")
                .setText(R.id.tv_content, "项目简介项目简介项目简介项目简介项目简介项目简介项目简介")
                .setText(R.id.tv_area, "济南市莱芜区")
                .setText(R.id.tv_time, "2018-5-8");
    }
}
