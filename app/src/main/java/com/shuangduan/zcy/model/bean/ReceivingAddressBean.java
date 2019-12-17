package com.shuangduan.zcy.model.bean;

import java.util.List;

public class ReceivingAddressBean {

    public int page;
    public int count;
    public List<ReceivingAddressBean.Address> list;

    public class Address {

        //姓名
        public String name;
        //手机号
        public String phone;
        //公司
        public String company;
        //地址
        public String address;
        //是否默认
        public int state;
    }
}
