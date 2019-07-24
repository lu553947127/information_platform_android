package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
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
        LogUtils.i(subscriber);
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 已查看轨迹
     */
    public MutableLiveData<List<TrackBean.ListBean>> getViewTrack(int user_id, int id){
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

    /**
     * 收藏
     */
    public MutableLiveData collect(int user_id, int id){
        return request(apiService.collect(user_id, id)).send().getData();
    }

    /**
     * 取消收藏
     */
    public MutableLiveData cancelCollect(int user_id, int id){
        return request(apiService.cancelCollection(user_id, id)).send().getData();
    }

    /**
     * 纠错
     */
    public MutableLiveData error(int user_id, int id, String content){
        BaseSubscriber subscriber = request(apiService.correction(user_id, id, content)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

}
