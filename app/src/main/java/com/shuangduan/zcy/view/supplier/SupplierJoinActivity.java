package com.shuangduan.zcy.view.supplier;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PhotoDialog;
import com.shuangduan.zcy.dialog.ScaleDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.ContactBean;
import com.shuangduan.zcy.model.bean.FileUploadBean;
import com.shuangduan.zcy.model.event.AddressEvent;
import com.shuangduan.zcy.model.event.CityEvent;
import com.shuangduan.zcy.model.event.MultiAreaEvent;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.MultiAreaActivity;
import com.shuangduan.zcy.view.PhotoViewActivity;
import com.shuangduan.zcy.view.mine.BusinessAreaActivity;
import com.shuangduan.zcy.view.photo.CameraActivity;
import com.shuangduan.zcy.view.release.ReleaseAreaSelectActivity;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.SupplierVm;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.shuangduan.zcy.weight.PicContentView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.supplier
 * @class describe  加入供应商
 * @time 2019/8/12 14:55
 * @change
 * @chang time
 * @class describe
 */
public class SupplierJoinActivity extends BaseActivity implements BaseDialog.PhotoCallBack {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_content)
    PicContentView picContentView;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_contact_info)
    EditText edtContactInfo;
    @BindView(R.id.edt_company_website)
    EditText edtCompanyWebsite;
    @BindView(R.id.edt_company)
    EditText edtCompany;
    @BindView(R.id.tv_scale)
    TextView tvScale;
    @BindView(R.id.edt_address_detail)
    EditText edtAddressDetail;
    @BindView(R.id.tv_service_area)
    TextView tvServiceArea;
    @BindView(R.id.edt_production)
    AppCompatEditText edtProduction;

    @BindView(R.id.iv_authorization)
    ImageView ivAuthorization;
    @BindView(R.id.tv_authorization)
    TextView tvAuthorization;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    private PermissionVm permissionVm;
    private UploadPhotoVm uploadPhotoVm;
    private RxPermissions rxPermissions;
    private SupplierVm supplierVm;

    public static final int CAMERA=111;
    public static final int PHOTO=222;
    private int scale=0;
    private String type,authorization,logo;
    private BottomSheetDialogs btn_dialog;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_supplier_join;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.supplier_join));

        supplierVm = ViewModelProviders.of(this).get(SupplierVm.class);
        supplierVm.joinLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.supplier_join_success));
            finish();
        });
        supplierVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        String str="请下载相关<font color=\"#6a5ff8\">授权申请文件</font>，填写加盖公章后拍照上传（非必填）";
        tvAuthorization.setText(Html.fromHtml(str));
        initPhoto();
        photoSet();
    }

    private void initPhoto() {
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
                        .captureStrategy(new CaptureStrategy(true, "com.shuangduan.zcy.fileprovider"))
                        .imageEngine(new Glide4Engine())
                        .forResult(PermissionVm.REQUEST_CODE_CHOOSE_HEAD);
            }
        });
        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> supplierVm.addImage(uploadBean.getImage_id()));
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
    }

    private void photoSet() {
        picContentView.setListener(new PicContentView.OnClickListener() {
            @Override
            public void add() {
                new PhotoDialog(SupplierJoinActivity.this)
                        .setPhotoCallBack(SupplierJoinActivity.this)
                        .showDialog();
            }

            @Override
            public void see(View view, int position) {
                //获取图片，移除最后的加号图片
                List<PicContentView.PicContentBean> picContentList = new ArrayList<>(picContentView.getList());
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
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SupplierJoinActivity.this, view, "shareElement");
                    ActivityUtils.startActivity(bundle, PhotoViewActivity.class, Objects.requireNonNull(activityOptionsCompat.toBundle()));
                } else {
                    ActivityUtils.startActivity(bundle, PhotoViewActivity.class);
                }
            }

            @Override
            public void delete(int pos) {
                supplierVm.delImage(pos);
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
            uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
        }

        if (requestCode == PermissionVm.REQUEST_CODE_HEAD && resultCode == RESULT_OK) {
            //设置给图片盛放控件
            List<PicContentView.PicContentBean> list = new ArrayList<>();
            list.add(new PicContentView.PicContentBean(Uri.fromFile(new File(MatisseCamera.obtainPathResult())), null, 0));
            picContentView.insert(list);
            uploadPhotoVm.upload(MatisseCamera.obtainPathResult());
        }

        // 从相机返回的数据
        if (resultCode == 101) {
            String path = data.getStringExtra("path");
            if (type.equals("authorization")){
                ivAuthorization.setImageBitmap(BitmapFactory.decodeFile(path));
            }else {
                ivLogo.setImageBitmap(BitmapFactory.decodeFile(path));
            }
            File file = new File(Objects.requireNonNull(path));
            LogUtils.i(file);
            getFileUpload(file);
        }
        if (resultCode == 103) {
            ToastUtils.showShort("您没有打开相机权限");
        }

        //从相册返回的数据
        if (requestCode == PHOTO && resultCode == RESULT_OK) {
            LogUtils.i(Matisse.obtainPathResult(data).get(0));
            if (type.equals("authorization")){
                ivAuthorization.setImageBitmap(BitmapFactory.decodeFile(Matisse.obtainPathResult(data).get(0)));
            }else {
                ivLogo.setImageBitmap(BitmapFactory.decodeFile(Matisse.obtainPathResult(data).get(0)));
            }
            File file = new File(Objects.requireNonNull(Matisse.obtainPathResult(data).get(0)));
            getFileUpload(file);
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

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.iv_bar_back, R.id.tv_confirm, R.id.tv_scale, R.id.tv_service_area,R.id.iv_authorization,R.id.tv_authorization,R.id.iv_logo})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(edtCompany.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_company));
                    return;
                }
                if (TextUtils.isEmpty(edtAddressDetail.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_address_detail));
                    return;
                }
                if (scale==0){
                    ToastUtils.showShort(getString(R.string.hint_scale));
                    return;
                }
                if (TextUtils.isEmpty(edtCompanyWebsite.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hind_company_website));
                    return;
                }
                if (TextUtils.isEmpty(edtName.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_name));
                    return;
                }
                if (TextUtils.isEmpty(edtContactInfo.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                if (TextUtils.isEmpty(edtProduction.getText())){
                    ToastUtils.showShort(getString(R.string.hint_production));
                    return;
                }
                if (TextUtils.isEmpty(logo)){
                    ToastUtils.showShort("请上传公司Logo");
                    return;
                }
                supplierVm.join(edtName.getText().toString(), edtContactInfo.getText().toString(), edtCompany.getText().toString(), edtAddressDetail.getText().toString(), edtProduction.getText().toString()
                        ,scale,edtCompanyWebsite.getText().toString(),authorization,logo);
                break;
            case R.id.tv_scale:
//                Bundle bundle = new Bundle();
//                bundle.putInt(CustomConfig.PROJECT_ADDRESS, 0);
//                ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                new ScaleDialog(this).setSelected(0).setSingleCallBack((item, position) -> {
                    scale=position+1;
                    tvScale.setText(item+"人");
                }).showDialog();
                break;
            case R.id.tv_service_area:
                ActivityUtils.startActivity(MultiAreaActivity.class);
                break;
            case R.id.iv_authorization:
                type="authorization";
                getPermissions();
                break;
            case R.id.iv_logo:
                type="logo";
                getPermissions();
                break;
            case R.id.tv_authorization:
                new CustomDialog(this)
                        .setTip(CustomConfig.SUPPLIER_AUTHORIZATION)
                        .setOk("跳转")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                Intent intent = new Intent();
                                //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(CustomConfig.SUPPLIER_AUTHORIZATION);
                                intent.setData(content_url);
                                startActivity(intent);
                            }
                        }).showDialog();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventAddressEvent(AddressEvent event){
        tvScale.setText(event.getProvince() + " " + event.getCity());
        supplierVm.cityId = event.getCityId();
        supplierVm.provinceId = event.getProvinceId();
    }

    @Subscribe
    public void onEventServiceCity(MultiAreaEvent event) {
        supplierVm.serviceArea.postValue(event);
        tvServiceArea.setText(event.getStringResult());
    }

    //获取权限
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                getUploadPicture();
                btn_dialog.show();
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, CAMERA);
            }
        } else {
            getUploadPicture();
            btn_dialog.show();
        }
    }

    //上传图片底部弹出框
    @SuppressLint("RestrictedApi")
    private void getUploadPicture() {
        //底部滑动对话框
        btn_dialog = new BottomSheetDialogs(this);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(R.layout.dialog_photo, null);
        //把布局添加进去
        btn_dialog.setContentView(dialog_view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((ViewGroup.MarginLayoutParams)dialog_view.findViewById(R.id.tv_cancel).getLayoutParams()).bottomMargin=60;
        dialog_view.findViewById(R.id.tv_cancel).setOnClickListener(view -> btn_dialog.cancel());
        dialog_view.findViewById(R.id.tv_photo).setOnClickListener(view -> {
            startActivityForResult(new Intent(this, CameraActivity.class), 100);
            btn_dialog.cancel();
        });
        dialog_view.findViewById(R.id.tv_select_pic).setOnClickListener(view -> {
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
                    .forResult(PHOTO);
            btn_dialog.cancel();
        });
    }

    //上传附件
    private void getFileUpload(File file) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.UPLOAD_IMAGE)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("file",file)//文件流
                .execute(new com.lzy.okgo.callback.StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            FileUploadBean bean=new Gson().fromJson(response.body(), FileUploadBean.class);
                            if (bean.getCode().equals("200")){
                                if (type.equals("authorization")){
                                    authorization=bean.getData().getSource();
                                }else {
                                    logo=bean.getData().getSource();
                                }
                            }else if (bean.getCode().equals("-1")){
                                ToastUtils.showShort(bean.getMsg());
                                LoginUtils.getExitLogin(SupplierJoinActivity.this);
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }
}
