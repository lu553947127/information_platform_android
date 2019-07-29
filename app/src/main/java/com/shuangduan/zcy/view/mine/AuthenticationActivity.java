package com.shuangduan.zcy.view.mine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.PhotoDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  身份认证,上传名片
 * @time 2019/7/8 13:25
 * @change
 * @chang time
 * @class describe
 */
public class AuthenticationActivity extends BaseActivity implements BaseDialog.PhotoCallBack {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_id_card_positive)
    ImageView ivIdCardPositive;
    @BindView(R.id.iv_id_card_negative)
    ImageView ivIdCardNegative;
    @BindView(R.id.tv_id_card_positive)
    TextView tvIdCardPositive;
    @BindView(R.id.tv_id_card_negative)
    TextView tvIdCardNegative;

    private String type;
    private PermissionVm permissionVm;
    private RxPermissions rxPermissions;
    private UploadPhotoVm uploadPhotoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarRight.setText(getString(R.string.save));
        type = getIntent().getStringExtra(CustomConfig.UPLOAD_TYPE);
        switch (type){
            case CustomConfig.uploadTypeIdCard://身份认证
                tvBarTitle.setText(getString(R.string.authentication));
                tvIdCardPositive.setText(getString(R.string.id_card_positive));
                tvIdCardNegative.setText(getString(R.string.id_card_negative));
                ivIdCardPositive.setImageResource(R.drawable.id_card_positive);
                ivIdCardNegative.setImageResource(R.drawable.id_card_negative);
                break;
            case CustomConfig.uploadTypeBusinessCard://上传名片
                tvBarTitle.setText(getString(R.string.upload_business_card));
                tvIdCardPositive.setText(getString(R.string.business_card_positive));
                tvIdCardNegative.setText(getString(R.string.business_card_negative));
                ivIdCardPositive.setImageResource(R.drawable.business_card);
                ivIdCardNegative.setImageResource(R.drawable.business_card);
                break;
        }

        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        rxPermissions = new RxPermissions(this);
        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_CAMERA){
                MatisseCamera.from(this)
                        .forResult(PermissionVm.REQUEST_CODE_HEAD, "com.shuangduan.zcy.fileprovider");
            }else if (integer == PermissionVm.PERMISSION_STORAGE){
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .showSingleMediaType(true)
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .captureStrategy(
                                new CaptureStrategy(true, "com.shuangduan.zcy.fileprovider"))
                        .imageEngine(new Glide4Engine())
                        .forResult(PermissionVm.REQUEST_CODE_CHOOSE_HEAD);
            }
        });

        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
//            userInfoVm.setImg(uploadBean.getThumbnail());
//            userInfoVm.updateAvatar(uploadBean.getSource());
        });
        uploadPhotoVm.mPageStateLiveData.observe(this, s -> {
            if (s != PageState.PAGE_LOADING){
                hideLoading();
            }else {
                showLoading();
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.iv_id_card_positive, R.id.iv_id_card_negative})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                switch (type){
                    case CustomConfig.uploadTypeIdCard://身份认证
                        break;
                    case CustomConfig.uploadTypeBusinessCard://上传名片
                        break;
                }
                break;
            case R.id.iv_id_card_positive:
                new PhotoDialog(this)
                        .setPhotoCallBack(this)
                        .showDialog();
                break;
            case R.id.iv_id_card_negative:
                new PhotoDialog(this)
                        .setPhotoCallBack(this)
                        .showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionVm.REQUEST_CODE_CHOOSE_AUTHENTICATION && resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
            uploadPhotoVm.upload(mSelected.get(0));
        }

        if (requestCode == PermissionVm.REQUEST_CODE_AUTHENTICATION && resultCode == RESULT_OK) {
            uploadPhotoVm.upload(MatisseCamera.obtainPathResult());
        }
    }

    @Override
    public void camera() {
        permissionVm.getPermissionCamera(rxPermissions);
    }

    @Override
    public void album() {
        permissionVm.getPermissionAlbum(rxPermissions);
    }
}
