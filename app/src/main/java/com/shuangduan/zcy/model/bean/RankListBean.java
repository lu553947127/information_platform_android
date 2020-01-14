package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: RankListBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/14 9:33
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/14 9:33
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class RankListBean {
    /**
     * page : 1
     * count : 20
     * list : [{"avatar":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/3bd8277cefd90e37c2faddc25dbe6427.JPG","username":"挂篮模板","company":"山东博远重工集团","count":51558},{"avatar":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/7f0ba48c8a3bac73cf75eaebf4746cce.jpg","username":"托架","company":"山东博远重工集团","count":51558},{"avatar":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/0e337aed4a651b5ccdd7f52e1f6587f5.jpg","username":"墩柱模板","company":"山东博远重工集团","count":51558},{"avatar":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/2c4e385aa83347cad14a40eb05f5889b.jpg","username":"台车","company":"山东博远重工集团","count":51558}]
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
         * avatar : https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/3bd8277cefd90e37c2faddc25dbe6427.JPG
         * username : 挂篮模板
         * company : 山东博远重工集团
         * count : 51558
         */

        private String avatar;
        private String username;
        private String company;
        private int count;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
