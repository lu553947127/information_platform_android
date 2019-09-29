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

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
    public int id,supplier_id;

    public MaterialDetailVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        collectedLiveData = new MutableLiveData();
        collectLiveData = new MutableLiveData();
        depositingPlaceBeanMutableLiveData = new MutableLiveData<>();
        mutableLiveData = new MutableLiveData<>();
        mutableLiveDataDel = new MutableLiveData<>();
        mutableLiveAddOrder= new MutableLiveData<>();
        orderDetailLiveData = new MutableLiveData<>();
        mutableLiveDataCancel = new MutableLiveData();
    }

    public void getDetail(int id) {
        new MaterialRepository().materialDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    public void getCollected() {
        new MaterialRepository().collected(collectedLiveData, pageStateLiveData, userId, id);
    }

    public void getCollect() {
        new MaterialRepository().collect(collectLiveData, pageStateLiveData, userId, id);
    }

    public void getAddressList() {
        new MaterialRepository().getAddress(depositingPlaceBeanMutableLiveData, pageStateLiveData, userId, id,supplier_id);
    }

    public void getAddMaterial(int material_id, int num) {
        new MaterialRepository().getAddMaterial(mutableLiveData, pageStateLiveData, userId, material_id, num);
    }

    public void getDelMaterial(int material_id) {
        new MaterialRepository().getDelMaterial(mutableLiveDataDel, pageStateLiveData, userId, material_id);
    }

    public void  getAddMaterialOrder(int material_id,String real_name,String tel,String company,int province,int city
            ,String address,String remark,String science_num_id){
        new MaterialRepository().getAddMaterialOrder(mutableLiveAddOrder, pageStateLiveData, userId, material_id,real_name,tel,company,province,city,address,remark,science_num_id);
    }

    //物资预定详情
    public void materialOrderDetail(int orderId) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderDetail(orderDetailLiveData, pageStateLiveData, userId, orderId);
    }

    //取消物资预定
    public void materialOrderCancel(int orderId) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderCancel(mutableLiveDataCancel, pageStateLiveData, userId, orderId);
    }
}
