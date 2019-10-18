package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
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
import com.shuangduan.zcy.rongyun.RongExtraBean;
import com.shuangduan.zcy.view.demand.FindBuyerDetailActivity;
import com.shuangduan.zcy.view.demand.FindRelationshipDetailActivity;
import com.shuangduan.zcy.view.demand.FindSubstanceDetailActivity;
import com.shuangduan.zcy.view.mine.MaterialOrderDetailActivity;
import com.shuangduan.zcy.view.mine.TransRecordActivity;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.view.supplier.SupplierDetailActivity;

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
        if (mConversationType.getName().equals("group")) {
            ivBarRight.setImageResource(R.drawable.icon_more);
            tvBarRight.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.VISIBLE);
            ll_bar_title_small.setVisibility(View.GONE);
        } else if (mConversationType.getName().equals("private")) {
            ivBarRight.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.GONE);
            ll_bar_title_small.setVisibility(View.VISIBLE);
            //刷新好友头像
            getFriendData(user_id);
            //刷新自己头像
            getFriendData(String.valueOf(SPUtils.getInstance().getInt(SpConfig.USER_ID)));
        } else {
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

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL + Common.WECHAT_USER_INFO)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("id", userId)//对应的用户编号
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
                            IMWechatUserInfoBean bean = new Gson().fromJson(response.body(), IMWechatUserInfoBean.class);
                            if (bean.getCode().equals("200")) {
                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getData().getUserId()
                                        , bean.getData().getName()
                                        , Uri.parse(bean.getData().getPortraitUri())));
                                if (userId.equals(user_id)) {
                                    tvBarCompanyOrPost.setText(bean.getData().getCompany() + " " + bean.getData().getPosition());
                                }
                            } else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        } catch (JsonSyntaxException | IllegalStateException ignored) {
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                Bundle bundle = new Bundle();
                bundle.putString("group_id", user_id);
                ActivityUtils.startActivity(bundle, IMGroupDetailsActivity.class);
                break;
        }
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        if (conversationType == Conversation.ConversationType.CUSTOMER_SERVICE || conversationType == Conversation.ConversationType.PUBLIC_SERVICE
                || conversationType == Conversation.ConversationType.APP_PUBLIC_SERVICE || conversationType == Conversation.ConversationType.SYSTEM) {
            return false;
        }
        if (userInfo.getUserId() != null) {
            if (Integer.parseInt(userInfo.getUserId()) != SPUtils.getInstance().getInt(SpConfig.USER_ID)) {
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


        if (mConversationType == Conversation.ConversationType.SYSTEM) {

            if (Integer.valueOf(message.getTargetId()) != 18) return false;

            String messageJson = new Gson().toJson(message.getContent());
            String extra = JsonUtils.getString(messageJson, "extra");

            LogUtils.i(extra);

            if (StringUtils.isEmpty(extra)) return false;

            RongExtraBean extraBean = new Gson().fromJson(extra, RongExtraBean.class);

            Bundle bundle = new Bundle();
            switch (extraBean.type) {
                case 3: //工程信息：%s已通过审核
                case 7: //收藏的工程信息更新
                case 15://认购的工程信息即将到期
                    bundle.putInt(CustomConfig.PROJECT_ID, extraBean.data.id);
                    bundle.putInt(CustomConfig.LOCATION, 0);
                    ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);//工程信息详情->概况
                    break;
                case 5: //工程动态：%s已通过审核
                    bundle.putInt(CustomConfig.PROJECT_ID, extraBean.data.id);
                    bundle.putInt(CustomConfig.LOCATION, 1);
                    ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);//工程信息详情-->动态
                    break;
                case 8: //收藏的招采信息更新
                    bundle.putInt(CustomConfig.RECRUIT_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, RecruitDetailActivity.class); //招采信息详情
                    break;
                case 12://发布的工程有新的收益
                case 13://发布的工程动态有新的收益
                case 14://认购的工程信息有新的收益
                case 16://人脉有新的收益
                case 17://回答的找关系已被采纳，有一笔收益到账
                case 18://查看了工程信息消费记录
                case 19://查看了工程动态收益消费
                case 20://认购了一条工程信息消费
                case 21://您查看了一条供应商信息消费
                case 22://您查看了一条找买家需求信息消费
                case 23://您查看了一条找物资需求信息消费
                    ActivityUtils.startActivity(TransRecordActivity.class);  //跳转交易记录列表
                    break;
                case 24:// 物资订单状态有更新
                    bundle.putInt(CustomConfig.ORDER_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, MaterialOrderDetailActivity.class);//物资预定详情
                    break;
                case 25://优质供应商申请已通过审核
                    bundle.putInt(CustomConfig.SUPPLIER_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, SupplierDetailActivity.class);//跳转供应商详情
                    break;
                case 27://找关系发布成功
                case 28://发布的找关系即将到期
                case 29://发布的找关系有人回答
                    bundle.putInt(CustomConfig.DEMAND_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, FindRelationshipDetailActivity.class);//找关系详情页
                    break;
                case 30://发布的找物资成功
                    bundle.putInt(CustomConfig.DEMAND_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, FindSubstanceDetailActivity.class);//找物资详情
                    break;
                case 32://发布找买家成功
                    bundle.putInt(CustomConfig.DEMAND_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, FindBuyerDetailActivity.class);//找买家详情
                    break;
                case 34://申请添加好友
                    ActivityUtils.startActivity(NewFriendsActivity.class);//好友提醒
                    break;
                case 36://好友申请通过
//                    RongIM.getInstance().startPrivateChat(this, String.valueOf(extraBean.data.id),"");
                    break;
            }


            return true;
        } else {
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
