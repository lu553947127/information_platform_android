package com.shuangduan.zcy.view.supplier;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.PhotoDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.ContactBean;
import com.shuangduan.zcy.model.event.AddressEvent;
import com.shuangduan.zcy.model.event.CityEvent;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.PhotoViewActivity;
import com.shuangduan.zcy.view.mine.BusinessAreaActivity;
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
import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.edt_company)
    EditText edtCompany;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.edt_address_detail)
    EditText edtAddressDetail;
    @BindView(R.id.tv_service_area)
    TextView tvServiceArea;
    @BindView(R.id.edt_production)
    AppCompatEditText edtProduction;
    private PermissionVm permissionVm;
    private UploadPhotoVm uploadPhotoVm;
    private RxPermissions rxPermissions;
    private SupplierVm supplierVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_supplier_join;
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
                        .captureStrategy(
                                new CaptureStrategy(true, "com.shuangduan.zcy.fileprovider"))
                        .imageEngine(new Glide4Engine())
                        .forResult(PermissionVm.REQUEST_CODE_CHOOSE_HEAD);
            }
        });
        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);
        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
            supplierVm.addImage(uploadBean.getImage_id());
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
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SupplierJoinActivity.this, view, "shareElement");
                    ActivityUtils.startActivity(bundle, PhotoViewActivity.class, activityOptionsCompat.toBundle());
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
    }

    @Override
    public void camera() {
        permissionVm.getPermissionCamera(rxPermissions);
    }

    @Override
    public void album() {
        permissionVm.getPermissionAlbum(rxPermissions);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm, R.id.tv_company_address, R.id.tv_service_area})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(edtName.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_name));
                    return;
                }
                if (TextUtils.isEmpty(edtContactInfo.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                if (TextUtils.isEmpty(edtCompany.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_company));
                    return;
                }
                if (TextUtils.isEmpty(edtAddressDetail.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_address_detail));
                    return;
                }
                if (TextUtils.isEmpty(edtProduction.getText())){
                    ToastUtils.showShort(getString(R.string.hint_production));
                    return;
                }
                supplierVm.join(edtName.getText().toString(), edtContactInfo.getText().toString(), edtCompany.getText().toString(), edtAddressDetail.getText().toString(), edtProduction.getText().toString());
                break;
            case R.id.tv_company_address:
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.PROJECT_ADDRESS, 0);
                ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                break;
            case R.id.tv_service_area:
                ActivityUtils.startActivity(BusinessAreaActivity.class);
                break;
        }
    }

    @Subscribe()
    public void addressEvent(AddressEvent event){
        tvCompanyAddress.setText(event.getProvince() + " " + event.getCity());
        supplierVm.cityId = event.getCityId();
        supplierVm.provinceId = event.getProvinceId();
    }

    @Subscribe()
    public void serviceCity(CityEvent event) {
        supplierVm.serviceArea.postValue(event);
        tvServiceArea.setText(event.city);
    }
}
