package com.shuangduan.zcy.view.projectinfo;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.manage.ShareManage;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.LocusRefreshEvent;
import com.shuangduan.zcy.model.event.RefreshViewLocusEvent;
import com.shuangduan.zcy.model.event.WarrantSuccessEvent;
import com.shuangduan.zcy.utils.PermissionUtils;
import com.shuangduan.zcy.view.mine.set.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.view.release.ReleaseProjectActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  工程信息详情
 * @time 2019/7/12 16:26
 * @change
 * @chang time
 * @class describe
 */
public class ProjectDetailActivity extends BaseActivity {
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    TextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.iv_subscribe)
    ImageView ivSubscribe;
    @BindView(R.id.tv_subscribe)
    TextView tvSubscribe;
    @BindView(R.id.fl_subscription)
    LinearLayout flSubscribe;
    @BindView(R.id.fl_release)
    LinearLayout flRelease;
    @BindView(R.id.ll_operate)
    LinearLayout llOperate;
    private AMap aMap = null;
    public ProjectDetailVm projectDetailVm;
    //分享管理
    private ShareManage shareManage;

    //更新修改密码
    public UpdatePwdPayVm updatePwdPayVm;

    //支付
    public CoinPayVm coinPayVm;

    private PermissionVm permissionVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);


        tvBarTitle.setText(getString(R.string.message_detail));
        ivBarRight.setImageResource(R.drawable.icon_share);
        tvBarRight.setVisibility(View.GONE);


        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);

        if (PermissionUtils.isOPen(this)) {
            permissionVm.getPermissionLocation(new RxPermissions(this));
        } else {
            showLocationDialog(PermissionVm.PERMISSION_LOCATION);
        }

        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_LOCATION) {
                init();

                projectDetailVm.latitudeLiveData.observe(this, s -> {
                    try {
                        aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(s.latitude, s.longitude)));
                    }catch (NullPointerException ignored){
                    }
                });
            } else if (integer == PermissionVm.PERMISSION_NO_LOCATION) {
                showLocationDialog(integer);
            }
        });



        Fragment[] fragments = new Fragment[4];
        fragments[0] = ProjectContentFragment.newInstance();
        fragments[1] = ProjectLocusFragment.newInstance();
        fragments[2] = ProjectReadFragment.newInstance();
        fragments[3] = ProjectConsumptionFragment.newInstance();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.project_detail));
        vp.setAdapter(adapter);
        tabLayout.setupWithViewPager(vp);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        llOperate.setVisibility(View.VISIBLE);
                        flSubscribe.setVisibility(View.VISIBLE);
                        flRelease.setVisibility(View.GONE);
                        break;
                    case 1:
                        llOperate.setVisibility(View.VISIBLE);
                        flSubscribe.setVisibility(View.GONE);
                        flRelease.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        llOperate.setVisibility(View.GONE);
                    case 3:
                        llOperate.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //初始化分享功能
        shareManage = ShareManage.newInstance(getApplicationContext());
        shareManage.init(this, ShareManage.SHARE_PROJECT_TYPE, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));

        //支付密码状态查询
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        coinPayVm = ViewModelProviders.of(this).get(CoinPayVm.class);
        coinPayVm.projectId = getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);

        updatePwdPayVm.stateLiveData.observe(this, pwdPayStateBean -> {
            int status = pwdPayStateBean.getStatus();
            SPUtils.getInstance().put(SpConfig.PWD_PAY_STATUS, status);
            if (status == 1) {
                goToPay();
            } else {
                ActivityUtils.startActivity(SetPwdPayActivity.class);
            }
        });

        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        //工程支付
        coinPayVm.contentPayLiveData.observe(this, coinPayResultBean -> {
            if (coinPayResultBean.getPay_status() == 1) {
                //加入工程圈讨论组（群聊）
                projectDetailVm.joinGroup(coinPayVm.projectId);
            } else {
                //余额不足
                addDialog(new CustomDialog(this)
                        .setIcon(R.drawable.icon_error)
                        .setTip(getString(R.string.no_balance))
                        .setOk(getString(R.string.recharge_dialog))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                ActivityUtils.startActivity(RechargeActivity.class);
                            }
                        })
                        .showDialog());
            }
        });

        //动态支付成功
        coinPayVm.locusPayLiveData.observe(this, coinPayResultBean -> {
            if (coinPayResultBean.getPay_status() == 1) {
                //加入工程圈讨论组（群聊）
                projectDetailVm.joinGroup(coinPayVm.projectId);
            } else {
                //余额不足
                addDialog(new CustomDialog(this)
                        .setIcon(R.drawable.icon_error)
                        .setTip(getString(R.string.no_balance))
                        .setOk(getString(R.string.recharge_dialog))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                ActivityUtils.startActivity(RechargeActivity.class);
                            }
                        })
                        .showDialog());
            }
        });


        coinPayVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });


        projectDetailVm = ViewModelProviders.of(this).get(ProjectDetailVm.class);
        projectDetailVm.init(getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
        projectDetailVm.titleLiveData.observe(this, s -> tvTitle.setText(s));
        projectDetailVm.locationLiveData.observe(this, s -> tvLocation.setText(s));
        projectDetailVm.collectionLiveData.observe(this, i -> {
            tvCollect.setText(getString(i == 1 ? R.string.collected : R.string.collection));
            ivCollect.setImageResource(i == 1 ? R.drawable.icon_collected : R.drawable.icon_collection);
        });
        projectDetailVm.subscribeLiveData.observe(this, i -> {
            tvSubscribe.setText(getString(i == 1 ? R.string.subscribed : R.string.unsubscribe));
            ivSubscribe.setImageResource(i == 1 ? R.drawable.icon_shopping_cart_select : R.drawable.icon_shopping_cart);
        });


        //查询是否可以进入讨论组返回结果
        projectDetailVm.projectMembersStatusData.observe(this, projectMembersStatusBean -> {
            RongIM.getInstance().startGroupChat(ProjectDetailActivity.this, projectMembersStatusBean.getGroupId(), projectMembersStatusBean.getGroupName());
        });


        //加入群聊返回结果
        projectDetailVm.joinGroupData.observe(this, item -> {
            ToastUtils.showShort(getString(R.string.buy_success));
            if (vp.getCurrentItem() == 0) {
                projectDetailVm.getDetail();
            } else {
                projectDetailVm.getTrack();
                //刷新已查看列表
                EventBus.getDefault().post(new RefreshViewLocusEvent());
            }
        });


        if (getIntent().getIntExtra(CustomConfig.LOCATION, 0) != 0) {
            int position = getIntent().getIntExtra(CustomConfig.LOCATION, 0);
            vp.setCurrentItem(position);
            tabLayout.getTabAt(position).select();
        }
    }


    private void showLocationDialog(int locationCode) {
        new CustomDialog(this)
                .setTip(locationCode == PermissionVm.PERMISSION_LOCATION ? "为了更好的为您服务，请您打开您的GPS!" : "为了更好的为您服务，请您开启您APP的定位权限！")
                .setCallBack(new BaseDialog.CallBack() {
                    @Override
                    public void cancel() {
                        finish();
                    }

                    @Override
                    public void ok(String s) {
                        if (locationCode == PermissionVm.PERMISSION_LOCATION) {
                            PermissionUtils.openGPS(ProjectDetailActivity.this);
                        } else if (locationCode == PermissionVm.PERMISSION_NO_LOCATION) {
                            PermissionUtils.openLocationPermission(ProjectDetailActivity.this);
                        }
                    }
                }).showDialog();
    }

    /**
     * 去支付
     */
    public void goToPay() {
        try {
            addDialog(new PayDialog(this)
                    .setSingleCallBack((item, position) -> {
                        coinPayVm.payProject(item);
                    })
                    .showDialog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payLocus() {
        addDialog(new PayDialog(this)
                .setSingleCallBack((item, position) -> {
                    coinPayVm.payLocus(item);
                })
                .showDialog());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            permissionVm.getPermissionLocation(new RxPermissions(this));
        } else {
            Tencent.onActivityResultData(requestCode, resultCode, data, shareManage.getQQListener());
        }
    }

    @Subscribe
    public void onEventWarrantSuccess(WarrantSuccessEvent event) {
        projectDetailVm.getDetail();
        projectDetailVm.getTrack();
        projectDetailVm.getViewTrack();
    }

    @Subscribe
    public void onEventReleaseSuccess(LocusRefreshEvent event) {
        projectDetailVm.getTrack();
        projectDetailVm.getViewTrack();
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.fl_collect, R.id.fl_error, R.id.fl_subscription, R.id.ll_chat, R.id.fl_release})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right://分享
                if (shareManage.getItem() == null) return;
                shareManage.initDialog(this, shareManage.getItem().getUrl(), shareManage.getItem().getTitle(),
                        shareManage.getItem().getDes(), shareManage.getItem().getImage(), shareManage.getBitmap(), "分享工程信息");
                break;
            case R.id.fl_collect://收藏
                projectDetailVm.collect();
                if (projectDetailVm.collectLiveData != null) {
                    projectDetailVm.collectLiveData.observe(this, o -> {
                        projectDetailVm.collectionLiveData.postValue(1);
                    });
                }
                if (projectDetailVm.cancelCollectLiveData != null) {
                    projectDetailVm.cancelCollectLiveData.observe(this, o -> {
                        projectDetailVm.collectionLiveData.postValue(0);
                    });
                }
                break;
            case R.id.fl_error://纠错
                bundle.putInt(CustomConfig.PROJECT_ID, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
                ActivityUtils.startActivity(bundle, ProjectErrorActivity.class);
                break;
            case R.id.ll_chat://进入讨论组
                projectDetailVm.membersStatus(getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
                break;
            case R.id.fl_subscription://认购
                switch (projectDetailVm.subscribeLiveData.getValue()) {
                    case 1:
                        bundle.putInt(CustomConfig.PROJECT_ID, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
                        ActivityUtils.startActivity(bundle, SubInfoActivity.class);
                        break;
                    case 0:
                        bundle.putInt(CustomConfig.PROJECT_ID, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
                        ActivityUtils.startActivity(bundle, GoToSubActivity.class);
                        break;
                }
                break;
            case R.id.fl_release://发布动态
                bundle.putInt(CustomConfig.RELEASE_TYPE, 2);
                bundle.putInt(CustomConfig.PROJECT_ID, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
                bundle.putString(CustomConfig.PROJECT_NAME, tvTitle.getText().toString());
                ActivityUtils.startActivity(bundle, ReleaseProjectActivity.class);
                break;
        }
    }

    //初始化
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();


            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    startJumpAnimation();
                }
            });

            aMap.setOnMapLoadedListener(this::addMarkerInScreenCenter);
        }
    }

    /**
     * 设置一些aMap的属性
     */
    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setRotateGesturesEnabled(false);//设置地图不能旋转
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setAllGesturesEnabled(false);//关闭所有手势
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置是否允许显示缩放按钮
    }

    private Marker locationMarker;

    private void addMarkerInScreenCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        locationMarker.setZIndex(1);
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= ConvertUtils.dp2px(50);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(input -> {
                // 模拟重加速度的interpolator
                if (input <= 0.5) {
                    return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                } else {
                    return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();

        } else {
            LogUtils.i("ama", "screenMarker is null");
        }
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mapView != null)
            mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mapView != null)
            mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mapView != null)
            mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mapView != null)
            mapView.onSaveInstanceState(outState);
    }
}
