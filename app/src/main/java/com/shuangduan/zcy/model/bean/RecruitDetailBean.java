package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/6 10:12
 * @change
 * @chang time
 * @class describe
 */
public class RecruitDetailBean {

    /**
     * amount : 68
     * title : 中铁十九局集团有限公司G3011敦当高速公路DD1合同段施工总承包项目柴油招标采购方案招标公告
     * content : "
     * start_time : null
     * price : 0.01
     * collection : 0
     * is_pay : 0
     */

    private int id;
    private String title;
    private String content;
    private String start_time;
    private String price;
    private int collection;
    private int is_pay;
    private String sourceName;

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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
