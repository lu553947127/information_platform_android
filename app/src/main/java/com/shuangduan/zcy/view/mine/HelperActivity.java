package com.shuangduan.zcy.view.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.HelpAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.HelpBean;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class describe  帮助
 * @time 2019/7/10 11:45
 * @change
 * @chang time
 * @class describe
 */
public class HelperActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    HelpAdapter adapter;
    List<HelpBean.DataBean.ListBean> list=new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_helper;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.helper));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        adapter = new HelpAdapter(HelperActivity.this,R.layout.item_help, list);
        adapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(adapter);
        refresh.setEnableLoadMore(false);
        refresh.setEnableRefresh(false);
        getHelp();
    }

    //帮助
    private void getHelp() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.USER_INFO_HELP)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
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
                            HelpBean bean=new Gson().fromJson(response.body(), HelpBean.class);
                            if (bean.getCode().equals("200")){
                                list.clear();
                                list.addAll(bean.getData().getList());
                                if (list!=null&&list.size()!=0){
                                    adapter.notifyDataSetChanged();
                                }else {
                                    adapter.setEmptyView(R.layout.layout_empty, recyclerView);
                                }
                                String str="关于易基建平台的<font color=\"#6a5ff8\">"+bean.getData().getCount()+"</font>个问题";
                                textView.setText(Html.fromHtml(str));
                            }else if (bean.getCode().equals("-1")){
                                ToastUtils.showShort(bean.getMsg());
                                LoginUtils.getExitLogin();
                            }else {
                                adapter.setEmptyView(R.layout.layout_empty, recyclerView);
                                list.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){
        finish();
    }
}
