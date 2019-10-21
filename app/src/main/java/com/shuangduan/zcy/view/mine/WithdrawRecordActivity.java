package com.shuangduan.zcy.view.mine;

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
import com.shuangduan.zcy.adapter.WithdrawRecordAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.WithdrawRecordBean;
import com.shuangduan.zcy.vm.WithdrawVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  提现记录
 * @time 2019/8/6 17:47
 * @change
 * @chang time
 * @class describe
 */
public class WithdrawRecordActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private WithdrawVm withdrawVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_income,R.string.empty_withdraw_info,0,null);

        tvBarTitle.setText(getString(R.string.withdraw_record));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        WithdrawRecordAdapter adapter = new WithdrawRecordAdapter(R.layout.item_withdraw_record, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            WithdrawRecordBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.WITHDRAW_RECORD_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, WithdrawDetailActivity.class);
        });

        withdrawVm = ViewModelProviders.of(this).get(WithdrawVm.class);
        withdrawVm.recordLiveData.observe(this, withdrawRecordBean -> {
            if (withdrawRecordBean.getPage() == 1) {
                adapter.setNewData(withdrawRecordBean.getList());
                adapter.setEmptyView(emptyView);
            }else {
                adapter.addData(withdrawRecordBean.getList());
            }
            setNoMore(withdrawRecordBean.getPage(), withdrawRecordBean.getCount());
        });
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                withdrawVm.moreWithdrawRecord();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                withdrawVm.withdrawRecord();
            }
        });
        withdrawVm.withdrawRecord();
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

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
