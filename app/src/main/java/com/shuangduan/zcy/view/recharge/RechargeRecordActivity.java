package com.shuangduan.zcy.view.recharge;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.RechargeRecordAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.RechargeRecordBean;
import com.shuangduan.zcy.vm.RechargeRecordVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recharge
 * @class describe  充值记录
 * @time 2019/8/13 15:41
 * @change
 * @chang time
 * @class describe
 */
public class RechargeRecordActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recharge_record;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_income, R.string.empty_recharge_info, 0, null);

        tvBarTitle.setText(getString(R.string.recharge_record));

        RechargeRecordVm rechargeRecordVm = ViewModelProviders.of(this).get(RechargeRecordVm.class);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        RechargeRecordAdapter recordAdapter = new RechargeRecordAdapter(R.layout.item_recharge_record, null);
        recordAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(recordAdapter);
        recordAdapter.setOnItemClickListener((adapter, view, position) -> {
            RechargeRecordBean.ListBean listBean = recordAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.RECHARGE_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, RechargeRecordDetailActivity.class);
        });

        rechargeRecordVm.recordLiveData.observe(this, rechargeRecordBean -> {
            if (rechargeRecordBean.getPage() == 1) {
                recordAdapter.setNewData(rechargeRecordBean.getList());
                recordAdapter.setEmptyView(emptyView);
            } else {
                recordAdapter.addData(rechargeRecordBean.getList());
            }
            setNoMore(rechargeRecordBean.getPage(), rechargeRecordBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                rechargeRecordVm.getMoreRecord();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                rechargeRecordVm.getRecord();
            }
        });

        rechargeRecordVm.getRecord();
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

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }
}
