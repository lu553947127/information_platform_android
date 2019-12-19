package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.AuthGroupBean;
import com.shuangduan.zcy.model.bean.MaterialAddBean;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.model.bean.MaterialCategoryBean;
import com.shuangduan.zcy.model.bean.MaterialDepositingPlaceBean;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;

import java.math.BigInteger;
import java.util.List;

import retrofit2.http.Field;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/7 10:52
 * @change
 * @chang time
 * @class describe
 */
public class MaterialRepository extends BaseRepository {
    public void materialList(MutableLiveData<MaterialBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int type,
                             String materialName, String spec, int supplierId, int page) {
        request(apiService.materialList(userId, type, materialName, spec, supplierId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void materialList(MutableLiveData<MaterialBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int type,
                             String materialName, String spec, int supplierId, int page, AuthGroupBean authGroup) {
        request(apiService.materialList(userId, type, materialName, spec, supplierId, page, authGroup)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getEquipmentList(MutableLiveData<MaterialBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int type,
                                 String materialName, String spec, int supplierId, int page) {
        request(apiService.getEquipmentList(userId, type, materialName, spec, supplierId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getEquipmentList(MutableLiveData<MaterialBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int type,
                                 String materialName, String spec, int supplierId, int page, AuthGroupBean authGroup) {
        request(apiService.getEquipmentList(userId, type, materialName, spec, supplierId, page, authGroup)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---周转材料详情
    public void materialDetail(MutableLiveData<MaterialDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.materialDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---设备物资详情
    public void getEquipmentDetail(MutableLiveData<MaterialDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.getEquipmentDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }


    public void getCategory(MutableLiveData<List<MaterialCategoryBean>> liveData, MutableLiveData<String> pageStateLiveData, int userId, int category_id) {
        request(apiService.getCategory(userId, category_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---周转材料收藏
    public void collected(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.collected(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---设备物资收藏
    public void equipmentCollection(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.equipmentCollection(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---取消周转材料收藏
    public void collect(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.collectNew(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---取消设备物资收藏
    public void equipmentCancelCollection(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.equipmentCancelCollection(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }


    public void getAddress(MutableLiveData<List<MaterialDepositingPlaceBean>> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, int supplier_id, int isShelf, int method) {
        request(apiService.addressList(userId, id, supplier_id, isShelf, method)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getAddress(MutableLiveData<List<MaterialDepositingPlaceBean>> liveData, MutableLiveData<String> pageStateLiveData, int userId,
                           int id, int supplier_id, int isShelf, int method, AuthGroupBean auth_group) {
        request(apiService.addressList(userId, id, supplier_id, isShelf, method, auth_group)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getAddMaterial(MutableLiveData<MaterialAddBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int material_id, BigInteger num, int type,
                               String leaseStartTime, String leaseEndTime) {
        request(apiService.getAddMaterial(userId, material_id, num, type, leaseStartTime, leaseEndTime)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getDelMaterial(MutableLiveData<MaterialAddBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int material_id) {
        request(apiService.getDelMaterial(userId, material_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---周转材料预定订单
    public void getAddMaterialOrder(MutableLiveData<MaterialAddBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int material_id, String remark, long buyStock, String leaseStartTime, String leaseEndTime,int address_id) {
        request(apiService.getAddMaterialOrder(userId, material_id, remark, buyStock, leaseStartTime, leaseEndTime,address_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物质---设备物资预定订单
    public void getAddEquipmentOrder(MutableLiveData<MaterialAddBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int materialId,
                                     String realName, String tel, String company, int province, int city, String address, String remark,
                                      long num,  String leaseStartTime, String leaseEndTime) {
        request(apiService.getAddEquipmentOrder(userId, materialId, realName, tel, company, province, city, address,
                remark, num, leaseStartTime, leaseEndTime)).
                setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物质---周转材料预定订单列表
    public void materialOrderList(MutableLiveData<MaterialOrderBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page, int inside) {
        request(apiService.materialOrder(userId, page, inside)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---设备物资预定订单列表
    public void getEquipmentOrder(MutableLiveData<MaterialOrderBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page, int inside) {
        request(apiService.getEquipmentOrder(userId, page, inside)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---周转材料预定订单详情
    public void materialOrderDetail(MutableLiveData<MaterialOrderBean.ListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int orderId) {
        request(apiService.materialOrderDetail(userId, orderId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---设备详情预定订单详情
    public void equipmentOrderDetail(MutableLiveData<MaterialOrderBean.ListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int orderId) {
        request(apiService.equipmentOrderDetail(userId, orderId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---周转材料取消物资预定
    public void materialOrderCancel(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int orderId) {
        request(apiService.cancelMaterialOrder(userId, orderId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //基建物资---设备物资取消物资预定
    public void cancelEquipmentOrder(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int orderId) {
        request(apiService.cancelEquipmentOrder(userId, orderId)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
