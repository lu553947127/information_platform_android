package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.MaterialRepository;
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
    public MutableLiveData<MaterialBean> sellLiveData;
    public MutableLiveData<MaterialBean> leaseLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<List<MaterialCategoryBean>> categoryFirstLiveData;
    public MutableLiveData<List<MaterialCategoryBean>> categoryLiveData;
    //物资预定列表数据
    public MutableLiveData<MaterialOrderBean> orderLiveData;


    public int categoryFirstId;//一级分类id， 用于二级数据
    public int categoryId;//二级分类id， 用于列表加载
    private int sellPage;
    private int leasePage;
    private int orderPage;

    public int materialId;

    public int supplierId;

    public String specification;
    //供应方式ID
    public int supplierMethodId;

    //筛选的商品名称
    public String materialName;

    //赛选的供应商名称
    public String supplier;
    //筛选框的供应方式
    public String supplierMethod;


    public MaterialVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        sellLiveData = new MutableLiveData<>();
        leaseLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        categoryFirstLiveData = new MutableLiveData<>();
        categoryLiveData = new MutableLiveData<>();
        orderLiveData = new MutableLiveData<>();


        categoryFirstId = 0;
        categoryId = 0;


        materialId = 0;
        supplierId = 0;
        supplierMethodId =0;

        specification = "";

        supplierMethod = "";
    }

    public void sellList(int materialId, String spec, int supplierId) {
        sellPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, 2, materialId, spec, supplierId, sellPage);
    }

    public void moreSellList(int materialId, String spec, int supplierId) {
        sellPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, 2, materialId, spec, supplierId, sellPage);
    }

    public void leaseList(int materialId, String spec, int supplierId) {
        leasePage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(leaseLiveData, pageStateLiveData, userId, 1, materialId, spec, supplierId, leasePage);
    }

    public void moreLeaseList(int materialId, String spec, int supplierId) {
        leasePage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(leaseLiveData, pageStateLiveData, userId, 1, materialId, spec, supplierId, leasePage);
    }

    //物质预定列表
    public void orderList() {
        orderPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderList(orderLiveData, pageStateLiveData, userId, orderPage);
    }

    public void moreOrderList() {
        orderPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialOrderList(orderLiveData, pageStateLiveData, userId, orderPage);
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
