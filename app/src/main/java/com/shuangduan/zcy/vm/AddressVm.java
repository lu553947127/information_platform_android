package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.UserRepository;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;

public class AddressVm extends BaseViewModel {

    private int userId, page;  //用户ID，页码
    //当前点击的item的position
    public int position;
    //地址列表
    public MutableLiveData<ReceivingAddressBean> addressLiveData;
    //地址默认状态
    public MutableLiveData defaultLiveData;
    //删除地址
    public MutableLiveData deleteLiveData;
    //新增地址
    public MutableLiveData newAddressLiveData;
    //修改地址
    public MutableLiveData editAddressLiveData;

    public MutableLiveData<String> pageStateLiveData;


    public AddressVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        addressLiveData = new MutableLiveData<>();

        newAddressLiveData = new MutableLiveData();

        editAddressLiveData = new MutableLiveData();

        defaultLiveData = new MutableLiveData();

        deleteLiveData = new MutableLiveData();

        pageStateLiveData = new MutableLiveData<>();
    }


    //收货地址列表
    public void addressList() {
        new UserRepository().receivingAddress(addressLiveData, pageStateLiveData, userId);
    }

    //更新默认地址状态
    public void setDefaultState(int id) {
        new UserRepository().setDefaultState(defaultLiveData, pageStateLiveData, userId, id);
    }

    //删除地址
    public void deleteAddress(int id) {
        new UserRepository().deleteAddress(deleteLiveData, pageStateLiveData, userId, id);
    }

    //新建地址
    public void newAddress(String name, String phone, String company, int province, int city, String address, int state) {
        new UserRepository().newAddress(newAddressLiveData, pageStateLiveData, userId, name, phone, company, province, city, address, state);
    }

    //修改地址
    public void editAddress(int id, String name, String phone, String company, int province, int city, String address, int state) {
        new UserRepository().editAddress(editAddressLiveData, pageStateLiveData, userId, id, name, phone, company, province, city, address, state);
    }

}
