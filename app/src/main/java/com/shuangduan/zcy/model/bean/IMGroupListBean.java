package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/9/9 11:22
 * @change
 * @chang time
 * @class describe
 */
public class IMGroupListBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1567999285
     * data : {"page":1,"count":6,"list":[{"group_id":8,"group_name":"陕西合阳至铜川、吴起至华池高速公路PPP（HTTJ-04标）李","province":"陕西省","city":""},{"group_id":9,"group_name":"丹锡李高速公路克什克腾至承德联络线克什克腾（经棚）至乌兰布统 （蒙冀界）段公路施工总承包","province":"内蒙古自治区","city":""},{"group_id":14,"group_name":"提供给李","province":"河北省","city":"唐山市"},{"group_id":15,"group_name":"新模式李","province":"内蒙古自治区","city":"包头市"},{"group_id":10,"group_name":"黑龙江跨江大桥1","province":"黑龙江","city":"黑河市"},{"group_id":16,"group_name":"江北高速1标","province":"湖北省","city":""}]}
     */

    private String code;
    private String msg;
    private int time;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : 1
         * count : 6
         * list : [{"group_id":8,"group_name":"陕西合阳至铜川、吴起至华池高速公路PPP（HTTJ-04标）李","province":"陕西省","city":""},{"group_id":9,"group_name":"丹锡李高速公路克什克腾至承德联络线克什克腾（经棚）至乌兰布统 （蒙冀界）段公路施工总承包","province":"内蒙古自治区","city":""},{"group_id":14,"group_name":"提供给李","province":"河北省","city":"唐山市"},{"group_id":15,"group_name":"新模式李","province":"内蒙古自治区","city":"包头市"},{"group_id":10,"group_name":"黑龙江跨江大桥1","province":"黑龙江","city":"黑河市"},{"group_id":16,"group_name":"江北高速1标","province":"湖北省","city":""}]
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
             * group_id : 8
             * group_name : 陕西合阳至铜川、吴起至华池高速公路PPP（HTTJ-04标）李
             * province : 陕西省
             * city :
             */

            private String group_id;
            private String group_name;
            private String province;
            private String city;

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }
        }
    }
}
