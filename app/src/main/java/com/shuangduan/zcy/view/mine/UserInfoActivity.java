package com.shuangduan.zcy.view.mine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BusinessExpDialog;
import com.shuangduan.zcy.dialog.PhotoDialog;
import com.shuangduan.zcy.dialog.SexDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.CityEvent;
import com.shuangduan.zcy.model.event.CompanyEvent;
import com.shuangduan.zcy.model.event.EmailEvent;
import com.shuangduan.zcy.model.event.MobileEvent;
import com.shuangduan.zcy.model.event.MultiAreaEvent;
import com.shuangduan.zcy.model.event.OfficeEvent;
import com.shuangduan.zcy.model.event.ProductionEvent;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.MultiAreaActivity;
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
import butterknife.ButterKnife;
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
    @BindView(R.id.tv_production)
    TextView tvProduction;
    @BindView(R.id.tv_production_tip)
    TextView tvProductionTip;
    @BindView(R.id.tv_add_friend)
    TextView tvAddFriend;

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
        int uid = getIntent().getIntExtra(CustomConfig.UID, 0);

        if (SPUtils.getInstance().getInt(SpConfig.USER_ID)!=uid){
            ivUser.setClickable(false);
            findViewById(R.id.fl_name).setClickable(false);
            tvName.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_sex).setClickable(false);
            tvSex.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_mobile).setClickable(false);
            tvMobile.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_id_card).setClickable(false);
            tvIdCard.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_email).setClickable(false);
            tvEmail.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_company).setClickable(false);
            tvCompany.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_office).setClickable(false);
            tvOffice.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_business_area).setClickable(false);
            tvBusinessArea.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.fl_business_exp).setClickable(false);
            tvBusinessExp.setCompoundDrawables(null,null,null,null);
            tvProductionTip.setClickable(false);
            tvProductionTip.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.tv_production).setClickable(false);
        }

        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.uid = uid;
        userInfoVm.informationLiveData.observe(this, userInfoBean -> {
            SPUtils.getInstance().put(SpConfig.USERNAME, userInfoBean.getUsername());
            SPUtils.getInstance().put(SpConfig.MOBILE, userInfoBean.getTel());
            tvName.setText(userInfoBean.getUsername());
            tvMobile.setText(userInfoBean.getTel());
            tvIdCard.setText(userInfoBean.getIdentity_card());
            tvEmail.setText(userInfoBean.getEmail());
            tvCompany.setText(userInfoBean.getCompany());
            tvOffice.setText(userInfoBean.getPosition());
            tvBusinessArea.setText(userInfoBean.getBusiness_city());
            if (userInfoBean.getExperience() >= 1 && userInfoBean.getExperience() <= 4)
                tvBusinessExp.setText(getResources().getStringArray(R.array.experience_list)[userInfoBean.getExperience() - 1] + "年");
            tvProduction.setText(userInfoBean.getManaging_products());
            if (userInfoBean.getApply_status()!=null && userInfoBean.getApply_status().equals("1")){
                tvAddFriend.setVisibility(View.VISIBLE);
            }else {
                tvAddFriend.setVisibility(View.GONE);
            }
        });
        userInfoVm.avatarLiveData.observe(this, s -> {
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(s)
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
        });
        userInfoVm.sexLiveData.observe(this, integer -> {
            switch (integer) {
                case 1:
                    tvSex.setText(getString(R.string.man));
                    break;
                case 2:
                    tvSex.setText(getString(R.string.woman));
                    break;
                case 0:
                    tvSex.setText(getString(R.string.select_not));
                    break;
            }
        });
        userInfoVm.infoLiveData.observe(this, o -> {
            switch (userInfoVm.changeType) {
                case 1:
                    userInfoVm.updateImage();
                    break;
                case 2:
                    userInfoVm.updateSex();
                    break;
            }
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        userInfoVm.areaLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.edit_success));
            tvBusinessArea.setText(userInfoVm.multiAreaLiveData.getValue().getStringResult());
        });
        userInfoVm.expLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.edit_success));
            if (userInfoVm.experience.getValue() >= 1 && userInfoVm.experience.getValue() <= 4)
                tvBusinessExp.setText(getResources().getStringArray(R.array.experience_list)[userInfoVm.experience.getValue() - 1] + "年");
        });

        rxPermissions = new RxPermissions(this);
        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_CAMERA) {
                MatisseCamera.from(this)
                        .forResult(PermissionVm.REQUEST_CODE_HEAD, "com.shuangduan.zcy.fileprovider");
            } else if (integer == PermissionVm.PERMISSION_STORAGE) {
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
            userInfoVm.setImg(uploadBean.getThumbnail());
            userInfoVm.updateAvatar(uploadBean.getSource());
        });
        uploadPhotoVm.mPageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        userInfoVm.information();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_user, R.id.fl_name, R.id.fl_sex, R.id.fl_mobile, R.id.fl_email, R.id.fl_id_card,
            R.id.fl_company, R.id.fl_office, R.id.fl_business_area, R.id.fl_business_exp, R.id.tv_production_tip, R.id.tv_production})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
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
                        .setSex(userInfoVm.sexLiveData.getValue())
                        .setOnSexSelectListener(new SexDialog.OnSexSelectListener() {
                            @Override
                            public void man() {
                                userInfoVm.setSex(1);
                                userInfoVm.updateSex(1);
                            }

                            @Override
                            public void woman() {
                                userInfoVm.setSex(2);
                                userInfoVm.updateSex(2);
                            }
                        })
                        .showDialog();
                break;
            case R.id.fl_mobile:
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypePhone);
                ActivityUtils.startActivity(bundle, UpdateResultActivity.class);
                break;
            case R.id.fl_email:
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypeEmail);
                ActivityUtils.startActivity(bundle, UpdateResultActivity.class);
                break;
            case R.id.fl_id_card:
                int isVerified = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED, 0);
                if (isVerified != 2){
                    bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                    ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                    finish();
                }else {
                    ToastUtils.showShort("已认证完成");
                }
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
                ActivityUtils.startActivity(MultiAreaActivity.class);
                break;
            case R.id.fl_business_exp:
                new BusinessExpDialog(this)
                        .setSelected(userInfoVm.experience.getValue() == null ? userInfoVm.informationLiveData.getValue().getExperience() - 1 : userInfoVm.experience.getValue() - 1)
                        .setSingleCallBack((item, position) -> {
                            userInfoVm.experience.postValue(position + 1);
                        })
                        .showDialog();
                break;
            case R.id.tv_production_tip:
            case R.id.tv_production:
                bundle.putString(CustomConfig.PRODUCTION, tvProduction.getText().toString());
                ActivityUtils.startActivity(bundle, UpdateProductionActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionVm.REQUEST_CODE_CHOOSE_HEAD && resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
            uploadPhotoVm.upload(mSelected.get(0));
        }

        if (requestCode == PermissionVm.REQUEST_CODE_HEAD && resultCode == RESULT_OK) {
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

    @Subscribe
    public void onEventUpdateUserName(UserNameEvent event) {
        tvName.setText(event.username);
    }

    @Subscribe
    public void onEventUpdateMobile(MobileEvent event) {
        tvMobile.setText(event.mobile);
    }

    @Subscribe
    public void onEventUpdateEmail(EmailEvent event) {
        tvEmail.setText(event.email);
    }

    @Subscribe
    public void onEventUpdateCompany(CompanyEvent event) {
        tvCompany.setText(event.company);
    }

    @Subscribe
    public void onEventUpdateOffice(OfficeEvent event) {
        tvOffice.setText(event.office);
    }

    @Subscribe
    public void onEventServiceCity(MultiAreaEvent event) {
        userInfoVm.multiAreaLiveData.postValue(event);
    }

    @Subscribe
    public void onEventUpdateProduction(ProductionEvent event) {
        tvProduction.setText(event.production);
    }

}
