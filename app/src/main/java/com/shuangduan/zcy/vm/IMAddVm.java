package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.IMRepository;
import com.shuangduan.zcy.model.bean.IMFriendApplyDetailBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyOperationBean;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;

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
    public MutableLiveData applyLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public String searchName;
    public int receiverId;
    private int pageNewFriend;

    public IMAddVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        searchLiveData = new MutableLiveData<>();
        applyListLiveData = new MutableLiveData<>();
        applyDetailLiveData = new MutableLiveData<>();
        applyOperateLiveData = new MutableLiveData<>();
        applyLiveData = new MutableLiveData();
        pageStateLiveData = new MutableLiveData<>();
    }

    //好友群组搜索列表
    public void search(String name){
        new IMRepository().imFriendSearch(searchLiveData, pageStateLiveData, userId, name);
    }

    public void apply(String msg){
        new IMRepository().imFriendApply(applyLiveData, pageStateLiveData, userId, receiverId, msg);
    }

    public void newFriendList(){
        pageNewFriend = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new IMRepository().imFriendApplyList(applyListLiveData, pageStateLiveData, userId, pageNewFriend);
    }

    public void moreNewFriendList(){
        pageNewFriend ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new IMRepository().imFriendApplyList(applyListLiveData, pageStateLiveData, userId, pageNewFriend);
    }

    public void operateNewFriend(int id, int status){
        new IMRepository().imFriendApplyOperation(applyOperateLiveData, pageStateLiveData, userId, id, status, "");
    }
}
