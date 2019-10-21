package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.ProjectFilterBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/19 14:18
 * @change
 * @chang time
 * @class describe
 */
public class ProjectListVm extends BaseViewModel {
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<ProjectInfoBean> projectLiveData;
    private int userId;
    private int page = 1;
    public List<Integer> province = null;
    public List<Integer> city = null;
    public List<Integer> phases = null;
    public List<Integer> type = null;
    public String stime = null;
    public String etime = null;
    public String warrant_status = null;
    public int currentSelect = 0;//当前显示的弹窗

    /**
     * 初始化数据
     */
    public void init() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        projectLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        projectList();
    }

    public void projectList() {
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        ProjectFilterBean projectFilterBean = new ProjectFilterBean();
        projectFilterBean.setProvince(province);
        projectFilterBean.setCity(city);
        projectFilterBean.setType(type);
        projectFilterBean.setPhases(phases);

        new ProjectRepository().projectList(projectLiveData, pageStateLiveData, userId, projectFilterBean, stime, etime, warrant_status, page);
    }

    public void moreProjectList() {
        page++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        ProjectFilterBean projectFilterBean = new ProjectFilterBean();
        projectFilterBean.setProvince(province);
        projectFilterBean.setCity(city);
        projectFilterBean.setType(type);
        projectFilterBean.setPhases(phases);
        new ProjectRepository().projectList(projectLiveData, pageStateLiveData, userId, projectFilterBean, stime, etime, warrant_status, page);
    }
}
