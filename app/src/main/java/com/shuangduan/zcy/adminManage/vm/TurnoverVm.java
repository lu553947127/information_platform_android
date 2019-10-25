package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
import com.shuangduan.zcy.adminManage.repository.TurnoverRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;

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
    public MutableLiveData<TurnoverBean> turnoverLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public TurnoverVm(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        turnoverLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    //后台管理 --- 周转材料列表
    public void constructionList(int type,int is_shelf,int use_status,int province,int city,int p_category_id,int category_id,int supplier_id){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new TurnoverRepository().constructionList(turnoverLiveData, pageStateLiveData, userId,page,type,is_shelf,use_status,province,city,p_category_id,category_id,supplier_id);
    }

    public void constructionListMore(int type,int is_shelf,int use_status,int province,int city,int p_category_id,int category_id,int supplier_id){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new TurnoverRepository().constructionList(turnoverLiveData, pageStateLiveData, userId,page,type,is_shelf,use_status,province,city,p_category_id,category_id,supplier_id);
    }
}
