package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.AddTrackBean;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ContactListBean;
import com.shuangduan.zcy.model.bean.ContactTypeBean;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.model.bean.ProjectFilterBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProjectMineBean;
import com.shuangduan.zcy.model.bean.ProjectSubConfirmBean;
import com.shuangduan.zcy.model.bean.ProjectSubFirstBean;
import com.shuangduan.zcy.model.bean.ProjectSubViewBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.MessagePushBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.SubscribeOrderBean;
import com.shuangduan.zcy.model.bean.TypeBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/18 17:13
 * @change
 * @chang time
 * @class describe
 */
public class ProjectRepository extends BaseRepository {

    public void getProvince(MutableLiveData<List<ProvinceBean>> liveData, MutableLiveData<String> pageState, int user_id){
        request(apiService.getProvince(user_id)).setDataList(liveData).setPageState(pageState).send();
    }

    public void getCity(MutableLiveData<List<CityBean>> liveData, int user_id, int id){
        request(apiService.getCity(user_id, id)).setDataList(liveData).send();
    }

    public void mapList(MutableLiveData<List<MapBean>> liveData, MutableLiveData<String> pageState, int user_id, double lng, double lat){
        request(apiService.mapList(user_id, lng, lat)).setDataList(liveData).setPageState(pageState).send();
    }

    public void projectList(MutableLiveData<ProjectInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, ProjectFilterBean filterBean, String stime, String etime, String warrant_status, int page){
        request(apiService.projectList(user_id, filterBean, stime, etime, warrant_status, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void projectTypes(MutableLiveData<List<TypeBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id){
        request(apiService.projectTypes(user_id, id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    public void projectStage(MutableLiveData<List<StageBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id){
        request(apiService.projectStage(user_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    public void myProject(MutableLiveData<ProjectMineBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.myProject(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void myProjectTrack(MutableLiveData<LocusMineBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.myProjectTrack(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getContactType(MutableLiveData<List<ContactTypeBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id){
        request(apiService.getContactType(user_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    public void addProject(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String title, String company, int province, int city, int phases,
                           String start_time, String end_time, String acreage, String valuation, String intro, String materials, String longitude, String latitude, ContactListBean contact){
        request(apiService.addProject(user_id, title, company, province, city, phases, start_time, end_time, acreage, valuation, intro, materials, longitude, latitude, contact)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void addTrack(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id, String remarks, String name, String tel, String update_time, AddTrackBean image_id){
        request(apiService.addTrack(user_id, id, remarks, name, tel, update_time, image_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //订阅消息列表
    public void subscribe(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id,int type, int page){
        request(apiService.subscribe(user_id,type, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //订单提醒列表
    public void subscribeOrder(MutableLiveData<SubscribeOrderBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page){
        request(apiService.subscribeOrder(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //订阅消息开关状态返回
    public void msgPush(MutableLiveData<MessagePushBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int type){
        request(apiService.msgPush(user_id, type)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //订阅消息开关状态修改
    public void msgPushStatus(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int type,int status){
        request(apiService.msgPushStatus(user_id, type,status)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void startWarrant(MutableLiveData<ProjectSubFirstBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id){
        request(apiService.startWarrant(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void confirmWarrant(MutableLiveData<ProjectSubConfirmBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id, int month, String order_sn){
        request(apiService.confirmWarrant(user_id, id, month, order_sn)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void viewWarrant(MutableLiveData<ProjectSubViewBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id){
        request(apiService.viewWarrant(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

}
