package com.shuangduan.zicaicloudplatform.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.app.CustomConfig;
import com.shuangduan.zicaicloudplatform.app.SpConfig;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;
import com.shuangduan.zicaicloudplatform.dialog.BaseDialog;
import com.shuangduan.zicaicloudplatform.dialog.BusinessExpDialog;
import com.shuangduan.zicaicloudplatform.dialog.PhotoDialog;
import com.shuangduan.zicaicloudplatform.dialog.SexDialog;
import com.shuangduan.zicaicloudplatform.model.event.EmailEvent;
import com.shuangduan.zicaicloudplatform.model.event.MobileEvent;
import com.shuangduan.zicaicloudplatform.model.event.UserNameEvent;
import com.shuangduan.zicaicloudplatform.utils.matisse.Glide4Engine;
import com.shuangduan.zicaicloudplatform.utils.matisse.MatisseCamera;
import com.shuangduan.zicaicloudplatform.vm.PhotoVm;
import com.shuangduan.zicaicloudplatform.weight.CircleImageView;
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

    private int sex = -1;
    private PhotoVm photoVm;
    private RxPermissions rxPermissions;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));
        ivUser.setImageResource(R.drawable.default_head);
        tvName.setText("王某某");
        tvSex.setText("王某某");
        tvMobile.setText("王某某");
        tvIdCard.setText("王某某");
        tvEmail.setText("王某某");
        tvCompany.setText("王某某");
        tvOffice.setText("王某某");
        tvBusinessArea.setText("济南市济南市济南市济南市济南市济南市济南市济南市济南市");
        tvBusinessExp.setText("王某某");

        rxPermissions = new RxPermissions(this);
        photoVm = ViewModelProviders.of(this).get(PhotoVm.class);
        photoVm.getPhotoLiveData().observe(this, integer -> {
            if (integer == 1){
                MatisseCamera.from(this)
                        .forResult(PhotoVm.REQUEST_CODE_AUTHENTICATION, "com.shuangduan.zicaicloudplatform.fileprovider");
            }else if (integer == 2){
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .showSingleMediaType(true)
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .captureStrategy(
                                new CaptureStrategy(true, "com.shuangduan.zicaicloudplatform.fileprovider"))
                        .imageEngine(new Glide4Engine())
                        .forResult(PhotoVm.REQUEST_CODE_CHOOSE_AUTHENTICATION);
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_user, R.id.tv_name, R.id.tv_sex, R.id.tv_mobile, R.id.tv_email, R.id.tv_id_card,
            R.id.tv_company, R.id.tv_office, R.id.tv_business_area, R.id.tv_business_exp})
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
            case R.id.tv_name:
                ActivityUtils.startActivity(UpdateNameActivity.class);
                break;
            case R.id.tv_sex:
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
            case R.id.tv_mobile:
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypePhone);
                ActivityUtils.startActivity(bundle, MobileVerificationActivity.class);
                break;
            case R.id.tv_email:
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypeEmail);
                ActivityUtils.startActivity(bundle, MobileVerificationActivity.class);
                break;
            case R.id.tv_id_card:
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
            case R.id.tv_company:
                bundle.putString(CustomConfig.SEARCH_TYPE, CustomConfig.searchTypeCompany);
                ActivityUtils.startActivity(bundle, CompanySearchActivity.class);
                break;
            case R.id.tv_office:
                bundle.putString(CustomConfig.SEARCH_TYPE, CustomConfig.searchTypeOffice);
                ActivityUtils.startActivity(bundle, CompanySearchActivity.class);
                break;
            case R.id.tv_business_area:
                ActivityUtils.startActivity(BusinessAreaActivity.class);
                break;
            case R.id.tv_business_exp:
                new BusinessExpDialog(this)
                        .setSingleCallBack(item -> tvBusinessExp.setText(item))
                        .showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoVm.REQUEST_CODE_CHOOSE_HEAD && resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
//            photoVm.upLoadImg(mSelected.get(0));
        }

        if (requestCode == PhotoVm.REQUEST_CODE_HEAD && resultCode == RESULT_OK) {
//            photoVm.upLoadImg(MatisseCamera.obtainPathResult());
            LogUtils.i(MatisseCamera.obtainPathResult(), MatisseCamera.obtainUriResult());
        }
    }

    @Override
    public void camera() {
        photoVm.getPermissionCamera(rxPermissions);
    }

    @Override
    public void album() {
        photoVm.getPermissionAlbum(rxPermissions);
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
