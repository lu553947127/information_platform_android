package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.base.TrackDateilBean;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.ProjectMembersStatusBean;
import com.shuangduan.zcy.model.bean.TrackBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
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
    public void getDetail(MutableLiveData<ProjectDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id) {
        request(apiService.getDetail(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 我的工程信息---概况详情
     */
    public void getMyProjectDetail(MutableLiveData<ProjectDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id) {
        request(apiService.getMyProjectDateil(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 我的工程---动态详情
     *
     * @param liveData
     * @param pageStateLiveData
     * @param user_id
     * @param id
     */
    public void getMyTrackDateil(MutableLiveData<TrackDateilBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id) {
        request(apiService.getMyTrackDateil(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }


    /**
     * 工程动态列表
     */
    public void getTrack(MutableLiveData<TrackBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id, int page, int type) {
        request(apiService.getTrack(user_id, id, page, type)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 已查看动态
     */
    public void getViewTrack(MutableLiveData<TrackBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id, int page) {
        request(apiService.getViewTrack(user_id, id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 消费列表
     */
    public void consumeList(MutableLiveData<ConsumeBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id, int page) {
        request(apiService.consumeList(user_id, id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 收藏
     */
    public void collect(MutableLiveData liveData, int user_id, int id) {
        request(apiService.collect(user_id, id)).setData(liveData).send();
    }

    /**
     * 取消收藏
     */
    public void cancelCollect(MutableLiveData liveData, int user_id, int id) {
        request(apiService.cancelCollection(user_id, id)).setData(liveData).send();
    }

    /**
     * 纠错
     */
    public void error(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id, String content) {
        request(apiService.correction(user_id, id, content)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 查询是否可以进入讨论组
     */
    public void membersStatus(MutableLiveData<ProjectMembersStatusBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int project_id) {
        request(apiService.membersStatus(user_id, project_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 加入群聊
     */
    public void joinGroup(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id) {
        request(apiService.joinGroup(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
