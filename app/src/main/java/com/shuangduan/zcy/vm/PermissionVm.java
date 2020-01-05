package com.shuangduan.zcy.vm;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.base.BaseViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.vm
 * @class describe
 * @time 2019/7/8 15:19
 * @change
 * @chang time
 * @class describe
 */
public class PermissionVm extends BaseViewModel {

    /*相机*/
    public static final int CAMERA = 1111;
    /*相册*/
    public static final int PHOTO = 2222;

    /*拍照*/
    public static final int PERMISSION_CAMERA = 77;
    public static final int PERMISSION_CAMERA_NO = 777;
    /*内存*/
    public static final int PERMISSION_STORAGE = 88;
    public static final int PERMISSION_STORAGE_NO = 888;
    /*定位*/
    public static final int PERMISSION_LOCATION = 99;
    public static final int PERMISSION_NO_LOCATION = 999;

    private MutableLiveData<Integer> liveData;

    public PermissionVm() {
        liveData = new MutableLiveData<>();
    }

    @SuppressLint("CheckResult")
    public void getPermissionCamera(RxPermissions rxPermissions){
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        liveData.postValue(PERMISSION_CAMERA);
                    }else {
                        liveData.postValue(PERMISSION_CAMERA_NO);
                    }
                }, LogUtils::i, () -> LogUtils.i("OnComplete"));
    }

    @SuppressLint("CheckResult")
    public void getPermissionAlbum(RxPermissions rxPermissions){
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean){
                       liveData.postValue(PERMISSION_STORAGE);
                    }else {
                        liveData.postValue(PERMISSION_STORAGE_NO);
                    }
                }, LogUtils::i, () -> LogUtils.i("OnComplete"));
    }

    
    @SuppressLint("CheckResult")
    public void getPermissionLocation(RxPermissions rxPermissions){
        rxPermissions.request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        liveData.postValue(PERMISSION_LOCATION);
                    }else {
                        liveData.postValue(PERMISSION_NO_LOCATION);
                    }
                }, LogUtils::i, () -> LogUtils.i("OnComplete"));

        
    }

    public MutableLiveData<Integer> getLiveData() {
        return liveData;
    }
}
