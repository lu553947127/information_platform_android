package com.shuangduan.zcy.adminManage.view.order;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.AdminOrderListAdapter;
import com.shuangduan.zcy.adminManage.adapter.OrderInsideAdapter;
import com.shuangduan.zcy.adminManage.adapter.OrderPhasesAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverCompanyAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverProjectAdapter;
import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.adminManage.bean.OrderSearchBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverCompanyBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.vm.OrderTurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.base.BaseNoRefreshFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.DrawableUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.PhoneUtils;
import com.shuangduan.zcy.weight.XEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: OrderTurnoverFragment
 * @Description: ??????????????????????????????
 * @Author: ?????????
 * @CreateDate: 2019/10/25 13:30
 * @UpdateUser: ?????????
 * @UpdateDate: 2019/10/25 13:30
 * @UpdateRemark: ????????????
 * @Version: 1.0
 */
public class OrderTurnoverFragment extends BaseNoRefreshFragment implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.tv_project)
    AppCompatTextView tvProject;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_order_phases)
    AppCompatTextView tvOrderPhases;
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
    @BindView(R.id.tv_order_number)
    XEditText xetOrderNumber;
    private OrderTurnoverVm orderVm;
    private AdminOrderListAdapter adminOrderListAdapter;
    private int manage_status;
    private View emptyView = null;

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
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_material_order_turnover, 0,R.color.colorBgDark, null);

        manage_status = SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS, 0);
        getAdminEntrance(manage_status);

        Drawable drawable = DrawableUtils.getDrawable(getResources().getColor(R.color.color_EDEDED), 25);
        rlSearch.setBackground(drawable);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adminOrderListAdapter = new AdminOrderListAdapter(R.layout.adapter_admin_order_item, null
                , SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_EDIT, 0), manage_status, 0);
        rv.setAdapter(adminOrderListAdapter);

        adminOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {
            AdminOrderBean.OrderList listBean = adminOrderListAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            //????????????????????????????????????????????????????????????????????????
            switch (manage_status) {
                case 1://???????????????
                case 2://?????????
                case 3://??????
                    bundle.putInt(CustomConfig.ADMIN_ORDER_ID, listBean.orderId);
                    bundle.putInt("manage_status", manage_status);
                    bundle.putInt("order_type", 3);
                    ActivityUtils.startActivity(bundle, OrderDetailsActivity.class);
                    break;
                case 4://??????????????????
                case 5://???????????????
                    if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_DETAIL, 0) == 1) {
                        bundle.putInt(CustomConfig.ADMIN_ORDER_ID, listBean.orderId);
                        bundle.putInt("manage_status", manage_status);
                        bundle.putInt("order_type", 3);
                        ActivityUtils.startActivity(bundle, OrderDetailsActivity.class);
                    }
                    break;
            }
        });

        adminOrderListAdapter.setOnItemChildClickListener(this);

        orderVm = mActivity.getViewModel(OrderTurnoverVm.class);

        //????????????????????????
        orderVm.orderListLiveData.observe(this, item -> {
            if (item.page == 1) {
                adminOrderListAdapter.setNewData(item.list);
                adminOrderListAdapter.setEmptyView(emptyView);
            } else {
                adminOrderListAdapter.addData(item.list);
            }
            setNoMore(item.page, item.count);
        });

        //???????????????????????????
        orderVm.turnoverCompanyData.observe(this, turnoverCompanyBeans -> {
            companyList = turnoverCompanyBeans;
            turnoverCompanyAdapter.setNewData(companyList);
        });

        //????????????????????????
        orderVm.turnoverProject.observe(this, turnoverNameBeans -> {
            projectList = turnoverNameBeans;
            if (manage_status==3||manage_status==5&&!projectList.get(0).name.equals("??????")) projectList.add(0,new TurnoverNameBean(0,"??????"));
            turnoverProjectAdapter.setNewData(projectList);
        });

        //??????????????????????????????
        orderVm.rejectLiveData.observe(this, rejectItem -> {
            AdminOrderBean.OrderList orderItem = adminOrderListAdapter.getData().get(orderVm.position);
            orderItem.statusId = 3;
            orderItem.status = "????????????";
            adminOrderListAdapter.notifyItemChanged(orderVm.position, orderItem);
        });

        //??????????????????????????????
        orderVm.orderSearchLiveData.observe(this, orderSearchBean -> {
            //??????????????????
            orderPhasesList = orderSearchBean.getOrder_phases();
            //??????????????????
            orderInsideList = orderSearchBean.getInside();
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

        //???????????????????????????
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                orderVm.orderListData("");
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                orderVm.moreOrderListData("");
            }
        });

        //EditTextView ??????
        xetOrderNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //???????????????
                KeyboardUtil.closeKeyboard(mActivity);
                orderVm.orderListData(xetOrderNumber.getText().toString());
                return true;
            }
            return false;
        });

        //EditText ??? X??? ????????????
        xetOrderNumber.setOnTouchListener((v, event) -> {
            Drawable drawable1 = xetOrderNumber.getCompoundDrawables()[2];
            //???????????????????????????????????????
            if (drawable1 == null)
                return false;
            //???????????????????????????????????????
            if (event.getAction() != MotionEvent.ACTION_UP)
                return false;
            if (event.getX() > xetOrderNumber.getWidth() - xetOrderNumber.getPaddingRight() - drawable1.getIntrinsicWidth()) {
                KeyboardUtil.closeKeyboard(mActivity);
                orderVm.orderListData("");
            }
            return false;
        });

        //????????????????????????????????????
        orderVm.orderPhases.observe(this, phases -> {
            LogUtils.e(phases);
            AdminOrderBean.OrderList orderItem = adminOrderListAdapter.getData().get(orderVm.position);
            orderItem.statusUpdate = phases.statusUpdate;
            orderItem.phases = orderVm.phasesName;
            orderItem.phasesId = orderVm.updatePhasesId;
            adminOrderListAdapter.notifyItemChanged(orderVm.position, orderItem);
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
        if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_LIST, 0) == 1){
            orderVm.orderListData("");
            orderVm.orderSearch();
        }
    }

    //Adapter Child ???????????????
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        orderVm.position = position;
        AdminOrderBean.OrderList orderItem = adminOrderListAdapter.getData().get(position);
        switch (view.getId()) {
            case R.id.tv_reject://??????
                new CustomDialog(Objects.requireNonNull(getActivity()))
                        .setTip(getString(R.string.admin_turnover_reject))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                orderVm.constructionOrderEditStatus(orderItem.orderId);
                            }
                        }).showDialog();
                break;
            case R.id.tv_progress://????????????
                //????????????????????? ??????????????????
                if (!orderPhasesList.get(2).getName().equals("????????????")&&orderItem.inside==1&&orderItem.method==2){
                    orderPhasesList.add(2,new OrderSearchBean.OrderPhasesBean(4,"????????????"));
                }else if (orderPhasesList.get(2).getName().equals("????????????")&&orderItem.inside==1&&orderItem.method==1){
                    orderPhasesList.remove(2);
                }else if (orderPhasesList.get(2).getName().equals("????????????")&&orderItem.inside==3&&orderItem.method==1){
                    orderPhasesList.remove(2);
                }else if (orderPhasesList.get(2).getName().equals("????????????")&&orderItem.inside==3&&orderItem.method==2){
                    orderPhasesList.remove(2);
                }
                LogUtils.i(orderPhasesList);
                orderVm.phasesId = orderItem.phasesId;
                getBottomSheetDialog(R.layout.dialog_is_grounding, "order_phases", 1);
                break;
        }
    }

    @OnClick({R.id.tv_project, R.id.tv_name, R.id.tv_order_phases, R.id.tv_order_type
            , R.id.tv_reset, R.id.tv_company_children, R.id.tv_name_second, R.id.tv_is_shelf, R.id.tv_use_status})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_project://???????????????/??????
                if (manage_status == 3 || manage_status == 5) {
                    getBottomSheetDialog(R.layout.dialog_depositing_place, "company", 0);
                } else {
                    getBottomSheetDialog(R.layout.dialog_is_grounding, "project", 0);
                }
                getDrawableRightView(tvProject, R.drawable.icon_pullup_arrow, R.color.color_5C54F4);
                break;
            case R.id.tv_name://??????????????????
                getBottomSheetDialog(R.layout.dialog_search_edit,"edit",0);
                break;
            case R.id.tv_order_phases://??????????????????
                orderVm.phasesId = 0;
                if (!orderPhasesList.get(2).getName().equals("????????????")) {
                    orderPhasesList.add(2, new OrderSearchBean.OrderPhasesBean(4, "????????????"));
                }
                getBottomSheetDialog(R.layout.dialog_is_grounding, "order_phases", 0);
                getDrawableRightView(tvOrderPhases, R.drawable.icon_pullup_arrow, R.color.color_5C54F4);
                break;
            case R.id.tv_order_type://??????????????????
                getBottomSheetDialog(R.layout.dialog_is_grounding, "order_type", 0);
                getDrawableRightView(tvOrderType, R.drawable.icon_pullup_arrow, R.color.color_5C54F4);
                break;
            case R.id.tv_reset://????????????
                llAdminManageScreen.setVisibility(View.GONE);
                tvOne.setVisibility(View.GONE);
                tvTwo.setVisibility(View.GONE);
                tvThree.setVisibility(View.GONE);
                tvFour.setVisibility(View.GONE);
                orderVm.supplier_id = 0;
                orderVm.unit_id = 0;
                orderVm.material_name = "";
                orderVm.phases = 0;
                orderVm.inside = 0;
                orderVm.orderListData("");
                break;
            case R.id.tv_company_children://?????????/??????
                orderVm.supplier_id = 0;
                orderVm.unit_id = 0;
                getDeleteView(tvOne);
                break;
            case R.id.tv_name_second://????????????
                orderVm.material_name = "";
                getDeleteView(tvTwo);
                break;
            case R.id.tv_is_shelf://????????????
                orderVm.phases = 0;
                getDeleteView(tvThree);
                break;
            case R.id.tv_use_status://????????????
                orderVm.inside = 0;
                getDeleteView(tvFour);
                break;
        }
    }

    private TurnoverCompanyAdapter turnoverCompanyAdapter;
    private List<TurnoverCompanyBean> companyList = new ArrayList<>();
    private TurnoverProjectAdapter turnoverProjectAdapter;
    private List<TurnoverNameBean> projectList = new ArrayList<>();
    private List<OrderSearchBean.OrderPhasesBean> orderPhasesList = new ArrayList<>();
    private List<OrderSearchBean.InsideBean> orderInsideList = new ArrayList<>();
    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog(int layout, String type, int updateState) {
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
            getDrawableRightView(tvProject, R.drawable.icon_pulldown_arrow, R.color.color_666666);
            getDrawableRightView(tvOrderPhases, R.drawable.icon_pulldown_arrow, R.color.color_666666);
            getDrawableRightView(tvOrderType, R.drawable.icon_pulldown_arrow, R.color.color_666666);
            if (KeyboardUtil.isSoftShowing(mActivity)){
                KeyboardUtil.showORhideSoftKeyboard(mActivity);
            }
        });
        orderVm.type = type;
        switch (type) {
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
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project_company, null, 7);
                rvCompany.setAdapter(turnoverCompanyAdapter);
                rvProjectGroup.setAdapter(turnoverProjectAdapter);
                turnoverCompanyAdapter.setOnItemClickListener((adapter, view, position) -> {
                    orderVm.supplier_id = companyList.get(position).getSupplier_id();
                    orderVm.supplier_name = companyList.get(position).getCompany();
                    orderVm.getUnitInfo();
                    turnoverCompanyAdapter.setIsSelect(companyList.get(position).getSupplier_id());
                });
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    orderVm.unit_id = projectList.get(position).id;
                    orderVm.orderListData("");
                    turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvProject, R.drawable.icon_pulldown_arrow, R.color.color_666666);
                    if (orderVm.unit_id != 0) {
                        getAddTopScreenView(tvOne, projectList.get(position).name);
                    } else {
                        getAddTopScreenView(tvOne, orderVm.supplier_name);
                    }
                });
                orderVm.getSupplierInfo();
                if (orderVm.supplier_id != 0) {
                    turnoverCompanyAdapter.setIsSelect(orderVm.supplier_id);
                    orderVm.getUnitInfo();
                }
                if (orderVm.unit_id != 0) {
                    turnoverProjectAdapter.setIsSelect(orderVm.unit_id);
                }
                break;
            case "project":
                TextView tvProject = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvProject).setText("????????????");
                RecyclerView rvProject = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvProject).setLayoutManager(new LinearLayoutManager(getActivity()));
                turnoverProjectAdapter = new TurnoverProjectAdapter(R.layout.adapter_turnover_project, null, 12);
                rvProject.setAdapter(turnoverProjectAdapter);
                turnoverProjectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    orderVm.unit_id = projectList.get(position).id;
                    orderVm.orderListData("");
                    turnoverProjectAdapter.setIsSelect(projectList.get(position).id);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvProject, R.drawable.icon_pulldown_arrow, R.color.color_666666);
                    getAddTopScreenView(tvOne, projectList.get(position).name);
                });
                orderVm.getUnitInfo();
                if (orderVm.unit_id != 0) {
                    turnoverProjectAdapter.setIsSelect(orderVm.unit_id);
                }
                break;
            case "order_phases":
                TextView tv_order_phases = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tv_order_phases).setText("????????????");
                RecyclerView rvOrderPhases = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvOrderPhases).setLayoutManager(new LinearLayoutManager(getActivity()));
                OrderPhasesAdapter orderPhasesAdapter = new OrderPhasesAdapter(R.layout.adapter_selector_area_second, orderPhasesList);
                rvOrderPhases.setAdapter(orderPhasesAdapter);
                orderPhasesAdapter.setOnItemClickListener((adapter, view, position) -> {

                    orderVm.phasesName = orderPhasesList.get(position).getName();
                    if (updateState == 0) {
                        orderVm.phases = orderPhasesList.get(position).getId();
                        getDrawableRightView(tvOrderPhases, R.drawable.icon_pulldown_arrow, R.color.color_666666);
                        orderVm.orderListData("");
                        getAddTopScreenView(tvThree, orderPhasesList.get(position).getName());
                    } else {
                        OrderSearchBean.OrderPhasesBean orderPhases = orderPhasesList.get(position);

                        orderVm.updatePhasesId = orderPhases.getId();
                        AdminOrderBean.OrderList order = adminOrderListAdapter.getData().get(orderVm.position);
                        if (orderPhasesList.get(position).getId() <= order.phasesId) {
                            ToastUtils.showShort("????????????????????????");
                            return;
                        }

                        int index = 0;
                        for (int i = 0; i < orderPhasesList.size(); i++) {
                            if (order.phasesId == orderPhasesList.get(i).getId()) {
                                index = i;
                            }
                        }
                        if (position - index != 1) {
                            ToastUtils.showShort("????????????????????????");
                            return;
                        }
                        orderVm.constructionOrderPhases(order.orderId, orderPhasesList.get(position).getId());
                    }
                    btn_dialog.dismiss();
                });
                if (orderVm.phases != 0) {
                    orderPhasesAdapter.setIsSelect(orderVm.phases);
                }
                if (orderVm.phasesId != 0){
                    orderPhasesAdapter.setIsSelect(orderVm.phasesId);
                }
                break;
            case "order_type":
                TextView tv_order_inside = btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tv_order_inside).setText("????????????");
                RecyclerView rvOrderInside = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvOrderInside).setLayoutManager(new LinearLayoutManager(getActivity()));
                OrderInsideAdapter orderInsideAdapter = new OrderInsideAdapter(R.layout.adapter_selector_area_second, orderInsideList);
                rvOrderInside.setAdapter(orderInsideAdapter);
                orderInsideAdapter.setOnItemClickListener((adapter, view, position) -> {
                    orderVm.inside = orderInsideList.get(position).getId();
                    orderVm.orderListData("");
                    btn_dialog.dismiss();
                    getDrawableRightView(tvOrderType, R.drawable.icon_pulldown_arrow, R.color.color_666666);
                    getAddTopScreenView(tvFour, orderInsideList.get(position).getName());

                });
                if (orderVm.inside != 0) {
                    orderInsideAdapter.setIsSelect(orderVm.inside);
                }
                break;
            case "edit"://??????????????????
                XEditText xEditText = dialog_view.findViewById(R.id.edit);
                TextView tvSearch = dialog_view.findViewById(R.id.tv_search);
                View view = dialog_view.findViewById(R.id.view);
                PhoneUtils.isPhone(view);
                KeyboardUtil.showSoftInputFromWindow((BaseActivity) getActivity(), xEditText);
                tvSearch.setOnClickListener(v -> {
                    orderVm.material_name = xEditText.getText().toString();
                    orderVm.orderListData("");
                    getAddTopScreenView(tvTwo, xEditText.getText().toString());
                    btn_dialog.dismiss();
                });
                //EditTextView ??????
                xEditText.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        //???????????????
                        KeyboardUtil.closeKeyboard(mActivity);
                        orderVm.material_name = xEditText.getText().toString();
                        orderVm.orderListData("");
                        getAddTopScreenView(tvTwo, xEditText.getText().toString());
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
    private void getDrawableRightView(TextView textView, int icon, int color) {
        Drawable drawable = mContext.getResources().getDrawable(icon);
        // ?????????????????????,??????????????????.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setTextColor(getResources().getColor(color));
    }

    //????????????????????????view
    private void getAddTopScreenView(TextView textView, String text) {
        llAdminManageScreen.setVisibility(View.VISIBLE);
        tvReset.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

    //??????????????????
    private void getDeleteView(TextView textView) {
        textView.setVisibility(View.GONE);
        if (tvOne.getVisibility() == View.GONE && tvTwo.getVisibility() == View.GONE
                && tvThree.getVisibility() == View.GONE && tvFour.getVisibility() == View.GONE) {
            llAdminManageScreen.setVisibility(View.GONE);
        }
        orderVm.orderListData("");
    }

    //??????????????????
    private void getAdminEntrance(int manage_status) {
        switch (manage_status) {
            case 1://???????????????
                tvProject.setText("????????????");
                tvOrderType.setVisibility(View.GONE);
                break;
            case 2://?????????
            case 4://??????????????????
                tvProject.setText("????????????");
                tvOrderType.setVisibility(View.VISIBLE);
                break;
            case 3://??????
            case 5://???????????????
                tvProject.setText("??????/??????");
                tvOrderType.setVisibility(View.VISIBLE);
                break;
        }
    }
}
