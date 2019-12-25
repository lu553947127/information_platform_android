package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.DemandRepository;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.model.bean.FindRelationshipAcceptBean;
import com.shuangduan.zcy.model.bean.FindRelationshipReleaseBean;
import com.shuangduan.zcy.model.bean.RelationshipDetailBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/20 14:12
 * @change
 * @chang time
 * @class describe
 */
public class DemandRelationshipVm extends BaseViewModel {
    private int userId;
    private int page;
    private int pageAccept;
    private int pageRelease;
    public MutableLiveData<DemandRelationshipBean> relationshipLiveData;
    public MutableLiveData<DemandRelationshipBean> acceptLiveData;
    public MutableLiveData<DemandRelationshipBean> releaseLiveData;
    public MutableLiveData<RelationshipDetailBean> relationshipDetailLiveData;
    public MutableLiveData<FindRelationshipReleaseBean> relationshipReleaseDetailLiveData;
    public MutableLiveData<FindRelationshipAcceptBean> relationshipAcceptDetailLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData replyLiveData;

    public MutableLiveData cancelLiveData;

    public int id;
    public int relationships_reply_id;

    public DemandRelationshipVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        relationshipLiveData = new MutableLiveData<>();
        acceptLiveData = new MutableLiveData<>();
        releaseLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        replyLiveData = new MutableLiveData();
        relationshipDetailLiveData = new MutableLiveData<>();
        relationshipReleaseDetailLiveData = new MutableLiveData<>();
        relationshipAcceptDetailLiveData = new MutableLiveData<>();

        cancelLiveData = new MutableLiveData();
    }

    public void getRelationship() {
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandRelationship(relationshipLiveData, pageStateLiveData, userId, page);
    }

    public void getMoreRelationship() {
        page++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandRelationship(relationshipLiveData, pageStateLiveData, userId, page);
    }

    public void getReleaseRelationship() {
        pageRelease = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandRelationshipRelease(releaseLiveData, pageStateLiveData, userId, pageRelease);
    }

    public void getMoreReleaseRelationship() {
        pageRelease++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandRelationshipRelease(releaseLiveData, pageStateLiveData, userId, pageRelease);
    }

    public void getAcceptRelationship() {
        pageAccept = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandRelationshipAccept(acceptLiveData, pageStateLiveData, userId, pageAccept);
    }

    public void getMoreAcceptRelationship() {
        pageAccept++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new DemandRepository().demandRelationshipAccept(acceptLiveData, pageStateLiveData, userId, pageAccept);
    }

    public void reply(String name, String tel, String remarks) {
        new DemandRepository().orderTaking(replyLiveData, pageStateLiveData, userId, id, name, tel, remarks);
    }

    public void relationshipDetail() {
        new DemandRepository().relationshipDetail(relationshipDetailLiveData, pageStateLiveData, userId, id);
    }

    public void releaseDetail() {
        new DemandRepository().mineReleaseRelationshipDetail(relationshipReleaseDetailLiveData, pageStateLiveData, userId, id);
    }

    public void acceptDetail() {
        new DemandRepository().mineAcceptRelationshipDetail(relationshipAcceptDetailLiveData, pageStateLiveData, userId, relationships_reply_id);
    }

    /**
     * 取消找关系发布
     *
     * @param id
     */
    public void closeRelation(int id) {
        new DemandRepository().closeRelation(cancelLiveData,pageStateLiveData,userId,id);
    }


}
