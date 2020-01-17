package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ShareRepository;
import com.shuangduan.zcy.model.bean.ShareBean;

/**
 * @author 徐玉 QQ:876885613
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
    public MutableLiveData<ShareBean.DataBean> shareLiveData;
    public MutableLiveData<String> pageStateLiveData;

    //分享的URL 路径
    public MutableLiveData<String> url;

    public ShareVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        shareLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();

        url = new MutableLiveData<>();
    }

    //工程信息分享
    public void projectShare(int id) {
        new ShareRepository().getProjectShare(shareLiveData, pageStateLiveData, userId, id);
    }

    //个人用户信息分享
    public void userInfoShare() {
        new ShareRepository().getUserInfoShare(shareLiveData, pageStateLiveData, userId);
    }

    //招采信息分享
    public void tendererShare(int id) {
        new ShareRepository().tendererShare(shareLiveData, pageStateLiveData, userId, id);
    }

    //基建头条分享
    public void headlinesShare(int id){
        new ShareRepository().headlinesShare(shareLiveData,pageStateLiveData,userId,id);
    }

    //基建物资周转材料分享
    public void materialShare(int id){
        new ShareRepository().materialShare(shareLiveData,pageStateLiveData,userId,id);
    }

    //基建物资设备物资分享
    public void equipmentShare(int id){
        new ShareRepository().equipmentShare(shareLiveData,pageStateLiveData,userId,id);
    }
}
