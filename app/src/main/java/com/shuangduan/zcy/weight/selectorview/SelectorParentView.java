package com.shuangduan.zcy.weight.selectorview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;

import java.util.List;

import static com.blankj.utilcode.util.CrashUtils.init;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight
 * @class describe
 * @time 2019/7/13 9:04
 * @change
 * @chang time
 * @class describe
 */
public class SelectorParentView extends BaseSelectorView<ProvinceBean> {
    private int positionProvinceNow = 0;
    private int positionProvincePrevious = -1;

    public SelectorParentView(@NonNull Context context) {
        super(context);
    }

    public SelectorParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectorParentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        adapter = new BaseSelectorAdapter(R.layout.item_province, data);

        adapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
            if (positionProvinceNow == i) return;
            //点击新的省份，直接清空上一个选择的省份
            setSelectState(0);

            //刷新子view数据
            if (dataChangeListener != null){
                dataChangeListener.updateChild(i);
            }

            //选中状态：未选中0，选中1
            data.get(positionProvinceNow).setIsSelect(0);
            data.get(i).setIsSelect(1);
            adapter.notifyDataSetChanged();

            positionProvincePrevious = positionProvinceNow;
            positionProvinceNow = i;
        });
    }

    /**
     * 设置城市数据
     * @param i
     * @return
     */
    public List<CityBean> setCityData(int i){
        List<CityBean> dataCity = data.get(i).getCityList();
        //没有添加全部选项的添加
        if (!dataCity.get(0).getName().equals("全部")){
            CityBean cityBean = new CityBean();
            cityBean.setName("全部");
            cityBean.setId(0);
            cityBean.setIsSelect(0);
            dataCity.add(0, cityBean);
        }
        return dataCity;
    }



    public void setOnDataChangeListener(DataChangeListener dataChangeListener){
        this.dataChangeListener = dataChangeListener;
    }

    private DataChangeListener dataChangeListener;

    public interface DataChangeListener{
        void updateChild(int position);
    }

}
