package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.PayRepository;
import com.shuangduan.zcy.model.bean.CoinPayResultBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/14 16:27
 * @change
 * @chang time
 * @class describe
 */
public class CoinPayVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<CoinPayResultBean> contentPayLiveData;
    public MutableLiveData<CoinPayResultBean> locusPayLiveData;
    public MutableLiveData<CoinPayResultBean> supplierPayLiveData;
    public MutableLiveData<CoinPayResultBean> recruitPayLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public int projectId;
    public int locusId;
    public int supplierId;
    public int recruitId;

    public CoinPayVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        contentPayLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        locusPayLiveData = new MutableLiveData<>();
        supplierPayLiveData = new MutableLiveData<>();
        recruitPayLiveData = new MutableLiveData<>();
    }

    public void payProject(String pwd){
        new PayRepository().payProjectContent(contentPayLiveData, pageStateLiveData, userId, projectId, pwd);
    }

    public void payLocus(String pwd){
        new PayRepository().payLocus(locusPayLiveData, pageStateLiveData, userId, locusId, pwd);
    }

    public void paySupplier(String pwd){
        new PayRepository().paySupplier(supplierPayLiveData, pageStateLiveData, userId, supplierId, pwd);
    }

    public void payRecruit(String pwd){
        new PayRepository().payRecruit(recruitPayLiveData, pageStateLiveData, userId, recruitId, pwd);
    }
}
