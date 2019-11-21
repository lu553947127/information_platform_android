package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: HeadlinesGetCategoryBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/21 9:19
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/21 9:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HeadlinesGetCategoryBean {

    public HeadlinesGetCategoryBean(int id, String catname) {
        this.id = id;
        this.catname = catname;
    }

    /**
     * id : 1
     * catname : 基建头条
     * icon : https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/87858b0b610372642ea08c91c272e0a1.jpg
     */

    private int id;
    private String catname;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
