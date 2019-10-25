package com.shuangduan.zcy.adminManage.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
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
}
