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
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: OrderDeviceVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/15 9:41
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/15 9:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderDeviceVm extends BaseViewModel {

    public int userId;
    //选择弹窗区分类别
    public String type;
    //当前选中的Adapter 条目
    public int position;
    public int page = 1;
    //子公司id
    public int supplier_id;
    //子公司名称
    public String supplier_name;
    //项目id
    public int unit_id;
    //周转材料子类型ID
    public int categoryId;
    //订单进度
    public int phases;
    //订单进度名称
    public String phasesName;
    //订单类型
    public int inside;
    public MutableLiveData<String> pageStateLiveData;
    //周转材料订单列表数据
    public MutableLiveData<AdminOrderBean> orderListLiveData;
    //驳回订单
    public MutableLiveData rejectLiveData;
    //修改订单进度
    public MutableLiveData<AdminOrderBean.OrderList> orderPhasesLiveData;
    //设备详情
    public MutableLiveData<OrderDetailsBean> orderDetailsLiveData;
    public MutableLiveData<List<TurnoverCompanyBean>> turnoverCompanyData;
    public MutableLiveData<List<TurnoverNameBean>> turnoverProject;
    public MutableLiveData<OrderSearchBean> orderSearchLiveData;

    public OrderDeviceVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        orderListLiveData = new MutableLiveData<>();
        rejectLiveData = new MutableLiveData();
        orderDetailsLiveData = new MutableLiveData<>();
        orderPhasesLiveData = new MutableLiveData<>();
        turnoverCompanyData = new MutableLiveData<>();
        turnoverProject = new MutableLiveData<>();
        orderSearchLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    //后台管理 --- 设备订单列表
    public void orderDeviceListData(String orderNumber) {
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new AdminOrderRepository().orderDeviceListData(orderListLiveData, pageStateLiveData, userId, unit_id, supplier_id, categoryId, phases, inside, orderNumber, page);
    }

    public void moreDeviceOrderListData(String orderNumber) {
        page++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new AdminOrderRepository().orderDeviceListData(orderListLiveData, pageStateLiveData, userId, unit_id, supplier_id, categoryId, phases, inside, orderNumber, page);
    }

    //后台管理 --- 设备订单驳回
    public void equipmentOrderStatus(int orderId){
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new AdminOrderRepository().equipmentOrderStatus(rejectLiveData,pageStateLiveData,userId,orderId,3);
    }

    //后台管理 --- 设备订单详情
    public void equipmentOrderDetail(int orderId){
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new AdminOrderRepository().equipmentOrderDetail(orderDetailsLiveData,pageStateLiveData,userId,orderId);
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
}
