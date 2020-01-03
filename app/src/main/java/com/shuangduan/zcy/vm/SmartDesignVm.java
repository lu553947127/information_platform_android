package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.adminManage.bean.OrderSearchBean;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.SmartDesignOrderBean;
import com.shuangduan.zcy.model.bean.SmartDesignPhasesBean;

import java.util.List;

public class SmartDesignVm extends BaseViewModel {
    private int userId;
    public MutableLiveData addSmartDesignLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<SmartDesignOrderBean> orderLiveData;
    public MutableLiveData<SmartDesignOrderBean.SmartDesignOrder> orderDetailsLiveData;

    public MutableLiveData<List<SmartDesignPhasesBean>> phasesLiveData;

    public MutableLiveData editLiveData;

    private int page = 1;

    public SmartDesignVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        addSmartDesignLiveData = new MutableLiveData();
        orderLiveData = new MutableLiveData<>();
        orderDetailsLiveData = new MutableLiveData<>();

        phasesLiveData = new MutableLiveData<>();
        editLiveData = new MutableLiveData();
    }



    //提交智能设计
    public void addSmartDesign(String mobile, String email, String automateName, String automateDetail) {
        new UserRepository().addSmartDesign(addSmartDesignLiveData, pageStateLiveData, userId, mobile, email, automateName, automateDetail);
    }

    //获取智能设计订单列表
    public void dataList() {
        page = 1;
        new UserRepository().dataList(orderLiveData, pageStateLiveData, userId, page);
    }

    public void moreDataList() {
        page++;
        new UserRepository().dataList(orderLiveData, pageStateLiveData, userId, page);
    }

    //获取智能设计订单详情
    public void getSmartDesignDetail(int id) {
        new UserRepository().getSmartDesignDetail(orderDetailsLiveData, pageStateLiveData, userId, id);
    }

    //智能设计阶段列表
    public void getPhases() {
        new UserRepository().getPhases(phasesLiveData, pageStateLiveData, userId);
    }

    /**
     * 取消 或 删除 设计订单
     *
     * @param id
     * @param status 编辑状态 1取消 2删除
     */
    public void editPost(int id, int status) {
        new UserRepository().editPost(editLiveData,pageStateLiveData,userId,id,status);
    }

}


