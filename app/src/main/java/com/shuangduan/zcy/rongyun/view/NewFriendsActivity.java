package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

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
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.NewFriendAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.model.bean.IMFriendOperationBean;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.SharedUtils;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  新的好友列表
 * @time 2019/8/29 17:25
 * @change
 * @chang time
 * @class describe
 */
public class NewFriendsActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    NewFriendAdapter newFriendAdapter;
    IMFriendApplyListBean imFriendApplyListBean;
    List<IMFriendApplyListBean.DataBean.ListBean> list=new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_new_friends;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.friends_new_notice));


        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        newFriendAdapter = new NewFriendAdapter(R.layout.item_friends_new, list);
        newFriendAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(newFriendAdapter);
        newFriendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.tv_accept://接受
                    getFriendOperation(imFriendApplyListBean.getData().getList().get(position).getId(),"2","");
                    break;
                case R.id.tv_refuse://拒绝
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.FRIEND_DATA, 3);
                    bundle.putString("id",imFriendApplyListBean.getData().getList().get(position).getId());
                    bundle.putString("name",imFriendApplyListBean.getData().getList().get(position).getUsername());
                    bundle.putString("msg",imFriendApplyListBean.getData().getList().get(position).getApply_user_msg());
                    bundle.putString("image",imFriendApplyListBean.getData().getList().get(position).getImage());
                    ActivityUtils.startActivity(bundle,IMAddFriendActivity.class);
                    break;
            }
        });

        refresh.setEnableLoadMore(false);
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNewFriendList();
            }
        });
    }

    //我的新好友列表
    private void getNewFriendList() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.FRIEND_APPLY_LIST)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
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
                            imFriendApplyListBean=new Gson().fromJson(response.body(), IMFriendApplyListBean.class);
                            if (imFriendApplyListBean.getCode().equals("200")){
                                list.clear();
                                list.addAll(imFriendApplyListBean.getData().getList());
                                setNoMore(imFriendApplyListBean.getData().getPage(),imFriendApplyListBean.getData().getCount());
                                if(list!=null&&list.size()!=0){
                                    newFriendAdapter.notifyDataSetChanged();
                                }else {
                                    newFriendAdapter.setEmptyView(R.layout.layout_empty, rv);
                                }
                            }else if (imFriendApplyListBean.getCode().equals("-1")){
                                ToastUtils.showShort(imFriendApplyListBean.getMsg());
                                LoginUtils.getExitLogin();
                            }else {
                                newFriendAdapter.setEmptyView(R.layout.layout_empty, rv);
                                list.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    //好友添加/拒绝验证
    private void getFriendOperation(String user_id,String status,String msg) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.FRIEND_OPERATION)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("id",user_id)//接受/拒绝用户编号
                .params("status",status)//接受/拒绝
                .params("msg",msg)//接受/拒绝原因
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
                            IMFriendOperationBean bean=new Gson().fromJson(response.body(), IMFriendOperationBean.class);
                            if (bean.getCode().equals("200")){
                                getNewFriendList();
                            }else if (bean.getCode().equals("-1")){
                                ToastUtils.showShort(bean.getMsg());
                                LoginUtils.getExitLogin();
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
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


    @Override
    protected void onResume() {
        super.onResume();
        getNewFriendList();
    }
}
