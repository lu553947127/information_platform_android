package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/29 11:38
 * @change
 * @chang time
 * @class describe
 */
public class PayInfoBean {

    /**
     * alipay : app_id=2019072465975398&method=alipay.trade.app.pay&charset=UTF-8&format=JSON&version=1.0&sign_type=RSA2&biz_content=%7B%22out_trade_no%22%3A%22sdapp201907297737117%22%2C%22total_amount%22%3A%222.00%22%2C%22subject%22%3A%22%5Cu5de5%5Cu7a0b%5Cu4fe1%5Cu606f%5Cu8ba4%5Cu8d2d%22%2C%22timeout_express%22%3A%2230m%22%7D&timestamp=2019-07-29+11%3A59%3A27&notify_url=http%3A%2F%2Fxx.yijijian.com%2Fapi%2Fpay_return%2Fnotify%2Ftype%2Falipay&sign=fgrVXCY44b3P6uVaJFG0u0BD8%2BYa8TQj60krMsFp9FkMdLqrsV1ZqdSoabPhDV6dH0tT6idyxETjWK2ol5IoxMTDvq6jiKNpsdsUyaFMLSHEVwWOGVVnhq%2BQ%2F4oZ33u4QQTTI9IsGMWDnxg%2F%2B3%2FVqdALfiZGbbsEomWn%2F6d1IxkoQdabvjlP777w6tVkix9a1Q8G5Yn%2ByczW%2BeZwwffizVGNqfOfu0RLyHRlQKKUJpo3gnCBa2kdJTmhKODr4VRpkYmfpsnYkIVvoWUt80JpqyEbSM3DDScjh7uVDzQBQo2UZa7j%2B2erb2tlG5JDEdgQTaTprA%2BGojdslUBFE6jWJw%3D%3D
     */

    private String alipay;
    private String order_sn;
    /**
     * appid : wx2e2f0d4ccdf3e52f
     * partnerid : 1526823381
     * prepayid : wx30153503062370a064a8e49c1504692500
     * package : Sign=WXPay
     * noncestr : 5vtGhwrd0J205nF0MTtV2uxUv8fFOH2M
     * timestamp : 1564472103
     * sign_type : MD5
     * sign : A48350C7DED76B7C238802612DACB719
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String sign_type;
    private String sign;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
