package com.shuangduan.zcy.weight.selectorview;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight.selectorview
 * @class describe
 * @time 2019/7/13 9:13
 * @change
 * @chang time
 * @class describe
 */
public class BaseSelectorAdapter<T  extends BaseSelectorBean> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseSelectorAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (item instanceof ProvinceBean){
            helper.setText(R.id.tv_province, ((ProvinceBean) item).getName())
                    .addOnClickListener(R.id.tv_province)
                    .setVisible(R.id.mark, item.getIsSelect() == 1)
                    .setVisible(R.id.iv_more, item.getIsSelect() == 1);

            helper.getView(R.id.tv_province).setSelected(item.getIsSelect() == 1);
        }else if (item instanceof CityBean){
            helper.setText(R.id.tv_city, ((CityBean) item).getName())
                    .addOnClickListener(R.id.tv_city);

            helper.getView(R.id.tv_city).setSelected(item.getIsSelect() == 1);
            helper.getView(R.id.iv_mark).setSelected(item.getIsSelect() == 1);
        }
    }

}
