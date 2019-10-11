package com.shuangduan.zcy.view.projectinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.bean.MapBean;
import com.shuangduan.zcy.utils.GpsUtils;
import com.shuangduan.zcy.view.search.SearchActivity;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.ProjectInfoVm;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.activity
 * @class 工程信息
 * @time 2019/7/11 16:58
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    MapView mMapView = null;
    AMap aMap = null;
    private List<MapBean> throughPointList;
    private Marker targetMarker;
    private ProjectInfoVm projectInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_info;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[0]);
        ivBarRight.setImageResource(R.drawable.icon_search);
        tvBarRight.setVisibility(View.GONE);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        getLocationPermission();
        projectInfoVm = ViewModelProviders.of(this).get(ProjectInfoVm.class);
    }

    //地图初始化
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
        }
    }

    //设置aMap的属性
    private void setUpMap() {
        //地图缩放级别
        double zoom = 14.190743;
//        LatLng latLng = new LatLng(36.67648360228993,117.13573493840586);
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) zoom));
        aMap.moveCamera(CameraUpdateFactory.zoomTo((float) zoom));
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setRotateGesturesEnabled(false);//设置地图旋转手势
        aMap.getUiSettings().setTiltGesturesEnabled(false);//设置地图倾斜手势
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//设置放缩图标在右下
        aMap.setOnMyLocationChangeListener(location -> {
            LogUtils.i(location.getLongitude(), location.getLatitude());
            projectInfoVm.mapList(location.getLongitude(), location.getLatitude());
        });

//        //地图点击事件监听接口。当用户点击地图时回调此方法，如果点击在某个覆盖物（如marker、polyline）上，且处理了该点击事件，则不会回调此方法。
//        aMap.setOnMapClickListener(poi -> {
//            if (popupWindow != null) {
//                if (popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                }
//                popupWindow = null;
//            }
//        });

        //监测地图画面的移动
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {

            //用户对地图做出一系列改变地图可视区域的操作（如拖动、动画滑动、缩放）完成之后回调此方法。
            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LogUtils.i(cameraPosition.target.latitude, cameraPosition.target.longitude);
                LogUtils.i(cameraPosition.zoom);
                projectInfoVm.mapList(cameraPosition.target.longitude,cameraPosition.target.latitude);
                if (popupWindow != null) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    popupWindow = null;
                }
            }

            //可视范围改变时回调此方法。
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }
        });

        //获取地图马克点数据成功返回结果
        projectInfoVm.mapLiveData.observe(ProjectInfoActivity.this, mapBeans -> {
            //marker经纬度数据
            throughPointList = mapBeans != null ? mapBeans : new ArrayList<>();
            if (throughPointList.size()!=0){
                setMarker();
            }else {
                ToastUtils.showShort("您所处位置周围没有工程项目，换个地方吧！");
            }
        });
        setupLocationStyle();
    }

    //设置自定义定位蓝点
    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.icon_location));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(getResources().getColor(R.color.colorTransparent));
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.colorTransparent));
        //定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    //加载地图马克点
    ArrayList<Marker> markerArrayList;
    private void setMarker() {
        clearAllMarker();
        //marker集合
        ArrayList<MarkerOptions> markers = new ArrayList<>();
        for (MapBean mapBean : throughPointList) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(mapBean.getLatitude(), mapBean.getLongitude()))
                    .visible(true)
                    .title(mapBean.getTitle())
                    .infoWindowEnable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker));
            markers.add(markerOptions);
        }

        //添加marker
        markerArrayList = aMap.addMarkers(markers, false);
        if (markerArrayList != null){
            for (int i = 0; i < markerArrayList.size(); i++) {
                //遍历添加的marker,给marker添加额外数据
                markerArrayList.get(i).setObject(i);
            }
        }

        //马克点点击监听
        aMap.setOnMarkerClickListener(marker -> {
            LogUtils.i(marker.getId());
            //点击的marker是定位的小蓝点，不走动画和弹窗
            if ("Marker1".equals(marker.getId())) return true;
            //上一个标记marker
            if (targetMarker != null) {
                setMarkerAnim(targetMarker, 1.5f, 1, 1.5f, 1);
            }
            setMarkerAnim(marker, 1, 1.5f, 1, 1.5f);
            showPopWindow(marker.getTitle(), (Integer) marker.getObject());
            return true;
        });
    }

    //清除所有Marker
    private void clearAllMarker() {
        if (markerArrayList != null){
            for (Marker marker : markerArrayList) {
                marker.remove();
            }
            markerArrayList.clear();
        }
    }

    //点击地图马克点变大动画
    private void setMarkerAnim(Marker marker, float v, float v1, float v2, float v3) {
        Animation animation = new ScaleAnimation(v, v1, v2, v3);
        long duration = 300L;
        animation.setDuration(duration);
        animation.setFillMode(Animation.FILL_MODE_FORWARDS);
        animation.setInterpolator(new LinearInterpolator());

        marker.setAnimation(animation);
        marker.startAnimation();
        targetMarker = marker;
    }

    /**
     * 地图马克点显示信息弹窗
     * @param title
     * @param position
     */
    private CommonPopupWindow popupWindow;
    TextView tvTitle;
    TextView tvContent;
    TextView tvType;
    TextView tvReaders;
    TextView tvTime;
    private void showPopWindow(String title, int position) {
        MapBean mapBean = throughPointList.get(position);
        if (popupWindow == null) {
            popupWindow = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.dialog_project_info)
                    .setOutsideTouchable(false)
                    .setBackGroundLevel(1f)
                    .setWidthAndHeight(ConvertUtils.dp2px(320), ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setAnimationStyle(R.style.DialogAnimBottom)
                    .setViewOnclickListener((view, layoutResId) -> {
                        tvTitle = view.findViewById(R.id.tv_title);
                        tvContent = view.findViewById(R.id.tv_content);
                        tvType = view.findViewById(R.id.tv_type);
                        tvReaders = view.findViewById(R.id.tv_readers);
                        tvTime = view.findViewById(R.id.tv_time);
                        view.setOnClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt(CustomConfig.PROJECT_ID, mapBean.getId());
                            bundle.putInt(CustomConfig.LOCATION, 0);
                            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
                        });
                    })
                    .create();
        }
        tvTitle.setText(title);
        tvContent.setText(mapBean.getIntro());
        tvType.setText(mapBean.getPhases());
        tvReaders.setText(String.format(getString(R.string.format_num_of_collector), mapBean.getSubscription_num()));
        tvTime.setText(String.format(getString(R.string.format_update_time), mapBean.getUpdate_time()));

        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, ConvertUtils.dp2px(45));
        }
    }

    @Override
    protected void onDestroy() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow = null;
        }
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

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }

    //获取定位权限
    private void getLocationPermission() {
        PermissionVm permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_LOCATION){
                if (GpsUtils.isOPen(this)){
                    init();
                }else {
                    new CustomDialog(this)
                            .setTip("为了更好的为您服务，请您打开您的GPS!")
                            .setCallBack(new BaseDialog.CallBack() {
                                @Override
                                public void cancel() {
                                    finish();
                                }

                                @Override
                                public void ok(String s) {
                                    GpsUtils.openGPS(ProjectInfoActivity.this);
                                }
                            }).showDialog();
                }
            }
        });
        permissionVm.getPermissionLocation(new RxPermissions(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            init();
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.fl_list})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                ActivityUtils.startActivity(SearchActivity.class);

//                Bundle bundle = new Bundle();
//                bundle.putString(CustomConfig.KEYWORD, "");
//                bundle.putString(CustomConfig.PROJECT_TYPE, "");
//                ActivityUtils.startActivity(bundle, SearchResultActivity.class);
                break;
            case R.id.fl_list:
                ActivityUtils.startActivity(ProjectInfoListActivity.class);
                break;
        }
    }

}
