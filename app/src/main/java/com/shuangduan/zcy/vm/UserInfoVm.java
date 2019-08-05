package com.shuangduan.zcy.vm;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.UserInfoBean;
import com.shuangduan.zcy.model.event.AvatarEvent;

import org.greenrobot.eventbus.EventBus;

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
    public MediatorLiveData<Integer> sexLiveData;
    public MediatorLiveData<String> avatarLiveData;
    public MutableLiveData checkTelLiveData;
    public MutableLiveData updateTelLiveData;

    private int userId;
    private int sex = 0;
    private String img;
    public int changeType = 0;
    private String oldTel;

    public UserInfoVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        oldTel = SPUtils.getInstance().getString(SpConfig.MOBILE);
        infoLiveData = new MutableLiveData();
        getInfoLiveData = new MutableLiveData<>();
        informationLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        sexLiveData = (MediatorLiveData<Integer>) Transformations.map(informationLiveData, input -> input.getSex());
        avatarLiveData = (MediatorLiveData<String>) Transformations.map(informationLiveData, input -> input.getImage_thumbnail());
        checkTelLiveData = new MutableLiveData();
        updateTelLiveData = new MutableLiveData();
    }

    public void infoSet(String username, int sex, String company, String position, int[] business_city, int experience, String managing_products){
        new UserRepository().setInfo(infoLiveData, pageStateLiveData, userId, username, sex, company, position, business_city, experience, managing_products);
    }

    public void userInfo(){
        new UserRepository().userInfo(getInfoLiveData, pageStateLiveData, userId);
    }

    public void information(){
        new UserRepository().information(informationLiveData, pageStateLiveData, userId);
    }

    public void updateUserName(String username){
        new UserRepository().updateUserName(infoLiveData, pageStateLiveData, userId, username);
    }

    public void updateAvatar(String avatar){
        changeType = 1;
        new UserRepository().updateAvatar(infoLiveData, pageStateLiveData, userId, avatar);
    }

    public void updateSex(int sex){
        changeType = 2;
        new UserRepository().updateSex(infoLiveData, pageStateLiveData, userId, sex);
    }

    public void checkTel(String code){
        new UserRepository().checkTel(checkTelLiveData, pageStateLiveData, userId, code);
    }

    public void updateTel(String tel, String code, String oldCode){
        new UserRepository().updateTel(checkTelLiveData, pageStateLiveData, userId, tel, code, oldTel, oldCode);
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * 服务器修改成功，更新界面显示
     */
    public void updateSex(){
        sexLiveData.postValue(sex);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void updateImage(){
        avatarLiveData.postValue(img);
        EventBus.getDefault().post(new AvatarEvent(img));
    }
}
