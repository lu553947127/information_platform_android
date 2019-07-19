package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseObjResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
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

    public MutableLiveData<List<ProvinceBean>> getProvince(int user_id){
        BaseSubscriber subscriber = request(apiService.getProvince(user_id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getDataList();
    }

    public MutableLiveData<List<CityBean>> getCity(int user_id, int id){
        BaseSubscriber subscriber = request(apiService.getCity(user_id, id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getDataList();
    }

    public MutableLiveData<List<MapBean>> mapList(int user_id, double lng, double lat){
        BaseSubscriber subscriber = request(apiService.mapList(user_id, lng, lat)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getDataList();
    }

    public MutableLiveData<ProjectInfoBean> projectList(int user_id, String province, String[] city, String phases, String[] type, String stime, String etime, String warrant_status, int page){
        BaseSubscriber subscriber = request(apiService.projectList(user_id, province, city, phases, type, stime, etime, warrant_status, page)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    public MutableLiveData<List<TypeBean>> projectTypes(int user_id, int id){
        BaseSubscriber subscriber = request(apiService.projectTypes(user_id, id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getDataList();
    }

    public MutableLiveData<List<StageBean>> projectStage(int user_id){
        BaseSubscriber subscriber = request(apiService.projectStage(user_id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getDataList();
    }

}
