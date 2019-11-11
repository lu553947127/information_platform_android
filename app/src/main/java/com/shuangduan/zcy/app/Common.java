package com.shuangduan.zcy.app;

public class Common {

    //搜索 更多群聊列表
    public static final String SEARCH_GROUP="/api/Friend/searchGroup";
    //会话列表人员头像名称显示
    public static final String WECHAT_USER_INFO="/api/Wechat/userInfo";
    //会话列表群组头像名称显示
    public static final String WECHAT_GROUP_INFO="/api/wechat/groupInfo";
    //群聊详情
    public static final String WECHAT_GROUP="/api/Wechat/groupList";

    //群聊退出
    public static final String WECHAT_QUIT_GROUP="/api/Wechat/quitGroup";
    //群聊加入
    public static final String WECHAT_JOIN_GROUP="/api/wechat/joinGroup";
    //查询是否加群
    public static final String WECHAT_MEMBERS_STATUS="/api/Wechat/membersStatus";

    //通讯录 群组列表
    public static final String WECHAT_MY_GROUP="/api/wechat/myGroup";
    //通讯录 好友申请数量
    public static final String FRIEND_APPLY_COUNT="/api/Friend/applyCount";
    //通讯录 新的好友列表
    public static final String FRIEND_APPLY_LIST="/api/Friend/applyList";


    //注册 隐私协议
    public static final String AGREEMENT_PRIVACY="/home/Agreement/privacy";
    //注册 注册协议
    public static final String AGREEMENT_REGISTER="/home/Agreement/register";
    //工程信息 认购协议
    public static final String AGREEMENT_WARRANT="/home/Agreement/warrant";
    //微信登录获取oppid和unionid
    public static final String WECHAT_LOGIN_="https://api.weixin.qq.com/sns/oauth2/access_token";
}
