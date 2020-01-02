package com.shuangduan.zcy.view.mine.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseBottomSheetDialog;
import com.shuangduan.zcy.dialog.BusinessExpDialog;
import com.shuangduan.zcy.dialog.SexDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.UserInfoBean;
import com.shuangduan.zcy.model.event.CompanyEvent;
import com.shuangduan.zcy.model.event.MobileEvent;
import com.shuangduan.zcy.model.event.MultiAreaEvent;
import com.shuangduan.zcy.model.event.OfficeEvent;
import com.shuangduan.zcy.model.event.ProductionEvent;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.rongyun.view.IMAddFriendActivity;
import com.shuangduan.zcy.utils.image.CompressUtils;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  基本信息界面
 * @time 2019/7/5 17:01
 * @change
 * @chang time
 * @class describe
 */
public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.iv_sgs)
    AppCompatImageView ivSgs;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.cb_state)
    CheckBox cbState;
    @BindView(R.id.iv_name)
    ImageView ivName;
    @BindView(R.id.tv_sex)
    AppCompatTextView tvSex;
    @BindView(R.id.tv_mobile)
    AppCompatTextView tvMobile;
    @BindView(R.id.tv_id_card)
    AppCompatTextView tvIdCard;
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
    private UserInfoBean userInfoBean;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_user_info;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));

        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);

        userInfoVm.uid = getIntent().getIntExtra(CustomConfig.UID, 0);
        if (SPUtils.getInstance().getInt(SpConfig.USER_ID) != userInfoVm.uid) {
            getNoClickable();
        }

        //获取用户基本信息
        userInfoVm.informationLiveData.observe(this, userInfoBean -> {
            this.userInfoBean = userInfoBean;

            //用户认证标识状态显示
            ivSgs.setVisibility(userInfoBean.getCardStatus() == 2 ? View.VISIBLE : View.INVISIBLE);
            cbState.setChecked(userInfoBean.getCardStatus() == 2);
            cbState.setText(userInfoBean.getCardStatus() == 2 ? R.string.real_name : R.string.un_real_name);

            SPUtils.getInstance().put(SpConfig.USERNAME, userInfoBean.getUsername(), true);
            SPUtils.getInstance().put(SpConfig.MOBILE, userInfoBean.getTel(), true);
            tvName.setText(userInfoBean.getUsername());
            tvMobile.setText(userInfoBean.getTel());

            tvIdCard.setText(StringUtils.isTrimEmpty(userInfoBean.getIdentity_card()) ? "请申请实名认证" : userInfoBean.getIdentity_card());
            tvCompany.setText(userInfoBean.getCompany());
            tvOffice.setText(userInfoBean.getPosition());
            tvBusinessArea.setText(StringUtils.isTrimEmpty(userInfoBean.getBusiness_city()) ? "请选择业务地区" : userInfoBean.getBusiness_city());

            if (userInfoBean.getExperience() >= 1 && userInfoBean.getExperience() <= 5)
                tvBusinessExp.setText(getResources().getStringArray(R.array.experience_list)[userInfoBean.getExperience() - 1] + "年");
            tvProduction.setText(userInfoBean.getManaging_products());

            //判断是否为好友
            if (userInfoBean.getApply_status() != null && userInfoBean.getApply_status().equals("1")) {
                if (SPUtils.getInstance().getInt(SpConfig.USER_ID) != userInfoVm.uid) {
                    tvAddFriend.setText(getString(R.string.im_add_friend));
                } else {
                    tvAddFriend.setVisibility(View.GONE);
                }
            } else {
                tvAddFriend.setText(getString(R.string.im_send_message));
            }
        });

        //获取用户头像
        userInfoVm.avatarLiveData.observe(this, s -> {
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(s)
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
        });

        //获取用户性别
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

        //编辑用户信息
        userInfoVm.areaLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.edit_success));
            tvBusinessArea.setText(userInfoVm.multiAreaLiveData.getValue().getStringResult());
        });

        userInfoVm.expLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.edit_success));
            if (userInfoVm.experience.getValue() >= 1 && userInfoVm.experience.getValue() <= 5)
                tvBusinessExp.setText(getResources().getStringArray(R.array.experience_list)[userInfoVm.experience.getValue() - 1] + "年");
        });

        rxPermissions = new RxPermissions(this);
        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_CAMERA) {
                MatisseCamera.from(this).forResult(PermissionVm.REQUEST_CODE_HEAD, "com.shuangduan.zcy.fileprovider");
            } else if (integer == PermissionVm.PERMISSION_STORAGE) {
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

    @OnClick({R.id.iv_bar_back, R.id.iv_user, R.id.fl_name, R.id.fl_sex, R.id.fl_mobile, R.id.fl_id_card,
            R.id.fl_company, R.id.fl_office, R.id.fl_business_area, R.id.fl_business_exp, R.id.tv_production_tip, R.id.tv_production
            , R.id.tv_add_friend})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back://返回
                finish();
                break;
            case R.id.iv_user://更换头像
                BaseBottomSheetDialog baseBottomSheetDialog =new BaseBottomSheetDialog(this,rxPermissions,permissionVm);
                baseBottomSheetDialog.showPhotoDialog();
                break;
            case R.id.fl_name://修改姓名
                ActivityUtils.startActivity(UpdateNameActivity.class);
                break;
            case R.id.fl_sex://修改性别
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
            case R.id.fl_mobile://手改手机号
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypePhone);
                ActivityUtils.startActivity(bundle, UpdateResultActivity.class);
                break;
            case R.id.fl_id_card://身份认证
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                bundle.putString(CustomConfig.AUTHENTICATION_TYPE, CustomConfig.ID_USER_INFO);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
            case R.id.fl_company://修改公司
                bundle.putString(CustomConfig.SEARCH_TYPE, CustomConfig.searchTypeCompany);
                ActivityUtils.startActivity(bundle, CompanySearchActivity.class);
                break;
            case R.id.fl_office://修改职位
                bundle.putString(CustomConfig.SEARCH_TYPE, CustomConfig.searchTypeOffice);
                ActivityUtils.startActivity(bundle, CompanySearchActivity.class);
                break;
            case R.id.fl_business_area://修改业务地区
                ActivityUtils.startActivity(MultiAreaActivity.class);
                break;
            case R.id.fl_business_exp://修改业务经验
                int experience = Objects.requireNonNull(userInfoVm.informationLiveData.getValue()).getExperience();
                if (experience > 0) {
                    experience = experience - 1;
                }
                new BusinessExpDialog(this)
                        .setSelected(userInfoVm.experience.getValue() == null ? experience : userInfoVm.experience.getValue() - 1)
                        .setSingleCallBack((item, position) -> userInfoVm.experience.postValue(position + 1))
                        .showDialog();
                break;
            case R.id.tv_production_tip:
            case R.id.tv_production://修改经营产品
                bundle.putString(CustomConfig.PRODUCTION, tvProduction.getText().toString());
                ActivityUtils.startActivity(bundle, UpdateProductionActivity.class);
                break;
            case R.id.tv_add_friend://添加好友/发消息
                if (userInfoBean.getApply_status() != null && userInfoBean.getApply_status().equals("1")) {
                    bundle.putInt(CustomConfig.FRIEND_DATA, 0);
                    bundle.putInt("id", userInfoBean.getId());
                    bundle.putString("name", userInfoBean.getUsername());
                    bundle.putString("msg", userInfoBean.getCompany());
                    bundle.putString("image", userInfoBean.getImage());
                    ActivityUtils.startActivity(bundle, IMAddFriendActivity.class);
                } else {
                    RongIM.getInstance().startPrivateChat(UserInfoActivity.this, String.valueOf(userInfoBean.getId()), userInfoBean.getUsername());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionVm.REQUEST_CODE_CHOOSE_HEAD && resultCode == RESULT_OK) {
            if (MatisseCamera.isAndroidQ) {
                LogUtils.e(Matisse.obtainResult(data).get(0));
                uploadPhotoVm.upload(CompressUtils.getRealFilePath(this,Matisse.obtainResult(data).get(0)));
            }else {
                LogUtils.e(Matisse.obtainPathResult(data).get(0));
                uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
            }
        }

        if (requestCode == PermissionVm.REQUEST_CODE_HEAD && resultCode == RESULT_OK) {
            if (MatisseCamera.isAndroidQ) {
                LogUtils.e(MatisseCamera.obtainUriResult());
                uploadPhotoVm.upload(CompressUtils.getRealFilePath(this,MatisseCamera.obtainUriResult()));
            } else {
                uploadPhotoVm.upload(MatisseCamera.obtainPathResult());
            }
        }
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

    //取消点击监听
    private void getNoClickable() {
        ivUser.setClickable(false);
        findViewById(R.id.fl_name).setClickable(false);
        ivName.setVisibility(View.GONE);
        findViewById(R.id.fl_sex).setClickable(false);
        tvSex.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.fl_mobile).setClickable(false);
        findViewById(R.id.fl_mobile).setVisibility(View.GONE);
        tvMobile.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.fl_id_card).setClickable(false);
        findViewById(R.id.fl_id_card).setVisibility(View.GONE);
        tvIdCard.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.fl_company).setClickable(false);
        tvCompany.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.fl_office).setClickable(false);
        tvOffice.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.fl_business_area).setClickable(false);
        tvBusinessArea.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.fl_business_exp).setClickable(false);
        tvBusinessExp.setCompoundDrawables(null, null, null, null);
        tvProductionTip.setClickable(false);
        tvProductionTip.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.tv_production).setClickable(false);
    }
}
