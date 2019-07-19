package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.BaseSelectorBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight.selectorview
 * @class describe  二级分类第一级
 * @time 2019/7/13 9:13
 * @change
 * @chang time
 * @class describe
 */
public class SelectorFirstAdapter<T  extends BaseSelectorBean> extends BaseQuickAdapter<T, BaseViewHolder> {

    public SelectorFirstAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (item instanceof ProvinceBean){
            helper.setText(R.id.tv_province, ((ProvinceBean) item).getName())
                    .setVisible(R.id.mark, item.getIsSelect() == 1)
                    .setVisible(R.id.iv_more, item.getIsSelect() == 1);

            helper.getView(R.id.tv_province).setSelected(item.getIsSelect() == 1);
        }else if (item instanceof StageBean){
            helper.setText(R.id.tv_province, ((StageBean) item).getCatname())
                    .setVisible(R.id.mark, item.getIsSelect() == 1)
                    .setVisible(R.id.iv_more, item.getIsSelect() == 1);

            helper.getView(R.id.tv_province).setSelected(item.getIsSelect() == 1);
        }
    }

}
