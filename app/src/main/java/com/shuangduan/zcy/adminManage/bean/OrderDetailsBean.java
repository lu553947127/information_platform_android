package com.shuangduan.zcy.adminManage.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.bean$
 * @class OrderDetailsBean$
 * @class describe
 * @time 2019/11/15 11:31
 * @change
 * @class describe
 */
public class OrderDetailsBean {
    //物资类别
    @SerializedName("category_name")
    public String categoryName;
    //物质名称
    @SerializedName("material_name")
    public String materialIdName;
    //商品价格
    @SerializedName("price")
    public String price;
    //商品单位
    @SerializedName("unit")
    public String unit;
    //规格
    @SerializedName("spec")
    public String spec;
    //供应方式 1 出租 2 售卖
    public int method;
    //租赁开始时间
    @SerializedName("lease_start_time")
    public String leaseStartTime;
    //租赁结束时间
    @SerializedName("lease_end_time")
    public String leaseEndTime;
    //图片集合
    public List<Images> images;

    //预定数量
    public String number;

    //存放地省
    public String province;
    //存放地市
    public String city;
    //存放地详细地址
    public String address;
    //物资子公司
    public String company;
    //物资项目
    @SerializedName("unit_name")
    public String unitName;
    //订单号
    @SerializedName("order_number")
    public String orderNumber;
    //创建时间
    @SerializedName("create_time")
    public String createTime;
    //订单进度
    @SerializedName("phases")
    public String phases;
    //联系人
    @SerializedName("real_name")
    public String realName;
    //联系电话
    public String tel;
    //收货人公司
    @SerializedName("order_company")
    public String orderCompany;
    //收货省份
    @SerializedName("order_province")
    public String orderProvince;
    //收货城市
    @SerializedName("order_city")
    public String orderCity;
    //收货人详细地址
    @SerializedName("order_address")
    public String orderAddress;
    //订单备注
    public String remark;
    //订单状态 1订单生效 2取消订单 3驳回订单
    public int status;

    public class Images{
        public String url;
    }

    @Override
    public String toString() {
        return "OrderDetailsBean{" +
                "categoryName='" + categoryName + '\'' +
                ", materialIdName='" + materialIdName + '\'' +
                ", price='" + price + '\'' +
                ", unit='" + unit + '\'' +
                ", spec='" + spec + '\'' +
                ", method=" + method +
                ", leaseStartTime='" + leaseStartTime + '\'' +
                ", leaseEndTime='" + leaseEndTime + '\'' +
                ", images=" + images +
                ", number=" + number +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                ", unitName='" + unitName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", createTime='" + createTime + '\'' +
                ", phases='" + phases + '\'' +
                ", realName='" + realName + '\'' +
                ", tel='" + tel + '\'' +
                ", orderCompany='" + orderCompany + '\'' +
                ", orderProvince='" + orderProvince + '\'' +
                ", orderCity='" + orderCity + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
