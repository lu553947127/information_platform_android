package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMFriendListAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoListActivity;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class 搜索好友更多列表
 * @time 2019/9/9 17:37
 * @change
 * @chang time
 * @class describe
 */
@SuppressLint("Registered")
public class IMFriendMoreActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rvFriend;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private View emptyView;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_friend_more;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_circle_contacts, R.string.empty_new_friends_info, R.string.to_look_over, this);
        tvBarTitle.setText(getString(R.string.search_friends));
        tvTitle.setText(getString(R.string.im_search_friend));

        String name = getIntent().getStringExtra("name");

        IMAddVm imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);

        rvFriend.setLayoutManager(new LinearLayoutManager(this));
        rvFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IMFriendListAdapter imFriendListAdapter = new IMFriendListAdapter(R.layout.item_im_friend_list, null);
        imFriendListAdapter.setEmptyView(R.layout.layout_loading, rvFriend);
        rvFriend.setAdapter(imFriendListAdapter);

        imFriendListAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMFriendListBean.ListBean listBean = imFriendListAdapter.getData().get(position);
            if (listBean.getUserId().equals("18")){
                RongIM.getInstance().startConversation(IMFriendMoreActivity.this, Conversation.ConversationType.SYSTEM, listBean.getUserId(), listBean.getName());
            }else {
                RongIM.getInstance().startPrivateChat(IMFriendMoreActivity.this, listBean.getUserId(), listBean.getName());
            }
        });
        imAddVm.imFriendListLiveData.observe(this,imFriendListBean ->{
            if (imFriendListBean.getPage() == 1) {
                imFriendListAdapter.setNewData(imFriendListBean.getList());
                imFriendListAdapter.setEmptyView(emptyView);
            } else {
                imFriendListAdapter.addData(imFriendListAdapter.getData().size(), imFriendListBean.getList());
            }
            setNoMore(imFriendListBean.getPage(), imFriendListBean.getCount());
        });

        imAddVm.friendListLiveData.observe(this,imFriendListBean ->{
            if (imFriendListBean.getPage() == 1) {
                imFriendListAdapter.setNewData(imFriendListBean.getList());
                imFriendListAdapter.setEmptyView(emptyView);
            } else {
                imFriendListAdapter.addData(imFriendListAdapter.getData().size(), imFriendListBean.getList());
            }
            setNoMore(imFriendListBean.getPage(), imFriendListBean.getCount());
        });

        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getFriendList(imAddVm,name);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                getFriendListMore(imAddVm,name);
            }
        });

        imAddVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        getFriendList(imAddVm,name);
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

    //我的好友列表
    private void getFriendList(IMAddVm imAddVm,String name) {
        if (name != null) {
            imAddVm.searchFriend(name);
        } else {
            imAddVm.friendList();
        }
    }

    //我的好友列表更多
    private void getFriendListMore(IMAddVm imAddVm,String name) {
        if (name != null) {
            imAddVm.searchFriendMore(name);
        } else {
            imAddVm.friendListMore();
        }
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(ProjectInfoListActivity.class);
    }

    @OnClick({R.id.iv_bar_back})
    void onClick() {
        finish();
    }
}
