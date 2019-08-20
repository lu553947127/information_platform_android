package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.DemandBuyerBean;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.model.bean.DemandReleaseBean;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;
import com.shuangduan.zcy.model.bean.RelationshipDetailBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/19 18:27
 * @change
 * @chang time
 * @class describe
 */
public class DemandRepository extends BaseRepository {
    public void demandRelationshipRelease(MutableLiveData<DemandReleaseBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String title, String intro, String start_time, String end_time, String price){
        request(apiService.demandRelationshipRelease(user_id, title, intro, start_time, end_time, price)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void demandSubstanceRelease(MutableLiveData<DemandReleaseBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String material_name, String count, String project_name, String address,
                              String acceptance_price, String tel, String way, String start_time, String end_time){
        request(apiService.demandSubstanceRelease(user_id, material_name, count, project_name, address, acceptance_price, tel, way, start_time, end_time)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void demandBuyerRelease(MutableLiveData<DemandReleaseBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String material_name, String count, String project_name, String address,
                              String acceptance_price, String tel, String way, String start_time, String end_time){
        request(apiService.demandBuyerRelease(user_id, material_name, count, project_name, address, acceptance_price, tel, way, start_time, end_time)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void demandRelationship(MutableLiveData<DemandRelationshipBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.demandRelationship(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void demandRelationshipAccept(MutableLiveData<DemandRelationshipBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.demandRelationshipAccept(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void demandRelationshipRelease(MutableLiveData<DemandRelationshipBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.demandRelationshipRelease(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void demandSubstance(MutableLiveData<DemandSubstanceBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page, int isMy){
        request(apiService.demandSubstance(user_id, page, isMy)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void demandBuyer(MutableLiveData<DemandBuyerBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page, int isMy){
        request(apiService.demandBuyer(user_id, page, isMy)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void orderTaking(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id, String name, String tel, String intro){
        request(apiService.orderTaking(user_id, id, name, tel, intro)).setData(liveData).setPageState(pageStateLiveData).send();
    }
    public void relationshipDetail(MutableLiveData<RelationshipDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id){
        request(apiService.relationshipDetail(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
