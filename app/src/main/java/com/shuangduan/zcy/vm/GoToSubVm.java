package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.ProjectSubConfirmBean;
import com.shuangduan.zcy.model.bean.ProjectSubFirstBean;
import com.shuangduan.zcy.model.bean.ProjectSubViewBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/22 15:12
 * @change
 * @chang time
 * @class describe
 */
public class GoToSubVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<ProjectSubFirstBean> startLiveData;
    public MutableLiveData<ProjectSubConfirmBean> confirmLiveData;
    public MutableLiveData<ProjectSubViewBean> viewLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public int projectId;
    public int month;
    public String orderSn;

    public GoToSubVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        startLiveData = new MutableLiveData<>();
        confirmLiveData = new MutableLiveData<>();
        viewLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    /**
     * 认购信息展示
     */
    public void startWarrant(){
        new ProjectRepository().startWarrant(startLiveData, pageStateLiveData, userId, projectId);
    }

    /**
     * 认购信息确认
     */
    public void confirmWarrant(){
        new ProjectRepository().confirmWarrant(confirmLiveData, pageStateLiveData, userId, projectId, month, orderSn);
    }

    /**
     * 已认购信息展示
     */
    public void viewWarrant(){
        new ProjectRepository().viewWarrant(viewLiveData, pageStateLiveData, userId, projectId);
    }
}
