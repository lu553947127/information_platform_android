package com.shuangduan.zcy.adminManage.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.bean$
 * @class TurnoverDetailBean$
 * @class describe
 * @time 2019/10/31 14:25
 * @change
 * @class describe
 */
public class TurnoverDetailBean {

    //材料类别名称
    @SerializedName("category_name")
    public String categoryName;

    //材料名称
    @SerializedName("material_id_name")
    public String materialIdName;

    //所属项目名称
    @SerializedName("unit_id_name")
    public String unitIdName;

    //库存数量
    public String stock;

    //单价
    @SerializedName("unit_price")
    public String unitPrice;

    //单位描述
    @SerializedName("unit_name")
    public String unitName;

    //规格
    public String spec;

    //使用状态描述
    @SerializedName("use_status_name")
    public String useStatusName;

    //材料状态描述
    @SerializedName("material_status_name")
    public String materialStatusName;

    //省份
    @SerializedName("province_name")
    public String provinceName;
    //城市
    @SerializedName("city_name")
    public String cityName;
    //详细地址
    @SerializedName("address")
    public String address;

    //责任人
    @SerializedName("person_liable")
    public String personLiable;

    //联系电话
    public String tel;
    //是否易损
    @SerializedName("rapid_wear")
    public String rapidWear;
    //是否上架
    @SerializedName("is_shelf_name")
    public String isShelfName;
    //供应方式 1出租 2售卖
    @SerializedName("method")
    public int method;
    //上架开始时间
    @SerializedName("shelf_start_time")
    public String shelfStartTime;
    //上架结束时间
    @SerializedName("shelf_end_time")
    public String shelfEndTime;
    //内部上架类型 1到期自动公开  2到期自动下架
    @SerializedName("shelf_type")
    public int shelfType;
    //指导价格
    @SerializedName("guidance_price")
    public String guidancePrice;
    //下部使用计划描述
    @SerializedName("plan_name")
    public String planName;
    //在用数量
    @SerializedName("use_count")
    public String useCount;
    //开始使用日期
    @SerializedName("start_date")
    public String startDate;
    //材料进场时间
    @SerializedName("entry_time")
    public String entryTime;
    //材料退场时间
    @SerializedName("exit_time")
    public String exitTime;
    //累计摊售
    @SerializedName("accumulated_amortization")
    public String accumulatedAmortization;
    //周转材料原值
    @SerializedName("original_price")
    public String originalPrice;
    //净值
    @SerializedName("net_worth")
    public String netWorth;
    //备注
    @SerializedName("remark")
    public String remark;

    public String username;

    public List<Images> images;

    public class Images {
        //原图
        public String url;
        //缩略图
        @SerializedName("heade_url")
        public String heade_url;
    }

    @Override
    public String toString() {
        return "TurnoverDetailBean{" +
                "categoryName='" + categoryName + '\'' +
                ", materialIdName='" + materialIdName + '\'' +
                ", unitIdName='" + unitIdName + '\'' +
                ", stock=" + stock +
                ", unitPrice='" + unitPrice + '\'' +
                ", unitName='" + unitName + '\'' +
                ", spec='" + spec + '\'' +
                ", useStatusName='" + useStatusName + '\'' +
                ", materialStatusName='" + materialStatusName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", personLiable='" + personLiable + '\'' +
                ", tel='" + tel + '\'' +
                ", rapidWear='" + rapidWear + '\'' +
                ", isShelfName='" + isShelfName + '\'' +
                ", method=" + method +
                ", shelfStartTime='" + shelfStartTime + '\'' +
                ", shelfEndTime='" + shelfEndTime + '\'' +
                ", shelfType=" + shelfType +
                ", guidancePrice='" + guidancePrice + '\'' +
                ", planName='" + planName + '\'' +
                ", useCount=" + useCount +
                ", startDate='" + startDate + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", exitTime='" + exitTime + '\'' +
                ", accumulatedAmortization='" + accumulatedAmortization + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", netWorth='" + netWorth + '\'' +
                ", remark='" + remark + '\'' +
                ", username='" + username + '\'' +
                ", images=" + images +
                '}';
    }
}
