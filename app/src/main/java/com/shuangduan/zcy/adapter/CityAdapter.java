package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.CityBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.adapter
 * @class describe
 * @time 2019/7/9 15:17
 * @change
 * @chang time
 * @class describe
 */
public class CityAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {
    public CityAdapter(int layoutResId, @Nullable List<CityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CityBean cityBean) {
        baseViewHolder.setText(R.id.tv_city, cityBean.getName())
                .addOnClickListener(R.id.tv_city);

        baseViewHolder.getView(R.id.tv_city).setSelected(cityBean.getIsSelect() == 1);
        baseViewHolder.getView(R.id.iv_mark).setSelected(cityBean.getIsSelect() == 1);
    }
}
