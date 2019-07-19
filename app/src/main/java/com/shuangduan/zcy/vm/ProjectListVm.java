package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
    public int page = 1;
    private String province = null;
    private String[] city = null;
    private String phases = null;
    private String[] type = null;
    private String stime = null;
    private String etime = null;
    private String warrant_status = null;

    /**
     * 初始化数据
     */
    public void init(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        projectList();
    }

    public void projectList(){
        ProjectRepository projectRepository = new ProjectRepository();
        projectLiveData = projectRepository.projectList(userId, province, city, phases, type, stime, etime, warrant_status, page);
        pageStateLiveData = projectRepository.getPageStateLiveData();
    }
}
