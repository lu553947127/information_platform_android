package com.shuangduan.zcy.adminManage.view.order;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.AdminOrderListAdapter;
import com.shuangduan.zcy.adminManage.bean.AdminOrderBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.vm.OrderDeviceVm;
import com.shuangduan.zcy.adminManage.vm.OrderTurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.DrawableUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.weight.XEditText;

import java.util.Objects;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view
 * @ClassName: OrderDeviceFragment
 * @Description: 设备管理订单管理列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 14:11
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 14:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderDeviceFragment extends BaseLazyFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.tv_project)
    AppCompatTextView tvProject;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_order_phases)
    AppCompatTextView tvOrderPhases;
    @BindView(R.id.tv_order_type)
    AppCompatTextView tvOrderType;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_order_number)
    XEditText xetOrderNumber;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
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
    @BindView(R.id.ll_admin_manage_screen)
    LinearLayout llAdminManageScreen;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private int manage_status;
    private AdminOrderListAdapter adminOrderListAdapter;
    private OrderDeviceVm orderVm;

    public static OrderDeviceFragment newInstance() {
        Bundle args = new Bundle();
        OrderDeviceFragment fragment = new OrderDeviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_device;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("设备名称");

        manage_status = SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS, 0);
        getAdminEntrance(manage_status);

        Drawable drawable = DrawableUtils.getDrawable(getResources().getColor(R.color.color_EDEDED), 25);
        rlSearch.setBackground(drawable);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adminOrderListAdapter = new AdminOrderListAdapter(R.layout.adapter_admin_order_item, null
                , SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_EDIT, 0), manage_status, 1);
        rv.setAdapter(adminOrderListAdapter);

        adminOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {
            AdminOrderBean.OrderList listBean = adminOrderListAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            //判断当前登录身份为子账户时，是否有查看详情的权限
            switch (manage_status) {
                case 1://普通供应商
                case 2://子公司
                case 3://集团
                    bundle.putInt(CustomConfig.ADMIN_ORDER_ID, listBean.id);
                    bundle.putInt("manage_status", manage_status);
                    bundle.putInt("order_type", 1);
                    ActivityUtils.startActivity(bundle, OrderDetailsActivity.class);
                    break;
                case 4://子公司子账号
                case 5://集团子账号
                    if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ORDER_DETAIL, 0) == 1) {
                        bundle.putInt(CustomConfig.ADMIN_ORDER_ID, listBean.id);
                        bundle.putInt("manage_status", manage_status);
                        bundle.putInt("order_type", 1);
                        ActivityUtils.startActivity(bundle, OrderDetailsActivity.class);
                    }
                    break;
            }
        });

        adminOrderListAdapter.setOnItemChildClickListener(this);

        orderVm = ViewModelProviders.of(this).get(OrderDeviceVm.class);

        //订单列表返回数据
        orderVm.orderListLiveData.observe(this, item -> {
            if (item.page == 1) {
                adminOrderListAdapter.setNewData(item.list);
                adminOrderListAdapter.setEmptyView(R.layout.layout_empty_admin, rv);
            } else {
                adminOrderListAdapter.addData(item.list);
            }
            setNoMore(item.page, item.count);
        });

        //订单驳回
        orderVm.rejectLiveData.observe(this, rejectItem -> {
            AdminOrderBean.OrderList orderItem = adminOrderListAdapter.getData().get(orderVm.position);
            orderItem.statusId = 3;
            adminOrderListAdapter.notifyItemChanged(orderVm.position, orderItem);
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
                orderVm.orderDeviceListData("");
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                orderVm.moreDeviceOrderListData("");
            }
        });


        //EditTextView 搜索
        xetOrderNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //关闭软键盘
                KeyboardUtil.closeKeyboard(mActivity);
                orderVm.orderDeviceListData(xetOrderNumber.getText().toString());
                return true;
            }
            return false;
        });

        //EditText 的 X号 点击事件
        xetOrderNumber.setOnTouchListener((v, event) -> {
            Drawable drawable1 = xetOrderNumber.getCompoundDrawables()[2];
            //如果右边没有图片，不再处理
            if (drawable1 == null)
                return false;
            //如果不是按下事件，不再处理
            if (event.getAction() != MotionEvent.ACTION_UP)
                return false;
            if (event.getX() > xetOrderNumber.getWidth() - xetOrderNumber.getPaddingRight() - drawable1.getIntrinsicWidth()) {
                KeyboardUtil.closeKeyboard(mActivity);
                orderVm.orderDeviceListData("");
            }
            return false;
        });

        //修改订单进度成功回调监听
        orderVm.orderPhasesLiveData.observe(this, phases -> {
            LogUtils.e(phases);
            AdminOrderBean.OrderList orderItem = adminOrderListAdapter.getData().get(orderVm.position);
            orderItem.statusUpdate = phases.statusUpdate;
            orderItem.phases = orderVm.phasesName;
            adminOrderListAdapter.notifyItemChanged(orderVm.position, orderItem);
        });

    }

    @Override
    protected void initDataFromService() {
        orderVm.orderDeviceListData("");
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


    //权限显示判断
    private void getAdminEntrance(int manage_status) {
        switch (manage_status) {
            case 1://普通供应商
                tvProject.setText("项目名称");
                tvOrderType.setVisibility(View.GONE);
                break;
            case 2://子公司
            case 4://子公司子账号
                tvProject.setText("项目名称");
                tvOrderType.setVisibility(View.VISIBLE);
                break;
            case 3://集团
            case 5://集团子账号
                tvProject.setText("公司/项目");
                tvOrderType.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        orderVm.position = position;
        AdminOrderBean.OrderList orderItem = adminOrderListAdapter.getData().get(position);
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
                                orderVm.equipmentOrderStatus(orderItem.id);
                            }
                        }).showDialog();
                break;
            case R.id.tv_progress://修改进度
//                getBottomSheetDialog(R.layout.dialog_is_grounding, "order_phases", 1);
                break;
        }
    }
}
