package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.MaterialRepository;
import com.shuangduan.zcy.model.bean.AuthGroupBean;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.model.bean.MaterialCategoryBean;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/7 10:44
 * @change
 * @chang time
 * @class describe
 */
public class MaterialVm extends BaseViewModel {
    private int userId;
    //周转材料
    public MutableLiveData<MaterialBean> sellLiveData;
    //设备物质
    public MutableLiveData<MaterialBean> equipmentLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<List<MaterialCategoryBean>> categoryFirstLiveData;
    public MutableLiveData<List<MaterialCategoryBean>> categoryLiveData;
    //物资预定列表数据
    public MutableLiveData<MaterialOrderBean> orderLiveData;

    //材料列表授权组
    public AuthGroupBean authGroup;

    public int categoryFirstId;//一级分类id， 用于二级数据
    public int categoryId;//二级分类id， 用于列表加载

    private int sellPage;
    private int equipmentPage;
    private int orderPage;

    public int materialId;

    public int supplierId;
    //规格
    public String specification;
    //供应方式ID
    public int supplierMethodId;

    //筛选的商品名称
    public String materialName;

    //赛选的供应商名称
    public String supplier;
    //筛选框的供应方式
    public String supplierMethod;

    //物资大分类 1：公开物资 3：内定物资
    public int materialFlag;


    public MaterialVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        sellLiveData = new MutableLiveData<>();
        equipmentLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        categoryFirstLiveData = new MutableLiveData<>();
        categoryLiveData = new MutableLiveData<>();
        orderLiveData = new MutableLiveData<>();

        authGroup = new AuthGroupBean();
        categoryFirstId = 0;
        categoryId = 0;


        materialId = 0;
        supplierId = 0;
        supplierMethodId = 0;

        specification = "";

        supplierMethod = "";

        materialFlag = 1;
    }

    /**
     * 周转材料
     *
     * @param flag 1：公开物资 3：内定物资
     */
    public void sellList(int flag) {
        sellPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        if (flag == 1) {
            new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, sellPage);
        } else {
            new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, sellPage, authGroup);
        }
    }

    public void moreSellList(int flag) {
        sellPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        if (flag == 1) {
            new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, sellPage);
        } else {
            new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, sellPage, authGroup);
        }
    }

    /**
     * 设备物资
     *
     * @param flag 1：公开物资 3：内定物资
     */
    public void equipmentList(int flag) {
        equipmentPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        if (flag == 1) {
            new MaterialRepository().getEquipmentList(equipmentLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, equipmentPage);
        } else {
            new MaterialRepository().getEquipmentList(equipmentLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, equipmentPage, authGroup);
        }
    }

    public void moreEquipmentList(int flag) {
        equipmentPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        if (flag == 1) {
            new MaterialRepository().getEquipmentList(equipmentLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, equipmentPage);
        } else {
            new MaterialRepository().getEquipmentList(equipmentLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, equipmentPage, authGroup);
        }
    }


    public void leaseList() {
        equipmentPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(equipmentLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, equipmentPage);
    }

    public void moreLeaseList() {
        equipmentPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(equipmentLiveData, pageStateLiveData, userId, supplierMethodId, materialId, specification, supplierId, equipmentPage);
    }

    //基建物资---周转材料预定列表
    public void orderList() {
        orderPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderList(orderLiveData, pageStateLiveData, userId, orderPage, materialFlag);
    }

    public void moreOrderList() {
        orderPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderList(orderLiveData, pageStateLiveData, userId, orderPage, materialFlag);
    }

    //基建物资---设备物资预定列表
    public void getEquipmentOrder() {
        equipmentPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().getEquipmentOrder(orderLiveData, pageStateLiveData, userId, equipmentPage, materialFlag);
    }

    public void getMoreEquipmentOrder() {
        equipmentPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().getEquipmentOrder(orderLiveData, pageStateLiveData, userId, equipmentPage, materialFlag);
    }


    /**
     * 获取一级分类
     */
    public void getCategoryFirst() {
        new MaterialRepository().getCategory(categoryFirstLiveData, pageStateLiveData, userId, 0);
    }

    /**
     * 获取二级分类
     */
    public void getCategory() {
        new MaterialRepository().getCategory(categoryLiveData, pageStateLiveData, userId, categoryFirstId);
    }

}
