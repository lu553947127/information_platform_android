package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMGroupInfoAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMGroupInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class 群聊详情人员列表
 * @time 2019/9/11 10:12
 * @change
 * @chang time
 * @class describe
 */

public class IMGroupMembersActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private String group_id;
    IMGroupInfoBean imGroupInfoBean;
    IMGroupInfoAdapter imGroupInfoAdapter;
    List<IMGroupInfoBean.DataBean.ListBean> list=new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_group_members;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.im_group_group_members));
        group_id=getIntent().getStringExtra("group_id");

        recyclerView.setLayoutManager(new GridLayoutManager(IMGroupMembersActivity.this, 5));
        imGroupInfoAdapter = new IMGroupInfoAdapter(R.layout.adapter_im_group_info, list);
        imGroupInfoAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(imGroupInfoAdapter);
        getGroupInfo();
    }

    //群聊详情
    private void getGroupInfo() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_GROUP)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("group_id",group_id)
                .params("page","1")
                .params("pageSize","15")
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.i(response.body());
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.i(response.body());
                        try {
                            imGroupInfoBean=new Gson().fromJson(response.body(), IMGroupInfoBean.class);
                            if (imGroupInfoBean.getCode().equals("200")){
                                list.clear();
                                list.addAll(imGroupInfoBean.getData().getList());
                                if (list!=null&&list.size()!=0){
                                    imGroupInfoAdapter.notifyDataSetChanged();
                                }else {
                                    imGroupInfoAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
                                }
                            }else {
                                imGroupInfoAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
                                list.clear();
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

}
