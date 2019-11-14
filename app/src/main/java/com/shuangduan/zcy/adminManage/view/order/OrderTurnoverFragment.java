package com.shuangduan.zcy.adminManage.view.order;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.AdminOrderListAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverCompanyAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverProjectAdapter;
import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.event.OrderTurnoverEvent;
import com.shuangduan.zcy.adminManage.view.SelectTypeActivity;
import com.shuangduan.zcy.adminManage.vm.AdminOrderVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.DrawableUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: OrderTurnoverFragment
 * @Description: 周转材料订单管理列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 13:30
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 13:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderTurnoverFragment extends BaseLazyFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.tv_project)
    AppCompatTextView tvProject;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_order_manage)
    AppCompatTextView tvOrderManage;
    @BindView(R.id.tv_order_type)
    AppCompatTextView tvOrderType;

    @BindView(R.id.ll_admin_manage_screen)
    LinearLayout llAdminManageScreen;
    @BindView(R.id.tv_reset)
    AppCompatTextView tvReset;
    @BindView(R.id.tv_company_children)
    AppCompatTextView tvOne;
    @BindView(R.id.tv_name_second)
    AppCompatTextView tvTwo;
    @BindView(R.id.tv_is_shelf)
    AppCompatTextView tvThree;
    @BindView(R.id.tv_use_status)
    AppCompatTextView tvFour;

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    private AdminOrderVm orderVm;
    private int manage_status;

    public static OrderTurnoverFragment newInstance() {
        Bundle args = new Bundle();
        OrderTurnoverFragment fragment = new OrderTurnoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_turnover;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        manage_status = SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS,0);
        getAdminEntrance(manage_status);

        Drawable drawable = DrawableUtils.getDrawable(getResources().getColor(R.color.color_EDEDED), 25);
        rlSearch.setBackground(drawable);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdminOrderListAdapter adminOrderListAdapter = new AdminOrderListAdapter(R.layout.adapter_admin_order_item, null
                ,SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_EDIT,0),manage_status);
        rv.setAdapter(adminOrderListAdapter);

        adminOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {
            AdminOrderBean.OrderList listBean = adminOrderListAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            //判断当前登录身份为子账户时，是否有查看详情的权限
            switch (manage_status){
                case 1://普通供应商
                case 2://子公司
                case 3://集团

                    break;
                case 4://子公司子账号
                case 5://集团子账号
                    if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_DETAIL,0)==1){
                        bundle.putInt(CustomConfig.ADMIN_ORDER_ID,listBean.orderId);

                    }
                    break;
            }
        });

        adminOrderListAdapter.setOnItemChildClickListener(this);

        orderVm = ViewModelProviders.of(this).get(AdminOrderVm.class);

        //订单列表返回数据
        orderVm.orderListLiveData.observe(this, item -> {
            if (item.page == 1) {
                adminOrderListAdapter.setNewData(item.list);
            } else {
                adminOrderListAdapter.addData(item.list);
            }
            setNoMore(item.page, item.count);
        });

        //获取子公司列表数据
        orderVm.turnoverCompanyData.observe(this,turnoverCompanyBeans -> {
            companyList=turnoverCompanyBeans;
            turnoverCompanyAdapter.setNewData(companyList);
            if (orderVm.supplier_id==0){
                orderVm.supplier_id=companyList.get(0).getSupplier_id();
                orderVm.supplier_name = companyList.get(0).getCompany();
                turnoverCompanyAdapter.setIsSelect(orderVm.supplier_id);
                orderVm.getUnitInfo();
            }
        });

        //获取项目列表数据
        orderVm.turnoverProject.observe(this,turnoverNameBeans -> {
            projectList = turnoverNameBeans;
            if (SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS,0)==3)projectList.add(0,new TurnoverNameBean(0,"全部"));
            turnoverProjectAdapter.setNewData(projectList);
        });

        orderVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        //下拉刷新和上拉加载
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                orderVm.orderListData( "");
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                orderVm.moreOrderListData( "");
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
        orderVm.orderListData( "");
    }

    //Adapter Child 的点击事件
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_reject://驳回
                new CustomDialog(Objects.requireNonNull(getActivity()))
                        .setTip(getString(R.string.admin_turnover_reject))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                            }
                        }).showDialog();
                break;
            case R.id.tv_progress://修改进度
                break;
        }
    }

    @OnClick({R.id.tv_project,R.id.tv_name,R.id.tv_order_manage,R.id.tv_order_type
            ,R.id.tv_reset,R.id.tv_company_children,R.id.tv_name_second,R.id.tv_is_shelf,R.id.tv_use_status})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.tv_project://选择子公司/项目
                if (manage_status==3){
                    getBottomSheetDialog(R.layout.dialog_depositing_place,"company");
                }else {
                    getBottomSheetDialog(R.layout.dialog_is_grounding,"project");
                }
                getDrawableRightView(tvProject,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_name://选择材料名称
                bundle.putInt(CustomConfig.ADMIN_MANAGE_TYPE,CustomConfig.ADMIN_MANAGE_CONSTRUCTION_ORDER);
                ActivityUtils.startActivity(bundle, SelectTypeActivity.class);
                break;
            case R.id.tv_order_manage://选择订单管理
                break;
            case R.id.tv_order_type://选择订单类型
                break;
            case R.id.tv_reset://重置按钮
                llAdminManageScreen.setVisibility(View.GONE);
                tvOne.setVisibility(View.GONE);
                tvTwo.setVisibility(View.GONE);
                tvThree.setVisibility(View.GONE);
                tvFour.setVisibility(View.GONE);
                orderVm.supplier_id=0;
                orderVm.unit_id=0;
                orderVm.categoryId=0;
                orderVm.orderListData( "");
                break;
            case R.id.tv_company_children://子公司/项目
                orderVm.supplier_id=0;
                orderVm.unit_id=0;
                getDeleteView(tvOne,View.GONE);
                break;
            case R.id.tv_name_second://材料名称
                orderVm.categoryId=0;
                getDeleteView(tvTwo,View.GONE);
                break;
            case R.id.tv_is_shelf://订单管理
                break;
            case R.id.tv_use_status://订单类型
                break;
        }
    }

    private TurnoverCompanyAdapter turnoverCompanyAdapter;
    private List<TurnoverCompanyBean> companyList = new ArrayList<>();
    private TurnoverProjectAdapter turnoverProjectAdapter;
    private List<TurnoverNameBean> projectList = new ArrayList<>();
    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog(int layout, String type) {
        //底部滑动对话框
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(Objects.requireNonNull(getActivity()), R.style.BottomSheetStyle);
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
            getDrawableRightView(tvProject,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvName,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvOrderManage,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvOrderType,R.drawable.icon_pulldown_arrow,R.color.color_666666);
        });
        orderVm.type=type;
        switch (type){
            case "company":
                TextView tvFirst = btn_dialog.findViewById(R.id.tv_first);
                TextView tvSecond = btn_dialog.findViewById(R.id.tv_second);
                RecyclerView rvCompany = btn_dialog.findViewById(R.id.rv_province);
                RecyclerView rvProjectGroup = btn_dialog.findViewById(R.id.rv_city);
                Objects.requireNonNull(tvFirst).setText("选择下属公司");
                Objects.requireNonNull(tvSecond).setText("选择项目");
                Objects.requireNonNull(rvCompany).setLayoutManager(new LinearLayoutManager(getActivity()));
                Objects.requireNonNull(rvProjectGroup).setLayoutManager(new LinearLayoutManager(getActivity()));
                turnoverCompanyAdapter = new TurnoverCompanyAdapter(R.layout.adapter_selector_area_first, null);
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project, null);
                rvCompany.setAdapter(turnoverCompanyAdapter);
                rvProjectGroup.setAdapter(turnoverProjectAdapter);
                turnoverCompanyAdapter.setOnItemClickListener((adapter, view, position) -> {
                    orderVm.supplier_id = companyList.get(position).getSupplier_id();
                    orderVm.supplier_name = companyList.get(position).getCompany();
                    orderVm.getUnitInfo();
                    turnoverCompanyAdapter.setIsSelect(companyList.get(position).getSupplier_id());
                });
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (projectList.get(position).id!=0){
                        orderVm.unit_id = projectList.get(position).id;
                        orderVm.orderListData( "");
                        turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                        btn_dialog.dismiss();
                        getDrawableRightView(tvProject,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                        getAddTopScreenView(tvOne,projectList.get(position).name,View.VISIBLE);
                    }else {
                        orderVm.unit_id=0;
                        orderVm.orderListData( "");
                        btn_dialog.dismiss();
                        getDrawableRightView(tvProject,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                        getAddTopScreenView(tvOne,orderVm.supplier_name,View.VISIBLE);
                    }
                });
                orderVm.getSupplierInfo();
                if (orderVm.supplier_id!=0){
                    turnoverCompanyAdapter.setIsSelect(orderVm.supplier_id);
                    orderVm.getUnitInfo();
                }
                if (orderVm.unit_id!=0){
                    turnoverProjectAdapter.setIsSelect(orderVm.unit_id);
                }
                break;
            case "project":
                TextView tvProject=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvProject).setText("选择项目");
                RecyclerView rvProject = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvProject).setLayoutManager(new LinearLayoutManager(getActivity()));
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project, null);
                rvProject.setAdapter(turnoverProjectAdapter);
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    orderVm.unit_id = projectList.get(position).id;
                    orderVm.orderListData( "");
                    turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvProject,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    getAddTopScreenView(tvOne,projectList.get(position).name,View.VISIBLE);
                });
                orderVm.getUnitInfo();
                if (orderVm.unit_id!=0){
                    turnoverProjectAdapter.setIsSelect(orderVm.unit_id);
                }
                break;
        }
        btn_dialog.show();
    }


    //给textView设置drawableRight图片
    private void getDrawableRightView(TextView textView, int icon, int color) {
        Drawable drawable = mContext.getResources().getDrawable(icon);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setTextColor(getResources().getColor(color));
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
        if (tvOne.getVisibility()==View.GONE &&tvTwo.getVisibility()==View.GONE
                &&tvThree.getVisibility()==View.GONE &&tvFour.getVisibility()==View.GONE ){
            llAdminManageScreen.setVisibility(type);
        }
        orderVm.orderListData( "");
    }

    @Subscribe
    public void onEventOrderTurnover(OrderTurnoverEvent event) {
        orderVm.categoryId=event.material_id;
        orderVm.orderListData( "");
        getAddTopScreenView(tvTwo,event.material_name,View.VISIBLE);
    }

    //权限显示判断
    private void getAdminEntrance(int manage_status) {
        switch (manage_status){
            case 1://普通供应商
            case 2://子公司
            case 4://子公司子账号
                tvProject.setText("项目名称");
                break;
            case 3://集团
            case 5://集团子账号
                tvProject.setText("公司/项目");
                break;
        }
    }
}
