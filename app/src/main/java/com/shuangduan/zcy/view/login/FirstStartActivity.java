package com.shuangduan.zcy.view.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.view.WebViewActivity;
import com.shuangduan.zcy.weight.MaterialIndicator;
import com.shuangduan.zcy.weight.SwitchView;
import com.zcy.framelibrary.dialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.login
 * @ClassName: FirstStartActivity
 * @Description: 引导页
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/17 9:17
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/17 9:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FirstStartActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, SwitchView.OnStateChangedListener{

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.material_indicator)
    MaterialIndicator materialIndicator;
    private boolean isScrolled = false;
    private List<View> viewList=new ArrayList<>();
    private AlertDialog dialog,dialogPermission;
    private SwitchView svLocation,svStorage,svCamera;
    private int isLocation = 1;
    private int isStorage = 1;
    private int isCamera = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_first_start;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.activity_first_start_one, null);
        View view2 = inflater.inflate(R.layout.activity_first_start_two, null);
        View view3 = inflater.inflate(R.layout.activity_first_start_three, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(materialIndicator);
        materialIndicator.setAdapter(Objects.requireNonNull(viewPager.getAdapter()));
        viewPager.setOnPageChangeListener(this);
        view1.findViewById(R.id.tv_next_two).setOnClickListener(this);
        view2.findViewById(R.id.tv_next_two).setOnClickListener(this);
        view3.findViewById(R.id.tv_next_three).setOnClickListener(this);
        view3.findViewById(R.id.rl_experience).setOnClickListener(this);
        getUserAgreementDialog();
    }

    //权限弹窗
    private void getPermissionDialog() {
        dialogPermission = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_permission) //Dialog View
                .setCancelable(false) //点击空白是否取消
                .setText(R.id.tv_title, "“易基建”正在申请以下权限")//控件的文本
                .setOnClickListener(R.id.tv_open, v -> {//开启
                    //检测权限是否开启
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                                .PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                                        .PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager
                                        .PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                                        .PERMISSION_GRANTED ){
                            dialogPermission.dismiss();
                        } else {
                            dialogPermission.dismiss();
                            //不具有获取权限，需要进行权限申请
                            if (isLocation == 1 && isStorage == 1 && isCamera == 1){
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                            }else if (isLocation == 1 && isStorage == 1 && isCamera == 0){
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                            }else if (isLocation == 1 && isStorage == 0 && isCamera == 1){
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                            }else if (isLocation == 1 && isStorage == 0 && isCamera == 0){
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                            }else if (isLocation == 0 && isStorage == 1 && isCamera == 1){
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
                            }else if (isLocation == 0 && isStorage == 1 && isCamera == 0){
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
                            }else if (isLocation == 0 && isStorage == 0 && isCamera == 1){
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.CAMERA}, 111);
                            }else {
                                dialogPermission.dismiss();
                            }
                        }
                    } else {
                        dialogPermission.dismiss();
                    }
                })
                .setOnClickListener(R.id.tv_close, view -> {//暂不
                    dialogPermission.dismiss();
                })
                .setGravity(Gravity.CENTER)//弹窗显示位置 默认居中
                .fullWidth()//全屏
                .show();

        svLocation = dialogPermission.getView(R.id.sv_location);
        svStorage = dialogPermission.getView(R.id.sv_storage);
        svCamera = dialogPermission.getView(R.id.sv_camera);
        svLocation.setOnStateChangedListener(this);
        svStorage.setOnStateChangedListener(this);
        svCamera.setOnStateChangedListener(this);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        //权限拒绝开启返回监听
//        if (requestCode == 111) {
//            if (grantResults.length > 0) {
//                for (int result : grantResults) {
//                    if (result != PackageManager.PERMISSION_GRANTED) {
//                        //执行相应的逻辑
//                        getPermissionDialog();
//                        return;
//                    }
//                }
//            }
//        }
//    }

    //用户协议 隐私协议弹窗
    private void getUserAgreementDialog() {

        String content = "欢迎您访问易基建APP，当您使用我们的产品有服务时，我们可能会手机和使用您的相关信息。在使用我们的产品或服务前，请您务必仔细阅读《紫菜云隐私协议政策》了解我们对您信息的处理规则，包括：\n <br>" +
                "我们如何收集和使用您的个人信息\n <br>" +
                "我们如何共享、转让、公开披露用户的个人信息\n <br>" +
                "我们如何存储收集用户信息\n <br>" +
                "我们如何保证收集用户信息安全\n <br>" +
                "您如何管理个人信息\n <br>" +
                "未成年人保护";

        String content2 = "请点击“我知道了”开始使用我们的产品和服务，我们将按照法律法规要求，采取严格的安全保护措施，保护您的个人信息安全！";

        Bundle bundle = new Bundle();
        dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_user_agreement) //Dialog View
                .setCancelable(false) //点击空白是否取消
                .setText(R.id.tv_title, "紫菜云隐私协议政策")//控件的文本
                .setText(R.id.tv_content, Html.fromHtml(content))
                .setText(R.id.tv_content2, content2)
                .setOnClickListener(R.id.tv_user, v -> {//隐私政策
                    bundle.putString("register", "register");
                    ActivityUtils.startActivity(bundle, WebViewActivity.class);
                })
                .setOnClickListener(R.id.tv_user2, v -> {//用户注册协议
                    bundle.putString("register", "privacy");
                    ActivityUtils.startActivity(bundle, WebViewActivity.class);
                })
                .setOnClickListener(R.id.tv_release, view -> {//我知道了
                    dialog.dismiss();
                    getPermissionDialog();
                })
                .setGravity(Gravity.CENTER)//弹窗显示位置 默认居中
                .fullWidth()//全屏
                .show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
            case 1:
                materialIndicator.setVisibility(View.VISIBLE);
                break;
            case 2:
                materialIndicator.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                //滑动跳转
                if (viewPager.getCurrentItem() == Objects.requireNonNull(viewPager.getAdapter()).getCount() - 1 && !isScrolled) {
                    SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
                    ActivityUtils.startActivity(LoginActivity.class);
                    finish();
                }
                isScrolled = true;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_next_two:
            case R.id.rl_experience:
            case R.id.tv_next_three:
                SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
                ActivityUtils.startActivity(LoginActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void toggleToOn(SwitchView view) {
        switch (view.getId()){
            case R.id.sv_location:
                isLocation = 1;
                svLocation.setOpened(true);
                break;
            case R.id.sv_storage:
                isStorage = 1;
                svStorage.setOpened(true);
                break;
            case R.id.sv_camera:
                isCamera = 1;
                svCamera.setOpened(true);
                break;
        }
    }

    @Override
    public void toggleToOff(SwitchView view) {
        switch (view.getId()){
            case R.id.sv_location:
                isLocation = 0;
                svLocation.setOpened(false);
                break;
            case R.id.sv_storage:
                isStorage = 0;
                svStorage.setOpened(false);
                break;
            case R.id.sv_camera:
                isCamera = 0;
                svCamera.setOpened(false);
                break;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //这个方法是返回总共有几个滑动的页面（）
            return viewList == null ? 0 : viewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            //该方法判断是否由该对象生成界面。
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            //这个方法从viewPager中移动当前的view。（划过的时候）
            container.removeView(viewList.get(position));
        }
    }
}
