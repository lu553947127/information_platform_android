package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.vm
 * @class describe  地区选择
 * @time 2019/7/9 17:35
 * @change
 * @chang time
 * @class describe
 */
public class AreaVm extends BaseViewModel {

    private MutableLiveData<ProvinceBean> provinceLiveData;
    private MutableLiveData<CityBean> cityLiveData;

    public AreaVm() {
        this.provinceLiveData = new MutableLiveData<>();
        this.cityLiveData = new MutableLiveData<>();
        addLiveData(provinceLiveData);
        addLiveData(cityLiveData);
    }

    public void setProvinceLiveData(MutableLiveData<ProvinceBean> provinceLiveData) {
        this.provinceLiveData = provinceLiveData;
    }

    public void setCityLiveData(MutableLiveData<CityBean> cityLiveData) {
        this.cityLiveData = cityLiveData;
    }
}
