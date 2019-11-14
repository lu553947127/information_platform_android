package com.shuangduan.zcy.adminManage.view.order;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.AdminOrderListAdapter;
import com.shuangduan.zcy.adminManage.vm.AdminOrderVm;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.DrawableUtils;

import java.util.Objects;

import butterknife.BindView;

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

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;

    @BindView(R.id.rv)
    RecyclerView rv;
    private AdminOrderVm orderVm;

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

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        Drawable drawable = DrawableUtils.getDrawable(getResources().getColor(R.color.color_EDEDED), 25);
        rlSearch.setBackground(drawable);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdminOrderListAdapter adapter = new AdminOrderListAdapter(R.layout.adapter_admin_order_item, null);
        rv.setAdapter(adapter);

        adapter.setOnItemChildClickListener(this);

        orderVm = ViewModelProviders.of(this).get(AdminOrderVm.class);


        //下拉刷新和上拉加载
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                orderVm.orderListData(0, "");
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                orderVm.moreOrderListData(0, "");
            }
        });

        //获取接口返回数据
        orderVm.orderListLiveData.observe(this, item -> {
            if (item.page == 1) {
                adapter.setNewData(item.list);
            } else {
                adapter.addData(item.list);
            }

            setNoMore(item.page, item.count);
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


        orderVm.orderListData(0, "");

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
}
