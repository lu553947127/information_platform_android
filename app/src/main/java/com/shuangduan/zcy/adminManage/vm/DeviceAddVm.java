package com.shuangduan.zcy.adminManage.vm;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.repository.DeviceRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.utils.DateUtils;

import java.util.Calendar;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: DeviceAddVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/7 14:41
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/7 14:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceAddVm extends BaseViewModel {

    private int userId;
    public int unit_id;
    public int category;
    public int material_id;
    public String categoryName;
    public String materialName;
    public int unit;
    public int use_status;
    public int province;
    public int city;
    public String address;
    public double longitude;
    public double latitude;
    public int is_shelf;
    public String todayTime;
    public String shelf_start_time;
    public String shelf_end_time;
    public int shelf_type;//1到期自动公开 2到期自动下架
    public int method;//1出租 2出售
    public String images;

    public String brand;
    public String start_date;
    public String operator_name;
    public String original_price;
    public String main_params;
    public String power;
    public String entry_time;
    public String exit_time;

    public int material_status;
    public int plan;
    public int editId;

    public MutableLiveData<String> deviceAddData;
    public MutableLiveData<String> deviceEditData;
    public MutableLiveData<String> pageStateLiveData;

    public DeviceAddVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        deviceAddData = new MutableLiveData<>();
        deviceEditData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();

        //设置时间为今天
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        todayTime = DateUtils.formatTime(year, month, day);
    }

    //后台管理 --- 设备添加/编辑
    public void equipmentAdd(String type,String encoding,String stock,String spec,String person_liable,String tel,String guidance_price
            ,String use_month_count,String technology_detail,String equipment_time){
        if (unit_id==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_project));
            return;
        }
        if (category==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_device_no_category));
            return;
        }
        if (material_id==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_id));
            return;
        }
        if (TextUtils.isEmpty(encoding)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_encoding));
            return;
        }
        if (TextUtils.isEmpty(stock)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_stock));
            return;
        }
        if (unit==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_unit));
            return;
        }
        if (TextUtils.isEmpty(spec)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_spec));
            return;
        }
        if (use_status==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_use_status));
            return;
        }
        if (province==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_address));
            return;
        }
        if (city==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_address));
            return;
        }
        if (TextUtils.isEmpty(address)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_address));
            return;
        }
        if (TextUtils.isEmpty(person_liable)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_person_liable));
            return;
        }
        if (TextUtils.isEmpty(tel)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_tel));
            return;
        }
        if (is_shelf==0){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_is_shelf));
            return;
        }
        if (is_shelf==3&&TextUtils.isEmpty(shelf_start_time)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_shelf_start_time));
            return;
        }
        if (is_shelf==3&&TextUtils.isEmpty(shelf_end_time)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_shelf_end_time));
            return;
        }
        if (is_shelf!=2&&TextUtils.isEmpty(guidance_price)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_shelf_guidance_price));
            return;
        }
        if (is_shelf!=2&&TextUtils.isEmpty(images)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_shelf_guidance_images));
            return;
        }

        switch (type){
            case "add"://添加设备
                new DeviceRepository().equipmentAdd(deviceAddData,userId,unit_id,category,material_id,encoding,stock,unit,spec,use_status
                        ,province,city,address,longitude,latitude,person_liable,tel,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                        ,brand,start_date,operator_name,original_price,main_params,power,entry_time,exit_time,material_status,use_month_count,plan,technology_detail,equipment_time);
                break;
            case "edit"://编辑设备
                new DeviceRepository().equipmentEdit(deviceEditData,userId,editId,unit_id,category,material_id,encoding,stock,unit,spec,use_status
                        ,province,city,address,longitude,latitude,person_liable,tel,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                        ,brand,start_date,operator_name,original_price,main_params,power,entry_time,exit_time,material_status,use_month_count,plan,technology_detail,equipment_time);
                break;
        }
    }
}
