package com.shuangduan.zcy.adminManage.view.turnover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.GroundingAdapter;
import com.shuangduan.zcy.adminManage.adapter.ImagesAdapter;
import com.shuangduan.zcy.adminManage.adapter.MaterialStatusAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorCategoryFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorMaterialSecondAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverProjectAdapter;
import com.shuangduan.zcy.adminManage.adapter.UnitAdapter;
import com.shuangduan.zcy.adminManage.adapter.UseStatueAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailEditBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.dialog.TurnoverDialogControl;
import com.shuangduan.zcy.adminManage.event.TurnoverEvent;
import com.shuangduan.zcy.adminManage.vm.TurnoverAddVm;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.LocationEvent;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.utils.matisse.Glide4Engine;
import com.shuangduan.zcy.view.photo.CameraActivity;
import com.shuangduan.zcy.view.release.ReleaseAreaSelectActivity;
import com.shuangduan.zcy.vm.UploadPhotoVm;
import com.shuangduan.zcy.weight.RoundCheckBox;
import com.shuangduan.zcy.weight.SwitchView;
import com.shuangduan.zcy.weight.TurnoverSelectView;
import com.shuangduan.zcy.weight.XEditText;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;
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
 * @Package: com.shuangduan.zcy.adminManage.view.turnover
 * @ClassName: TurnoverAddActivity
 * @Description: 周转材料添加
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/1 9:05
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/1 9:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("SimpleDateFormat,UseSparseArrays")
public class TurnoverAddActivity extends BaseActivity implements TurnoverDialogControl.TurnoverDetailListening {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_category_material_id)
    TextView tvCategoryMaterial_id;
    @BindView(R.id.et_material_name)
    XEditText etMaterialName;
    @BindView(R.id.et_stock)
    XEditText etStock;
    @BindView(R.id.et_unit_price)
    XEditText etUnitPrice;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.et_spec)
    XEditText etSpec;
    @BindView(R.id.tv_use_status)
    TextView tvUseStatus;
    @BindView(R.id.tv_material_status)
    TextView tvMaterialStatus;
    @BindView(R.id.et_address)
    XEditText etAddress;
    @BindView(R.id.et_person_liable)
    XEditText etPersonLiable;
    @BindView(R.id.et_tel)
    XEditText etTel;
    @BindView(R.id.cb_yes)
    RoundCheckBox cbYes;
    @BindView(R.id.cb_no)
    RoundCheckBox cbNo;
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

    @BindView(R.id.ts_plan)
    TurnoverSelectView tsPlan;
    @BindView(R.id.ts_num)
    TurnoverSelectView tsNum;
    @BindView(R.id.ts_start_time)
    TurnoverSelectView tsStartTime;
    @BindView(R.id.ts_enter_time)
    TurnoverSelectView tsEnterTime;
    @BindView(R.id.ts_exit_time)
    TurnoverSelectView tsExitTime;
    @BindView(R.id.ts_amortize)
    TurnoverSelectView tsAmortize;
    @BindView(R.id.ts_original)
    TurnoverSelectView tsOriginal;
    @BindView(R.id.ts_value)
    TurnoverSelectView tsValue;
    @BindView(R.id.et_remark)
    EditText etRemark;

    private TurnoverVm turnoverVm;
    private TurnoverAddVm turnoverAddVm;
    private UploadPhotoVm uploadPhotoVm;
    private List<String> picture_list = new ArrayList<>();
    private TurnoverDialogControl dialogControl;
    private SimpleDateFormat f;
    private Calendar c;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_turnover_add;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvBarTitle.setText(R.string.admin_turnover_material_add);

        int constructionId = getIntent().getIntExtra(CustomConfig.CONSTRUCTION_ID, 0);
        turnoverVm = ViewModelProviders.of(this).get(TurnoverVm.class);
        turnoverAddVm = ViewModelProviders.of(this).get(TurnoverAddVm.class);
        uploadPhotoVm = ViewModelProviders.of(this).get(UploadPhotoVm.class);

        //判断时添加还是编辑
        switch (getIntent().getIntExtra(CustomConfig.HANDLE_TYPE, 0)) {
            case ADD://添加
                break;
            case EDIT://编辑
                getEditDetail(constructionId);
                break;
        }

        //获取项目名称
        turnoverVm.turnoverProject.observe(this, turnoverNameBeans -> {
            projectList = turnoverNameBeans;
            if (getIntent().getIntExtra(CustomConfig.HANDLE_TYPE, 0) == ADD && projectList.size() != 0) {
                turnoverAddVm.unit_id = projectList.get(0).id;
                tvProject.setText(projectList.get(0).name);
                tvProject.setTextColor(getResources().getColor(R.color.colorTv));
            }
        });

        //获取材料类别
        turnoverVm.turnoverFirstData.observe(this, turnoverCategoryBeans -> {
            materialList = turnoverCategoryBeans;
        });

        //详细信息输入弹出框初始化
        dialogControl = new TurnoverDialogControl(this, turnoverVm, this);
        dialogControl.initView();

        //获取筛选条件列表数据
        turnoverVm.turnoverTypeData.observe(this, turnoverTypeBean -> {
            //获取单位
            unitList = turnoverTypeBean.getUnit();
            //获取使用状态
            useStatueList = turnoverTypeBean.getUse_status();
            //获取材料状态
            materialStatusList = turnoverTypeBean.getMaterial_status();
            //获取是否上架
            groundingList = turnoverTypeBean.getIs_shelf();
            if (SPUtils.getInstance().getInt(CustomConfig.INNER_SWITCH, 0) != 1)
                groundingList.remove(1);
        });

        //详细信息开关隐藏
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
        //设置选择按钮默认选中
        cbShelfTypeOpen.setChecked(true);
        cbLease.setChecked(true);
        cbNo.setChecked(true);

        //图片上传转换返回地址
        uploadPhotoVm.uploadLiveData.observe(this, uploadBean -> {
            picture_list.add(uploadBean.getSource());
            turnoverAddVm.images = getListToString();
            getImageBitmap();
        });

        //添加周转材料成功返回结果
        turnoverAddVm.turnoverAddData.observe(this, item -> {
            ToastUtils.showShort("添加成功");
            EventBus.getDefault().post(new TurnoverEvent(0, ""));
            finish();
        });

        turnoverVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        turnoverAddVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        turnoverVm.constructionSearch();
        turnoverVm.getUnitInfo();
        turnoverVm.constructionCategoryParent();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_project, R.id.tv_category_material_id, R.id.tv_unit, R.id.tv_use_status, R.id.tv_material_status
            , R.id.iv_address, R.id.tv_is_shelf, R.id.tv_shelf_time_start, R.id.tv_shelf_time_end, R.id.iv_images
            , R.id.ts_project, R.id.ts_plan, R.id.ts_num, R.id.ts_start_time, R.id.ts_enter_time, R.id.ts_exit_time, R.id.ts_amortize, R.id.ts_original, R.id.ts_value
            , R.id.tv_reserve})
    void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_project://选择所属项目
                if (projectList.size() != 0) {
                    getBottomSheetDialog(R.layout.dialog_is_grounding, "project");
                } else {
                    ToastUtils.showShort(getString(R.string.admin_selector_no_project_list));
                }
                break;
            case R.id.tv_category_material_id://选择材料类别
                getBottomSheetDialog(R.layout.dialog_is_grounding, "category_material_id");
                break;
            case R.id.tv_unit://选择单位
                getBottomSheetDialog(R.layout.dialog_is_grounding, "unit");
                break;
            case R.id.tv_use_status://选择使用状态
                getBottomSheetDialog(R.layout.dialog_is_grounding, "use_statue");
                break;
            case R.id.tv_material_status://选择材料状态
                getBottomSheetDialog(R.layout.dialog_is_grounding, "material_status");
                break;
            case R.id.iv_address://选择存放地点
                bundle.putInt(CustomConfig.PROJECT_ADDRESS, 3);
                ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                break;
            case R.id.tv_is_shelf://选择是否上架
                getBottomSheetDialog(R.layout.dialog_is_grounding, "grounding");
                break;
            case R.id.tv_shelf_time_start://选择开始时间
                showTimeDialog("shelf_time_start", turnoverAddVm.todayTime);
                break;
            case R.id.tv_shelf_time_end://选择结束时间
                if (TextUtils.isEmpty(turnoverAddVm.shelf_start_time)) {
                    ToastUtils.showShort("请先选择开始时间");
                    return;
                }
                if (f == null || c == null) {
                    f = new SimpleDateFormat("yyyy-MM-dd");
                    c = Calendar.getInstance();
                }
                try {
                    c.setTime(Objects.requireNonNull(f.parse(turnoverAddVm.shelf_start_time)));
                    showTimeDialog("shelf_time_end", f.format(c.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_images://上传图片
                if (picture_list != null && picture_list.size() < 5) {
                    getPermissions();
                } else {
                    ToastUtils.showShort("最多上传5张照片哦");
                }
                break;
            case R.id.ts_plan://预计下步使用计划
                dialogControl.showDialog(1, R.string.admin_selector_material_plan);
                break;
            case R.id.ts_num://在用数量
                dialogControl.showDialog(2, R.string.admin_input_material_num);
                break;
            case R.id.ts_start_time://开始使用日期
                dialogControl.showDialog(3, R.string.admin_selector_material_start_time);
                break;
            case R.id.ts_enter_time://材料进场时间
                dialogControl.showDialog(4, R.string.admin_selector_material_enter_time);
                break;
            case R.id.ts_exit_time://材料退场时间
                dialogControl.showDialog(5, R.string.admin_selector_material_exit_time);
                break;
            case R.id.ts_amortize://累计摊销
                dialogControl.showDialog(6, R.string.admin_input_material_amortize);
                break;
            case R.id.ts_original://周转材料原值
                dialogControl.showDialog(7, R.string.admin_input_material_original);
                break;
            case R.id.ts_value://净值
                dialogControl.showDialog(8, R.string.admin_input_material_value);
                break;
            case R.id.tv_reserve://提交
                switch (getIntent().getIntExtra(CustomConfig.HANDLE_TYPE, 0)) {
                    case ADD://添加
                        turnoverAddVm.constructionAdd("add", etMaterialName.getText().toString(),etStock.getText().toString(), etUnitPrice.getText().toString()
                                , etSpec.getText().toString(), etPersonLiable.getText().toString(), etTel.getText().toString(), etGuidancePrice.getText().toString(), etRemark.getText().toString());
                        break;
                    case EDIT://编辑
                        turnoverAddVm.constructionAdd("edit", etMaterialName.getText().toString(), etStock.getText().toString(), etUnitPrice.getText().toString()
                                , etSpec.getText().toString(), etPersonLiable.getText().toString(), etTel.getText().toString(), etGuidancePrice.getText().toString(), etRemark.getText().toString());
                        break;
                }
                break;
        }
    }

    @OnCheckedChanged({R.id.cb_shelf_type_open, R.id.cb_shelf_type_close, R.id.cb_lease, R.id.cb_sell, R.id.cb_yes, R.id.cb_no})
    void OnOnCheckedChanged(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.cb_shelf_type_open://到期自动公开
                if (isChecked) {
                    cbShelfTypeOpen.setChecked(true);
                    cbShelfTypeClose.setChecked(false);
                    turnoverAddVm.shelf_type = 1;
                } else {
                    if (!cbShelfTypeClose.isChecked()) cbShelfTypeOpen.setChecked(true);
                }
                break;
            case R.id.cb_shelf_type_close://到期自动下架
                if (isChecked) {
                    cbShelfTypeOpen.setChecked(false);
                    cbShelfTypeClose.setChecked(true);
                    turnoverAddVm.shelf_type = 2;
                } else {
                    if (!cbShelfTypeOpen.isChecked()) cbShelfTypeClose.setChecked(true);
                }
                break;
            case R.id.cb_lease://出租
                if (isChecked) {
                    cbLease.setChecked(true);
                    cbSell.setChecked(false);
                    turnoverAddVm.method = 1;
                } else {
                    if (!cbSell.isChecked()) cbLease.setChecked(true);
                }
                tvGuidancePrice.setText(getString(R.string.admin_turnover_add_guidance_price_unit));
                break;
            case R.id.cb_sell://出售
                if (isChecked) {
                    cbLease.setChecked(false);
                    cbSell.setChecked(true);
                    turnoverAddVm.method = 2;
                } else {
                    if (!cbLease.isChecked()) cbSell.setChecked(true);
                }
                tvGuidancePrice.setText(getString(R.string.admin_turnover_add_guidance_price));
                break;
            case R.id.cb_yes://是
                if (isChecked) {
                    cbYes.setChecked(true);
                    cbNo.setChecked(false);
                    turnoverAddVm.is_vulnerable = 2;
                } else {
                    if (!cbNo.isChecked()) cbYes.setChecked(true);
                }
                break;
            case R.id.cb_no://否
                if (isChecked) {
                    cbYes.setChecked(false);
                    cbNo.setChecked(true);
                    turnoverAddVm.is_vulnerable = 1;
                } else {
                    if (!cbYes.isChecked()) cbNo.setChecked(true);
                }
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
    private MaterialStatusAdapter materialStatusAdapter;
    private List<TurnoverTypeBean.MaterialStatusBean> materialStatusList = new ArrayList<>();
    private GroundingAdapter groundingAdapter;
    private List<TurnoverTypeBean.IsShelfBean> groundingList = new ArrayList<>();
    @SuppressLint({"RestrictedApi,InflateParams", "SetTextI18n"})
    private void getBottomSheetDialog(int layout, String type) {
        //底部滑动对话框
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(this, R.style.BottomSheetStyle);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(layout, null);
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
        turnoverVm.type = type;
        switch (type) {
            case "project":
                TextView tvProjects = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvProjects).setText("选择项目");
                RecyclerView rvProject = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvProject).setLayoutManager(new LinearLayoutManager(this));
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project, projectList, 12);
                rvProject.setAdapter(turnoverProjectAdapter);
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverAddVm.unit_id = projectList.get(position).id;
                    tvProject.setText(projectList.get(position).name);
                    tvProject.setTextColor(getResources().getColor(R.color.colorTv));
                    turnoverProjectAdapter.setIsSelect(turnoverAddVm.unit_id);
                    btn_dialog.dismiss();
                });
                if (turnoverAddVm.unit_id != 0) {
                    turnoverProjectAdapter.setIsSelect(turnoverAddVm.unit_id);
                }
                break;
            case "category_material_id"://选择材料类别
                TextView tvMaterialId = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvMaterialId).setText("选择材料类别");
                RecyclerView rvMaterialId = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvMaterialId).setLayoutManager(new LinearLayoutManager(this));
                selectorMaterialSecondAdapter = new SelectorMaterialSecondAdapter(R.layout.adapter_selector_area_second,materialList);
                rvMaterialId.setAdapter(selectorMaterialSecondAdapter);
                selectorMaterialSecondAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverAddVm.category = materialList.get(position).getId();
                    tvCategoryMaterial_id.setText(materialList.get(position).getCatname());
                    tvCategoryMaterial_id.setTextColor(getResources().getColor(R.color.colorTv));
                    selectorMaterialSecondAdapter.setIsSelect(turnoverAddVm.category);
                    btn_dialog.dismiss();
                });
                if (turnoverAddVm.category != 0) {
                    selectorMaterialSecondAdapter.setIsSelect(turnoverAddVm.category);
                }
                break;
            case "unit"://选择单位
                TextView tvUnits = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvUnits).setText("选择单位");
                RecyclerView rvUnit = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvUnit).setLayoutManager(new LinearLayoutManager(this));
                unitAdapter = new UnitAdapter(R.layout.adapter_selector_area_second, unitList);
                rvUnit.setAdapter(unitAdapter);
                unitAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverAddVm.unit = unitList.get(position).getId();
                    tvUnit.setText(unitList.get(position).getName());
                    tvUnit.setTextColor(getResources().getColor(R.color.colorTv));
                    unitAdapter.setIsSelect(turnoverAddVm.unit);
                    btn_dialog.dismiss();
                });
                if (turnoverAddVm.unit != 0) {
                    unitAdapter.setIsSelect(turnoverAddVm.unit);
                }
                break;
            case "use_statue"://使用状态弹窗
                TextView tvUseStatue = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvUseStatue).setText("使用状态");
                RecyclerView rvUseStatue = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvUseStatue).setLayoutManager(new LinearLayoutManager(this));
                useStatueAdapter = new UseStatueAdapter(R.layout.adapter_selector_area_second, useStatueList);
                rvUseStatue.setAdapter(useStatueAdapter);
                useStatueAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverAddVm.use_status = useStatueList.get(position).getId();
                    tvUseStatus.setText(useStatueList.get(position).getName());
                    tvUseStatus.setTextColor(getResources().getColor(R.color.colorTv));
                    useStatueAdapter.setIsSelect(turnoverAddVm.use_status);
                    btn_dialog.dismiss();
                });

                if (turnoverAddVm.use_status != 0) {
                    useStatueAdapter.setIsSelect(turnoverAddVm.use_status);
                }
                break;
            case "material_status"://材料状态弹窗
                TextView tv_material_status = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tv_material_status).setText("材料状态");
                RecyclerView rvMaterialStatus = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvMaterialStatus).setLayoutManager(new LinearLayoutManager(this));
                materialStatusAdapter = new MaterialStatusAdapter(R.layout.adapter_selector_area_second, materialStatusList);
                rvMaterialStatus.setAdapter(materialStatusAdapter);
                materialStatusAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverAddVm.material_status = materialStatusList.get(position).getId();
                    tvMaterialStatus.setText(materialStatusList.get(position).getName());
                    tvMaterialStatus.setTextColor(getResources().getColor(R.color.colorTv));
                    materialStatusAdapter.setIsSelect(turnoverAddVm.material_status);
                    btn_dialog.dismiss();
                });

                if (turnoverAddVm.material_status != 0) {
                    materialStatusAdapter.setIsSelect(turnoverAddVm.material_status);
                }
                break;
            case "grounding"://是否上架弹窗
                TextView tvGrounding = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvGrounding).setText("是否上架");
                RecyclerView rvGrounding = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvGrounding).setLayoutManager(new LinearLayoutManager(this));
                groundingAdapter = new GroundingAdapter(R.layout.adapter_selector_area_second, groundingList);
                rvGrounding.setAdapter(groundingAdapter);
                groundingAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverAddVm.is_shelf = groundingList.get(position).getId();
                    tvIsShelf.setText(groundingList.get(position).getName());
                    tvIsShelf.setTextColor(getResources().getColor(R.color.colorTv));
                    groundingAdapter.setIsSelect(turnoverAddVm.is_shelf);
                    btn_dialog.dismiss();
                    switch (groundingList.get(position).getName()) {
                        case "未上架":
                            llInsideShelf.setVisibility(View.GONE);
                            llMethod.setVisibility(View.GONE);
                            llGuidancePrice.setVisibility(View.GONE);
                            tvImageRed.setVisibility(View.INVISIBLE);
                            break;
                        case "公开上架":
                            llInsideShelf.setVisibility(View.GONE);
                            llMethod.setVisibility(View.VISIBLE);
                            llGuidancePrice.setVisibility(View.VISIBLE);
                            tvImageRed.setVisibility(View.VISIBLE);
                            break;
                        case "内部上架":
                            llInsideShelf.setVisibility(View.VISIBLE);
                            llMethod.setVisibility(View.VISIBLE);
                            llGuidancePrice.setVisibility(View.VISIBLE);
                            tvImageRed.setVisibility(View.VISIBLE);
                            break;
                    }
                });
                if (turnoverAddVm.is_shelf != 0) {
                    groundingAdapter.setIsSelect(turnoverAddVm.is_shelf);
                }
                break;
            case "images"://上传图片
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
                break;
        }
        btn_dialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventLocationEvent(LocationEvent event) {
        turnoverAddVm.province = event.getProvinceId();
        turnoverAddVm.city = event.getCityId();
        turnoverAddVm.address = event.getAddress();
        turnoverAddVm.latitude = event.getLatitude();
        turnoverAddVm.longitude = event.getLongitude();
        etAddress.setText(event.getProvince() + event.getCity() + event.getAddress());
    }

    //时间选择器
    private void showTimeDialog(String type, String showTime) {
        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            switch (type) {
                case "shelf_time_start"://上架开始时间
                    turnoverAddVm.shelf_start_time = time;
                    tvShelfTimeStart.setText(time);
                    tvShelfTimeStart.setTextColor(getResources().getColor(R.color.colorTv));
                    break;
                case "shelf_time_end"://上架结束时间
                    turnoverAddVm.shelf_end_time = time;
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

    //获取权限
    public static final int CAMERA = 111;
    public static final int PHOTO = 222;
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                getBottomSheetDialog(R.layout.dialog_photo, "images");
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, CAMERA);
            }
        } else {
            getBottomSheetDialog(R.layout.dialog_photo, "images");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 从相机返回的数据
        if (resultCode == 101) {
            String path = Objects.requireNonNull(data).getStringExtra("path");
            File file = new File(Objects.requireNonNull(path));
            LogUtils.i(file);
            uploadPhotoVm.upload(path);
        }
        if (resultCode == 103) {
            ToastUtils.showShort("您没有打开相机权限");
        }
        //从相册返回的数据
        if (requestCode == PHOTO && resultCode == RESULT_OK) {
            LogUtils.i(Matisse.obtainPathResult(Objects.requireNonNull(data)).get(0));
            uploadPhotoVm.upload(Matisse.obtainPathResult(data).get(0));
        }
    }

    //添加后的图片显示
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
                    turnoverAddVm.images = getListToString();
                }
            });
        }
    }

    //list转换成String并用，拼接
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

    //获取编辑详细数据显示
    @SuppressLint("SetTextI18n")
    private void getEditDetail(int id) {
        turnoverAddVm.editId = id;
        turnoverVm.constructionEditShow(id);
        turnoverVm.turnoverDetailEditLiveData.observe(this, turnoverDetailEditBean -> {
            turnoverAddVm.unit_id = turnoverDetailEditBean.getUnit_id();
            tvProject.setText(turnoverDetailEditBean.getUnit_id_name());
            tvProject.setTextColor(getResources().getColor(R.color.colorTv));
            turnoverAddVm.category = turnoverDetailEditBean.getCategory();
            tvCategoryMaterial_id.setText(turnoverDetailEditBean.getCategory_name());
            tvCategoryMaterial_id.setTextColor(getResources().getColor(R.color.colorTv));
            etMaterialName.setText(turnoverDetailEditBean.getMaterial_name());
            etStock.setText(turnoverDetailEditBean.getStock());
            etUnitPrice.setText(turnoverDetailEditBean.getUnit_price());
            turnoverAddVm.unit = turnoverDetailEditBean.getUnit();
            tvUnit.setText(turnoverDetailEditBean.getUnit_name());
            tvUnit.setTextColor(getResources().getColor(R.color.colorTv));
            etSpec.setText(turnoverDetailEditBean.getSpec());
            turnoverAddVm.use_status = turnoverDetailEditBean.getUse_status();
            tvUseStatus.setText(turnoverDetailEditBean.getUse_status_name());
            tvUseStatus.setTextColor(getResources().getColor(R.color.colorTv));
            turnoverAddVm.material_status = turnoverDetailEditBean.getMaterial_status();
            tvMaterialStatus.setText(turnoverDetailEditBean.getMaterial_status_name());
            tvMaterialStatus.setTextColor(getResources().getColor(R.color.colorTv));
            turnoverAddVm.province = turnoverDetailEditBean.getProvince();
            turnoverAddVm.city = turnoverDetailEditBean.getCity();
            turnoverAddVm.address = turnoverDetailEditBean.getAddress();
            turnoverAddVm.latitude = turnoverDetailEditBean.getLatitude();
            turnoverAddVm.longitude = turnoverDetailEditBean.getLongitude();
            etAddress.setText(turnoverDetailEditBean.getProvince_name() + turnoverDetailEditBean.getCity_name() + turnoverDetailEditBean.getAddress());
            etPersonLiable.setText(turnoverDetailEditBean.getPerson_liable());
            etTel.setText(turnoverDetailEditBean.getTel());
            turnoverAddVm.is_vulnerable = turnoverDetailEditBean.getRapid_wear();
            if (turnoverAddVm.is_vulnerable == 1) {
                cbYes.setChecked(false);
                cbNo.setChecked(true);
            } else {
                cbYes.setChecked(true);
                cbNo.setChecked(false);
            }
            turnoverAddVm.is_shelf = turnoverDetailEditBean.getIs_shelf();
            tvIsShelf.setText(turnoverDetailEditBean.getIs_shelf_name());
            tvIsShelf.setTextColor(getResources().getColor(R.color.colorTv));
            switch (turnoverAddVm.is_shelf) {
                case 1://公开上架
                    llInsideShelf.setVisibility(View.GONE);
                    llMethod.setVisibility(View.VISIBLE);
                    llGuidancePrice.setVisibility(View.VISIBLE);
                    tvImageRed.setVisibility(View.VISIBLE);
                    etGuidancePrice.setText(turnoverDetailEditBean.getGuidance_price());
                    turnoverAddVm.method = turnoverDetailEditBean.getMethod();
                    if (turnoverAddVm.method == 1) {
                        cbLease.setChecked(true);
                        cbSell.setChecked(false);
                    } else {
                        cbLease.setChecked(false);
                        cbSell.setChecked(true);
                    }
                    break;
                case 2://未上架
                    llInsideShelf.setVisibility(View.GONE);
                    llMethod.setVisibility(View.GONE);
                    llGuidancePrice.setVisibility(View.GONE);
                    tvImageRed.setVisibility(View.INVISIBLE);
                    break;
                case 3://内部上架
                    llInsideShelf.setVisibility(View.VISIBLE);
                    llMethod.setVisibility(View.VISIBLE);
                    llGuidancePrice.setVisibility(View.VISIBLE);
                    tvImageRed.setVisibility(View.VISIBLE);
                    etGuidancePrice.setText(turnoverDetailEditBean.getGuidance_price());
                    turnoverAddVm.shelf_start_time = turnoverDetailEditBean.getShelf_start_time();
                    tvShelfTimeStart.setText(turnoverDetailEditBean.getShelf_start_time());
                    tvShelfTimeStart.setTextColor(getResources().getColor(R.color.colorTv));
                    turnoverAddVm.shelf_end_time = turnoverDetailEditBean.getShelf_end_time();
                    tvShelfTimeEnd.setText(turnoverDetailEditBean.getShelf_end_time());
                    tvShelfTimeEnd.setTextColor(getResources().getColor(R.color.colorTv));
                    turnoverAddVm.shelf_type = turnoverDetailEditBean.getShelf_type();
                    if (turnoverAddVm.shelf_type == 1) {
                        cbShelfTypeOpen.setChecked(true);
                        cbShelfTypeClose.setChecked(false);
                    } else {
                        cbShelfTypeOpen.setChecked(false);
                        cbShelfTypeClose.setChecked(true);
                    }
                    turnoverAddVm.method = turnoverDetailEditBean.getMethod();
                    if (turnoverAddVm.method == 1) {
                        cbLease.setChecked(true);
                        cbSell.setChecked(false);
                    } else {
                        cbLease.setChecked(false);
                        cbSell.setChecked(true);
                    }
                    break;
            }
            for (TurnoverDetailEditBean.ImagesBean img : turnoverDetailEditBean.getImages()) {
                picture_list.add(img.getUrl());
            }
            turnoverAddVm.images = getListToString();
            getImageBitmap();

            turnoverAddVm.plan = turnoverDetailEditBean.getPlan();
            tsPlan.setValue(turnoverDetailEditBean.getPlan_name());
            turnoverAddVm.use_count = String.valueOf(turnoverDetailEditBean.getUse_count());
            tsNum.setValue(String.valueOf(turnoverDetailEditBean.getUse_count()));
            turnoverAddVm.start_date = turnoverDetailEditBean.getStart_date();
            tsStartTime.setValue(turnoverDetailEditBean.getStart_date());
            turnoverAddVm.entry_time = turnoverDetailEditBean.getEntry_time();
            tsEnterTime.setValue(turnoverDetailEditBean.getEntry_time());
            turnoverAddVm.exit_time = turnoverDetailEditBean.getExit_time();
            tsExitTime.setValue(turnoverDetailEditBean.getExit_time());
            turnoverAddVm.accumulated_amortization = String.valueOf(turnoverDetailEditBean.getAccumulated_amortization());
            tsAmortize.setValue(String.valueOf(turnoverDetailEditBean.getAccumulated_amortization()));
            turnoverAddVm.original_price = String.valueOf(turnoverDetailEditBean.getOriginal_price());
            tsOriginal.setValue(String.valueOf(turnoverDetailEditBean.getOriginal_price()));
            turnoverAddVm.net_worth = String.valueOf(turnoverDetailEditBean.getNet_worth());
            tsValue.setValue(String.valueOf(turnoverDetailEditBean.getNet_worth()));

            dialogControl.setDetail(turnoverDetailEditBean.getPlan_name()
                    , String.valueOf(turnoverDetailEditBean.getUse_count()), turnoverDetailEditBean.getStart_date(),
                    turnoverDetailEditBean.getEntry_time(), turnoverDetailEditBean.getExit_time()
                    , String.valueOf(turnoverDetailEditBean.getAccumulated_amortization()), String.valueOf(turnoverDetailEditBean.getOriginal_price()),
                    String.valueOf(turnoverDetailEditBean.getNet_worth()));

            etRemark.setText(turnoverDetailEditBean.getRemark());
            if (turnoverDetailEditBean.getPlan() != 0 || turnoverDetailEditBean.getUse_count() != 0
                    || !StringUtils.isTrimEmpty(turnoverAddVm.start_date) || !StringUtils.isTrimEmpty(turnoverAddVm.entry_time)
                    || !StringUtils.isTrimEmpty(turnoverAddVm.exit_time) || turnoverDetailEditBean.getAccumulated_amortization() != 0
                    || turnoverDetailEditBean.getOriginal_price() != 0 || turnoverDetailEditBean.getNet_worth() != 0) {
                svOtherDetails.setOpened(true);
                llTurnoverDetail.setVisibility(View.VISIBLE);
            }
        });

        //编辑周转材料返回结果
        turnoverAddVm.turnoverEditData.observe(this, item -> {
            ToastUtils.showShort("编辑成功");
            EventBus.getDefault().post(new TurnoverEvent(0, ""));
            finish();
        });
    }

    @Override
    public void callInfo(String planStr, String use_count, String start_date, String entry_time, String exit_time,
                         String accumulated_amortization, String original_price, String net_worth) {

        turnoverAddVm.use_count = use_count;
        turnoverAddVm.start_date = start_date;
        turnoverAddVm.entry_time = entry_time;
        turnoverAddVm.exit_time = exit_time;
        turnoverAddVm.accumulated_amortization = accumulated_amortization;
        turnoverAddVm.original_price = original_price;
        turnoverAddVm.net_worth = net_worth;


        tsPlan.setValue(planStr);
        tsNum.setValue(use_count);
        tsStartTime.setValue(start_date);
        tsEnterTime.setValue(entry_time);
        tsExitTime.setValue(exit_time);
        tsAmortize.setValue(accumulated_amortization);
        tsOriginal.setValue(original_price);
        tsValue.setValue(net_worth);
    }
}
