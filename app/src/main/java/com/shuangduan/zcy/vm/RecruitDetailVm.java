package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.RecruitReporitory;
import com.shuangduan.zcy.model.bean.RecruitDetailBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/6 10:35
 * @change
 * @chang time
 * @class describe
 */
public class RecruitDetailVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<RecruitDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<Integer> collectionLiveData;
    public MutableLiveData collectLiveData;
    public MutableLiveData collectCancelLiveData;

    public RecruitDetailVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        detailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        collectionLiveData = new MutableLiveData<>();
        collectLiveData = new MutableLiveData<>();
        collectCancelLiveData = new MutableLiveData<>();
    }

    public void getDetail(int id){
        new RecruitReporitory().recruitDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    public void collect(int id){
        if (collectionLiveData != null && collectionLiveData.getValue() == 1){
            //取消收藏
            new RecruitReporitory().recruitCancelCollect(collectCancelLiveData, pageStateLiveData, userId, id);
        }else {
            //收藏
            new RecruitReporitory().recruitCollect(collectLiveData, pageStateLiveData, userId, id);
        }
    }

}
