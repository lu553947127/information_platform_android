package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.model.bean.SupplierDetailBean;
import com.shuangduan.zcy.model.bean.SupplierJoinImageBean;
import com.shuangduan.zcy.model.bean.SupplierStatusBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/12 10:10
 * @change
 * @chang time
 * @class describe
 */
public class SupplierRepository extends BaseRepository {
    public void getSupplier(MutableLiveData<SupplierBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String title, int page){
        request(apiService.supplierList(userId, title, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getSupplierDetail(MutableLiveData<SupplierDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.supplierDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getSupplierJoin(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId,String company,int province,int city, String address,int scale,String company_website,String name, String tel,String product,String authorization,String logo, SupplierJoinImageBean joinImageBean){
        request(apiService.supplierJoin(userId ,company,province,city,address, scale,company_website, name, tel,product ,authorization, logo,joinImageBean )).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //获取供应商审核状态
    public void supplierStatus(MutableLiveData<SupplierStatusBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.supplierStatus(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
