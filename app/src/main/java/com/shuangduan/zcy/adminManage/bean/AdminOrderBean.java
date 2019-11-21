package com.shuangduan.zcy.adminManage.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.bean$
 * @class OrderBean$
 * @class describe
 * @time 2019/11/12 16:46
 * @change
 * @class describe
 */
public class AdminOrderBean {

    public int page;
    public int count;
    public List<OrderList> list;

    public class OrderList {
        //周转材料订单ID
        @SerializedName("order_id")
        public int orderId;
        //设备订单ID
        public int id;
        //订单号
        @SerializedName("order_number")
        public String orderNumber;

        //子公司Id
        @SerializedName("supplier_id")
        public int supplierId;

        //所属项目Id
        @SerializedName("unit_id")
        public int unitId;

        //订单阶段
        public String phases;
        //订单阶段ID
        @SerializedName("phasess")
        public int phasesId;

        //订单状态
        public String status;
        //订单状态ID
        @SerializedName("statuss")
        public int statusId;

        //订单类型  1: 公开 3：内定
        public int inside;
        //供应方式 1 出租 2 售卖
        public int method;
        //是否可以修改阶段 1是 0否
        @SerializedName("status_update")
        public int statusUpdate;
        //材料类别名称
        @SerializedName("category_name")
        public String categoryName;
        //材料名称
        @SerializedName("material_id_name")
        public String materialIdName;
        //订单描述
        @SerializedName("inside_name")
        public String insideName;
        //公司名称
        public String company;
        //项目名称
        @SerializedName("unit_name")
        public String unitName;

    }
}
