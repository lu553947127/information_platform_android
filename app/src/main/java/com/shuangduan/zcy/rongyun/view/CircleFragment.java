package com.shuangduan.zcy.rongyun.view;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMWechatGroupInfoBean;
import com.shuangduan.zcy.model.bean.IMWechatUserInfoBean;
import com.shuangduan.zcy.model.event.AvatarEvent;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.GroupInfoProvider;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
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
public class CircleFragment extends BaseFragment implements RongIM.UserInfoProvider {

    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.iv_header)
    CircleImageView iv_header;
    private UserInfoVm userInfoVm;

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
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

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
        //根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
        RongIM.setUserInfoProvider(this::getFriendData, true);

        RongIM.setGroupInfoProvider(this::getGroupData,true);
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
                        LogUtils.i(response);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.i(response);
                        Log.e("TAG","请求成功"+response.body());
                        Gson gson=new Gson();
                        try {
                            IMWechatUserInfoBean bean=gson.fromJson(response.body(),IMWechatUserInfoBean.class);
                            if (bean.getCode().equals("200")){
                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getData().getUserId()
                                        ,bean.getData().getName(),Uri.parse(bean.getData().getPortraitUri())));
                            }else {
                                ToastUtils.showShort(getString(R.string.request_error));
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
                        LogUtils.i(response);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.i(response);
                        Log.e("TAG","请求成功"+response.body());
                        Gson gson=new Gson();
                        try {
                            IMWechatGroupInfoBean bean=gson.fromJson(response.body(),IMWechatGroupInfoBean.class);
                            if (bean.getCode().equals("200")){
                                Group groupInfo;
                                groupInfo = new Group(bean.getData().getGroupId(), bean.getData().getGroupName()
                                        , Uri.parse(bean.getData().getGroupName()));
                                RongIM.getInstance().refreshGroupInfoCache(groupInfo);
                            }else {
                                ToastUtils.showShort(getString(R.string.request_error));
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
        return null;
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

    @OnClick({R.id.tv_bar_title, R.id.iv_message})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_bar_title:
                ActivityUtils.startActivity(IMSearchActivity.class);
                break;
            case R.id.iv_message:
                ActivityUtils.startActivity(IMContactsActivity.class);
                break;
                default:
                    break;
        }
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return null;
    }
}
