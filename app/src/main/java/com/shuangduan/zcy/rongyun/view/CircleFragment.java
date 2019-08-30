package com.shuangduan.zcy.rongyun.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.utils.BarUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));

        FragmentManager fragmentManage = getChildFragmentManager();
        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + MyApplication.getInstance().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragement.setUri(uri);
    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_bar_title, R.id.iv_message})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_bar_title:
                ActivityUtils.startActivity(IMSearchActivity.class);
                break;
            case R.id.iv_message:
                ActivityUtils.startActivity(IMContactActivity.class);
                break;
                default:
                    break;
        }
    }
}
