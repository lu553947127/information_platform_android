package com.shuangduan.zcy.rongyun;

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

        @Override
        public String toString() {
            return "ExtraData{" +
                    "id=" + id +
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
