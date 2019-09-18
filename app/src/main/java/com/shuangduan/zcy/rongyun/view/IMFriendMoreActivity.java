package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMFriendListAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

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
public class IMFriendMoreActivity extends BaseActivity {

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
    IMFriendListAdapter imFriendListAdapter;
    List<IMFriendListBean.DataBean.ListBean> listFriend=new ArrayList<>();
    IMFriendListBean imFriendListBean;
    String name;
    int page=1;

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
        tvBarTitle.setText(getString(R.string.search_friends));
        tvTitle.setText(getString(R.string.im_search_friend));
        rvFriend.setLayoutManager(new LinearLayoutManager(this));
        rvFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        imFriendListAdapter = new IMFriendListAdapter(R.layout.item_im_friend_list, listFriend);
        imFriendListAdapter.setEmptyView(R.layout.layout_loading, rvFriend);
        rvFriend.setAdapter(imFriendListAdapter);

        name=getIntent().getStringExtra("name");
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getFriendList(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                getFriendList(page);
            }
        });
        imFriendListAdapter.setOnItemClickListener((adapter, view, position) -> {
            RongIM.getInstance().startPrivateChat(IMFriendMoreActivity.this, imFriendListBean.getData().getList().get(position).getUserId()
                    , imFriendListBean.getData().getList().get(position).getName());
        });
        getFriendList(page);
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

    //我的好友列表
    private void getFriendList(int page) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.SEARCH_FRIEND)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("name",name)
                .params("page",page)
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            imFriendListBean=new Gson().fromJson(response.body(), IMFriendListBean.class);
                            if (imFriendListBean.getCode().equals("200")){
                                listFriend.clear();
                                listFriend.addAll(imFriendListBean.getData().getList());
                                setNoMore(imFriendListBean.getData().getPage(),imFriendListBean.getData().getCount());
                                if(listFriend!=null&&listFriend.size()!=0){
                                    imFriendListAdapter.notifyDataSetChanged();
                                }else {
                                    imFriendListAdapter.setEmptyView(R.layout.layout_empty, rvFriend);
                                }
                            }else if (imFriendListBean.getCode().equals("-1")){
                                ToastUtils.showShort(imFriendListBean.getMsg());
                                LoginUtils.getExitLogin(IMFriendMoreActivity.this);
                            }else {
                                imFriendListAdapter.setEmptyView(R.layout.layout_empty, rvFriend);
                                listFriend.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){ finish(); }
}
