package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverHistoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.repository.TurnoverRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: TurnoverVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 13:51
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 13:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverVm extends BaseViewModel{

    private int userId;
    private int page = 1;
    public String type;
    public int is_shelf;
    public int use_status;
    public int supplier_id;
    public int p_category_id;
    public int category_id;
    public MutableLiveData<TurnoverBean> turnoverLiveData;
    public MutableLiveData<TurnoverTypeBean> turnoverTypeData;
    public MutableLiveData<List<TurnoverHistoryBean>> turnoverHistoryData;
    public MutableLiveData<List<TurnoverCompanyBean>> turnoverCompanyData;
    public MutableLiveData<List<TurnoverCategoryBean>> turnoverFirstData;
    public MutableLiveData<List<TurnoverCategoryBean>> turnoverSecondData;
    public MutableLiveData<String> pageStateLiveData;

    public TurnoverVm(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        turnoverLiveData = new MutableLiveData<>();
        turnoverTypeData = new MutableLiveData<>();
        turnoverHistoryData = new MutableLiveData<>();
        turnoverCompanyData = new MutableLiveData<>();
        turnoverFirstData = new MutableLiveData<>();
        turnoverSecondData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        is_shelf = 0;
        use_status = 0;
        supplier_id = 0;
        p_category_id = 0;
        category_id = 0;
    }

    //后台管理 --- 周转材料列表
    public void constructionList(int type,int province,int city){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new TurnoverRepository().constructionList(turnoverLiveData, pageStateLiveData, userId,page,type,is_shelf,use_status,province,city,p_category_id,category_id,supplier_id);
    }

    public void constructionListMore(int type,int province,int city){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new TurnoverRepository().constructionList(turnoverLiveData, pageStateLiveData, userId,page,type,is_shelf,use_status,province,city,p_category_id,category_id,supplier_id);
    }

    //后台管理 --- 筛选条件列表
    public void constructionSearch(){
        new TurnoverRepository().constructionSearch(turnoverTypeData, pageStateLiveData, userId);
    }

    //后台管理 --- 选择条件历史列表
    public void constructionCategoryHistory(){
        new TurnoverRepository().constructionCategoryHistory(turnoverHistoryData, pageStateLiveData, userId);
    }

    //后台管理 --- 子公司列表
    public void getSupplierInfo(){
        new TurnoverRepository().getSupplierInfo(turnoverCompanyData, pageStateLiveData, userId);
    }

    //后台管理 --- 周转材料名称类别一级
    public void constructionCategoryParent(){
        new TurnoverRepository().constructionCategoryParent(turnoverFirstData, pageStateLiveData, userId);
    }

    //后台管理 --- 周转材料名称类别二级
    public void constructionCategoryList(String title,int id){
        new TurnoverRepository().constructionCategoryList(turnoverSecondData, pageStateLiveData, userId,title,id);
    }
}
