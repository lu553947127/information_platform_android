package com.shuangduan.zcy.adminManage.view.turnover;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
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
import com.shuangduan.zcy.adminManage.adapter.UseStatueAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.event.TurnoverChildrenEvent;
import com.shuangduan.zcy.adminManage.view.SelectTypeActivity;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.view.release.ReleaseAreaSelectActivity;
import com.shuangduan.zcy.vm.MultiAreaVm;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.CHIILDREN;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: TurnoverMaterialFragment
 * @Description: 周转材料列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 14:35
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 14:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverMaterialFragment extends BaseLazyFragment {

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
        tvName.setText("材料名称");

        getAdminEntrance(SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS,0));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        turnoverAdapter = new TurnoverAdapter(R.layout.item_turnover, null,SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_EDIT,0)
                , SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_DELETE,0),SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS,0));
        recyclerView.setAdapter(turnoverAdapter);

        turnoverVm = ViewModelProviders.of(this).get(TurnoverVm.class);

        //获取周转材料列表数据
        turnoverVm.turnoverLiveData.observe(this,turnoverBean -> {
            if (turnoverBean.getPage() == 1) {
                turnoverAdapter.setNewData(turnoverBean.getList());
                turnoverAdapter.setEmptyView(R.layout.layout_empty_admin, recyclerView);
            } else {
                turnoverAdapter.addData(turnoverAdapter.getData().size(), turnoverBean.getList());
            }
            setNoMore(turnoverBean.getPage(), turnoverBean.getCount());
        });

        turnoverAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverBean.ListBean listBean = turnoverAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            //判断当前登录身份为子账户时，是否有查看详情的权限
            switch (SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS,0)){
                case 1:
                case 2:
                case 3:
                    bundle.putInt(CustomConfig.CONSTRUCTION_ID,listBean.getId());
                    ActivityUtils.startActivity(bundle,TurnoverDetailActivity.class);
                    break;
                case 4:
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
                case R.id.tv_edit://编辑
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.HANDLE_TYPE,CustomConfig.EDIT);
                    bundle.putInt(CustomConfig.CONSTRUCTION_ID,listBean.getId());
                    ActivityUtils.startActivity(bundle,TurnoverAddActivity.class);
                    break;
                case R.id.tv_delete://删除
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
                case R.id.tv_split://拆分
                    getBottomSheetDialog(R.layout.dialog_turnover_split,"split",listBean.getId(),1);
                    break;
            }
        });

        turnoverVm.turnoverDeleteData.observe(this,item ->{
            ToastUtils.showShort("删除成功");
        });

        turnoverVm.turnoverSplitData.observe(this,item ->{
            ToastUtils.showShort("拆分成功");
            turnoverVm.constructionList(areaVm.id,areaVm.city_id);
            splitUseStatue=0;
            splitProvince=0;
            splitCity=0;
            splitAddress="";
            longitude=0;
            latitude=0;
        });

        //获取子公司列表数据
        turnoverVm.turnoverCompanyData.observe(this,turnoverCompanyBeans -> {
            companyList=turnoverCompanyBeans;
            turnoverCompanyAdapter.setNewData(companyList);
        });

        //获取筛选条件列表数据
        turnoverVm.turnoverTypeData.observe(this,turnoverTypeBean -> {
            //是否上架数据
            groundingList=turnoverTypeBean.getIs_shelf();
            if (SPUtils.getInstance().getInt(CustomConfig.INNER_SWITCH, 0) != 1) groundingList.remove(1);
            //使用状态数据
            useStatueList=turnoverTypeBean.getUse_status();
        });

        //获取省市数据
        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            provinceList = provinceBeans;
            provinceAdapter.setNewData(provinceList);
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            cityList = cityBeans;
            cityList.add(0,new CityBean(0,"全部"));
            cityAdapter.setNewData(cityList);
        });

        //下拉刷新和上拉加载
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

        //滑动监听
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
            case R.id.iv_add://添加
                bundle.putInt(CustomConfig.HANDLE_TYPE,CustomConfig.ADD);
                ActivityUtils.startActivity(bundle, TurnoverAddActivity.class);
                break;
            case R.id.tv_company://选择子公司/项目
                if (SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS,0)==3){
                    getBottomSheetDialog(R.layout.dialog_is_grounding,"company",0,1);
                }else {

                }
                getDrawableRightView(tvCompany,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_name://选择材料名称
                bundle.putInt(CustomConfig.ADMIN_MANAGE_TYPE,CustomConfig.ADMIN_MANAGE_CONSTRUCTION);
                bundle.putInt(CustomConfig.SELECT_TYPE,CHIILDREN);
                ActivityUtils.startActivity(bundle, SelectTypeActivity.class);
                break;
            case R.id.tv_grounding://是否上架
                getBottomSheetDialog(R.layout.dialog_is_grounding,"grounding",0,1);
                getDrawableRightView(tvGrounding,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_use_statue://使用状态
                getBottomSheetDialog(R.layout.dialog_is_grounding,"use_statue",0,1);
                getDrawableRightView(tvUseStatue,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_depositing_place://存放地点
                getBottomSheetDialog(R.layout.dialog_depositing_place,"depositing_place",0,1);
                getDrawableRightView(tvDepositingPlace,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_reset://重置按钮
                llAdminManageScreen.setVisibility(View.GONE);
                tvCompanyChildren.setVisibility(View.GONE);
                tvNameSecond.setVisibility(View.GONE);
                tvIsShelf.setVisibility(View.GONE);
                tvUseStatus.setVisibility(View.GONE);
                tvAddress.setVisibility(View.GONE);
                turnoverVm.category_id=0;
                turnoverVm.is_shelf=0;
                turnoverVm.use_status=0;
                areaVm.id=0;
                areaVm.city_id=0;
                turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                break;
            case R.id.tv_company_children://子公司
                turnoverVm.supplier_id=0;
                getDeleteView(tvCompanyChildren,View.GONE);
                break;
            case R.id.tv_name_second://名称二级
                turnoverVm.category_id=0;
                getDeleteView(tvNameSecond,View.GONE);
                break;
            case R.id.tv_is_shelf://是否上架
                turnoverVm.is_shelf=0;
                getDeleteView(tvIsShelf,View.GONE);
                break;
            case R.id.tv_use_status://使用状态
                turnoverVm.use_status=0;
                getDeleteView(tvUseStatus,View.GONE);
                break;
            case R.id.tv_address://存放地点
                areaVm.id=0;
                areaVm.city_id=0;
                getDeleteView(tvAddress,View.GONE);
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

    private int splitUseStatue,splitProvince,splitCity;
    private String splitAddress;
    private double latitude,longitude;
    private TextView tvSplitUseStatue,tvSplitAddress;
    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog(int layout, String type,int id,int use) {
        //底部滑动对话框
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(Objects.requireNonNull(getActivity()));
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
        });
        turnoverVm.type=type;
        switch (type){
            case "company":
                TextView tvCompany=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvCompany).setText("选择子公司");
                RecyclerView rvCompany = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvCompany).setLayoutManager(new LinearLayoutManager(getActivity()));
                turnoverCompanyAdapter = new TurnoverCompanyAdapter(R.layout.adapter_selector_area_second, null);
                rvCompany.setAdapter(turnoverCompanyAdapter);
                turnoverCompanyAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverVm.supplier_id=companyList.get(position).getSupplier_id();
                    turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvCompany,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvCompanyChildren,companyList.get(position).getCompany(),View.VISIBLE);
                });
                turnoverVm.getSupplierInfo();
                if (turnoverVm.supplier_id!=0){
                    turnoverCompanyAdapter.setIsSelect(turnoverVm.supplier_id);
                }
                break;
            case "grounding":
                TextView tvGrounding=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvGrounding).setText("是否上架");
                RecyclerView rvGrounding = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvGrounding).setLayoutManager(new LinearLayoutManager(getActivity()));
                GroundingAdapter groundingAdapter = new GroundingAdapter(R.layout.adapter_selector_area_second, groundingList);
                rvGrounding.setAdapter(groundingAdapter);
                groundingAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverVm.is_shelf=groundingList.get(position).getId();
                    turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvGrounding,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvIsShelf,groundingList.get(position).getName(),View.VISIBLE);
                });
                if (turnoverVm.is_shelf!=0){
                    groundingAdapter.setIsSelect(turnoverVm.is_shelf);
                }
                break;
            case "use_statue":
                TextView tvUseStatue=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvUseStatue).setText("使用状态");
                RecyclerView rvUseStatue = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvUseStatue).setLayoutManager(new LinearLayoutManager(getActivity()));
                UseStatueAdapter useStatueAdapter = new UseStatueAdapter(R.layout.adapter_selector_area_second, useStatueList);
                rvUseStatue.setAdapter(useStatueAdapter);
                useStatueAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (use==1){
                        turnoverVm.use_status=useStatueList.get(position).getId();
                        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                        getDrawableRightView(tvUseStatue,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                        getAddTopScreenView(tvUseStatus,useStatueList.get(position).getName(),View.VISIBLE);
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
                    if (cityList.get(position).getId()!=0){
                        areaVm.city_id = cityList.get(position).getId();
                        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                        cityAdapter.setIsSelect(cityList.get(position).getId());
                        btn_dialog.dismiss();
                        getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                        getAddTopScreenView(tvAddress,cityList.get(position).getName(),View.VISIBLE);
                    }else {
                        areaVm.city_id=0;
                        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
                        btn_dialog.dismiss();
                        getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                        getAddTopScreenView(tvAddress,areaVm.cityResult,View.VISIBLE);
                    }
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
            case "split"://拆分弹窗
                TextView tvSave=dialog_view.findViewById(R.id.tv_save);
                EditText etNum=dialog_view.findViewById(R.id.et_num);
                tvSplitUseStatue=dialog_view.findViewById(R.id.tv_use_statue);
                tvSplitAddress=dialog_view.findViewById(R.id.tv_material_id);
                tvSave.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(etNum.getText().toString())) {
                        ToastUtils.showShort(getString(R.string.no_mun));
                        return;
                    }
                    if (Integer.valueOf(etNum.getText().toString()) == 0) {
                        ToastUtils.showShort("数量不能为0");
                        return;
                    }
                    if (splitUseStatue==0) {
                        ToastUtils.showShort("使用状态不能为空");
                        return;
                    }
                    if (splitProvince==0&&splitCity==0&&TextUtils.isEmpty(splitAddress)) {
                        ToastUtils.showShort("存放地不能为空");
                        return;
                    }
                    turnoverVm.constructionSplit(id,etNum.getText().toString(),splitUseStatue,splitProvince,splitCity,splitAddress,longitude,latitude);
                    btn_dialog.dismiss();
                });
                tvSplitUseStatue.setOnClickListener(v -> {
                    getBottomSheetDialog(R.layout.dialog_is_grounding,"use_statue",0,2);
                });
                tvSplitAddress.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.PROJECT_ADDRESS, 3);
                    ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                });
                break;
        }
        btn_dialog.show();
    }

    //给textView设置drawableRight图片
    private void getDrawableRightView(TextView textView,int icon,int color) {
        Drawable drawable = mContext.getResources().getDrawable(icon);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setTextColor(getResources().getColor(color));
    }

    @Subscribe
    public void onEventTurnoverChildren(TurnoverChildrenEvent event) {
        turnoverVm.category_id=event.material_id;
        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
        getAddTopScreenView(tvNameSecond,event.material_name,View.VISIBLE);
    }

    //添加头部筛选布局view
    private void getAddTopScreenView(TextView textView,String text,int type) {
        llAdminManageScreen.setVisibility(type);
        tvReset.setVisibility(type);
        textView.setVisibility(type);
        textView.setText(text);
    }

    //关闭筛选条件
    private void getDeleteView(TextView textView,int type) {
        textView.setVisibility(type);
        if (tvCompanyChildren.getVisibility()==View.GONE &&tvNameSecond.getVisibility()==View.GONE
                &&tvIsShelf.getVisibility()==View.GONE &&tvUseStatus.getVisibility()==View.GONE &&tvAddress.getVisibility()==View.GONE){
            llAdminManageScreen.setVisibility(type);
        }
        turnoverVm.constructionList(areaVm.id,areaVm.city_id);
    }

    //权限显示判断
    private void getAdminEntrance(int manage_status) {
        switch (manage_status){
            case 1://普通供应商
            case 2://子公司
                tvCompany.setText("项目名称");
                ivAdd.setVisibility(View.VISIBLE);
                break;
            case 3://集团
                tvCompany.setText("公司/项目");
                ivAdd.setVisibility(View.GONE);
                break;
            case 4://子账号
                tvCompany.setText("项目名称");
                if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ADD,0) ==1){
                    ivAdd.setVisibility(View.VISIBLE);
                }else {
                    ivAdd.setVisibility(View.GONE);
                }
                break;
        }
    }
}
