package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.MaterialRepository;
import com.shuangduan.zcy.model.bean.MaterialAddBean;
import com.shuangduan.zcy.model.bean.MaterialDepositingPlaceBean;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;

import java.math.BigInteger;
import java.util.List;

import retrofit2.http.Field;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/7 14:57
 * @change
 * @chang time
 * @class describe
 */
public class MaterialDetailVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<MaterialDetailBean> detailLiveData;
    public MutableLiveData collectedLiveData;
    public MutableLiveData collectLiveData;
    public MutableLiveData<List<MaterialDepositingPlaceBean>> depositingPlaceBeanMutableLiveData;
    public MutableLiveData<MaterialAddBean> mutableLiveData;
    public MutableLiveData<MaterialAddBean> mutableLiveDataDel;
    public MutableLiveData<MaterialAddBean> mutableLiveAddOrder;
    public MutableLiveData<String> pageStateLiveData;

    //物资预定订单详情
    public MutableLiveData<MaterialOrderBean.ListBean> orderDetailLiveData;
    //取消预定订单
    public MutableLiveData mutableLiveDataCancel;
    public int id, supplier_id;

    public MaterialDetailVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        collectedLiveData = new MutableLiveData();
        collectLiveData = new MutableLiveData();
        depositingPlaceBeanMutableLiveData = new MutableLiveData<>();
        mutableLiveData = new MutableLiveData<>();
        mutableLiveDataDel = new MutableLiveData<>();
        mutableLiveAddOrder = new MutableLiveData<>();
        orderDetailLiveData = new MutableLiveData<>();
        mutableLiveDataCancel = new MutableLiveData();
    }

    //基建物资---周转材料详情
    public void getDetail(int id) {
        new MaterialRepository().materialDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    //基建物资---设备物资详情
    public void getEquipmentDetail(int id) {
        new MaterialRepository().getEquipmentDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    //基建物资---周转材料收藏
    public void getCollected() {
        new MaterialRepository().collected(collectedLiveData, pageStateLiveData, userId, id);
    }

    //基建物资---设备物资收藏
    public void equipmentCollection() {
        new MaterialRepository().equipmentCollection(collectedLiveData, pageStateLiveData, userId, id);
    }

    //基建物资---周转材料取消收藏
    public void getCollect() {
        new MaterialRepository().collect(collectLiveData, pageStateLiveData, userId, id);
    }

    //基建物质---设备物质取消收藏
    public void equipmentCancelCollection() {
        new MaterialRepository().equipmentCancelCollection(collectLiveData, pageStateLiveData, userId, id);
    }

    public void getAddressList(int isShelf) {
        new MaterialRepository().getAddress(depositingPlaceBeanMutableLiveData, pageStateLiveData, userId, id, supplier_id, isShelf);
    }

    public void getAddMaterial(int material_id, BigInteger num, int day, int type) {
        new MaterialRepository().getAddMaterial(mutableLiveData, pageStateLiveData, userId, material_id, num, day, type);
    }

    public void getDelMaterial(int material_id) {
        new MaterialRepository().getDelMaterial(mutableLiveDataDel, pageStateLiveData, userId, material_id);
    }

    //基建物资---周转材料预定订单
    public void getAddMaterialOrder(int material_id, String real_name, String tel, String company, int province, int city
            , String address, String remark, String science_num_id) {
        new MaterialRepository().getAddMaterialOrder(mutableLiveAddOrder, pageStateLiveData, userId, material_id, real_name, tel, company, province, city, address, remark, science_num_id);
    }


    //基建物资---设备物资预定订单
    public void getAddEquipmentOrder(int materialId, String realName, String tel, String company,
                                     int province, int city, String address, String remark, int method, int day, int num, int cateId) {
        new MaterialRepository().getAddEquipmentOrder(mutableLiveAddOrder, pageStateLiveData, userId, materialId, realName, tel, company, province, city, address, remark, method, day, num, cateId);
    }


    //物资预定---周转材料详情
    public void materialOrderDetail(int orderId) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderDetail(orderDetailLiveData, pageStateLiveData, userId, orderId);
    }

    //物资预定---设备物资详情
    public void equipmentOrderDetail(int orderId) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().equipmentOrderDetail(orderDetailLiveData, pageStateLiveData, userId, orderId);
    }

    //基建物资---周转材料取消物资预定
    public void materialOrderCancel(int orderId) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderCancel(mutableLiveDataCancel, pageStateLiveData, userId, orderId);
    }

    //基建物资---设备物质取消物质预定
    public void cancelEquipmentOrder(int orderId) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().cancelEquipmentOrder(mutableLiveDataCancel, pageStateLiveData, userId, orderId);
    }
}
