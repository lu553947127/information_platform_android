package com.shuangduan.zcy.adminManage.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.bean
 * @ClassName: OrderSearchBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/15 9:46
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/15 9:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderSearchBean {
    private List<OrderStatusBean> order_status;
    private List<OrderPhasesBean> order_phases;
    private List<InsideBean> inside;

    public List<OrderStatusBean> getOrder_status() {
        return order_status;
    }

    public void setOrder_status(List<OrderStatusBean> order_status) {
        this.order_status = order_status;
    }

    public List<OrderPhasesBean> getOrder_phases() {
        return order_phases;
    }

    public void setOrder_phases(List<OrderPhasesBean> order_phases) {
        this.order_phases = order_phases;
    }

    public List<InsideBean> getInside() {
        return inside;
    }

    public void setInside(List<InsideBean> inside) {
        this.inside = inside;
    }

    public static class OrderStatusBean {
        /**
         * id : 1
         * name : 订单生效
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class OrderPhasesBean {

        public OrderPhasesBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * id : 1
         * name : 提交订单
         */

        private int id;
        private String name;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "OrderPhasesBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class InsideBean {
        /**
         * id : 1
         * name : 公开物资
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "InsideBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderSearchBean{" +
                "order_status=" + order_status +
                ", order_phases=" + order_phases +
                ", inside=" + inside +
                '}';
    }
}
