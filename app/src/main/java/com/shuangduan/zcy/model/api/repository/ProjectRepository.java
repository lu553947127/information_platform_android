package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;

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
        return request(apiService.getProvince(user_id)).send().getDataList();
    }

    public MutableLiveData<List<CityBean>> getCity(int user_id, int id){
        return request(apiService.getCity(user_id, id)).send().getDataList();
    }

    public MutableLiveData<List<MapBean>> mapList(int user_id, double lng, double lat){
        return request(apiService.mapList(user_id, lng, lat)).send().getDataList();
    }

}
