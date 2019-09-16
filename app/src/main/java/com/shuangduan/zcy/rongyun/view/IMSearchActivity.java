package com.shuangduan.zcy.rongyun.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMSearchAdapter;
import com.shuangduan.zcy.adapter.IMSearchGroupAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author 鹿鸿祥 QQ:553947127
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
    RecyclerView rv;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.tv_more_friend)
    TextView tvFriend;
    @BindView(R.id.tv_more_group)
    TextView tvGroup;

    private IMSearchAdapter imSearchAdapter;
    private List<IMFriendSearchBean.DataBean.FriendBean> list=new ArrayList<>();

    private IMSearchGroupAdapter imSearchGroupAdapter;
    private List<IMFriendSearchBean.DataBean.GroupBean> list_group=new ArrayList<>();

    private IMFriendSearchBean imFriendSearchBean;

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

        imSearchAdapter.setOnItemClickListener((adapter, view, position) -> {
            RongIM.getInstance().startPrivateChat(IMSearchActivity.this, imFriendSearchBean.getData().getFriend().get(position).getUserId()
                    , imFriendSearchBean.getData().getFriend().get(position).getName());
        });
        imSearchGroupAdapter.setOnItemClickListener((adapter, view, position) -> {
            RongIM.getInstance().startGroupChat(IMSearchActivity.this
                    , imFriendSearchBean.getData().getGroup().get(position).getGroup_id()
                    , imFriendSearchBean.getData().getGroup().get(position).getGroup_name());
        });

        edtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
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
    }

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
                        LogUtils.i(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.i(response.body());
                        try {
                            imFriendSearchBean=new Gson().fromJson(response.body(),IMFriendSearchBean.class);
                            if (imFriendSearchBean.getCode().equals("200")){
                                list.clear();
                                list.addAll(imFriendSearchBean.getData().getFriend());
                                if(list!=null&&list.size()!=0){
                                    imSearchAdapter.notifyDataSetChanged();
                                }else {
                                    imSearchAdapter.setEmptyView(R.layout.layout_empty, rv);
                                }

                                list_group.clear();
                                list_group.addAll(imFriendSearchBean.getData().getGroup());
                                if(list_group!=null&&list_group.size()!=0){
                                    imSearchGroupAdapter.notifyDataSetChanged();
                                }else {
                                    imSearchGroupAdapter.setEmptyView(R.layout.layout_empty, rv2);
                                }

                            }else {
                                imSearchAdapter.setEmptyView(R.layout.layout_empty, rv);
                                imSearchGroupAdapter.setEmptyView(R.layout.layout_empty, rv2);
                                list.clear();
                                list_group.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @OnClick({R.id.iv_bar_back,R.id.tv_more_friend,R.id.tv_more_group})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_more_friend:
                if (list!=null&&list.size()!=0){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", edtKeyword.getText().toString());
                    ActivityUtils.startActivity(bundle,IMFriendMoreActivity.class);
                }else {
                    ToastUtils.showShort(getString(R.string.im_no_friend));
                }
                break;
            case R.id.tv_more_group:
                if (list_group!=null&&list_group.size()!=0){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", edtKeyword.getText().toString());
                    ActivityUtils.startActivity(bundle,IMGroupMoreActivity.class);
                }else {
                    ToastUtils.showShort(getString(R.string.im_no_group));
                }
                break;
        }

    }
}
