package com.shuangduan.zcy.vm;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.DemandRepository;
import com.shuangduan.zcy.model.bean.DemandReleaseBean;
import com.shuangduan.zcy.utils.DataUtils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/19 17:12
 * @change
 * @chang time
 * @class describe
 */
public class DemandReleaseVm extends BaseViewModel {
    public static final int RELEASE_TYPE_RELATIONSHIP = 1;
    public static final int RELEASE_TYPE_SUBSTANCE = 2;
    public static final int RELEASE_TYPE_BUYER = 3;
    public MutableLiveData<DemandReleaseBean> releaseLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int releaseType = RELEASE_TYPE_RELATIONSHIP;
    public String startTime;
    public String selectEndTime;
    public String endTime;

    public DemandReleaseVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        releaseLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        startTime = DataUtils.formatTime(year, month, day);
        if (month != 12){
            selectEndTime = DataUtils.formatTime(year, month + 1, day);
        }else {
            selectEndTime = DataUtils.formatTime(year + 1, 1, day);
        }
    }

    public void releaseRelationShip(String title, String intro, String price){
        if (StringUtils.isTrimEmpty(endTime)){
            ToastUtils.showShort(StringUtils.getString(R.string.select_time));
            return;
        }
        new DemandRepository().demandRelationshipRelease(releaseLiveData, pageStateLiveData, userId, title, intro, startTime, endTime, price);
    }

    public void releaseSubstance(){
//        new DemandRepository().demandSubstanceRelease(releaseLiveData, pageStateLiveData, userId, title, intro, startTime, endTime);
    }

    public void releaseBuyer(){
//        new DemandRepository().demandBuyerRelease(releaseLiveData, pageStateLiveData, userId, title, intro, startTime, endTime);
    }
}
