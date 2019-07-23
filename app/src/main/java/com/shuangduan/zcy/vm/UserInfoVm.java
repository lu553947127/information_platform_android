package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;

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

}
