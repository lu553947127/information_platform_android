package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.vm
 * @class describe
 * @time 2019/7/10 16:06
 * @change
 * @chang time
 * @class describe
 */
public class ReadHistoryVm extends BaseViewModel {
    public MutableLiveData<List<ReadHistoryBean>> projectHistoryLiveData;
    public MutableLiveData<List<ReadHistoryBean>> recruitHistoryLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;

    public ReadHistoryVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        this.projectHistoryLiveData = new MutableLiveData<>();
        this.recruitHistoryLiveData = new MutableLiveData<>();
        this.pageStateLiveData = new MutableLiveData<>();
    }

    public void getProjectHistory(){
        new UserRepository().historyProject(projectHistoryLiveData, pageStateLiveData, userId);
    }

    public void getRecruitHistory(){
        new UserRepository().historyRecruit(recruitHistoryLiveData, pageStateLiveData, userId);
    }

}
