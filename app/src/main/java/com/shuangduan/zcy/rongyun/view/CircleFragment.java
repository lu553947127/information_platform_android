package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.event.AvatarEvent;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.weight.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class 工程圈
 * @time 2019/8/27 15:11
 * @change
 * @chang time
 * @class describe
 */
public class CircleFragment extends BaseFragment {

    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.iv_header)
    CircleImageView iv_header;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.iv_sgs)
    AppCompatImageView ivSgs;
    private TextView tvNumber;
    private UserInfoVm userInfoVm;
    private NoScrollViewPager viewPager;
    private RelativeLayout relativeLayout;
    private TextView number;
    private IMAddVm imAddVm;
    private IUnReadMessageObserver observer;

    public static CircleFragment newInstance() {
        Bundle args = new Bundle();
        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_circle;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @SuppressLint({"CutPasteId", "SetTextI18n"})
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState,View view) {
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
        FragmentManager fragmentManage = getChildFragmentManager();
        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        ConversationListAdapterEx adapterEx = new ConversationListAdapterEx(RongContext.getInstance());
        Objects.requireNonNull(fragement).setAdapter(adapterEx);
        Uri uri = Uri.parse("rong://" + MyApplication.getInstance().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                .build();
        fragement.setUri(uri);

        userInfoVm = ViewModelProviders.of(mActivity).get(UserInfoVm.class);
        userInfoVm.getInfoLiveData.observe(this, userInfoBean -> {
            SPUtils.getInstance().put(SpConfig.AVATAR, userInfoBean.getImage_thumbnail());
            ImageLoader.load(mContext, new ImageConfig.Builder()
                    .url(userInfoBean.getImage_thumbnail())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(iv_header)
                    .build());
        });

        imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);
        //未读消息数量返回数据
        imAddVm.applyCountData.observe(this,friendApplyCountBean -> {
            if (friendApplyCountBean.getCount() == 0) {
                tvNumber.setVisibility(View.INVISIBLE);
            } else if (friendApplyCountBean.getCount() > 0 && friendApplyCountBean.getCount() < 100) {
                tvNumber.setVisibility(View.VISIBLE);
                tvNumber.setText(String.valueOf(friendApplyCountBean.getCount()));
            } else {
                tvNumber.setVisibility(View.VISIBLE);
                tvNumber.setText(R.string.im_no_read_message);
            }
            //设置底部标签数量
            int counts=imAddVm.count+friendApplyCountBean.getCount();
            if (counts < 1) {
                relativeLayout.setVisibility(View.GONE);
            } else if (counts < 100) {
                number.setTextSize(11);
                relativeLayout.setVisibility(View.VISIBLE);
                number.setText(" " + counts + " ");
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
                number.setTextSize(9);
                number.setText("99+");
            }
        });

        ///会话列表人员头像名称显示
        imAddVm.imUserInfoLiveData.observe(this,imWechatUserInfoBean -> {
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(imWechatUserInfoBean.getUserId(),imWechatUserInfoBean.getName(),Uri.parse(imWechatUserInfoBean.getPortraitUri())));
        });

        //会话列表群组头像名称显示
        imAddVm.imWechaGroupInfoLiveData.observe(this,imWechatGroupInfoBean -> {
            Group groupInfo = new Group(imWechatGroupInfoBean.getGroupId(), imWechatGroupInfoBean.getGroupName(), Uri.parse(imWechatGroupInfoBean.getGroupName()));
            RongIM.getInstance().refreshGroupInfoCache(groupInfo);
        });

        //判断是否认证
        if (SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED)==2){
            ivSgs.setVisibility(View.VISIBLE);
        }else {
            ivSgs.setVisibility(View.INVISIBLE);
        }

        refresh.setEnableLoadMore(false);
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnRefreshLoadMoreListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                RongIM.getInstance().addUnReadMessageCountChangedObserver(i -> {
                    LogUtils.i(i);
                    // i 是未读数量
                    imAddVm.count=i;
                    imAddVm.applyCount();
                }, Conversation.ConversationType.PRIVATE,Conversation.ConversationType.GROUP,Conversation.ConversationType.SYSTEM);
                refreshLayout.finishRefresh(1000);
            }
        });
        getBadgeViewInitView(view);

        //根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
        RongIM.setUserInfoProvider(s -> imAddVm.userInfo(s),true);
        //设置群聊列表数据
        RongIM.setGroupInfoProvider(s -> imAddVm.groupInfo(s),true);

        //获取未读消息数量
        observer = i -> {
            LogUtils.i(i);
            // i 是未读数量
            imAddVm.count = i;
            imAddVm.applyCount();
        };
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE,Conversation.ConversationType.GROUP,Conversation.ConversationType.SYSTEM);

        //设置会话列表头像点击监听
        adapterEx.setOnPortraitItemClick(new ConversationListAdapter.OnPortraitItemClick() {
            @Override
            public void onPortraitItemClick(View view, UIConversation uiConversation) {
                String targetId=uiConversation.getConversationTargetId();
                Conversation.ConversationType conversationType=uiConversation.getConversationType();
                if (targetId != null) {
                    if (conversationType == Conversation.ConversationType.PRIVATE){
                        if (Integer.parseInt(targetId)!= SPUtils.getInstance().getInt(SpConfig.USER_ID)) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(CustomConfig.UID, Integer.parseInt(targetId));
                            ActivityUtils.startActivity(bundle, UserInfoActivity.class);
                        }
                    }else if (conversationType == Conversation.ConversationType.GROUP){
                        Bundle bundle = new Bundle();
                        bundle.putString("group_id", targetId);
                        ActivityUtils.startActivity(bundle,IMGroupDetailsActivity.class);
                    }
                }
            }

            @Override
            public boolean onPortraitItemLongClick(View view, UIConversation uiConversation) {
                return false;
            }
        });

        //设置官方消息默认置顶
        RongIM.getInstance().setConversationToTop(Conversation.ConversationType.SYSTEM, "18", true, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                LogUtils.i("置顶成功");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.i("置顶失败");
            }
        });
    }

    //设置底部消息提醒数字布局
    private void getBadgeViewInitView(View view) {
        //因为黄油刀在碎片重绘时初始化空指针异常，则用view视图来显示
        tvNumber=view.findViewById(R.id.tv_numbers);
        viewPager= Objects.requireNonNull(getActivity()).findViewById(R.id.view_pager);
        //底部标题栏右上角标设置
        //获取整个的NavigationView
        BottomNavigationView navigation =getActivity().findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，设置在标题栏的位置
        View tab = menuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(getContext()).inflate(R.layout.layout_apply_count, menuView, false);
        //添加到Tab上
        itemView.addView(badge);
        //显示角标数字
        relativeLayout = badge.findViewById(R.id.rl);
        //显示/隐藏整个视图
        number=badge.findViewById(R.id.number);
    }

    @Override
    protected void initDataFromService() {
        userInfoVm.userInfo();
    }

    @Subscribe
    public void onEventUpdateAvatar(AvatarEvent event){
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(event.getAvatar())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(iv_header)
                .build());
    }

    @OnClick({R.id.tv_bar_title, R.id.rl_message,R.id.iv_header})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_bar_title:
                ActivityUtils.startActivity(IMSearchActivity.class);
                break;
            case R.id.rl_message:
                ActivityUtils.startActivity(IMContactsActivity.class);
                break;
            case R.id.iv_header:
                viewPager.setCurrentItem(3);
                break;
                default:
                    break;
        }
    }

    @Override
    public void onDestroy() {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
        super.onDestroy();
    }
}
