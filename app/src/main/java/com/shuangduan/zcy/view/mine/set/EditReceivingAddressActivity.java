package com.shuangduan.zcy.view.mine.set;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaSecondAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.vm.AddressVm;
import com.shuangduan.zcy.vm.MultiAreaVm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class EditReceivingAddressActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.et_address)
    EditText etAddress;


    private AddressVm vm;
    private MultiAreaVm areaVm;


    private List<ProvinceBean> provinceList = new ArrayList<>();
    private List<CityBean> cityList = new ArrayList<>();
    private SelectorAreaFirstAdapter provinceAdapter;
    private SelectorAreaSecondAdapter cityAdapter;
    private BottomSheetDialogs dialog;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_edit_address;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        int type = getIntent().getIntExtra("type", 0);

        tvBarTitle.setText(type == 0 ? R.string.add_receiving_address : R.string.edit_receiving_address);

        vm = ViewModelProviders.of(this).get(AddressVm.class);

        //获取省市数据
        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            provinceList = provinceBeans;
            provinceAdapter.setNewData(provinceList);
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            cityList = cityBeans;
            cityAdapter.setNewData(cityList);
        });


        getBottomSheetDialog(R.layout.dialog_depositing_place);
    }


    @OnClick({R.id.iv_bar_back,R.id.tv_area})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_area:
                dialog.show();
                break;
        }
    }

    //省市弹框
    private void getBottomSheetDialog(int layoutRes) {
        //底部滑动对话框
        dialog = new BottomSheetDialogs(Objects.requireNonNull(this), R.style.BottomSheetStyle);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(layoutRes, null);
        //把布局添加进去
        dialog.setContentView(dialog_view);

        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecyclerView rvProvince = dialog.findViewById(R.id.rv_province);
        RecyclerView rvCity = dialog.findViewById(R.id.rv_city);
        Objects.requireNonNull(rvProvince).setLayoutManager(new LinearLayoutManager(this));
        Objects.requireNonNull(rvCity).setLayoutManager(new LinearLayoutManager(this));
        provinceAdapter = new SelectorAreaFirstAdapter(R.layout.adapter_selector_area_first, null);
        cityAdapter = new SelectorAreaSecondAdapter(R.layout.adapter_selector_area_second, null);
        rvProvince.setAdapter(provinceAdapter);
        rvCity.setAdapter(cityAdapter);
        provinceAdapter.setOnItemClickListener((adapter, view, position) -> {
            areaVm.id = provinceList.get(position).getId();
            areaVm.provinceResult = provinceList.get(position).getName();
            areaVm.getCity();
            provinceAdapter.setIsSelect(provinceList.get(position).getId());
        });
        cityAdapter.setOnItemClickListener((adapter, view, position) -> {

            areaVm.city_id = cityList.get(position).getId();
            cityAdapter.setIsSelect(cityList.get(position).getId());
            tvArea.setText(areaVm.provinceResult +" "+ cityList.get(position).getName());

            dialog.dismiss();
        });
        areaVm.getProvince();
        if (areaVm.id != 0) {
            provinceAdapter.setIsSelect(areaVm.id);
            areaVm.getCity();
        }
        if (areaVm.city_id != 0) {
            cityAdapter.setIsSelect(areaVm.city_id);
        }
    }
}
