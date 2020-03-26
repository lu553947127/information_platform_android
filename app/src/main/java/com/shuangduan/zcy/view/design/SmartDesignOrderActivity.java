package com.shuangduan.zcy.view.design;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SmartDesignOrderAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.vm.SmartDesignVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: SmartDesignOrderActivity
 * @Description: 智能设置订单列表
 * @Author: 徐玉
 * @CreateDate: 2019/12/15 15:14
 * @UpdateUser: 徐玉
 * @UpdateDate: 2019/12/15 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SmartDesignOrderActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;

    private View emptyView;
    private SmartDesignVm vm;

    private int mPosition;
    private SmartDesignOrderAdapter mAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_smart_design_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.design_order_title);

        emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.hint_design_order, R.string.go_design, this);

        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this), DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));

        mAdapter = new SmartDesignOrderAdapter(R.layout.adapter_smart_design_item, null);

        rvOrder.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener((adapter1, view, position) -> {
            this.mPosition = position;
            Bundle bundle = new Bundle();
            bundle.putInt("id", mAdapter.getData().get(position).id);
            ActivityUtils.startActivityForResult(bundle, this, SmartDesignDetailsActivity.class, 200);
        });


        vm = getViewModel(SmartDesignVm.class);

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                vm.moreDataList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                vm.dataList();
            }
        });


        vm.orderLiveData.observe(this, item -> {

            if (item.page == 1) {
                mAdapter.setNewData(item.list);
                mAdapter.setEmptyView(emptyView);
            } else {
                mAdapter.addData(item.list);
            }
            setNoMore(item.page, item.count);
        });

        vm.dataList();
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

    @OnClick({R.id.iv_bar_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
        }
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(SmartDesignActivity.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 200) {
            mAdapter.remove(mPosition);
        }
    }
}
