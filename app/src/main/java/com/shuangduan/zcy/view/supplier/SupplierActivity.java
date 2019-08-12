package com.shuangduan.zcy.view.supplier;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.shuangduan.zcy.adapter.SupplierAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.vm.SupplierVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.supplier
 * @class describe  优质供应商
 * @time 2019/8/12 9:37
 * @change
 * @chang time
 * @class describe
 */
public class SupplierActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    TextView tvBarRight;
    @BindView(R.id.iv_bar_right)
    ImageView ivBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_supplier;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.supplier));
        tvBarRight.setVisibility(View.GONE);
        ivBarRight.setImageResource(R.drawable.add_white);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        SupplierAdapter adapter = new SupplierAdapter(R.layout.item_supplier, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            SupplierBean.ListBean listBean = adapter.getData().get(position);
            switch (view.getId()){
                case R.id.tv_read:
                    break;
                case R.id.iv_header:
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.SUPPLIER_ID, listBean.getId());
                    ActivityUtils.startActivity(bundle, SupplierDetailActivity.class);
                    break;
            }
        });

        SupplierVm supplierVm = ViewModelProviders.of(this).get(SupplierVm.class);
        supplierVm.supplierLiveData.observe(this, supplierBean -> {
            if (supplierBean.getPage() == 1) {
                adapter.setNewData(supplierBean.getList());
                adapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                adapter.addData(supplierBean.getList());
            }
            setNoMore(supplierBean.getPage(), supplierBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                supplierVm.getMoreSupplier();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                supplierVm.getSupplier();
            }
        });
        supplierVm.getSupplier();
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

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                ActivityUtils.startActivity(SupplierJoinActivity.class);
                break;
        }
    }
}
