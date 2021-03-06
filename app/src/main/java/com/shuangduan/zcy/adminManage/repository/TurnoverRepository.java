package com.shuangduan.zcy.adminManage.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.adminManage.bean.OrderSearchBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailEditBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.model.api.repository.BaseRepository;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.repository
 * @ClassName: TurnoverRepository
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 15:16
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 15:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverRepository extends BaseRepository {

    //后台管理 --- 周转材料列表
    public void constructionList(MutableLiveData<TurnoverBean> liveData, MutableLiveData<String> pageState, int user_id, int page, int unit_id, int is_shelf, int use_status, int province, int city, String material_name, int supplier_id) {
        request(apiService.constructionList(user_id, page, unit_id, is_shelf, use_status, province, city, material_name, supplier_id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 筛选条件列表
    public void constructionSearch(MutableLiveData<TurnoverTypeBean> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.constructionSearch(user_id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 选择条件历史列表
    public void constructionCategoryHistory(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.constructionCategoryHistory(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 子公司列表
    public void getSupplierInfo(MutableLiveData<List<TurnoverCompanyBean>> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.getSupplierInfo(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料名称类别一级
    public void constructionCategoryParent(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.constructionCategoryParent(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料名称类别二级
    public void constructionCategoryList(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id, String title, int id) {
        request(apiService.constructionCategoryList(user_id, title, id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料详情
    public void getTurnoverDetail(MutableLiveData<TurnoverDetailBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.getTurnoverDetail(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料删除
    public void constructionDelete(MutableLiveData<String> liveData, int userId, int id) {
        request(apiService.constructionDelete(userId, id)).setData(liveData).send();
    }

    //后台管理 --- 周转材料拆分
    public void constructionSplit(MutableLiveData<String> liveData, int userId, int id, String stock,String unit_price, int use_status, int province, int city, String address, double longitude, double latitude) {
        request(apiService.constructionSplit(userId, id, stock,unit_price, use_status, province, city, address, longitude, latitude)).setData(liveData).send();
    }

    //后台管理 --- 周转材料添加--选择项目
    public void projectListData(MutableLiveData<List<TurnoverNameBean>> liveData, MutableLiveData<String> pageState, int userId) {
        request(apiService.projectListData(userId)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料添加
    public void constructionAdd(MutableLiveData<String> liveData, int userId,int unit_id,int category,String material_name,String stock,String unit_price,int unit,String spec,int use_status,int material_status
            ,int province,int city,String address,double longitude,double latitude,String person_liable,String tel,int rapid_wear,int is_shelf
            ,String shelf_start_time,String shelf_end_time,int shelf_type,int method,String guidance_price,String images
            ,String use_count,String plan,String accumulated_amortization,String start_date,String entry_time,String exit_time,String original_price,String net_worth, String remark) {
        request(apiService.constructionAdd(userId,unit_id,category,material_name,stock,unit_price,unit,spec,use_status,material_status
                ,province,city,address,longitude,latitude,person_liable,tel,rapid_wear,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                ,use_count,plan,accumulated_amortization,start_date,entry_time,exit_time,original_price,net_worth,remark)).setData(liveData).send();
    }

    //后台管理 --- 周转材料编辑
    public void constructionEdit(MutableLiveData<String> liveData, int userId,int id,int unit_id,int category,String material_name,String stock,String unit_price,int unit,String spec,int use_status,int material_status
            ,int province,int city,String address,double longitude,double latitude,String person_liable,String tel,int rapid_wear,int is_shelf
            ,String shelf_start_time,String shelf_end_time,int shelf_type,int method,String guidance_price,String images
            ,String use_count,String plan,String accumulated_amortization,String start_date,String entry_time,String exit_time,String original_price,String net_worth, String remark) {
        request(apiService.constructionEdit(userId,id,unit_id,category,material_name,stock,unit_price,unit,spec,use_status,material_status
                ,province,city,address,longitude,latitude,person_liable,tel,rapid_wear,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                ,use_count,plan,accumulated_amortization,start_date,entry_time,exit_time,original_price,net_worth,remark)).setData(liveData).send();
    }

    //后台管理 --- 周转材料编辑详情
    public void constructionEditShow(MutableLiveData<TurnoverDetailEditBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.constructionEditShow(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料列表 筛选项目
    public void getUnitInfo(MutableLiveData<List<TurnoverNameBean>> liveData, MutableLiveData<String> pageState, int userId,int supplier_id) {
        request(apiService.getUnitInfo(userId,supplier_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //订单管理 --- 筛选条件列表
    public void orderSearch(MutableLiveData<OrderSearchBean> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.orderSearch(user_id)).setData(liveData).setPageState(pageState).send();
    }
}
