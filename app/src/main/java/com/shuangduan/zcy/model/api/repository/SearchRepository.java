package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.PostBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProjectSearchBean;
import com.shuangduan.zcy.model.bean.RecruitBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/23 8:57
 * @change
 * @chang time
 * @class describe
 */
public class SearchRepository extends BaseRepository {

    public void searchProject(MutableLiveData<ProjectInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String keyWord, int page) {
        request(apiService.searchProject(user_id, keyWord, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void searchRecruit(MutableLiveData<RecruitBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String keyWord, int page) {
        request(apiService.searchRecruit(user_id, keyWord, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void searchHot(MutableLiveData<List<String>> liveData, int user_id) {
        request(apiService.keywordHot(user_id)).setDataList(liveData).send();
    }

    public void searchProjectTitle(MutableLiveData<ProjectSearchBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String keyword, int page) {
        request(apiService.keywordTitle(user_id, keyword, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void searchCompany(MutableLiveData<List<String>> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String title) {
        request(apiService.searchCompany(user_id, title)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    //职位搜索
    public void searchPost(MutableLiveData<List<PostBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.searchPost(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
