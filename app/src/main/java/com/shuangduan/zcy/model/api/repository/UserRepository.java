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

    public void searchCompany(MutableLiveData<SearchCompanyBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String title){
        request(apiService.searchCompany(user_id, title)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 录入信息
     */
    public void setInfo(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String username, int sex, String company, String position, int[] business_city, int experience, String managing_products){
        request(apiService.setInfo(user_id, username, sex, company, position, business_city, experience, managing_products)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 我的界面
     */
    public void userInfo(MutableLiveData<UserInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id){
        request(apiService.userInfo(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 个人信息
     */
    public void information(MutableLiveData<UserInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id){
        request(apiService.information(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新用户名
     */
    public void updateUserName(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String username){
        request(apiService.updateUserName(user_id, username)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新头像
     */
    public void updateAvatar(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String avatar){
        request(apiService.updateAvatar(user_id, avatar)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新性别
     */
    public void updateSex(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int sex){
        request(apiService.updateSex(user_id, sex)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新公司
     */
    public void updateSex(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String company){
        request(apiService.updateCompany(user_id, company)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新职位
     */
    public void updatePosition(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String position){
        request(apiService.updatePosition(user_id, position)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新城市
     */
    public void updateBusinessCity(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int[] businessCity){
        request(apiService.updateBusinessCity(user_id, businessCity)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新经验
     */
    public void updateExperience(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int experience){
        request(apiService.updateExperience(user_id, experience)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新产品
     */
    public void updateProduct(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String product){
        request(apiService.updateProduct(user_id, product)).setData(liveData).setPageState(pageStateLiveData).send();
    }

}
