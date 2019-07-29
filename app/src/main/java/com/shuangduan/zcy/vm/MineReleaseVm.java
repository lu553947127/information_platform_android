package com.shuangduan.zcy.vm;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.ProjectMineBean;

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
public class MineReleaseVm extends BaseViewModel {

    private int userId;
    public int projectPage = 1;
    public int tractPage = 1;
    public MutableLiveData<ProjectMineBean> projectLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public int type = 1;

    public MineReleaseVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        projectLiveData = new MutableLiveData<>();
    }

    public void myProject(){
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new ProjectRepository().myProject(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void refreshMyProject(){
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new ProjectRepository().myProject(projectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void myProjectTract(){

    }

    public void refreshMyProjectTract(){
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
    }

}
