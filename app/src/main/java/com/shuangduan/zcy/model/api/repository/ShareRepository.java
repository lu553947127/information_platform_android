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
    public void getUserInfoShare(MutableLiveData<ShareBean> liveData, MutableLiveData<String> pageState, int user_id){
        request(apiService.getUserInfoShare(user_id)).setDataList(liveData).setPageState(pageState).send();
    }
}
