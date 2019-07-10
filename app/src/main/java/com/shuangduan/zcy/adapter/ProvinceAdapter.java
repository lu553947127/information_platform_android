package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProvinceBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.adapter
 * @class describe
 * @time 2019/7/9 14:11
 * @change
 * @chang time
 * @class describe
 */
public class ProvinceAdapter extends BaseQuickAdapter<ProvinceBean, BaseViewHolder> {
    public ProvinceAdapter(int layoutResId, @Nullable List<ProvinceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProvinceBean provinceBean) {
        baseViewHolder.setText(R.id.tv_province, provinceBean.getName())
                .addOnClickListener(R.id.tv_province)
                .setVisible(R.id.mark, provinceBean.getIsSelect() == 1)
                .setVisible(R.id.iv_more, provinceBean.getIsSelect() == 1);

        baseViewHolder.getView(R.id.tv_province).setSelected(provinceBean.getIsSelect() == 1);
    }
}
