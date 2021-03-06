package com.shuangduan.zcy.adminManage.view.device;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.GroundingAdapter;
import com.shuangduan.zcy.adminManage.adapter.ImagesAdapter;
import com.shuangduan.zcy.adminManage.adapter.MaterialStatusAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorMaterialSecondAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverProjectAdapter;
import com.shuangduan.zcy.adminManage.adapter.UnitAdapter;
import com.shuangduan.zcy.adminManage.adapter.UseStatueAdapter;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailEditBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.dialog.DeviceDialogControl;
import com.shuangduan.zcy.adminManage.event.DeviceEvent;
import com.shuangduan.zcy.adminManage.vm.DeviceAddVm;
import com.shuangduan.zcy.adminManage.vm.DeviceVm;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseBottomSheetDialog;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.LocationEvent;
import com.shuangduan.zcy.utils.PermissionUtils;
import com.shuangduan.zcy.utils.image.CompressUtils;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.utils.matisse.MatisseCamera;
import com.shuangduan.zcy.view.photo.CameraActivity;
import com.shuangduan.zcy.view.release.ReleaseAreaSelectActivity;
import com.shuangduan.zcy.vm.PermissionVm;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.shuangduan.zcy.weight.RoundCheckBox;
import com.shuangduan.zcy.weight.SwitchView;
import com.shuangduan.zcy.weight.TurnoverSelectView;
import com.shuangduan.zcy.weight.XEditText;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.ADD;
import static com.shuangduan.zcy.app.CustomConfig.EDIT;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view.device
 * @ClassName: DeviceAddActivity
 * @Description: ??????????????????
 * @Author: ?????????
 * @CreateDate: 2019/11/6 16:15
 * @UpdateUser: ?????????
 * @UpdateDate: 2019/11/6 16:15
 * @UpdateRemark: ????????????
 * @Version: 1.0
 */
@SuppressLint("SimpleDateFormat,UseSparseArrays")
public class DeviceAddActivity extends BaseActivity implements DeviceDialogControl.DeviceDetailListening {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_category_material_id)
    TextView tvCategoryMaterial_id;
    @BindView(R.id.et_material_name)
    XEditText etMaterialName;
    @BindView(R.id.et_encoding)
    XEditText etEncoding;
    @BindView(R.id.et_stock)
    XEditText etStock;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.et_spec)
    XEditText etSpec;
    @BindView(R.id.tv_use_status)
    TextView tvUseStatus;
    @BindView(R.id.et_address)
    XEditText etAddress;
    @BindView(R.id.et_person_liable)
    XEditText etPersonLiable;
    @BindView(R.id.et_tel)
    XEditText etTel;
    @BindView(R.id.tv_is_shelf)
    TextView tvIsShelf;
    @BindView(R.id.ll_inside_shelf)
    LinearLayout llInsideShelf;
    @BindView(R.id.tv_shelf_time_start)
    TextView tvShelfTimeStart;
    @BindView(R.id.tv_shelf_time_end)
    TextView tvShelfTimeEnd;
    @BindView(R.id.cb_shelf_type_open)
    RoundCheckBox cbShelfTypeOpen;
    @BindView(R.id.cb_shelf_type_close)
    RoundCheckBox cbShelfTypeClose;
    @BindView(R.id.ll_method)
    LinearLayout llMethod;
    @BindView(R.id.cb_lease)
    RoundCheckBox cbLease;
    @BindView(R.id.cb_sell)
    RoundCheckBox cbSell;
    @BindView(R.id.ll_guidance_price)
    LinearLayout llGuidancePrice;
    @BindView(R.id.tv_guidance_price)
    TextView tvGuidancePrice;
    @BindView(R.id.et_guidance_price)
    XEditText etGuidancePrice;
    @BindView(R.id.sv_other_details)
    SwitchView svOtherDetails;
    @BindView(R.id.ll_turnover_detail)
    LinearLayout llTurnoverDetail;
    @BindView(R.id.tv_image_red)
    TextView tvImageRed;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;

    @BindView(R.id.ts_start_time)
    TurnoverSelectView tsStartTime;
    @BindView(R.id.ts_brand)
    TurnoverSelectView tsBrand;
    @BindView(R.id.ts_original_price)
    TurnoverSelectView tsOriginalPrice;
    @BindView(R.id.ts_main_params)
    TurnoverSelectView tsMainParams;
    @BindView(R.id.ts_power)
    TurnoverSelectView tsPower;
    @BindView(R.id.ts_entry_time)
    TurnoverSelectView tsEntryTime;
    @BindView(R.id.ts_exit_time)
    TurnoverSelectView tsExitTime;
    @BindView(R.id.ts_operator_name)
    TurnoverSelectView tsOperatorName;

    @BindView(R.id.tv_material_status)
    TextView tvMaterialStatus;
    @BindView(R.id.et_use_month_count)
    XEditText etUseMonthCount;
    @BindView(R.id.et_plan)
    XEditText etPlan;
    @BindView(R.id.et_technology_detail)
    XEditText etTechnologyDetail;
    @BindView(R.id.et_equipment_time)
    XEditText etEquipmentTime;

    private DeviceVm deviceVm;
    private DeviceAddVm deviceAddVm;
    private UploadPhotoVm uploadPhotoVm;
    private PermissionVm permissionVm;
    private RxPermissions rxPermissions;
    private List<String> picture_list = new ArrayList<>();
    private DeviceDialogControl dialogControl;
    private SimpleDateFormat f;
    private Calendar c;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_device_add;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        int eqipmentId = getIntent().getIntExtra(CustomConfig.EQIPMENT_ID, 0);

        TurnoverVm turnoverVm = getViewModel(TurnoverVm.class);

        deviceVm = getViewModel(DeviceVm.class);
        deviceAddVm = getViewModel(DeviceAddVm.class);
        uploadPhotoVm = getViewModel(UploadPhotoVm.class);

        //???????????????????????????
        switch (getIntent().getIntExtra(CustomConfig.HANDLE_TYPE, 0)) {
            case ADD://??????
                tvBarTitle.setText(R.string.admin_device_material_add);
                break;
            case EDIT://??????
                tvBarTitle.setText(R.string.admin_device_material_edit);
                getEditDetail(eqipmentId);
                break;
        }
        //????????????????????????????????????
        dialogControl = new DeviceDialogControl(this, turnoverVm, this);
        dialogControl.initView();

        //??????????????????
        deviceVm.turnoverProject.observe(this, turnoverNameBeans -> {
            projectList = turnoverNameBeans;
            if (getIntent().getIntExtra(CustomConfig.HANDLE_TYPE, 0) == ADD && projectList.size() != 0) {
                deviceAddVm.unit_id = projectList.get(0).id;
                tvProject.setText(projectList.get(0).name);
                tvProject.setTextColor(getResources().getColor(R.color.colorTv));
            }
        });

        //??????????????????
        deviceVm.deviceFirstData.observe(this, turnoverCategoryBeans -> {
            materialList = turnoverCategoryBeans;
        });

        //??????????????????????????????
        deviceVm.turnoverTypeData.observe(this, turnoverTypeBean -> {
            //????????????
            unitList = turnoverTypeBean.getUnit();
            //??????????????????
            useStatueList = turnoverTypeBean.getUse_status();
            //??????????????????
            groundingList = turnoverTypeBean.getIs_shelf();
            if (SPUtils.getInstance().getInt(CustomConfig.INNER_SWITCH, 0) != 1)
                groundingList.remove(1);
            //??????????????????
            materialStatusList = turnoverTypeBean.getMaterial_status();
        });

        //????????????????????????
        svOtherDetails.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                svOtherDetails.setOpened(!view.isOpened());
                llTurnoverDetail.setVisibility(View.VISIBLE);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                svOtherDetails.setOpened(!view.isOpened());
                llTurnoverDetail.setVisibility(View.GONE);
            }
        });
        //??????????????????????????????
        cbShelfTypeOpen.setChecked(true);
        cbLease.setChecked(true);

        //??????????????????????????????
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

        //??????????????????????????????
        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
            picture_list.add(uploadBean.getSource());
            deviceAddVm.images = getListToString();
            getImageBitmap();
        });

        //????????????????????????????????????
        deviceAddVm.deviceAddData.observe(this, item -> {
            EventBus.getDefault().post(new DeviceEvent(0, ""));
            ToastUtils.showShort("????????????");
            finish();
        });

        deviceVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        deviceAddVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        deviceVm.constructionSearch();
        deviceVm.getUnitInfo();
        deviceVm.equipmentCategoryParent();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_project, R.id.tv_category_material_id, R.id.tv_unit, R.id.tv_use_status
            , R.id.iv_address, R.id.tv_is_shelf, R.id.tv_shelf_time_start, R.id.tv_shelf_time_end, R.id.iv_images
            , R.id.ts_project, R.id.ts_start_time, R.id.ts_brand, R.id.ts_original_price, R.id.ts_main_params, R.id.ts_power, R.id.ts_entry_time, R.id.ts_exit_time, R.id.ts_operator_name
            , R.id.tv_material_status, R.id.tv_reserve})
    void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_project://????????????
                if (projectList.size() != 0) {
                    getBottomSheetDialog(R.layout.dialog_is_grounding, "project");
                } else {
                    ToastUtils.showShort(getString(R.string.admin_selector_no_project_list));
                }
                break;
            case R.id.tv_category_material_id://??????????????????/??????
                getBottomSheetDialog(R.layout.dialog_is_grounding, "category_material_id");
                break;
            case R.id.tv_unit://????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding, "unit");
                break;
            case R.id.tv_use_status://??????????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding, "use_statue");
                break;
            case R.id.iv_address://??????????????????
                bundle.putInt(CustomConfig.PROJECT_ADDRESS, 3);
                ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                break;
            case R.id.tv_is_shelf://??????????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding, "grounding");
                break;
            case R.id.tv_shelf_time_start://??????????????????
                showTimeDialog("shelf_time_start", deviceAddVm.todayTime);
                break;
            case R.id.tv_shelf_time_end://??????????????????
                if (TextUtils.isEmpty(deviceAddVm.shelf_start_time)) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                if (f == null || c == null) {
                    f = new SimpleDateFormat("yyyy-MM-dd");
                    c = Calendar.getInstance();
                }
                try {
                    c.setTime(Objects.requireNonNull(f.parse(deviceAddVm.shelf_start_time)));
                    showTimeDialog("shelf_time_end", f.format(c.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_images://????????????
                if (picture_list != null && picture_list.size() < 5) {
                    BaseBottomSheetDialog baseBottomSheetDialog =new BaseBottomSheetDialog(this,rxPermissions,permissionVm);
                    baseBottomSheetDialog.showPhotoDialog();
                } else {
                    ToastUtils.showShort("????????????5????????????");
                }
                break;
            case R.id.ts_start_time://??????????????????
                dialogControl.showDialog(1, R.string.admin_selector_material_start_time);
                break;
            case R.id.ts_brand://??????
                dialogControl.showDialog(2, R.string.admin_input_material_brand);
                break;
            case R.id.ts_original_price://????????????
                dialogControl.showDialog(3, R.string.admin_input_material_original_price);
                break;
            case R.id.ts_main_params://????????????
                dialogControl.showDialog(4, R.string.admin_input_material_main_params);
                break;
            case R.id.ts_power://??????
                dialogControl.showDialog(5, R.string.admin_input_material_power);
                break;
            case R.id.ts_entry_time://??????????????????
                dialogControl.showDialog(6, R.string.admin_input_device_material_entry_time);
                break;
            case R.id.ts_exit_time://??????????????????
                dialogControl.showDialog(7, R.string.admin_input_device_material_exit_time);
                break;
            case R.id.ts_operator_name://???????????????
                dialogControl.showDialog(8, R.string.admin_input_device_material_operator_name);
                break;
            case R.id.tv_material_status://????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding, "material_status");
                break;
            case R.id.tv_reserve://??????
                deviceAddVm.address = etAddress.getText().toString();
                switch (getIntent().getIntExtra(CustomConfig.HANDLE_TYPE, 0)) {
                    case ADD://??????
                        deviceAddVm.equipmentAdd("add",etMaterialName.getText().toString(), etEncoding.getText().toString(), etStock.getText().toString()
                                , etSpec.getText().toString(), etPersonLiable.getText().toString(), etTel.getText().toString(), etGuidancePrice.getText().toString()
                                , etUseMonthCount.getText().toString(),etPlan.getText().toString(), etTechnologyDetail.getText().toString(), etEquipmentTime.getText().toString());
                        break;
                    case EDIT://??????
                        deviceAddVm.equipmentAdd("edit",etMaterialName.getText().toString(), etEncoding.getText().toString(), etStock.getText().toString()
                                , etSpec.getText().toString(), etPersonLiable.getText().toString(), etTel.getText().toString(), etGuidancePrice.getText().toString()
                                , etUseMonthCount.getText().toString(),etPlan.getText().toString(), etTechnologyDetail.getText().toString(), etEquipmentTime.getText().toString());
                        break;
                }
                break;
        }
    }

    @OnCheckedChanged({R.id.cb_shelf_type_open, R.id.cb_shelf_type_close, R.id.cb_lease, R.id.cb_sell})
    void OnOnCheckedChanged(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.cb_shelf_type_open://??????????????????
                if (isChecked) {
                    cbShelfTypeOpen.setChecked(true);
                    cbShelfTypeClose.setChecked(false);
                    deviceAddVm.shelf_type = 1;
                } else {
                    if (!cbShelfTypeClose.isChecked()) cbShelfTypeOpen.setChecked(true);
                }
                break;
            case R.id.cb_shelf_type_close://??????????????????
                if (isChecked) {
                    cbShelfTypeOpen.setChecked(false);
                    cbShelfTypeClose.setChecked(true);
                    deviceAddVm.shelf_type = 2;
                } else {
                    if (!cbShelfTypeOpen.isChecked()) cbShelfTypeClose.setChecked(true);
                }
                break;
            case R.id.cb_lease://??????
                if (isChecked) {
                    cbLease.setChecked(true);
                    cbSell.setChecked(false);
                    deviceAddVm.method = 1;
                } else {
                    if (!cbSell.isChecked()) cbLease.setChecked(true);
                }
                tvGuidancePrice.setText(getString(R.string.admin_turnover_add_guidance_price_unit));
                break;
            case R.id.cb_sell://??????
                if (isChecked) {
                    cbLease.setChecked(false);
                    cbSell.setChecked(true);
                    deviceAddVm.method = 2;
                } else {
                    if (!cbLease.isChecked()) cbSell.setChecked(true);
                }
                tvGuidancePrice.setText(getString(R.string.admin_turnover_add_guidance_price));
                break;
        }
    }

    private TurnoverProjectAdapter turnoverProjectAdapter;
    private List<TurnoverNameBean> projectList = new ArrayList<>();
    private SelectorMaterialSecondAdapter selectorMaterialSecondAdapter;
    private List<TurnoverCategoryBean> materialList = new ArrayList<>();
    private UnitAdapter unitAdapter;
    private List<TurnoverTypeBean.UnitBean> unitList = new ArrayList<>();
    private UseStatueAdapter useStatueAdapter;
    private List<TurnoverTypeBean.UseStatusBean> useStatueList = new ArrayList<>();
    private GroundingAdapter groundingAdapter;
    private List<TurnoverTypeBean.IsShelfBean> groundingList = new ArrayList<>();
    private MaterialStatusAdapter materialStatusAdapter;
    private List<TurnoverTypeBean.MaterialStatusBean> materialStatusList = new ArrayList<>();
    @SuppressLint({"RestrictedApi,InflateParams", "SetTextI18n"})
    private void getBottomSheetDialog(int layout, String type) {
        //?????????????????????
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(this, R.style.BottomSheetStyle);
        //????????????view
        View dialog_view = this.getLayoutInflater().inflate(layout, null);
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
        deviceVm.type = type;
        switch (type) {
            case "project":
                TextView tvProjects = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvProjects).setText("????????????");
                RecyclerView rvProject = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvProject).setLayoutManager(new LinearLayoutManager(this));
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project, projectList, 12);
                rvProject.setAdapter(turnoverProjectAdapter);
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceAddVm.unit_id = projectList.get(position).id;
                    tvProject.setText(projectList.get(position).name);
                    tvProject.setTextColor(getResources().getColor(R.color.colorTv));
                    turnoverProjectAdapter.setIsSelect(deviceAddVm.unit_id);
                    btn_dialog.dismiss();
                });
                if (deviceAddVm.unit_id != 0) {
                    turnoverProjectAdapter.setIsSelect(deviceAddVm.unit_id);
                }
                break;
            case "category_material_id"://??????????????????/??????
                TextView tvMaterialId = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvMaterialId).setText("??????????????????");
                RecyclerView rvMaterialId = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvMaterialId).setLayoutManager(new LinearLayoutManager(this));
                selectorMaterialSecondAdapter = new SelectorMaterialSecondAdapter(R.layout.adapter_selector_area_second,materialList);
                rvMaterialId.setAdapter(selectorMaterialSecondAdapter);
                selectorMaterialSecondAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceAddVm.category = materialList.get(position).getId();
                    tvCategoryMaterial_id.setText(materialList.get(position).getCatname());
                    tvCategoryMaterial_id.setTextColor(getResources().getColor(R.color.colorTv));
                    selectorMaterialSecondAdapter.setIsSelect(deviceAddVm.category);
                    btn_dialog.dismiss();
                });
                if (deviceAddVm.category != 0) {
                    selectorMaterialSecondAdapter.setIsSelect(deviceAddVm.category);
                }
                break;
            case "unit"://????????????
                TextView tvUnits = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvUnits).setText("????????????");
                RecyclerView rvUnit = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvUnit).setLayoutManager(new LinearLayoutManager(this));
                unitAdapter = new UnitAdapter(R.layout.adapter_selector_area_second, unitList);
                rvUnit.setAdapter(unitAdapter);
                unitAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceAddVm.unit = unitList.get(position).getId();
                    tvUnit.setText(unitList.get(position).getName());
                    tvUnit.setTextColor(getResources().getColor(R.color.colorTv));
                    unitAdapter.setIsSelect(deviceAddVm.unit);
                    btn_dialog.dismiss();
                });
                if (deviceAddVm.unit != 0) {
                    unitAdapter.setIsSelect(deviceAddVm.unit);
                }
                break;
            case "use_statue"://??????????????????
                TextView tvUseStatue = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvUseStatue).setText("????????????");
                RecyclerView rvUseStatue = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvUseStatue).setLayoutManager(new LinearLayoutManager(this));
                useStatueAdapter = new UseStatueAdapter(R.layout.adapter_selector_area_second, useStatueList);
                rvUseStatue.setAdapter(useStatueAdapter);
                useStatueAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceAddVm.use_status = useStatueList.get(position).getId();
                    tvUseStatus.setText(useStatueList.get(position).getName());
                    tvUseStatus.setTextColor(getResources().getColor(R.color.colorTv));
                    useStatueAdapter.setIsSelect(deviceAddVm.use_status);
                    btn_dialog.dismiss();
                });

                if (deviceAddVm.use_status != 0) {
                    useStatueAdapter.setIsSelect(deviceAddVm.use_status);
                }
                break;
            case "grounding"://??????????????????
                TextView tvGrounding = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvGrounding).setText("????????????");
                RecyclerView rvGrounding = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvGrounding).setLayoutManager(new LinearLayoutManager(this));
                groundingAdapter = new GroundingAdapter(R.layout.adapter_selector_area_second, groundingList);
                rvGrounding.setAdapter(groundingAdapter);
                groundingAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceAddVm.is_shelf = groundingList.get(position).getId();
                    tvIsShelf.setText(groundingList.get(position).getName());
                    tvIsShelf.setTextColor(getResources().getColor(R.color.colorTv));
                    groundingAdapter.setIsSelect(deviceAddVm.is_shelf);
                    btn_dialog.dismiss();
                    switch (groundingList.get(position).getName()) {
                        case "?????????":
                            llInsideShelf.setVisibility(View.GONE);
                            llMethod.setVisibility(View.GONE);
                            llGuidancePrice.setVisibility(View.GONE);
                            tvImageRed.setVisibility(View.INVISIBLE);
                            break;
                        case "????????????":
                            llInsideShelf.setVisibility(View.GONE);
                            llMethod.setVisibility(View.VISIBLE);
                            llGuidancePrice.setVisibility(View.VISIBLE);
                            tvImageRed.setVisibility(View.INVISIBLE);
                            break;
                        case "????????????":
                            llInsideShelf.setVisibility(View.VISIBLE);
                            llMethod.setVisibility(View.VISIBLE);
                            llGuidancePrice.setVisibility(View.VISIBLE);
                            tvImageRed.setVisibility(View.INVISIBLE);
                            break;
                    }
                });
                if (deviceAddVm.is_shelf != 0) {
                    groundingAdapter.setIsSelect(deviceAddVm.is_shelf);
                }
                break;
            case "material_status"://????????????
                TextView tv_material_status = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tv_material_status).setText("????????????");
                RecyclerView rvMaterialStatus = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvMaterialStatus).setLayoutManager(new LinearLayoutManager(this));
                materialStatusAdapter = new MaterialStatusAdapter(R.layout.adapter_selector_area_second, materialStatusList);
                rvMaterialStatus.setAdapter(materialStatusAdapter);
                materialStatusAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceAddVm.material_status = materialStatusList.get(position).getId();
                    tvMaterialStatus.setText(materialStatusList.get(position).getName());
                    tvMaterialStatus.setTextColor(getResources().getColor(R.color.colorTv));
                    materialStatusAdapter.setIsSelect(deviceAddVm.material_status);
                    btn_dialog.dismiss();
                });
                if (deviceAddVm.material_status != 0) {
                    materialStatusAdapter.setIsSelect(deviceAddVm.material_status);
                }
                break;
        }
        btn_dialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventLocationEvent(LocationEvent event) {
        deviceAddVm.province = event.getProvinceId();
        deviceAddVm.city = event.getCityId();
        deviceAddVm.address = event.getAddress();
        deviceAddVm.latitude = event.getLatitude();
        deviceAddVm.longitude = event.getLongitude();
        etAddress.setText(event.getAddress());
    }

    //???????????????
    private void showTimeDialog(String type, String showTime) {
        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            switch (type) {
                case "shelf_time_start"://??????????????????
                    deviceAddVm.shelf_start_time = time;
                    tvShelfTimeStart.setText(time);
                    tvShelfTimeStart.setTextColor(getResources().getColor(R.color.colorTv));
                    break;
                case "shelf_time_end"://??????????????????
                    deviceAddVm.shelf_end_time = time;
                    tvShelfTimeEnd.setText(time);
                    tvShelfTimeEnd.setTextColor(getResources().getColor(R.color.colorTv));
                    break;
            }
        }, "yyyy-MM-dd", showTime, "2040-12-31");
        customDatePicker.showSpecificTime(false);

        if (type.equals("shelf_time_start")) {
            customDatePicker.show(TimeUtils.getNowString());
        } else {
            try {
                c.setTime(Objects.requireNonNull(f.parse(showTime)));
                c.add(Calendar.DAY_OF_MONTH, 1);
                customDatePicker.show(f.format(c.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ????????????????????????
        if (resultCode == 101) {
            String path = Objects.requireNonNull(data).getStringExtra("path");
            File file = new File(Objects.requireNonNull(path));
            LogUtils.i(file);
            uploadPhotoVm.upload(path);
        }
        if (resultCode == 103) {
            ToastUtils.showShort("???????????????????????????");
        }

        //????????????????????????
        if (requestCode == PermissionVm.PHOTO && resultCode == RESULT_OK) {
            if (MatisseCamera.isAndroidQ) {
                LogUtils.e(Matisse.obtainResult(Objects.requireNonNull(data)).get(0));
                uploadPhotoVm.upload(CompressUtils.getRealFilePath(this,Matisse.obtainResult(data).get(0)));
            }else {
                LogUtils.e(Matisse.obtainPathResult(Objects.requireNonNull(data)).get(0));
                uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
            }
        }
    }

    //????????????????????????
    private void getImageBitmap() {
        if (picture_list != null && picture_list.size() != 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            rvImages.setLayoutManager(gridLayoutManager);
            ImagesAdapter imagesAdapter = new ImagesAdapter(R.layout.adapter_images, picture_list);
            rvImages.setAdapter(imagesAdapter);
            imagesAdapter.setOnItemClickListener((adapter, view, position) -> {
                PictureEnlargeUtils.getPictureEnlargeList(this, picture_list, position);
            });
            imagesAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                if (view.getId() == R.id.iv_delete) {
                    imagesAdapter.removeData(picture_list, position);
                    deviceAddVm.images = getListToString();
                }
            });
        }
    }

    //list?????????String???????????????
    private String getListToString() {
        StringBuilder sb = new StringBuilder();
        for (String str : picture_list) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(str);
        }
        LogUtils.i(sb.toString());
        return sb.toString();
    }

    //??????????????????????????????
    @SuppressLint("SetTextI18n")
    private void getEditDetail(int id) {
        deviceAddVm.editId = id;
        deviceVm.equipmentEditShow(id);
        deviceVm.deviceDetailEditLiveData.observe(this, deviceDetailEditBean -> {
            deviceAddVm.unit_id = deviceDetailEditBean.getUnit_id();
            tvProject.setText(deviceDetailEditBean.getUnit_id_name());
            tvProject.setTextColor(getResources().getColor(R.color.colorTv));
            deviceAddVm.category = deviceDetailEditBean.getCategory();
            tvCategoryMaterial_id.setText(deviceDetailEditBean.getCategory_name());
            tvCategoryMaterial_id.setTextColor(getResources().getColor(R.color.colorTv));
            etMaterialName.setText(deviceDetailEditBean.getMaterial_name());
            etEncoding.setText(deviceDetailEditBean.getEncoding());
            etStock.setText(deviceDetailEditBean.getStock());
            deviceAddVm.unit = deviceDetailEditBean.getUnit();
            tvUnit.setText(deviceDetailEditBean.getUnit_name());
            tvUnit.setTextColor(getResources().getColor(R.color.colorTv));
            etSpec.setText(deviceDetailEditBean.getSpec());
            deviceAddVm.use_status = deviceDetailEditBean.getUse_status();
            tvUseStatus.setText(deviceDetailEditBean.getUse_status_name());
            tvUseStatus.setTextColor(getResources().getColor(R.color.colorTv));
            deviceAddVm.province = deviceDetailEditBean.getProvince();
            deviceAddVm.city = deviceDetailEditBean.getCity();
            deviceAddVm.address = deviceDetailEditBean.getAddress();
            deviceAddVm.latitude = deviceDetailEditBean.getLatitude();
            deviceAddVm.longitude = deviceDetailEditBean.getLongitude();
            etAddress.setText(deviceDetailEditBean.getAddress());
            etPersonLiable.setText(deviceDetailEditBean.getPerson_liable());
            etTel.setText(deviceDetailEditBean.getTel());
            deviceAddVm.is_shelf = deviceDetailEditBean.getIs_shelf();
            tvIsShelf.setText(deviceDetailEditBean.getIs_shelf_name());
            tvIsShelf.setTextColor(getResources().getColor(R.color.colorTv));
            switch (deviceAddVm.is_shelf) {
                case 1://????????????
                    llInsideShelf.setVisibility(View.GONE);
                    llMethod.setVisibility(View.VISIBLE);
                    llGuidancePrice.setVisibility(View.VISIBLE);
                    tvImageRed.setVisibility(View.VISIBLE);
                    etGuidancePrice.setText(deviceDetailEditBean.getGuidance_price());
                    deviceAddVm.method = deviceDetailEditBean.getMethod();
                    if (deviceAddVm.method == 1) {
                        cbLease.setChecked(true);
                        cbSell.setChecked(false);
                    } else {
                        cbLease.setChecked(false);
                        cbSell.setChecked(true);
                    }
                    break;
                case 2://?????????
                    llInsideShelf.setVisibility(View.GONE);
                    llMethod.setVisibility(View.GONE);
                    llGuidancePrice.setVisibility(View.GONE);
                    tvImageRed.setVisibility(View.INVISIBLE);
                    break;
                case 3://????????????
                    llInsideShelf.setVisibility(View.VISIBLE);
                    llMethod.setVisibility(View.VISIBLE);
                    llGuidancePrice.setVisibility(View.VISIBLE);
                    tvImageRed.setVisibility(View.VISIBLE);
                    etGuidancePrice.setText(deviceDetailEditBean.getGuidance_price());
                    deviceAddVm.shelf_start_time = deviceDetailEditBean.getShelf_start_time();
                    tvShelfTimeStart.setText(deviceDetailEditBean.getShelf_start_time());
                    tvShelfTimeStart.setTextColor(getResources().getColor(R.color.colorTv));
                    deviceAddVm.shelf_end_time = deviceDetailEditBean.getShelf_end_time();
                    tvShelfTimeEnd.setText(deviceDetailEditBean.getShelf_end_time());
                    tvShelfTimeEnd.setTextColor(getResources().getColor(R.color.colorTv));
                    deviceAddVm.shelf_type = deviceDetailEditBean.getShelf_type();
                    if (deviceAddVm.shelf_type == 1) {
                        cbShelfTypeOpen.setChecked(true);
                        cbShelfTypeClose.setChecked(false);
                    } else {
                        cbShelfTypeOpen.setChecked(false);
                        cbShelfTypeClose.setChecked(true);
                    }
                    deviceAddVm.method = deviceDetailEditBean.getMethod();
                    if (deviceAddVm.method == 1) {
                        cbLease.setChecked(true);
                        cbSell.setChecked(false);
                    } else {
                        cbLease.setChecked(false);
                        cbSell.setChecked(true);
                    }
                    break;
            }
            for (DeviceDetailEditBean.ImagesBean img : deviceDetailEditBean.getImages()) {
                picture_list.add(img.getUrl());
            }
            deviceAddVm.images = getListToString();
            getImageBitmap();

            deviceAddVm.start_date = deviceDetailEditBean.getStart_date();
            tsStartTime.setValue(deviceDetailEditBean.getStart_date());
            deviceAddVm.brand = deviceDetailEditBean.getBrand();
            tsBrand.setValue(deviceDetailEditBean.getBrand());
            deviceAddVm.original_price = deviceDetailEditBean.getOriginal_price();
            tsOriginalPrice.setValue(deviceDetailEditBean.getOriginal_price());
            deviceAddVm.main_params = deviceDetailEditBean.getMain_params();
            tsMainParams.setValue(deviceDetailEditBean.getMain_params());
            deviceAddVm.power = deviceDetailEditBean.getPower();
            tsPower.setValue(deviceDetailEditBean.getPower());
            deviceAddVm.entry_time = deviceDetailEditBean.getEntry_time();
            tsEntryTime.setValue(deviceDetailEditBean.getEntry_time());
            deviceAddVm.exit_time = deviceDetailEditBean.getExit_time();
            tsExitTime.setValue(deviceDetailEditBean.getExit_time());
            deviceAddVm.operator_name = deviceDetailEditBean.getOperator_name();
            tsOperatorName.setValue(deviceDetailEditBean.getOperator_name());

            dialogControl.setDetail(deviceDetailEditBean.getStart_date()
                    , deviceDetailEditBean.getBrand(), String.valueOf(deviceDetailEditBean.getOriginal_price()), deviceDetailEditBean.getMain_params(), String.valueOf(deviceDetailEditBean.getPower())
                    , deviceDetailEditBean.getEntry_time(), deviceDetailEditBean.getExit_time(), deviceDetailEditBean.getOperator_name());

            if (!StringUtils.isTrimEmpty(deviceDetailEditBean.getStart_date()) || !StringUtils.isTrimEmpty(deviceDetailEditBean.getBrand())
                    || !StringUtils.isTrimEmpty(deviceDetailEditBean.getOriginal_price()) || !StringUtils.isTrimEmpty(deviceDetailEditBean.getMain_params())
                    || !StringUtils.isTrimEmpty(deviceDetailEditBean.getPower()) || !StringUtils.isTrimEmpty(deviceDetailEditBean.getEntry_time())
                    || !StringUtils.isTrimEmpty(deviceDetailEditBean.getExit_time()) || !StringUtils.isTrimEmpty(deviceDetailEditBean.getOperator_name())) {
                svOtherDetails.setOpened(true);
                llTurnoverDetail.setVisibility(View.VISIBLE);
            }

            deviceAddVm.material_status = deviceDetailEditBean.getMaterial_status();
            tvMaterialStatus.setText(deviceDetailEditBean.getMaterial_status_name());
            tvMaterialStatus.setTextColor(getResources().getColor(R.color.colorTv));
            etUseMonthCount.setText(deviceDetailEditBean.getUse_month_count());
            etPlan.setText(deviceDetailEditBean.getPlan());
            etTechnologyDetail.setText(deviceDetailEditBean.getTechnology_detail());
            etEquipmentTime.setText(deviceDetailEditBean.getEquipment_time());
        });

        //??????????????????????????????
        deviceAddVm.deviceEditData.observe(this, item -> {
            EventBus.getDefault().post(new DeviceEvent(0, ""));
            ToastUtils.showShort("????????????");
            finish();
        });
    }

    @Override
    public void callInfo(String brand, String start_date, String operator_name, String original_price,
                         String main_params, String power, String entry_time, String exit_time) {
        deviceAddVm.brand = brand;
        deviceAddVm.start_date = start_date;
        deviceAddVm.operator_name = operator_name;
        deviceAddVm.original_price = original_price;
        deviceAddVm.main_params = main_params;
        deviceAddVm.power = power;
        deviceAddVm.entry_time = entry_time;
        deviceAddVm.exit_time = exit_time;

        tsBrand.setValue(brand);
        tsStartTime.setValue(start_date);
        tsOperatorName.setValue(operator_name);
        tsOriginalPrice.setValue(original_price);
        tsMainParams.setValue(main_params);
        tsPower.setValue(power);
        tsEntryTime.setValue(entry_time);
        tsExitTime.setValue(exit_time);
    }
}
