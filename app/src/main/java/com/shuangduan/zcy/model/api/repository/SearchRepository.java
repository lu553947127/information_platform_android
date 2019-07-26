package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.SearchBean;
import com.shuangduan.zcy.model.bean.SearchHotBean;

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

    public void search(MutableLiveData<SearchBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String keyWord){
        request(apiService.keywordList(user_id, keyWord)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void searchHot(MutableLiveData<List<SearchHotBean>> liveData, int user_id){
        request(apiService.keywordHot(user_id)).setDataList(liveData).send();
    }

}
