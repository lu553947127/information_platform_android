package com.shuangduan.zcy.weight.selectorview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.CityBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight.selectorview
 * @class describe
 * @time 2019/7/13 9:57
 * @change
 * @chang time
 * @class describe
 */
public class SelectorChildView extends BaseSelectorView<CityBean> {

    public SelectorChildView(@NonNull Context context) {
        super(context);
    }

    public SelectorChildView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectorChildView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        adapter = new BaseSelectorAdapter(R.layout.item_city, data);

        adapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
            /*//点击城市后，如果存在上一个选择的省份，则将上一个省份的选中城市清空
            if (positionProvincePrevious >= 0){
                setCitySelectState(positionProvincePrevious);
            }*/

            if (i == 0) {
                //选全部
                if (data.get(0).getIsSelect() == 1){
                    //全部选中，修改全部为非选中状态
                    setSelectState(0);
                }else {
                    //非全部选中，修改全部为选中状态
                    setSelectState(1);
                }
                adapter.notifyDataSetChanged();
            }else {
                //单一选项
                data.get(i).setIsSelect(data.get(i).getIsSelect() == 1?0:1);
                if (data.get(i).getIsSelect() != 1){
                    //未选中状态，之前就是选中状态，需判断是否为全部选中，是则修改全部为非选中状态
                    if (data.get(0).getIsSelect() == 1){
                        //只有“全部”选项是选中状态才更新状态
                        data.get(0).setIsSelect(0);
                    }
                }else {
                    //选中状态，之前就是未选中状态，需判断是否为全部选中，是则修改全部为选中状态
                    //初始把“全部”更新为选中
                    data.get(0).setIsSelect(1);
                    for (int j = 0; j < data.size(); j++) {
                        if (data.get(j).getIsSelect() != 1){
                            //循环查询，出现未选中就更新第一个选项“全部”的状态
                            data.get(0).setIsSelect(0);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

        });
    }

}
