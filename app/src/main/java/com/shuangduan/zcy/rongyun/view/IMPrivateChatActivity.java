package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

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
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMWechatUserInfoBean;
import com.shuangduan.zcy.view.mine.UserInfoActivity;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  单聊页面
 * @time 2019/8/29 17:51
 * @change
 * @chang time
 * @class describe
 */
public class IMPrivateChatActivity extends BaseActivity implements RongIM.ConversationClickListener {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    ImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.tv_bar_title_small)
    AppCompatTextView tvBarTitleSmall;
    @BindView(R.id.tv_bar_company_or_post)
    AppCompatTextView tvBarCompanyOrPost;
    @BindView(R.id.ll_bar_title_small)
    LinearLayout ll_bar_title_small;
    private ConversationFragment fragment;
    private Conversation.ConversationType mConversationType;
    private String user_id;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_private_chat;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        mConversationType = Conversation.ConversationType.valueOf(Objects.requireNonNull(Objects.requireNonNull(getIntent().getData()).getLastPathSegment()).toUpperCase(Locale.US));
        user_id = getIntent().getData().getQueryParameter("targetId");
        tvBarTitle.setText(getIntent().getData().getQueryParameter("title"));
        tvBarTitleSmall.setText(getIntent().getData().getQueryParameter("title"));
        enterFragment(mConversationType, user_id);//加载页面
        //判断是否为群聊还是单聊
        if (mConversationType.getName().equals("group")){
            ivBarRight.setImageResource(R.drawable.icon_more);
            tvBarRight.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.VISIBLE);
            ll_bar_title_small.setVisibility(View.GONE);
        }else if (mConversationType.getName().equals("private")){
            ivBarRight.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.GONE);
            ll_bar_title_small.setVisibility(View.VISIBLE);
            //刷新好友头像
            getFriendData(user_id);
            //刷新自己头像
            getFriendData(String.valueOf(SPUtils.getInstance().getInt(SpConfig.USER_ID)));
        }else {
            ivBarRight.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.VISIBLE);
            ll_bar_title_small.setVisibility(View.GONE);
        }
        //设置会话页面操作监听
        RongIM.setConversationClickListener(this);
    }

    //加载会话页面
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        fragment = new ConversationFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();
        fragment.setUri(uri);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_im_contact_fragment, fragment);
        transaction.commitAllowingStateLoss();
    }

    //会话列表头像名称显示
    private void getFriendData(String userId) {

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

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            IMWechatUserInfoBean bean=new Gson().fromJson(response.body(),IMWechatUserInfoBean.class);
                            if (bean.getCode().equals("200")){
                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getData().getUserId()
                                        ,bean.getData().getName()
                                        ,Uri.parse(bean.getData().getPortraitUri())));
                                if (userId.equals(user_id)){
                                    tvBarCompanyOrPost.setText(bean.getData().getCompany()+" "+bean.getData().getPosition());
                                }
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @OnClick({R.id.iv_bar_back,R.id.iv_bar_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                Bundle bundle = new Bundle();
                bundle.putString("group_id", user_id);
                ActivityUtils.startActivity(bundle,IMGroupDetailsActivity.class);
                break;
        }
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        if (conversationType == Conversation.ConversationType.CUSTOMER_SERVICE || conversationType == Conversation.ConversationType.PUBLIC_SERVICE
                || conversationType == Conversation.ConversationType.APP_PUBLIC_SERVICE|| conversationType == Conversation.ConversationType.SYSTEM) {
            return false;
        }
        if (userInfo.getUserId() != null) {
            if (Integer.parseInt(userInfo.getUserId())!= SPUtils.getInstance().getInt(SpConfig.USER_ID)){
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.UID, Integer.parseInt(userInfo.getUserId()));
                ActivityUtils.startActivity(bundle, UserInfoActivity.class);
            }
        }
        return true;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        if (mConversationType == Conversation.ConversationType.SYSTEM){
//            ToastUtils.showShort(String.valueOf(message));
            LogUtils.i(message);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
}
