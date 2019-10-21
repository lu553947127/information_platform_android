package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/16 10:07
 * @change
 * @chang time
 * @class describe
 */
public class TransRecordDetailBean {

    /**
     * id : 454
     * type_id : 68
     * user_id : 33
     * order_sn : sdapp201908163372831
     * remark : 查看招采信息：中铁十九局集团有限公司G3011敦当高速公路DD1合同段施工总承包项目柴油招标采购方案招标公告，消费1.00金币
     * price : 1.00
     * type : 2
     * flow_type : 7
     * create_time : 2019-08-16 11:57:49
     * vein_user_id : 0
     * type_arr : 招采信息
     * flow_types : 招采信息支出
     * title : 中铁十九局集团有限公司G3011敦当高速公路DD1合同段施工总承包项目柴油招标采购方案招标公告
     */

    private int id;
    private int type_id;
    private int user_id;
    private String order_sn;
    private String remark;
    private String price;
    private int type;
    private int flow_type;
    private String create_time;
    private int vein_user_id;
    private String type_arr;
    private String flow_types;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
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

    public int getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(int flow_type) {
        this.flow_type = flow_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getVein_user_id() {
        return vein_user_id;
    }

    public void setVein_user_id(int vein_user_id) {
        this.vein_user_id = vein_user_id;
    }

    public String getType_arr() {
        return type_arr;
    }

    public void setType_arr(String type_arr) {
        this.type_arr = type_arr;
    }

    public String getFlow_types() {
        return flow_types;
    }

    public void setFlow_types(String flow_types) {
        this.flow_types = flow_types;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
