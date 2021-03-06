package com.shuangduan.zcy.adminManage.view.device;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.DeviceAdapter;
import com.shuangduan.zcy.adminManage.adapter.GroundingAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaSecondAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverCompanyAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverProjectAdapter;
import com.shuangduan.zcy.adminManage.adapter.UseStatueAdapter;
import com.shuangduan.zcy.adminManage.bean.DeviceBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.event.DeviceEvent;
import com.shuangduan.zcy.adminManage.vm.DeviceVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.base.BaseNoRefreshFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.PhoneUtils;
import com.shuangduan.zcy.vm.MultiAreaVm;
import com.shuangduan.zcy.weight.XEditText;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: DeviceManagementFragment
 * @Description: ??????????????????
 * @Author: ?????????
 * @CreateDate: 2019/10/23 14:38
 * @UpdateUser: ?????????
 * @UpdateDate: 2019/10/23 14:38
 * @UpdateRemark: ????????????
 * @Version: 1.0
 */
public class DeviceManagementFragment extends BaseNoRefreshFragment {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_company)
    AppCompatTextView tvCompany;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_grounding)
    AppCompatTextView tvGrounding;
    @BindView(R.id.tv_use_statue)
    AppCompatTextView tvUseStatue;
    @BindView(R.id.tv_depositing_place)
    AppCompatTextView tvDepositingPlace;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.ll_admin_manage_screen)
    LinearLayout llAdminManageScreen;
    @BindView(R.id.tv_reset)
    AppCompatTextView tvReset;
    @BindView(R.id.tv_company_children)
    AppCompatTextView tvCompanyChildren;
    @BindView(R.id.tv_name_second)
    AppCompatTextView tvNameSecond;
    @BindView(R.id.tv_is_shelf)
    AppCompatTextView tvIsShelf;
    @BindView(R.id.tv_use_status)
    AppCompatTextView tvUseStatus;
    @BindView(R.id.tv_address)
    AppCompatTextView tvAddress;
    private DeviceVm deviceVm;
    private MultiAreaVm areaVm;
    private DeviceAdapter deviceAdapter;
    private int manage_status;
    private View emptyView = null;

    public static DeviceManagementFragment newInstance() {
        Bundle args = new Bundle();
        DeviceManagementFragment fragment = new DeviceManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_device_management;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.device_management));
        tvName.setText("????????????");

        emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_mine_equipment_info, 0,R.color.colorBgDark, null);

        manage_status = SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS, 0);
        getAdminEntrance(manage_status);

        deviceVm = mActivity.getViewModel(DeviceVm.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        deviceAdapter = new DeviceAdapter(R.layout.item_device, null,SPUtils.getInstance().getInt(CustomConfig.EQIPMENT_EDIT,0)
                , SPUtils.getInstance().getInt(CustomConfig.EQIPMENT_DELETE,0),manage_status);
        recyclerView.setAdapter(deviceAdapter);

        deviceVm.deviceLiveData.observe(this,deviceBean -> {
            if (deviceBean.getPage() == 1) {
                deviceAdapter.setNewData(deviceBean.getList());
                deviceAdapter.setEmptyView(emptyView);
            } else {
                deviceAdapter.addData(deviceAdapter.getData().size(), deviceBean.getList());
            }
            setNoMore(deviceBean.getPage(), deviceBean.getCount());
        });

        deviceAdapter.setOnItemClickListener((adapter, view, position) -> {
            DeviceBean.ListBean listBean = deviceAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            //????????????????????????????????????????????????????????????????????????
            switch (manage_status){
                case 1:
                case 2:
                case 3:
                    bundle.putInt(CustomConfig.EQIPMENT_ID,listBean.getId());
                    ActivityUtils.startActivity(bundle,DeviceDetailActivity.class);
                    break;
                case 4:
                case 5:
                    if (SPUtils.getInstance().getInt(CustomConfig.EQIPMENT_DETAIL,0)==1){
                        bundle.putInt(CustomConfig.EQIPMENT_ID,listBean.getId());
                        ActivityUtils.startActivity(bundle,DeviceDetailActivity.class);
                    }
                    break;
            }
        });

        deviceAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DeviceBean.ListBean listBean = deviceAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_edit://??????
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.HANDLE_TYPE,CustomConfig.EDIT);
                    bundle.putInt(CustomConfig.EQIPMENT_ID,listBean.getId());
                    ActivityUtils.startActivity(bundle,DeviceAddActivity.class);
                    break;
                case R.id.tv_delete://??????
                    new CustomDialog(Objects.requireNonNull(getActivity()))
                            .setTip(getString(R.string.admin_turnover_delete))
                            .setCallBack(new BaseDialog.CallBack() {
                                @Override
                                public void cancel() {
                                }

                                @Override
                                public void ok(String s) {
                                    deviceVm.equipmentDelete(listBean.getId());
                                    deviceAdapter.remove(position);
                                }
                            }).showDialog();
                    break;
            }
        });

        deviceVm.deviceDeleteData.observe(this,item ->{
            ToastUtils.showShort("????????????");
        });

        //???????????????????????????
        deviceVm.turnoverCompanyData.observe(this,turnoverCompanyBeans -> {
            companyList=turnoverCompanyBeans;
            turnoverCompanyAdapter.setNewData(companyList);
        });

        //????????????????????????
        deviceVm.turnoverProject.observe(this,turnoverNameBeans -> {
            projectList = turnoverNameBeans;
            if (manage_status==3||manage_status==5&&!projectList.get(0).name.equals("??????")) projectList.add(0,new TurnoverNameBean(0,"??????"));
            turnoverProjectAdapter.setNewData(projectList);
        });

        //??????????????????????????????
        deviceVm.turnoverTypeData.observe(this,turnoverTypeBean -> {
            //??????????????????
            groundingList=turnoverTypeBean.getIs_shelf();
            if (SPUtils.getInstance().getInt(CustomConfig.INNER_SWITCH, 0) != 1&& groundingList.get(1).getName().equals("????????????")) groundingList.remove(1);
            //??????????????????
            useStatueList=turnoverTypeBean.getUse_status();
        });

        //??????????????????
        areaVm = mActivity.getViewModel(MultiAreaVm.class);
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            provinceList = provinceBeans;
            provinceAdapter.setNewData(provinceList);
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            cityList = cityBeans;
            if (!cityList.get(0).getName().equals("??????"))cityList.add(0,new CityBean(0,"??????"));
            cityAdapter.setNewData(cityList);
        });

        //???????????????????????????
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                deviceVm.constructionSearch();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                deviceVm.equipmentListMore(areaVm.id,areaVm.city_id);
            }
        });

        //????????????
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                AnimationUtils.listScrollAnimation(ivAdd,dy);
            }
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
    }

    @Override
    protected void initDataFromService() {
        deviceVm.equipmentList(areaVm.id,areaVm.city_id);
        deviceVm.constructionSearch();
    }

    private void setNoMore(int page, int count) {
        if (page == 1) {
            if (page * 10 >= count) {
                if (refresh.getState() == RefreshState.None) {
                    refresh.setNoMoreData(true);
                } else {
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                refresh.finishRefresh();
            }
        } else {
            if (page * 10 >= count) {
                refresh.finishLoadMoreWithNoMoreData();
            } else {
                refresh.finishLoadMore();
            }
        }
    }

    @OnClick({R.id.iv_add,R.id.iv_bar_back,R.id.tv_company,R.id.tv_name,R.id.tv_grounding,R.id.tv_use_statue,R.id.tv_depositing_place
            ,R.id.tv_reset,R.id.tv_company_children,R.id.tv_name_second,R.id.tv_is_shelf,R.id.tv_use_status,R.id.tv_address})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                Objects.requireNonNull(getActivity()).finish();
                break;
            case R.id.iv_add://??????
                bundle.putInt(CustomConfig.HANDLE_TYPE,CustomConfig.ADD);
                ActivityUtils.startActivity(bundle, DeviceAddActivity.class);
                break;
            case R.id.tv_company://???????????????
                if (manage_status==3||manage_status==5){
                    getBottomSheetDialog(R.layout.dialog_depositing_place,"company");
                }else {
                    getBottomSheetDialog(R.layout.dialog_is_grounding,"project");
                }
                getDrawableRightView(tvCompany,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_name://??????????????????
                getBottomSheetDialog(R.layout.dialog_search_edit,"edit");
                break;
            case R.id.tv_grounding://????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding,"grounding");
                getDrawableRightView(tvGrounding,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_use_statue://????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding,"use_statue");
                getDrawableRightView(tvUseStatue,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_depositing_place://????????????
                getBottomSheetDialog(R.layout.dialog_depositing_place,"depositing_place");
                getDrawableRightView(tvDepositingPlace,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_reset://????????????
                llAdminManageScreen.setVisibility(View.GONE);
                tvCompanyChildren.setVisibility(View.GONE);
                tvNameSecond.setVisibility(View.GONE);
                tvIsShelf.setVisibility(View.GONE);
                tvUseStatus.setVisibility(View.GONE);
                tvAddress.setVisibility(View.GONE);
                deviceVm.supplier_id=0;
                deviceVm.unit_id=0;
                deviceVm.material_name="";
                deviceVm.is_shelf=0;
                deviceVm.use_status=0;
                areaVm.id=0;
                areaVm.city_id=0;
                deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                break;
            case R.id.tv_company_children://?????????
                deviceVm.supplier_id=0;
                deviceVm.unit_id=0;
                getDeleteView(tvCompanyChildren);
                break;
            case R.id.tv_name_second://????????????
                deviceVm.material_name="";
                getDeleteView(tvNameSecond);
                break;
            case R.id.tv_is_shelf://????????????
                deviceVm.is_shelf=0;
                getDeleteView(tvIsShelf);
                break;
            case R.id.tv_use_status://????????????
                deviceVm.use_status=0;
                getDeleteView(tvUseStatus);
                break;
            case R.id.tv_address://????????????
                areaVm.id=0;
                areaVm.city_id=0;
                getDeleteView(tvAddress);
                break;
        }
    }

    private SelectorAreaFirstAdapter provinceAdapter;
    private SelectorAreaSecondAdapter cityAdapter;
    private List<ProvinceBean> provinceList = new ArrayList<>();
    private List<CityBean> cityList = new ArrayList<>();
    private List<TurnoverTypeBean.IsShelfBean> groundingList = new ArrayList<>();
    private List<TurnoverTypeBean.UseStatusBean> useStatueList = new ArrayList<>();
    private TurnoverCompanyAdapter turnoverCompanyAdapter;
    private List<TurnoverCompanyBean> companyList = new ArrayList<>();
    private TurnoverProjectAdapter turnoverProjectAdapter;
    private List<TurnoverNameBean> projectList = new ArrayList<>();
    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog(int layout, String type) {
        //?????????????????????
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(Objects.requireNonNull(getActivity()), R.style.BottomSheetStyle);
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
        btn_dialog.setOnDismissListener(dialog -> {
            getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvGrounding,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvUseStatue,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvCompany,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            if (KeyboardUtil.isSoftShowing(mActivity)){
                KeyboardUtil.showORhideSoftKeyboard(mActivity);
            }
        });
        deviceVm.type=type;
        switch (type){
            case "company":
                TextView tvFirst = btn_dialog.findViewById(R.id.tv_first);
                TextView tvSecond = btn_dialog.findViewById(R.id.tv_second);
                RecyclerView rvCompany = btn_dialog.findViewById(R.id.rv_province);
                RecyclerView rvProjectGroup = btn_dialog.findViewById(R.id.rv_city);
                Objects.requireNonNull(tvFirst).setText("??????????????????");
                Objects.requireNonNull(tvSecond).setText("????????????");
                Objects.requireNonNull(rvCompany).setLayoutManager(new LinearLayoutManager(getActivity()));
                Objects.requireNonNull(rvProjectGroup).setLayoutManager(new LinearLayoutManager(getActivity()));
                turnoverCompanyAdapter = new TurnoverCompanyAdapter(R.layout.adapter_selector_area_first, null);
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project_company, null,7);
                rvCompany.setAdapter(turnoverCompanyAdapter);
                rvProjectGroup.setAdapter(turnoverProjectAdapter);
                turnoverCompanyAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceVm.supplier_id = companyList.get(position).getSupplier_id();
                    deviceVm.supplier_name = companyList.get(position).getCompany();
                    deviceVm.getUnitInfo();
                    turnoverCompanyAdapter.setIsSelect(companyList.get(position).getSupplier_id());
                });
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceVm.unit_id = projectList.get(position).id;
                    deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                    turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvCompany,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    if (deviceVm.unit_id!=0){
                        getAddTopScreenView(tvCompanyChildren,projectList.get(position).name);
                    }else {
                        getAddTopScreenView(tvCompanyChildren,deviceVm.supplier_name);
                    }
                });
                deviceVm.getSupplierInfo();
                if (deviceVm.supplier_id!=0){
                    turnoverCompanyAdapter.setIsSelect(deviceVm.supplier_id);
                    deviceVm.getUnitInfo();
                }
                if (deviceVm.unit_id!=0){
                    turnoverProjectAdapter.setIsSelect(deviceVm.unit_id);
                }
                break;
            case "project":
                TextView tvProject=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvProject).setText("????????????");
                RecyclerView rvProject = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvProject).setLayoutManager(new LinearLayoutManager(getActivity()));
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project, null,12);
                rvProject.setAdapter(turnoverProjectAdapter);
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceVm.unit_id = projectList.get(position).id;
                    deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                    turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvCompany,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvCompanyChildren,projectList.get(position).name);
                });
                deviceVm.getUnitInfo();
                if (deviceVm.unit_id!=0){
                    turnoverProjectAdapter.setIsSelect(deviceVm.unit_id);
                }
                break;
            case "grounding":
                TextView tvGrounding=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvGrounding).setText("????????????");
                RecyclerView rvGrounding = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvGrounding).setLayoutManager(new LinearLayoutManager(getActivity()));
                GroundingAdapter groundingAdapter = new GroundingAdapter(R.layout.adapter_selector_area_second, groundingList);
                rvGrounding.setAdapter(groundingAdapter);
                groundingAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceVm.is_shelf=groundingList.get(position).getId();
                    deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvGrounding,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvIsShelf,groundingList.get(position).getName());
                });
                if (deviceVm.is_shelf!=0){
                    groundingAdapter.setIsSelect(deviceVm.is_shelf);
                }
                break;
            case "use_statue":
                TextView tvUseStatue=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvUseStatue).setText("????????????");
                RecyclerView rvUseStatue = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvUseStatue).setLayoutManager(new LinearLayoutManager(getActivity()));
                UseStatueAdapter useStatueAdapter = new UseStatueAdapter(R.layout.adapter_selector_area_second, useStatueList);
                rvUseStatue.setAdapter(useStatueAdapter);
                useStatueAdapter.setOnItemClickListener((adapter, view, position) -> {
                    deviceVm.use_status=useStatueList.get(position).getId();
                    deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvUseStatue,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvUseStatus,useStatueList.get(position).getName());
                });
                if (deviceVm.use_status!=0){
                    useStatueAdapter.setIsSelect(deviceVm.use_status);
                }
                break;
            case "depositing_place":
                RecyclerView rvProvince = btn_dialog.findViewById(R.id.rv_province);
                RecyclerView rvCity = btn_dialog.findViewById(R.id.rv_city);
                Objects.requireNonNull(rvProvince).setLayoutManager(new LinearLayoutManager(getActivity()));
                Objects.requireNonNull(rvCity).setLayoutManager(new LinearLayoutManager(getActivity()));
                provinceAdapter = new SelectorAreaFirstAdapter(R.layout.adapter_selector_area_first, null);
                cityAdapter = new SelectorAreaSecondAdapter(R.layout.adapter_selector_area_second, null);
                rvProvince.setAdapter(provinceAdapter);
                rvCity.setAdapter(cityAdapter);
                provinceAdapter.setOnItemClickListener((adapter, view, position) -> {
                    areaVm.id = provinceList.get(position).getId();
                    areaVm.cityResult = provinceList.get(position).getName();
                    areaVm.getCity();
                    provinceAdapter.setIsSelect(provinceList.get(position).getId());
                });
                cityAdapter.setOnItemClickListener((adapter, view, position) -> {
                    areaVm.city_id = cityList.get(position).getId();
                    if (cityList.get(position).getId()==0){
                        getAddTopScreenView(tvAddress,areaVm.cityResult);
                    }else {
                        getAddTopScreenView(tvAddress,cityList.get(position).getName());
                    }
                    deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                    cityAdapter.setIsSelect(cityList.get(position).getId());
                    btn_dialog.dismiss();
                    getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                });
                areaVm.getProvince();
                if (areaVm.id!=0){
                    provinceAdapter.setIsSelect(areaVm.id);
                    areaVm.getCity();
                }
                if (areaVm.city_id!=0){
                    cityAdapter.setIsSelect(areaVm.city_id);
                }
                break;
            case "edit"://??????????????????
                XEditText xEditText = dialog_view.findViewById(R.id.edit);
                TextView tvSearch = dialog_view.findViewById(R.id.tv_search);
                xEditText.setHint("?????????????????????");
                View view = dialog_view.findViewById(R.id.view);
                PhoneUtils.isPhone(view);
                KeyboardUtil.showSoftInputFromWindow((BaseActivity) getActivity(), xEditText);
                tvSearch.setOnClickListener(v -> {
                    deviceVm.material_name = xEditText.getText().toString();
                    deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                    getAddTopScreenView(tvNameSecond,xEditText.getText().toString());
                    btn_dialog.dismiss();
                });
                //EditTextView ??????
                xEditText.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        //???????????????
                        KeyboardUtil.closeKeyboard(mActivity);
                        deviceVm.material_name = xEditText.getText().toString();
                        deviceVm.equipmentList(areaVm.id,areaVm.city_id);
                        getAddTopScreenView(tvNameSecond,xEditText.getText().toString());
                        btn_dialog.dismiss();
                        return true;
                    }
                    return false;
                });
                break;
        }
        btn_dialog.show();
    }

    //???textView??????drawableRight??????
    private void getDrawableRightView(TextView textView,int icon,int color) {
        Drawable drawable = mContext.getResources().getDrawable(icon);
        // ?????????????????????,??????????????????.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setTextColor(getResources().getColor(color));
    }

    @Subscribe
    public void onEventDevice(DeviceEvent event) {
        deviceVm.equipmentList(areaVm.id,areaVm.city_id);
    }

    //????????????????????????view
    private void getAddTopScreenView(TextView textView, String text) {
        if (TextUtils.isEmpty(text)){
            return;
        }
        llAdminManageScreen.setVisibility(View.VISIBLE);
        tvReset.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

    //??????????????????
    private void getDeleteView(TextView textView) {
        textView.setVisibility(View.GONE);
        if (tvCompanyChildren.getVisibility()==View.GONE &&tvNameSecond.getVisibility()==View.GONE
                &&tvIsShelf.getVisibility()==View.GONE &&tvUseStatus.getVisibility()==View.GONE &&tvAddress.getVisibility()==View.GONE){
            llAdminManageScreen.setVisibility(View.GONE);
        }
        deviceVm.equipmentList(areaVm.id,areaVm.city_id);
    }

    //??????????????????
    private void getAdminEntrance(int manage_status) {
        switch (manage_status){
            case 1://???????????????
            case 2://?????????
                tvCompany.setText("????????????");
                ivAdd.setVisibility(View.VISIBLE);
                break;
            case 3://??????
            case 5://???????????????
                tvCompany.setText("??????/??????");
                ivAdd.setVisibility(View.GONE);
                break;
            case 4://??????????????????
                tvCompany.setText("????????????");
                if (SPUtils.getInstance().getInt(CustomConfig.EQIPMENT_ADD,0) ==1){
                    ivAdd.setVisibility(View.VISIBLE);
                }else {
                    ivAdd.setVisibility(View.GONE);
                }
                break;
        }
    }
}
