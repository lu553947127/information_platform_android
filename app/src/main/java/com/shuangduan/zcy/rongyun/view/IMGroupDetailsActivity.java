package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMGroupInfoAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.IMGroupInfoBean;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.SwitchView;

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

    private IMAddVm imAddVm;
    private String group_id;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_group_details;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.im_group_details));
        group_id=getIntent().getStringExtra("group_id");
        switchView.setOnStateChangedListener(this);

        imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);

        recyclerView.setLayoutManager(new GridLayoutManager(IMGroupDetailsActivity.this, 5));
        IMGroupInfoAdapter imGroupInfoAdapter = new IMGroupInfoAdapter(R.layout.adapter_im_group_info, null);
        imGroupInfoAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(imGroupInfoAdapter);

        imGroupInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMGroupInfoBean.ListBean listBean = imGroupInfoAdapter.getData().get(position);
            if (listBean.getUsername().equals("更多")){
                Bundle bundle = new Bundle();
                bundle.putString("group_id", group_id);
                ActivityUtils.startActivity(bundle, IMGroupMembersActivity.class);
            }else {
                if (listBean.getId()!=SPUtils.getInstance().getInt(SpConfig.USER_ID)){
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.UID, listBean.getId());
                    ActivityUtils.startActivity(bundle, UserInfoActivity.class);
                }
            }
        });

        //获取群聊详情返回结果
        imAddVm.imGroupInfoLiveData.observe(this,imGroupInfoBean -> {
            tvName.setText(imGroupInfoBean.getInfo().getGroup_name());
            tvAddress.setText(imGroupInfoBean.getInfo().getProvince()+imGroupInfoBean.getInfo().getCity());
            if (imGroupInfoBean.getList().size()!=0){
                //添加最后一个更多按钮
                imGroupInfoBean.getList().add(new IMGroupInfoBean.ListBean("更多"));
                imGroupInfoAdapter.setNewData(imGroupInfoBean.getList());
            }else {
                imGroupInfoAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
            }
        });

        //退出群聊返回结果
        imAddVm.quitGroupLiveData.observe(this, item ->{
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
        });

        //获取会话提醒状态
        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP, group_id, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {

                if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB) {
                    switchView.setOpened(true);
                } else if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.NOTIFY){
                    switchView.setOpened(false);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.i(errorCode.getValue()+errorCode.getMessage());
            }
        });

        imAddVm.groupList(group_id,9);
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
                imAddVm.quitGroup(group_id);
                break;
        }
    }
}
