package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: SubscribeOrderBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/7 11:10
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/7 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SubscribeOrderBean {

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
         * id : 1594
         * user_id : 48
         * type : 1
         * type_id : 0
         * title : 【易基建】闲置物资
         * content : ydydy新上架闲置物资，包括横移运梁平车查看详情>>
         * send_time : 今天 13:51
         * is_view : 0
         */

        private int id;
        private int user_id;
        private int type;
        private int type_id;
        private String title;
        private int is_view;
        private String materialName;
        private String materialImage;
        private String spec;
        private String totalNumber;
        private String totalPrice;
        private String send_time;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIs_view() {
            return is_view;
        }

        public void setIs_view(int is_view) {
            this.is_view = is_view;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getMaterialImage() {
            return materialImage;
        }

        public void setMaterialImage(String materialImage) {
            this.materialImage = materialImage;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(String totalNumber) {
            this.totalNumber = totalNumber;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }
    }
}
