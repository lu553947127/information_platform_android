package com.shuangduan.zcy.adminManage.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.adminManage.bean.OrderDetailsBean;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.BaseRepository;
import com.shuangduan.zcy.model.bean.BaseResponse;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.repository$
 * @class OrderRepository$
 * @class describe
 * @time 2019/11/12 17:08
 * @change
 * @class describe
 */
public class AdminOrderRepository extends BaseRepository {


    //后台管理 --- 周转材料订单列表
    public void orderListData(MutableLiveData<AdminOrderBean> liveData, MutableLiveData<String> pageState, int userId, int pCategoryId,
                              int categoryId, int phases, int inside, String orderNumber, int page) {
        request(apiService.orderListData(userId, pCategoryId, categoryId, phases, inside, orderNumber, page)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料订单驳回
    public void constructionOrderEditStatus(MutableLiveData<AdminOrderBean> liveData, MutableLiveData<String> pageState, int userId, int orderId) {
        request(apiService.constructionOrderEditStatus(userId, orderId)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料详情
    public void constructionOrderDetail(MutableLiveData<OrderDetailsBean> liveData, MutableLiveData<String> pageState, int userId, int orderId) {
        request(apiService.constructionOrderDetail(userId, orderId)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料修改订单进度
    public void constructionOrderPhases(MutableLiveData<BaseResponse> liveData, MutableLiveData<String> pageState, int userId, int orderId, int phases) {
        request(apiService.constructionOrderPhases(userId, orderId, phases)).setData(liveData).setPageState(pageState).send();
    }

}
