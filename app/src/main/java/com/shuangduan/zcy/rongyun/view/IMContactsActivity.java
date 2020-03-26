package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMFriendListAdapter;
import com.shuangduan.zcy.adapter.IMGroupListAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.model.bean.IMGroupListBean;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  我的通讯录
 * @time 2019/8/29 17:51
 * @change
 * @chang time
 * @class describe
 */

public class IMContactsActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_new_friend)
    RelativeLayout rlNewFriend;
    @BindView(R.id.rl_my_group)
    RelativeLayout rlMyGroup;
    @BindView(R.id.rl_my_friend)
    RelativeLayout rlMyFriend;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    @BindView(R.id.rv_friend)
    RecyclerView rvFriend;
    @BindView(R.id.iv_my_group)
    ImageView ivGroup;
    @BindView(R.id.iv_my_friend)
    ImageView ivFriend;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_more_group)
    TextView tvGroup;
    @BindView(R.id.tv_more_friend)
    TextView tvFriend;
    @BindView(R.id.ll_group)
    LinearLayout llGroup;
    @BindView(R.id.ll_friend)
    LinearLayout llFriend;
    IMAddVm imAddVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_contacts;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        setBackgroundData(rvGroup,ivGroup,llGroup);
        setBackgroundData(rvFriend,ivFriend,llFriend);

        imAddVm = getViewModel(IMAddVm.class);

        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        rvGroup.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IMGroupListAdapter imGroupListAdapter = new IMGroupListAdapter(R.layout.item_im_group_list, null);
        imGroupListAdapter.setEmptyView(R.layout.layout_loading, rvGroup);
        rvGroup.setAdapter(imGroupListAdapter);

        rvFriend.setLayoutManager(new LinearLayoutManager(this));
        rvFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IMFriendListAdapter imFriendListAdapter = new IMFriendListAdapter(R.layout.item_im_friend_list, null);
        imFriendListAdapter.setEmptyView(R.layout.layout_loading, rvFriend);
        rvFriend.setAdapter(imFriendListAdapter);

        imGroupListAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMGroupListBean.ListBean listBean = imGroupListAdapter.getData().get(position);
            RongIM.getInstance().startGroupChat(IMContactsActivity.this,listBean.getGroup_id(),listBean.getGroup_name());
        });

        imFriendListAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMFriendListBean.ListBean listBean = imFriendListAdapter.getData().get(position);
            if (listBean.getUserId().equals("18")){
                RongIM.getInstance().startConversation(IMContactsActivity.this, Conversation.ConversationType.SYSTEM, listBean.getUserId(), listBean.getName());
            }else {
                RongIM.getInstance().startPrivateChat(IMContactsActivity.this, listBean.getUserId(), listBean.getName());
            }
        });

        imAddVm.groupListData.observe(this,imGroupListBean -> {
            imGroupListAdapter.setNewData(imGroupListBean.getList());
            if(imGroupListBean.getList().size()!=0){
                rlMyGroup.setClickable(true);
                rvGroup.setVisibility(View.VISIBLE);
                if (imGroupListBean.getList().size()>3){
                    tvGroup.setVisibility(View.VISIBLE);
                }else {
                    tvGroup.setVisibility(View.GONE);
                }
                imGroupListAdapter.notifyDataSetChanged();
            }else {
                rlMyGroup.setClickable(false);
                rvGroup.setVisibility(View.GONE);
            }
        });

        imAddVm.friendListLiveData.observe(this,imFriendListBean ->{
            imFriendListAdapter.setNewData(imFriendListBean.getList());
            if(imFriendListBean.getList().size()!=0){
                rlMyFriend.setClickable(true);
                rvFriend.setVisibility(View.VISIBLE);
                if (imFriendListBean.getList().size()>3){
                    tvFriend.setVisibility(View.VISIBLE);
                }else {
                    tvFriend.setVisibility(View.GONE);
                }
            }else {
                rlMyFriend.setClickable(false);
                rvFriend.setVisibility(View.GONE);
            }
        });

        imAddVm.applyCountData.observe(this,friendApplyCountBean -> {
            if (friendApplyCountBean.getCount()!=0){
                tvNumber.setVisibility(View.VISIBLE);
                tvNumber.setText(String.valueOf(friendApplyCountBean.getCount()));
            }else {
                tvNumber.setVisibility(View.GONE);
            }
        });

        imAddVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
//                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back,R.id.rl_new_friend,R.id.rl_my_group,R.id.rl_my_friend,R.id.tv_bar_title,R.id.tv_more_group,R.id.tv_more_friend})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_title:
                ActivityUtils.startActivity(IMSearchActivity.class);
                break;
            case R.id.rl_new_friend:
                ActivityUtils.startActivity(NewFriendsActivity.class);
                break;
            case R.id.rl_my_group:
                setBackgroundData(rvGroup,ivGroup,llGroup);
                break;
            case R.id.rl_my_friend:
                setBackgroundData(rvFriend,ivFriend,llFriend);
                break;
            case R.id.tv_more_group:
                ActivityUtils.startActivity(bundle,IMGroupMoreActivity.class);
                break;
            case R.id.tv_more_friend:
                ActivityUtils.startActivity(bundle,IMFriendMoreActivity.class);
                break;
        }
    }

    //切换按钮状态
    @SuppressLint("NewApi")
    private void setBackgroundData(RecyclerView recyclerView, ImageView imageView, LinearLayout linearLayout) {
        if (recyclerView.getVisibility() == View.GONE) {
            imageView.setBackgroundResource(R.drawable.icon_up);
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            imageView.setBackgroundResource(R.drawable.icon_bottom);
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        imAddVm.applyCount();
        imAddVm.myGroup(10);
        imAddVm.friendList(10);
    }
}
