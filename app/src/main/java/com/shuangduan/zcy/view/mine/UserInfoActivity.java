package com.shuangduan.zcy.view.mine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BusinessExpDialog;
import com.shuangduan.zcy.dialog.PhotoDialog;
import com.shuangduan.zcy.dialog.SexDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.EmailEvent;
import com.shuangduan.zcy.model.event.MobileEvent;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.login.MobileVerificationActivity;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  基本信息界面
 * @time 2019/7/5 17:01
 * @change
 * @chang time
 * @class describe
 */
public class UserInfoActivity extends BaseActivity implements BaseDialog.PhotoCallBack {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_sex)
    AppCompatTextView tvSex;
    @BindView(R.id.tv_mobile)
    AppCompatTextView tvMobile;
    @BindView(R.id.tv_id_card)
    AppCompatTextView tvIdCard;
    @BindView(R.id.tv_email)
    AppCompatTextView tvEmail;
    @BindView(R.id.tv_company)
    AppCompatTextView tvCompany;
    @BindView(R.id.tv_office)
    AppCompatTextView tvOffice;
    @BindView(R.id.tv_business_area)
    AppCompatTextView tvBusinessArea;
    @BindView(R.id.tv_business_exp)
    AppCompatTextView tvBusinessExp;
    @BindView(R.id.edt_production)
    AppCompatEditText edtProduction;

    private int sex = -1;
    private PermissionVm permissionVm;
    private RxPermissions rxPermissions;
    private UserInfoVm userInfoVm;
    private UploadPhotoVm uploadPhotoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));

        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.information();
        userInfoVm.informationLiveData.observe(this, userInfoBean -> {
            ivUser.setImageResource(R.drawable.default_head);
            tvName.setText(userInfoBean.getUsername());
            switch (userInfoBean.getSex()){
                case 1:
                    tvSex.setText(getString(R.string.man));
                    break;
                case 2:
                    tvSex.setText(getString(R.string.woman));
                    break;
                case 0:
                    tvSex.setText("");
                    break;
            }
            tvMobile.setText(userInfoBean.getTel());
            tvIdCard.setText(userInfoBean.getIdentity_card());
            tvEmail.setText(userInfoBean.getEmail());
            tvCompany.setText(userInfoBean.getCompany());
            tvOffice.setText(userInfoBean.getPosition());
            tvBusinessArea.setText(userInfoBean.getBusiness_city());
            if (userInfoBean.getExperience() >= 1 && userInfoBean.getExperience() <= 4)
                tvBusinessExp.setText(getResources().getStringArray(R.array.experience_list)[userInfoBean.getExperience() - 1]);
            edtProduction.setText(userInfoBean.getManaging_products());
        });

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

        uploadPhotoVm.changeLiveData.observe(this, integer -> {
            uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
                ImageLoader.load(this, new ImageConfig.Builder()
                        .url(uploadBean.getThumbnail())
                        .imageView(ivUser)
                        .build());
                userInfoVm.updateAvatar(uploadBean.getImage_id());
            });
            uploadPhotoVm.mPageStateLiveData.observe(this, s -> {
                if (s != PageState.PAGE_LOADING){
                    hideLoading();
                }
            });
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_user, R.id.fl_name, R.id.fl_sex, R.id.fl_mobile, R.id.fl_email, R.id.fl_id_card,
            R.id.fl_company, R.id.fl_office, R.id.fl_business_area, R.id.fl_business_exp})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_user:
                new PhotoDialog(this)
                        .setPhotoCallBack(this)
                        .showDialog();
                break;
            case R.id.fl_name:
                ActivityUtils.startActivity(UpdateNameActivity.class);
                break;
            case R.id.fl_sex:
                new SexDialog(this)
                        .setSex(sex)
                        .setOnSexSelectListener(new SexDialog.OnSexSelectListener() {
                            @Override
                            public void man() {
                                tvSex.setText(getString(R.string.man));
                                sex = 0;
                            }

                            @Override
                            public void woman() {
                                tvSex.setText(getString(R.string.woman));
                                sex = 1;
                            }
                        })
                        .showDialog();
                break;
            case R.id.fl_mobile:
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypePhone);
                ActivityUtils.startActivity(bundle, MobileVerificationActivity.class);
                break;
            case R.id.fl_email:
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypeEmail);
                ActivityUtils.startActivity(bundle, MobileVerificationActivity.class);
                break;
            case R.id.fl_id_card:
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
            case R.id.fl_company:
                bundle.putString(CustomConfig.SEARCH_TYPE, CustomConfig.searchTypeCompany);
                ActivityUtils.startActivity(bundle, CompanySearchActivity.class);
                break;
            case R.id.fl_office:
                bundle.putString(CustomConfig.SEARCH_TYPE, CustomConfig.searchTypeOffice);
                ActivityUtils.startActivity(bundle, CompanySearchActivity.class);
                break;
            case R.id.fl_business_area:
                ActivityUtils.startActivity(BusinessAreaActivity.class);
                break;
            case R.id.fl_business_exp:
                new BusinessExpDialog(this)
                        .setSingleCallBack((item, position) -> tvBusinessExp.setText(item))
                        .showDialog();
                break;
        }
    }

    private void upload(String path){
        uploadPhotoVm.upload(path);
        showLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionVm.REQUEST_CODE_CHOOSE_HEAD && resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
            upload(mSelected.get(0));
        }

        if (requestCode == PermissionVm.REQUEST_CODE_HEAD && resultCode == RESULT_OK) {
            upload(MatisseCamera.obtainPathResult());
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

    @Subscribe()
    void updateUserName(UserNameEvent event){
        tvName.setText(event.username);
    }

    @Subscribe()
    void updateMobile(MobileEvent event){
        tvMobile.setText(event.mobile);
    }

    @Subscribe()
    void updateEmail(EmailEvent event){
        tvEmail.setText(event.email);
    }
}
