package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: HelpBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/20 14:48
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/20 14:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HelpBean {
    /**
     * page : 1
     * count : 5
     * list : [{"id":15,"title":"普通用户可以注册成为供应商有什么用处？","content":"<p>用户成为供应商后，可以在pc端使用数据管理平台对自己的物资以及签订的合同进行管理，而且可以将自己的闲置物资放在平台上售卖或租赁。<\/p>"},{"id":14,"title":"什么是紫金币？","content":"<p>紫金币是在易基建平台流通的虚拟货币，1人民币=1紫金币。余额大于10元时可以直接提现(不限次数)。<\/p>"},{"id":13,"title":"发布工程信息和动态信息有什么好处？","content":"<p>用户发布工程信息(或动态)，一旦其他用户付费查看，平台将从这笔费用中，拿出30%回馈给信息的发布者<\/p>"},{"id":12,"title":"如果发布找关系没有找到的情况下佣金怎么返还？","content":"<p>在找关系没有找到的情况下，在有效期到期后佣金原路返回。<\/p>"},{"id":11,"title":"普通用户可以注册成为供应商有什么用处？","content":"<p>用户成为供应商后，可以在pc端使用数据管理平台对自己的物资以及签订的合同进行管理，而且可以将自己的闲置物资放在平台上售卖或租赁。<\/p>"}]
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
         * id : 15
         * title : 普通用户可以注册成为供应商有什么用处？
         * content : <p>用户成为供应商后，可以在pc端使用数据管理平台对自己的物资以及签订的合同进行管理，而且可以将自己的闲置物资放在平台上售卖或租赁。</p>
         */

        private int id;
        private String title;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
