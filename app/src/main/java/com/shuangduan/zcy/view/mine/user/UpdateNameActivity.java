package com.shuangduan.zcy.view.mine.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.vm.UserInfoVm;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  更新用户名
 * @time 2019/7/8 10:30
 * @change
 * @chang time
 * @class describe
 */
public class UpdateNameActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_name)
    EditText edtName;
    private UserInfoVm userInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_name;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.name));

        String username = SPUtils.getInstance().getString(SpConfig.USERNAME);
        edtName.setText(username);

        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.infoLiveData.observe(this, s -> {
            SPUtils.getInstance().put(SpConfig.USERNAME, edtName.getText().toString());
            EventBus.getDefault().post(new UserNameEvent(edtName.getText().toString()));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyboardUtil.showSoftInputFromWindow(this, edtName);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_save})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_save:
                String username = edtName.getText().toString();
                if (StringUtils.isTrimEmpty(username)) {
                    ToastUtils.showShort(getString(R.string.hint_real_name));
                    return;
                }
                userInfoVm.updateUserName(username);
                break;
        }
    }
}
