package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.rongyun.bean.RongExtraBean;
import com.shuangduan.zcy.view.material.MaterialDetailActivity;
import com.shuangduan.zcy.view.material.MaterialEquipmentDetailActivity;
import com.shuangduan.zcy.view.mine.demand.FindBuyerDetailActivity;
import com.shuangduan.zcy.view.mine.demand.FindRelationshipReleaseDetailActivity;
import com.shuangduan.zcy.view.mine.demand.FindSubstanceDetailActivity;
import com.shuangduan.zcy.view.mine.material.MaterialOrderDetailActivity;
import com.shuangduan.zcy.view.mine.user.UserInfoActivity;
import com.shuangduan.zcy.view.mine.wallet.TransRecordDetailActivity;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.vm.IMAddVm;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.RichContentMessage;

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
    private IMAddVm imAddVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_private_chat;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        imAddVm = getViewModel(IMAddVm.class);

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
            imAddVm.userInfo(user_id);
            imAddVm.userInfo(String.valueOf(SPUtils.getInstance().getInt(SpConfig.USER_ID)));
        } else {
            ivBarRight.setVisibility(View.GONE);
            tvBarTitle.setVisibility(View.VISIBLE);
            ll_bar_title_small.setVisibility(View.GONE);
        }
        //设置会话页面操作监听
        RongIM.setConversationClickListener(this);

        //会话列表头像名称显示
        imAddVm.imUserInfoLiveData.observe(this, imWechatUserInfoBean -> {
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(imWechatUserInfoBean.getUserId(), imWechatUserInfoBean.getName(), Uri.parse(imWechatUserInfoBean.getPortraitUri())));
            if (imWechatUserInfoBean.getUserId().equals(user_id)) {
                if (!TextUtils.isEmpty(imWechatUserInfoBean.getCompany())&&!TextUtils.isEmpty(imWechatUserInfoBean.getPosition())){
                    tvBarCompanyOrPost.setText(imWechatUserInfoBean.getCompany() + " " + imWechatUserInfoBean.getPosition());
                }else {
                    tvBarCompanyOrPost.setText("");
                }
            }
        });
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
        LogUtils.i(message);
        LogUtils.i(message.getContent());
        //接收的系统消息 根据服务器返回的类型跳转对应的页面
        if (mConversationType == Conversation.ConversationType.SYSTEM) {
            //系统消息的id固定为18
            if (Integer.valueOf(message.getTargetId()) != 18) return false;
            //接收的数据转换成json
            String messageJson = new Gson().toJson(message.getContent());
            String extra = JsonUtils.getString(messageJson, "extra");
            if (StringUtils.isEmpty(extra)) return false;
            LogUtils.i(extra);
            RongExtraBean extraBean = new Gson().fromJson(extra, RongExtraBean.class);

            Bundle bundle = new Bundle();
            switch (extraBean.type) {
                case 3: //工程信息：%s已通过审核
                case 7: //收藏的工程信息更新
                case 15://认购的工程信息即将到期
                case 18://查看了工程信息消费记录
                case 20://认购了一条工程信息消费
                    if (extraBean.data == null) return false;
                    bundle.putInt(CustomConfig.PROJECT_ID, extraBean.data.id);
                    bundle.putInt(CustomConfig.LOCATION, 0);
                    ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);//工程信息详情->概况
                    break;
                case 5: //工程动态：%s已通过审核
                case 19://查看了工程动态收益消费
                    if (extraBean.data == null) return false;
                    bundle.putInt(CustomConfig.PROJECT_ID, extraBean.data.id);
                    bundle.putInt(CustomConfig.LOCATION, 1);
                    ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);//工程信息详情-->动态
                    break;
                case 8: //收藏的招采信息更新
                    if (extraBean.data == null) return false;
                    bundle.putInt(CustomConfig.RECRUIT_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, RecruitDetailActivity.class); //招采信息详情
                    break;
                case 12://发布的工程有新的收益
                case 13://发布的工程动态有新的收益
                case 14://认购的工程信息有新的收益
                case 16://邀请好友有新的收益
                case 17://回答的找资源已被采纳，有一笔收益到账
                case 21://您查看了一条供应商信息消费
                case 22://您查看了一条找买家需求信息消费
                case 23://您查看了一条找物资需求信息消费
                    if (extraBean.data == null) return false;
                    bundle.putInt(CustomConfig.TRANS_RECORD_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, TransRecordDetailActivity.class); //交易记录详情
                    break;
                case 24:// 物资订单状态有更新
                    bundle.putInt(CustomConfig.ORDER_ID, extraBean.data.orderId);
                    bundle.putInt(CustomConfig.MATERIALS_TYPE, extraBean.data.orderType);
                    ActivityUtils.startActivity(bundle, MaterialOrderDetailActivity.class);//物资预定详情
                    break;
//                case 25://优质供应商申请已通过审核
//                    bundle.putInt(CustomConfig.SUPPLIER_ID, extraBean.data.id);
//                    ActivityUtils.startActivity(bundle, SupplierDetailActivity.class);//跳转供应商详情
//                    break;
                case 27://找资源发布成功
                case 28://发布的找资源即将到期
                case 29://发布的找资源有人回答
                    if (extraBean.data == null) return false;
                    bundle.putInt(CustomConfig.DEMAND_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, FindRelationshipReleaseDetailActivity.class);//找资源详情页
                    break;
                case 30://发布的找物资成功
                    if (extraBean.data == null) return false;
                    bundle.putInt(CustomConfig.DEMAND_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, FindSubstanceDetailActivity.class);//找物资详情
                    break;
                case 32://发布找买家成功
                    if (extraBean.data == null) return false;
                    bundle.putInt(CustomConfig.DEMAND_ID, extraBean.data.id);
                    ActivityUtils.startActivity(bundle, FindBuyerDetailActivity.class);//找买家详情
                    break;
                case 34://申请添加好友
                    ActivityUtils.startActivity(NewFriendsActivity.class);//好友提醒
                    break;
                case 36://好友申请通过
                    if (extraBean.data == null) return false;
                    RongIM.getInstance().startPrivateChat(this, String.valueOf(extraBean.data.userId), extraBean.data.name);
                    break;
            }
            return true;
        } else if (mConversationType == Conversation.ConversationType.PRIVATE){
//            //文字接收
//            if (message.getContent() instanceof TextMessage) {
//                LogUtils.i(((TextMessage) message.getContent()).getExtra());
//            }
            //图文接收
            if (message.getContent() instanceof RichContentMessage) {
                if (!StringUtils.isEmpty(((RichContentMessage) message.getContent()).getExtra())) {
                    Bundle bundle = new Bundle();
                    switch (((RichContentMessage) message.getContent()).getUrl()){
                        case "1"://周转材料
                            bundle.putInt(CustomConfig.MATERIAL_ID, Integer.parseInt(((RichContentMessage) message.getContent()).getExtra()));
                            ActivityUtils.startActivity(bundle, MaterialDetailActivity.class);
                            break;
                        case "2"://设备
                            bundle.putInt(CustomConfig.MATERIAL_ID, Integer.parseInt(((RichContentMessage) message.getContent()).getExtra()));
                            ActivityUtils.startActivity(bundle, MaterialEquipmentDetailActivity.class);
                            break;
                    }
                }else {
                    ToastUtils.showShort("找不到当前物资");
                }
                return true;
            }
//            //定位接收
//            if (message.getContent() instanceof LocationMessage) {
//                LogUtils.i(((LocationMessage) message.getContent()).getLat());
//                LogUtils.i(((LocationMessage) message.getContent()).getLng());
//            }
//            //图片接收
//            if (message.getContent() instanceof ImageMessage) {
//                LogUtils.i(((ImageMessage) message.getContent()).getThumUri());
//                LogUtils.i(((ImageMessage) message.getContent()).getLocalUri());
//                LogUtils.i(((ImageMessage) message.getContent()).getRemoteUri());
//                PictureEnlargeUtils.getPictureEnlarge(this, String.valueOf(((ImageMessage) message.getContent()).getRemoteUri()));
//                return true;
//            }
            return false;
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
