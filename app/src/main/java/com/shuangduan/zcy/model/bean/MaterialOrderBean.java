package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.model.bean$
 * @class MaterialOrderBean$
 * @class describe
 * @time 2019/9/25 16:22
 * @change
 * @class describe
 */
public class MaterialOrderBean {
    private int page;
    private int count;
    private List<MaterialOrderBean.ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MaterialOrderBean.ListBean> getList() {
        return list;
    }

    public void setList(List<MaterialOrderBean.ListBean> list) {
        this.list = list;
    }

    public class ListBean {

        //订单ID
        @SerializedName("order_id")
        public int orderId;
        //物资id
        @SerializedName("science_id")
        public int scienceId;
        //分类名称
        @SerializedName("cate_name")
        public String cateName;
        //物资名称
        @SerializedName("material_name")
        public String materialName;

        public String category;

        //供应商名称
        @SerializedName("supplier")
        public String supplier;
        @SerializedName("supplier_company")
        public String supplierCompany;

        //订单号
        @SerializedName("order_sn")
        public String orderSn;
        @SerializedName("order_number")
        public String orderNumber;
        //订单状态
        public String status;
        //订单阶段
        public String phases;
        //缩略图
        public String images;

        //计量单位
        public String unit;

        @SerializedName("address_list")
        public List<AddressList> addressList;

        //下单人
        public String user;
        //联系人
        @SerializedName("real_name")
        public String realName;
        //联系电话
        public String tel;

        //公司名称
        public String company;
        //规格
        public String spec;
        //商品单价
        public String price;
        //下单时间
        @SerializedName("create_time")
        public String createTime;
        //使用地点
        public String address;
        //采购要求
        public String remark;

        //是否能够取消订单 0:不可以取消 1：可以取消
        @SerializedName("is_close")
        public int isClose;
        // 1 出租 2 售卖
        public int method;
        //物资订单来源类型 1公开物资 3内购物资
        public int inside;
        @SerializedName("lease_start_time")
        public String leaseStartTime;
        @SerializedName("lease_end_time")
        public String leaseEndTime;
        //订单数量
        public int number;
        //物资详细地址
        @SerializedName("science_address")
        public String scienceAddress;

        @Override
        public String toString() {
            return "ListBean{" + "scienceId=" + scienceId +
                    ", orderId=" + orderId +
                    ", cateName='" + cateName + '\'' +
                    ", materialName='" + materialName + '\'' +
                    ", category='" + category + '\'' +
                    ", supplier='" + supplier + '\'' +
                    ", orderSn='" + orderSn + '\'' +
                    ", orderNumber='" + orderNumber + '\'' +
                    ", status=" + status +
                    ", images='" + images + '\'' +
                    ", unit='" + unit + '\'' +
                    ", addressList=" + addressList +
                    ", user='" + user + '\'' +
                    ", realName='" + realName + '\'' +
                    ", tel='" + tel + '\'' +
                    ", company='" + company + '\'' +
                    ", address='" + address + '\'' +
                    ", remark='" + remark + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MaterialOrderBean{" +
                "page=" + page +
                ", count=" + count +
                ", list=" + list +
                '}';
    }

    public class AddressList {
        public int number;
        public String address;

        @Override
        public String toString() {
            return "AddressList{" +
                    "number=" + number +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
