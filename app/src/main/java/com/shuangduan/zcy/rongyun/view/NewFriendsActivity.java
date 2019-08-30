package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.NewFriendAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * @author 宁文强 QQ:858777523
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

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_new_friends;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.friends_new_notice));
        IMAddVm imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        NewFriendAdapter newFriendAdapter = new NewFriendAdapter(R.layout.item_friends_new, null);
        newFriendAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(newFriendAdapter);
        newFriendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            IMFriendApplyListBean.ListBean listBean = newFriendAdapter.getData().get(position);
            switch (view.getId()){
                case R.id.tv_accept:
                    imAddVm.operateNewFriend(listBean.getId(), 2);
                    break;
                case R.id.tv_refuse:
                    imAddVm.operateNewFriend(listBean.getId(), 3);
                    break;
            }
        });
        newFriendAdapter.setOnItemClickListener((adapter, view, position) -> {
            //进入聊天界面
            IMFriendApplyListBean.ListBean listBean = newFriendAdapter.getData().get(position);
            chatDemo(listBean.getApply_user_id());
        });

        imAddVm.applyListLiveData.observe(this, imFriendApplyListBean -> {
            if (imFriendApplyListBean.getPage() == 1) {
                newFriendAdapter.setNewData(imFriendApplyListBean.getList());
                newFriendAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                newFriendAdapter.addData(imFriendApplyListBean.getList());
            }
            setNoMore(imFriendApplyListBean.getPage(), imFriendApplyListBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                imAddVm.moreNewFriendList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                imAddVm.newFriendList();
            }
        });

        imAddVm.newFriendList();
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

    private void chatDemo(int id){
        //模拟会话，用于后台消息同步调试
        // 构造 TextMessage 实例
        TextMessage myTextMessage = TextMessage.obtain("李彤彤，在吗");

        /* 生成 Message 对象。
         */
        Message myMessage = Message.obtain(String.valueOf(id), Conversation.ConversationType.PRIVATE, myTextMessage);

/**
 * 发送消息。
 */
        RongIMClient.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
                LogUtils.i(message.getContent());
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }
}
