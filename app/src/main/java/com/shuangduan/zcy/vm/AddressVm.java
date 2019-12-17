package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;

public class AddressVm extends BaseViewModel {

    private int userId, page;

    //地址列表
    public MutableLiveData<ReceivingAddressBean> addressLiveData;
    //地址默认状态
    public MutableLiveData defaultLiveData;
    //删除地址
    public MutableLiveData deleteLiveData;

    public MutableLiveData<String> pageStateLiveData;

    public AddressVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        addressLiveData = new MutableLiveData<>();

        defaultLiveData = new MutableLiveData();

        pageStateLiveData = new MutableLiveData<>();
    }


    //收货地址列表
    public void addressList() {
        new UserRepository().receivingAddress(addressLiveData, pageStateLiveData, userId);
    }

    //更新默认地址状态
    public void setDefaultState(int id) {
        new UserRepository().setDefaultState(defaultLiveData, pageStateLiveData, id);
    }

    //删除地址
    public void deleteAddress(int id) {
        new UserRepository().deleteAddres(defaultLiveData, pageStateLiveData, id);
    }


}
