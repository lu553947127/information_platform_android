package com.shuangduan.zcy.vm;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/5 14:18
 * @change
 * @chang time
 * @class describe
 */
public class FeedbackVm extends BaseViewModel {
    public MutableLiveData feedbackLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;

    public FeedbackVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        feedbackLiveData = new MutableLiveData();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void submit(String s){
        new UserRepository().feedback(feedbackLiveData, pageStateLiveData, userId, s);
    }
}
