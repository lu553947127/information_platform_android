package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SupplierOrderAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.view.supplier.SupplierActivity;
import com.shuangduan.zcy.vm.OrderVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  供应商订单
 * @time 2019/8/13 10:42
 * @change
 * @chang time
 * @class describe
 */
public class SupplierOrderFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private OrderVm orderVm;

    public static SupplierOrderFragment newInstance() {

        Bundle args = new Bundle();

        SupplierOrderFragment fragment = new SupplierOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_recruit;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_get_supplier_info, R.string.to_look_over, this);


        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        SupplierOrderAdapter adapter = new SupplierOrderAdapter(R.layout.item_order_supplier, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        orderVm = ViewModelProviders.of(mActivity).get(OrderVm.class);
        orderVm.supplierLiveData.observe(this, orderListBean -> {
            isInited = true;
            if (orderListBean.getPage() == 1) {
                adapter.setNewData(orderListBean.getList());
                adapter.setEmptyView(emptyView);
            }else {
                adapter.addData(orderListBean.getList());
            }
            setNoMore(orderListBean.getPage(), orderListBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                orderVm.getMoreSupplierOrder();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                orderVm.getSupplierOrder();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        orderVm.getSupplierOrder();
    }

    private void setNoMore(int page, int count){
        if (page == 1){
            if (page * 10 >= count){
                if (refresh.getState() == RefreshState.None){
                    refresh.setNoMoreData(true);
                }else {
                    refresh.finishRefreshWithNoMoreData();
                }
            }else {
                refresh.finishRefresh();
            }
        }else {
            if (page * 10 >= count){
                refresh.finishLoadMoreWithNoMoreData();
            }else {
                refresh.finishLoadMore();
            }
        }
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(SupplierActivity.class);
    }
}
