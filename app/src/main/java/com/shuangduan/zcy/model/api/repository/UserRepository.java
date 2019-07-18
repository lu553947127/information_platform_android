package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

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
        return request(apiService.searchCompany(user_id, title)).send().getData();
    }

    public MutableLiveData setInfo(int user_id, String username, int sex, String company, String position, Integer[] business_city, int experience, String managing_products){
        return request(apiService.setInfo(user_id, username, sex, company, position, business_city, experience, managing_products)).send().getData();
    }

}
