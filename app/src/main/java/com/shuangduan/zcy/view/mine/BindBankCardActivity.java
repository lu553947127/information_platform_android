package com.shuangduan.zcy.view.mine;

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
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.PhotoDialog;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.WithdrawVm;
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
 * @class describe  绑定银行卡
 * @time 2019/7/8 14:56
 * @change
 * @chang time
 * @class describe
 */
public class BindBankCardActivity extends BaseActivity implements BaseDialog.PhotoCallBack {

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
    private WithdrawVm withdrawVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.bank_card_message));
        tvBarRight.setText(getString(R.string.save));

        rxPermissions = new RxPermissions(this);
        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_CAMERA){
                MatisseCamera.from(this)
                        .forResult(PermissionVm.REQUEST_CODE_BANK_CARD, "com.shuangduan.zcy.fileprovider");
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
                        .forResult(PermissionVm.REQUEST_CODE_CHOOSE_BANK_CARD);
            }
        });
        withdrawVm = ViewModelProviders.of(this).get(WithdrawVm.class);
        withdrawVm.authenticationLiveData.observe(this, authenBean -> {
            tvRealName.setText(authenBean.getReal_name());
            tvIdentityNumber.setText(authenBean.getIdentity_card());
        });
        withdrawVm.authentication();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.iv_camera})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                break;
            case R.id.iv_camera:
                new PhotoDialog(this)
                        .setPhotoCallBack(this)
                        .showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionVm.REQUEST_CODE_CHOOSE_BANK_CARD && resultCode == RESULT_OK) {
            List<String> mSelected = Matisse.obtainPathResult(data);
        }

        if (requestCode == PermissionVm.REQUEST_CODE_BANK_CARD && resultCode == RESULT_OK) {
            LogUtils.i(MatisseCamera.obtainPathResult(), MatisseCamera.obtainUriResult());
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
