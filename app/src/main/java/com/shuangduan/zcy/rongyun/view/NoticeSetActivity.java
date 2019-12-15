package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.vm.MineSubVm;
import com.shuangduan.zcy.weight.SwitchView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.ORDER_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.SUBSCRIBE;
import static com.shuangduan.zcy.app.CustomConfig.UNUSED;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: NoticeSetActivity
 * @Description: 订阅通知推送设置
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/19 14:31
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/19 14:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NoticeSetActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sv)
    SwitchView switchView;
    private MineSubVm mineSubVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_notice_set;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        mineSubVm = ViewModelProviders.of(this).get(MineSubVm.class);

        switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE,0)){
            case SUBSCRIBE://订阅消息
                tvBarTitle.setText(getString(R.string.close_subscribe_message));
                mineSubVm.msgPush(1);
                break;
            case UNUSED://闲置提醒
                tvBarTitle.setText(getString(R.string.close_subscribe_message_group));
                mineSubVm.msgPush(2);
                break;
            case ORDER_TYPE://订单提醒
                tvBarTitle.setText(getString(R.string.close_subscribe_message_order));
                mineSubVm.msgPush(3);
                break;
        }

        //订阅消息开关状态返回结果
        mineSubVm.messagePushLiveData.observe(this,messagePushBean -> {
            if (messagePushBean.getStatus()==0){
                switchView.setOpened(true);
            }else {
                switchView.setOpened(false);
            }
        });

        //订阅消息开关状态修改返回结果
        mineSubVm.messagePushStatusLiveData.observe(this,item ->{

        });

        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                if (view.getId() == R.id.sv) {
                    switchView.setOpened(true);
                    switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE,0)){
                        case SUBSCRIBE://订阅消息
                            mineSubVm.msgPushStatus(1,0);
                            break;
                        case UNUSED://闲置提醒
                            mineSubVm.msgPushStatus(2,0);
                            break;
                        case ORDER_TYPE://订单提醒
                            mineSubVm.msgPushStatus(3,0);
                            break;
                    }
                }
            }

            @Override
            public void toggleToOff(SwitchView view) {
                if (view.getId() == R.id.sv) {
                    switchView.setOpened(false);
                    switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE,0)){
                        case SUBSCRIBE://订阅消息
                            mineSubVm.msgPushStatus(1,1);
                            break;
                        case UNUSED://闲置提醒
                            mineSubVm.msgPushStatus(2,1);
                            break;
                        case ORDER_TYPE://订单提醒
                            mineSubVm.msgPushStatus(3,1);
                            break;
                    }
                }
            }
        });
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
