package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/16 9:05
 * @change
 * @chang time
 * @class describe
 */
public class TransRecordBean {

    /**
     * page : 1
     * count : 20
     * list : [{"id":197,"user_id":31,"remark":"通过工程发布：兰州至海口国家高速公路重庆至遵义段（贵州境）扩容工程第CZTJ-11标段，收益20.00金币","price":"20.00","type":1,"flow_type":17,"create_time":"2019-08-08 17:15:11"}]
     */

    private int page;
    private int count;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 197
         * user_id : 31
         * remark : 通过工程发布：兰州至海口国家高速公路重庆至遵义段（贵州境）扩容工程第CZTJ-11标段，收益20.00金币
         * price : 20.00
         * type : 1
         * flow_type : 17
         * create_time : 2019-08-08 17:15:11
         */

        private int id;
        private int user_id;
        private String remark;
        private String price;
        private int type;
        private String flow_type;
        private String create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getFlow_type() {
            return flow_type;
        }

        public void setFlow_type(String flow_type) {
            this.flow_type = flow_type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
