package com.shuangduan.zcy.model.api;

import com.shuangduan.zcy.model.bean.BaseListResponse;
import com.shuangduan.zcy.model.bean.BaseResponse;
import com.shuangduan.zcy.model.bean.LoginBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.ReSetPwdBean;
import com.shuangduan.zcy.model.bean.RegisterBean;
import com.shuangduan.zcy.model.bean.SearchCompanyBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    Observable<BaseResponse> smsCode(
            @Field("tel")String tel,
            @Field("type")int type
    );

    @FormUrlEncoded
    @POST("api/Passport/codeLogin")
    Observable<BaseResponse<LoginBean>> codeLogin(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("client_id")String client_id
    );

    @FormUrlEncoded
    @POST("api/Passport/accountLogin")
    Observable<BaseResponse<LoginBean>> accountLogin(
            @Field("tel")String tel,
            @Field("password")String password,
            @Field("client_id")String client_id
    );

    @FormUrlEncoded
    @POST("api/Passport/register")
    Observable<BaseResponse<RegisterBean>> register(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("password")String password,
            @Field("invite_tel")String invite_tel
    );

    @FormUrlEncoded
    @POST("api/Passport/setPassword")
    Observable<BaseResponse<ReSetPwdBean>> setPassword(
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("password")String password
    );

    @FormUrlEncoded
    @POST("api/Passport/outLogin")
    Observable<BaseResponse> outLogin(
            @Field("user_id")String user_id
    );

    @FormUrlEncoded
    @POST("api/Userinfo/searchCompany")
    Observable<BaseListResponse<SearchCompanyBean>> searchCompany(
            @Field("user_id")String user_id,
            @Field("title")String title
    );

    @FormUrlEncoded
    @POST("api/Userinfo/setInfo")
    Observable<BaseResponse> setInfo(
            @Field("user_id")String user_id,
            @Field("username")String username,
            @Field("sex")String sex,
            @Field("company")String company,
            @Field("position")String position,
            @Field("business_city[]")String business_city[],
            @Field("experience")String experience,
            @Field("managing_products")String managing_products
    );

    @FormUrlEncoded
    @POST("api/Userinfo/telUpdate")
    Observable<BaseResponse> telUpdate(
            @Field("user_id")String user_id,
            @Field("tel")String tel,
            @Field("code")String code,
            @Field("old_tel")String old_tel,
            @Field("old_code")String old_code
    );

    @FormUrlEncoded
    @POST("api/Userinfo/telCheck")
    Observable<BaseResponse> telCheck(
            @Field("user_id")String user_id,
            @Field("code")String code
    );

}
