package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/12 10:19
 * @change
 * @chang time
 * @class describe
 */
public class SupplierDetailBean {
    /**
     * id : 3
     * user_id : 0
     * name : 胡发志
     * headimg : http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/a110e0bcf7095773de47aab63a15588a.png
     * tel : 071****50333
     * company : 湖北港桥模板有限公司
     * serve_address : 北京市、东城区
     * product : 钢模板、挂篮、T梁
     * detail_price : 10.00
     * images_json : [{"source":"http://information-api.oss-cn-qingdao.aliyuncs.com/b22ca6cef501b676c8ee487d505c9137.jpeg","thumbnail":"http://information-api.oss-cn-qingdao.aliyuncs.com/b22ca6cef501b676c8ee487d505c9137.jpeg?x-oss-process=image/resize,m_lfit,h_100,w_100"},{"source":"http://information-api.oss-cn-qingdao.aliyuncs.com/6e3f368c961dd2caac7458004cb97ce3.jpeg","thumbnail":"http://information-api.oss-cn-qingdao.aliyuncs.com/6e3f368c961dd2caac7458004cb97ce3.jpeg?x-oss-process=image/resize,m_lfit,h_100,w_100"},{"source":"http://information-api.oss-cn-qingdao.aliyuncs.com/d49d995274f709854fb56a971b34e245.jpeg","thumbnail":"http://information-api.oss-cn-qingdao.aliyuncs.com/d49d995274f709854fb56a971b34e245.jpeg?x-oss-process=image/resize,m_lfit,h_100,w_100"}]
     * scale : 1
     * company_website :
     * authorization : null
     * address : null
     * is_pay : 0
     */

    private int id;
    private int user_id;
    private String name;
    private String headimg;
    private String tel;
    private String company;
    private String serve_address;
    private String product;
    private String detail_price;
    private int scale;
    private String company_website;
    private String authorization;
    private String address;
    private int is_pay;
    private List<ImagesJsonBean> images_json;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getServe_address() {
        return serve_address;
    }

    public void setServe_address(String serve_address) {
        this.serve_address = serve_address;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDetail_price() {
        return detail_price;
    }

    public void setDetail_price(String detail_price) {
        this.detail_price = detail_price;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getCompany_website() {
        return company_website;
    }

    public void setCompany_website(String company_website) {
        this.company_website = company_website;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public List<ImagesJsonBean> getImages_json() {
        return images_json;
    }

    public void setImages_json(List<ImagesJsonBean> images_json) {
        this.images_json = images_json;
    }

    public static class ImagesJsonBean {
        /**
         * source : http://information-api.oss-cn-qingdao.aliyuncs.com/b22ca6cef501b676c8ee487d505c9137.jpeg
         * thumbnail : http://information-api.oss-cn-qingdao.aliyuncs.com/b22ca6cef501b676c8ee487d505c9137.jpeg?x-oss-process=image/resize,m_lfit,h_100,w_100
         */

        private String source;
        private String thumbnail;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

}
