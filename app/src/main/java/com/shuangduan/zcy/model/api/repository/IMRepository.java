package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.IMFriendApplyCountBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyOperationBean;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.model.bean.IMGroupInfoBean;
import com.shuangduan.zcy.model.bean.IMGroupListBean;
import com.shuangduan.zcy.model.bean.IMTokenBean;
import com.shuangduan.zcy.model.bean.IMWechatGroupInfoBean;
import com.shuangduan.zcy.model.bean.IMWechatUserInfoBean;

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
    //融云获取token
    public void getIMToken(MutableLiveData<IMTokenBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.getIMToken(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //好友群组搜索列表
    public void imFriendSearch(MutableLiveData<IMFriendSearchBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String name){
        request(apiService.imFriendSearch(userId, name)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //通讯录 好友申请操作
    public void imFriendApply(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int receive_user_id, String msg){
        request(apiService.imFriendApply(userId, receive_user_id, msg)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //通讯录 好友申请数量
    public void applyCount(MutableLiveData<IMFriendApplyCountBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.applyCount(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //工程圈 新的好友列表
    public void imFriendApplyList(MutableLiveData<IMFriendApplyListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.imFriendApplyList(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //工程圈 好友添加验证
    public void imFriendApplyOperation(MutableLiveData<IMFriendApplyOperationBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, int status, String msg){
        request(apiService.imFriendApplyOperation(userId, id, status, msg)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //搜索 更多好友列表
    public void searchFriend(MutableLiveData<IMFriendListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String name, int page){
        request(apiService.searchFriend(userId, name, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //通讯录 好友列表
    public void friendList(MutableLiveData<IMFriendListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page,int pageSize){
        request(apiService.friendList(userId, page,pageSize)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //搜索 更多群聊列表
    public void searchGroup(MutableLiveData<IMGroupListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String name, int page){
        request(apiService.searchGroup(userId, name, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //通讯录 群组列表
    public void myGroup(MutableLiveData<IMGroupListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page,int pageSize){
        request(apiService.myGroup(userId, page,pageSize)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //退出群聊
    public void quitGroup(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, String group_id){
        request(apiService.quitGroup(userId, group_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //群聊详情
    public void groupList(MutableLiveData<IMGroupInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String group_id,int page,int pageSize){
        request(apiService.groupList(userId, group_id,page,pageSize)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //会话列表人员头像名称显示
    public void userInfo(MutableLiveData<IMWechatUserInfoBean.DataBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String id){
        request(apiService.userInfo(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //会话列表群组头像名称显示
    public void groupInfo(MutableLiveData<IMWechatGroupInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String group_id){
        request(apiService.groupInfo(userId, group_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
