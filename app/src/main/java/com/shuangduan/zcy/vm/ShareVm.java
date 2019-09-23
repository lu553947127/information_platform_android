package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ShareRepository;
import com.shuangduan.zcy.model.bean.ShareBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/15 16:47
 * @change
 * @chang time
 * @class describe
 */
public class ShareVm extends BaseViewModel {

    private int userId;
    public MutableLiveData<ShareBean> shareLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public ShareVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        shareLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void userInfoShare(){
        new ShareRepository().getUserInfoShare(shareLiveData, pageStateLiveData, userId);
    }
}
