package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.model.bean.ViewTrackBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/23 11:42
 * @change
 * @chang time
 * @class describe
 */
public class ProjectDetailRepository extends BaseRepository {

    /**
     * 工程信息概况
     */
    public MutableLiveData<ProjectDetailBean> getDetail(int user_id, int id){
        BaseSubscriber subscriber = request(apiService.getDetail(user_id, id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 工程轨迹列表
     */
    public MutableLiveData<TrackBean> getTrack(int user_id, int id, int page, int type){
        BaseSubscriber subscriber = request(apiService.getTrack(user_id, id, page, type)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 已查看轨迹
     */
    public MutableLiveData<List<ViewTrackBean>> getViewTrack(int user_id, int id){
        BaseSubscriber subscriber = request(apiService.getViewTrack(user_id, id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getDataList();
    }

    /**
     * 消费列表
     */
    public MutableLiveData<ConsumeBean> consumeList(int user_id, int id, int page){
        BaseSubscriber subscriber = request(apiService.consumeList(user_id, id, page)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

}
