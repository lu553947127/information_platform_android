package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.SearchCompanyBean;
import com.shuangduan.zcy.model.bean.UserInfoBean;

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

    /**
     * 录入信息
     */
    public MutableLiveData setInfo(int user_id, String username, int sex, String company, String position, int[] business_city, int experience, String managing_products){
        BaseSubscriber subscriber = request(apiService.setInfo(user_id, username, sex, company, position, business_city, experience, managing_products)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 我的界面
     */
    public MutableLiveData<UserInfoBean> userInfo(int user_id){
        BaseSubscriber subscriber = request(apiService.userInfo(user_id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 个人信息
     */
    public MutableLiveData<UserInfoBean> information(int user_id){
        BaseSubscriber subscriber = request(apiService.information(user_id)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新用户名
     */
    public MutableLiveData updateUserName(int user_id, String username){
        BaseSubscriber subscriber = request(apiService.updateUserName(user_id, username)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新头像
     */
    public MutableLiveData updateAvatar(int user_id, String avatar){
        BaseSubscriber subscriber = request(apiService.updateAvatar(user_id, avatar)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新性别
     */
    public MutableLiveData updateSex(int user_id, int sex){
        BaseSubscriber subscriber = request(apiService.updateSex(user_id, sex)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新公司
     */
    public MutableLiveData updateSex(int user_id, String company){
        BaseSubscriber subscriber = request(apiService.updateCompany(user_id, company)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新职位
     */
    public MutableLiveData updatePosition(int user_id, String position){
        BaseSubscriber subscriber = request(apiService.updatePosition(user_id, position)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新城市
     */
    public MutableLiveData updateBusinessCity(int user_id, int[] businessCity){
        BaseSubscriber subscriber = request(apiService.updateBusinessCity(user_id, businessCity)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新经验
     */
    public MutableLiveData updateExperience(int user_id, int experience){
        BaseSubscriber subscriber = request(apiService.updateExperience(user_id, experience)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

    /**
     * 更新产品
     */
    public MutableLiveData updateProduct(int user_id, String product){
        BaseSubscriber subscriber = request(apiService.updateProduct(user_id, product)).send();
        pageStateLiveData = subscriber.getPageState();
        return subscriber.getData();
    }

}
