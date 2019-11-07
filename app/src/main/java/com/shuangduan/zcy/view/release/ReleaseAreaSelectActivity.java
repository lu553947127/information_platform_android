package com.shuangduan.zcy.view.release;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.CityAdapter;
import com.shuangduan.zcy.adapter.ProvinceAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.event.AddressEvent;
import com.shuangduan.zcy.vm.AreaVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  发布信息，地区选择
 * @time 2019/7/29 17:48
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseAreaSelectActivity extends BaseActivity {
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

    private AreaVm areaVm;
    private CityAdapter cityAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_release_area_select;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        switch (getIntent().getIntExtra(CustomConfig.PROJECT_ADDRESS, 0)){
            case 0:
                tvBarTitle.setText(getString(R.string.people_area));
                tvBarRight.setText(getString(R.string.save));
                break;
            case 1:
                tvBarTitle.setText(getString(R.string.project_area));
                tvBarRight.setText(getString(R.string.next));
                break;
            case 2:
                tvBarTitle.setText(getString(R.string.receiving_area));
                tvBarRight.setText(getString(R.string.save));
                break;
            case 3:
                tvBarTitle.setText(getString(R.string.admin_selector_area));
                tvBarRight.setText(getString(R.string.next));
                break;
        }
        LinearLayoutManager managerProvence = new LinearLayoutManager(this);
        rvProvince.setLayoutManager(managerProvence);
        rvProvince.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_province));
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(R.layout.item_province, null);
        rvProvince.setAdapter(provinceAdapter);

        LinearLayoutManager managerCity = new LinearLayoutManager(this);
        rvCity.setLayoutManager(managerCity);
        cityAdapter = new CityAdapter(R.layout.item_city, null);
        rvCity.setAdapter(cityAdapter);

        provinceAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            areaVm.clickFirst(i);
        });

        cityAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            areaVm.clickSecondSingle(i);
        });

        areaVm = ViewModelProviders.of(this).get(AreaVm.class);
        areaVm.init();
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            //刷新省份
            provinceAdapter.setNewData(provinceBeans);
            areaVm.setProvinceInit();

            areaVm.cityLiveData.observe(this, cityBeans -> {
                //刷新市区
                cityAdapter.setNewData(cityBeans);
            });
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
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                String province = "";
                int provinceId = 0;
                String city = "";
                int cityId = 0;
                List<ProvinceBean> types = areaVm.provinceLiveData.getValue();
                assert types != null;
                for (ProvinceBean bean : types) {
                    if (bean.isSelect == 1) {
                        province = bean.getName();
                        provinceId = bean.getId();
                        List<CityBean> citys = areaVm.cityLiveData.getValue();
                        assert citys != null;
                        for (int i = 0; i < citys.size(); i++) {
                            if (citys.get(i).getIsSelect() == 1) {
                                city = citys.get(i).getName();
                                cityId = citys.get(i).getId();
                                switch (getIntent().getIntExtra(CustomConfig.PROJECT_ADDRESS, 0)) {
                                    case 0://只选择省市区
                                    case 2:
                                        EventBus.getDefault().post(new AddressEvent(province, city, provinceId, cityId));
                                        finish();
                                        break;
                                    case 1://选择省市区并选择地图定位
                                    case 3:
                                        Bundle bundle = new Bundle();
                                        bundle.putString(CustomConfig.PROVINCE_NAME, province);
                                        bundle.putInt(CustomConfig.PROVINCE_ID, provinceId);
                                        bundle.putString(CustomConfig.CITY_NAME, city);
                                        bundle.putInt(CustomConfig.CITY_ID, cityId);
                                        ActivityUtils.startActivity(bundle, LocationMapActivity.class);
                                        break;
                                }
                                return;
                            }
                        }
                        break;
                    }
                }
                ToastUtils.showShort(getString(R.string.select_city_correct));
                break;
        }
    }
}
