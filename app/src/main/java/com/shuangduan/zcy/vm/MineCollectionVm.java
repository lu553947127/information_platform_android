package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.MaterialCollectBean;
import com.shuangduan.zcy.model.bean.ProjectCollectBean;
import com.shuangduan.zcy.model.bean.RecruitBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/5 14:48
 * @change
 * @chang time
 * @class describe
 */
public class MineCollectionVm extends BaseViewModel {
    public MutableLiveData<ProjectCollectBean> projectCollectLiveData;
    public MutableLiveData<RecruitBean> recruitCollectLiveData;
    public MutableLiveData<String> pageStateLiveData;

    //基建物资收藏
    public MutableLiveData<MaterialCollectBean> materialCollectLiveData;

    private int userId;
    public int projectPage;
    public int recruitPage;
    public int materialPage;

    public MineCollectionVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        projectCollectLiveData = new MutableLiveData<>();
        recruitCollectLiveData = new MutableLiveData<>();
        materialCollectLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void projectCollection() {
        projectPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().projectCollection(projectCollectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void moreProjectCollection() {
        projectPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().projectCollection(projectCollectLiveData, pageStateLiveData, userId, projectPage);
    }

    public void recruitCollection() {
        recruitPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().recruitCollection(recruitCollectLiveData, pageStateLiveData, userId, recruitPage);
    }

    public void moreRecruitCollection() {
        recruitPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().recruitCollection(recruitCollectLiveData, pageStateLiveData, userId, recruitPage);
    }


    public void materialCollection() {
        materialPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().materialCollection(materialCollectLiveData, pageStateLiveData, userId, materialPage);
    }

    public void moreMaterialCollection() {
        materialPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().materialCollection(materialCollectLiveData, pageStateLiveData, userId, materialPage);
    }
}
