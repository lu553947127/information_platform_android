package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.adminManage.bean.OrderDetailsBean;
import com.shuangduan.zcy.adminManage.bean.OrderSearchBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.repository.AdminOrderRepository;
import com.shuangduan.zcy.adminManage.repository.TurnoverRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.vm$
 * @class OrderTurnoverVm$
 * @class describe
 * @time 2019/11/12 17:09
 * @change
 * @class describe
 */
public class OrderTurnoverVm extends BaseViewModel {

    public int userId;
    public int page = 1;
    //选择弹窗区分类别
    public String type;
    //子公司id
    public int supplier_id;
    //子公司名称
    public String supplier_name;
    //项目id
    public int unit_id;

    //周转材料父类型ID
    public int pCategoryId;
    //周转材料子类型ID
    public int categoryId;
    //订单进度
    public int phases;
    //订单类型
    public int inside;

    //周转材料订单列表数据
    public MutableLiveData<AdminOrderBean> orderListLiveData;
    public MutableLiveData<List<TurnoverCompanyBean>> turnoverCompanyData;
    public MutableLiveData<List<TurnoverNameBean>> turnoverProject;

    //驳回订单
    public MutableLiveData rejectLiveData;

    public MutableLiveData<OrderSearchBean> orderSearchLiveData;

    //周转材料详情
    public MutableLiveData<OrderDetailsBean> orderDetailsLiveData;

    public MutableLiveData<String> pageStateLiveData;

    //记录当前Adapter点击的position值
    public int position;


    public OrderTurnoverVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        orderListLiveData = new MutableLiveData<>();
        turnoverCompanyData = new MutableLiveData<>();
        turnoverProject = new MutableLiveData<>();
        orderSearchLiveData = new MutableLiveData<>();
        rejectLiveData = new MutableLiveData();

        orderDetailsLiveData = new MutableLiveData<>();
    }

    //后台管理 --- 周转材料订单列表
    public void orderListData(String orderNumber) {
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new AdminOrderRepository().orderListData(orderListLiveData, pageStateLiveData, userId, pCategoryId, categoryId, phases, inside, orderNumber, page);
    }

    //后台管理 --- 周转材料订单列表
    public void moreOrderListData(String orderNumber) {
        page++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new AdminOrderRepository().orderListData(orderListLiveData, pageStateLiveData, userId, pCategoryId, categoryId, phases, inside, orderNumber, page);
    }

    //后台管理 --- 子公司列表
    public void getSupplierInfo() {
        new TurnoverRepository().getSupplierInfo(turnoverCompanyData, pageStateLiveData, userId);
    }

    //后台管理 --- 周转材料列表 筛选项目
    public void getUnitInfo() {
        new TurnoverRepository().getUnitInfo(turnoverProject, pageStateLiveData, userId, supplier_id);
    }

    //订单管理 --- 筛选条件列表
    public void orderSearch() {
        new TurnoverRepository().orderSearch(orderSearchLiveData, pageStateLiveData, userId);
    }

    //订单管理 --- 驳回功能
    public void constructionOrderEditStatus(int orderId) {
        new AdminOrderRepository().constructionOrderEditStatus(rejectLiveData, pageStateLiveData, userId, orderId);
    }

    //订单管理 --- 周转材料详情
    public void constructionOrderDetail(int orderId){
        new AdminOrderRepository().constructionOrderDetail(orderDetailsLiveData,pageStateLiveData,userId,orderId);
    }

}
