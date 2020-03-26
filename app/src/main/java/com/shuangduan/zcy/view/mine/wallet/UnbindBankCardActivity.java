package com.shuangduan.zcy.view.mine.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.BankcardUpdateEvent;
import com.shuangduan.zcy.vm.BankCardVm;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 解绑银行卡
 * @time 2019/8/8 14:12
 * @change
 * @chang time
 * @class describe
 */
public class UnbindBankCardActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bankcard_name)
    TextView tvBankcardName;
    private BankCardVm bankCardVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_unbind_bankcard;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.unbind));

        tvBankcardName.setText(getIntent().getStringExtra(CustomConfig.BANKCARD_NAME));
        bankCardVm = getViewModel(BankCardVm.class);
        bankCardVm.unbindLiveData.observe(this, o -> {
            ToastUtils.showShort("解绑成功");
            EventBus.getDefault().post(new BankcardUpdateEvent());
            finish();
        });
        bankCardVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_unbind})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_unbind:
                new CustomDialog(this)
                        .setTip("解除绑定后银行服务不可用，包括支付功能")
                        .setOk("确定解除")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                bankCardVm.unbindBankcard(getIntent().getIntExtra(CustomConfig.BANKCARD_ID, 0));
                            }
                        }).showDialog();
                break;
        }
    }

}
