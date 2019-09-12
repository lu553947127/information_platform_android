package com.shuangduan.zcy.rongyun.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

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
public class IMPrivateChatActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_right)
    TextView tvBarRight;
    @BindView(R.id.iv_bar_right)
    ImageView ivBarRight;

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
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData()
                .getLastPathSegment().toUpperCase(Locale.US));
        user_id = getIntent().getData().getQueryParameter("targetId");
        tvBarTitle.setText(getIntent().getData().getQueryParameter("title"));
        enterFragment(mConversationType, user_id);//加载页面
        //判断是否为群聊还是单聊
        if (mConversationType.getName().equals("group")){
            tvBarRight.setVisibility(View.GONE);
            ivBarRight.setImageResource(R.drawable.icon_more);
        }
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
}
