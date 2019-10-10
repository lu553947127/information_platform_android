package com.shuangduan.zcy.view.release;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.event.LocationEvent;
import com.shuangduan.zcy.vm.PermissionVm;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  根据省市定位项目位置，获取经纬度
 * @time 2019/7/30 9:10
 * @change
 * @chang time
 * @class describe
 */
public class LocationMapActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;

    private PermissionVm permissionVm;
    MapView mMapView = null;
    AMap aMap = null;
    private String cityName;
    private Marker locationMarker;
    private LatLonPoint searchLatlonPoint;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_location_map;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.location_select));
        tvBarRight.setText(getString(R.string.save));

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写

        cityName = getIntent().getStringExtra(CustomConfig.CITY_NAME);

        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_LOCATION){
                init();
            }
        });
        permissionVm.getPermissionLocation(new RxPermissions(this));
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        //直接设置不生效，做下延迟
                        aMap.getUiSettings().setZoomControlsEnabled(false);//设置是否允许显示缩放按钮
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    startJumpAnimation();
                    searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    LogUtils.i(searchLatlonPoint.getLatitude(), searchLatlonPoint.getLongitude());
                }
            });

            aMap.setOnMapLoadedListener(() -> addMarkerInScreenCenter(null));
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setRotateGesturesEnabled(false);//设置地图不能旋转
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。

        DistrictSearch search = new DistrictSearch(this);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(cityName);//传入关键字
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        search.setOnDistrictSearchListener(districtResult -> {
            //在回调函数中解析districtResult获取行政区划信息
            //在districtResult.getAMapException().getErrorCode()=1000时调用districtResult.getDistrict()方法
            //获取查询行政区的结果，详细信息可以参考DistrictItem类。
            LogUtils.i(districtResult.getAMapException().getErrorCode());
            if (districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS){
                LogUtils.i(districtResult.getDistrict().get(0).getName());
                LatLonPoint center = districtResult.getDistrict().get(0).getCenter();
                aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(center.getLatitude(), center.getLongitude())));
            }
        });//绑定监听器
        search.searchDistrictAsyn();//开始搜索
    }

    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f,0.5f));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x,screenPosition.y);
        locationMarker.setZIndex(1);
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (locationMarker != null ) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point =  aMap.getProjection().toScreenLocation(latLng);
            point.y -= ConvertUtils.dp2px(50);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if(input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f)*(1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();

        } else {
            LogUtils.i("ama","screenMarker is null");
        }
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null)
            mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mMapView != null)
            mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mMapView != null)
            mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                LogUtils.i(searchLatlonPoint.getLatitude(), searchLatlonPoint.getLongitude());
                EventBus.getDefault().post(new LocationEvent(
                        getIntent().getStringExtra(CustomConfig.PROVINCE_NAME),
                        getIntent().getStringExtra(CustomConfig.CITY_NAME),
                        getIntent().getIntExtra(CustomConfig.PROVINCE_ID, 0),
                        getIntent().getIntExtra(CustomConfig.CITY_ID, 0),
                        searchLatlonPoint.getLatitude(),
                        searchLatlonPoint.getLongitude()
                ));
                ActivityUtils.finishActivity(ReleaseAreaSelectActivity.class);
                finish();
                break;
        }
    }
}
