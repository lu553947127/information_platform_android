package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMFriendListAdapter;
import com.shuangduan.zcy.adapter.IMGroupListAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendApplyCountBean;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.model.bean.IMGroupListBean;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

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
    IMGroupListAdapter imGroupListAdapter;
    List<IMGroupListBean.DataBean.ListBean> listGroup=new ArrayList<>();
    IMGroupListBean imGroupListBean;
    IMFriendListAdapter imFriendListAdapter;
    List<IMFriendListBean.DataBean.ListBean> listFriend=new ArrayList<>();
    IMFriendListBean imFriendListBean;

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
        setBackgroundData(rvGroup,ivGroup,tvGroup);
        setBackgroundData(rvFriend,ivFriend,tvFriend);

        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        rvGroup.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        imGroupListAdapter = new IMGroupListAdapter(R.layout.item_im_group_list, listGroup);
        imGroupListAdapter.setEmptyView(R.layout.layout_loading, rvGroup);
        rvGroup.setAdapter(imGroupListAdapter);
        imGroupListAdapter.setOnItemClickListener((adapter, view, position) -> {
            RongIM.getInstance().startGroupChat(IMContactsActivity.this
                    , imGroupListBean.getData().getList().get(position).getGroup_id()
                    , imGroupListBean.getData().getList().get(position).getGroup_name());
        });

        rvFriend.setLayoutManager(new LinearLayoutManager(this));
        rvFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        imFriendListAdapter = new IMFriendListAdapter(R.layout.item_im_friend_list, listFriend);
        imFriendListAdapter.setEmptyView(R.layout.layout_loading, rvFriend);
        rvFriend.setAdapter(imFriendListAdapter);

        imFriendListAdapter.setOnItemClickListener((adapter, view, position) -> {
            RongIM.getInstance().startPrivateChat(IMContactsActivity.this, imFriendListBean.getData().getList().get(position).getUserId()
                    , imFriendListBean.getData().getList().get(position).getName());
        });

        getGroupList();
        getFriendList();
    }

    //我的群组列表
    private void getGroupList() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_MY_GROUP)
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
                             imGroupListBean=new Gson().fromJson(response.body(), IMGroupListBean.class);
                            if (imGroupListBean.getCode().equals("200")){
                                listGroup.clear();
                                listGroup.addAll(imGroupListBean.getData().getList());
                                if(listGroup!=null&&listGroup.size()!=0){
                                    imGroupListAdapter.notifyDataSetChanged();
                                }else {
                                    imGroupListAdapter.setEmptyView(R.layout.layout_empty, rvGroup);
                                }
                            }else if (imGroupListBean.getCode().equals("-1")){
                                ToastUtils.showShort(imGroupListBean.getMsg());
                                LoginUtils.getExitLogin(IMContactsActivity.this);
                            }else {
                                imGroupListAdapter.setEmptyView(R.layout.layout_empty, rvGroup);
                                listGroup.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    //我的好友列表
    private void getFriendList() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_MY_FRIEND)
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
                            imFriendListBean=new Gson().fromJson(response.body(), IMFriendListBean.class);
                            if (imFriendListBean.getCode().equals("200")){
                                listFriend.clear();
                                listFriend.addAll(imFriendListBean.getData().getList());
                                if(listFriend!=null&&listFriend.size()!=0){
                                    imFriendListAdapter.notifyDataSetChanged();
                                }else {
                                    imFriendListAdapter.setEmptyView(R.layout.layout_empty, rvFriend);
                                }
//                            }else if (imFriendListBean.getCode().equals("-1")){
//                                ToastUtils.showShort(imFriendListBean.getMsg());
//                                LoginUtils.getExitLogin(IMContactsActivity.this);
                            }else {
                                imFriendListAdapter.setEmptyView(R.layout.layout_empty, rvFriend);
                                listFriend.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    //好友申请数量
    private void getFriendApplyCount() {

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
                                if (bean.getData().getCount()!=0){
                                    tvNumber.setText(String.valueOf(bean.getData().getCount()));
                                    tvNumber.setVisibility(View.VISIBLE);
                                }else {
                                    tvNumber.setVisibility(View.GONE);
                                }
//                            }else if (bean.getCode().equals("-1")){
//                                ToastUtils.showShort(bean.getMsg());
//                                LoginUtils.getExitLogin(IMContactsActivity.this);
                            }else {
                                tvNumber.setVisibility(View.GONE);
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
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
                setBackgroundData(rvGroup,ivGroup,tvGroup);
                break;
            case R.id.rl_my_friend:
                setBackgroundData(rvFriend,ivFriend,tvFriend);
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
    private void setBackgroundData(RecyclerView recyclerView,ImageView imageView,TextView textView) {
        if (recyclerView.getVisibility() == View.GONE) {
            imageView.setBackgroundResource(R.drawable.icon_up);
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            imageView.setBackgroundResource(R.drawable.icon_bottom);
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFriendApplyCount();
    }
}
