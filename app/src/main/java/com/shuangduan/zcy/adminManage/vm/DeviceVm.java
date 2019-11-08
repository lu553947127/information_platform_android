package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.adminManage.bean.DeviceBean;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailBean;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailEditBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.repository.DeviceRepository;
import com.shuangduan.zcy.adminManage.repository.TurnoverRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: DeviceVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/5 16:28
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/5 16:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceVm extends BaseViewModel {

    private int userId;
    private int page = 1;
    public String type;
    public int is_shelf;
    public int use_status;
    public int supplier_id;
    public int p_category_id;
    public int category_id;
    public MutableLiveData<DeviceBean> deviceLiveData;
    public MutableLiveData<TurnoverTypeBean> turnoverTypeData;
    public MutableLiveData<List<TurnoverCompanyBean>> turnoverCompanyData;
    public MutableLiveData<List<TurnoverCategoryBean>> deviceHistoryData;
    public MutableLiveData<List<TurnoverCategoryBean>> deviceFirstData;
    public MutableLiveData<List<TurnoverCategoryBean>> deviceSecondData;
    public MutableLiveData<String> deviceDeleteData;
    public MutableLiveData<DeviceDetailBean> deviceDetailLiveData;
    public MutableLiveData<DeviceDetailEditBean> deviceDetailEditLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public DeviceVm(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        deviceLiveData = new MutableLiveData<>();
        turnoverTypeData = new MutableLiveData<>();
        turnoverCompanyData = new MutableLiveData<>();
        deviceHistoryData = new MutableLiveData<>();
        deviceFirstData = new MutableLiveData<>();
        deviceSecondData = new MutableLiveData<>();
        deviceDeleteData = new MutableLiveData<>();
        deviceDetailLiveData = new MutableLiveData<>();
        deviceDetailEditLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        is_shelf = 0;
        use_status = 0;
        supplier_id = 0;
        p_category_id = 0;
        category_id = 0;
    }

    //后台管理 --- 设备管理列表
    public void equipmentList(int type,int province,int city){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DeviceRepository().equipmentList(deviceLiveData, pageStateLiveData, userId,page,type,is_shelf,use_status,province,city,p_category_id,category_id,supplier_id);
    }

    public void equipmentListMore(int type,int province,int city){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DeviceRepository().equipmentList(deviceLiveData, pageStateLiveData, userId,page,type,is_shelf,use_status,province,city,p_category_id,category_id,supplier_id);
    }

    //后台管理 --- 筛选条件列表
    public void constructionSearch(){
        new TurnoverRepository().constructionSearch(turnoverTypeData, pageStateLiveData, userId);
    }

    //后台管理 --- 子公司列表
    public void getSupplierInfo(){
        new TurnoverRepository().getSupplierInfo(turnoverCompanyData, pageStateLiveData, userId);
    }

    //后台管理 --- 设备管理名称选择条件历史列表
    public void equipmentCategoryHistory(){
        new DeviceRepository().equipmentCategoryHistory(deviceHistoryData, pageStateLiveData, userId);
    }

    //后台管理 --- 设备管理名称类别一级
    public void equipmentCategoryParent(){
        new DeviceRepository().equipmentCategoryParent(deviceFirstData, pageStateLiveData, userId);
    }

    //后台管理 --- 设备管理名称类别二级
    public void equipmentCategoryList(String title,int id){
        new DeviceRepository().equipmentCategoryList(deviceSecondData, pageStateLiveData, userId,title,id);
    }

    //后台管理 --- 设备管理删除
    public void equipmentDelete(int id){
        new DeviceRepository().equipmentDelete(deviceDeleteData,userId,id);
    }

    //后台管理 --- 设备管理详情
    public void equipmentDetail(int id){
        new DeviceRepository().equipmentDetail(deviceDetailLiveData, pageStateLiveData, userId,id);
    }

    //后台管理 --- 设备管理编辑详情
    public void equipmentEditShow(int id) {
        new DeviceRepository().equipmentEditShow(deviceDetailEditLiveData, pageStateLiveData, userId, id);
    }
}
