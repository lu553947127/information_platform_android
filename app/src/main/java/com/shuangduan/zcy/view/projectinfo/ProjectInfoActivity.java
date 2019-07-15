package com.shuangduan.zcy.view.projectinfo;

import android.location.Location;
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

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.view.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.activity
 * @class describe
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
    private List<LatLonPoint> throughPointList;
    private Marker targetMarker;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[0]);
        ivBarRight.setImageResource(R.drawable.icon_search);
        tvBarRight.setVisibility(View.GONE);

        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //marker经纬度假数据
        throughPointList = new ArrayList<>();
        throughPointList.add(new LatLonPoint(36.676262, 117.135815));
        throughPointList.add(new LatLonPoint(36.676262, 117.136015));
        throughPointList.add(new LatLonPoint(36.676062, 117.135815));
        throughPointList.add(new LatLonPoint(36.676062, 117.136015));
        throughPointList.add(new LatLonPoint(36.676062, 117.135715));
        throughPointList.add(new LatLonPoint(36.676062, 117.135615));
        throughPointList.add(new LatLonPoint(36.676062, 117.135515));
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

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setRotateGesturesEnabled(false);//设置地图不能旋转
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LogUtils.i(location);
            }
        });
        setupLocationStyle();
    }

    /**
     * 设置自定义定位蓝点
     */
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

        setMarker();
    }

    private void setMarker() {
        //marker集合
        ArrayList<MarkerOptions> markers = new ArrayList<>();
        for (int i = 0; i < throughPointList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(throughPointList.get(i).getLatitude(), throughPointList.get(i).getLongitude()))
                    .visible(true)
                    .title("济南莱芜" + i + 1 + "期")
                    .infoWindowEnable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker));
            markers.add(markerOptions);
        }
        //添加marker
        aMap.addMarkers(markers, true);

        aMap.setOnMarkerClickListener(marker -> {
            //点击的marker是定位的小蓝点，不走动画和弹窗
            if ("Marker1".equals(marker.getId())) return true;
            //上一个标记marker
            if (targetMarker != null) {
                setMarkerAnim(targetMarker, 1.5f, 1, 1.5f, 1);
            }
            setMarkerAnim(marker, 1, 1.5f, 1, 1.5f);
            showPopWindow(marker.getTitle());
            return true;
        });
    }

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

    private CommonPopupWindow popupWindow;
    TextView tvTitle;
    TextView tvContent;
    TextView tvType;
    TextView tvReaders;
    TextView tvTime;

    private void showPopWindow(String title) {
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
                            ActivityUtils.startActivity(ProjectDetailActivity.class);
                        });
                    })
                    .create();
        }
        tvTitle.setText(title);
        tvContent.setText("项目简介项目简介项目简介项目简介项目简介项目简介项目简介项目简介");
        tvType.setText("勘察设计");
        tvReaders.setText(String.format(getString(R.string.format_num_of_readers), 12));
        tvTime.setText(String.format(getString(R.string.format_update_time), "2018-6-9 15:55"));

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
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initDataAndEvent() {

    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.fl_list})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.fl_list:
                ActivityUtils.startActivity(ProjectInfoListActivity.class);
                break;
        }
    }

}
