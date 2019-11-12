package com.shuangduan.zcy.adminManage.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.adminManage.bean.DeviceBean;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailBean;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailEditBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.model.api.repository.BaseRepository;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.repository
 * @ClassName: DeviceRepository
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 9:47
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 9:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceRepository extends BaseRepository {

    //后台管理 --- 设备管理列表
    public void equipmentList(MutableLiveData<DeviceBean> liveData, MutableLiveData<String> pageState, int user_id, int page, int unit_id, int is_shelf, int use_status, int province, int city, int p_category_id, int category_id, int supplier_id) {
        request(apiService.equipmentList(user_id, page, unit_id, is_shelf, use_status, province, city, p_category_id, category_id, supplier_id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 设备管理选择条件历史列表
    public void equipmentCategoryHistory(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.equipmentCategoryHistory(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 设备管理名称类别一级
    public void equipmentCategoryParent(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.equipmentCategoryParent(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 设备管理名称类别二级
    public void equipmentCategoryList(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id, String title, int id) {
        request(apiService.equipmentCategoryList(user_id, title, id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 设备管理删除
    public void equipmentDelete(MutableLiveData<String> liveData, int userId, int id) {
        request(apiService.equipmentDelete(userId, id)).setData(liveData).send();
    }

    //后台管理 --- 设备管理详情
    public void equipmentDetail(MutableLiveData<DeviceDetailBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.equipmentDetail(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 设备管理编辑详情
    public void equipmentEditShow(MutableLiveData<DeviceDetailEditBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.equipmentEditShow(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 设备添加
    public void equipmentAdd(MutableLiveData<String> liveData, int userId,int category,int material_id,String encoding,String stock,int unit,String spec,int use_status
            ,int province,int city,String address,double longitude,double latitude,String person_liable,String tel,int is_shelf
            ,String shelf_start_time,String shelf_end_time,int shelf_type,int method,String guidance_price,String images
            ,int unit_id,String start_date,String brand,String original_price,String main_params,String power,String entry_time,String exit_time,String operator_name,int material_status
            ,String use_month_count,int plan,String technology_detail,String equipment_time) {
        request(apiService.equipmentAdd(userId,category,material_id,encoding,stock,unit,spec,use_status
                ,province,city,address,longitude,latitude,person_liable,tel,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                ,unit_id,start_date,brand,original_price,main_params,power,entry_time,exit_time,operator_name,material_status,use_month_count,plan,technology_detail,equipment_time)).setData(liveData).send();
    }

    //后台管理 --- 设备编辑
    public void equipmentEdit(MutableLiveData<String> liveData, int userId,int id,int category,int material_id,String encoding,String stock,int unit,String spec,int use_status
            ,int province,int city,String address,double longitude,double latitude,String person_liable,String tel,int is_shelf
            ,String shelf_start_time,String shelf_end_time,int shelf_type,int method,String guidance_price,String images
            ,int unit_id,String start_date,String brand,String original_price,String main_params,String power,String entry_time,String exit_time,String operator_name,int material_status
            ,String use_month_count,int plan,String technology_detail,String equipment_time) {
        request(apiService.equipmentEdit(userId,id,category,material_id,encoding,stock,unit,spec,use_status
                ,province,city,address,longitude,latitude,person_liable,tel,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                ,unit_id,start_date,brand,original_price,main_params,power,entry_time,exit_time,operator_name,material_status,use_month_count,plan,technology_detail,equipment_time)).setData(liveData).send();
    }
}
