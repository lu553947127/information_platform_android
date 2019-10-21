package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.bean.BankCardDisBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/6 16:39
 * @change
 * @chang time
 * @class describe
 */
public class BankCardVm extends BaseViewModel {
    private int userId;
    public MutableLiveData<List<BankCardBean>> bankcardLiveData;
    public MutableLiveData<BankCardDisBean> disLiveData;
    public MutableLiveData AddLiveData;
    public MutableLiveData unbindLiveData;
    public MutableLiveData<String> pageStateLiveData;
    public String imageBandCard;//识别银行卡的拍照

    public BankCardVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        bankcardLiveData = new MutableLiveData<>();
        disLiveData = new MutableLiveData<>();
        AddLiveData = new MutableLiveData<>();
        unbindLiveData = new MutableLiveData<>();
    }

    public void bankcardList(){
        new UserRepository().bankcardList(bankcardLiveData, pageStateLiveData, userId);
    }

    public void bankcardDis(){
        new UserRepository().bankcardDis(disLiveData, pageStateLiveData, userId, imageBandCard);
    }

    public void bankcardAdd(String bank_card_num, String opening_bank){
        LogUtils.i(userId, bank_card_num, opening_bank);
        new UserRepository().bankcardAdd(AddLiveData, pageStateLiveData, userId, bank_card_num, opening_bank);
    }

    public void unbindBankcard(int bankcard_id){
        new UserRepository().unbindBankcard(unbindLiveData, pageStateLiveData, userId, bankcard_id);
    }

}
