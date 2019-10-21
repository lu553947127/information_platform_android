package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.OrderListBean;
import com.shuangduan.zcy.model.bean.OrderSubBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/13 10:49
 * @change
 * @chang time
 * @class describe
 */
public class OrderRepository extends BaseRepository {
    public void projectOrder(MutableLiveData<OrderListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.projectOrder(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void locusOrder(MutableLiveData<OrderListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.locusOrder(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void recruitOrder(MutableLiveData<OrderListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.recruitOrder(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void supplierOrder(MutableLiveData<OrderListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.supplierOrder(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void subOrder(MutableLiveData<OrderSubBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.subOrder(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
