package com.shuangduan.zcy.vm;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.HomeRepository;
import com.shuangduan.zcy.model.bean.HelpBean;
import com.shuangduan.zcy.model.bean.HomeBannerBean;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.model.bean.SupplierCliqueBean;
import com.shuangduan.zcy.model.bean.HomePushBean;
import com.shuangduan.zcy.model.bean.SupplierRoleBean;
import com.shuangduan.zcy.model.bean.VersionUpgradesBean;
import com.shuangduan.zcy.utils.VersionUtils;

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

    private Context context;
    private int userId;
    public MutableLiveData<List<HomePushBean>> pushLiveData;
    public MutableLiveData<List<HomeBannerBean>> bannerLiveData;
    public MutableLiveData<HomeListBean> listLiveData;
    public MutableLiveData<SupplierCliqueBean> supplierCliqueLiveData;
    public MutableLiveData<SupplierRoleBean> supplierRoleLiveData;
    public MutableLiveData<HelpBean> helpLiveData;
    public MutableLiveData<VersionUpgradesBean> versionUpgradesLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public HomeVm() {
        LogUtils.e("运行了....");


        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pushLiveData = new MutableLiveData<>();
        bannerLiveData = new MutableLiveData<>();
        listLiveData = new MutableLiveData<>();
        supplierCliqueLiveData = new MutableLiveData<>();
        supplierRoleLiveData = new MutableLiveData<>();
        helpLiveData = new MutableLiveData<>();
        versionUpgradesLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getInit(Context context){
        this.context = context;
        getPush();
        getBanner();
        getList();
        versionUpgrade();
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

    //版本升级 type：1 安卓，2 ios
    public void versionUpgrade(){
        new HomeRepository().versionUpgrade(versionUpgradesLiveData, pageStateLiveData, userId, VersionUtils.getVerName(context),1);
    }

    //后台管理权限
    public void getSupplierRole(){
        new HomeRepository().getSupplierRole(supplierRoleLiveData,userId);
    }

    //基建物资 内定物资显示权限 supplier_status 0.普通用户 1.普通供应商 2.集团供应商子公司 3.集团供应商
    public void getSupplierClique(){
        new HomeRepository().getSupplierClique(supplierCliqueLiveData,userId);
    }

    //帮助列表
    public void help(){
        new HomeRepository().help(helpLiveData, pageStateLiveData, userId);
    }
}
