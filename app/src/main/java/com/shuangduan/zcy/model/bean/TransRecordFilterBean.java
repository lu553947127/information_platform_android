package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/16 10:47
 * @change
 * @chang time
 * @class describe
 */
public class TransRecordFilterBean {

    private List<FlowTypeBean> flow_type;
    private List<TypeBean> type;

    public List<FlowTypeBean> getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(List<FlowTypeBean> flow_type) {
        this.flow_type = flow_type;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public static class FlowTypeBean extends BaseSelectorBean {
        /**
         * name : 工程信息
         * id : [4,5,6,15,16,17]
         */

        private String name;
        private List<Integer> id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getId() {
            return id;
        }

        public void setId(List<Integer> id) {
            this.id = id;
        }
    }

    public static class TypeBean extends BaseSelectorBean {
        /**
         * id : 1
         * name : 收入
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
}
