package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.HomeRepository;
import com.shuangduan.zcy.model.bean.ExplainDetailBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/22 10:39
 * @change
 * @chang time
 * @class describe
 */
public class ExplainVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<ExplainDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public int id;

    public ExplainVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getDetail(){
        new HomeRepository().explainDetail(detailLiveData, pageStateLiveData, userId, id);
    }
}
