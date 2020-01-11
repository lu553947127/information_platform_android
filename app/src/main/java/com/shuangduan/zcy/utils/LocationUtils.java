package com.shuangduan.zcy.utils;

import android.annotation.SuppressLint;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.model.bean.WeatherBean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.manage
 * @ClassName: LocationUtils
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/7 13:59
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/7 13:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("StaticFieldLeak")
public class LocationUtils {

    private static AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption = null;

    private static class LocationHolder {
        private static final LocationUtils INSTANCE = new LocationUtils();
    }

    public static LocationUtils getInstance() {
        return LocationHolder.INSTANCE;
    }

    public void startLocalService() {
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getInstance());
        //设置定位回调监听
        mLocationOption = getDefaultOption();
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(location -> {
            if (null != location) {
                if (location.getErrorCode() == 0) {
                    SPUtils.getInstance().put(SpConfig.LONGITUDE, String.valueOf(location.getLongitude()), true);
                    SPUtils.getInstance().put(SpConfig.LATITUDE, String.valueOf(location.getLatitude()), true);
                    getAddressChange(location.getLatitude(), location.getLongitude());
                }
            } else {
                ToastUtils.showShort("定位失败，loc is null");
            }
        });
        mLocationClient.startLocation();
        mLocationClient.setLocationListener(aMapLocation -> {

        });
    }

    //经纬度转换地址
    private void getAddressChange(final double latitude, double longitude) {
        GeocodeSearch geocoderSearch = new GeocodeSearch(MyApplication.getInstance());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                if (rCode == 1000) {
                    LogUtils.e("formatAddress", "rCode:" + result.getRegeocodeAddress().getAdCode());
                    LogUtils.e("formatAddress", "address:" + result.getRegeocodeAddress().getFormatAddress());
                    //区域编码
                    getWeather(result.getRegeocodeAddress().getAdCode());
                } else {
                    LogUtils.e("地理编码", "地址名出错");
                }
            }
        });
        LatLonPoint lp = new LatLonPoint(latitude, longitude);
        RegeocodeQuery query = new RegeocodeQuery(lp, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }


    public void stopLocalService() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient.stopLocation();
            mLocationClient = null;
            mLocationOption = null;
        }
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false(是否进行持续定位)
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    //获取天气资料
    private void getWeather(String cityCode) {
        OkGo.<String>post(Common.WEATHER_INFO)
                .tag(this)
                .headers("key", Common.AMAP_WEB_KEY)//key值
                .params("city", cityCode)//城市编码
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        try {
                            WeatherBean bean = new Gson().fromJson(response.body(), WeatherBean.class);
                            LogUtils.json(response.body());
                            SPUtils.getInstance().put(SpConfig.WEATHER, bean.getLives().get(0).getTemperature() + "℃\n" + bean.getLives().get(0).getWeather(), true);
                        } catch (JsonSyntaxException | IllegalStateException ignored) {

                        }
                    }
                });
    }
}
