package com.shuangduan.zcy.view.release;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ReleaseContactAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseBottomSheetDialog;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.ContactBean;
import com.shuangduan.zcy.model.event.AddressEvent;
import com.shuangduan.zcy.model.event.ContactTypeEvent;
import com.shuangduan.zcy.model.event.LocationEvent;
import com.shuangduan.zcy.model.event.ProjectNameEvent;
import com.shuangduan.zcy.model.event.StageEvent;
import com.shuangduan.zcy.model.event.TypesArrayEvent;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.utils.PermissionUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.image.CompressUtils;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.mine.user.AuthenticationActivity;
import com.shuangduan.zcy.view.photo.CameraActivity;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.ReleaseVm;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.PicContentView;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ?????? QQ:876885613
 * @name information_platform_android
 * @class name???com.shuangduan.zcy.view.release
 * @class describe  ??????????????????
 * @time 2019/7/17 14:23
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseProjectActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.edt_project_name)
    EditText edtProjectName;
    @BindView(R.id.tv_project_address)
    TextView tvProjectAddress;
    @BindView(R.id.fl_project_address)
    FrameLayout flProjectAddress;
    @BindView(R.id.tv_project_stage)
    TextView tvProjectStage;
    @BindView(R.id.fl_project_stage)
    FrameLayout flProjectStage;
    @BindView(R.id.tv_project_types)
    TextView tvProjectTypes;
    @BindView(R.id.fl_project_types)
    FrameLayout flProjectTypes;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.rl_project_cycle)
    RelativeLayout rlProjectCycle;
    @BindView(R.id.edt_project_acreage)
    EditText edtProjectAcreage;
    @BindView(R.id.fl_project_acreage)
    FrameLayout flProjectAcreage;
    @BindView(R.id.edt_project_price)
    EditText edtProjectPrice;
    @BindView(R.id.fl_project_price)
    FrameLayout flProjectPrice;
    @BindView(R.id.edt_project_detail)
    EditText edtProjectDetail;
    @BindView(R.id.fl_project_detail)
    FrameLayout flProjectDetail;
    @BindView(R.id.edt_project_material)
    EditText edtProjectMaterial;
    @BindView(R.id.fl_project_material)
    FrameLayout flProjectMaterial;
    @BindView(R.id.edt_project_des)
    EditText edtProjectDes;
    @BindView(R.id.fl_project_des)
    FrameLayout flProjectDes;
    @BindView(R.id.edt_visitor)
    EditText edtVisitor;
    @BindView(R.id.fl_visitor)
    FrameLayout flVisitor;
    @BindView(R.id.edt_mobile)
    EditText edtMobile;
    @BindView(R.id.edt_update_time)
    EditText edtUpdateTime;
    @BindView(R.id.fl_update_time)
    FrameLayout flUpdateTime;
    @BindView(R.id.rl_photo)
    RelativeLayout rlPhoto;
    @BindView(R.id.pic_content)
    PicContentView picContentView;
    @BindView(R.id.fl_project_name_edit)
    FrameLayout flProjectNameEdit;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.fl_project_name_select)
    FrameLayout flProjectNameSelect;
    @BindView(R.id.fl_mobile)
    FrameLayout flMobile;
    @BindView(R.id.rv_contact)
    RecyclerView rvContact;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.fl_add)
    FrameLayout flAdd;
    @BindView(R.id.fl_project_company)
    FrameLayout flProjectCompany;
    @BindView(R.id.edt_project_company)
    EditText edtProjectCompany;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ll_authentication)
    LinearLayout llAuthentication;
    @BindView(R.id.marquee)
    TextView tvMarquee;
    private PermissionVm permissionVm;
    private RxPermissions rxPermissions;
    private ReleaseVm releaseVm;
    private ReleaseContactAdapter releaseContactAdapter;
    private UploadPhotoVm uploadPhotoVm;
    private BottomSheetDialogs btn_dialog;
    private SimpleDateFormat f;
    private Calendar c;
    private int release_type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_release_project;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.release_msg));

        release_type = getIntent().getIntExtra(CustomConfig.RELEASE_TYPE, 0);

        f = new SimpleDateFormat("yyyy-MM-dd");
        c = Calendar.getInstance();

        initPhoto();
        photoSet();

        releaseVm = getViewModel(ReleaseVm.class);
        releaseVm.contactLiveData.observe(this, contactBeans -> {
            if (releaseContactAdapter == null) {
                rvContact.setLayoutManager(new LinearLayoutManager(this));
                rvContact.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_10_10));
                releaseContactAdapter = new ReleaseContactAdapter(R.layout.item_release_contact, contactBeans) {
                    @Override
                    public void typeChange(String text, int position) {
                        List<ContactBean> list = releaseVm.contactLiveData.getValue();
                        if (list != null) {
                            list.get(position).setPhone_type(text);
                            releaseVm.contactLiveData.postValue(list);
                        }
                    }

                    @Override
                    public void unitChange(String text, int position) {
                        List<ContactBean> list = releaseVm.contactLiveData.getValue();
                        if (list != null) {
                            list.get(position).setCompany(text);
                            releaseVm.contactLiveData.postValue(list);
                        }
                    }

                    @Override
                    public void principleChange(String text, int position) {
                        List<ContactBean> list = releaseVm.contactLiveData.getValue();
                        if (list != null) {
                            list.get(position).setName(text);
                            releaseVm.contactLiveData.postValue(list);
                        }
                    }

                    @Override
                    public void mobileChange(String text, int position) {
                        List<ContactBean> list = releaseVm.contactLiveData.getValue();
                        if (list != null) {
                            list.get(position).setTel(text);
                            releaseVm.contactLiveData.postValue(list);
                        }
                    }
                };
                releaseContactAdapter.setHasStableIds(true);
                rvContact.setAdapter(releaseContactAdapter);
                releaseContactAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    switch (view.getId()) {
                        case R.id.iv_del:
                            releaseVm.delContact(position);
                            break;
                        case R.id.tv_address:
                            releaseVm.editContactAddressPos = position;
                            Bundle bundle = new Bundle();
                            bundle.putInt(CustomConfig.PROJECT_ADDRESS, 0);
                            ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                            break;
                    }
                });
            } else {
                releaseContactAdapter.setNewData(contactBeans);
            }
        });

        releaseVm.releaseProjectLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.release_success));
            finish();
        });
        releaseVm.releaseLocusLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.release_success));
            finish();
        });
        releaseVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        //???????????????????????????????????????
        switch (release_type) {
            case 0://????????????
                projectLocus();
                break;
            case 1://????????????
                clickLocus();
                break;
            case 2://????????????????????????????????????
                clickLocus();
                tvProjectType.setClickable(false);
                tvProjectName.setText(getIntent().getStringExtra(CustomConfig.PROJECT_NAME));
                releaseVm.projectId = getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);
                tvProjectName.setClickable(false);
                break;
        }
        KeyboardUtil.RemoveDecimalPoints(edtProjectAcreage);
        KeyboardUtil.RemoveDecimalPoints(edtProjectPrice);
    }

    private void photoSet() {
        picContentView.setListener(new PicContentView.OnClickListener() {
            @Override
            public void add() {
                BaseBottomSheetDialog baseBottomSheetDialog =new BaseBottomSheetDialog(ReleaseProjectActivity.this,rxPermissions,permissionVm);
                baseBottomSheetDialog.showPhotoDialog();
            }

            @Override
            public void see(View view, int position) {
                //??????????????????????????????????????????
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
                //????????????
                PictureEnlargeUtils.getPictureEnlargeList(ReleaseProjectActivity.this, list, position);
            }

            @Override
            public void delete(int pos) {
                releaseVm.delImage(pos);
            }
        });
    }

    private void initPhoto() {
        rxPermissions = new RxPermissions(this);
        permissionVm = getViewModel(PermissionVm.class);
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
                            .setTip("?????????????????????????????????????????????????????????????????????!")
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
        uploadPhotoVm = getViewModel(UploadPhotoVm.class);
        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
            releaseVm.addImage(uploadBean.getImage_id());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(requestCode, resultCode);
        if (requestCode == PermissionVm.PHOTO && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            LogUtils.i(mSelected.get(0), Matisse.obtainPathResult(data).get(0), Uri.parse(Matisse.obtainPathResult(data).get(0)));
            //???????????????????????????
            List<PicContentView.PicContentBean> list = new ArrayList<>();
            for (Uri image : mSelected) {
                list.add(new PicContentView.PicContentBean(image, null, 0));
            }
            picContentView.insert(list);
            if (MatisseCamera.isAndroidQ) {
                LogUtils.e(Matisse.obtainResult(Objects.requireNonNull(data)).get(0));
                uploadPhotoVm.upload(CompressUtils.getRealFilePath(this,Matisse.obtainResult(data).get(0)));
            }else {
                LogUtils.e(Matisse.obtainPathResult(Objects.requireNonNull(data)).get(0));
                uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
            }
//            uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
        }

        if (resultCode == 101) {
            String path = data.getStringExtra("path");
            //???????????????????????????
            List<PicContentView.PicContentBean> list = new ArrayList<>();
            list.add(new PicContentView.PicContentBean(Uri.fromFile(new File(path)), null, 0));
            picContentView.insert(list);
            uploadPhotoVm.upload(path);
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_release, R.id.tv_project_type, R.id.tv_project_address, R.id.tv_project_stage, R.id.tv_project_types, R.id.tv_time_start, R.id.tv_time_end, R.id.tv_add, R.id.tv_project_name, R.id.tv_authentication})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_project_type://????????????
                getBottomWindow();
                btn_dialog.show();
                break;
            case R.id.tv_project_address://????????????
                bundle.putInt(CustomConfig.PROJECT_ADDRESS, 1);
                ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                break;
            case R.id.tv_project_stage://????????????
                ActivityUtils.startActivity(ReleaseStageSelectActivity.class);
                break;
            case R.id.tv_project_types://????????????
                ActivityUtils.startActivity(ReleaseTypeSelectActivity.class);
                break;
            case R.id.tv_time_start://??????????????????????????????
                String startTime = StringUtils.isTrimEmpty(releaseVm.start_time) ? releaseVm.todayTime : releaseVm.start_time;
                showTimeDialog(tvTimeStart, 0, startTime);
                break;
            case R.id.tv_time_end://??????????????????????????????
                if (TextUtils.isEmpty(releaseVm.start_time)) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                try {
                    c.setTime(Objects.requireNonNull(f.parse(releaseVm.todayTime)));
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    String endTime = StringUtils.isTrimEmpty(releaseVm.end_time) ? f.format(c.getTime()) : releaseVm.end_time;
                    showTimeDialog(tvTimeEnd, 1,endTime );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_add://?????????????????????
                releaseVm.addContact();
                break;
            case R.id.tv_project_name://??????????????????
                ActivityUtils.startActivity(ProjectSearchActivity.class);
                break;
            case R.id.tv_release://????????????
                if (releaseVm.type == 1) {
                    releaseVm.releaseProject(edtProjectName.getText().toString(), edtProjectCompany.getText().toString(), edtProjectAcreage.getText().toString(), edtProjectPrice.getText().toString(), edtProjectDetail.getText().toString(), edtProjectMaterial.getText().toString());
                } else if (releaseVm.type == 2) {
                    releaseVm.releaseLocus(edtProjectDes.getText().toString(), edtVisitor.getText().toString(), edtMobile.getText().toString());
                }
                break;
            case R.id.tv_authentication://????????????
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                bundle.putString(CustomConfig.AUTHENTICATION_TYPE, CustomConfig.ID_USER_INFO);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
        }
    }

    //??????????????????
    private void projectLocus() {
        releaseVm.type = 1;
        flProjectNameEdit.setVisibility(View.VISIBLE);
        tvProjectAddress.setClickable(true);
        flProjectStage.setVisibility(View.VISIBLE);
        flProjectTypes.setVisibility(View.VISIBLE);
        rlProjectCycle.setVisibility(View.VISIBLE);
        flProjectAcreage.setVisibility(View.VISIBLE);
        flProjectPrice.setVisibility(View.VISIBLE);
        flProjectDetail.setVisibility(View.VISIBLE);
        flProjectMaterial.setVisibility(View.VISIBLE);
        rvContact.setVisibility(View.VISIBLE);
        flAdd.setVisibility(View.VISIBLE);
        flProjectCompany.setVisibility(View.VISIBLE);
        flProjectAddress.setVisibility(View.VISIBLE);
        flProjectNameSelect.setVisibility(View.GONE);
        flProjectDes.setVisibility(View.GONE);
        flVisitor.setVisibility(View.GONE);
        flMobile.setVisibility(View.GONE);
        flUpdateTime.setVisibility(View.GONE);
        rlPhoto.setVisibility(View.GONE);
        picContentView.setVisibility(View.GONE);
        tvProjectType.setText(getString(R.string.project_project));
    }

    //??????????????????
    private void clickLocus() {
        releaseVm.type = 2;
        flProjectNameEdit.setVisibility(View.GONE);
        tvProjectAddress.setClickable(false);
        flProjectStage.setVisibility(View.GONE);
        flProjectTypes.setVisibility(View.GONE);
        rlProjectCycle.setVisibility(View.GONE);
        flProjectAcreage.setVisibility(View.GONE);
        flProjectPrice.setVisibility(View.GONE);
        flProjectDetail.setVisibility(View.GONE);
        flProjectMaterial.setVisibility(View.GONE);
        rvContact.setVisibility(View.GONE);
        flAdd.setVisibility(View.GONE);
        flProjectCompany.setVisibility(View.GONE);
        flProjectAddress.setVisibility(View.GONE);
        flProjectNameSelect.setVisibility(View.VISIBLE);
        flProjectDes.setVisibility(View.VISIBLE);
        flVisitor.setVisibility(View.VISIBLE);
        flMobile.setVisibility(View.VISIBLE);
        flUpdateTime.setVisibility(View.GONE);
        rlPhoto.setVisibility(View.VISIBLE);
        picContentView.setVisibility(View.VISIBLE);
        tvProjectType.setText(getString(R.string.project_locus));
    }

    /**
     * ???????????????
     */
    private void showTimeDialog(TextView tv, int type, String showTime) {
        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            tv.setText(time);
            if (type == 0) releaseVm.start_time = time;
            else releaseVm.end_time = time;
        }, "yyyy-MM-dd", showTime, "2040-12-31");
        customDatePicker.showSpecificTime(false);
        customDatePicker.show(showTime);
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventLocationEvent(LocationEvent event) {
        releaseVm.province = event.getProvinceId();
        releaseVm.city = event.getCityId();
        releaseVm.address = event.getAddress();
        DecimalFormat df = new DecimalFormat("#.000000");
        releaseVm.longitude = df.format(event.getLongitude());
        releaseVm.latitude = df.format(event.getLatitude());
        tvProjectAddress.setText(event.getProvince() + event.getCity() + event.getAddress());
    }

    @Subscribe
    public void onEventStageEvent(StageEvent event) {
        releaseVm.phases = event.getId();
        tvProjectStage.setText(event.getName());
    }

    @Subscribe
    public void onEventTypesEvent(TypesArrayEvent event) {
        releaseVm.types = event.getId();
        tvProjectTypes.setText(event.getName());
    }

    @Subscribe
    public void onEventContactEvent(ContactTypeEvent event) {
        List<ContactBean> list = releaseVm.contactLiveData.getValue();
        if (list != null) {
            list.get(releaseVm.editContactTypePos).setType(event.getBean());
            list.get(releaseVm.editContactTypePos).setPhone_type(String.valueOf(event.getBean().getId()));
            releaseVm.contactLiveData.postValue(list);
        }
    }

    @Subscribe
    public void onEventAddressEvent(AddressEvent event) {
        List<ContactBean> list = releaseVm.contactLiveData.getValue();
        if (list != null) {
            list.get(releaseVm.editContactAddressPos).setAddress(event.getProvince() + event.getCity());
            list.get(releaseVm.editContactAddressPos).setProvince(event.getProvinceId());
            list.get(releaseVm.editContactAddressPos).setCity(event.getCityId());
            releaseVm.contactLiveData.postValue(list);
        }
    }

    @Subscribe
    public void onEventProjectNameEvent(ProjectNameEvent event) {
        tvProjectName.setText(event.getName());
        releaseVm.projectId = event.getId();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        //????????????EditText????????????EditText??????????????????????????????EditText?????????????????????????????????????????????
        if (view != null && (view.getId() == R.id.edt_project_des && canVerticalScroll(edtProjectDes))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * EditText??????????????????????????????
     *
     * @param editText ???????????????EditText
     * @return true???????????????  false??????????????????
     */
    private boolean canVerticalScroll(EditText editText) {
        //???????????????
        int scrollY = editText.getScrollY();
        //????????????????????????
        int scrollRange = editText.getLayout().getHeight();
        //???????????????????????????
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //???????????????????????????????????????????????????
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    //???????????????
    private TextView tv_project;
    private TextView tv_locus;
    @SuppressLint({"RestrictedApi", "InflateParams"})
    private void getBottomWindow() {
        //?????????????????????
        btn_dialog = new BottomSheetDialogs(this);
        //????????????view
        View dialog_view = this.getLayoutInflater().inflate(R.layout.dialog_release_type, null);
        //?????????????????????
        btn_dialog.setContentView(dialog_view);
        //??????????????????????????????
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_project = dialog_view.findViewById(R.id.tv_project);
        tv_locus = dialog_view.findViewById(R.id.tv_locus);
        tv_project.setOnClickListener(view -> {
            release_type = 0;
            tv_project.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_locus.setTextColor(getResources().getColor(R.color.color_666666));
            projectLocus();
            btn_dialog.cancel();
        });
        tv_locus.setOnClickListener(view -> {
            release_type = 1;
            tv_project.setTextColor(getResources().getColor(R.color.color_666666));
            tv_locus.setTextColor(getResources().getColor(R.color.colorPrimary));
            clickLocus();
            btn_dialog.cancel();
        });

        switch (release_type) {
            case 0://????????????
                tv_project.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_locus.setTextColor(getResources().getColor(R.color.color_666666));
                break;
            case 1://????????????
                tv_project.setTextColor(getResources().getColor(R.color.color_666666));
                tv_locus.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    //??????????????????????????????
    private void getAuthenticationData() {
        if (SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED) != 2) {
            llAuthentication.setVisibility(View.VISIBLE);
            String str = "????????????????????????????????????????????????<font color=\"#6a5ff8\">" + "2?????????" + "</font>???" +
                    "?????????????????????????????????<font color=\"#6a5ff8\">" + "24?????????" + "</font>?????????????????????????????????????????????????????????~";
            tvMarquee.setText(Html.fromHtml(str));
            tvMarquee.setSelected(true);
        } else {
            llAuthentication.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAuthenticationData();
    }
}
