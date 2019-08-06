package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.RecruitReporitory;
import com.shuangduan.zcy.model.bean.RecruitBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/6 9:51
 * @change
 * @chang time
 * @class describe
 */
public class RecruitVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<RecruitBean> recruitMutableLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int page = 1;

    public RecruitVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        recruitMutableLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getRecruit(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new RecruitReporitory().recruitList(recruitMutableLiveData, pageStateLiveData, userId, page);
    }

    public void refreshRecruit(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new RecruitReporitory().recruitList(recruitMutableLiveData, pageStateLiveData, userId, page);
    }
}
