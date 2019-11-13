package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.base.TrackDateilBean;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.ProjectDetailRepository;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.ProjectMembersStatusBean;
import com.shuangduan.zcy.model.bean.TrackBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/23 11:39
 * @change
 * @chang time
 * @class describe
 */
public class ProjectDetailVm extends BaseViewModel {

    private int userId;
    private int id;//工程信息id OR 动态id
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<ProjectDetailBean> detailLiveData;
    public MutableLiveData<TrackBean> trackLiveData;
    public MutableLiveData<TrackBean> viewTrackLiveData;
    public MutableLiveData<ConsumeBean> consumeLiveData;
    public MutableLiveData<String> titleLiveData;
    public MutableLiveData<String> locationLiveData;
    public MutableLiveData<LatLng> latitudeLiveData;
    public MutableLiveData<String> introLiveData;
    public MutableLiveData<String> materialLiveData;
    public MutableLiveData<Integer> locusTypeLiveData;
    public MutableLiveData<Integer> collectionLiveData;
    public MutableLiveData<Integer> subscribeLiveData;

    //我的工程 动态详细
    public MutableLiveData<TrackDateilBean> trackDateilLiveDate;

    public MutableLiveData collectLiveData;
    public MutableLiveData cancelCollectLiveData;
    public MutableLiveData errorLiveData;
    public MutableLiveData<ProjectMembersStatusBean> projectMembersStatusData;
    public MutableLiveData joinGroupData;
    public int pageTrack = 1;
    public int pageViewTrack = 1;
    public int pageConsume = 1;
    private final int TYPE_ALL = 1;//全部
    private final int TYPE_MINE = 2;//我发布的
    private int type = TYPE_ALL;

    public void init(int id) {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        this.id = id;
        titleLiveData = new MutableLiveData<>();
        locationLiveData = new MutableLiveData<>();
        latitudeLiveData = new MutableLiveData<>();
        introLiveData = new MutableLiveData<>();
        materialLiveData = new MutableLiveData<>();
        locusTypeLiveData = new MutableLiveData<>();
        collectionLiveData = new MutableLiveData<>();
        subscribeLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
        trackLiveData = new MutableLiveData<>();
        viewTrackLiveData = new MutableLiveData<>();
        consumeLiveData = new MutableLiveData<>();
        cancelCollectLiveData = new MutableLiveData();
        collectLiveData = new MutableLiveData();
        errorLiveData = new MutableLiveData();
        trackDateilLiveDate = new MutableLiveData<>();
        projectMembersStatusData = new MutableLiveData<>();
        joinGroupData = new MutableLiveData();
    }

    private ProjectDetailRepository repositoryDetail;

    public void getDetail() {
        if (repositoryDetail == null)
            repositoryDetail = new ProjectDetailRepository();
        repositoryDetail.getDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    public void getMyProjectDateil() {
        if (repositoryDetail == null)
            repositoryDetail = new ProjectDetailRepository();
        repositoryDetail.getMyProjectDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    public void getMyTrackDateil() {
        if (repositoryDetail == null)
            repositoryDetail = new ProjectDetailRepository();
        repositoryDetail.getMyTrackDateil(trackDateilLiveDate, pageStateLiveData, userId, id);
    }

    public void getTrack() {
        pageTrack = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getTrack(trackLiveData, pageStateLiveData, userId, id, pageTrack, type);
        locusTypeLiveData.postValue(type);
    }

    public void getMoreTract() {
        pageTrack++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getTrack(trackLiveData, pageStateLiveData, userId, id, pageTrack, type);
    }

    public void getViewTrack() {
        pageViewTrack = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getViewTrack(viewTrackLiveData, pageStateLiveData, userId, id, pageViewTrack);
    }

    public void getMoreViewTrack() {
        pageViewTrack++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getViewTrack(viewTrackLiveData, pageStateLiveData, userId, id, pageViewTrack);
    }

    public void getConsume() {
        pageConsume = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().consumeList(consumeLiveData, pageStateLiveData, userId, id, pageConsume);
    }

    public void getMoreConsume() {
        pageConsume++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().consumeList(consumeLiveData, pageStateLiveData, userId, id, pageConsume);
    }

    /**
     * 切换动态列表，我的和全部
     */
    public void switchLocusList() {
        type = type == TYPE_ALL ? TYPE_MINE : TYPE_ALL;
        getTrack();
    }

    public void collect() {
        ProjectDetailRepository repositoryCollect = new ProjectDetailRepository();
        if (collectionLiveData != null && collectionLiveData.getValue() == 1) {
            //取消收藏
            repositoryCollect.cancelCollect(cancelCollectLiveData, userId, id);
        } else {
            //收藏
            repositoryCollect.collect(collectLiveData, userId, id);
        }
    }

    /**
     * 纠错
     */
    public void error(String error) {
        new ProjectDetailRepository().error(errorLiveData, pageStateLiveData, userId, id, error);
    }

    //查询是否可以进入讨论组
    public void membersStatus(int project_id) {
        new ProjectDetailRepository().membersStatus(projectMembersStatusData, pageStateLiveData, userId, project_id);
    }

    //加入群聊
    public void joinGroup(int id) {
        new ProjectDetailRepository().joinGroup(joinGroupData, pageStateLiveData, userId, id);
    }
}
