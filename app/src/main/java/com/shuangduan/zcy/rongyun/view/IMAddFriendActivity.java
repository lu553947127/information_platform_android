package com.shuangduan.zcy.rongyun.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMFriendOperationBean;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  融云加好友
 * @time 2019/8/29 16:17
 * @change
 * @chang time
 * @class describe
 */
public class IMAddFriendActivity extends BaseActivity {

    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.edt_msg)
    EditText edtMsg;

    private String id,name,msg,image;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_add_friend;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
        int friend_data = getIntent().getIntExtra(CustomConfig.FRIEND_DATA, 0);
        if (friend_data==3){
            tvBarTitle.setText(getString(R.string.refuse_friends));
        }else {
            tvBarTitle.setText(getString(R.string.add_friends));
        }

        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
        name=bundle.getString("name");
        msg=bundle.getString("msg");
        image=bundle.getString("image");

        tvName.setText(name);
        tvContent.setText(msg);
        ImageLoader.load(this, new ImageConfig.Builder()
                .url(image)
                .imageView(ivHeader)
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .build());

    }

    //好友添加/拒绝验证
    private void getFriendOperation(String user_id,String status,String msg) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.FRIEND_OPERATION)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .params("id",user_id)//接受/拒绝用户编号
                .params("status",status)//接受/拒绝
                .params("msg",msg)//接受/拒绝原因
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
                            IMFriendOperationBean bean=gson.fromJson(response.body(), IMFriendOperationBean.class);
                            Log.e("TAG","bean.getCode()"+bean.getCode());
                            if (bean.getCode().equals("200")){
                                finish();
                            }else {
                                ToastUtils.showShort(getString(R.string.request_error));
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                getFriendOperation(id,"3",edtMsg.getText().toString());
                break;
        }
    }
}
