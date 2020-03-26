package com.shuangduan.zcy.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.google.gson.Gson;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;
import com.shuangduan.zcy.adapter.SelectorSecondAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.MultiAreaEvent;
import com.shuangduan.zcy.vm.MultiAreaVm;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view
 * @class describe  省市多选
 * @time 2019/8/20 9:16
 * @change
 * @chang time
 * @class describe
 */
public class MultiAreaActivity extends BaseActivity {
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
    private MultiAreaVm areaVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_multi_area;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.business_area));
        tvBarRight.setText(getString(R.string.save));

        areaVm = getViewModel(MultiAreaVm.class);
        areaVm.selectAll = false;

        rvProvince.setLayoutManager(new LinearLayoutManager(this));
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        SelectorFirstAdapter provinceAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
        SelectorSecondAdapter cityAdapter = new SelectorSecondAdapter(R.layout.item_city, null);
        rvProvince.setAdapter(provinceAdapter);
        rvCity.setAdapter(cityAdapter);
        //地区点击事件
        provinceAdapter.setOnItemClickListener((adapter, view1, position) -> areaVm.clickFirst(position));
        cityAdapter.setOnItemClickListener((adapter, view12, position) -> areaVm.clickSecond(position));

        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            if (!areaVm.provinceInited) {
                //首次加载，未添加全部，
                areaVm.setProvinceInit();
            } else {
                provinceAdapter.setNewData(provinceBeans);
            }
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            areaVm.setCityInit();
            if (cityBeans != null && cityBeans.size() > 0 && (cityBeans.get(0).getName().equals("全部") || cityBeans.get(0).getName().equals("全国"))) {
                //刷新市区
                cityAdapter.setNewData(cityBeans);
            }
        });
        areaVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        areaVm.getProvince();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                List<Integer> cityResult = areaVm.getProvinceCityIds();
                String stringResult = areaVm.getStringResult();

                Intent intent = new Intent();
                intent.putExtra("citys", new Gson().toJson(cityResult));
                intent.putExtra("citystr", stringResult);
                setResult(200, intent);
                EventBus.getDefault().post(new MultiAreaEvent(cityResult, stringResult));
                finish();
                break;
            default:
                break;
        }
    }
}
