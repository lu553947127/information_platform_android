package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.BaseSelectorBean;
import com.shuangduan.zcy.model.bean.TypeBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight.selectorview
 * @class describe  二级分类第二级
 * @time 2019/7/13 9:13
 * @change
 * @chang time
 * @class describe
 */
public class SelectorSecondAdapter<T  extends BaseSelectorBean> extends BaseQuickAdapter<T, BaseViewHolder> {

    public SelectorSecondAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (item instanceof CityBean){
            helper.setText(R.id.tv_city, ((CityBean) item).getName());

            helper.getView(R.id.tv_city).setSelected(item.getIsSelect() == 1);
            helper.getView(R.id.iv_mark).setSelected(item.getIsSelect() == 1);
        }else if (item instanceof StageBean){
            helper.setText(R.id.tv_city, ((StageBean) item).getPhases_name());

            helper.getView(R.id.tv_city).setSelected(item.getIsSelect() == 1);
            helper.getView(R.id.iv_mark).setSelected(item.getIsSelect() == 1);
        }else if (item instanceof TypeBean){
            helper.setText(R.id.tv_city, ((TypeBean) item).getCatname());

            helper.getView(R.id.tv_city).setSelected(item.getIsSelect() == 1);
            helper.getView(R.id.iv_mark).setSelected(item.getIsSelect() == 1);
        }
    }

}
