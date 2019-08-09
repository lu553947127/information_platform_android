package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.SearchRepository;
import com.shuangduan.zcy.model.bean.ProjectSearchBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/2 9:16
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSearchVm extends BaseViewModel {
    public MutableLiveData<ProjectSearchBean> searchLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    private int page = 1;
    private String keyword;

    public ProjectSearchVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        searchLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void search(String keyword){
        this.keyword = keyword;
        page = 1;
        pageStateLiveData.setValue(PageState.PAGE_REFRESH);
        new SearchRepository().searchProjectTitle(searchLiveData, pageStateLiveData, userId, keyword, page);
    }

    public void moreSearch(){
        page++;
        new SearchRepository().searchProjectTitle(searchLiveData, pageStateLiveData, userId, keyword, page);
    }

}
