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
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMGroupListAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMGroupListBean;
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
 * @class 搜索群聊更多列表
 * @time 2019/9/9 17:37
 * @change
 * @chang time
 * @class describe
 */
@SuppressLint("Registered")
public class IMGroupMoreActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rvGroup;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    IMGroupListAdapter imGroupListAdapter;
    List<IMGroupListBean.DataBean.ListBean> listGroup=new ArrayList<>();
    IMGroupListBean imGroupListBean;
    String name;
    int pageSize=10;
    int count;

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
        tvBarTitle.setText(getString(R.string.search_group));
        tvTitle.setText(getString(R.string.im_search_group));
        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        rvGroup.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        imGroupListAdapter = new IMGroupListAdapter(R.layout.item_im_group_list, listGroup);
        imGroupListAdapter.setEmptyView(R.layout.layout_loading, rvGroup);
        rvGroup.setAdapter(imGroupListAdapter);

        name=getIntent().getStringExtra("name");

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageSize<count){
                    pageSize+=10;
                    if (name!=null){
                        getGroupList("1",pageSize);
                    }else {
                        getGroupList("",pageSize);
                    }
                    refreshLayout.finishLoadMore(2000);
                }else {
                    refresh.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (name!=null){
                    getGroupList("1",pageSize);
                }else {
                    getGroupList("",pageSize);
                }
                refresh.finishRefresh(2000);
            }
        });
        imGroupListAdapter.setOnItemClickListener((adapter, view, position) -> RongIM.getInstance().startGroupChat(IMGroupMoreActivity.this
                , imGroupListBean.getData().getList().get(position).getGroup_id()
                , imGroupListBean.getData().getList().get(position).getGroup_name()));
        if (name!=null){
            getGroupList("1",pageSize);
        }else {
            getGroupList("",pageSize);
        }
    }

    //我的群组列表
    private void getGroupList(String size,int pageSize) {

        if(size.equals("1")){
            OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.SEARCH_GROUP)
                    .tag(this)
                    .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                    .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                    .params("name",name)
                    .params("pageSize",pageSize)
                    .execute(new MyStringCallback());
        }else {
            OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_MY_GROUP)
                    .tag(this)
                    .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                    .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                    .params("pageSize",pageSize)
                    .execute(new MyStringCallback());
        }
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            LogUtils.json(response.body());
        }

        @Override
        public void onSuccess(Response<String> response) {
            LogUtils.json(response.body());
            try {
                imGroupListBean=new Gson().fromJson(response.body(), IMGroupListBean.class);
                if (imGroupListBean.getCode().equals("200")){
                    listGroup.clear();
                    listGroup.addAll(imGroupListBean.getData().getList());
                    count=imGroupListBean.getData().getCount();
                    if(listGroup!=null&&listGroup.size()!=0){
                        imGroupListAdapter.notifyDataSetChanged();
                    }else {
                        imGroupListAdapter.setEmptyView(R.layout.layout_empty, rvGroup);
                    }
                }else if (imGroupListBean.getCode().equals("-1")){
                    ToastUtils.showShort(imGroupListBean.getMsg());
                    LoginUtils.getExitLogin(IMGroupMoreActivity.this);
                }else {
                    imGroupListAdapter.setEmptyView(R.layout.layout_empty, rvGroup);
                    listGroup.clear();
                }
            }catch (JsonSyntaxException | IllegalStateException ignored){
                ToastUtils.showShort(getString(R.string.request_error));
            }
        }
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){ finish(); }
}
