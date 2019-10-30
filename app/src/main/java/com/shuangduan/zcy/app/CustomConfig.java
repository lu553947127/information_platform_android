package com.shuangduan.zcy.app;

/**
 * <pre>
 *     author : 徐玉
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
    /*供应商授权委托书*/
    public static final String SUPPLIER_AUTHORIZATION = "http://information-api.oss-cn-qingdao.aliyuncs.com/doc/供应商授权委托书.docx";
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
    public static final String DYNAMICS_ID = "dynamics_id";
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
    public static final String ORDER_ID = "order_id";
    public static final String PAY_STYLE = "pay_style";
    public static final String RECHARGE_ID = "recharge_id";
    public static final String UID = "uid";
    public static final String KEYWORD = "keyword";
    public static final String HEADLINES_ID = "headlines_id";
    public static final String EXPLAIN_ID = "explain_id";
    public static final String TRANS_RECORD_ID = "trans_record_id";
    public static final String DEMAND_ID = "demand_id";
    public static final String FIND_RELATIONSHIP_ID = "find_relationship_id";
    public static final String ORDER = "order";
    public static final String RELEASE_TYPE = "release_type";//0发布项目1发布动态
    public static final String FRIEND_DATA = "friend_data";
    public static final String PROJECT_TYPE = "project_type";//搜索区分工程信息还是招采信息
    public static final String IS_ADMIN_MANAGE = "is_admin_manage";//区分后台管理 1.周转材料 2.设备管理 3.订单管理
    public static final String IS_SHELF = "is_shelf";//1 公开物资存放地，3 内购物资存放地
    public static final String METHOD_TYPE = "method";
    //基建物资类型
    public static final String MATERIALS_TYPE = "materials_type";

    //------------------------------------身份验证判断----------------------------------------------------
    //身份认证提示类型
    public static final String AUTHENTICATION_TYPE = "authentication_id";
    //用户详情跳转编辑身份证
    public static final String ID_USER_INFO = "id_user_info";
    //工程信息查看详情
    public static final String PROJECT_INFO = "project_info";
    //工程信息动态查看详情
    public static final String PROJECT_DYNAMIC = "project_dynamic";
    //工程信息认购
    public static final String PROJECT_SUBSCRIPTION = "project_subscription";
    //优质供应商列表查看详情
    public static final String SUPPLIER_INFO = "supplier_info";
    //需求广场  发布找关系
    public static final String NEED_RELATIONSHIP = "need_relationship";
    //发布信息
    public static final String RELEASE_MESSAGE = "release_message";
    //余额 零钱提现
    public static final String BALANCE_CASH = "balance_cash";
    //余额 银行卡
    public static final String BALANCE_BANK = "balance_bank";
    //余额 提现记录
    public static final String BALANCE_CASH_RECORD = "balance_cash_record";
    //支付密码页
    public static final String PAYMENT_PASSWORD = "payment_password";
    //设置支付密码页
    public static final String SET_PAYMENT_PASSWORD = "set_payment_password";
    //-------------------------------------------------------------------------------------------------------

    //------------------------------------基建物资搜索类型----------------------------------------------------
    public static final int MATERIAL_TYPE = 1;                                             //
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

    //获取验证码 type参数
    public static final String SMS_TYPE = "sms_type";
    public static final int SMS_REGISTER = 1;//注册
    public static final int SMS_LOGIN = 2;//登录
    public static final int SMS_FORGET_PWD = 3;//忘记密码
    public static final int SMS_UPDATE_PWD = 4;//修改密码
    public static final int SMS_PWD_PAY = 5;//修改支付密码
    public static final int SMS_UPDATE_PHONE = 6;//修改手机号码
    public static final int SMS_WECHAT_LOGIN = 7;//微信登录

    public static final int PAY_STYLE_ALIPAY = 2;//支付宝支付
    public static final int PAY_STYLE_WECHAT = 1;//微信支付

    public static final int AREA_REQUEST_CODE = 1001;


    public static String DEMAND_TYPE = "DEMAND_TYPE";

    //找关系
    public static int FIND_RELATIONSHIP_TYPE = 0;
    //找物资
    public static int FIND_SUBSTANCE_TYPE = 1;
    //找买家
    public static int FIND_BUYER_TYPE = 2;

    //周转材料
    public static int FRP = 1;
    //设备物资
    public static int EQUIPMENT = 2;

    //------------------------------------基建物资 后台管理权限----------------------------------------------------
    public static final String SON_LIST = "son-list";//判断是否为集团 有无子公司
    public static final String CONSTRUCTION_LIST = "construction-list";//周转材料列表
    public static final String CONSTRUCTION_DETAIL = "construction-detail";//周转材料详情
    public static final String CONSTRUCTION_ADD = "construction-add";//周转材料添加
    public static final String CONSTRUCTION_EDIT = "construction-edit"; //周转材料修改
    public static final String CONSTRUCTION_DELETE = "construction-delete";//周转材料删除
    public static final String EQIPMENT_LIST = "equipment-list";//设备列表
    public static final String EQIPMENT_DETAIL = "equipment-detail"; //设备详情
    public static final String EQIPMENT_ADD = "equipment-add"; //设备添加
    public static final String EQIPMENT_EDIT = "equipment-edit"; //设备修改
    public static final String EQIPMENT_DELETE = "equipment-delete";//设备删除
    public static final String EQIPMENT_ORDER_LIST = "equipment-order-list";//设备订单列表
    public static final String EQIPMENT_ORDER_DETAIL = "equipment-order-detail";//设备订单详细
    public static final String EQIPMENT_ORDER_EDIT = "equipment-order-edit";//设备订单修改
    public static final String CONSTRUCTION_ORDER_LIST = "construction-order-list";//周转材料订单列表
    public static final String CONSTRUCTION_ORDER_DETAIL = "construction-order-detail"; //周转材料订单详情
    public static final String CONSTRUCTION_ORDER_EDIT = "construction-order-edit"; //周转材料订单修改

    public static String ADMIN_MANAGE_TYPE = "ADMIN_MANAGE_TYPE";
    public static int ADMIN_MANAGE_CONSTRUCTION = 1;//选择周转材料
    public static int ADMIN_MANAGE_EQIPMENT = 2;//选择设备

    public static String CONSTRUCTION_ID = "CONSTRUCTION_ID";
    //-------------------------------------------------------------------------------------------------------
}
