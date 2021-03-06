package com.shuangduan.zcy.adminManage.vm;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.repository.TurnoverRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.utils.DateUtils;

import java.util.Calendar;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: TurnoverAddVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/4 10:54
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/4 10:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverAddVm extends BaseViewModel {

    private int userId;
    public int unit_id;
    public int category;
    public String materialName;
    public int unit;
    public int use_status;
    public int material_status;
    public int province;
    public int city;
    public String address;
    public double longitude;
    public double latitude;
    public int is_vulnerable;
    public int is_shelf;
    public String todayTime;
    public String shelf_start_time;
    public String shelf_end_time;
    public int shelf_type;//1到期自动公开 2到期自动下架
    public int method;//1出租 2出售
    public String images;

    public String use_count;
    public String plan;
    public String accumulated_amortization;
    public String start_date;
    public String entry_time;
    public String exit_time;
    public String original_price;
    public String net_worth;

    public int editId;

    public MutableLiveData<String> turnoverAddData;
    public MutableLiveData<String> turnoverEditData;
    public MutableLiveData<String> pageStateLiveData;

    public TurnoverAddVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        turnoverAddData = new MutableLiveData<>();
        turnoverEditData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();

        //设置时间为今天
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        todayTime = DateUtils.formatTime(year, month, day);

        shelf_type = 1;
        method = 1;
        is_vulnerable = 1;
    }

    //后台管理 --- 周转材料添加/编辑
    public void constructionAdd(String type,String material_name,String stock,String unit_price,String spec,String person_liable,String tel,String guidance_price,String remark){
        if (unit_id==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_project));
            return;
        }
        if (category==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_category));
            return;
        }
        if (TextUtils.isEmpty(material_name)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_id));
            return;
        }
        if (TextUtils.isEmpty(stock)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_stock));
            return;
        }
        if (TextUtils.isEmpty(unit_price)){
            ToastUtils.showShort(getString(R.string.admin_selector_no_unit_price));
            return;
        }
        if (unit==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_unit));
            return;
        }
        if (use_status==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_use_status));
            return;
        }
        if (material_status==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_status));
            return;
        }
        if (province==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_city));
            return;
        }
        if (city==0) {
            ToastUtils.showShort(getString(R.string.admin_selector_no_material_city));
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
//        if (is_shelf!=2&&TextUtils.isEmpty(images)){
//            ToastUtils.showShort(getString(R.string.admin_selector_no_material_shelf_guidance_images));
//            return;
//        }

        switch (type){
            case "add"://添加周转材料
                new TurnoverRepository().constructionAdd(turnoverAddData,userId,unit_id,category,material_name,stock,unit_price,unit,spec,use_status,material_status
                        ,province,city,address,longitude,latitude,person_liable,tel,is_vulnerable,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                        ,use_count,plan,accumulated_amortization,start_date,entry_time,exit_time,original_price,net_worth,remark);
                break;
            case "edit"://编辑周转材料
                new TurnoverRepository().constructionEdit(turnoverEditData,userId,editId,unit_id,category,material_name,stock,unit_price,unit,spec,use_status,material_status
                        ,province,city,address,longitude,latitude,person_liable,tel,is_vulnerable,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                        ,use_count,plan,accumulated_amortization,start_date,entry_time,exit_time,original_price,net_worth,remark);
                break;
        }
    }
}
