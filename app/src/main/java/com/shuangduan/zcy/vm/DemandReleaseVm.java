package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.DemandRepository;
import com.shuangduan.zcy.model.bean.DemandReleaseBean;
import com.shuangduan.zcy.model.bean.NeedBean;
import com.shuangduan.zcy.model.bean.NeedInfoBean;
import com.shuangduan.zcy.model.bean.RelationshipOrderBean;
import com.shuangduan.zcy.model.bean.UnitBean;
import com.shuangduan.zcy.utils.DateUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/19 17:12
 * @change
 * @chang time
 * @class describe
 */
public class DemandReleaseVm extends BaseViewModel {
    public static final int RELEASE_TYPE_RELATIONSHIP = 1;//找资源发布
    public static final int RELEASE_TYPE_SUBSTANCE = 2;//找物资发布
    public static final int RELEASE_TYPE_BUYER = 3;//找买家发布
    public MutableLiveData<DemandReleaseBean> releaseLiveData;
    public MutableLiveData<RelationshipOrderBean> relationshipOrderLiveData;

    public MutableLiveData<List<UnitBean>> unitLiveData;

    //找方案
    public MutableLiveData liveData;

    //个人中心发布找方案、找物流、找基地列表
    public MutableLiveData<NeedBean> needLiveData;
    //个人中心发布找方案、找物流、找基地详情
    public MutableLiveData<NeedInfoBean> needInfoLiveData;


    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int releaseType = RELEASE_TYPE_RELATIONSHIP;
    public String startTime;
    public String endTime;
    public int way = 1;

    private int drawingPage;

    private int logisticsPage;

    public DemandReleaseVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        releaseLiveData = new MutableLiveData<>();
        relationshipOrderLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        unitLiveData = new MutableLiveData<>();

        liveData = new MutableLiveData();
        needLiveData = new MutableLiveData<>();

        needInfoLiveData = new MutableLiveData<>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        startTime = DateUtils.formatTime(year, month, day);
    }

    //找资源
    public void releaseRelationShip(String title, String intro, String price) {
        if (StringUtils.isTrimEmpty(endTime)) {
            ToastUtils.showShort(StringUtils.getString(R.string.select_time));
            return;
        }
        new DemandRepository().demandRelationshipRelease(releaseLiveData, pageStateLiveData, userId, title, intro, startTime, endTime, price);
    }

    //找物资
    public void releaseSubstance(String title, String demandNum, String projectName, String projectAddress, String priceAccept, String tel, String owner, int demand_num, String remark) {
        new DemandRepository().demandSubstanceRelease(releaseLiveData, pageStateLiveData, userId, title, demandNum, projectName, projectAddress, priceAccept, tel, owner, startTime, endTime, demand_num, remark);
    }

    //找买家
    public void releaseBuyer(String title, String demandNum, String projectAddress, String priceAccept, String tel, String owner, int supply_num, String remark) {
        new DemandRepository().demandBuyerRelease(releaseLiveData, pageStateLiveData, userId, title, demandNum, projectAddress, priceAccept, tel, owner, way, startTime, endTime, supply_num, remark);
    }

    public void relationshipReleaseOrder(int id) {
        new DemandRepository().relationshipReleaseOrder(relationshipOrderLiveData, pageStateLiveData, userId, id);
    }

    //获取数量单位
    public void getUnit() {
        new DemandRepository().getUnit(unitLiveData, pageStateLiveData, userId);
    }

    //发布找方案
    public void bluePrintAdd(String projectName, String projectLocation, String remark, String personalName, String tel) {
        new DemandRepository().bluePrintAdd(liveData, pageStateLiveData, userId, projectName, projectLocation, startTime, endTime, remark, personalName, tel);
    }

    //发布找物流
    public void logisticsAdd(String materialName, int materialCount, int unit, String deliveryAddress, String receivingAddress,
                             String receivingTime, String personalName, String tel, String remark) {
        new DemandRepository().logisticsAdd(liveData, pageStateLiveData, userId, materialName, materialCount,
                unit, deliveryAddress, receivingAddress, receivingTime, startTime, endTime, personalName, tel, remark);
    }


    //个人中心发布找方案列表
    public void drawingList() {
        drawingPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().drawingList(needLiveData, pageStateLiveData, userId, drawingPage);
    }

    public void moreDrawingList() {
        drawingPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().drawingList(needLiveData, pageStateLiveData, userId, drawingPage);
    }

    //个人中心发布找方案详情
    public void drawingDetail(int id) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().drawingDetail(needInfoLiveData, pageStateLiveData, userId, id);
    }

    //个人中心取消发布方案
    public void drawingClose(int id) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().drawingClose(needLiveData, pageStateLiveData, userId, id);
    }


    //个人中心发布找物流列表
    public void logisticsList() {
        logisticsPage = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().logisticsList(needLiveData, pageStateLiveData, userId, logisticsPage);
    }

    public void moreLogisticsList() {
        logisticsPage++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().logisticsList(needLiveData, pageStateLiveData, userId, logisticsPage);
    }

    //个人中心发布找物流详情
    public void logisticsDetail(int id) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().logisticsDetail(needInfoLiveData, pageStateLiveData, userId, id);
    }

    //个人中心取消发布物流
    public void logisticsClose(int id) {
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().logisticsClose(needLiveData, pageStateLiveData, userId, id);
    }

}
