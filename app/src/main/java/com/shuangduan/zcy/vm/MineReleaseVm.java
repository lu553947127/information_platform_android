package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.model.bean.ProjectMineBean;
import com.shuangduan.zcy.model.bean.TrackMineBean;

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
public class MineReleaseVm extends BaseViewModel {

    private int userId;
    public int projectPage = 1;
    public int tractPage = 1;
    public MutableLiveData<ProjectMineBean> projectLiveData;
    public MutableLiveData<LocusMineBean> trackLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public MineReleaseVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        projectLiveData = new MutableLiveData<>();
        trackLiveData = new MutableLiveData<>();
    }

    public void myProject(){
        projectPage = 1;
        new ProjectRepository().myProject(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void moreMyProject(){
        projectPage++;
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new ProjectRepository().myProject(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void myProjectTract(){
        tractPage = 1;
        new ProjectRepository().myProjectTrack(trackLiveData, pageStateLiveData, userId, tractPage);
    }

    public void moreMyProjectTract(){
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new ProjectRepository().myProjectTrack(trackLiveData, pageStateLiveData, userId, tractPage);
    }

}
