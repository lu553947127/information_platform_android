package com.shuangduan.zcy.model.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shuangduan.zcy.model.bean.AuthenBean;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseObjResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.ContactBean;
import com.shuangduan.zcy.model.bean.ContactListBean;
import com.shuangduan.zcy.model.bean.ContactTypeBean;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.model.bean.MineIncomeBean;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.model.bean.PayInfoBean;
import com.shuangduan.zcy.model.bean.ProjectCollectBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProjectMineBean;
import com.shuangduan.zcy.model.bean.ProjectSearchBean;
import com.shuangduan.zcy.model.bean.ProjectSubBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.ReSetPwdBean;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.model.bean.RecruitDetailBean;
import com.shuangduan.zcy.model.bean.RecruitSubBean;
import com.shuangduan.zcy.model.bean.RegisterBean;
import com.shuangduan.zcy.model.bean.SearchBean;
import com.shuangduan.zcy.model.bean.SearchCompanyBean;
import com.shuangduan.zcy.model.bean.SearchHotBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.SubBean;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.model.bean.TypeBean;
import com.shuangduan.zcy.model.bean.UploadBean;
import com.shuangduan.zcy.model.bean.UserInfoBean;
import com.shuangduan.zcy.model.bean.WithdrawBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
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
    Flowable<BaseObjResponse> smsCode(
            @Field("tel")String tel,
            @Field("type")int type
    );

    @FormUrlEncoded
    @POST("api/Passport/codeLogin")
    Flowable<BaseObjResponse<LoginBean>> codeLogin(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("client_id")String client_id
    );

    @FormUrlEncoded
    @POST("api/Passport/accountLogin")
    Flowable<BaseObjResponse<LoginBean>> accountLogin(
            @Field("tel")String tel,
            @Field("password")String password,
            @Field("client_id")String client_id
    );

    @FormUrlEncoded
    @POST("api/Passport/register")
    Flowable<BaseObjResponse<RegisterBean>> register(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("password")String password,
            @Field("invite_tel")String invite_tel
    );

    @FormUrlEncoded
    @POST("api/Passport/setPassword")
    Flowable<BaseObjResponse<ReSetPwdBean>> setPassword(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("password")String password
    );

    @FormUrlEncoded
    @POST("api/Passport/outLogin")
    Flowable<BaseObjResponse> outLogin(
            @Field("user_id")int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/searchCompany")
    Flowable<BaseListResponse<SearchCompanyBean>> searchCompany(
            @Field("user_id")int user_id,
            @Field("title")String title
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> setInfo(
            @Field("user_id")int user_id,
            @Field("username")String username,
            @Field("sex")int sex,
            @Field("company")String company,
            @Field("position")String position,
            @Query("business_city[]") int[] business_city,
            @Field("experience")int experience,
            @Field("managing_products")String managing_products
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateUserName(
            @Field("user_id")int user_id,
            @Field("username")String username
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateSex(
            @Field("user_id")int user_id,
            @Field("sex")int sex
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateCompany(
            @Field("user_id")int user_id,
            @Field("company")String company
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updatePosition(
            @Field("user_id")int user_id,
            @Field("position")String position
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateBusinessCity(
            @Field("user_id")int user_id,
            @Query("business_city[]") int[] business_city
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateAvatar(
            @Field("user_id")int user_id,
            @Field("avatar")String avatar
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateExperience(
            @Field("user_id")int user_id,
            @Field("experience") int experience
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateProduct(
            @Field("user_id")int user_id,
            @Field("managing_products") String managing_products
    );

    @FormUrlEncoded
    @POST("api/Userinfo/telUpdate")
    Flowable<BaseObjResponse> telUpdate(
            @Field("user_id")int user_id,
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("old_tel")String old_tel,
            @Field("old_code")String old_code
    );

    @FormUrlEncoded
    @POST("api/Userinfo/telCheck")
    Flowable<BaseObjResponse> telCheck(
            @Field("user_id")int user_id,
            @Field("code")String code
    );

    @FormUrlEncoded
    @POST("api/Userinfo/userInfo")
    Flowable<BaseObjResponse<UserInfoBean>> userInfo(
            @Field("user_id")int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/information")
    Flowable<BaseObjResponse<UserInfoBean>> information(
            @Field("user_id")int user_id
    );

    @Multipart
    @POST("api/Upload/uploadImage")
    Flowable<BaseObjResponse<UploadBean>> upload(
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
            @Field("id")int id
    );

    @FormUrlEncoded
    @POST("api/Project/mapList")
    Flowable<BaseListResponse<MapBean>> mapList(
            @Field("user_id")int user_id,
            @Field("lng")double lng,
            @Field("lat")double lat
    );

    @FormUrlEncoded
    @POST("api/Project/dataList")
    Flowable<BaseObjResponse<ProjectInfoBean>> projectList(
            @Field("user_id")int user_id,
            @Field("province")String province,
            @Query("city[]")int[] city,
            @Field("phases")String phases,
            @Query("type[]")int[] type,
            @Field("stime")String stime,
            @Field("etime")String etime,
            @Field("warrant_status")String warrant_status,
            @Field("page")int page
            );

    @FormUrlEncoded
    @POST("api/Project/getTypes")
    Flowable<BaseListResponse<TypeBean>> projectTypes(
            @Field("user_id")int user_id,
            @Field("id")int id
            );

    @FormUrlEncoded
    @POST("api/Project/getPhases")
    Flowable<BaseListResponse<StageBean>> projectStage(
            @Field("user_id")int user_id
            );

    @FormUrlEncoded
    @POST("api/Project/keywordHot")
    Flowable<BaseListResponse<SearchHotBean>> keywordHot(
            @Field("user_id")int user_id
            );

    @FormUrlEncoded
    @POST("api/Project/keywordList")
    Flowable<BaseObjResponse<SearchBean>> keywordList(
            @Field("user_id")int user_id,
            @Field("keyword")String keyword
            );

    @FormUrlEncoded
    @POST("api/Project/getDetail")
    Flowable<BaseObjResponse<ProjectDetailBean>> getDetail(
            @Field("user_id")int user_id,
            @Field("id")int id
    );

    @FormUrlEncoded
    @POST("api/Project/getTrack")
    Flowable<BaseObjResponse<TrackBean>> getTrack(
            @Field("user_id")int user_id,
            @Field("id")int id,
            @Field("page")int page,
            @Field("type")int type
    );

    @FormUrlEncoded
    @POST("api/Project/viewTrack")
    Flowable<BaseObjResponse<TrackBean>> getViewTrack(
            @Field("user_id")int user_id,
            @Field("id")int id
    );

    @FormUrlEncoded
    @POST("api/Project/consumeList")
    Flowable<BaseObjResponse<ConsumeBean>> consumeList(
            @Field("user_id")int user_id,
            @Field("id")int id,
            @Field("page")int page
    );

    @FormUrlEncoded
    @POST("api/Project/setCollection")
    Flowable<BaseObjResponse> collect(
            @Field("user_id")int user_id,
            @Field("id")int id
    );

    @FormUrlEncoded
    @POST("api/Project/cancelCollection")
    Flowable<BaseObjResponse> cancelCollection(
            @Field("user_id")int user_id,
            @Field("id")int id
    );

    @FormUrlEncoded
    @POST("api/Project/correction")
    Flowable<BaseObjResponse> correction(
            @Field("user_id")int user_id,
            @Field("id")int id,
            @Field("content")String content
    );

    @FormUrlEncoded
    @POST("api/Userinfo/myProject")
    Flowable<BaseObjResponse<ProjectMineBean>> myProject(
            @Field("user_id")int user_id,
            @Field("page")int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/myProjectTrack")
    Flowable<BaseObjResponse<LocusMineBean>> myProjectTrack(
            @Field("user_id")int user_id,
            @Field("page")int page
    );

    @FormUrlEncoded
    @POST("api/Pay/projectWarrant")
    Flowable<BaseObjResponse<PayInfoBean>> projectWarrant(
            @Field("user_id")int user_id,
            @Field("id")int id,
            @Field("type")String type
    );

    @FormUrlEncoded
    @POST("api/Project/getPhoneType")
    Flowable<BaseListResponse<ContactTypeBean>> getContactType(
            @Field("user_id")int user_id
    );

    @POST("api/Project/addProject")
    Flowable<BaseObjResponse> addProject(
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

    @FormUrlEncoded
    @POST("api/Project/addTrack")
    Flowable<BaseObjResponse> addTrack(
            @Field("user_id") int user_id,
            @Field("id")int id,
            @Field("remarks")String remarks,
            @Field("name")String name,
            @Field("tel")String tel,
            @Field("update_time")String update_time,
            @Query("image_id")int[] image_id
    );

    @FormUrlEncoded
    @POST("api/Project/keywordTitle")
    Flowable<BaseObjResponse<ProjectSearchBean>> keywordTitle(
            @Field("user_id") int user_id,
            @Field("keyword")String keyword,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Subscription/projectSubscription")
    Flowable<BaseObjResponse<ProjectSubBean>> projectSub(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Subscription/tenderer")
    Flowable<BaseObjResponse<RecruitSubBean>> recruitSub(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/myPhases")
    Flowable<BaseListResponse<MyPhasesBean>> myPhases(
            @Field("user_id") int user_id
    );

    @POST("api/Userinfo/setPhases")
    Flowable<BaseObjResponse> setPhases(
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
    Flowable<BaseObjResponse> feedback(
            @Field("user_id") int user_id,
            @Field("content") String content
    );

    @FormUrlEncoded
    @POST("api/Userinfo/projectCollection")
    Flowable<BaseObjResponse<ProjectCollectBean>> projectCollection(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/tendererCollection")
    Flowable<BaseObjResponse<RecruitBean>> recruitCollection(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Userinfo/authentication")
    Flowable<BaseObjResponse<AuthenBean>> authentication(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/idcard")
    Flowable<BaseObjResponse> idcard(
            @Field("user_id") int user_id,
            @Field("image_front") String image_front,
            @Field("image_reverse_site") String image_reverse_site
    );

    @FormUrlEncoded
    @POST("api/Userinfo/loading")
    Flowable<BaseObjResponse<WithdrawBean>> withdrawMsg(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Bankcard/bankcardList")
    Flowable<BaseListResponse<BankCardBean>> bankcardList(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Profit/myProceeds")
    Flowable<BaseObjResponse<MineIncomeBean>> myProceeds(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("api/Tenderer/dataList")
    Flowable<BaseObjResponse<RecruitBean>> recruitList(
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("api/Tenderer/getDetail")
    Flowable<BaseObjResponse<RecruitDetailBean>> recruitDetail(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("api/Tenderer/setCollection")
    Flowable<BaseObjResponse> recruitCollect(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("api/Tenderer/cancelCollection")
    Flowable<BaseObjResponse> recruitCancelCollect(
            @Field("user_id") int user_id,
            @Field("id") int id
    );

}
