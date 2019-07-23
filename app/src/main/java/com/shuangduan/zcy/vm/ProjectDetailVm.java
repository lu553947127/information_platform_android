package com.shuangduan.zcy.vm;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectDetailRepository;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.model.bean.ViewTrackBean;

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
    public MutableLiveData<List<ViewTrackBean>> viewTrackLiveData;
    public MutableLiveData<ConsumeBean> consumeLiveData;
    public MutableLiveData<String> titleLiveData;
    public MutableLiveData<String> locationLiveData;
    public int pageTrack = 1;
    public int pageConsume = 1;
    public int type = 1;

    public void init(int id){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        this.id = id;
        titleLiveData = new MutableLiveData<>();
        locationLiveData = new MutableLiveData<>();
    }

    public void getDetail(){
        ProjectDetailRepository repository = new ProjectDetailRepository();
        pageStateLiveData = repository.getPageStateLiveData();
        detailLiveData = repository.getDetail(userId, id);
    }

    public void getTrack(){
        ProjectDetailRepository repository = new ProjectDetailRepository();
        pageStateLiveData = repository.getPageStateLiveData();
        trackLiveData = repository.getTrack(userId, id, pageTrack, type);
    }

    public void getViewTrack(){
        ProjectDetailRepository repository = new ProjectDetailRepository();
        pageStateLiveData = repository.getPageStateLiveData();
        viewTrackLiveData = repository.getViewTrack(userId, id);
    }

    public void getConsume(){
        ProjectDetailRepository repository = new ProjectDetailRepository();
        pageStateLiveData = repository.getPageStateLiveData();
        consumeLiveData = repository.consumeList(userId, id, pageConsume);
    }

}
