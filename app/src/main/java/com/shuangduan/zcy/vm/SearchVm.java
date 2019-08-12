package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.SearchRepository;
import com.shuangduan.zcy.model.bean.SearchBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/23 8:55
 * @change
 * @chang time
 * @class describe
 */
public class SearchVm extends BaseViewModel {

    public MutableLiveData<List<String>> hotLiveData;
    public MutableLiveData<SearchBean> searchLiveData;
    public MutableLiveData<List<String>> companyLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<List<String>> historyLiveData;
    private int userId;

    public SearchVm(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        hotLiveData = new MutableLiveData<>();
        searchLiveData = new MutableLiveData<>();
        companyLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        historyLiveData = new MutableLiveData<>();
    }

    public void search(String keyWord){
        new SearchRepository().search(searchLiveData, pageStateLiveData, userId, keyWord);
        boolean isRemove = false;
        String history = SPUtils.getInstance().getString(SpConfig.SEARCH_HISTORY);
        String[] split = history.split("|,");
        List<String> historyList = new ArrayList<>();
        Collections.addAll(historyList, split);
        for (int i = 0; i < historyList.size(); i++) {
            if (keyWord.equals(historyList.get(i))){
                historyList.remove(i);
                isRemove = true;
            }
        }
        if (!isRemove && historyList.size() >= 20){
            historyList.remove(historyList.size() - 1);
        }
        historyList.add(0, keyWord);
    }

    public void getHot(){
        new SearchRepository().searchHot(hotLiveData, userId);
    }

    public void getHistory(){
        String history = SPUtils.getInstance().getString(SpConfig.SEARCH_HISTORY);
        String[] split = history.split("|,");
        List<String> historyList = new ArrayList<>();
        Collections.addAll(historyList, split);
        historyLiveData.postValue(historyList);
    }

    public void searchCompany(String keyword){
        new SearchRepository().searchCompany(companyLiveData, pageStateLiveData, userId, keyword);
    }

}
