package com.shuangduan.zcy.app;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/31
 *     desc   : 静态参数集合
 *     version: 1.0
 * </pre>
 */

public class CustomConfig {

    /*个人信息更新类型：手机，邮箱*/
    public static final String UPDATE_TYPE = "update_type";
    /*搜索类型：公司，职位*/
    public static final String SEARCH_TYPE = "search_type";

    /*基建搜索类型：商品名称，规格，供应商*/
    public static final String SEARCH_MATERIAL_TYPE = "search_material_type";
    /*上传类型：身份证，名片*/
    public static final String UPLOAD_TYPE = "upload_type";
    /*验证码*/
    public static final String VERIFICATION_CODE = "verification_code";

    /**
     * 传递预览图片的url
     */
    public static final String PHOTO_VIEW_URL_LIST = "photo_view_url_list";

    public static final String updateTypePhone = "phone";
    public static final String updateTypeEmail = "email";
    public static final String searchTypeCompany = "company";
    public static final String searchTypeOffice = "office";
    public static final String uploadTypeIdCard = "id_card";
    public static final String uploadTypeBusinessCard = "business_card";
    public static final String PROJECT_ID = "project_id";
    public static final String PROJECT_NAME = "project_name";
    public static final String PROVINCE_NAME = "province_name";
    public static final String CITY_NAME = "city_name";
    public static final String PROVINCE_ID = "province_id";
    public static final String CITY_ID = "city_id";
    public static final String PROJECT_ADDRESS = "project_address";
    public static final String LOCATION = "location";
    public static final String RECRUIT_ID = "recruit_id";
    public static final String WITHDRAW_RECORD_ID = "withdraw_record_id";
    public static final String MATERIAL_ID = "material_id";
    public static final String BANKCARD_ID = "bankcard_id";
    public static final String BANKCARD_NAME = "bankcard_name";
    public static final String PRODUCTION = "production";
    public static final String SUPPLIER_ID = "supplier_id";
    public static final String RECHARGE_AMOUNT = "recharge_amount";
    public static final String ORDER_SN = "order_sn";
    public static final String PAY_STYLE = "pay_style";
    public static final String RECHARGE_ID = "recharge_id";
    public static final String UID = "uid";
    public static final String KEYWORD = "keyword";
    public static final String HEADLINES_ID = "headlines_id";
    public static final String EXPLAIN_ID = "explain_id";
    public static final String TRANS_RECORD_ID = "trans_record_id";
    public static final String DEMAND_ID = "demand_id";
    public static final String FIND_RELATIONSHIP_ID = "find_relationship_id";
    public static final String IS_MY = "is_my";
    public static final String ORDER = "order";
    public static final String RELEASE_TYPE = "release_type";//0发布项目1发布动态
    public static final String FRIEND_DATA = "friend_data";

    public static final String PROJECT_TYPE = "project_type";//搜索区分工程信息还是招采信息


    //------------------------------------基建物资搜索类型----------------------------------------------------
    public static final int MATERIAL_TYPE = 1;                                             //     //
    public static final int SUPPLIER_TYPE = 2;                                               //
    //-------------------------------------------------------------------------------------------------------


    public static final String MORE = "更多";

    public static final String PEOPLE_DEGREE = "people_degree";
    public static final int FIRST_DEGREE = 1;//1°人脉收益
    public static final int SECOND_DEGREE = 2;//2°人脉收益
    public static final int THREE_DEGREE = 3;//3°人脉收益
    public static final int FOUR_DEGREE = 4;//4°人脉收益
    public static final int FIVE_DEGREE = 5;//5°人脉收益
    public static final int SIX_DEGREE = 6;//6°人脉收益
    public static final int SEVEN_DEGREE = 7;//1~6°人脉收益

    public static final String SMS_TYPE = "sms_type";
    public static final int SMS_REGISTER = 1;//注册
    public static final int SMS_LOGIN = 2;//登录
    public static final int SMS_FORGET_PWD = 3;//忘记密码
    public static final int SMS_UPDATE_PWD = 4;//修改密码
    public static final int SMS_PWD_PAY = 5;//修改支付密码
    public static final int SMS_UPDATE_PHONE = 6;//修改手机号码

    public static final int PAY_STYLE_ALIPAY = 2;//支付宝支付
    public static final int PAY_STYLE_WECHAT = 1;//微信支付

    public static final int AREA_REQUEST_CODE = 1001;

}
