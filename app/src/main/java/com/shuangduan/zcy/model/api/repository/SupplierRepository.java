package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.model.bean.SupplierDetailBean;
import com.shuangduan.zcy.model.bean.SupplierJoinImageBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/12 10:10
 * @change
 * @chang time
 * @class describe
 */
public class SupplierRepository extends BaseRepository {
    public void getSupplier(MutableLiveData<SupplierBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.supplierList(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getSupplierDetail(MutableLiveData<SupplierDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.supplierDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getSupplierJoin(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, String name, String tel, String company, String address, String product, int province, int city,SupplierJoinImageBean joinImageBean){
        request(apiService.supplierJoin(userId, name, tel, company, address, product, province, city, joinImageBean)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
