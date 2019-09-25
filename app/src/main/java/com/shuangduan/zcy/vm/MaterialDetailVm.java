package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.MaterialRepository;
import com.shuangduan.zcy.model.bean.MaterialDepositingPlaceBean;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/7 14:57
 * @change
 * @chang time
 * @class describe
 */
public class MaterialDetailVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<MaterialDetailBean> detailLiveData;
    public MutableLiveData collectedLiveData;
    public MutableLiveData collectLiveData;
    public MutableLiveData<List<MaterialDepositingPlaceBean>>depositingPlaceBeanMutableLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public int id;

    public MaterialDetailVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        collectedLiveData = new MutableLiveData();
        collectLiveData = new MutableLiveData();
        depositingPlaceBeanMutableLiveData = new MutableLiveData<>();
    }

    public void getDetail(int id){
        new MaterialRepository().materialDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    public void getCollected(){
        new MaterialRepository().collected(collectedLiveData, pageStateLiveData, userId, id);
    }

    public void getCollect(){
        new MaterialRepository().collect(collectLiveData, pageStateLiveData, userId, id);
    }

    public void getAddressList(){
        new MaterialRepository().getAddress(depositingPlaceBeanMutableLiveData, pageStateLiveData, userId, id);
    }
}
