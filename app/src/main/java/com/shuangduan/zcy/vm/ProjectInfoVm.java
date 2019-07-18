package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.MapBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
    private int userId;

    public ProjectInfoVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
    }

    public void mapList(double lng, double lat){
        mapLiveData = new ProjectRepository().mapList(userId, lng, lat);
    }

}
