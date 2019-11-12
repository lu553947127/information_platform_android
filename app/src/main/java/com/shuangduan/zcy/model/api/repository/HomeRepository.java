package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.ExplainDetailBean;
import com.shuangduan.zcy.model.bean.HeadlinesBean;
import com.shuangduan.zcy.model.bean.HeadlinesDetailBean;
import com.shuangduan.zcy.model.bean.HelpBean;
import com.shuangduan.zcy.model.bean.HomeBannerBean;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.model.bean.SupplierCliqueBean;
import com.shuangduan.zcy.model.bean.HomePushBean;
import com.shuangduan.zcy.model.bean.SupplierRoleBean;
import com.shuangduan.zcy.model.bean.VersionUpgradesBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/15 9:26
 * @change
 * @chang time
 * @class describe
 */
public class HomeRepository extends BaseRepository {
    public void homePush(MutableLiveData<List<HomePushBean>> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.homePush(userId)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    public void homeBanner(MutableLiveData<List<HomeBannerBean>> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.homeBanner(userId)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    public void homeList(MutableLiveData<HomeListBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.homeList(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void headlines(MutableLiveData<HeadlinesBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int page){
        request(apiService.headlines(userId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void headlinesDetail(MutableLiveData<HeadlinesDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.headlinesDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void explainDetail(MutableLiveData<ExplainDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.explainDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getSupplierClique(MutableLiveData<SupplierCliqueBean> liveData, int userId){
        request(apiService.getSupplierClique(userId)).setData(liveData).send();
    }

    public void getSupplierRole(MutableLiveData<SupplierRoleBean> liveData, int userId){
        request(apiService.getSupplierRole(userId)).setData(liveData).send();
    }

    public void help(MutableLiveData<HelpBean> liveData, MutableLiveData<String> pageStateLiveData, int userId){
        request(apiService.help(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void versionUpgrade(MutableLiveData<VersionUpgradesBean> liveData, MutableLiveData<String> pageStateLiveData, int userId,String version,int type){
        request(apiService.versionUpgrade(userId,version,type)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
