package com.shuangduan.zcy.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UploadRepository;
import com.shuangduan.zcy.model.bean.UploadBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/25 14:17
 * @change
 * @chang time
 * @class describe
 */
public class UploadPhotoVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<String> mPageStateLiveData;
    public MutableLiveData<UploadBean> uploadLiveData;
    public MutableLiveData<Integer> changeLiveData;

    public UploadPhotoVm() {
        this.userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        changeLiveData = new MutableLiveData<>();
    }

    public UploadRepository repository;
    public void upload(String path){
        repository = new UploadRepository(){
            @Override
            public void compressed(MutableLiveData<UploadBean> liveData) {
                uploadLiveData = liveData;
                mPageStateLiveData = repository.getPageStateLiveData();
                changeLiveData.postValue(2);
            }
        };
        repository.uploadPhoto(userId, path);
    }

}
