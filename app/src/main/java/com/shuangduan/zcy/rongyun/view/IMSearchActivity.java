package com.shuangduan.zcy.rongyun.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMSearchAdapter;
import com.shuangduan.zcy.adapter.IMSearchGroupAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  融云搜索好友
 * @time 2019/8/29 14:03
 * @change
 * @chang time
 * @class describe
 */
public class IMSearchActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_keyword)
    EditText edtKeyword;
    @BindView(R.id.rv)
    SwipeMenuRecyclerView rv;
    @BindView(R.id.rv2)
    SwipeMenuRecyclerView rv2;

    private IMSearchAdapter imSearchAdapter;
    private List<IMFriendSearchBean.DataBean.FriendBean> list=new ArrayList<>();

    private IMSearchGroupAdapter imSearchGroupAdapter;
    private List<IMFriendSearchBean.DataBean.GroupBean> list_group=new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_search;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.search_friends));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        imSearchAdapter = new IMSearchAdapter(R.layout.item_im_search, list);
        imSearchAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(imSearchAdapter);

        rv2.setLayoutManager(new LinearLayoutManager(this));
        rv2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        imSearchGroupAdapter = new IMSearchGroupAdapter(R.layout.item_im_search_group, list_group);
        imSearchGroupAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv2.setAdapter(imSearchGroupAdapter);

        getFriendSearch();


//        imSearchAdapter.setOnItemClickListener((adapter, view, position) -> {
//            IMFriendSearchBean.ListBean listBean = imSearchAdapter.getData().get(position);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(CustomConfig.FRIEND_DATA, listBean);
//            ActivityUtils.startActivity(bundle, IMAddFriendActivity.class);
//        });

        IMAddVm imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);
//        imAddVm.searchLiveData.observe(this, imFriendSearchBean -> {
//            if (imFriendSearchBean.getPage() == 1) {
//                imSearchAdapter.setNewData(imFriendSearchBean.getList());
//                imSearchAdapter.setEmptyView(R.layout.layout_empty, rv);
//            }else {
//                imSearchAdapter.addData(imFriendSearchBean.getList());
//            }
//            setNoMore(imFriendSearchBean.getPage(), imFriendSearchBean.getCount());
//        });
        edtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                imAddVm.searchName = s.toString();
//                imAddVm.search();
                getFriendSearch();
            }
        });
        edtKeyword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(edtKeyword.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(IMSearchActivity.this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                getFriendSearch();
                return true;
            }
            return false;
        });
//        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                imAddVm.searchMore();
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                imAddVm.search();
//            }
//        });
    }
//
//    private void setNoMore(int page, int count){
//        if (page == 1){
//            if (page * 10 >= count){
//                if (refresh.getState() == RefreshState.None){
//                    refresh.setNoMoreData(true);
//                }else {
//                    refresh.finishRefreshWithNoMoreData();
//                }
//            }else {
//                refresh.finishRefresh();
//            }
//        }else {
//            if (page * 10 >= count){
//                refresh.finishLoadMoreWithNoMoreData();
//            }else {
//                refresh.finishLoadMore();
//            }
//        }
//    }

    //好友群组搜索列表
    private void getFriendSearch() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.FRIEND_SEARCH)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("name",edtKeyword.getText().toString())//搜索名称
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
                            IMFriendSearchBean bean=gson.fromJson(response.body(),IMFriendSearchBean.class);
                            if (bean.getCode().equals("200")){
                                list.clear();
                                list.addAll(bean.getData().getFriend());
                                imSearchAdapter.notifyDataSetChanged();

                                list_group.clear();
                                list_group.addAll(bean.getData().getGroup());
                                imSearchGroupAdapter.notifyDataSetChanged();
                            }else {
//                                imSearchAdapter.setNewData(bean.getData().getFriend());
                                imSearchAdapter.setEmptyView(R.layout.layout_empty, rv);
                                imSearchGroupAdapter.setEmptyView(R.layout.layout_empty, rv2);
                                list.clear();
                                list_group.clear();
                            }
                        }catch (JsonSyntaxException e){
                        }catch (IllegalStateException e){
                        }
                    }
                });
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
