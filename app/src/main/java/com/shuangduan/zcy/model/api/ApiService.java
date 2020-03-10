package com.shuangduan.zcy.model.api;

import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.adminManage.bean.DeviceBean;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailBean;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailEditBean;
import com.shuangduan.zcy.adminManage.bean.OrderDetailsBean;
import com.shuangduan.zcy.adminManage.bean.OrderSearchBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailEditBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.model.bean.*;

import java.math.BigInteger;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.model.api
 * @class describe  后台接口集合
 * @time 2019/7/3 17:11
 * @change
 * @chang time
 * @class describe
 */
public interface ApiService {

    //获取验证码
    @FormUrlEncoded
    @POST("api/Passport/smsCode")
    Flowable<BaseResponse> smsCode(
            @Field("tel") String tel,
            @Field("type") int type
    );

    //验证码登录
    @FormUrlEncoded
    @POST("api/Passport/codeLogin")
    Flowable<BaseResponse<LoginBean>> codeLogin(
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("client_id") String client_id
    );

    //账号密码登录
    @FormUrlEncoded
    @POST("api/Passport/accountLogin")
    Flowable<BaseResponse<LoginBean>> accountLogin(
            @Field("tel") String tel,
            @Field("password") String password,
            @Field("client_id") String client_id
    );

    //注册
    @FormUrlEncoded
    @POST("api/Passport/register")
    Flowable<BaseResponse<RegisterBean>> register(
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("password") String password,
            @Field("invite_tel") String invite_tel
    );

    //重置密码
    @FormUrlEncoded
    @POST("api/Passport/setPassword")
    Flowable<BaseResponse<ReSetPwdBean>> setPassword(
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("password") String password
    );

    //修改登录密码
    @FormUrlEncoded
    @POST("api/Userinfo/setPassword")
    Flowable<BaseResponse> resetPassword(
            @Field("user_id") int user_id,
            @Field("old_password") String old_password,
            @Field("password") String password
    );

    //退出登录
    @FormUrlEncoded
    @POST("api/Userinfo/outLogin")
    Flowable<BaseResponse> outLogin(
            @Field("user_id") int user_id
    );

    //搜索公司名称
    @FormUrlEncoded
    @POST("api/Userinfo/searchCompany")
    Flowable<BaseListResponse<String>> searchCompany(
            @Field("user_id") int user_id,
            @Field("title") String title
    );

    //录入信息
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> setInfo(
            @Query("user_id") int user_id,
            @Query("username") String username,
            @Query("sex") int sex,
            @Query("company") String company,
            @Query("position") String position,
            @Body BusinessAreaBean business_city,
            @Query("experience") int experience,
            @Query("managing_products") String managing_products
    );

    //更新用户名
    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateUserName(
            @Field("user_id") int user_id,
            @Field("username") String username
    );

    //更新性别
    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateSex(
            @Field("user_id") int user_id,
            @Field("sex") int sex
    );

    //更新公司
    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateCompany(
            @Field("user_id") int user_id,
            @Field("company") String company
    );

    //更新职位
    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updatePosition(
            @Field("user_id") int user_id,
            @Field("position") String position
    );

    //更新城市
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateBusinessCity(
            @Query("user_id") int user_id,
            @Body BusinessAreaBean business_city
    );

    //获取头像
    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateAvatar(
            @Field("user_id") int user_id,
            @Field("avatar") String avatar
    );

    //获取经验
    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateExperience(
            @Field("user_id") int user_id,
            @Field("experience") int experience
    );

    //获取产品
    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateProduct(
            @Field("user_id") int user_id,
            @Field("managing_products") String managing_products
    );

    //旧手机验证
    @FormUrlEncoded
    @POST("api/Userinfo/telUpdate")
    Flowable<BaseResponse> telUpdate(
            @Field("user_id") int user_id,
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("old_tel") String old_tel,
            @Field("old_code") String old_code
    );

    //旧手机验证（邮箱）
    @FormUrlEncoded
    @POST("api/Userinfo/telCheck")
    Flowable<BaseResponse> telCheck(
            @Field("user_id") int user_id,
            @Field("code") String code
    );

    //用户详情
    @FormUrlEncoded
    @POST("api/Userinfo/userInfo")
    Flowable<BaseResponse<UserInfoBean>> userInfo(
            @Field("user_id") int user_id
    );

    //基本信息
    @FormUrlEncoded
    @POST("api/Userinfo/information")
    Flowable<BaseResponse<UserInfoBean>> information(
            @Field("user_id") int user_id,
            @Field("uid") int uid
    );

    //帮助列表
    @FormUrlEncoded
    @POST("api/Userinfo/help")
    Flowable<BaseResponse<HelpBean>> help(
            @Field("user_id") int user_id
    );

    //上传附件
    @Multipart
    @POST("api/Upload/uploadImage")
    Flowable<BaseResponse<UploadBean>> upload(
            @Query("user_id") int user_id,
            @Part MultipartBody.Part file
    );

    //获取省
    @FormUrlEncoded
    @POST("api/District/getProvince")
    Flowable<BaseListResponse<ProvinceBean>> getProvince(
            @Field("user_id") int user_id
    );

    //获取市
    @FormUrlEncoded
    @POST("api/District/getCity")
    Flowable<BaseListResponse<CityBean>> getCity(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //工程地图列表
    @FormUrlEncoded
    @POST("api/Project/mapList")
    Flowable<BaseListResponse<MapBean>> mapList(
            @Field("user_id") int user_id,
            @Field("lng") double lng,
            @Field("lat") double lat
    );

    //工程信息列表
    @POST("api/Project/dataList")
    Flowable<BaseResponse<ProjectInfoBean>> projectList(
            @Query("user_id") int user_id,
            @Body ProjectFilterBean city,
            @Query("stime") String stime,
            @Query("etime") String etime,
            @Query("warrant_status") String warrant_status,
            @Query("page") int page
    );

    //工程类型
    @FormUrlEncoded
    @POST("api/Project/getTypes")
    Flowable<BaseListResponse<TypeBean>> projectTypes(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //工程阶段
    @FormUrlEncoded
    @POST("api/Project/getPhases")
    Flowable<BaseListResponse<StageBean>> projectStage(
            @Field("user_id") int user_id
    );

    //工程阶段
    @FormUrlEncoded
    @POST("api/Project/keywordHot")
    Flowable<BaseListResponse<String>> keywordHot(
            @Field("user_id") int user_id
    );

    //搜索工程信息
    @FormUrlEncoded
    @POST("api/Project/keywordList")
    Flowable<BaseResponse<ProjectInfoBean>> searchProject(
            @Field("user_id") int user_id,
            @Field("keyword") String keyword,
            @Field("page") int page
    );

    //搜索招采信息
    @FormUrlEncoded
    @POST("api/Tenderer/keywordList")
    Flowable<BaseResponse<RecruitBean>> searchRecruit(
            @Field("user_id") int user_id,
            @Field("keyword") String keyword,
            @Field("page") int page
    );

    //工程信息详情
    @FormUrlEncoded
    @POST("api/Project/getDetail")
    Flowable<BaseResponse<ProjectDetailBean>> getDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //工程动态列表
    @FormUrlEncoded
    @POST("api/Project/getTrack")
    Flowable<BaseResponse<TrackBean>> getTrack(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("page") int page,
            @Field("type") int type
    );

    //工程信息已查看动态列表
    @FormUrlEncoded
    @POST("api/Project/viewTrack")
    Flowable<BaseResponse<TrackBean>> getViewTrack(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("page") int page
    );

    //工程信息消费列表
    @FormUrlEncoded
    @POST("api/Project/consumeList")
    Flowable<BaseResponse<ConsumeBean>> consumeList(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("page") int page
    );

    //工程信息收藏
    @FormUrlEncoded
    @POST("api/Project/setCollection")
    Flowable<BaseResponse> collect(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //工程信息取消收藏
    @FormUrlEncoded
    @POST("api/Project/cancelCollection")
    Flowable<BaseResponse> cancelCollection(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //工程信息纠错
    @FormUrlEncoded
    @POST("api/Project/correction")
    Flowable<BaseResponse> correction(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("content") String content
    );

    //查询是否可以进入讨论组
    @FormUrlEncoded
    @POST("api/Wechat/membersStatus")
    Flowable<BaseResponse<ProjectMembersStatusBean>> membersStatus(
            @Field("user_id") int user_id,
            @Field("project_id") int project_id
    );

    //加入群聊
    @FormUrlEncoded
    @POST("api/wechat/joinGroup")
    Flowable<BaseResponse> joinGroup(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //退出群聊
    @FormUrlEncoded
    @POST("api/Wechat/quitGroup")
    Flowable<BaseResponse> quitGroup(
            @Field("user_id") int user_id,
            @Field("group_id") String group_id
    );

    //群聊详情
    @FormUrlEncoded
    @POST("api/Wechat/groupList")
    Flowable<BaseResponse<IMGroupInfoBean>> groupList(
            @Field("user_id") int user_id,
            @Field("group_id") String group_id,
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    //会话列表人员头像名称显示
    @FormUrlEncoded
    @POST("api/Wechat/userInfo")
    Flowable<BaseResponse<IMWechatUserInfoBean.DataBean>> userInfo(
            @Field("user_id") int user_id,
            @Field("id") String id
    );

    //会话列表群组头像名称显示
    @FormUrlEncoded
    @POST("api/wechat/groupInfo")
    Flowable<BaseResponse<IMWechatGroupInfoBean>> groupInfo(
            @Field("user_id") int user_id,
            @Field("group_id") String group_id
    );

    //我的工程列表
    @FormUrlEncoded
    @POST("api/Userinfo/myProject")
    Flowable<BaseResponse<ProjectMineBean>> myProject(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //我的工程动态列表
    @FormUrlEncoded
    @POST("api/Userinfo/myProjectTrack")
    Flowable<BaseResponse<LocusMineBean>> myProjectTrack(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //获取支付宝和微信的订单信息
    @FormUrlEncoded
    @POST("/api/Userinfo/recharge")
    Flowable<BaseResponse<PayInfoBean>> recharge(
            @Field("user_id") int user_id,
            @Field("amount") String amount,
            @Field("type") int type
    );

    //发布工程信息联系人类型选择
    @FormUrlEncoded
    @POST("api/Project/getPhoneType")
    Flowable<BaseListResponse<ContactTypeBean>> getContactType(
            @Field("user_id") int user_id
    );

    //发布工程信息
    @POST("api/Project/addProject")
    Flowable<BaseResponse> addProject(
            @Query("user_id") int user_id,
            @Query("title") String title,
            @Query("company") String company,
            @Query("province") int province,
            @Query("city") int city,
            @Query("address") String address,
            @Query("phases") int phases,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time,
            @Query("acreage") String acreage,
            @Query("valuation") String valuation,
            @Query("intro") String intro,
            @Query("materials") String materials,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Body ContactListBean contact
    );

    //发布工程动态信息
    @POST("api/Project/addTrack")
    Flowable<BaseResponse> addTrack(
            @Query("user_id") int user_id,
            @Query("id") int id,
            @Query("remarks") String remarks,
            @Query("name") String name,
            @Query("tel") String tel,
            @Query("update_time") String update_time,
            @Body AddTrackBean image_id
    );

    //工程信息搜索
    @FormUrlEncoded
    @POST("api/Project/keywordTitle")
    Flowable<BaseResponse<ProjectSearchBean>> keywordTitle(
            @Field("user_id") int user_id,
            @Field("keyword") String keyword,
            @Field("page") int page
    );

    //订阅消息列表
    @FormUrlEncoded
    @POST("api/Subscription/subscribe")
    Flowable<BaseResponse<SubscribeBean>> subscribe(
            @Field("user_id") int user_id,
            @Field("type") int type,
            @Field("page") int page
    );

    //订单提醒列表
    @FormUrlEncoded
    @POST("api/Subscription/order")
    Flowable<BaseResponse<SubscribeOrderBean>> subscribeOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //订阅消息开关状态返回
    @FormUrlEncoded
    @POST("api/Userinfo/msgPush")
    Flowable<BaseResponse<MessagePushBean>> msgPush(
            @Field("user_id") int user_id,
            @Field("type") int type
    );

    //订阅消息开关状态修改
    @FormUrlEncoded
    @POST("api/Userinfo/msgPushStatus")
    Flowable<BaseResponse> msgPushStatus(
            @Field("user_id") int user_id,
            @Field("type") int type,
            @Field("status") int status
    );

    //工程推送阶段选项
    @FormUrlEncoded
    @POST("api/Userinfo/myPhases")
    Flowable<BaseListResponse<MyPhasesBean>> myPhases(
            @Field("user_id") int user_id
    );

    //工程推送阶段提交
    @POST("api/Userinfo/setPhases")
    Flowable<BaseResponse> setPhases(
            @Query("user_id") int user_id,
            @Body SubBean id
    );

    //浏览历史-工程信息
    @FormUrlEncoded
    @POST("api/Userinfo/historyProject")
    Flowable<BaseListResponse<ReadHistoryBean>> historyProject(
            @Field("user_id") int user_id
    );

    //浏览历史-招采信息
    @FormUrlEncoded
    @POST("api/Userinfo/historyTenderer")
    Flowable<BaseListResponse<ReadHistoryBean>> historyTenderer(
            @Field("user_id") int user_id
    );

    //意见反馈
    @FormUrlEncoded
    @POST("api/Userinfo/feedback")
    Flowable<BaseResponse> feedback(
            @Field("user_id") int user_id,
            @Field("content") String content
    );

    //我的收藏-工程信息
    @FormUrlEncoded
    @POST("api/Userinfo/projectCollection")
    Flowable<BaseResponse<ProjectCollectBean>> projectCollection(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //我的收藏-招采信息
    @FormUrlEncoded
    @POST("api/Userinfo/tendererCollection")
    Flowable<BaseResponse<RecruitBean>> recruitCollection(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //实名认证检测
    @FormUrlEncoded
    @POST("api/Userinfo/authentication")
    Flowable<BaseResponse<AuthenBean>> authentication(
            @Field("user_id") int user_id
    );

    //身份认证
    @FormUrlEncoded
    @POST("api/Userinfo/idcard")
    Flowable<BaseResponse> idcard(
            @Field("user_id") int user_id,
            @Field("image_front") String image_front,
            @Field("image_reverse_site") String image_reverse_site
    );

    //提现信息
    @FormUrlEncoded
    @POST("api/Userinfo/loading")
    Flowable<BaseResponse<WithdrawBean>> withdrawMsg(
            @Field("user_id") int user_id
    );

    //提现记录
    @FormUrlEncoded
    @POST("api/Userinfo/record")
    Flowable<BaseResponse<WithdrawRecordBean>> withdrawRecord(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //提现详情
    @FormUrlEncoded
    @POST("api/Userinfo/detailsRecord")
    Flowable<BaseResponse<WithdrawRecordBean.ListBean>> withdrawRecordDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //提现
    @FormUrlEncoded
    @POST("api/Userinfo/withdrawDeposit")
    Flowable<BaseResponse> withdraw(
            @Field("user_id") int user_id,
            @Field("bankcard_id") int bankcard_id,
            @Field("price") String price
    );

    //银行卡列表
    @FormUrlEncoded
    @POST("api/Bankcard/bankcardList")
    Flowable<BaseListResponse<BankCardBean>> bankcardList(
            @Field("user_id") int user_id
    );

    //银行卡识别
    @FormUrlEncoded
    @POST("api/Bankcard/ibcard")
    Flowable<BaseResponse<BankCardDisBean>> bankcardDis(
            @Field("user_id") int user_id,
            @Field("image") String image
    );

    //银行卡添加
    @FormUrlEncoded
    @POST("api/Bankcard/bankcardAdd")
    Flowable<BaseResponse> bankcardAdd(
            @Field("user_id") int user_id,
            @Field("bankcard_num") String bankcard_num,
            @Field("opening_bank") String opening_bank
    );

    //银行卡解绑
    @FormUrlEncoded
    @POST("api/Bankcard/bankCardDel")
    Flowable<BaseResponse> unbindBankcard(
            @Field("user_id") int user_id,
            @Field("bankcard_id") int bankcard_id
    );

    //我的收益
    @FormUrlEncoded
    @POST("api/Profit/myProceeds")
    Flowable<BaseResponse<MineIncomeBean>> myProceeds(
            @Field("user_id") int user_id
    );

    //发布信息收益
    @FormUrlEncoded
    @POST("api/Profit/publish")
    Flowable<BaseResponse<IncomeReleaseBean>> incomeRelease(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //亲密好友列表
    @FormUrlEncoded
    @POST("api/Profit/Floor")
    Flowable<BaseResponse<IncomePeopleBean>> incomePeople(
            @Field("user_id") int user_id,
            @Field("page") int page,
            @Field("type") int type
    );

    //信息认购收益
    @FormUrlEncoded
    @POST("api/Profit/subscribe")
    Flowable<BaseResponse<IncomeMsgBean>> incomeMsg(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //发布动态收益
    @FormUrlEncoded
    @POST("api/Profit/track")
    Flowable<BaseResponse<IncomeLocusBean>> incomeLocus(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //招采信息列表
    @FormUrlEncoded
    @POST("api/Tenderer/dataList")
    Flowable<BaseResponse<RecruitBean>> recruitList(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //招采信息详情
    @FormUrlEncoded
    @POST("api/Tenderer/getDetail")
    Flowable<BaseResponse<RecruitDetailBean>> recruitDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //招采信息收藏
    @FormUrlEncoded
    @POST("api/Tenderer/setCollection")
    Flowable<BaseResponse> recruitCollect(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //招采信息取消收藏
    @FormUrlEncoded
    @POST("api/Tenderer/cancelCollection")
    Flowable<BaseResponse> recruitCancelCollect(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //基建物质--公开周转材料列表
    @POST("api/Material/dataList")
    Flowable<BaseResponse<MaterialBean>> materialList(
            @Query("user_id") int user_id,
            @Query("type") int type,
            @Query("material_name") String materialName,
            @Query("spec") String spec,
            @Query("supplier_id") int supplierId,
            @Query("page") int page
    );

    //基建物资---公开设备物资列表
    @POST("api/Equipment/dataList")
    Flowable<BaseResponse<MaterialBean>> getEquipmentList(
            @Query("user_id") int userId,
            @Query("type") int type,
            @Query("material_name") String materialName,
            @Query("spec") String spec,
            @Query("supplier_id") int supplierId,
            @Query("page") int page
    );


    //基建物质列表--内定周转材料
    @POST("api/Material/dataList")
    Flowable<BaseResponse<MaterialBean>> materialList(
            @Query("user_id") int user_id,
            @Query("type") int type,
            @Query("material_name") String materialName,
            @Query("spec") String spec,
            @Query("supplier_id") int supplierId,
            @Query("page") int page,
            @Body AuthGroupBean auth_group
    );

    //基建物资---内定设备物资列表
    @POST("api/Equipment/dataList")
    Flowable<BaseResponse<MaterialBean>> getEquipmentList(
            @Query("user_id") int userId,
            @Query("type") int type,
            @Query("material_name") String materialName,
            @Query("spec") String spec,
            @Query("supplier_id") int supplierId,
            @Query("page") int page,
            @Body AuthGroupBean auth_group
    );

    //基建物资---材料详情
    @FormUrlEncoded
    @POST("api/Material/getDetail")
    Flowable<BaseResponse<MaterialDetailBean>> materialDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //基建物资--设备详情
    @FormUrlEncoded
    @POST("api/Equipment/getDetail")
    Flowable<BaseResponse<MaterialDetailBean>> getEquipmentDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //基建物资-一级二级类别筛选
    @FormUrlEncoded
    @POST("api/Material/getCategory")
    Flowable<BaseListResponse<MaterialCategoryBean>> getCategory(
            @Field("user_id") int user_id,
            @Field("category_id") int category_id
    );

    //优质供应商列表
    @FormUrlEncoded
    @POST("api/supplier/supplierList")
    Flowable<BaseResponse<SupplierBean>> supplierList(
            @Field("user_id") int user_id,
            @Field("title") String title,
            @Field("page") int page
    );

    //优质供应商详情
    @FormUrlEncoded
    @POST("api/supplier/supplierDetails")
    Flowable<BaseResponse<SupplierDetailBean>> supplierDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //加入供应商
    @POST("api/supplier/supplierApply")
    Flowable<BaseResponse> supplierJoin(
            @Query("user_id") int user_id,
            @Query("company") String company,
            @Query("province") int province,
            @Query("city") int city,
            @Query("address") String address,
            @Query("scale") int scale,
            @Query("company_website") String company_website,
            @Query("name") String name,
            @Query("tel") String tel,
            @Query("product") String product,
            @Query("authorization") String authorization,
            @Query("headimg") String headimg,
            @Body SupplierJoinImageBean joinImageBean
    );

    //获取供应商审核状态
    @FormUrlEncoded
    @POST("api/Supplier/supplierStatus")
    Flowable<BaseResponse<SupplierStatusBean>> supplierStatus(
            @Field("user_id") int user_id
    );

    //工程信息订单
    @FormUrlEncoded
    @POST("api/Order/projectOrder")
    Flowable<BaseResponse<OrderListBean>> projectOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //工程动态订单
    @FormUrlEncoded
    @POST("api/Order/trackOrder")
    Flowable<BaseResponse<OrderListBean>> locusOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //招采信息订单
    @FormUrlEncoded
    @POST("api/Order/tendererOrder")
    Flowable<BaseResponse<OrderListBean>> recruitOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //供应商订单
    @FormUrlEncoded
    @POST("api/Order/supplierOrder")
    Flowable<BaseResponse<OrderListBean>> supplierOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //已认购订单
    @FormUrlEncoded
    @POST("api/Order/warrantOrder")
    Flowable<BaseResponse<OrderSubBean>> subOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //充值列表
    @FormUrlEncoded
    @POST("api/Userinfo/rechargeLoading")
    Flowable<BaseResponse<RechargeShowBean>> rechargeShow(
            @Field("user_id") int user_id
    );

    //充值结果
    @FormUrlEncoded
    @POST("/api/Pay/orderStatus")
    Flowable<BaseResponse<RechargeResultBean>> rechargeResult(
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn
    );

    //充值记录列表
    @FormUrlEncoded
    @POST("api/Userinfo/rechargeRecord")
    Flowable<BaseResponse<RechargeRecordBean>> rechargeRecord(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //充值记录详情
    @FormUrlEncoded
    @POST("api/Userinfo/rechargeDetails")
    Flowable<BaseResponse<RechargeRecordDetailBean>> rechargeRecordDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //设置支付密码
    @FormUrlEncoded
    @POST("api/Userinfo/coinPassword")
    Flowable<BaseResponse> setPwdPay(
            @Field("user_id") int user_id,
            @Field("password") String password,
            @Field("tel") String tel,
            @Field("code") String code
    );

    //修改支付密码
    @FormUrlEncoded
    @POST("api/Userinfo/setCoinPassword")
    Flowable<BaseResponse> updatePwdPay(
            @Field("user_id") int user_id,
            @Field("old_password") String old_password,
            @Field("password") String password
    );

    //忘记支付密码
    @FormUrlEncoded
    @POST("api/Userinfo/forgetPassword")
    Flowable<BaseResponse> forgetPwdPay(
            @Field("user_id") int user_id,
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("password") String password
    );

    //验证支付密码
    @FormUrlEncoded
    @POST("api/Userinfo/coinPwdStatus")
    Flowable<BaseResponse<PwdPayStateBean>> pwdPayState(
            @Field("user_id") int user_id
    );

    //亲密好友详情
    @FormUrlEncoded
    @POST("api/Profit/details")
    Flowable<BaseResponse<PeopleDetailBean>> peopleDetail(
            @Field("user_id") int user_id,
            @Field("path_user_id") int path_user_id
    );

    //榜单列表
    @FormUrlEncoded
    @POST("api/Honor/dataList")
    Flowable<BaseResponse<RankListBean>> honorList(
            @Field("user_id") int user_id,
            @Field("type") int type
    );

    //邀请好友收益记录列表
    @FormUrlEncoded
    @POST("api/Profit/record")
    Flowable<BaseResponse<IncomeRecordBean>> incomeRecord(
            @Field("user_id") int user_id,
            @Field("path_user_id") int path_user_id,
            @Field("page") int page
    );

    //支付概况
    @FormUrlEncoded
    @POST("api/Project/payDetail")
    Flowable<BaseResponse<CoinPayResultBean>> payProjectContent(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("coin_password") String coin_password
    );

    //支付动态
    @FormUrlEncoded
    @POST("api/Project/payTrack")
    Flowable<BaseResponse<CoinPayResultBean>> payLocus(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("coin_password") String coin_password
    );

    //支付供应商
    @FormUrlEncoded
    @POST("api/Supplier/payDetail")
    Flowable<BaseResponse<CoinPayResultBean>> paySupplier(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("coin_password") String coin_password
    );

    //支付招采信息
    @FormUrlEncoded
    @POST("api/Tenderer/payDetail")
    Flowable<BaseResponse<CoinPayResultBean>> payRecruit(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("coin_password") String coin_password
    );

    //支付找物资
    @FormUrlEncoded
    @POST("api/Relation/payMaterial")
    Flowable<BaseResponse<CoinPayResultBean>> payFindSubstance(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("coin_password") String coin_password
    );

    //支付找买家
    @FormUrlEncoded
    @POST("api/Relation/payBuyer")
    Flowable<BaseResponse<CoinPayResultBean>> payFindBuyer(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("coin_password") String coin_password
    );

    //支付找资源佣金
    @FormUrlEncoded
    @POST("api/Relation/pay")
    Flowable<BaseResponse<CoinPayResultBean>> payRelationshipRelease(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("coin_password") String coin_password
    );

    //支付工程信息认购
    @FormUrlEncoded
    @POST("api/Project/payWarrant")
    Flowable<BaseResponse<CoinPayResultBean>> payWarrant(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("order_id") int order_id,
            @Field("coin_password") String coin_password
    );

    //首页快讯标题
    @FormUrlEncoded
    @POST("api/index/profitPaper")
    Flowable<BaseListResponse<HomePushBean>> homePush(
            @Field("user_id") int user_id
    );

    //首页轮播图列表
    @FormUrlEncoded
    @POST("api/index/banner")
    Flowable<BaseListResponse<HomeBannerBean>> homeBanner(
            @Field("user_id") int user_id
    );

    //首页基建头条+收益说明列表
    @FormUrlEncoded
    @POST("api/index/explain")
    Flowable<BaseResponse<HomeListBean>> homeList(
            @Field("user_id") int user_id
    );

    //基建物资 内定物资显示权限
    @FormUrlEncoded
    @POST("api/Manage/getSupplierClique")
    Flowable<BaseResponse<SupplierCliqueBean>> getSupplierClique(
            @Field("user_id") int user_id
    );

    //基建物资 管理权限
    @FormUrlEncoded
    @POST("api/Manage/getSupplierRole")
    Flowable<BaseResponse<SupplierRoleBean>> getSupplierRole(
            @Field("user_id") int user_id
    );

    //基建头条列表
    @FormUrlEncoded
    @POST("api/Headline/dataList")
    Flowable<BaseResponse<HeadlinesBean>> headlines(
            @Field("user_id") int user_id,
            @Field("category_id") int category_id,
            @Field("page") int page
    );

    //基建头条详情
    @FormUrlEncoded
    @POST("api/Headline/getDetail")
    Flowable<BaseResponse<HeadlinesDetailBean>> headlinesDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //基建头条分类列表
    @FormUrlEncoded
    @POST("api/Headline/getCategory")
    Flowable<BaseListResponse<HeadlinesGetCategoryBean>> headlinesGetCategory(
            @Field("user_id") int user_id
    );

    //收益说明详情
    @FormUrlEncoded
    @POST("api/index/details")
    Flowable<BaseResponse<ExplainDetailBean>> explainDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //交易记录
    @POST("api/Userinfo/cashFlow")
    Flowable<BaseResponse<TransRecordBean>> transactionRecord(
            @Query("user_id") int user_id,
            @Query("page") int page,
            @Query("type") int type,
            @Body TransactionFlowTypeBean flow_type
    );

    //交易记录详情
    @FormUrlEncoded
    @POST("/api/Userinfo/cashFlowDetails")
    Flowable<BaseResponse<TransRecordDetailBean>> transRecordDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //交易记录类型
    @FormUrlEncoded
    @POST("api/Userinfo/cashSearch")
    Flowable<BaseResponse<TransRecordFilterBean>> transRecordFilter(
            @Field("user_id") int user_id
    );

    //发布找资源
    @FormUrlEncoded
    @POST("api/Relation/add")
    Flowable<BaseResponse<DemandReleaseBean>> demandRelationshipRelease(
            @Field("user_id") int user_id,
            @Field("title") String title,
            @Field("intro") String intro,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time,
            @Field("price") String price
    );

    //发布找物资
    @FormUrlEncoded
    @POST("api/Relation/materialAdd")
    Flowable<BaseResponse<DemandReleaseBean>> demandSubstanceRelease(
            @Field("user_id") int user_id,
            @Field("material_name") String material_name,
            @Field("count") String count,
            @Field("project_name") String project_name,
            @Field("address") String address,
            @Field("acceptance_price") String acceptance_price,
            @Field("tel") String tel,
            @Field("real_name") String real_name,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time,
            @Field("unit") int demand_num,
            @Field("remark") String remark
    );

    //发布找买家
    @FormUrlEncoded
    @POST("api/Relation/buyerAdd")
    Flowable<BaseResponse<DemandReleaseBean>> demandBuyerRelease(
            @Field("user_id") int user_id,
            @Field("material_name") String material_name,
            @Field("count") String count,
            @Field("address") String address,
            @Field("acceptance_price") String acceptance_price,
            @Field("tel") String tel,
            @Field("real_name") String real_name,
            @Field("way") int way,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time,
            @Field("unit") int supply_num,
            @Field("remark") String remark
    );

    //发布找方案
    @FormUrlEncoded
    @POST("api/Relation/drawingAdd")
    Flowable<BaseResponse> bluePrintAdd(
            @Field("user_id") int userId,
            @Field("project_name") String projectName,
            @Field("project_location") String projectLocation,
            @Field("start_time") String startTime,
            @Field("end_time") String endTime,
            @Field("remark") String remark,
            @Field("personal_name") String personalName,
            @Field("tel") String tel
    );

    //发布找物流
    @FormUrlEncoded
    @POST("api/Relation/logisticsAdd")
    Flowable<BaseResponse> logisticsAdd(
            @Field("user_id") int userId,
            @Field("material_name") String materialName,
            @Field("material_count") int materialCount,
            @Field("unit") int unit,
            @Field("delivery_address") String deliveryAddress,
            @Field("receiving_address") String receivingAddress,
            @Field("receiving_time") String receivingTime,
            @Field("start_time") String startTime,
            @Field("end_time") String endTime,
            @Field("personal_name") String personalName,
            @Field("tel") String tel,
            @Field("remark") String remark
    );

    //个人中心-找方案发布列表
    @FormUrlEncoded
    @POST("api/Relation/drawingList")
    Flowable<BaseResponse<NeedBean>> drawingList(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //个人中心-找方案详细信息
    @FormUrlEncoded
    @POST("api/Relation/drawingDetail")
    Flowable<BaseResponse<NeedInfoBean>> drawingDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );


    //个人中心-找物流发布列表
    @FormUrlEncoded
    @POST("api/Relation/logisticsList")
    Flowable<BaseResponse<NeedBean>> logisticsList(
            @Field("user_id") int userId,
            @Field("page") int page
    );

    //个人中心-找物流详细信息
    @FormUrlEncoded
    @POST("api/Relation/logisticsDetail")
    Flowable<BaseResponse<NeedInfoBean>> logisticsDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //个人中心-取消找物流
    @FormUrlEncoded
    @POST("api/Relation/logisticsClose")
    Flowable<BaseResponse> logisticsClose(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //个人中心-取消找方案
    @FormUrlEncoded
    @POST("api/Relation/drawingClose")
    Flowable<BaseResponse> drawingClose(
            @Field("user_id") int userId,
            @Field("id") int id
    );


    //发布找基地
    @FormUrlEncoded
    @POST("api/Relation/baseAdd")
    Flowable<BaseResponse> baseAdd(
            @Field("user_id") int userId,
            @Field("material_name") String materialName,
            @Field("material_count") int materialCount,
            @Field("unit") int unit,
            @Field("existing_location") String existingLocation,
            @Field("need_location") String needLocation,
            @Field("storage_distance") String storageDistance,
            @Field("is_reform") int isReform,
            @Field("start_time") String startTime,
            @Field("end_time") String endTime,
            @Field("personal_name") String personal_name,
            @Field("tel") String tel,
            @Field("remark") String remark
    );

    //个人中心-找基地发布列表
    @FormUrlEncoded
    @POST("api/Relation/baseList")
    Flowable<BaseResponse<NeedBean>> baseList(
            @Field("user_id") int userId,
            @Field("page") int page
    );


    //个人中心-找基地详情
    @FormUrlEncoded
    @POST("api/Relation/baseDetail")
    Flowable<BaseResponse<NeedInfoBean>> baseDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //个人中心-取消找基地
    @FormUrlEncoded
    @POST("api/Relation/baseClose")
    Flowable<BaseResponse> baseClose(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //找资源支付订单
    @FormUrlEncoded
    @POST("api/Relation/waitPay")
    Flowable<BaseResponse<RelationshipOrderBean>> relationshipReleaseOrder(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //获取数量单位
    @FormUrlEncoded
    @POST("api/Relation/getUnit")
    Flowable<BaseListResponse<UnitBean>> getUnit(
            @Field("user_id") int user_id
    );

    //找资源列表
    @FormUrlEncoded
    @POST("api/Relation/relationList")
    Flowable<BaseResponse<DemandRelationshipBean>> demandRelationship(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //取消发布的找资源
    @FormUrlEncoded
    @POST("api/Relation/closeRelation")
    Flowable<BaseResponse> closeRelation(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //取消发布的找买家
    @FormUrlEncoded
    @POST("api/Relation/closeBuyer")
    Flowable<BaseResponse> closeBuyer(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //取消发布的找物资
    @FormUrlEncoded
    @POST("api/Relation/closeMterial")
    Flowable<BaseResponse> closeMterial(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //找资源 我接单的 列表
    @FormUrlEncoded
    @POST("api/Relation/index")
    Flowable<BaseResponse<DemandRelationshipBean>> demandRelationshipAccept(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //找资源 我发布的 列表
    @FormUrlEncoded
    @POST("api/Relation/myRelationList")
    Flowable<BaseResponse<DemandRelationshipBean>> demandRelationshipRelease(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //找物资列表
    @FormUrlEncoded
    @POST("api/Relation/materialList")
    Flowable<BaseResponse<DemandSubstanceBean>> demandSubstance(
            @Field("user_id") int user_id,
            @Field("page") int page,
            @Field("is_my") int is_my
    );

    //找买家列表
    @FormUrlEncoded
    @POST("api/Relation/buyerList")
    Flowable<BaseResponse<DemandBuyerBean>> demandBuyer(
            @Field("user_id") int user_id,
            @Field("page") int page,
            @Field("is_my") int is_my
    );

    //找资源 提供信息
    @FormUrlEncoded
    @POST("api/Relation/orderTaking")
    Flowable<BaseResponse> orderTaking(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("name") String name,
            @Field("tel") String tel,
            @Field("intro") String intro
    );

    //找资源详情
    @FormUrlEncoded
    @POST("api/Relation/details")
    Flowable<BaseResponse<RelationshipDetailBean>> relationshipDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //找物资详情
    @FormUrlEncoded
    @POST("api/Relation/materialDetail")
    Flowable<BaseResponse<SubstanceDetailBean>> substanceDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //找买家详情
    @FormUrlEncoded
    @POST("api/Relation/buyerDetail")
    Flowable<BaseResponse<BuyerDetailBean>> buyerDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //我的需求，找资源，我发布的详情
    @FormUrlEncoded
    @POST("api/Relation/myDetails")
    Flowable<BaseResponse<FindRelationshipReleaseBean>> mineReleaseRelationshipDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //我的需求，找资源， 我接单的详情
    @FormUrlEncoded
    @POST("api/Relation/detail")
    Flowable<BaseResponse<FindRelationshipAcceptBean>> mineAcceptRelationshipDetail(
            @Field("user_id") int user_id,
            @Field("relationships_reply_id") int relationships_reply_id
    );

    //认购信息展示
    @FormUrlEncoded
    @POST("api/Project/startWarrant")
    Flowable<BaseResponse<ProjectSubFirstBean>> startWarrant(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //认购信息确认
    @FormUrlEncoded
    @POST("api/Project/confirmWarrant")
    Flowable<BaseResponse<ProjectSubConfirmBean>> confirmWarrant(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("months") int months,
            @Field("order_sn") String order_sn
    );

    //已认购信息展示
    @FormUrlEncoded
    @POST("api/Project/viewWarrant")
    Flowable<BaseResponse<ProjectSubViewBean>> viewWarrant(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //融云获取token
    @FormUrlEncoded
    @POST("api/WeChat/getToken")
    Flowable<BaseResponse<IMTokenBean>> getIMToken(
            @Field("user_id") int user_id
    );

    //好友群组搜索列表
    @FormUrlEncoded
    @POST("api/Friend/search")
    Flowable<BaseResponse<IMFriendSearchBean>> imFriendSearch(
            @Field("user_id") int user_id,
            @Field("name") String name
    );

    //搜索 更多好友列表
    @FormUrlEncoded
    @POST("api/Friend/searchFriend")
    Flowable<BaseResponse<IMFriendListBean>> searchFriend(
            @Field("user_id") int user_id,
            @Field("name") String name,
            @Field("page") int page
    );

    //搜索 更多群聊列表
    @FormUrlEncoded
    @POST("api/Friend/searchGroup")
    Flowable<BaseResponse<IMGroupListBean>> searchGroup(
            @Field("user_id") int user_id,
            @Field("name") String name,
            @Field("page") int page
    );

    //通讯录 好友列表
    @FormUrlEncoded
    @POST("api/Friend/friendList")
    Flowable<BaseResponse<IMFriendListBean>> friendList(
            @Field("user_id") int user_id,
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    //通讯录 群组列表
    @FormUrlEncoded
    @POST("api/wechat/myGroup")
    Flowable<BaseResponse<IMGroupListBean>> myGroup(
            @Field("user_id") int user_id,
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    //通讯录 好友申请操作
    @FormUrlEncoded
    @POST("api/Friend/apply")
    Flowable<BaseResponse> imFriendApply(
            @Field("user_id") int user_id,
            @Field("receive_user_id") int receive_user_id,
            @Field("msg") String msg
    );

    //工程圈 好友申请数量/角标数量
    @FormUrlEncoded
    @POST("api/Friend/applyCount")
    Flowable<BaseResponse<IMFriendApplyCountBean>> applyCount(
            @Field("user_id") int user_id
    );

    //工程圈 新的好友列表
    @FormUrlEncoded
    @POST("api/Friend/applyList")
    Flowable<BaseResponse<IMFriendApplyListBean>> imFriendApplyList(
            @Field("user_id") int user_id
    );

    //工程圈 好友添加验证
    @FormUrlEncoded
    @POST("api/Friend/operation")
    Flowable<BaseResponse<IMFriendApplyOperationBean>> imFriendApplyOperation(
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("status") int status,
            @Field("msg") String msg
    );

    //搜索职位
    @FormUrlEncoded
    @POST("api/Userinfo/commonPositions")
    Flowable<BaseResponse<List<List<PostBean>>>> searchPost(
            @Field("user_id") int userId
    );

    //基建物资搜索 --- 周转材料
    @FormUrlEncoded
    @POST("api/Material/search")
    Flowable<BaseResponse<List<SearchMaterialBean>>> searchMaterial(
            @Field("user_id") int userId,
            @Field("type") int type,
            @Field("name") String name
    );

    //基建物资搜索 --- 设备物资
    @FormUrlEncoded
    @POST("api/Equipment/search")
    Flowable<BaseResponse<List<SearchMaterialBean>>> searchEquipment(
            @Field("user_id") int userId,
            @Field("type") int type,
            @Field("name") String name
    );

    //基建物资---周转材料收藏
    @FormUrlEncoded
    @POST("api/Material/setCollection")
    Flowable<BaseResponse> collected(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //基建物资---设备物资收藏
    @FormUrlEncoded
    @POST("api/Equipment/setCollection")
    Flowable<BaseResponse> equipmentCollection(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //基建物资---周转材料取消收藏
    @FormUrlEncoded
    @POST("api/Material/cancelCollection")
    Flowable<BaseResponse> collectNew(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //基建物资---设备物资取消收藏
    @FormUrlEncoded
    @POST("api/Equipment/cancelCollection")
    Flowable<BaseResponse> equipmentCancelCollection(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //基建物资存放地列表
    @FormUrlEncoded
    @POST("api/Material/addressList")
    Flowable<BaseResponse<List<MaterialDepositingPlaceBean>>> addressList(
            @Field("user_id") int user_id,
            @Field("material_id") int material_id,
            @Field("supplier_id") int supplier_id,
            @Field("is_shelf") int isShelf,
            @Field("method") int method
    );

    //获取存放地列表
    @POST("api/Material/addressList")
    Flowable<BaseResponse<List<MaterialDepositingPlaceBean>>> addressList(
            @Query("user_id") int user_id,
            @Query("material_id") int material_id,
            @Query("supplier_id") int supplier_id,
            @Query("is_shelf") int isShelf,
            @Query("method") int method,
            @Body AuthGroupBean auth_group
    );

    //基建物资---周转材料收藏列表
    @FormUrlEncoded
    @POST("api/Userinfo/materialCollection")
    Flowable<BaseResponse<MaterialCollectBean>> materialCollection(
            @Field("user_id") int userId,
            @Field("page") int page
    );

    //基建物资---设备物资收藏列表
    @FormUrlEncoded
    @POST("api/Userinfo/equipmentCollection")
    Flowable<BaseResponse<MaterialCollectBean>> mineEquipmentCollection(
            @Field("user_id") int userId,
            @Field("page") int page
    );

    //基建物资---周转材料预定列表
    @FormUrlEncoded
    @POST("api/Material/myMaterial")
    Flowable<BaseResponse<MaterialOrderBean>> materialOrder(
            @Field("user_id") int userId,
            @Field("page") int page,
            @Field("inside") int inside
    );

    //基建物资---设备物资预定列表
    @FormUrlEncoded
    @POST("api/Equipment/myEquipment")
    Flowable<BaseResponse<MaterialOrderBean>> getEquipmentOrder(
            @Field("user_id") int userId,
            @Field("page") int page,
            @Field("inside") int inside
    );

    //基建物质---周转材料预定详情
    @FormUrlEncoded
    @POST("api/Material/myMaterialDetail")
    Flowable<BaseResponse<MaterialOrderBean.ListBean>> materialOrderDetail(
            @Field("user_id") int userId,
            @Field("order_id") int orderId
    );

    //基建物资---设备物资预定详情
    @FormUrlEncoded
    @POST("api/Equipment/myEquipmentDetail")
    Flowable<BaseResponse<MaterialOrderBean.ListBean>> equipmentOrderDetail(
            @Field("user_id") int userId,
            @Field("order_id") int orderId
    );

    //基建物资提交预订单 添加存放地
    @FormUrlEncoded
    @POST("api/Material/addMaterial")
    Flowable<BaseResponse<MaterialAddBean>> getAddMaterial(
            @Field("user_id") int userId,
            @Field("material_id") int material_id,
            @Field("num") BigInteger num,
            @Field("type") int type,
            @Field("lease_start_time") String leaseStartTime,
            @Field("lease_end_time") String leaseEndTime
    );

    //基建物资提交预订单 删除存放地
    @FormUrlEncoded
    @POST("api/Material/del")
    Flowable<BaseResponse<MaterialAddBean>> getDelMaterial(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //基建物资---周转材料提交预订单
    @FormUrlEncoded
    @POST("api/Material/order")
    Flowable<BaseResponse<MaterialAddBean>> getAddMaterialOrder(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("remark") String remark,
            @Field("buy_stock") double buyStock,
            @Field("lease_start_time") String leaseStartTime,
            @Field("lease_end_time") String leaseEndTime,
            @Field("address_id") int address_id
    );

    //基建物资---设备物资提交预定订单
    @FormUrlEncoded
    @POST("api/Equipment/order")
    Flowable<BaseResponse<MaterialAddBean>> getAddEquipmentOrder(
            @Field("user_id") int userId,
            @Field("id") int materialId,
            @Field("remark") String remark,
            @Field("buy_stock") long buyStock,
            @Field("lease_start_time") String leaseStartTime,
            @Field("lease_end_time") String leaseEndTime,
            @Field("address_id") int address_id
    );

    //基建物资---周转材料预定订单删除
    @FormUrlEncoded
    @POST("api/Material/close")
    Flowable<BaseResponse> cancelMaterialOrder(
            @Field("user_id") int userId,
            @Field("order_id") int orderId
    );

    //基建物资---设备物资预定订单删除
    @FormUrlEncoded
    @POST("api/Equipment/close")
    Flowable<BaseResponse> cancelEquipmentOrder(
            @Field("user_id") int userId,
            @Field("order_id") int orderId
    );


    //工程信息分享
    @FormUrlEncoded
    @POST("api/Project/share")
    Flowable<BaseResponse<ShareBean.DataBean>> projectShare(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //个人中心分享
    @FormUrlEncoded
    @POST("api/Userinfo/share")
    Flowable<BaseResponse<ShareBean.DataBean>> getUserInfoShare(
            @Field("user_id") int user_id
    );

    //招采信息分享
    @FormUrlEncoded
    @POST("api/Tenderer/share")
    Flowable<BaseResponse<ShareBean.DataBean>> tendererShare(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //基建头条分享
    @FormUrlEncoded
    @POST("api/Headline/share")
    Flowable<BaseResponse<ShareBean.DataBean>> headlinesShare(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //基建物资周转材料分享
    @FormUrlEncoded
    @POST("api/Material/share")
    Flowable<BaseResponse<ShareBean.DataBean>> materialShare(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //基建物资设备物资分享
    @FormUrlEncoded
    @POST("api/Equipment/share")
    Flowable<BaseResponse<ShareBean.DataBean>> equipmentShare(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //版本升级
    @FormUrlEncoded
    @POST("api/Passport/versionUpgrade")
    Flowable<BaseResponse<VersionUpgradesBean>> versionUpgrade(
            @Field("user_id") int userId,
            @Field("version") String version,
            @Field("type") int type
    );

    //微信登录绑定
    @FormUrlEncoded
    @POST("api/passport/wechatBind")
    Flowable<BaseResponse<WXLoginBindingBean>> getWeChatBinding(
            @Field("unionid") String unionid,
            @Field("openid") String openid,
            @Field("headimgurl") String headimgurl,
            @Field("nickname") String nickname,
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("invite_tel") String invite_tel
    );

    //微信登录验证(获取unionid/openid)
    @FormUrlEncoded
    @POST("api/passport/wxLogin")
    Flowable<BaseResponse<WXLoginVerificationBean>> wxLogin(
            @Field("code") String code
    );

    //微信绑定信息查询
    @FormUrlEncoded
    @POST("api/Userinfo/getWxStatus")
    Flowable<BaseResponse<WXUserInfoBean>> getWxStatus(
            @Field("user_id") int user_id
    );

    //微信内部绑定
    @FormUrlEncoded
    @POST("api/Userinfo/userWechatBind")
    Flowable<BaseResponse> userWechatBind(
            @Field("user_id") int user_id,
            @Field("code") String code
    );

    //微信解绑
    @FormUrlEncoded
    @POST("api/Userinfo/userWechatClose")
    Flowable<BaseResponse> userWechatClose(
            @Field("user_id") int user_id
    );

    //我的工程 --- 工程信息概况
    @FormUrlEncoded
    @POST("api/Userinfo/myProjectDateil")
    Flowable<BaseResponse<ProjectDetailBean>> getMyProjectDateil(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //我的工程 --- 动态信息概况
    @FormUrlEncoded
    @POST("api/Userinfo/myTrackDateil")
    Flowable<BaseResponse<TrackDateilBean>> getMyTrackDateil(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //用户 --- 收货地址列表
    @FormUrlEncoded
    @POST("api/Userinfo/getAddress")
    Flowable<BaseResponse<ReceivingAddressBean>> getReceivingAddress(
            @Field("user_id") int userId
    );

    //用户 --- 修改地址状态
    @FormUrlEncoded
    @POST("api/Userinfo/defaultAddress")
    Flowable<BaseResponse> setDefaultState(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //用户 --- 删除地址
    @FormUrlEncoded
    @POST("api/Userinfo/deleteAddress")
    Flowable<BaseResponse> deleteAddress(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //用户 --- 添加地址
    @FormUrlEncoded
    @POST("api/Userinfo/addAddress")
    Flowable<BaseResponse> newAddress(
            @Field("user_id") int userId,
            @Field("real_name") String name,
            @Field("tel") String phone,
            @Field("company") String company,
            @Field("province") int province,
            @Field("city") int city,
            @Field("address") String address,
            @Field("is_default") int state
    );

    //用户 --- 修改地址
    @FormUrlEncoded
    @POST("api/Userinfo/editAddress")
    Flowable<BaseResponse> editAddress(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("real_name") String name,
            @Field("tel") String phone,
            @Field("company") String company,
            @Field("province") int province,
            @Field("city") int city,
            @Field("address") String address,
            @Field("is_default") int state
    );

    //提交智能设计表单
    @FormUrlEncoded
    @POST("api/Automate/addPost")
    Flowable<BaseResponse> addPost(
            @Field("user_id") int userId,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("automate_name") String automateName,
            @Field("automate_detail") String automateDetail
    );

    //智能设计数据列表
    @FormUrlEncoded
    @POST("api/Automate/dataList")
    Flowable<BaseResponse<SmartDesignOrderBean>> dataList(
            @Field("user_id") int userId,
            @Field("page") int page
    );

    //智能设计详情
    @FormUrlEncoded
    @POST("api/Automate/getDetail")
    Flowable<BaseResponse<SmartDesignOrderBean.SmartDesignOrder>> getSmartDesignDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //智能设计订单阶段数据
    @FormUrlEncoded
    @POST("api/Automate/getPhases")
    Flowable<BaseResponse<List<SmartDesignPhasesBean>>> getPhases(
            @Field("user_id") int userId
    );

    //智能设计订单取消或删除
    @FormUrlEncoded
    @POST("api/Automate/editPost")
    Flowable<BaseResponse> editPost(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("status") int status
    );

    //后台管理 --- 周转材料列表
    @FormUrlEncoded
    @POST("api/Manage/constructionList")
    Flowable<BaseResponse<TurnoverBean>> constructionList(
            @Field("user_id") int userId,
            @Field("page") int page,
            @Field("unit_id") int unit_id,
            @Field("is_shelf") int is_shelf,
            @Field("use_status") int use_status,
            @Field("province") int province,
            @Field("city") int city,
            @Field("material_name") String material_name,
            @Field("supplier_id") int supplier_id
    );

    //后台管理 --- 筛选条件列表
    @FormUrlEncoded
    @POST("api/Manage/constructionSearch")
    Flowable<BaseResponse<TurnoverTypeBean>> constructionSearch(
            @Field("user_id") int userId
    );

    //后台管理 --- 选择条件历史列表
    @FormUrlEncoded
    @POST("api/Manage/constructionCategoryHistory")
    Flowable<BaseListResponse<TurnoverCategoryBean>> constructionCategoryHistory(
            @Field("user_id") int userId
    );

    //后台管理 --- 子公司列表
    @FormUrlEncoded
    @POST("api/Manage/getSupplierInfo")
    Flowable<BaseListResponse<TurnoverCompanyBean>> getSupplierInfo(
            @Field("user_id") int userId
    );

    //后台管理 --- 周转材料列表 筛选项目
    @FormUrlEncoded
    @POST("api/Manage/getUnitInfo")
    Flowable<BaseListResponse<TurnoverNameBean>> getUnitInfo(
            @Field("user_id") int userId,
            @Field("supplier_id") int supplier_id
    );

    //后台管理 --- 周转材料名称类别一级
    @FormUrlEncoded
    @POST("api/Manage/constructionCategoryParent")
    Flowable<BaseListResponse<TurnoverCategoryBean>> constructionCategoryParent(
            @Field("user_id") int userId
    );

    //后台管理 --- 周转材料名称类别二级
    @FormUrlEncoded
    @POST("api/Manage/constructionCategoryList")
    Flowable<BaseListResponse<TurnoverCategoryBean>> constructionCategoryList(
            @Field("user_id") int userId,
            @Field("title") String title,
            @Field("id") int id
    );

    //后台管理 --- 周转材料详情接口
    @FormUrlEncoded
    @POST("api/Manage/constructionDetail")
    Flowable<BaseResponse<TurnoverDetailBean>> getTurnoverDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 周转材料删除
    @FormUrlEncoded
    @POST("api/Manage/constructionDelete")
    Flowable<BaseResponse<String>> constructionDelete(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 周转材料拆分
    @FormUrlEncoded
    @POST("api/Manage/constructionSplit")
    Flowable<BaseResponse<String>> constructionSplit(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("stock") String stock,
            @Field("unit_price") String unit_price,
            @Field("use_status") int use_status,
            @Field("province") int province,
            @Field("city") int city,
            @Field("address") String address,
            @Field("longitude") double longitude,
            @Field("latitude") double latitude
    );

    //后台管理 --- 周转材料添加
    @FormUrlEncoded
    @POST("api/Manage/constructionAdd")
    Flowable<BaseResponse<String>> constructionAdd(
            @Field("user_id") int userId,
            @Field("unit_id") int unit_id,
            @Field("category") int category,
            @Field("material_name") String material_name,
            @Field("stock") String stock,
            @Field("unit_price") String unit_price,
            @Field("unit") int unit,
            @Field("spec") String spec,
            @Field("use_status") int use_status,
            @Field("material_status") int material_status,
            @Field("province") int province,
            @Field("city") int city,
            @Field("address") String address,
            @Field("longitude") double longitude,
            @Field("latitude") double latitude,
            @Field("person_liable") String person_liable,
            @Field("tel") String tel,
            @Field("rapid_wear") int rapid_wear,
            @Field("is_shelf") int is_shelf,
            @Field("shelf_start_time") String shelf_start_time,
            @Field("shelf_end_time") String shelf_end_time,
            @Field("shelf_type") int shelf_type,
            @Field("method") int method,
            @Field("guidance_price") String guidance_price,
            @Field("images") String images,
            @Field("use_count") String use_count,
            @Field("plan") String plan,
            @Field("accumulated_amortization") String accumulated_amortization,
            @Field("start_date") String start_date,
            @Field("entry_time") String entry_time,
            @Field("exit_time") String exit_time,
            @Field("original_price") String original_price,
            @Field("net_worth") String net_worth,
            @Field("remark") String remark
    );

    //后台管理 --- 周转材料编辑
    @FormUrlEncoded
    @POST("api/Manage/constructionEdit")
    Flowable<BaseResponse<String>> constructionEdit(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("unit_id") int unit_id,
            @Field("category") int category,
            @Field("material_name") String material_name,
            @Field("stock") String stock,
            @Field("unit_price") String unit_price,
            @Field("unit") int unit,
            @Field("spec") String spec,
            @Field("use_status") int use_status,
            @Field("material_status") int material_status,
            @Field("province") int province,
            @Field("city") int city,
            @Field("address") String address,
            @Field("longitude") double longitude,
            @Field("latitude") double latitude,
            @Field("person_liable") String person_liable,
            @Field("tel") String tel,
            @Field("rapid_wear") int rapid_wear,
            @Field("is_shelf") int is_shelf,
            @Field("shelf_start_time") String shelf_start_time,
            @Field("shelf_end_time") String shelf_end_time,
            @Field("shelf_type") int shelf_type,
            @Field("method") int method,
            @Field("guidance_price") String guidance_price,
            @Field("images") String images,
            @Field("use_count") String use_count,
            @Field("plan") String plan,
            @Field("accumulated_amortization") String accumulated_amortization,
            @Field("start_date") String start_date,
            @Field("entry_time") String entry_time,
            @Field("exit_time") String exit_time,
            @Field("original_price") String original_price,
            @Field("net_worth") String net_worth,
            @Field("remark") String remark
    );

    //后台管理 --- 周转材料添加-选择项目
    @FormUrlEncoded
    @POST("api/Manage/constructionGetUnit")
    Flowable<BaseListResponse<TurnoverNameBean>> projectListData(
            @Field("user_id") int userId
    );

    //后台管理 --- 周转材料编辑详情
    @FormUrlEncoded
    @POST("api/Manage/constructionEditShow")
    Flowable<BaseResponse<TurnoverDetailEditBean>> constructionEditShow(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 设备管理列表
    @FormUrlEncoded
    @POST("api/Manage/equipmentList")
    Flowable<BaseResponse<DeviceBean>> equipmentList(
            @Field("user_id") int userId,
            @Field("page") int page,
            @Field("unit_id") int unit_id,
            @Field("is_shelf") int is_shelf,
            @Field("use_status") int use_status,
            @Field("province") int province,
            @Field("city") int city,
            @Field("material_name") String material_name,
            @Field("supplier_id") int supplier_id
    );

    //后台管理 --- 设备管理选择条件历史列表
    @FormUrlEncoded
    @POST("api/Manage/equipmentCategoryHistory")
    Flowable<BaseListResponse<TurnoverCategoryBean>> equipmentCategoryHistory(
            @Field("user_id") int userId
    );

    //后台管理 --- 设备管理名称类别一级
    @FormUrlEncoded
    @POST("api/Manage/equipmentCategoryParent")
    Flowable<BaseListResponse<TurnoverCategoryBean>> equipmentCategoryParent(
            @Field("user_id") int userId
    );

    //后台管理 --- 设备管理名称类别二级
    @FormUrlEncoded
    @POST("api/Manage/equipmentCategoryList")
    Flowable<BaseListResponse<TurnoverCategoryBean>> equipmentCategoryList(
            @Field("user_id") int userId,
            @Field("title") String title,
            @Field("id") int id
    );

    //后台管理 --- 设备管理删除
    @FormUrlEncoded
    @POST("api/Manage/equipmentDelete")
    Flowable<BaseResponse<String>> equipmentDelete(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 设备管理详情
    @FormUrlEncoded
    @POST("api/Manage/equipmentDetail")
    Flowable<BaseResponse<DeviceDetailBean>> equipmentDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 设备添加
    @FormUrlEncoded
    @POST("api/Manage/equipmentAdd")
    Flowable<BaseResponse<String>> equipmentAdd(
            @Field("user_id") int userId,
            @Field("unit_id") int unit_id,
            @Field("category") int category,
            @Field("material_name") String material_name,
            @Field("encoding") String encoding,
            @Field("stock") String stock,
            @Field("unit") int unit,
            @Field("spec") String spec,
            @Field("use_status") int use_status,
            @Field("province") int province,
            @Field("city") int city,
            @Field("address") String address,
            @Field("longitude") double longitude,
            @Field("latitude") double latitude,
            @Field("person_liable") String person_liable,
            @Field("tel") String tel,
            @Field("is_shelf") int is_shelf,
            @Field("shelf_start_time") String shelf_start_time,
            @Field("shelf_end_time") String shelf_end_time,
            @Field("shelf_type") int shelf_type,
            @Field("method") int method,
            @Field("guidance_price") String guidance_price,
            @Field("images") String images,
            @Field("brand") String brand,
            @Field("start_date") String start_date,
            @Field("operator_name") String operator_name,
            @Field("original_price") String original_price,
            @Field("main_params") String main_params,
            @Field("power") String power,
            @Field("entry_time") String entry_time,
            @Field("exit_time") String exit_time,
            @Field("material_status") int material_status,
            @Field("use_month_count") String use_month_count,
            @Field("plan") String plan,
            @Field("technology_detail") String technology_detail,
            @Field("equipment_time") String equipment_time
    );

    //后台管理 --- 设备编辑
    @FormUrlEncoded
    @POST("api/Manage/equipmentEdit")
    Flowable<BaseResponse<String>> equipmentEdit(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("unit_id") int unit_id,
            @Field("category") int category,
            @Field("material_name") String material_name,
            @Field("encoding") String encoding,
            @Field("stock") String stock,
            @Field("unit") int unit,
            @Field("spec") String spec,
            @Field("use_status") int use_status,
            @Field("province") int province,
            @Field("city") int city,
            @Field("address") String address,
            @Field("longitude") double longitude,
            @Field("latitude") double latitude,
            @Field("person_liable") String person_liable,
            @Field("tel") String tel,
            @Field("is_shelf") int is_shelf,
            @Field("shelf_start_time") String shelf_start_time,
            @Field("shelf_end_time") String shelf_end_time,
            @Field("shelf_type") int shelf_type,
            @Field("method") int method,
            @Field("guidance_price") String guidance_price,
            @Field("images") String images,
            @Field("brand") String brand,
            @Field("start_date") String start_date,
            @Field("operator_name") String operator_name,
            @Field("original_price") String original_price,
            @Field("main_params") String main_params,
            @Field("power") String power,
            @Field("entry_time") String entry_time,
            @Field("exit_time") String exit_time,
            @Field("material_status") int material_status,
            @Field("use_month_count") String use_month_count,
            @Field("plan") String plan,
            @Field("technology_detail") String technology_detail,
            @Field("equipment_time") String equipment_time
    );

    //后台管理 --- 设备管理编辑详情
    @FormUrlEncoded
    @POST("api/Manage/equipmentEditShow")
    Flowable<BaseResponse<DeviceDetailEditBean>> equipmentEditShow(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 周转材料订单管理
    @FormUrlEncoded
    @POST("api/Manage/constructionOrderList")
    Flowable<BaseResponse<AdminOrderBean>> orderListData(
            @Field("user_id") int userId,
            @Field("unit_id") int unitId,
            @Field("supplier_id") int supplierId,
            @Field("material_name") String material_name,
            @Field("phases") int phases,
            @Field("inside") int inside,
            @Field("order_number") String orderNumber,
            @Field("page") int page
    );

    //后台管理 --- 设备订单管理
    @FormUrlEncoded
    @POST("api/Manage/equipmentOrderList")
    Flowable<BaseResponse<AdminOrderBean>> orderDeviceListData(
            @Field("user_id") int userId,
            @Field("unit_id") int unitId,
            @Field("supplier_id") int supplierId,
            @Field("material_name") String material_name,
            @Field("phases") int phases,
            @Field("inside") int inside,
            @Field("order_number") String orderNumber,
            @Field("page") int page
    );

    //订单管理 --- 筛选条件列表
    @FormUrlEncoded
    @POST("api/Manage/orderSearch")
    Flowable<BaseResponse<OrderSearchBean>> orderSearch(
            @Field("user_id") int userId
    );

    //后台管理 --- 周转材料订单驳回
    @FormUrlEncoded
    @POST("api/Manage/constructionOrderEditStatus")
    Flowable<BaseResponse> constructionOrderEditStatus(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 设备订单驳回
    @FormUrlEncoded
    @POST("api/Manage/equipmentOrderStatus")
    Flowable<BaseResponse> equipmentOrderStatus(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("status") int status
    );

    //后台管理 --- 周转材料详情
    @FormUrlEncoded
    @POST("api/Manage/constructionOrderDetail")
    Flowable<BaseResponse<OrderDetailsBean>> constructionOrderDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 周转材料修改订单进度
    @FormUrlEncoded
    @POST("api/Manage/constructionOrderPhases")
    Flowable<BaseResponse<AdminOrderBean.OrderList>> constructionOrderPhases(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("phases") int phases
    );

    //后台管理 --- 设备详情
    @FormUrlEncoded
    @POST("api/Manage/equipmentOrderDetail")
    Flowable<BaseResponse<OrderDetailsBean>> adminEquipmentOrderDetail(
            @Field("user_id") int userId,
            @Field("id") int id
    );

    //后台管理 --- 设备订单修改进度
    @FormUrlEncoded
    @POST("api/Manage/equipmentOrderPhases")
    Flowable<BaseResponse<AdminOrderBean.OrderList>> equipmentOrderPhases(
            @Field("user_id") int userId,
            @Field("id") int id,
            @Field("phases") int phases
    );

}
