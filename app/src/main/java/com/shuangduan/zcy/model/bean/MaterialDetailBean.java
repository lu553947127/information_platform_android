package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/7 14:58
 * @change
 * @chang time
 * @class describe
 */
public class MaterialDetailBean {

    /**
     * amount : 1
     * name : 挂篮物资
     * price : 7000.00
     * stock : 269
     * sell_stock : null
     * agent_id : 3
     * tonne : 100
     * intro : 挂篮是悬臂施工中的主要设备，按结构形式可分为桁架式、斜拉式、型钢式及混合式4种。根据混凝土悬臂施工工艺要求及设计图纸对挂篮的要求，综合比较各种形式挂篮特点、重量、采用钢材类型、施工工艺等；挂篮设计原则：自重轻、结构简单、坚固稳定、前移和装拆方便、具有较强的可重复利用性，受力后变形小等特点，并且挂篮下空间充足，可提供较大施工作业面，利于钢筋模板施工操作。
     * amount : 26900
     * sell_amount : 0
     * agent_name : 湖北港桥模板有限公司
     * order_count : 4
     */

    private int id;
    private String name;
    private String price;
    private int stock;
    private int sell_stock;
    private int agent_id;
    private int tonne;
    private String intro;
    private int amount;
    private int sell_amount;
    private String agent_name;
    private int order_count;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSell_stock() {
        return sell_stock;
    }

    public void setSell_stock(int sell_stock) {
        this.sell_stock = sell_stock;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public int getTonne() {
        return tonne;
    }

    public void setTonne(int tonne) {
        this.tonne = tonne;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSell_amount() {
        return sell_amount;
    }

    public void setSell_amount(int sell_amount) {
        this.sell_amount = sell_amount;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }
}
