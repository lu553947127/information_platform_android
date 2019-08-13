package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/13 16:38
 * @change
 * @chang time
 * @class describe
 */
public class RechargeRecordDetailBean {

    /**
     * order_sn : sdapp201908130544491
     * user_id : 52
     * type_id : 362
     * coin : 0.01
     * create_time : 2019-08-13 17:53:16
     * price : 0.01
     * pay_method : 支付宝支付
     */

    private String order_sn;
    private int user_id;
    private int type_id;
    private String coin;
    private String create_time;
    private String price;
    private String pay_method;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }
}
