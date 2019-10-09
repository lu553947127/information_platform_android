package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
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
        tvBarTitle.setText(getString(R.string.area));
        tvBarRight.setText(getString(R.string.save));

        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);
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
            }else {
                provinceAdapter.setNewData(provinceBeans);
            }
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            areaVm.setCityInit();
            if (cityBeans != null && cityBeans.size() > 0 && (cityBeans.get(0).getName().equals("全部")||cityBeans.get(0).getName().equals("全国"))){
                //刷新市区
                cityAdapter.setNewData(cityBeans);
            }
        });
        areaVm.pageStateLiveData.observe(this, s -> {
            switch (s){
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
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                List<Integer> cityResult = areaVm.getProvinceCityIds();
                if (cityResult == null){
                    ToastUtils.showShort("地区选择不能是全部");
                    return;
                }
                String stringResult = areaVm.getStringResult();
                EventBus.getDefault().post(new MultiAreaEvent(cityResult, stringResult));
                finish();
                break;
                default:
                    break;
        }
    }
}
