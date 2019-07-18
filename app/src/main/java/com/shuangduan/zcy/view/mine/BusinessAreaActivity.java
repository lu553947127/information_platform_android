package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.CityAdapter;
import com.shuangduan.zcy.adapter.ProvinceAdapter;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.event.CityEvent;
import com.shuangduan.zcy.vm.AreaVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class describe  业务地区
 * @time 2019/7/9 13:58
 * @change
 * @chang time
 * @class describe
 */
public class BusinessAreaActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_province)
    RecyclerView rvProvince;
    @BindView(R.id.rv_city)
    RecyclerView rvCity;

    private List<ProvinceBean> data;
    private List<CityBean> dataCity;
    private int positionProvinceNow = 0;
    private AreaVm areaVm;
    private int userId;
    private CityAdapter cityAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_business_area;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.business_area));
        tvBarRight.setText(getString(R.string.save));

        LinearLayoutManager managerProvence = new LinearLayoutManager(this);
        rvProvince.setLayoutManager(managerProvence);
        rvProvince.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_province));
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(R.layout.item_province, null);
        rvProvince.setAdapter(provinceAdapter);

        LinearLayoutManager managerCity = new LinearLayoutManager(this);
        rvCity.setLayoutManager(managerCity);
        cityAdapter = new CityAdapter(R.layout.item_city, null);
        rvCity.setAdapter(cityAdapter);

        areaVm = ViewModelProviders.of(this).get(AreaVm.class);
        areaVm.getProvince();
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            data = provinceBeans;
            //初始化数据默认选中第一个
            data.get(0).setIsSelect(1);
            provinceAdapter.setNewData(provinceBeans);
            setCityData(positionProvinceNow);
        });

        provinceAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
            if (positionProvinceNow == i) return;

            setCityData(i);
            //选中状态：未选中0，选中1
            data.get(positionProvinceNow).setIsSelect(0);
            data.get(i).setIsSelect(1);
            provinceAdapter.notifyDataSetChanged();

            positionProvinceNow = i;
        });

        cityAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
            if (i == 0) {
                //选全部
                if (dataCity.get(0).getIsSelect() == 1){
                    //全部选中，修改全部为非选中状态
                    setCitySelectState(0);
                }else {
                    //非全部选中，修改全部为选中状态
                    setCitySelectState(1);
                }
            }else {
                //单一选项
                dataCity.get(i).setIsSelect(dataCity.get(i).getIsSelect() == 1?0:1);
                if (dataCity.get(i).getIsSelect() != 1){
                    //未选中状态，之前就是选中状态，需判断是否为全部选中，是则修改全部为非选中状态
                    if (dataCity.get(0).getIsSelect() == 1){
                        //只有“全部”选项是选中状态才更新状态
                        dataCity.get(0).setIsSelect(0);
                    }
                }else {
                    //选中状态，之前就是未选中状态，需判断是否为全部选中，是则修改全部为选中状态
                    //初始把“全部”更新为选中
                    dataCity.get(0).setIsSelect(1);
                    for (int j = 0; j < dataCity.size(); j++) {
                        if (dataCity.get(j).getIsSelect() != 1){
                            //循环查询，出现未选中就更新第一个选项“全部”的状态
                            dataCity.get(0).setIsSelect(0);
                            break;
                        }
                    }
                }
            }

            cityAdapter.notifyDataSetChanged();
        });

    }

    private void setCityData(int i){
        int id = data.get(i).getId();
        areaVm.getCity(id);
        areaVm.cityLiveData.observe(this, cityBeans -> {
            dataCity = cityBeans;
            //没有添加全部选项的添加
            if (!dataCity.get(0).getName().equals("全部")){
                CityBean cityBean = new CityBean();
                cityBean.setName("全部");
                cityBean.setId(0);
                cityBean.setIsSelect(0);
                dataCity.add(0, cityBean);
            }

            cityAdapter.setNewData(dataCity);
        });
    }

    /**
     * 修改全部城市选中状态
     * @param i
     */
    private void setCitySelectState(int i){
        for (int j = 0; j < dataCity.size(); j++) {
            dataCity.get(j).setIsSelect(i);
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                StringBuilder builder = new StringBuilder();
                List<Integer> list = new ArrayList<>();
                for (int i = 1; i < dataCity.size(); i++) {
                    if (dataCity.get(i).getIsSelect() == 1) {
                        builder.append(dataCity.get(i).getName());
                        list.add(dataCity.get(i).getId());
                    }
                }
                Integer[] citys = list.toArray(new Integer[list.size()]);
                EventBus.getDefault().post(new CityEvent(citys, builder.toString()));
                finish();
                break;
        }
    }

}
