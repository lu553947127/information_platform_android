package com.shuangduan.zcy.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.TransRecordBean;
import com.shuangduan.zcy.model.bean.TransRecordDetailBean;
import com.shuangduan.zcy.model.bean.TransRecordFilterBean;
import com.shuangduan.zcy.model.bean.TransactionFlowTypeBean;


/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/16 9:30
 * @change
 * @chang time
 * @class describe
 */
public class TransRecordVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<TransRecordBean> transRecordLiveData;
    public LiveData<TransRecordDetailBean> detailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<Integer> idLiveData;
    public MutableLiveData<TransRecordFilterBean> filterLiveData;
    private int page = 1;
    public int type = 0;
    public int currentShow;
    public TransactionFlowTypeBean flowType = new TransactionFlowTypeBean();

    public TransRecordVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        transRecordLiveData = new MutableLiveData<>();
        filterLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        idLiveData = new MutableLiveData<>();
        detailLiveData = Transformations.switchMap(idLiveData, input -> {
            MutableLiveData<TransRecordDetailBean> detailLiveData = new MutableLiveData<>();
            new UserRepository().transRecordDetail(detailLiveData, pageStateLiveData, userId, input);
            return detailLiveData;
        });
    }

    public void getRecord(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().transRecord(transRecordLiveData, pageStateLiveData, userId, page, type, flowType);
    }

    public void getMoreRecord(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new UserRepository().transRecord(transRecordLiveData, pageStateLiveData, userId, page, type, flowType);
    }

    public void getFilter(){
        new UserRepository().transRecordFilter(filterLiveData, pageStateLiveData, userId);
    }
}
