package com.shuangduan.zcy.view.release;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.PhotoDialog;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.PhotoViewActivity;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.weight.PicContentView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  发布工程信息
 * @time 2019/7/17 14:23
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseProjectActivity extends BaseActivity implements BaseDialog.PhotoCallBack {

    @BindView(R.id.pic_content)
    PicContentView picContentView;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.edt_project_name)
    EditText edtProjectName;
    @BindView(R.id.edt_project_address)
    EditText edtProjectAddress;
    @BindView(R.id.fl_project_address)
    FrameLayout flProjectAddress;
    @BindView(R.id.edt_project_content)
    EditText edtProjectContent;
    @BindView(R.id.fl_project_content)
    FrameLayout flProjectContent;
    @BindView(R.id.edt_project_schedule)
    EditText edtProjectSchedule;
    @BindView(R.id.fl_project_schedule)
    FrameLayout flProjectSchedule;
    @BindView(R.id.edt_visitor)
    EditText edtVisitor;
    @BindView(R.id.fl_visitor)
    FrameLayout flVisitor;
    @BindView(R.id.edt_principle)
    EditText edtPrinciple;
    @BindView(R.id.fl_principle)
    FrameLayout flPrinciple;
    @BindView(R.id.edt_mobile)
    EditText edtMobile;
    @BindView(R.id.edt_update_time)
    EditText edtUpdateTime;
    @BindView(R.id.fl_update_time)
    FrameLayout flUpdateTime;
    @BindView(R.id.rl_photo)
    RelativeLayout rlPhoto;
    private PermissionVm permissionVm;
    private RxPermissions rxPermissions;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_release_project;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.release_msg));

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

        picContentView.setListener(new PicContentView.OnClickListener() {
            @Override
            public void add() {
                new PhotoDialog(ReleaseProjectActivity.this)
                        .setPhotoCallBack(ReleaseProjectActivity.this)
                        .showDialog();
            }

            @Override
            public void see(View view, int position) {
                //获取图片，移除最后的加号图片
                List<PicContentView.PicContentBean> picContentList = new ArrayList<>();
                picContentList.addAll(picContentView.getList());
                picContentList.remove(picContentList.size() - 1);
                ArrayList<String> list = new ArrayList<>();
                for (PicContentView.PicContentBean pic : picContentList) {
                    if (pic.getUri() != null) {
                        list.add(pic.getUri().getPath());
                    } else if (pic.getPath() != null) {
                        list.add(pic.getPath());
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putStringArrayList(CustomConfig.PHOTO_VIEW_URL_LIST, list);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //共享shareElement这个View
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ReleaseProjectActivity.this, view, "shareElement");
                    ActivityUtils.startActivity(bundle, PhotoViewActivity.class, activityOptionsCompat.toBundle());
                } else {
                    ActivityUtils.startActivity(bundle, PhotoViewActivity.class);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(requestCode, resultCode);
        if (requestCode == PermissionVm.REQUEST_CODE_CHOOSE_HEAD && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            LogUtils.i(mSelected.get(0), Matisse.obtainPathResult(data).get(0), Uri.parse(Matisse.obtainPathResult(data).get(0)));
            //设置给图片盛放控件
            List<PicContentView.PicContentBean> list = new ArrayList<>();
            for (Uri image : mSelected) {
                list.add(new PicContentView.PicContentBean(image, null, 0));
            }
            picContentView.insert(list);
        }

        if (requestCode == PermissionVm.REQUEST_CODE_HEAD && resultCode == RESULT_OK) {
            //设置给图片盛放控件
            List<PicContentView.PicContentBean> list = new ArrayList<>();
            list.add(new PicContentView.PicContentBean(Uri.fromFile(new File(MatisseCamera.obtainPathResult())), null, 0));
            picContentView.insert(list);
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

    @OnClick({R.id.iv_bar_back, R.id.tv_release})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_release:
                break;
        }
    }
}
