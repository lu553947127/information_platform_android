package com.shuangduan.zcy.view.material;

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
import com.shuangduan.zcy.adapter.SellAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.vm.MaterialVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.infrastructure
 * @class describe  售卖
 * @time 2019/8/6 11:16
 * @change
 * @chang time
 * @class describe
 */
public class SellFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MaterialVm materialVm;
    private View emptyView;

    public static SellFragment newInstance() {
        Bundle args = new Bundle();
        SellFragment fragment = new SellFragment();
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
    protected void initDataAndEvent(Bundle savedInstanceState) {

        //赛选条件列表为空
        emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_substance_screen_info, R.string.see_all, this);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
//        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_h_5));
        SellAdapter adapter = new SellAdapter(R.layout.item_material_list, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            MaterialBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.MATERIAL_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, MaterialDetailActivity.class);
        });

        materialVm = ViewModelProviders.of(mActivity).get(MaterialVm.class);
        materialVm.sellLiveData.observe(this, materialBean -> {
            isInited = true;
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
                materialVm.moreSellList(materialVm.materialFlag);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                materialVm.sellList(materialVm.materialFlag);
            }
        });
    }

    @Override
    protected void initDataFromService() {
        materialVm.sellList(materialVm.materialFlag);
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
        materialVm.materialId = 0;
        materialVm.materialName = "";
        materialVm.specification = "";
        materialVm.supplierId = 0;
        materialVm.supplier = "";
        materialVm.supplierMethod = "";
        materialVm.supplierMethodId = 0;
        ((MaterialActivity) getActivity()).updateFilterStyle();
        materialVm.sellList(materialVm.materialFlag);
    }


}
