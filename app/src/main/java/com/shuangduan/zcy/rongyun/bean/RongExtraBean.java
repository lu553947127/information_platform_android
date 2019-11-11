package com.shuangduan.zcy.rongyun.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.rongyun$
 * @class RongExtra$
 * @class describe
 * @time 2019/10/18 11:15
 * @change
 * @class describe
 */
public class RongExtraBean {
    public int type;

    public ExtraData data;

    public class ExtraData {
        public int id;

        @SerializedName("order_id")
        public int orderId;

        @SerializedName("user_id")
        public int userId;

        public String name;

        @Override
        public String toString() {
            return "ExtraData{" +
                    "id=" + id +
                    ", orderId=" + orderId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RongExtraBean{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
