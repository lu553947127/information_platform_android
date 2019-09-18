package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendApplyCountBean;
import com.shuangduan.zcy.model.bean.IMWechatGroupInfoBean;
import com.shuangduan.zcy.model.bean.IMWechatUserInfoBean;
import com.shuangduan.zcy.model.event.AvatarEvent;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.login.WelcomeActivity;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.weight.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  工程圈
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
//    @BindView(R.id.tv_numbers)
    TextView tvNumber;
    private UserInfoVm userInfoVm;
    private NoScrollViewPager viewPager;
    private RelativeLayout relativeLayout;
    private TextView number;
    private int count=0;

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

    @SuppressLint("CutPasteId")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState,View view) {

        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
        FragmentManager fragmentManage = getChildFragmentManager();
        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + MyApplication.getInstance().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
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
        userInfoVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
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
                RongIM.getInstance().addUnReadMessageCountChangedObserver(i -> {
                    LogUtils.i(i);
                    // i 是未读数量
                    getFriendApplyCount(i);
                }, Conversation.ConversationType.PRIVATE,Conversation.ConversationType.GROUP);
                refreshLayout.finishRefresh(2000);
            }
        });
        getBadgeViewInitView(view);

        //根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
        RongIM.setUserInfoProvider(this::getFriendData, true);
        //设置群聊列表数据
        RongIM.setGroupInfoProvider(this::getGroupData,true);
        //获取未读消息数量
        RongIM.getInstance().addUnReadMessageCountChangedObserver(i -> {
            LogUtils.i(i);
            // i 是未读数量
            getFriendApplyCount(i);
        }, Conversation.ConversationType.PRIVATE,Conversation.ConversationType.GROUP);
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

    //会话列表头像名称显示
    private UserInfo getFriendData(String userId) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_USER_INFO)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("id",userId)//对应的用户编号
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
                            IMWechatUserInfoBean bean=new Gson().fromJson(response.body(),IMWechatUserInfoBean.class);
                            if (bean.getCode().equals("200")){
                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getData().getUserId()
                                        ,bean.getData().getName()
                                        ,Uri.parse(bean.getData().getPortraitUri())));
//                            }else if (bean.getCode().equals("-1")){
//                                ToastUtils.showShort(bean.getMsg());
//                                LoginUtils.getExitLogin(getActivity());
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
        return null;
    }

    //会话列表群组头像名称显示
    private Group getGroupData(String group_id) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_GROUP_INFO)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("group_id",group_id)//群组编号
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
                            IMWechatGroupInfoBean bean=new Gson().fromJson(response.body(),IMWechatGroupInfoBean.class);
                            if (bean.getCode().equals("200")){
                                Group groupInfo = new Group(bean.getData().getGroupId()
                                        , bean.getData().getGroupName()
                                        , Uri.parse(bean.getData().getGroupName()));
                                RongIM.getInstance().refreshGroupInfoCache(groupInfo);
//                            }else if (bean.getCode().equals("-1")){
//                                ToastUtils.showShort(bean.getMsg());
//                                LoginUtils.getExitLogin(getActivity());
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
        return null;
    }

    //好友申请数量
    private void getFriendApplyCount(int i) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.FRIEND_APPLY_COUNT)
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
                            IMFriendApplyCountBean bean=new Gson().fromJson(response.body(), IMFriendApplyCountBean.class);
                            if (bean.getCode().equals("200")){
                                count=bean.getData().getCount();
                                if (count == 0) {
                                    tvNumber.setVisibility(View.INVISIBLE);
                                } else if (count > 0 && count < 100) {
                                    tvNumber.setVisibility(View.VISIBLE);
                                    tvNumber.setText(String.valueOf(count));
                                } else {
                                    tvNumber.setVisibility(View.VISIBLE);
                                    tvNumber.setText(R.string.im_no_read_message);
                                }
                                //设置底部标签数量
                                int counts=i+count;
                                if (counts < 1) {
                                    relativeLayout.setVisibility(View.GONE);
                                } else if (counts < 100) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    number.setText(" " + counts + " ");
                                } else {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    number.setText("99+");
                                }
//                            }else if (bean.getCode().equals("-1")){
//                                ToastUtils.showShort(bean.getMsg());
//                                LoginUtils.getExitLogin(getActivity());
                            }else {
                                tvNumber.setVisibility(View.INVISIBLE);
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
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

    @OnClick({R.id.tv_bar_title, R.id.iv_message,R.id.iv_header})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_bar_title:
                ActivityUtils.startActivity(IMSearchActivity.class);
                break;
            case R.id.iv_message:
                ActivityUtils.startActivity(IMContactsActivity.class);
                break;
            case R.id.iv_header:
                viewPager.setCurrentItem(3);
                break;
                default:
                    break;
        }
    }
}
