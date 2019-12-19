package com.shuangduan.zcy.view.mine.set;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.WxLoginEvent;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.LoginVm;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.wxapi.WeChatUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: ThirdLoginActivity
 * @Description: 第三方登录账号绑定
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/14 10:21
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/14 10:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ThirdLoginActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_wechat_circle)
    CircleImageView ivWechatCircle;
    @BindView(R.id.tv_wechat_name)
    TextView tvWechatName;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    private LoginVm loginVm;
    private int wechat_status;
    private String nickName;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_third_login;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText("社交账号绑定");
        loginVm = ViewModelProviders.of(this).get(LoginVm.class);

        //获取微信绑定信息返回结果
        loginVm.wxLoginLiveData.observe(this,wxUserInfoBean -> {
            wechat_status = wxUserInfoBean.getWechat_status();
            nickName = wxUserInfoBean.getNickname();
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(wechat_status ==0 ? "" : wxUserInfoBean.getHeadimgurl())
                    .placeholder(R.drawable.icon_set_wechat)
                    .errorPic(R.drawable.icon_set_wechat)
                    .imageView(ivWechatCircle)
                    .build());
            tvWechatName.setText(wechat_status ==0 || StringUtils.isTrimEmpty(nickName) ? "微信昵称" : nickName);
            tvStatus.setText(wechat_status ==0 ? "未绑定": "解绑");
        });

        //获取微信内部绑定后返回结果
        loginVm.wxLoginBindingLiveData.observe(this, item ->{
            loginVm.getWxStatus();
        });

        //获取微信解绑返回结果
        loginVm.wxLoginCloseLiveData.observe(this, item ->{
            loginVm.getWxStatus();
        });

        loginVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        loginVm.getWxStatus();
    }

    @OnClick({R.id.iv_bar_back, R.id.rl_wechat})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.rl_wechat://微信绑定/解绑
                switch (wechat_status){
                    case 0://未绑定
                        WeChatUtils.getWeChatLogin(this,"we_chat_set");
                        break;
                    case 1://解绑
                        new CustomDialog(this)
                                .setTip("确定要解除与微信账号"+ nickName +"的绑定吗？")
                                .setOk("确定")
                                .setCallBack(new BaseDialog.CallBack() {
                                    @Override
                                    public void cancel() {

                                    }

                                    @Override
                                    public void ok(String s) {
                                        loginVm.userWechatClose();
                                    }
                                }).showDialog();

                        break;
                }
                break;
        }
    }

    @Subscribe
    public void onEventWxLogin(WxLoginEvent event) {
        loginVm.userWechatBind(event.getCode());
    }
}
