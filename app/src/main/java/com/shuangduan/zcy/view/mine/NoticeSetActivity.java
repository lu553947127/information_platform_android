package com.shuangduan.zcy.view.mine;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.weight.SwitchView;

import butterknife.BindView;

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
        switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE,0)){
            case SUBSCRIBE://订阅消息
                tvBarTitle.setText(getString(R.string.close_subscribe_message));
                break;
            case UNUSED://闲置提醒
                tvBarTitle.setText(getString(R.string.close_subscribe_message_group));
                break;
        }

        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                if (view.getId() == R.id.sv) {
                    switchView.setOpened(true);
                }
            }

            @Override
            public void toggleToOff(SwitchView view) {
                if (view.getId() == R.id.sv) {
                    switchView.setOpened(false);
                }
            }
        });
    }
}
