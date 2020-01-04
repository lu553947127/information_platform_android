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

    /*身份认证部分*/
    public static final int REQUEST_CODE_CHOOSE_AUTHENTICATION = 100;
    public static final int REQUEST_CODE_AUTHENTICATION = 101;
    /*用户头像部分*/
    public static final int REQUEST_CODE_CHOOSE_HEAD = 200;
    public static final int REQUEST_CODE_HEAD = 201;
    /*银行卡上传部分*/
    public static final int REQUEST_CODE_CHOOSE_BANK_CARD = 300;
    public static final int REQUEST_CODE_BANK_CARD = 301;

    /*拍照*/
    public static final int PERMISSION_CAMERA = 1;
    public static final int PERMISSION_CAMERA_NO = 11;
    /*内存*/
    public static final int PERMISSION_STORAGE = 2;
    public static final int PERMISSION_STORAGE_NO = 22;
    /*定位*/
    public static final int PERMISSION_LOCATION = 3;

    public static final int PHOTO = 222;

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
                    }
                }, LogUtils::i, () -> LogUtils.i("OnComplete"));
    }

    public MutableLiveData<Integer> getLiveData() {
        return liveData;
    }
}
