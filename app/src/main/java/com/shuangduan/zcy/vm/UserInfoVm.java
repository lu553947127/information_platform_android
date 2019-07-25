package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.UserInfoBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe  个人信息录入
 * @time 2019/7/18 16:38
 * @change
 * @chang time
 * @class describe
 */
public class UserInfoVm extends BaseViewModel {

    public MutableLiveData infoLiveData;
    public MutableLiveData<UserInfoBean> getInfoLiveData;
    public MutableLiveData<UserInfoBean> informationLiveData;
    public MutableLiveData<String> pageStateLiveData;

    private int userId;

    public UserInfoVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
    }

    public void infoSet(String username, int sex, String company, String position, int[] business_city, int experience, String managing_products){
        UserRepository userRepository = new UserRepository();
        infoLiveData = userRepository.setInfo(userId, username, sex, company, position, business_city, experience, managing_products);
        pageStateLiveData = userRepository.getPageStateLiveData();
    }

    public void userInfo(){
        UserRepository userRepository = new UserRepository();
        getInfoLiveData = userRepository.userInfo(userId);
        pageStateLiveData = userRepository.getPageStateLiveData();
    }

    public void information(){
        UserRepository userRepository = new UserRepository();
        informationLiveData = userRepository.information(userId);
        pageStateLiveData = userRepository.getPageStateLiveData();
    }

    public void updateUserName(String username){
        UserRepository userRepository = new UserRepository();
        infoLiveData = userRepository.updateUserName(userId, username);
        pageStateLiveData = userRepository.getPageStateLiveData();
    }

    public void updateAvatar(String avatar){
        UserRepository userRepository = new UserRepository();
        infoLiveData = userRepository.updateAvatar(userId, avatar);
        pageStateLiveData = userRepository.getPageStateLiveData();
    }

}
