package com.shuangduan.zicaicloudplatform.model.api;

import com.shuangduan.zicaicloudplatform.model.api.retrofit.RetrofitHelper;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.model.api
 * @class describe
 * @time 2019/7/3 17:13
 * @change
 * @chang time
 * @class describe
 */
public class DataManager {

    private final ApiService apiService;

    public DataManager() {
        this.apiService = RetrofitHelper.getApiService();
    }

    private static class Holder{
        private static final DataManager instance = new DataManager();
    }

    public static DataManager getInstance(){
        return Holder.instance;
    }

}
