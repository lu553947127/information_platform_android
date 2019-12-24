package com.shuangduan.zcy.view.supplier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaSecondAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseBottomSheetDialog;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.ScaleDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.event.MultiAreaEvent;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.view.MultiAreaActivity;
import com.shuangduan.zcy.view.photo.CameraActivity;
import com.shuangduan.zcy.vm.MultiAreaVm;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.supplier
 * @class describe  加入供应商
 * @time 2019/8/12 14:55
 * @change
 * @chang time
 * @class describe
 */
@SuppressLint("SetTextI18n")
public class SupplierJoinActivity extends BaseActivity {
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
    @BindView(R.id.tv_address)
    TextView tvAddress;
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
    @BindView(R.id.tv_pic_content)
    TextView tvPicContent;
    @BindView(R.id.tv_logo)
    TextView tvLogo;

    private PermissionVm permissionVm;
    private UploadPhotoVm uploadPhotoVm;
    private RxPermissions rxPermissions;
    private SupplierVm supplierVm;

    public static final int PHOTO = 222;
    private int scale = 0;
    private String type, authorization,logo;
    private BaseBottomSheetDialog baseBottomSheetDialog;
    private MultiAreaVm areaVm;

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
        String str = "请下载相关<font color=\"#6a5ff8\">授权申请文件</font>，填写加盖公章后拍照上传（非必填）";
        tvAuthorization.setText(Html.fromHtml(str));
        initPhoto();
        photoSet();

        baseBottomSheetDialog =new BaseBottomSheetDialog(this,rxPermissions,permissionVm);
        getBottomSheetDialog(R.layout.dialog_depositing_place);
    }

    private void initPhoto() {
        rxPermissions = new RxPermissions(this);
        permissionVm = ViewModelProviders.of(this).get(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_CAMERA) {
                startActivityForResult(new Intent(this, CameraActivity.class), 100);
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
                        .forResult(PHOTO);
            }
        });
        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);

        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
            switch (type){
                case "images"://营业执照
                    supplierVm.addImage(uploadBean.getImage_id());
                    tvPicContent.setVisibility(View.GONE);
                    break;
                case "authorization"://授权申请
                    authorization = uploadBean.getSource();
                    tvAuthorization.setVisibility(View.INVISIBLE);
                    break;
                case "logo"://公司logo
                    logo = uploadBean.getSource();
                    tvLogo.setVisibility(View.INVISIBLE);
                    break;
            }
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

        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            provinceList = provinceBeans;
            provinceAdapter.setNewData(provinceList);
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            cityList = cityBeans;
            cityAdapter.setNewData(cityList);
        });
    }

    private void photoSet() {
        picContentView.setListener(new PicContentView.OnClickListener() {
            @Override
            public void add() {
                type = "images";
                baseBottomSheetDialog.showPhotoDialog();
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
                //查看图片
                PictureEnlargeUtils.getPictureEnlargeList(SupplierJoinActivity.this,list,position);
            }

            @Override
            public void delete(int pos) {
                supplierVm.delImage(pos);
                LogUtils.i("supplierVm"+pos);
                //显示提示语句
                if (pos == 0)tvPicContent.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(requestCode, resultCode);

        // 从相机返回的数据
        if (resultCode == 101) {
            String path = data.getStringExtra("path");
            LogUtils.e(path);
            switch (type){
                case "images"://营业执照
                    List<PicContentView.PicContentBean> list = new ArrayList<>();
                    list.add(new PicContentView.PicContentBean(Uri.fromFile(new File(path)), null, 0));
                    picContentView.insert(list);
                    uploadPhotoVm.upload(path);
                    break;
                case "authorization"://授权申请
                    ivAuthorization.setImageBitmap(BitmapFactory.decodeFile(path));
                    uploadPhotoVm.upload(path);
                    break;
                case "logo"://公司logo
                    ivLogo.setImageBitmap(BitmapFactory.decodeFile(path));
                    uploadPhotoVm.upload(path);
                    break;
            }
        }
        if (resultCode == 103) {
            ToastUtils.showShort("您没有打开相机权限");
        }

        //从相册返回的数据
        if (requestCode == PHOTO && resultCode == RESULT_OK) {
            LogUtils.i(Matisse.obtainPathResult(data).get(0));
            switch (type){
                case "images"://营业执照
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    LogUtils.i(mSelected.get(0), Matisse.obtainPathResult(data).get(0), Uri.parse(Matisse.obtainPathResult(data).get(0)));
                    //设置给图片盛放控件
                    List<PicContentView.PicContentBean> list = new ArrayList<>();
                    for (Uri image : mSelected) {
                        list.add(new PicContentView.PicContentBean(image, null, 0));
                    }
                    picContentView.insert(list);
                    uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
                    break;
                case "authorization"://授权申请
                    ivAuthorization.setImageBitmap(BitmapFactory.decodeFile(Matisse.obtainPathResult(data).get(0)));
                    uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
                    break;
                case "logo"://公司logo
                    ivLogo.setImageBitmap(BitmapFactory.decodeFile(Matisse.obtainPathResult(data).get(0)));
                    uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
                    break;
            }
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm, R.id.tv_scale, R.id.tv_service_area, R.id.iv_authorization, R.id.tv_authorization, R.id.iv_logo,R.id.tv_address})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                supplierVm.join(edtName.getText().toString(), edtContactInfo.getText().toString(), edtCompany.getText().toString(),areaVm.id,areaVm.city_id, edtAddressDetail.getText().toString(), edtProduction.getText().toString()
                        , scale, edtCompanyWebsite.getText().toString(), authorization, logo);
                break;
            case R.id.tv_scale:
                new ScaleDialog(this).setSelected(0).setSingleCallBack((item, position) -> {
                    scale = position + 1;
                    tvScale.setText(item + "人");
                }).showDialog();
                break;
            case R.id.tv_service_area:
                ActivityUtils.startActivity(MultiAreaActivity.class);
                break;
            case R.id.iv_authorization:
                type = "authorization";
                baseBottomSheetDialog.showPhotoDialog();
                break;
            case R.id.iv_logo:
                type = "logo";
                baseBottomSheetDialog.showPhotoDialog();
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
            case R.id.tv_address:
                dialog.show();
                break;
        }
    }

    @Subscribe
    public void onEventServiceCity(MultiAreaEvent event) {
        supplierVm.serviceArea.postValue(event);
        tvServiceArea.setText(event.getStringResult());
    }

    private List<ProvinceBean> provinceList = new ArrayList<>();
    private List<CityBean> cityList = new ArrayList<>();
    private SelectorAreaFirstAdapter provinceAdapter;
    private SelectorAreaSecondAdapter cityAdapter;
    private BottomSheetDialogs dialog;
    //省市弹框
    private void getBottomSheetDialog(int layoutRes) {
        //底部滑动对话框
        dialog = new BottomSheetDialogs(Objects.requireNonNull(this), R.style.BottomSheetStyle);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(layoutRes, null);
        //把布局添加进去
        dialog.setContentView(dialog_view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyclerView rvProvince = dialog.findViewById(R.id.rv_province);
        RecyclerView rvCity = dialog.findViewById(R.id.rv_city);
        Objects.requireNonNull(rvProvince).setLayoutManager(new LinearLayoutManager(this));
        Objects.requireNonNull(rvCity).setLayoutManager(new LinearLayoutManager(this));
        provinceAdapter = new SelectorAreaFirstAdapter(R.layout.adapter_selector_area_first, null);
        cityAdapter = new SelectorAreaSecondAdapter(R.layout.adapter_selector_area_second, null);
        rvProvince.setAdapter(provinceAdapter);
        rvCity.setAdapter(cityAdapter);
        provinceAdapter.setOnItemClickListener((adapter, view, position) -> {
            areaVm.id = provinceList.get(position).getId();
            areaVm.provinceResult = provinceList.get(position).getName();
            areaVm.getCity();
            provinceAdapter.setIsSelect(provinceList.get(position).getId());
        });
        cityAdapter.setOnItemClickListener((adapter, view, position) -> {
            areaVm.city_id = cityList.get(position).getId();
            cityAdapter.setIsSelect(cityList.get(position).getId());
            areaVm.cityResult = cityList.get(position).getName();
            tvAddress.setText(areaVm.provinceResult + " " + areaVm.cityResult);
            dialog.dismiss();
        });
        areaVm.getProvince();
        if (areaVm.id != 0) {
            provinceAdapter.setIsSelect(areaVm.id);
            areaVm.getCity();
        }
        if (areaVm.city_id != 0) {
            cityAdapter.setIsSelect(areaVm.city_id);
        }
    }
}
