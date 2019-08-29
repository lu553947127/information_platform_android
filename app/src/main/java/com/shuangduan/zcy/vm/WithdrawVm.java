package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.AuthenBean;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.bean.WithdrawBean;
import com.shuangduan.zcy.model.bean.WithdrawRecordBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/6 14:18
 * @change
 * @chang time
 * @class describe
 */
public class WithdrawVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<WithdrawBean> withdrawLiveData;
    public MutableLiveData<List<BankCardBean>> bankcardLiveData;
    public MutableLiveData<WithdrawRecordBean> recordLiveData;
    public MutableLiveData<WithdrawRecordBean.ListBean> recordDetailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData operateLiveData;
    private int recordPage;
    public int withdrawBankcardId = 0;

    public WithdrawVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        withdrawLiveData = new MutableLiveData<>();
        bankcardLiveData = new MutableLiveData<>();
        recordLiveData = new MutableLiveData<>();
        recordDetailLiveData = new MutableLiveData<>();
        operateLiveData = new MutableLiveData<>();
    }

    public void withdrawMsg(){
        new UserRepository().withdrawMsg(withdrawLiveData, pageStateLiveData, userId);
    }

    public void bankcardList(){
        new UserRepository().bankcardList(bankcardLiveData, pageStateLiveData, userId);
    }

    public void withdrawRecord(){
        recordPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().withdrawRecord(recordLiveData, pageStateLiveData, userId, recordPage);
    }

    public void moreWithdrawRecord(){
        recordPage ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().withdrawRecord(recordLiveData, pageStateLiveData, userId, recordPage);
    }

    public void getDetail(int id){
        new UserRepository().withdrawRecordDetail(recordDetailLiveData, pageStateLiveData, userId, id);
    }

    public void withdraw(String price){
        new UserRepository().withdraw(operateLiveData, pageStateLiveData, userId, withdrawBankcardId, price);
    }

}
