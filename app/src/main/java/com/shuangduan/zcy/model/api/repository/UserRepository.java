package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.SearchCompanyBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/18 16:25
 * @change
 * @chang time
 * @class describe
 */
public class UserRepository extends BaseRepository {

    public MutableLiveData<SearchCompanyBean> searchCompany(int user_id, String title){
        BaseSubscriber subscriber = request(apiService.searchCompany(user_id, title)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    public MutableLiveData setInfo(int user_id, String username, int sex, String company, String position, int[] business_city, int experience, String managing_products){
        BaseSubscriber subscriber = request(apiService.setInfo(user_id, username, sex, company, position, business_city, experience, managing_products)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

}
