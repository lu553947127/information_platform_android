package com.shuangduan.zcy.rongyun.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMSearchAdapter;
import com.shuangduan.zcy.adapter.IMSearchGroupAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

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

        IMAddVm imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IMSearchAdapter imSearchAdapter = new IMSearchAdapter(R.layout.item_im_search, null);
        imSearchAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(imSearchAdapter);

        rv2.setLayoutManager(new LinearLayoutManager(this));
        rv2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IMSearchGroupAdapter imSearchGroupAdapter = new IMSearchGroupAdapter(R.layout.item_im_search_group, null);
        imSearchGroupAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv2.setAdapter(imSearchGroupAdapter);

        imAddVm.searchLiveData.observe(this,imFriendSearchBean -> {
            imSearchAdapter.setKeyword(edtKeyword.getText().toString());
            imSearchAdapter.setNewData(imFriendSearchBean.getFriend());
            imSearchGroupAdapter.setKeyword(edtKeyword.getText().toString());
            imSearchGroupAdapter.setNewData(imFriendSearchBean.getGroup());

            if(imFriendSearchBean.getFriend().size()!=0){
                rv.setVisibility(View.VISIBLE);
                if (imFriendSearchBean.getFriend().size()>2){
                    tvFriend.setVisibility(View.VISIBLE);
                }else {
                    tvFriend.setVisibility(View.GONE);
                }
            }else {
                rv.setVisibility(View.GONE);
                tvFriend.setVisibility(View.GONE);
            }

            if(imFriendSearchBean.getGroup().size()!=0){
                rv2.setVisibility(View.VISIBLE);
                if (imFriendSearchBean.getGroup().size()>2){
                    tvGroup.setVisibility(View.VISIBLE);
                }else {
                    tvGroup.setVisibility(View.GONE);
                }
            }else {
                rv2.setVisibility(View.GONE);
                tvGroup.setVisibility(View.GONE);
            }
        });

        imSearchAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMFriendSearchBean.FriendBean listBean = imSearchAdapter.getData().get(position);
            RongIM.getInstance().startPrivateChat(IMSearchActivity.this, listBean.getUserId(), listBean.getName());
        });
        imSearchGroupAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMFriendSearchBean.GroupBean listBean = imSearchGroupAdapter.getData().get(position);
            RongIM.getInstance().startGroupChat(IMSearchActivity.this, listBean.getGroup_id(), listBean.getGroup_name());
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
                imAddVm.search(edtKeyword.getText().toString());
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
                imAddVm.search(edtKeyword.getText().toString());
                return true;
            }
            return false;
        });
        imAddVm.search(edtKeyword.getText().toString());
    }

    @OnClick({R.id.iv_bar_back,R.id.tv_more_friend,R.id.tv_more_group})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_more_friend:
                bundle.putString("name", edtKeyword.getText().toString());
                ActivityUtils.startActivity(bundle,IMFriendMoreActivity.class);
                break;
            case R.id.tv_more_group:
                bundle.putString("name", edtKeyword.getText().toString());
                ActivityUtils.startActivity(bundle,IMGroupMoreActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyboardUtil.showSoftInputFromWindow(this,edtKeyword);
    }
}
