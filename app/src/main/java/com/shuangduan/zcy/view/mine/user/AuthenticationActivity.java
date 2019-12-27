package com.shuangduan.zcy.view.mine.user;

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
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseBottomSheetDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.image.CompressUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.photo.CameraActivity;
import com.shuangduan.zcy.vm.AuthenticationVm;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class 身份认证,上传名片
 * @time 2019/7/8 13:25
 * @change
 * @chang time
 * @class describe
 */
public class AuthenticationActivity extends BaseActivity{

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
    private AuthenticationVm authenticationVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_authentication;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarRight.setText(getString(R.string.save));
        type = getIntent().getStringExtra(CustomConfig.UPLOAD_TYPE);
        String toast_type = getIntent().getStringExtra(CustomConfig.AUTHENTICATION_TYPE);
        //判断当前是否为身份证上传或为名片上传
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
        //判断验证身份证显示的土司类型
        switch (toast_type){
            case CustomConfig.ID_USER_INFO://用户详情跳转编辑身份证
                break;
            case CustomConfig.PROJECT_INFO://工程信息查看详情
            case CustomConfig.PROJECT_DYNAMIC://工程信息动态查看详情
            case CustomConfig.SUPPLIER_INFO://优质供应商列表查看详情
                ToastUtils.showShort("请实名认证后查看详情");
                break;
            case CustomConfig.PROJECT_SUBSCRIPTION://工程信息认购
                ToastUtils.showShort("请实名认证后认购");
                break;
            case CustomConfig.NEED_RELATIONSHIP://需求广场  发布找资源
                ToastUtils.showShort("请实名认证后发布找资源");
                break;
            case CustomConfig.RELEASE_MESSAGE://发布信息
                ToastUtils.showShort("请实名认证后发布信息");
                break;
            case CustomConfig.BALANCE_CASH://余额 零钱提现
                break;
            case CustomConfig.BALANCE_BANK://余额 银行卡
                break;
            case CustomConfig.BALANCE_CASH_RECORD://余额 提现记录
                break;
            case CustomConfig.PAYMENT_PASSWORD://支付密码页
                break;
            case CustomConfig.SET_PAYMENT_PASSWORD://设置支付密码页
                break;
        }
        authenticationVm = ViewModelProviders.of(this).get(AuthenticationVm.class);
        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        rxPermissions = new RxPermissions(this);
        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_CAMERA){
                startActivityForResult(new Intent(this, CameraActivity.class), 100);
            }else if (integer == PermissionVm.PERMISSION_STORAGE){
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .showSingleMediaType(true)
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .captureStrategy(new CaptureStrategy(true, "com.shuangduan.zcy.fileprovider"))
                        .imageEngine(new Glide4Engine())
                        .forResult(PermissionVm.REQUEST_CODE_CHOOSE_AUTHENTICATION);
            }
        });

        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
            if (uploadPhotoVm.type == UploadPhotoVm.ID_CARD_POSITIVE){
                authenticationVm.image_front = uploadBean.getSource();
                ImageLoader.load(this, new ImageConfig.Builder().url(uploadBean.getSource()).imageView(ivIdCardPositive).build());
            }else if (uploadPhotoVm.type == UploadPhotoVm.ID_CARD_NEGATIVE){
                authenticationVm.image_reverse_site = uploadBean.getSource();
                ImageLoader.load(this, new ImageConfig.Builder().url(uploadBean.getSource()).imageView(ivIdCardNegative).build());
            }
        });
        authenticationVm.authenticationLiveData.observe(this, o -> {
            SPUtils.getInstance().put(SpConfig.IS_VERIFIED, 2);
            ToastUtils.showShort("认证成功");
            finish();
        });
        uploadPhotoVm.mPageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        authenticationVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        authenticationVm.authentication();
        authenticationVm.authenticationStatusLiveData.observe(this,authenBean -> {
            ImageLoader.load(this, new ImageConfig.Builder().url(authenBean.getIdentity_image_front()).errorPic(R.drawable.id_card_positive).imageView(ivIdCardPositive).build());
            ImageLoader.load(this, new ImageConfig.Builder().url(authenBean.getIdentity_image_reverse_site()).errorPic(R.drawable.id_card_negative).imageView(ivIdCardNegative).build());
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.iv_id_card_positive, R.id.iv_id_card_negative})
    void onClick(View view){
        BaseBottomSheetDialog baseBottomSheetDialog =new BaseBottomSheetDialog(this,rxPermissions,permissionVm);
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                int isVerified = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED, 0);
                if (isVerified != 2){
                    switch (type){
                        case CustomConfig.uploadTypeIdCard://身份认证
                            authenticationVm.idCard();
                            break;
                        case CustomConfig.uploadTypeBusinessCard://上传名片
                            break;
                    }
                }else {
                    ToastUtils.showShort("已认证，请勿重复认证");
                }
                break;
            case R.id.iv_id_card_positive:
                uploadPhotoVm.type = UploadPhotoVm.ID_CARD_POSITIVE;
                baseBottomSheetDialog.showPhotoDialog();
                break;
            case R.id.iv_id_card_negative:
                uploadPhotoVm.type = UploadPhotoVm.ID_CARD_NEGATIVE;
                baseBottomSheetDialog.showPhotoDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionVm.REQUEST_CODE_CHOOSE_AUTHENTICATION && resultCode == RESULT_OK) {
            if (MatisseCamera.isAndroidQ) {
                LogUtils.e(Matisse.obtainResult(Objects.requireNonNull(data)).get(0));
                uploadPhotoVm.upload(CompressUtils.getRealFilePath(this,Matisse.obtainResult(data).get(0)));
            }else {
                LogUtils.e(Matisse.obtainPathResult(Objects.requireNonNull(data)).get(0));
                uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
            }
        }

        if (resultCode == 101) {
            uploadPhotoVm.upload(data.getStringExtra("path"));
        }
    }
}
