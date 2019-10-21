package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.MapBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/18 20:55
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoVm extends BaseViewModel {

    public MutableLiveData<List<MapBean>> mapLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;

    public ProjectInfoVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        mapLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void mapList(double lng, double lat){
        new ProjectRepository().mapList(mapLiveData, pageStateLiveData, userId, lng, lat);
    }

}
