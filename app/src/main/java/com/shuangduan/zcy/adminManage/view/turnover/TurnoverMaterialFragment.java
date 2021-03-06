package com.shuangduan.zcy.adminManage.view.turnover;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.shuangduan.zcy.adminManage.adapter.GroundingAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaSecondAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverCompanyAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverProjectAdapter;
import com.shuangduan.zcy.adminManage.adapter.UseStatueAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.event.TurnoverEvent;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.base.BaseNoRefreshFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.event.LocationEvent;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.PhoneUtils;
import com.shuangduan.zcy.view.release.ReleaseAreaSelectActivity;
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
 * @ClassName: TurnoverMaterialFragment
 * @Description: ??????????????????
 * @Author: ?????????
 * @CreateDate: 2019/10/23 14:35
 * @UpdateUser: ?????????
 * @UpdateDate: 2019/10/23 14:35
 * @UpdateRemark: ????????????
 * @Version: 1.0
 */
public class TurnoverMaterialFragment extends BaseNoRefreshFragment {
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
    private TurnoverVm turnoverVm;
    private MultiAreaVm areaVm;
    private TurnoverAdapter turnoverAdapter;
    private int manage_status;
    private View emptyView = null;

    public static TurnoverMaterialFragment newInstance() {
        Bundle args = new Bundle();
        TurnoverMaterialFragment fragment = new TurnoverMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_turnover_material;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.turnover_material));
        tvName.setText("????????????");

        emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_mine_materials_info, 0,R.color.colorBgDark, null);

        manage_status = SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS, 0);
        getAdminEntrance(manage_status);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        turnoverAdapter = new TurnoverAdapter(R.layout.item_turnover, null,SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_EDIT,0)
                , SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_DELETE,0),manage_status);
        recyclerView.setAdapter(turnoverAdapter);

        turnoverVm = mActivity.getViewModel(TurnoverVm.class);

        //??????????????????????????????
        turnoverVm.turnoverLiveData.observe(this,turnoverBean -> {
            if (turnoverBean.getPage() == 1) {
                turnoverAdapter.setNewData(turnoverBean.getList());
                turnoverAdapter.setEmptyView(emptyView);
            } else {
                turnoverAdapter.addData(turnoverAdapter.getData().size(), turnoverBean.getList());
            }
            setNoMore(turnoverBean.getPage(), turnoverBean.getCount());
        });

        turnoverAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverBean.ListBean listBean = turnoverAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            //????????????????????????????????????????????????????????????????????????
            switch (manage_status){
                case 1:
                case 2:
                case 3:
                    bundle.putInt(CustomConfig.CONSTRUCTION_ID,listBean.getId());
                    ActivityUtils.startActivity(bundle,TurnoverDetailActivity.class);
                    break;
                case 4:
                case 5:
                    if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_DETAIL,0)==1){
                        bundle.putInt(CustomConfig.CONSTRUCTION_ID,listBean.getId());
                        ActivityUtils.startActivity(bundle,TurnoverDetailActivity.class);
                    }
                    break;
            }
        });

        turnoverAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TurnoverBean.ListBean listBean = turnoverAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_edit://??????
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.HANDLE_TYPE,CustomConfig.EDIT);
                    bundle.putInt(CustomConfig.CONSTRUCTION_ID,listBean.getId());
                    ActivityUtils.startActivity(bundle,TurnoverAddActivity.class);
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
                                    turnoverAdapter.remove(position);
                                    turnoverVm.constructionDelete(listBean.getId());
                                }
                            }).showDialog();
                    break;
                case R.id.tv_split://??????
                    getBottomSheetDialog(R.layout.dialog_turnover_split,"split",listBean.getId(),1);
                    break;
            }
        });

        turnoverVm.turnoverDeleteData.observe(this,item ->{
            ToastUtils.showShort("????????????");
        });

        turnoverVm.turnoverSplitData.observe(this,item ->{
            ToastUtils.showShort("????????????");
            turnoverVm.constructionList(areaVm.id,areaVm.city_id);
            splitUseStatue=0;
            splitProvince=0;
            splitCity=0;
            splitAddress="";
            longitude=0;
            latitude=0;
        });

        //???????????????????????????
        turnoverVm.turnoverCompanyData.observe(this,turnoverCompanyBeans -> {
            companyList=turnoverCompanyBeans;
            turnoverCompanyAdapter.setNewData(companyList);
        });

        //????????????????????????
        turnoverVm.turnoverProject.observe(this,turnoverNameBeans -> {
            projectList = turnoverNameBeans;
            if (manage_status==3||manage_status==5&&!projectList.get(0).name.equals("??????")) projectList.add(0,new TurnoverNameBean(0,"??????"));
            turnoverProjectAdapter.setNewData(projectList);
        });

        //??????????????????????????????
        turnoverVm.turnoverTypeData.observe(this,turnoverTypeBean -> {
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
                turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                turnoverVm.constructionSearch();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                turnoverVm.constructionListMore(areaVm.id,areaVm.city_id);
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

    @Override
    protected void initDataFromService() {
        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
        turnoverVm.constructionSearch();
    }

    @SuppressLint("NewApi")
    @OnClick({R.id.iv_bar_back,R.id.iv_add,R.id.tv_company,R.id.tv_name,R.id.tv_grounding,R.id.tv_use_statue,R.id.tv_depositing_place
            ,R.id.tv_reset,R.id.tv_company_children,R.id.tv_name_second,R.id.tv_is_shelf,R.id.tv_use_status,R.id.tv_address})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                Objects.requireNonNull(getActivity()).finish();
                break;
            case R.id.iv_add://??????
                bundle.putInt(CustomConfig.HANDLE_TYPE,CustomConfig.ADD);
                ActivityUtils.startActivity(bundle, TurnoverAddActivity.class);
                break;
            case R.id.tv_company://???????????????/??????
                if (manage_status==3||manage_status==5){
                    getBottomSheetDialog(R.layout.dialog_depositing_place,"company",0,1);
                }else {
                    getBottomSheetDialog(R.layout.dialog_is_grounding,"project",0,1);
                }
                getDrawableRightView(tvCompany,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_name://??????????????????
                getBottomSheetDialog(R.layout.dialog_search_edit,"edit",0,1);
                break;
            case R.id.tv_grounding://????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding,"grounding",0,1);
                getDrawableRightView(tvGrounding,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_use_statue://????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding,"use_statue",0,1);
                getDrawableRightView(tvUseStatue,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_depositing_place://????????????
                getBottomSheetDialog(R.layout.dialog_depositing_place,"depositing_place",0,1);
                getDrawableRightView(tvDepositingPlace,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_reset://????????????
                llAdminManageScreen.setVisibility(View.GONE);
                tvCompanyChildren.setVisibility(View.GONE);
                tvNameSecond.setVisibility(View.GONE);
                tvIsShelf.setVisibility(View.GONE);
                tvUseStatus.setVisibility(View.GONE);
                tvAddress.setVisibility(View.GONE);
                turnoverVm.supplier_id=0;
                turnoverVm.unit_id=0;
                turnoverVm.material_name="";
                turnoverVm.is_shelf=0;
                turnoverVm.use_status=0;
                areaVm.id=0;
                areaVm.city_id=0;
                turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                break;
            case R.id.tv_company_children://?????????
                turnoverVm.supplier_id=0;
                turnoverVm.unit_id=0;
                getDeleteView(tvCompanyChildren);
                break;
            case R.id.tv_name_second://????????????
                turnoverVm.material_name="";
                getDeleteView(tvNameSecond);
                break;
            case R.id.tv_is_shelf://????????????
                turnoverVm.is_shelf=0;
                getDeleteView(tvIsShelf);
                break;
            case R.id.tv_use_status://????????????
                turnoverVm.use_status=0;
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
    private int splitUseStatue,splitProvince,splitCity;
    private String splitAddress;
    private double latitude,longitude;
    private TextView tvSplitUseStatue;
    private XEditText etSplitAddress;
    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog(int layout, String type,int id,int use) {
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
            if (type.equals("split")){
                splitUseStatue=0;
                splitProvince=0;
                splitCity=0;
                splitAddress="";
                longitude=0;
                latitude=0;
            }
            getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvGrounding,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvUseStatue,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvCompany,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            if (KeyboardUtil.isSoftShowing(mActivity)){
                KeyboardUtil.showORhideSoftKeyboard(mActivity);
            }
        });
        turnoverVm.type=type;
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
                    turnoverVm.supplier_id = companyList.get(position).getSupplier_id();
                    turnoverVm.supplier_name = companyList.get(position).getCompany();
                    turnoverVm.getUnitInfo();
                    turnoverCompanyAdapter.setIsSelect(companyList.get(position).getSupplier_id());
                });
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverVm.unit_id = projectList.get(position).id;
                    turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                    turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvCompany,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    if (turnoverVm.unit_id!=0){
                        getAddTopScreenView(tvCompanyChildren,projectList.get(position).name);
                    }else {
                        getAddTopScreenView(tvCompanyChildren,turnoverVm.supplier_name);
                    }
                });
                turnoverVm.getSupplierInfo();
                if (turnoverVm.supplier_id!=0){
                    turnoverCompanyAdapter.setIsSelect(turnoverVm.supplier_id);
                    turnoverVm.getUnitInfo();
                }
                if (turnoverVm.unit_id!=0){
                    turnoverProjectAdapter.setIsSelect(turnoverVm.unit_id);
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
                    turnoverVm.unit_id = projectList.get(position).id;
                    turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                    turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvCompany,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvCompanyChildren,projectList.get(position).name);
                });
                turnoverVm.getUnitInfo();
                if (turnoverVm.unit_id!=0){
                    turnoverProjectAdapter.setIsSelect(turnoverVm.unit_id);
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
                    turnoverVm.is_shelf=groundingList.get(position).getId();
                    turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvGrounding,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvIsShelf,groundingList.get(position).getName());
                });
                if (turnoverVm.is_shelf!=0){
                    groundingAdapter.setIsSelect(turnoverVm.is_shelf);
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
                    if (use==1){
                        turnoverVm.use_status=useStatueList.get(position).getId();
                        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                        getDrawableRightView(tvUseStatue,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                        getAddTopScreenView(tvUseStatus,useStatueList.get(position).getName());
                    }else {
                        splitUseStatue=useStatueList.get(position).getId();
                        tvSplitUseStatue.setText(useStatueList.get(position).getName());
                        tvSplitUseStatue.setTextColor(getResources().getColor(R.color.colorTv));
                    }
                    btn_dialog.dismiss();
                });
                if (turnoverVm.use_status!=0){
                    useStatueAdapter.setIsSelect(turnoverVm.use_status);
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
                    turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                    cityAdapter.setIsSelect(cityList.get(position).getId());
                    btn_dialog.dismiss();
                    getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                });
                areaVm.getProvince();
                if (areaVm.id!=0){
                    provinceAdapter.setIsSelect(areaVm.id);
                    areaVm.getCity();
                }
                if (areaVm.city_id!=0 ){
                    cityAdapter.setIsSelect(areaVm.city_id);
                }
                break;
            case "split"://????????????
                TextView tvSave=dialog_view.findViewById(R.id.tv_save);
                EditText etNum=dialog_view.findViewById(R.id.et_stock);
                EditText et_unit_price=dialog_view.findViewById(R.id.et_unit_price);
                tvSplitUseStatue=dialog_view.findViewById(R.id.tv_use_status);
                etSplitAddress = dialog_view.findViewById(R.id.et_address);
                ImageView ivAddress = dialog_view.findViewById(R.id.iv_address);
                KeyboardUtil.RemoveDecimalPoints(etNum);
                KeyboardUtil.RemoveDecimalPoints(et_unit_price);
                KeyboardUtil.showSoftInputFromWindow((BaseActivity) getActivity(), etNum);
                tvSave.setOnClickListener(v -> {
                    splitAddress = etSplitAddress.getText().toString();
                    if (TextUtils.isEmpty(etNum.getText().toString())) {
                        ToastUtils.showShort(getString(R.string.no_mun));
                        return;
                    }
                    if (Double.valueOf(etNum.getText().toString()) == 0) {
                        ToastUtils.showShort("???????????????0");
                        return;
                    }
                    if (TextUtils.isEmpty(et_unit_price.getText().toString())) {
                        ToastUtils.showShort("????????????????????????");
                        return;
                    }
                    if (splitUseStatue==0) {
                        ToastUtils.showShort("????????????????????????");
                        return;
                    }
                    if (splitProvince==0&&splitCity==0) {
                        ToastUtils.showShort("?????????????????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(splitAddress)) {
                        ToastUtils.showShort("???????????????????????????");
                        return;
                    }
                    turnoverVm.constructionSplit(id,etNum.getText().toString(),et_unit_price.getText().toString(),splitUseStatue,splitProvince,splitCity,splitAddress,longitude,latitude);
                    btn_dialog.dismiss();
                });
                tvSplitUseStatue.setOnClickListener(v -> {
                    getBottomSheetDialog(R.layout.dialog_is_grounding,"use_statue",0,2);
                });
                ivAddress.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.PROJECT_ADDRESS, 3);
                    ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                });
                break;
            case "edit"://??????????????????
                XEditText xEditText = dialog_view.findViewById(R.id.edit);
                TextView tvSearch = dialog_view.findViewById(R.id.tv_search);
                View view = dialog_view.findViewById(R.id.view);
                PhoneUtils.isPhone(view);
                KeyboardUtil.showSoftInputFromWindow((BaseActivity) getActivity(), xEditText);
                tvSearch.setOnClickListener(v -> {
                    turnoverVm.material_name = xEditText.getText().toString();
                    turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                    getAddTopScreenView(tvNameSecond,xEditText.getText().toString());
                    btn_dialog.dismiss();
                });
                //EditTextView ??????
                xEditText.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        //???????????????
                        KeyboardUtil.closeKeyboard(mActivity);
                        turnoverVm.material_name = xEditText.getText().toString();
                        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
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
    public void onEventTurnover(TurnoverEvent event) {
        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventLocationEvent(LocationEvent event){
        splitProvince=event.getProvinceId();
        splitCity=event.getCityId();
        splitAddress=event.getAddress();
        latitude=event.getLatitude();
        longitude=event.getLongitude();
        etSplitAddress.setText(event.getAddress());
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
        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
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
                ivAdd.setVisibility(View.VISIBLE);
                break;
            case 4://??????????????????
                tvCompany.setText("????????????");
                if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ADD,0) ==1){
                    ivAdd.setVisibility(View.VISIBLE);
                }else {
                    ivAdd.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
