package com.shuangduan.zcy.vm;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.MaterialRepository;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;

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
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<Integer> suitNumLiveData;
    public MutableLiveData orderLiveData;
    public int id;

    public MaterialDetailVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        orderLiveData = new MutableLiveData();
        suitNumLiveData = new MutableLiveData<>();
        suitNumLiveData.postValue(0);
    }

    public void getDetail(){
        new MaterialRepository().materialDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    /**
     * 预定增加
     */
    public void add(){
        Integer value = suitNumLiveData.getValue();
        value++;
        suitNumLiveData.postValue(value);
    }

    /**
     * 预定减少
     */
    public void less(){
        Integer num = suitNumLiveData.getValue();
        if (num <= 0) return;
        num--;
        suitNumLiveData.postValue(num);
    }

    public void pre(){
        new MaterialRepository().materialOrder(orderLiveData, pageStateLiveData, userId, id, suitNumLiveData.getValue());
    }
}
