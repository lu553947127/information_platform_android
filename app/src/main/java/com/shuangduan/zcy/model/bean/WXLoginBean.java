package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: WXLoginBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/12 16:29
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/12 16:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WXLoginBean {
    /**
     * access_token : 26_MkYykorl05Z_xM55Sy-WFiMoiAhuRcRb8UJSsMreUVmX9V9nW8m5-IWgc_mtkrvJ4jtSdLpOq9jYiCIri-o8g2fa-_Fmq8JDX7SHwyWQGSY
     * expires_in : 7200
     * refresh_token : 26_Sjh1vdbc4I3D-EwTKcGJqzJlGMRIciVZvPmeNz3pUDbuVrz2QznoRdvaBw-jZvgPaplU47_zIIrwuMD5nJgn7tnUIfy5kLa5JvrPsKmb5EY
     * openid : oJheMtwgUGQJ7ontXIxdfgvWeIgQ
     * scope : snsapi_userinfo
     * unionid : omO7Z1cAyXOFgKKZBFw-rPVj_ejQ
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
