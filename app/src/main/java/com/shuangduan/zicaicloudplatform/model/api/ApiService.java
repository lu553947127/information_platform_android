package com.shuangduan.zicaicloudplatform.model.api;

import com.shuangduan.zicaicloudplatform.model.bean.BaseListResponse;
import com.shuangduan.zicaicloudplatform.model.bean.ProvinceBean;

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
    @POST("district/province")
    Observable<BaseListResponse<ProvinceBean>> province(
            @Field("name")String name,
            @Field("id")int id
    );

}
