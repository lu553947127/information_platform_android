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
import com.shuangduan.zcy.adapter.MaterialOrderAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;
import com.shuangduan.zcy.view.material.MaterialActivity;
import com.shuangduan.zcy.vm.MaterialVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MineEquipmentFragment$
 * @class describe
 * @time 2019/10/23 11:38
 * @change
 * @class describe
 */
public class MineEquipmentFragment extends BaseFragment implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MaterialVm materialVm;

    private View emptyView;

    public static MineEquipmentFragment newInstance() {

        Bundle args = new Bundle();

        MineEquipmentFragment fragment = new MineEquipmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_info;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState,View v) {
        //赛选条件列表为空
        emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_substance_screen_info, R.string.see_all, this);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
//        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_h_5));
        MaterialOrderAdapter adapter = new MaterialOrderAdapter(R.layout.item_material_order, null);

        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            MaterialOrderBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.ORDER_ID, listBean.orderId);
            bundle.putInt(CustomConfig.MATERIALS_TYPE,CustomConfig.EQUIPMENT);
            ActivityUtils.startActivity(bundle, MaterialOrderDetailActivity.class);
        });

        materialVm = ViewModelProviders.of(mActivity).get(MaterialVm.class);
        materialVm.equipmentOrderLiveData.observe(this, materialBean -> {

            if (materialBean.getPage() == 1) {
                adapter.setNewData(materialBean.getList());
                adapter.setEmptyView(emptyView);
            } else {
                adapter.addData(materialBean.getList());
            }
            setNoMore(materialBean.getPage(), materialBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                materialVm.getMoreEquipmentOrder();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                materialVm.getEquipmentOrder();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        materialVm.getEquipmentOrder();
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
    public void onEmptyClick() {
        ActivityUtils.startActivity(MaterialActivity.class);
    }

}
