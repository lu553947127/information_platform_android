package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.SearchRepository;
import com.shuangduan.zcy.model.bean.PostBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.RecruitBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/23 8:55
 * @change
 * @chang time
 * @class describe
 */
public class SearchVm extends BaseViewModel {

    public MutableLiveData<List<String>> hotLiveData;
    public MutableLiveData<ProjectInfoBean> searchProjectLiveData;
    public MutableLiveData<RecruitBean> searchRecruitLiveData;
    public MutableLiveData<List<String>> companyLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<List<String>> historyLiveData;
    //岗位
    public MutableLiveData<List<PostBean>> postLiveData;

    private int userId;
    private int projectPage = 1;
    private int recruitPage = 1;
    public String keyword = "";

    public SearchVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        hotLiveData = new MutableLiveData<>();
        searchProjectLiveData = new MutableLiveData<>();
        searchRecruitLiveData = new MutableLiveData<>();
        companyLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        historyLiveData = new MutableLiveData<>();
        postLiveData = new MutableLiveData<>();
    }

    public void search() {
        projectPage = 1;
        recruitPage = 1;
        new SearchRepository().searchProject(searchProjectLiveData, pageStateLiveData, userId, keyword, projectPage);
        new SearchRepository().searchRecruit(searchRecruitLiveData, pageStateLiveData, userId, keyword, recruitPage);
        boolean isRemove = false;
        String history = SPUtils.getInstance().getString(SpConfig.SEARCH_HISTORY);
        String[] split = history.split(",");
        List<String> historyList = new ArrayList<>();
        Collections.addAll(historyList, split);
        for (int i = 0; i < historyList.size(); i++) {
            if (keyword.equals(historyList.get(i))) {
                historyList.remove(i);
                isRemove = true;
            }
        }
        if (!isRemove && historyList.size() >= 20) {
            historyList.remove(historyList.size() - 1);
        }
        historyList.add(0, keyword);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < historyList.size(); i++) {
            if (i == 0) {
                builder.append(historyList.get(i));
            } else {
                builder.append(",").append(historyList.get(i));
            }
        }
        SPUtils.getInstance().put(SpConfig.SEARCH_HISTORY, builder.toString());
        historyLiveData.postValue(historyList);
    }

    public void getHot() {
        new SearchRepository().searchHot(hotLiveData, userId);
    }

    public void getHistory() {
        String history = SPUtils.getInstance().getString(SpConfig.SEARCH_HISTORY);
        String[] split = history.split(",");
        List<String> historyList = new ArrayList<>();
        Collections.addAll(historyList, split);
        historyLiveData.postValue(historyList);
    }

    public void delHistory() {
        SPUtils.getInstance().put(SpConfig.SEARCH_HISTORY, "");
        historyLiveData.postValue(null);
    }

    public void searchCompany(String keyword) {
        new SearchRepository().searchCompany(companyLiveData, pageStateLiveData, userId, keyword);
    }

    public void searchProject() {
        projectPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new SearchRepository().searchProject(searchProjectLiveData, pageStateLiveData, userId, keyword, projectPage);
    }

    public void searchMoreProject() {
        projectPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new SearchRepository().searchProject(searchProjectLiveData, pageStateLiveData, userId, keyword, projectPage);
    }

    public void searchRecruit() {
        recruitPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new SearchRepository().searchRecruit(searchRecruitLiveData, pageStateLiveData, userId, keyword, recruitPage);
    }

    public void searchMoreRecruit() {
        recruitPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new SearchRepository().searchRecruit(searchRecruitLiveData, pageStateLiveData, userId, keyword, recruitPage);
    }

    public void searchPost() {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new SearchRepository().searchPost(postLiveData,pageStateLiveData,userId);
    }
}
