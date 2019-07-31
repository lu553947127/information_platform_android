package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.BaseObjResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ContactTypeBean;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProjectMineBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.TypeBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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

    public void projectList(MutableLiveData<ProjectInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String province, int[] city, String phases, int[] type, String stime, String etime, String warrant_status, int page){
        request(apiService.projectList(user_id, province, city, phases, type, stime, etime, warrant_status, page)).setData(liveData).setPageState(pageStateLiveData).send();
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

    public void addProject(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String title, int province, int city, int phases, int type,
                           String start_time, String end_time, String acreage, String valuation, String intro, String materials, String longitude, String latitude, String[] contact){
        request(apiService.addProject(user_id, title, province, city, phases, type, start_time, end_time, acreage, valuation, intro, materials, longitude, latitude, contact)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

}
