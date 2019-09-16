package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMGroupInfoAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendOperationBean;
import com.shuangduan.zcy.model.bean.IMGroupInfoBean;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.weight.SwitchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class 群聊详情
 * @time 2019/9/11 10:12
 * @change
 * @chang time
 * @class describe
 */
@SuppressLint("InflateParams")
public class IMGroupDetailsActivity extends BaseActivity implements SwitchView.OnStateChangedListener {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_address)
    AppCompatTextView tvAddress;
    @BindView(R.id.sv)
    SwitchView switchView;
    @BindView(R.id.tv_add_friend)
    TextView tvExitGroup;
    String group_id;
    IMGroupInfoBean imGroupInfoBean;
    IMGroupInfoAdapter imGroupInfoAdapter;
    List<IMGroupInfoBean.DataBean.ListBean> list=new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_group_details;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.im_group_details));
        group_id=getIntent().getStringExtra("group_id");

        switchView.setOnStateChangedListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(IMGroupDetailsActivity.this, 5));
        imGroupInfoAdapter = new IMGroupInfoAdapter(R.layout.adapter_im_group_info, list);
        imGroupInfoAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(imGroupInfoAdapter);

        imGroupInfoAdapter.setOnItemClickListener((adapter, view, position) -> {

            if (position==list.size()-1){
                Bundle bundle = new Bundle();
                bundle.putString("group_id", group_id);
                ActivityUtils.startActivity(bundle, IMGroupMembersActivity.class);
            }else {
                if (imGroupInfoBean.getData().getList().get(position).getId()!=SPUtils.getInstance().getInt(SpConfig.USER_ID)){
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.UID, imGroupInfoBean.getData().getList().get(position).getId());
                    ActivityUtils.startActivity(bundle, UserInfoActivity.class);
                }
            }
        });
        //获取会话提醒状态
        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.DISCUSSION, group_id, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {

                if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB) {
                    switchView.setOpened(true);
                } else {
                    switchView.setOpened(false);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        getGroupInfo();
    }

    //设置消息免打扰
    public static void setConverstionNotif( Conversation.ConversationType conversationType, String targetId, boolean state) {
        Conversation.ConversationNotificationStatus cns;
        if (state) {
            cns = Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;
        } else {
            cns = Conversation.ConversationNotificationStatus.NOTIFY;
        }
        RongIM.getInstance().setConversationNotificationStatus(conversationType, targetId, cns, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB) {
                    LogUtils.i("设置免打扰成功");
                } else if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.NOTIFY) {
                    LogUtils.i("取消免打扰成功");
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.i("设置失败");
            }
        });
    }

    //群聊详情
    private void getGroupInfo() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_GROUP)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("group_id",group_id)
                .params("page","1")
                .params("pageSize","9")
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.i(response.body());
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.i(response.body());
                        try {
                            imGroupInfoBean=new Gson().fromJson(response.body(), IMGroupInfoBean.class);
                            if (imGroupInfoBean.getCode().equals("200")){
                                tvName.setText(imGroupInfoBean.getData().getInfo().getGroup_name());
                                tvAddress.setText(imGroupInfoBean.getData().getInfo().getProvince()+imGroupInfoBean.getData().getInfo().getCity());
                                list.clear();
                                list.addAll(imGroupInfoBean.getData().getList());
                                //添加最后一个更多按钮
                                list.add(new IMGroupInfoBean.DataBean.ListBean("更多"));
                                if (list!=null&&list.size()!=0){
                                    imGroupInfoAdapter.notifyDataSetChanged();
                                }else {
                                    imGroupInfoAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
                                }
                            }else {
                                imGroupInfoAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
                                list.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    //退群
    private void getExitGroup() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_QUIT_GROUP)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("group_id",group_id)//接受/拒绝用户编号
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.i(response);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.i(response);
                        Log.e("TAG","请求成功"+response.body());
                        try {
                            IMFriendOperationBean bean=new Gson().fromJson(response.body(), IMFriendOperationBean.class);
                            if (bean.getCode().equals("200")){
                                RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, group_id, new RongIMClient.ResultCallback<Boolean>() {
                                    @Override
                                    public void onSuccess(Boolean aBoolean) {
                                        Intent intent = new Intent(IMGroupDetailsActivity.this, MainActivity.class);
                                        intent.putExtra("statueCar", "1");
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode e) {

                                    }
                                });
                            }else {
                                ToastUtils.showShort(getString(R.string.request_error));
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @Override
    public void toggleToOn(SwitchView view) {
        switch (view.getId()){
            case R.id.sv:
                switchView.setOpened(true);
                setConverstionNotif(Conversation.ConversationType.GROUP,group_id,true);
                break;
        }
    }

    @Override
    public void toggleToOff(SwitchView view) {
        switch (view.getId()){
            case R.id.sv:
                switchView.setOpened(false);
                setConverstionNotif(Conversation.ConversationType.GROUP,group_id,false);
                break;
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_add_friend})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_add_friend:
                getExitGroup();
                break;
        }
    }
}
