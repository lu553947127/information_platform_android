package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.MaterialRepository;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.model.bean.MaterialCategoryBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
    public int categoryFirstId;//一级分类id， 用于二级数据
    public int categoryId;//二级分类id， 用于列表加载
    private int sellPage;
    private int leasePage;

    public MaterialVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        sellLiveData = new MutableLiveData<>();
        leaseLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        categoryFirstLiveData = new MutableLiveData<>();
        categoryLiveData = new MutableLiveData<>();
        categoryFirstId = 0;
        categoryId = 0;
    }

    public void sellList(){
        sellPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, categoryId, 1, sellPage);
    }

    public void refreshSellList(){
        sellPage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(sellLiveData, pageStateLiveData, userId, categoryId, 1, sellPage);
    }

    public void leaseList(){
        leasePage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(leaseLiveData, pageStateLiveData, userId, categoryId, 2, leasePage);
    }

    public void refreshLeaseList(){
        leasePage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new MaterialRepository().materialList(leaseLiveData, pageStateLiveData, userId, categoryId, 2, leasePage);
    }

    /**
     * 获取一级分类
     */
    public void getCategoryFirst(){
        new MaterialRepository().getCategory(categoryFirstLiveData, pageStateLiveData, userId, 0);
    }

    /**
     * 获取二级分类
     */
    public void getCategory(){
        new MaterialRepository().getCategory(categoryLiveData, pageStateLiveData, userId, categoryFirstId);
    }
}
