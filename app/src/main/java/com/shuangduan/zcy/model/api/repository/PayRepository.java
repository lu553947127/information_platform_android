package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.CoinPayResultBean;
import com.shuangduan.zcy.model.bean.PayInfoBean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/29 11:44
 * @change
 * @chang time
 * @class describe
 */
public class PayRepository extends BaseRepository {

    /**
     * 获取支付宝和微信的订单信息
     */
    public void getPayInfo(MutableLiveData<PayInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, String amount, int type){
        request(apiService.recharge(userId, amount, type)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 紫金币支付概况
     */
    public void payProjectContent(MutableLiveData<CoinPayResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, String coin_password){
        request(apiService.payProjectContent(userId, id, coin_password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 紫金币支付轨迹
     */
    public void payLocus(MutableLiveData<CoinPayResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, String coin_password){
        request(apiService.payLocus(userId, id, coin_password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 紫金币支付供应商
     */
    public void paySupplier(MutableLiveData<CoinPayResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, String coin_password){
        request(apiService.paySupplier(userId, id, coin_password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 紫金币支付招采信息
     */
    public void payRecruit(MutableLiveData<CoinPayResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, String coin_password){
        request(apiService.payRecruit(userId, id, coin_password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 紫金币支付找物质信息
     */
    public void payFindSubstance(MutableLiveData<CoinPayResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, String coin_password){
        request(apiService.payFindSubstance(userId, id, coin_password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 紫金币支付找买家信息
     */
    public void payFindBuyer(MutableLiveData<CoinPayResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, String coin_password){
        request(apiService.payFindBuyer(userId, id, coin_password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 紫金币支付发布找关系佣金
     */
    public void payRelationshipRelease(MutableLiveData<CoinPayResultBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id, String coin_password){
        request(apiService.payRelationshipRelease(userId, id, coin_password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

}
