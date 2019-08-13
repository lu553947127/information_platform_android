package com.shuangduan.zcy.model.api;

import com.shuangduan.zcy.model.bean.AddTrackBean;
import com.shuangduan.zcy.model.bean.AuthenBean;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.bean.BankCardDisBean;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.ContactListBean;
import com.shuangduan.zcy.model.bean.ContactTypeBean;
import com.shuangduan.zcy.model.bean.IncomeLocusBean;
import com.shuangduan.zcy.model.bean.IncomeMsgBean;
import com.shuangduan.zcy.model.bean.IncomePeopleBean;
import com.shuangduan.zcy.model.bean.IncomeReleaseBean;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.model.bean.MaterialCategoryBean;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.bean.MineIncomeBean;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.model.bean.OrderListBean;
import com.shuangduan.zcy.model.bean.OrderSubBean;
import com.shuangduan.zcy.model.bean.PayInfoBean;
import com.shuangduan.zcy.model.bean.ProjectCollectBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.ProjectFilterBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProjectMineBean;
import com.shuangduan.zcy.model.bean.ProjectSearchBean;
import com.shuangduan.zcy.model.bean.ProjectSubBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.PwdPayStateBean;
import com.shuangduan.zcy.model.bean.ReSetPwdBean;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;
import com.shuangduan.zcy.model.bean.RechargeRecordBean;
import com.shuangduan.zcy.model.bean.RechargeRecordDetailBean;
import com.shuangduan.zcy.model.bean.RechargeResultBean;
import com.shuangduan.zcy.model.bean.RechargeShowBean;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.model.bean.RecruitDetailBean;
import com.shuangduan.zcy.model.bean.RecruitSubBean;
import com.shuangduan.zcy.model.bean.RegisterBean;
import com.shuangduan.zcy.model.bean.SearchBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.SubBean;
import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.model.bean.SupplierDetailBean;
import com.shuangduan.zcy.model.bean.SupplierJoinImageBean;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.model.bean.TypeBean;
import com.shuangduan.zcy.model.bean.UploadBean;
import com.shuangduan.zcy.model.bean.UserInfoBean;
import com.shuangduan.zcy.model.bean.WithdrawBean;
import com.shuangduan.zcy.model.bean.WithdrawRecordBean;
import com.shuangduan.zcy.model.event.CityEvent;

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
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.model.api
 * @class describe  后台接口集合
 * @time 2019/7/3 17:11
 * @change
 * @chang time
 * @class describe
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("api/Passport/smsCode")
    Flowable<BaseResponse> smsCode(
            @Field("tel")String tel,
            @Field("type")int type
    );

    @FormUrlEncoded
    @POST("api/Passport/codeLogin")
    Flowable<BaseResponse<LoginBean>> codeLogin(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("client_id")String client_id
    );

    @FormUrlEncoded
    @POST("api/Passport/accountLogin")
    Flowable<BaseResponse<LoginBean>> accountLogin(
            @Field("tel")String tel,
            @Field("password")String password,
            @Field("client_id")String client_id
    );

    @FormUrlEncoded
    @POST("api/Passport/register")
    Flowable<BaseResponse<RegisterBean>> register(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("password")String password,
            @Field("invite_tel")String invite_tel
    );

    @FormUrlEncoded
    @POST("api/Passport/setPassword")
    Flowable<BaseResponse<ReSetPwdBean>> setPassword(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("password")String password
    );

    @FormUrlEncoded
    @POST("api/Userinfo/outLogin")
    Flowable<BaseResponse> outLogin(
            @Field("user_id")int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/searchCompany")
    Flowable<BaseListResponse<String>> searchCompany(
            @Field("user_id")int user_id,
            @Field("title")String title
    );

    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> setInfo(
            @Query("user_id") int user_id,
            @Query("username")String username,
            @Query("sex")int sex,
            @Query("company")String company,
            @Query("position")String position,
            @Body CityEvent business_city,
            @Query("experience")int experience,
            @Query("managing_products")String managing_products
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateUserName(
            @Field("user_id")int user_id,
            @Field("username")String username
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateSex(
            @Field("user_id")int user_id,
            @Field("sex")int sex
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateCompany(
            @Field("user_id")int user_id,
            @Field("company")String company
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updatePosition(
            @Field("user_id")int user_id,
            @Field("position")String position
    );

    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateBusinessCity(
            @Query("user_id") int user_id,
            @Body CityEvent business_city
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateAvatar(
            @Field("user_id")int user_id,
            @Field("avatar")String avatar
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateExperience(
            @Field("user_id")int user_id,
            @Field("experience") int experience
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseResponse> updateProduct(
            @Field("user_id")int user_id,
            @Field("managing_products") String managing_products
    );

    @FormUrlEncoded
    @POST("api/Userinfo/telUpdate")
    Flowable<BaseResponse> telUpdate(
            @Field("user_id")int user_id,
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("old_tel")String old_tel,
            @Field("old_code")String old_code
    );

    @FormUrlEncoded
    @POST("api/Userinfo/telCheck")
    Flowable<BaseResponse> telCheck(
            @Field("user_id")int user_id,
            @Field("code")String code
    );

    @FormUrlEncoded
    @POST("api/Userinfo/userInfo")
    Flowable<BaseResponse<UserInfoBean>> userInfo(
            @Field("user_id")int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/information")
    Flowable<BaseResponse<UserInfoBean>> information(
            @Field("user_id")int user_id
    );

    @Multipart
    @POST("api/Upload/uploadImage")
    Flowable<BaseResponse<UploadBean>> upload(
            @Query("user_id") int user_id,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("api/District/getProvince")
    Flowable<BaseListResponse<ProvinceBean>> getProvince(
            @Field("user_id")int user_id
    );

    @FormUrlEncoded
    @POST("api/District/getCity")
    Flowable<BaseListResponse<CityBean>> getCity(
            @Field("user_id")int user_id,
            @Field("amount")int id
    );

    @FormUrlEncoded
    @POST("api/Project/mapList")
    Flowable<BaseListResponse<MapBean>> mapList(
            @Field("user_id")int user_id,
            @Field("lng")double lng,
            @Field("lat")double lat
    );

    @POST("api/Project/dataList")
    Flowable<BaseResponse<ProjectInfoBean>> projectList(
            @Query("user_id")int user_id,
            @Query("province")String province,
            @Body ProjectFilterBean city,
            @Query("phases")String phases,
            @Query("stime")String stime,
            @Query("etime")String etime,
            @Query("warrant_status")String warrant_status,
            @Query("page")int page
            );

    @FormUrlEncoded
    @POST("api/Project/getTypes")
    Flowable<BaseListResponse<TypeBean>> projectTypes(
            @Field("user_id")int user_id,
            @Field("amount")int id
            );

    @FormUrlEncoded
    @POST("api/Project/getPhases")
    Flowable<BaseListResponse<StageBean>> projectStage(
            @Field("user_id")int user_id
            );

    @FormUrlEncoded
    @POST("api/Project/keywordHot")
    Flowable<BaseListResponse<String>> keywordHot(
            @Field("user_id")int user_id
            );

    @FormUrlEncoded
    @POST("api/Project/keywordList")
    Flowable<BaseResponse<SearchBean>> keywordList(
            @Field("user_id")int user_id,
            @Field("keyword")String keyword
            );

    @FormUrlEncoded
    @POST("api/Project/getDetail")
    Flowable<BaseResponse<ProjectDetailBean>> getDetail(
            @Field("user_id")int user_id,
            @Field("amount")int id
    );

    @FormUrlEncoded
    @POST("api/Project/getTrack")
    Flowable<BaseResponse<TrackBean>> getTrack(
            @Field("user_id")int user_id,
            @Field("amount")int id,
            @Field("page")int page,
            @Field("type")int type
    );

    @FormUrlEncoded
    @POST("api/Project/viewTrack")
    Flowable<BaseResponse<TrackBean>> getViewTrack(
            @Field("user_id")int user_id,
            @Field("amount")int id,
            @Field("page")int page
    );

    @FormUrlEncoded
    @POST("api/Project/consumeList")
    Flowable<BaseResponse<ConsumeBean>> consumeList(
            @Field("user_id")int user_id,
            @Field("amount")int id,
            @Field("page")int page
    );

    @FormUrlEncoded
    @POST("api/Project/setCollection")
    Flowable<BaseResponse> collect(
            @Field("user_id")int user_id,
            @Field("amount")int id
    );

    @FormUrlEncoded
    @POST("api/Project/cancelCollection")
    Flowable<BaseResponse> cancelCollection(
            @Field("user_id")int user_id,
            @Field("amount")int id
    );

    @FormUrlEncoded
    @POST("api/Project/correction")
    Flowable<BaseResponse> correction(
            @Field("user_id")int user_id,
            @Field("amount")int id,
            @Field("content")String content
    );

    @FormUrlEncoded
    @POST("api/Userinfo/myProject")
    Flowable<BaseResponse<ProjectMineBean>> myProject(
            @Field("user_id")int user_id,
            @Field("page")int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/myProjectTrack")
    Flowable<BaseResponse<LocusMineBean>> myProjectTrack(
            @Field("user_id")int user_id,
            @Field("page")int page
    );

    @FormUrlEncoded
    @POST("/api/Userinfo/recharge")
    Flowable<BaseResponse<PayInfoBean>> recharge(
            @Field("user_id")int user_id,
            @Field("amount")String amount,
            @Field("type")int type
    );

    @FormUrlEncoded
    @POST("api/Project/getPhoneType")
    Flowable<BaseListResponse<ContactTypeBean>> getContactType(
            @Field("user_id")int user_id
    );

    @POST("api/Project/addProject")
    Flowable<BaseResponse> addProject(
            @Query("user_id") int user_id,
            @Query("title")String title,
            @Query("company")String company,
            @Query("province")int province,
            @Query("city")int city,
            @Query("phases")int phases,
            @Query("type")int type,
            @Query("start_time")String start_time,
            @Query("end_time")String end_time,
            @Query("acreage")String acreage,
            @Query("valuation")String valuation,
            @Query("intro")String intro,
            @Query("materials")String materials,
            @Query("longitude")String longitude,
            @Query("latitude")String latitude,
            @Body ContactListBean contact
    );

    @POST("api/Project/addTrack")
    Flowable<BaseResponse> addTrack(
            @Query("user_id") int user_id,
            @Query("amount")int id,
            @Query("remarks")String remarks,
            @Query("name")String name,
            @Query("tel")String tel,
            @Query("update_time")String update_time,
            @Body AddTrackBean image_id
    );

    @FormUrlEncoded
    @POST("api/Project/keywordTitle")
    Flowable<BaseResponse<ProjectSearchBean>> keywordTitle(
            @Field("user_id") int user_id,
            @Field("keyword")String keyword,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Subscription/projectSubscription")
    Flowable<BaseResponse<ProjectSubBean>> projectSub(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Subscription/tenderer")
    Flowable<BaseResponse<RecruitSubBean>> recruitSub(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/myPhases")
    Flowable<BaseListResponse<MyPhasesBean>> myPhases(
            @Field("user_id") int user_id
    );

    @POST("api/Userinfo/setPhases")
    Flowable<BaseResponse> setPhases(
            @Query("user_id") int user_id,
            @Body SubBean id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/historyProject")
    Flowable<BaseListResponse<ReadHistoryBean>> historyProject(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/historyTenderer")
    Flowable<BaseListResponse<ReadHistoryBean>> historyTenderer(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/feedback")
    Flowable<BaseResponse> feedback(
            @Field("user_id") int user_id,
            @Field("content") String content
    );

    @FormUrlEncoded
    @POST("api/Userinfo/projectCollection")
    Flowable<BaseResponse<ProjectCollectBean>> projectCollection(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/tendererCollection")
    Flowable<BaseResponse<RecruitBean>> recruitCollection(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/authentication")
    Flowable<BaseResponse<AuthenBean>> authentication(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/idcard")
    Flowable<BaseResponse> idcard(
            @Field("user_id") int user_id,
            @Field("image_front") String image_front,
            @Field("image_reverse_site") String image_reverse_site
    );

    @FormUrlEncoded
    @POST("api/Userinfo/loading")
    Flowable<BaseResponse<WithdrawBean>> withdrawMsg(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/record")
    Flowable<BaseResponse<WithdrawRecordBean>> withdrawRecord(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/detailsRecord")
    Flowable<BaseResponse<WithdrawRecordBean.ListBean>> withdrawRecordDetail(
            @Field("user_id") int user_id,
            @Field("amount") int id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/withdrawDeposit")
    Flowable<BaseResponse> withdraw(
            @Field("user_id") int user_id,
            @Field("bankcard_id") int bankcard_id,
            @Field("price") String price
    );

    @FormUrlEncoded
    @POST("api/Bankcard/bankcardList")
    Flowable<BaseListResponse<BankCardBean>> bankcardList(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Bankcard/ibcard")
    Flowable<BaseResponse<BankCardDisBean>> bankcardDis(
            @Field("user_id") int user_id,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("api/Bankcard/bankcardAdd")
    Flowable<BaseResponse> bankcardAdd(
            @Field("user_id") int user_id,
            @Field("bankcard_num") String bankcard_num,
            @Field("opening_bank") String opening_bank
    );

    @FormUrlEncoded
    @POST("api/Bankcard/bankCardDel")
    Flowable<BaseResponse> unbindBankcard(
            @Field("user_id") int user_id,
            @Field("bankcard_id") int bankcard_id
    );

    @FormUrlEncoded
    @POST("api/Profit/myProceeds")
    Flowable<BaseResponse<MineIncomeBean>> myProceeds(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Profit/publish")
    Flowable<BaseResponse<IncomeReleaseBean>> incomeRelease(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Profit/Floor")
    Flowable<BaseResponse<IncomePeopleBean>> incomePeople(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Profit/subscribe")
    Flowable<BaseResponse<IncomeMsgBean>> incomeMsg(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Profit/track")
    Flowable<BaseResponse<IncomeLocusBean>> incomeLocus(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Tenderer/dataList")
    Flowable<BaseResponse<RecruitBean>> recruitList(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Tenderer/getDetail")
    Flowable<BaseResponse<RecruitDetailBean>> recruitDetail(
            @Field("user_id") int user_id,
            @Field("amount") int id
    );

    @FormUrlEncoded
    @POST("api/Tenderer/setCollection")
    Flowable<BaseResponse> recruitCollect(
            @Field("user_id") int user_id,
            @Field("amount") int id
    );

    @FormUrlEncoded
    @POST("api/Tenderer/cancelCollection")
    Flowable<BaseResponse> recruitCancelCollect(
            @Field("user_id") int user_id,
            @Field("amount") int id
    );

    @FormUrlEncoded
    @POST("api/Material/dateList")
    Flowable<BaseResponse<MaterialBean>> materialList(
            @Field("user_id") int user_id,
            @Field("categoryId") int category_id,
            @Field("type") int type,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Material/getDetail")
    Flowable<BaseResponse<MaterialDetailBean>> materialDetail(
            @Field("user_id") int user_id,
            @Field("amount") int id
    );

    @FormUrlEncoded
    @POST("api/Material/getCategory")
    Flowable<BaseListResponse<MaterialCategoryBean>> getCategory(
            @Field("user_id") int user_id,
            @Field("categoryId") int category_id
    );

    @FormUrlEncoded
    @POST("api/supplier/supplierList")
    Flowable<BaseResponse<SupplierBean>> supplierList(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/supplier/supplierDetails")
    Flowable<BaseResponse<SupplierDetailBean>> supplierDetail(
            @Field("user_id") int user_id,
            @Field("amount") int id
    );

    @POST("api/supplier/supplierApply")
    Flowable<BaseResponse> supplierJoin(
            @Query("user_id") int user_id,
            @Body SupplierJoinImageBean joinImageBean
            );

    @FormUrlEncoded
    @POST("api/Order/projectOrder")
    Flowable<BaseResponse<OrderListBean>> projectOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Order/trackOrder")
    Flowable<BaseResponse<OrderListBean>> locusOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Order/tendererOrder")
    Flowable<BaseResponse<OrderListBean>> recruitOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Order/supplierOrder")
    Flowable<BaseResponse<OrderListBean>> supplierOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Order/warrantOrder")
    Flowable<BaseResponse<OrderSubBean>> subOrder(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/rechargeLoading")
    Flowable<BaseResponse<RechargeShowBean>> rechargeShow(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("/api/Pay/orderStatus")
    Flowable<BaseResponse<RechargeResultBean>> rechargeResult(
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn
    );

    @FormUrlEncoded
    @POST("api/Userinfo/rechargeRecord")
    Flowable<BaseResponse<RechargeRecordBean>> rechargeRecord(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/rechargeDetails")
    Flowable<BaseResponse<RechargeRecordDetailBean>> rechargeRecordDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/coinPassword")
    Flowable<BaseResponse> setPwdPay(
            @Field("user_id") int user_id,
            @Field("password") String password,
            @Field("tel") String tel,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setCoinPassword")
    Flowable<BaseResponse> updatePwdPay(
            @Field("user_id") int user_id,
            @Field("old_password") String old_password,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/Userinfo/forgetPassword")
    Flowable<BaseResponse> forgetPwdPay(
            @Field("user_id") int user_id,
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/Userinfo/coinPwdStatus")
    Flowable<BaseResponse<PwdPayStateBean>> pwdPayState(
            @Field("user_id") int user_id
    );
}
