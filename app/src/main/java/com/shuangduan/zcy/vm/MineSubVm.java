package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.MessagePushBean;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.model.bean.SubscribeBean;
import com.shuangduan.zcy.model.bean.SubBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/29 9:52
 * @change
 * @chang time
 * @class describe
 */
public class MineSubVm extends BaseViewModel {

    private int userId;
    public int page = 1;
    public int pos;
    public MutableLiveData<SubscribeBean> subscribeLiveData;
    public MutableLiveData<MessagePushBean> messagePushLiveData;
    public MutableLiveData messagePushStatusLiveData;
    public MutableLiveData<List<MyPhasesBean>> phasesLiveData;
    public MutableLiveData phasesSetLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public List<Integer> phasesId;

    public MineSubVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        subscribeLiveData = new MutableLiveData<>();
        messagePushLiveData = new MutableLiveData<>();
        messagePushStatusLiveData = new MutableLiveData();
        phasesLiveData = new MutableLiveData<>();
        phasesSetLiveData = new MutableLiveData<>();
        phasesId = new ArrayList<>();
    }

    //订阅消息列表
    public void subscribe(int type){
        page = 1;
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new ProjectRepository().subscribe(subscribeLiveData, pageStateLiveData, userId,type, page);
    }

    //订阅消息列表
    public void moreSubscribe(int type){
        page++;
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new ProjectRepository().subscribe(subscribeLiveData, pageStateLiveData, userId,type, page);
    }

    //工程信息推送选择
    public void myPhases(){
        new UserRepository().myPhases(phasesLiveData, pageStateLiveData, userId);
    }

    //工程信息推送选择设置
    public void setPhases(){
        SubBean subBean = new SubBean(phasesId);
        new UserRepository().setPhases(phasesSetLiveData, pageStateLiveData, userId, subBean);
    }

    //订阅消息开关状态返回
    public void msgPush(int type){
        new ProjectRepository().msgPush(messagePushLiveData, pageStateLiveData, userId, type);
    }

    //订阅消息开关状态修改
    public void msgPushStatus(int type,int status){
        new ProjectRepository().msgPushStatus(messagePushStatusLiveData, pageStateLiveData, userId, type,status);
    }
}
