package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: WXUserInfoBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/14 9:33
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/14 9:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WXUserInfoBean {
    /**
     * openid : oJheMtwl-eNpjqx1SKszVr6KFamY
     * nickname : A.大美👑网卡wifi，日円换米
     * sex : 0
     * language : zh_CN
     * city :
     * province :
     * country :
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIDR2qnJ8HlllYQ3jgZa67njmSibsUKSwP0UJaKUVkbNJ0RpzSXHic0EdE0pGQmZ7JcVhdUKiaPY7PdQ/132
     * privilege : []
     * unionid : omO7Z1arlZAZPmZBD0PqF6dWLYyE
     */

    private int wechat_status;//1已绑定 0未绑定
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public int getWechat_status() {
        return wechat_status;
    }

    public void setWechat_status(int wechat_status) {
        this.wechat_status = wechat_status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
