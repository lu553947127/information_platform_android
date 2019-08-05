package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.model.bean.ProjectSubBean;
import com.shuangduan.zcy.model.bean.RecruitSubBean;
import com.shuangduan.zcy.model.bean.SubBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
    public int projectPage = 1;
    public int RecruitPage = 1;
    public MutableLiveData<ProjectSubBean> projectLiveData;
    public MutableLiveData<RecruitSubBean> trackLiveData;
    public MutableLiveData<List<MyPhasesBean>> phasesLiveData;
    public MutableLiveData phasesSetLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public List<Integer> phasesId;

    public MineSubVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        projectLiveData = new MutableLiveData<>();
        trackLiveData = new MutableLiveData<>();
        phasesLiveData = new MutableLiveData<>();
        phasesSetLiveData = new MutableLiveData<>();
        phasesId = new ArrayList<>();
    }

    public void myProject(){
        projectPage = 1;
        new ProjectRepository().projectSub(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void refreshMyProject(){
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new ProjectRepository().projectSub(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void myRecruit(){
        RecruitPage = 1;
//        new ProjectRepository().myProjectTrack(trackLiveData, pageStateLiveData, userId, RecruitPage);
    }

    public void refreshMyRecruit(){
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
//        new ProjectRepository().myProjectTrack(trackLiveData, pageStateLiveData, userId, RecruitPage);
    }

    public void myPhases(){
        new UserRepository().myPhases(phasesLiveData, pageStateLiveData, userId);
    }

    public void setPhases(){
        SubBean subBean = new SubBean(phasesId);
        new UserRepository().setPhases(phasesSetLiveData, pageStateLiveData, userId, subBean);
    }

}
