package com.shuangduan.zcy.adminManage.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverHistoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.model.api.repository.BaseRepository;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.repository
 * @ClassName: TurnoverRepository
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 15:16
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 15:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverRepository extends BaseRepository {

    //后台管理 --- 周转材料列表
    public void constructionList(MutableLiveData<TurnoverBean> liveData, MutableLiveData<String> pageState, int user_id,int page,int type,int is_shelf,int use_status,int province,int city,int p_category_id,int category_id,int supplier_id){
        request(apiService.constructionList(user_id,page,type,is_shelf,use_status,province,city,p_category_id,category_id,supplier_id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 筛选条件列表
    public void constructionSearch(MutableLiveData<TurnoverTypeBean> liveData, MutableLiveData<String> pageState, int user_id){
        request(apiService.constructionSearch(user_id)).setData(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 选择条件历史列表
    public void constructionCategoryHistory(MutableLiveData<List<TurnoverHistoryBean>> liveData, MutableLiveData<String> pageState, int user_id){
        request(apiService.constructionCategoryHistory(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 子公司列表
    public void getSupplierInfo(MutableLiveData<List<TurnoverCompanyBean>> liveData, MutableLiveData<String> pageState, int user_id){
        request(apiService.getSupplierInfo(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料名称类别一级
    public void constructionCategoryParent(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id){
        request(apiService.constructionCategoryParent(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    //后台管理 --- 周转材料名称类别二级
    public void constructionCategoryList(MutableLiveData<List<TurnoverCategoryBean>> liveData, MutableLiveData<String> pageState, int user_id,String title,int id){
        request(apiService.constructionCategoryList(user_id,title,id)).setDataList(liveData).setPageState(pageState).send();
    }
}
