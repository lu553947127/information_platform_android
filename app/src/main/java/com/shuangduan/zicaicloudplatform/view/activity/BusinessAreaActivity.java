package com.shuangduan.zicaicloudplatform.view.activity;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.adapter.CityAdapter;
import com.shuangduan.zicaicloudplatform.adapter.ProvinceAdapter;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;
import com.shuangduan.zicaicloudplatform.model.bean.CityBean;
import com.shuangduan.zicaicloudplatform.model.bean.DistrictBean;
import com.shuangduan.zicaicloudplatform.model.bean.ProvinceBean;

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
    private int positionCityNow = 0;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_business_area;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.business_area));
        tvBarRight.setText(getString(R.string.save));

        LinearLayoutManager managerProvence = new LinearLayoutManager(this);
        rvProvince.setLayoutManager(managerProvence);
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(R.layout.item_province, data);
        rvProvince.setAdapter(provinceAdapter);

        LinearLayoutManager managerCity = new LinearLayoutManager(this);
        rvCity.setLayoutManager(managerCity);
        CityAdapter cityAdapter = new CityAdapter(R.layout.item_city, dataCity);
        rvCity.setAdapter(cityAdapter);



        provinceAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
            if (positionProvinceNow == i) return;
//            cityAdapter.setNewData(dataCity);
            //选中状态：未选中0，选中1
//            data.get(i).setIsSelect(view.isSelected()?0:1);
        });

        cityAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
            if (positionCityNow == i && positionCityNow != 0) return;

        });

    }

    private void getData(){
        try {
            InputStream is = getAssets().open("china_city_data");
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            LogUtils.i(result);
            Gson gson = new Gson();
            data = gson.fromJson(result, new TypeToken<ArrayList<ProvinceBean>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                break;
        }
    }

}
