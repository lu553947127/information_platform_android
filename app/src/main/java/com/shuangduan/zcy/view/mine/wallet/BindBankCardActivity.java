package com.shuangduan.zcy.view.mine.wallet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseBottomSheetDialog;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.BankcardUpdateEvent;
import com.shuangduan.zcy.utils.PermissionUtils;
import com.shuangduan.zcy.utils.image.CompressUtils;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.photo.CameraActivity;
import com.shuangduan.zcy.vm.AuthenticationVm;
import com.shuangduan.zcy.vm.BankCardVm;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class 绑定银行卡
 * @time 2019/7/8 14:56
 * @change
 * @chang time
 * @class describe
 */
public class BindBankCardActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_real_name)
    TextView tvRealName;
    @BindView(R.id.edt_bank_account)
    AppCompatEditText edtBankAccount;
    @BindView(R.id.edt_bank_card_number)
    AppCompatEditText edtBankCardNumber;
    @BindView(R.id.edt_identity_number)
    TextView tvIdentityNumber;

    private PermissionVm permissionVm;
    private RxPermissions rxPermissions;
    private UploadPhotoVm uploadPhotoVm;
    private BankCardVm bankCardVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.bank_card_message));
        tvBarRight.setText(getString(R.string.save));

        rxPermissions = new RxPermissions(this);
        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            switch (integer) {
                case PermissionVm.PERMISSION_CAMERA:
                    startActivityForResult(new Intent(this, CameraActivity.class), 100);
                    break;
                case PermissionVm.PERMISSION_STORAGE:
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
                            .forResult(PermissionVm.PHOTO);
                    break;
                case PermissionVm.PERMISSION_CAMERA_NO:
                case PermissionVm.PERMISSION_STORAGE_NO:
                    new CustomDialog(this)
                            .setTip("为了更好的为您服务，请您打开您的相机和存储权限!")
                            .setCallBack(new BaseDialog.CallBack() {
                                @Override
                                public void cancel() {

                                }

                                @Override
                                public void ok(String s) {
                                    PermissionUtils.toSelfSetting(getApplicationContext());
                                }
                            }).showDialog();
                    break;
            }
        });
        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
            bankCardVm.imageBandCard = uploadBean.getSource();
            bankCardVm.bankcardDis();
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

        //若实名认证成功直接获取姓名和身份证号
        AuthenticationVm authenticationVm = ViewModelProviders.of(this).get(AuthenticationVm.class);
        authenticationVm.authenticationStatusLiveData.observe(this, authenBean -> {
            tvRealName.setText(authenBean.getReal_name());
            tvIdentityNumber.setText(authenBean.getIdentity_card());
        });
        authenticationVm.authentication();

        bankCardVm = ViewModelProviders.of(this).get(BankCardVm.class);
        bankCardVm.disLiveData.observe(this, bankCardDisBean -> {
            String cardNum = RegexUtils.getReplaceAll(bankCardDisBean.getCard_num(), " ", "");
            edtBankAccount.setText(bankCardDisBean.getType_name());
            edtBankCardNumber.setText(cardNum);
        });
        bankCardVm.AddLiveData.observe(this, o -> {
            ToastUtils.showShort("添加成功");
            EventBus.getDefault().post(new BankcardUpdateEvent());
            finish();
        });
        bankCardVm.pageStateLiveData.observe(this, s -> {
            LogUtils.i(s);
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.iv_camera})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                if (StringUtils.isTrimEmpty(edtBankCardNumber.getText().toString()) || StringUtils.isTrimEmpty(edtBankAccount.getText().toString())){
                    ToastUtils.showShort(getString(R.string.bankcard_msg_not));
                    return;
                }
                bankCardVm.bankcardAdd(edtBankCardNumber.getText().toString(), edtBankAccount.getText().toString());
                break;
            case R.id.iv_camera:
                BaseBottomSheetDialog baseBottomSheetDialog =new BaseBottomSheetDialog(this,rxPermissions,permissionVm);
                baseBottomSheetDialog.showPhotoDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionVm.PHOTO && resultCode == RESULT_OK) {
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
