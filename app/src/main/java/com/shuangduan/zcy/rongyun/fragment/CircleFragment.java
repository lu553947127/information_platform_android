package com.shuangduan.zcy.rongyun.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.bean.SupplierRoleBean;
import com.shuangduan.zcy.model.event.AvatarEvent;
import com.shuangduan.zcy.rongyun.adapter.ConversationListAdapterEx;
import com.shuangduan.zcy.rongyun.view.IMContactsActivity;
import com.shuangduan.zcy.rongyun.view.IMGroupDetailsActivity;
import com.shuangduan.zcy.rongyun.view.MineSubActivity;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.user.UserInfoActivity;
import com.shuangduan.zcy.vm.HomeVm;
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
    @BindView(R.id.iv_header)
    CircleImageView iv_header;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.iv_sgs)
    AppCompatImageView ivSgs;
    @BindView(R.id.rl_subscribe_children)
    RelativeLayout rlSubscribeChildren;
    @BindView(R.id.tv_subscribe_children_numbers)
    TextView tvSubscribeChildrenNumbers;
    @BindView(R.id.tv_contacts_numbers)
    TextView tvContactsNumbers;
    @BindView(R.id.rl_idle_reminder)
    RelativeLayout rlIdleReminder;
    @BindView(R.id.tv_subscribe_group_number)
    TextView tvSubscribeGroupNumber;
    @BindView(R.id.rl_unused)
    RelativeLayout rlUnused;
    @BindView(R.id.tv_unused_number)
    TextView tvUnusedNumber;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.rl_need)
    RelativeLayout rlNeed;
    @BindView(R.id.tv_need_number)
    TextView tvNeedNumber;

    private UserInfoVm userInfoVm;
    private IMAddVm imAddVm;
    private HomeVm homeVm;
    private NoScrollViewPager viewPager;
    BottomNavigationView navigation;
    private int manage_status,order_turnover,order_device;

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

        viewPager= Objects.requireNonNull(getActivity()).findViewById(R.id.view_pager);
        navigation= Objects.requireNonNull(getActivity()).findViewById(R.id.navigation);

        FragmentManager fragmentManage = getChildFragmentManager();
        ConversationListFragment conversationListFragment = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        ConversationListAdapterEx adapterEx = new ConversationListAdapterEx(RongContext.getInstance());
        Objects.requireNonNull(conversationListFragment).setAdapter(adapterEx);
        Uri uri = Uri.parse("rong://" + MyApplication.getInstance().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                .build();
        conversationListFragment.setUri(uri);

        //获取后台管理权限
        homeVm = ViewModelProviders.of(mActivity).get(HomeVm.class);
        homeVm.supplierRoleLiveData.observe(this, supplierRoleBean -> {
            //获取订单权限
            for (SupplierRoleBean.RoleBean bean : supplierRoleBean.getRole()) {
                switch (bean.getMenu()){
                    case CustomConfig.EQIPMENT_ORDER_LIST://设备订单列表
                        order_device = bean.getStatus();
                        break;
                    case CustomConfig.CONSTRUCTION_ORDER_LIST://周转材料订单列表
                        order_turnover = bean.getStatus();
                        break;
                }
            }
            //获取用户身份 0普通用户 1普通供应商 2子公司 3集团 4子账号
            manage_status=supplierRoleBean.getManage_status();
            SPUtils.getInstance().put(CustomConfig.MANAGE_STATUS, supplierRoleBean.getManage_status());
            switch (manage_status){
                case 0://普通用户
                    rlSubscribeChildren.setVisibility(View.VISIBLE);
                    rlIdleReminder.setVisibility(View.GONE);
                    break;
                case 1://普通供应商
                    rlSubscribeChildren.setVisibility(View.GONE);
                    rlIdleReminder.setVisibility(View.VISIBLE);
                    rlOrder.setVisibility(View.VISIBLE);
                    rlUnused.setVisibility(View.GONE);
                    rlNeed.setVisibility(View.VISIBLE);
                    break;
                case 2://子公司
                    rlSubscribeChildren.setVisibility(View.GONE);
                    rlIdleReminder.setVisibility(View.VISIBLE);
                    rlOrder.setVisibility(View.VISIBLE);
                    rlUnused.setVisibility(View.VISIBLE);
                    rlNeed.setVisibility(View.VISIBLE);
                    break;
                case 4://子公司子账号
                    rlSubscribeChildren.setVisibility(View.GONE);
                    rlIdleReminder.setVisibility(View.VISIBLE);
                    rlUnused.setVisibility(View.VISIBLE);
                    rlNeed.setVisibility(View.VISIBLE);
                    if (order_device == 1 || order_turnover == 1){
                        rlOrder.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3://集团
                case 5://集团子账号
                    rlSubscribeChildren.setVisibility(View.GONE);
                    rlIdleReminder.setVisibility(View.VISIBLE);
                    rlUnused.setVisibility(View.VISIBLE);
                    break;
            }
        });

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
        //订阅消息/订单提醒/闲置提醒/新朋友添加 未读消息数量返回数据
        imAddVm.applyCountData.observe(this,friendApplyCountBean -> {
            getCountNumbers(friendApplyCountBean.getCount(),tvContactsNumbers);
            getCountNumbers(friendApplyCountBean.getSubscribe(),tvSubscribeChildrenNumbers);
            getCountNumbers(friendApplyCountBean.getSubscribe(),tvSubscribeGroupNumber);
            getCountNumbers(friendApplyCountBean.getMaterial(),tvUnusedNumber);
            getCountNumbers(friendApplyCountBean.getOrder(),tvOrderNumber);
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
                imAddVm.applyCount();
                homeVm.getSupplierRole();
                refreshLayout.finishRefresh(1000);
            }
        });

        //根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
        RongIM.setUserInfoProvider(s -> imAddVm.userInfo(s),true);
        //设置群聊列表数据
        RongIM.setGroupInfoProvider(s -> imAddVm.groupInfo(s),true);

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
                        ActivityUtils.startActivity(bundle, IMGroupDetailsActivity.class);
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

    //设备角标数量
    private void getCountNumbers(int count,TextView textView) {
        if (count == 0) {
            textView.setVisibility(View.INVISIBLE);
        } else if (count > 0 && count < 100) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(count));
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.im_no_read_message);
        }
    }

    @Override
    protected void initDataFromService() {

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

    @OnClick({R.id.iv_header,R.id.rl_subscribe_children,R.id.rl_message,R.id.rl_subscribe_group,R.id.rl_unused,R.id.rl_order,R.id.rl_need})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.iv_header://左侧头像
                viewPager.setCurrentItem(4);
                navigation.getMenu().getItem(4).setChecked(true);
                break;
            case R.id.rl_subscribe_children://普通用户订阅信息
            case R.id.rl_subscribe_group://集团用户订阅信息
                bundle.putInt(CustomConfig.NEWS_TYPE,CustomConfig.SUBSCRIBE);
                ActivityUtils.startActivity(bundle, MineSubActivity.class);
                break;
            case R.id.rl_message://通讯录
                ActivityUtils.startActivity(IMContactsActivity.class);
                break;
            case R.id.rl_unused://闲置提醒
                bundle.putInt(CustomConfig.NEWS_TYPE,CustomConfig.UNUSED);
                ActivityUtils.startActivity(bundle,MineSubActivity.class);
                break;
            case R.id.rl_order://订单提醒
                bundle.putInt(CustomConfig.NEWS_TYPE,CustomConfig.ORDER_TYPE);
                ActivityUtils.startActivity(bundle,MineSubActivity.class);
                break;
            case R.id.rl_need://需用匹配
                bundle.putInt(CustomConfig.NEWS_TYPE,CustomConfig.NEED_TYPE);
                ActivityUtils.startActivity(bundle,MineSubActivity.class);
                break;
                default:
                    break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfoVm.userInfo();
        homeVm.getSupplierRole();
        imAddVm.applyCount();
    }
}
