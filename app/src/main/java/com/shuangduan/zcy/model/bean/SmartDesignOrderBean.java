package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SmartDesignOrderBean {


    public int page;
    public int count;
    public List<SmartDesignOrderBean.SmartDesignOrder> list;


    public class SmartDesignOrder {
        //订单Id
        public int id;
        //订单编号
        @SerializedName("order_no")
        public String orderNo;

        //手机号
        public String mobile;

        //邮箱
        public String email;

        //产品名称
        @SerializedName("automate_name")
        public String automateName;

        //产品详情
        @SerializedName("automate_detail")
        public String automateDetail;

        //阶段状态标识
        @SerializedName("phase_status")
        public int phaseStatus;

        //订单状态 1正常 2取消 3删除
        @SerializedName("order_status")
        public int orderStatus;

        //订单时间
        @SerializedName("add_time")
        public String addTime;


        //操作状态   0不可操作 1可取消 2可删除
        @SerializedName("opera_status")
        public int operaStatus;

        //订单状态
        @SerializedName("status")
        public String status;
    }
}
