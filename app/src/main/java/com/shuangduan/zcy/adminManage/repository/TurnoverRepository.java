package com.shuangduan.zcy.adminManage.repository;

import androidx.lifecycle.MutableLiveData;

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
    public void constructionList(MutableLiveData<TurnoverBean> liveData, MutableLiveData<String> pageState, int user_id, int page, int unit_id, int is_shelf, int use_status, int province, int city, int p_category_id, int category_id, int supplier_id) {
        request(apiService.constructionList(user_id, page, unit_id, is_shelf, use_status, province, city, p_category_id, category_id, supplier_id)).setData(liveData).setPageState(pageState).send();
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
    public void constructionSplit(MutableLiveData<String> liveData, int userId, int id, String stock, int use_status, int province, int city, String address, double longitude, double latitude) {
        request(apiService.constructionSplit(userId, id, stock, use_status, province, city, address, longitude, latitude)).setData(liveData).send();
    }

    //后台管理 --- 周转材料添加--选择项目
    public void projectListData(MutableLiveData<List<TurnoverNameBean>> liveData, MutableLiveData<String> pageState, int userId) {
        request(apiService.projectListData(userId)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料添加
    public void constructionAdd(MutableLiveData<String> liveData, int userId,int category,int material_id,String stock,String unit_price,int unit,String spec,int use_status,int material_status
            ,int province,int city,String address,double longitude,double latitude,String person_liable,String tel,int is_shelf
            ,String shelf_start_time,String shelf_end_time,int shelf_type,int method,String guidance_price,String images
            ,int unit_id,int plan,String use_count,String start_date,String entry_time,String exit_time,String accumulated_amortization,String original_price,String net_worth, String remark) {
        request(apiService.constructionAdd(userId,category,material_id,stock,unit_price,unit,spec,use_status,material_status
                ,province,city,address,longitude,latitude,person_liable,tel,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                ,unit_id,plan,use_count,start_date,entry_time,exit_time,accumulated_amortization,original_price,net_worth,remark)).setData(liveData).send();
    }

    //后台管理 --- 周转材料编辑
    public void constructionEdit(MutableLiveData<String> liveData, int userId,int id,int category,int material_id,String stock,String unit_price,int unit,String spec,int use_status,int material_status
            ,int province,int city,String address,double longitude,double latitude,String person_liable,String tel,int is_shelf
            ,String shelf_start_time,String shelf_end_time,int shelf_type,int method,String guidance_price,String images
            ,int unit_id,int plan,String use_count,String start_date,String entry_time,String exit_time,String accumulated_amortization,String original_price,String net_worth, String remark) {
        request(apiService.constructionEdit(userId,id,category,material_id,stock,unit_price,unit,spec,use_status,material_status
                ,province,city,address,longitude,latitude,person_liable,tel,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                ,unit_id,plan,use_count,start_date,entry_time,exit_time,accumulated_amortization,original_price,net_worth,remark)).setData(liveData).send();
    }

    //后台管理 --- 周转材料编辑详情
    public void constructionEditShow(MutableLiveData<TurnoverDetailEditBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.constructionEditShow(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料列表 筛选项目
    public void getUnitInfo(MutableLiveData<List<TurnoverNameBean>> liveData, MutableLiveData<String> pageState, int userId,int supplier_id) {
        request(apiService.getUnitInfo(userId,supplier_id)).setDataList(liveData).setPageState(pageState).send();
    }
}
