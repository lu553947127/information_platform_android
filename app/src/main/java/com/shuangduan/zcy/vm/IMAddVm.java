package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.IMRepository;
import com.shuangduan.zcy.model.bean.IMFriendApplyCountBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyDetailBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyOperationBean;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.model.bean.IMGroupListBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/29 10:09
 * @change
 * @chang time
 * @class describe
 */
public class IMAddVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<IMFriendSearchBean> searchLiveData;
    public MutableLiveData<IMFriendApplyListBean> applyListLiveData;
    public MutableLiveData<IMFriendApplyDetailBean> applyDetailLiveData;
    public MutableLiveData<IMFriendApplyOperationBean> applyOperateLiveData;
    public MutableLiveData<IMFriendListBean> imFriendListLiveData;
    public MutableLiveData<IMFriendListBean> friendListLiveData;
    public MutableLiveData<IMGroupListBean> imGroupListData;
    public MutableLiveData<IMGroupListBean> groupListData;
    public MutableLiveData<IMFriendApplyCountBean> applyCountData;
    public MutableLiveData applyLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int page;
    public int count;

    public IMAddVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        searchLiveData = new MutableLiveData<>();
        applyListLiveData = new MutableLiveData<>();
        applyDetailLiveData = new MutableLiveData<>();
        applyOperateLiveData = new MutableLiveData<>();
        imFriendListLiveData = new MutableLiveData<>();
        friendListLiveData = new MutableLiveData<>();
        imGroupListData = new MutableLiveData<>();
        groupListData = new MutableLiveData<>();
        applyCountData = new MutableLiveData<>();
        applyLiveData = new MutableLiveData();
        pageStateLiveData = new MutableLiveData<>();
    }

    //好友群组搜索列表
    public void search(String name){
        new IMRepository().imFriendSearch(searchLiveData, pageStateLiveData, userId, name);
    }

    //通讯录 好友申请操作
    public void imFriendApply(int receiverId,String msg){
        new IMRepository().imFriendApply(applyLiveData, pageStateLiveData, userId, receiverId, msg);
    }

    //工程圈 新的好友列表
    public void imFriendApplyList(){
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new IMRepository().imFriendApplyList(applyListLiveData, pageStateLiveData, userId);
    }

    //工程圈 好友添加验证
    public void imFriendApplyOperation(int id, int status,String msg){
        new IMRepository().imFriendApplyOperation(applyOperateLiveData, pageStateLiveData, userId, id, status, msg);
    }

    //搜索 更多好友列表
    public void searchFriend(String name){
        page = 1;
        new IMRepository().searchFriend(imFriendListLiveData, pageStateLiveData, userId, name,page);
    }

    //搜索 更多好友列表
    public void searchFriendMore(String name){
        page ++;
        new IMRepository().searchFriend(imFriendListLiveData, pageStateLiveData, userId, name,page);
    }

    //搜索 更多好友列表
    public void friendList(int pageSize){
        page = 1;
        new IMRepository().friendList(friendListLiveData, pageStateLiveData, userId,page,pageSize);
    }

    //搜索 更多好友列表
    public void friendListMore(){
        page ++;
        new IMRepository().friendList(friendListLiveData, pageStateLiveData, userId,page,10);
    }

    //搜索 更多群聊列表
    public void searchGroup(String name){
        page = 1;
        new IMRepository().searchGroup(imGroupListData, pageStateLiveData, userId, name,page);
    }

    //搜索 更多群聊列表
    public void searchGroupMore(String name){
        page ++;
        new IMRepository().searchGroup(imGroupListData, pageStateLiveData, userId, name,page);
    }

    //通讯录 群组列表
    public void myGroup(int pageSize){
        page = 1;
        new IMRepository().myGroup(groupListData, pageStateLiveData, userId,page,pageSize);
    }

    //通讯录 群组列表
    public void myGroupMore(){
        page ++;
        new IMRepository().myGroup(groupListData, pageStateLiveData, userId,page,10);
    }

    //通讯录 好友申请数量
    public void applyCount(){
        new IMRepository().applyCount(applyCountData, pageStateLiveData, userId);
    }
}
