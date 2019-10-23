package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.HomeRepository;
import com.shuangduan.zcy.model.bean.HomeBannerBean;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.model.bean.HomeListDetailBean;
import com.shuangduan.zcy.model.bean.HomePushBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/15 9:26
 * @change
 * @chang time
 * @class describe
 */
public class HomeVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<List<HomePushBean>> pushLiveData;
    public MutableLiveData<List<HomeBannerBean>> bannerLiveData;
    public MutableLiveData<HomeListBean> listLiveData;
    public MutableLiveData<HomeListDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public HomeVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pushLiveData = new MutableLiveData<>();
        bannerLiveData = new MutableLiveData<>();
        listLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getInit(){
        getPush();
        getBanner();
        getList();
    }

    //快讯标题
    public void getPush(){
        new HomeRepository().homePush(pushLiveData, pageStateLiveData, userId);
    }

    //首页轮播图
    public void getBanner(){
        new HomeRepository().homeBanner(bannerLiveData, pageStateLiveData, userId);
    }

    //基建头条+收益说明列表
    public void getList(){
        new HomeRepository().homeList(listLiveData, pageStateLiveData, userId);
    }

    //用户权限设置 appoint 1.普通用户 2.普通供应商 3.集团供应商 4.集团供应商子公司
    public void getDetail(){
        new HomeRepository().homeListDetail(detailLiveData, pageStateLiveData, userId);
    }
}
