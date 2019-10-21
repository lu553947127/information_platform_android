package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.IMFriendApplyDetailBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyOperationBean;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.model.bean.IMTokenBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe  即时通讯
 * @time 2019/8/29 9:36
 * @change
 * @chang time
 * @class describe
 */
public class IMRepository extends BaseRepository {
    public void getIMToken(MutableLiveData<IMTokenBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.getIMToken(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void imFriendSearch(MutableLiveData<IMFriendSearchBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String name, int page){
        request(apiService.imFriendSearch(userId, name, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void imFriendApply(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int receive_user_id, String msg){
        request(apiService.imFriendApply(userId, receive_user_id, msg)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void imFriendApplyDetail(MutableLiveData<IMFriendApplyDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.imFriendApplyDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void imFriendApplyList(MutableLiveData<IMFriendApplyListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.imFriendApplyList(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void imFriendApplyOperation(MutableLiveData<IMFriendApplyOperationBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, int status, String msg){
        request(apiService.imFriendApplyOperation(userId, id, status, msg)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
