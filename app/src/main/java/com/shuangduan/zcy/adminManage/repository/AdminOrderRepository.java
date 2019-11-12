package com.shuangduan.zcy.adminManage.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.BaseRepository;

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
    public void orderListData(MutableLiveData<AdminOrderBean> liveData, MutableLiveData<String> pageState, int userId, int type, int pCategoryId,
                              int categoryId, int phases, int inside, String orderNumber, int page) {
        request(apiService.orderListData(userId, type, pCategoryId, categoryId, phases, inside, orderNumber, page)).setData(liveData).setPageState(pageState).send();
    }

}
