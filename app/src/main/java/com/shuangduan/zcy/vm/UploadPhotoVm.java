package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UploadRepository;
import com.shuangduan.zcy.model.bean.UploadBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/25 14:17
 * @change
 * @chang time
 * @class describe
 */
public class UploadPhotoVm extends BaseViewModel {
    public static final int ID_CARD_POSITIVE = 1;//身份证正面
    public static final int ID_CARD_NEGATIVE = 2;//身份证反面
    private int userId;
    public MutableLiveData<String> mPageStateLiveData;
    public MutableLiveData<UploadBean> uploadLiveData;
    public int type = 0;

    public UploadPhotoVm() {
        this.userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        mPageStateLiveData = new MutableLiveData<>();
        uploadLiveData = new MutableLiveData<>();
    }

    public void upload(String path){
        new UploadRepository().uploadPhoto(uploadLiveData, mPageStateLiveData, userId, path);
    }

}
