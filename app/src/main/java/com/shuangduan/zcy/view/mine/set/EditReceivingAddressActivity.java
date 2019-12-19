package com.shuangduan.zcy.view.mine.set;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaSecondAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;
import com.shuangduan.zcy.vm.AddressVm;
import com.shuangduan.zcy.vm.MultiAreaVm;
import com.shuangduan.zcy.weight.SwitchView;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.tv_bar_right)
    TextView tvBarRight;


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.tv_area)
    TextView tvArea;

    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.sv_address)
    SwitchView switchView;

    private AddressVm vm;
    private MultiAreaVm areaVm;


    private List<ProvinceBean> provinceList = new ArrayList<>();
    private List<CityBean> cityList = new ArrayList<>();
    private SelectorAreaFirstAdapter provinceAdapter;
    private SelectorAreaSecondAdapter cityAdapter;
    private BottomSheetDialogs dialog;
    private int type;
    private ReceivingAddressBean.Address mAddress;

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

        type = getIntent().getIntExtra("type", 0);

        mAddress = getIntent().getParcelableExtra("address");

        tvBarTitle.setText(type == 0 ? R.string.add_receiving_address : R.string.edit_receiving_address);

        tvBarRight.setText(type == 0 ? "" : "删除");

        vm = ViewModelProviders.of(this).get(AddressVm.class);

        //获取省市数据
        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);

        if (mAddress != null) {
            etName.setText(mAddress.name);
            etPhone.setText(mAddress.phone);
            etCompany.setText(mAddress.company);
            tvArea.setText(mAddress.province + " " + mAddress.city);
            etAddress.setText(mAddress.address);
            switchView.setOpened(mAddress.state == 1);
            areaVm.id = mAddress.provinceId;
            areaVm.city_id = mAddress.cityId;
        }

        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            provinceList = provinceBeans;
            provinceAdapter.setNewData(provinceList);
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            cityList = cityBeans;
            cityAdapter.setNewData(cityList);
        });

        //新建地址
        vm.newAddressLiveData.observe(this, newAddress -> {
            EventBus.getDefault().post(new MutableLiveData());
            finish();
        });

        //编辑地址
        vm.editAddressLiveData.observe(this, editAddress -> {
            EventBus.getDefault().post(new MutableLiveData());
            finish();
        });

        vm.deleteLiveData.observe(this, deleteAddress -> {
            EventBus.getDefault().post(mAddress);
            finish();
        });

        getBottomSheetDialog(R.layout.dialog_depositing_place);
    }


    @OnClick({R.id.iv_bar_back, R.id.tv_area, R.id.tv_save, R.id.tv_bar_right})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_area:
                dialog.show();
                break;
            case R.id.tv_bar_right:
                vm.deleteAddress(mAddress.id);
                break;
            case R.id.tv_save:
                //默认状态
                int state = !switchView.isOpened() ? 0 : 1;
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String company = etCompany.getText().toString();
                String address = etAddress.getText().toString();
                if (type == 0) {
                    vm.newAddress(name, phone, company, areaVm.id, areaVm.city_id, address, state);
                } else {
                    vm.editAddress(mAddress.id, name, phone, company, areaVm.id, areaVm.city_id, address, state);
                }
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
            areaVm.cityResult = cityList.get(position).getName();
            tvArea.setText(areaVm.provinceResult + " " + areaVm.cityResult);

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
