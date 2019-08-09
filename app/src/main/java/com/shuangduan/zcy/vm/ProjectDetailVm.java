package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.ProjectDetailRepository;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.TrackBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
    private int id;//工程信息id
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
    public MutableLiveData collectLiveData;
    public MutableLiveData cancelCollectLiveData;
    public MutableLiveData errorLiveData;
    public int pageTrack = 1;
    public int pageViewTrack = 1;
    public int pageConsume = 1;
    private final int TYPE_ALL = 1;//全部
    private final int TYPE_MINE = 2;//我发布的
    private int type = TYPE_ALL;

    public void init(int id){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        this.id = id;
        if (titleLiveData == null)
            titleLiveData = new MutableLiveData<>();
        if (locationLiveData == null)
            locationLiveData = new MutableLiveData<>();
        if (latitudeLiveData == null)
            latitudeLiveData = new MutableLiveData<>();
        if (introLiveData == null)
            introLiveData = new MutableLiveData<>();
        if (materialLiveData == null)
            materialLiveData = new MutableLiveData<>();
        if (locusTypeLiveData == null)
            locusTypeLiveData = new MutableLiveData<>();
        if (collectionLiveData == null)
            collectionLiveData = new MutableLiveData<>();
        if (subscribeLiveData == null)
            subscribeLiveData = new MutableLiveData<>();
        if (pageStateLiveData == null)
            pageStateLiveData = new MutableLiveData<>();
        if (detailLiveData == null)
            detailLiveData = new MutableLiveData<>();
        if (trackLiveData == null)
            trackLiveData = new MutableLiveData<>();
        if (viewTrackLiveData == null)
            viewTrackLiveData = new MutableLiveData<>();
        if (consumeLiveData == null)
            consumeLiveData = new MutableLiveData<>();
        if (cancelCollectLiveData == null)
            cancelCollectLiveData = new MutableLiveData();
        if (collectLiveData == null)
            collectLiveData = new MutableLiveData();
        if (errorLiveData == null)
            errorLiveData = new MutableLiveData();
    }

    private ProjectDetailRepository repositoryDetail;
    public void getDetail(){
        if (repositoryDetail == null)
            repositoryDetail = new ProjectDetailRepository();
        repositoryDetail.getDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    public void getTrack(){
        pageTrack = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getTrack(trackLiveData, pageStateLiveData, userId, id, pageTrack, type);
        locusTypeLiveData.postValue(type);
    }

    public void getMoreTract(){
        pageTrack ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getTrack(trackLiveData, pageStateLiveData, userId, id, pageTrack, type);
    }

    public void getViewTrack(){
        pageViewTrack = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getViewTrack(viewTrackLiveData, pageStateLiveData, userId, id, pageViewTrack);
    }

    public void getMoreViewTrack(){
        pageViewTrack ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().getViewTrack(viewTrackLiveData, pageStateLiveData, userId, id, pageViewTrack);
    }

    public void getConsume(){
        pageConsume = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().consumeList(consumeLiveData, pageStateLiveData, userId, id, pageConsume);
    }

    public void getMoreConsume(){
        pageConsume ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new ProjectDetailRepository().consumeList(consumeLiveData, pageStateLiveData, userId, id, pageConsume);
    }

    /**
     * 切换轨迹列表，我的和全部
     */
    public void switchLocusList(){
        type = type == TYPE_ALL?TYPE_MINE:TYPE_ALL;
        getTrack();
    }

    public void collect(){
        ProjectDetailRepository repositoryCollect = new ProjectDetailRepository();
        if (collectionLiveData != null && collectionLiveData.getValue() == 1){
            //取消收藏
            repositoryCollect.cancelCollect(cancelCollectLiveData, userId, id);
        }else {
            //收藏
            repositoryCollect.collect(collectLiveData, userId, id);
        }
    }

    /**
     * 纠错
     */
    public void error(String error){
        new ProjectDetailRepository().error(errorLiveData, pageStateLiveData, userId, id, error);
    }

}
