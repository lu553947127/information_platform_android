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
import com.shuangduan.zcy.adapter.MaterialCollectAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MaterialCollectBean;
import com.shuangduan.zcy.view.material.MaterialActivity;
import com.shuangduan.zcy.view.material.MaterialDetailActivity;
import com.shuangduan.zcy.vm.MineCollectionVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MaterialCollectFragment$
 * @class 基建收藏列表页
 * @time 2019/9/25 9:06
 * @change
 * @class describe
 */
public class MaterialCollectFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MineCollectionVm mineCollectionVm;

    public static MaterialCollectFragment newInstance() {

        Bundle args = new Bundle();

        MaterialCollectFragment fragment = new MaterialCollectFragment();
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
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_material_collect_info, R.string.to_look_over, this);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        MaterialCollectAdapter materialAdapter = new MaterialCollectAdapter(R.layout.item_material, null);
        materialAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(materialAdapter);
        materialAdapter.setOnItemClickListener((adapter, view, position) -> {
            MaterialCollectBean.ListBean listBean = materialAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.MATERIAL_ID, listBean.id);
            ActivityUtils.startActivity(bundle, MaterialDetailActivity.class);
        });

        mineCollectionVm = ViewModelProviders.of(this).get(MineCollectionVm.class);
        mineCollectionVm.materialCollectLiveData.observe(this, recruitBean -> {
            if (recruitBean.getPage() == 1) {
                materialAdapter.setNewData(recruitBean.getList());
                materialAdapter.setEmptyView(emptyView);
            } else {
                materialAdapter.addData(recruitBean.getList());
            }
            setNoMore(recruitBean.getPage(), recruitBean.getCount());
        });



        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineCollectionVm.moreMaterialCollection();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineCollectionVm.materialCollection();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        mineCollectionVm.materialCollection();
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
