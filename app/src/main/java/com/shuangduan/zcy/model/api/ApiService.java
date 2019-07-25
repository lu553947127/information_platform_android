package com.shuangduan.zcy.model.api;

import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseObjResponse;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ConsumeBean;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.ReSetPwdBean;
import com.shuangduan.zcy.model.bean.RegisterBean;
import com.shuangduan.zcy.model.bean.SearchBean;
import com.shuangduan.zcy.model.bean.SearchCompanyBean;
import com.shuangduan.zcy.model.bean.SearchHotBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.model.bean.TypeBean;
import com.shuangduan.zcy.model.bean.UploadBean;
import com.shuangduan.zcy.model.bean.UserInfoBean;
import com.shuangduan.zcy.model.bean.ViewTrackBean;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
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
            @Query("experience") int experience
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Flowable<BaseObjResponse> updateProduct(
            @Field("user_id")int user_id,
            @Query("managing_products") String managing_products
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
            @Field("city[]")int[] city,
            @Field("phases")String phases,
            @Field("type[]")int[] type,
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
    Flowable<BaseListResponse<TrackBean.ListBean>> getViewTrack(
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

}
