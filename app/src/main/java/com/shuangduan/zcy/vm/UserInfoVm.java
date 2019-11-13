package com.shuangduan.zcy.vm;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.BusinessAreaBean;
import com.shuangduan.zcy.model.bean.UserInfoBean;
import com.shuangduan.zcy.model.event.AvatarEvent;
import com.shuangduan.zcy.model.event.MultiAreaEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 徐玉 QQ:876885613
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
    public MutableLiveData<String> company;
    public LiveData companyLiveData;
    public MutableLiveData<String> office;
    public LiveData officeLiveData;
    public LiveData areaLiveData;
    public MutableLiveData<Integer> experience;
    public LiveData expLiveData;
    public MutableLiveData<MultiAreaEvent> multiAreaLiveData;

    private int userId;
    public int uid;
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
        company = new MutableLiveData<>();
        companyLiveData = Transformations.switchMap(company, input -> {
            MutableLiveData data = new MutableLiveData();
            new UserRepository().updateCompany(data, pageStateLiveData, userId, input);
            return data;
        });
        office = new MutableLiveData<>();
        officeLiveData = Transformations.switchMap(office, input -> {
            MutableLiveData data = new MutableLiveData();
            new UserRepository().updatePosition(data, pageStateLiveData, userId, input);
            return data;
        });
        multiAreaLiveData = new MutableLiveData<>();
        areaLiveData = Transformations.switchMap(multiAreaLiveData, (Function<MultiAreaEvent, LiveData<Object>>) input -> {
            MutableLiveData data = new MutableLiveData();
            BusinessAreaBean businessAreaBean = new BusinessAreaBean();
            businessAreaBean.setBusiness_city(input.getCityResult());
            new UserRepository().updateBusinessCity(data, pageStateLiveData, userId, businessAreaBean);
            return data;
        });
        experience = new MutableLiveData<>();
        expLiveData = Transformations.switchMap(experience, (Function<Integer, LiveData<Object>>) input -> {
            MutableLiveData data = new MutableLiveData();
            new UserRepository().updateExperience(data, pageStateLiveData, userId, input);
            return data;
        });
    }

    public void infoSet(String username, int sex, String company, String position, MultiAreaEvent business_city, int experience, String managing_products){
        BusinessAreaBean businessAreaBean = new BusinessAreaBean();
        if (business_city != null){
            businessAreaBean.setBusiness_city(business_city.getCityResult());
        }
        new UserRepository().setInfo(infoLiveData, pageStateLiveData, userId, username, sex, company, position, businessAreaBean, experience, managing_products);
    }

    public void userInfo(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        LogUtils.e("userId>>>>>>>>>>>>"+userId);
        new UserRepository().userInfo(getInfoLiveData, pageStateLiveData, userId);
    }

    public void information(){
        new UserRepository().information(informationLiveData, pageStateLiveData, userId, uid);
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
        new UserRepository().updateTel(updateTelLiveData, pageStateLiveData, userId, tel, code, oldTel, oldCode);
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

    public void updateProduction(String production){
        new UserRepository().updateProduct(infoLiveData, pageStateLiveData, userId, production);
    }
}
