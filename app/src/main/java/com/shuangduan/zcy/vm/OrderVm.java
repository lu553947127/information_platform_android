package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.OrderRepository;
import com.shuangduan.zcy.model.bean.OrderListBean;
import com.shuangduan.zcy.model.bean.OrderSubBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/13 10:51
 * @change
 * @chang time
 * @class describe
 */
public class OrderVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<OrderListBean> projectLiveData;
    public MutableLiveData<OrderListBean> locusLiveData;
    public MutableLiveData<OrderListBean> recruitLiveData;
    public MutableLiveData<OrderListBean> supplierLiveData;
    public MutableLiveData<OrderSubBean> subLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int projectPage;
    private int locusPage;
    private int recruitPage;
    private int supplierPage;
    private int subPage;

    public OrderVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        projectLiveData = new MutableLiveData<>();
        locusLiveData = new MutableLiveData<>();
        recruitLiveData = new MutableLiveData<>();
        supplierLiveData = new MutableLiveData<>();
        subLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    /**
     * 工程信息订单
     */
    public void getProjectOrder(){
        projectPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().projectOrder(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void getMoreProjectOrder(){
        projectPage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().projectOrder(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    /**
     * 动态订单
     */
    public void getLocusOrder(){
        locusPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().locusOrder(locusLiveData, pageStateLiveData, userId, locusPage);
    }

    public void getMoreLocusOrder(){
        locusPage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().locusOrder(locusLiveData, pageStateLiveData, userId, locusPage);
    }

    /**
     * 招采订单
     */
    public void getRecruitOrder(){
        recruitPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().recruitOrder(recruitLiveData, pageStateLiveData, userId, recruitPage);
    }

    public void getMoreRecruitOrder(){
        recruitPage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().recruitOrder(recruitLiveData, pageStateLiveData, userId, recruitPage);
    }

    /**
     * 供应商订单
     */
    public void getSupplierOrder(){
        supplierPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().supplierOrder(supplierLiveData, pageStateLiveData, userId, supplierPage);
    }

    public void getMoreSupplierOrder(){
        supplierPage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().supplierOrder(supplierLiveData, pageStateLiveData, userId, supplierPage);
    }

    /**
     * 已认购订单
     */
    public void getSubOrder(){
        subPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().subOrder(subLiveData, pageStateLiveData, userId, subPage);
    }

    public void getMoreSubOrder(){
        subPage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new OrderRepository().subOrder(subLiveData, pageStateLiveData, userId, subPage);
    }
}
