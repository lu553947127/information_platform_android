package com.shuangduan.zcy.app;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CustomConfig {

    /*个人信息更新类型：手机，邮箱*/
    public static final String UPDATE_TYPE = "update_type";
    /*搜索类型：公司，职位*/
    public static final String SEARCH_TYPE = "search_type";
    /*上传类型：身份证，名片*/
    public static final String UPLOAD_TYPE = "upload_type";
    /*验证码*/
    public static final String VERIFICATION_CODE = "verification_code";

    /**
     *  传递预览图片的url
     */
    public static final String PHOTO_VIEW_URL_LIST = "photo_view_url_list";

    public static final String updateTypePhone = "phone";
    public static final String updateTypeEmail = "email";
    public static final String searchTypeCompany = "company";
    public static final String searchTypeOffice = "office";
    public static final String uploadTypeIdCard = "id_card";
    public static final String uploadTypeBusinessCard = "business_card";
    public static final String PROJECT_ID = "project_id";

    public static final String SMS_TYPE = "sms_type";
    public static final int SMS_REGISTER = 1;//注册
    public static final int SMS_LOGIN = 2;//登录
    public static final int SMS_FORGET_PWD = 3;//忘记密码
    public static final int SMS_UPDATE_PHONE = 4;//修改手机号码

    public static final int AREA_REQUEST_CODE = 1001;

}
