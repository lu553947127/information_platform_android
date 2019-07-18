package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;

import java.util.List;

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

    public MutableLiveData<List<ProvinceBean>> provinceLiveData;
    public MutableLiveData<List<CityBean>> cityLiveData;

    private int userId;

    public AreaVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        cityLiveData = new MutableLiveData<>();
    }

    public void getProvince(){
        provinceLiveData = new ProjectRepository().getProvince(userId);
    }

    public void getCity(int id){
        cityLiveData = new ProjectRepository().getCity(userId, id);
    }

}
