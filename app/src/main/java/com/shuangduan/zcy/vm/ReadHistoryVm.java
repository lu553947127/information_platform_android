package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.vm
 * @class describe
 * @time 2019/7/10 16:06
 * @change
 * @chang time
 * @class describe
 */
public class ReadHistoryVm extends BaseViewModel {
    private MutableLiveData<List<ReadHistoryBean>> readHistoryBeanMutableLiveData;

    public ReadHistoryVm() {
        this.readHistoryBeanMutableLiveData = new MutableLiveData<>();
        addLiveData(readHistoryBeanMutableLiveData);
    }

    public void getHistory(){
        List<ReadHistoryBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ReadHistoryBean readHistoryBean = new ReadHistoryBean();
            readHistoryBean.setTitle("红星美凯龙");
            readHistoryBean.setTime("2019-6-1 15:25");
            list.add(readHistoryBean);
        }
        readHistoryBeanMutableLiveData.postValue(list);
    }

    public MutableLiveData<List<ReadHistoryBean>> getReadHistoryBeanMutableLiveData() {
        return readHistoryBeanMutableLiveData;
    }
}
