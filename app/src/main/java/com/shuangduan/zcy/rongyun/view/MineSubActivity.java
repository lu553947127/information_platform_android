package com.shuangduan.zcy.rongyun.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SubscribeNeedAdapter;
import com.shuangduan.zcy.adapter.SubscribeAdapter;
import com.shuangduan.zcy.adapter.SubscribeOrderAdapter;
import com.shuangduan.zcy.adminManage.view.order.OrderDetailsActivity;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.SubscriptionTypeDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.model.bean.SubscribeBean;
import com.shuangduan.zcy.model.bean.SubscribeOrderBean;
import com.shuangduan.zcy.view.material.MaterialActivity;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.vm.MineSubVm;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.NEED_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.ORDER_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.SUBSCRIBE;
import static com.shuangduan.zcy.app.CustomConfig.UNUSED;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 订阅消息推送页（工程信息订阅/订单提醒/闲置物资提醒）
 * @time 2019/8/2 17:40
 * @change
 * @chang time
 * @class describe
 */
public class MineSubActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right_select)
    AppCompatImageView ivBarRightSelect;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private SubscribeAdapter adapter;
    private SubscribeOrderAdapter subscribeOrderAdapter;
    private MineSubVm mineSubVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_sub;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        mineSubVm = ViewModelProviders.of(this).get(MineSubVm.class);

        switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE, 0)) {
            case SUBSCRIBE://订阅消息
                tvBarTitle.setText(getString(R.string.subscribe_message));
                ivBarRightSelect.setVisibility(View.VISIBLE);
                getSubscribe();
                break;
            case UNUSED://闲置提醒
                tvBarTitle.setText(getString(R.string.subscribe_message_group));
                ivBarRightSelect.setVisibility(View.GONE);
                getUnused();
                break;
            case ORDER_TYPE://订单提醒
                tvBarTitle.setText(getString(R.string.subscribe_order));
                ivBarRightSelect.setVisibility(View.GONE);
                getOrder();
                break;
            case NEED_TYPE: //需用匹配
                tvBarTitle.setText(getString(R.string.subscribe_need));
                ivBarRightSelect.setVisibility(View.GONE);
                getNeed();
                break;
        }

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE, 0)) {
                    case SUBSCRIBE://订阅消息
                        mineSubVm.moreSubscribe(1);
                        break;
                    case UNUSED://闲置提醒
                        mineSubVm.moreSubscribe(2);
                        break;
                    case ORDER_TYPE://订单提醒
                        mineSubVm.moreSubscribeOrder();
                        break;
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE, 0)) {
                    case SUBSCRIBE://订阅消息
                        mineSubVm.subscribe(1);
                        break;
                    case UNUSED://闲置提醒
                        mineSubVm.subscribe(2);
                        break;
                    case ORDER_TYPE://订单提醒
                        mineSubVm.subscribeOrder();
                        break;
                }
            }
        });

        mineSubVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    //订阅信息
    private void getSubscribe() {

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubscribeAdapter(R.layout.adapter_subscribe, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((helper, view, position) -> {
            mineSubVm.pos = position;
            SubscribeBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getType_id());
            bundle.putInt(CustomConfig.LOCATION, 0);
            ActivityUtils.startActivityForResult(bundle, this, ProjectDetailActivity.class, 200);
        });

        //获取订阅信息数据
        mineSubVm.subscribeLiveData.observe(this, subscribeBean -> {
            if (subscribeBean.getPage() == 1) {
                adapter.setNewData(subscribeBean.getList());
                adapter.setEmptyView(emptyViewFactory.createEmptyView(R.drawable.icon_empty_subscibe, R.string.empty_project_subscribe_info, 0, null));
            } else {
                adapter.addData(subscribeBean.getList());
            }
            setNoMore(subscribeBean.getPage(), subscribeBean.getCount());
        });

        //推送选择列表数据
        mineSubVm.phasesLiveData.observe(this, myPhasesBean -> new SubscriptionTypeDialog(this)
                .setItems(myPhasesBean)
                .setCallBack(new BaseDialog.CallBack() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok(String s) {
                        if (mineSubVm.phasesLiveData.getValue() == null) {
                            return;
                        }
                        mineSubVm.phasesId.clear();
                        for (MyPhasesBean bean : mineSubVm.phasesLiveData.getValue()) {
                            if (bean.getIs_select() == 1)
                                mineSubVm.phasesId.add(bean.getId());
                        }
                        mineSubVm.setPhases();
                    }
                }).showDialog());

        //推送选择成功返回结果
        mineSubVm.phasesSetLiveData.observe(this, o -> {
            mineSubVm.subscribe(1);
        });

        mineSubVm.subscribe(1);
    }

    //闲置提醒
    private void getUnused() {

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubscribeAdapter(R.layout.adapter_subscribe, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((helper, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("notice", 1);
            ActivityUtils.startActivity(bundle, MaterialActivity.class);
        });

        //获取订阅信息数据
        mineSubVm.subscribeLiveData.observe(this, subscribeBean -> {
            if (subscribeBean.getPage() == 1) {
                adapter.setNewData(subscribeBean.getList());
                adapter.setEmptyView(emptyViewFactory.createEmptyView(R.drawable.icon_empty_subscibe, R.string.empty_material_subscribe_info, 0, null));
            } else {
                adapter.addData(subscribeBean.getList());
            }
            setNoMore(subscribeBean.getPage(), subscribeBean.getCount());
        });

        mineSubVm.subscribe(2);
    }

    //订单提醒
    private void getOrder() {

        rv.setLayoutManager(new LinearLayoutManager(this));
        subscribeOrderAdapter = new SubscribeOrderAdapter(R.layout.adapter_subscribe_order, null);
        subscribeOrderAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(subscribeOrderAdapter);

        subscribeOrderAdapter.setOnItemClickListener((helper, view, position) -> {
            mineSubVm.posOrder = position;
            SubscribeOrderBean.ListBean listBean = subscribeOrderAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.ADMIN_ORDER_ID, listBean.getType_id());
            bundle.putInt("manage_status", SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS, 0));
            bundle.putInt("order_type", listBean.getType());
            ActivityUtils.startActivityForResult(bundle, this, OrderDetailsActivity.class, 300);
        });

        //获取订单提醒列表数据
        mineSubVm.subscribeOrderLiveData.observe(this, subscribeOrderBean -> {
            if (subscribeOrderBean.getPage() == 1) {
                subscribeOrderAdapter.setNewData(subscribeOrderBean.getList());
                subscribeOrderAdapter.setEmptyView(emptyViewFactory.createEmptyView(R.drawable.icon_empty_subscibe, R.string.empty_material_subscribe_order_info, 0, null));
            } else {
                subscribeOrderAdapter.addData(subscribeOrderBean.getList());
            }
            setNoMore(subscribeOrderBean.getPage(), subscribeOrderBean.getCount());
        });

        mineSubVm.subscribeOrder();
    }


    //订单提醒
    private void getNeed() {

        rv.setLayoutManager(new LinearLayoutManager(this));
        SubscribeNeedAdapter needAdapter = new SubscribeNeedAdapter(R.layout.adapter_need_item, null);

        rv.setAdapter(needAdapter);

        needAdapter.setEmptyView(emptyViewFactory.createEmptyView(R.drawable.icon_empty_subscibe, R.string.empty_subscribe_need_info, 0, null));


        needAdapter.setOnItemClickListener((helper, view, position) -> {
//            mineSubVm.posOrder = position;
//            SubscribeOrderBean.ListBean listBean = subscribeOrderAdapter.getData().get(position);
//            Bundle bundle = new Bundle();
//            bundle.putInt(CustomConfig.ADMIN_ORDER_ID, listBean.getType_id());
//            bundle.putInt("manage_status", SPUtils.getInstance().getInt(CustomConfig.MANAGE_STATUS, 0));
//            bundle.putInt("order_type", listBean.getType());
//            ActivityUtils.startActivityForResult(bundle,this, OrderDetailsActivity.class,300);
        });

        //获取订单提醒列表数据
//        mineSubVm.subscribeOrderLiveData.observe(this,subscribeOrderBean -> {
//            if (subscribeOrderBean.getPage() == 1) {
//                subscribeOrderAdapter.setNewData(subscribeOrderBean.getList());
//                subscribeOrderAdapter.setEmptyView(emptyViewFactory.createEmptyView(R.drawable.icon_empty_subscibe, R.string.empty_material_subscribe_order_info, 0, null));
//            }else {
//                subscribeOrderAdapter.addData(subscribeOrderBean.getList());
//            }
//            setNoMore(subscribeOrderBean.getPage(), subscribeOrderBean.getCount());
//        });

//        mineSubVm.subscribeOrder();
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

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right_select, R.id.iv_bar_right})
    void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right_select://工程推送选择
                mineSubVm.myPhases();
                break;
            case R.id.iv_bar_right://设置
                bundle.putInt(CustomConfig.NEWS_TYPE, getIntent().getIntExtra(CustomConfig.NEWS_TYPE, 0));
                ActivityUtils.startActivity(bundle, NoticeSetActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200) {
            SubscribeBean.ListBean listBean = adapter.getData().get(mineSubVm.pos);
            if (listBean.getType() == 1) {
                listBean.setIs_view(1);
                adapter.notifyItemChanged(mineSubVm.pos, listBean);
            }
        }

        if (requestCode == 300) {
            SubscribeOrderBean.ListBean listBean = subscribeOrderAdapter.getData().get(mineSubVm.posOrder);
            listBean.setIs_view(1);
            subscribeOrderAdapter.notifyItemChanged(mineSubVm.posOrder, listBean);
        }
    }
}
