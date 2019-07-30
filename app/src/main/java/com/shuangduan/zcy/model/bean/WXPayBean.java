package com.shuangduan.zcy.model.bean;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class WXPayBean {

    /**
     * payId : 90
     * payInfo : {"return_code":"SUCCESS","return_msg":"OK","appid":"wxc6e222aaea0a82fa","mch_id":"1507290361","nonce_str":"Kf45H8O46ewmGnHg","sign":"16EF9681825E7FF88EBFE6A95D32F6D6","result_code":"SUCCESS","prepay_id":"wx041545030055951fa24545a82383795801","trade_type":"APP","timestamp":1530690307,"newSign":"FE59ADB2D5FCF4734D2DDC1862AE70A7","newNonceStr":"CfZL2KfjFj3g9kR2vQUmub8A6gydT9"}
     */

    private int needPay;
    private String payId;
    private PayInfoBean payInfo;

    public int getNeedPay() {
        return needPay;
    }

    public void setNeedPay(int needPay) {
        this.needPay = needPay;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public PayInfoBean getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfoBean payInfo) {
        this.payInfo = payInfo;
    }

    public static class PayInfoBean {
        /**
         * return_code : SUCCESS
         * return_msg : OK
         * appid : wxc6e222aaea0a82fa
         * mch_id : 1507290361
         * nonce_str : Kf45H8O46ewmGnHg
         * sign : 16EF9681825E7FF88EBFE6A95D32F6D6
         * result_code : SUCCESS
         * prepay_id : wx041545030055951fa24545a82383795801
         * trade_type : APP
         * timestamp : 1530690307
         * newSign : FE59ADB2D5FCF4734D2DDC1862AE70A7
         * newNonceStr : CfZL2KfjFj3g9kR2vQUmub8A6gydT9
         */

        private String return_code;
        private String return_msg;
        private String appid;
        private String mch_id;
        private String nonce_str;
        private String sign;
        private String result_code;
        private String prepay_id;
        private String trade_type;
        private int timestamp;
        private String newSign;
        private String newNonceStr;

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getNewSign() {
            return newSign;
        }

        public void setNewSign(String newSign) {
            this.newSign = newSign;
        }

        public String getNewNonceStr() {
            return newNonceStr;
        }

        public void setNewNonceStr(String newNonceStr) {
            this.newNonceStr = newNonceStr;
        }
    }
}
