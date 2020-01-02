package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.ShareBean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.api.repository
 * @ClassName: ShareRepository
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/23 10:42
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/23 10:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ShareRepository extends BaseRepository {

    //推荐好友分享信息
    public void getUserInfoShare(MutableLiveData<ShareBean.DataBean> liveData, MutableLiveData<String> pageState, int user_id) {
        request(apiService.getUserInfoShare(user_id)).setData(liveData).setPageState(pageState).send();
    }

    //工程信息分享
    public void getProjectShare(MutableLiveData<ShareBean.DataBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.projectShare(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //招采信息分享
    public void tendererShare(MutableLiveData<ShareBean.DataBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.tendererShare(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //基建头条分享
    public void headlinesShare(MutableLiveData<ShareBean.DataBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.headlinesShare(userId, id)).setData(liveData).setPageState(pageState).send();
    }

    //基建物资分享
    public void materialShare(MutableLiveData<ShareBean.DataBean> liveData, MutableLiveData<String> pageState, int userId, int id) {
        request(apiService.materialShare(userId, id)).setData(liveData).setPageState(pageState).send();
    }
}
