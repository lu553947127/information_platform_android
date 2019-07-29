package com.shuangduan.zcy.model.bean;

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

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }
}
