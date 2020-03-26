package com.shuangduan.zcy.view.mine.wallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.BankCardAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.event.BankcardUpdateEvent;
import com.shuangduan.zcy.vm.BankCardVm;
import com.zcy.framelibrary.dialog.AlertDialog;

import org.greenrobot.eventbus.Subscribe;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 银行卡列表
 * @time 2019/8/6 16:48
 * @change
 * @chang time
 * @class describe
 */
@SuppressLint("InflateParams")
public class BankCardListActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private BankCardVm bankCardVm;

    private AlertDialog dialog;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_bank_card_list;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.bank_card));


        //银行卡列表尾部局（添加银行卡按钮）
        View foot = LayoutInflater.from(this).inflate(R.layout.footer_back_card, null);
        foot.setOnClickListener(v -> ActivityUtils.startActivity(BindBankCardActivity.class));

        rv.setLayoutManager(new LinearLayoutManager(this));
        BankCardAdapter bankCardAdapter = new BankCardAdapter(R.layout.item_bank_card, null);
        rv.setAdapter(bankCardAdapter);
        bankCardAdapter.setOnItemClickListener((adapter, view, position) -> {
            BankCardBean bankCardBean = bankCardAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.BANKCARD_ID, bankCardBean.getId());
            bundle.putString(CustomConfig.BANKCARD_NAME, bankCardBean.getType_name());
            ActivityUtils.startActivity(bundle, UnbindBankCardActivity.class);
        });
        bankCardVm = getViewModel(BankCardVm.class);
        bankCardVm.bankcardLiveData.observe(this, bankCardBeans -> {
            bankCardAdapter.setNewData(bankCardBeans);
            bankCardAdapter.removeAllFooterView();
            if (bankCardBeans != null && bankCardBeans.size() < 5) {
                bankCardAdapter.addFooterView(foot);
            }
        });
        bankCardVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        bankCardVm.bankcardList();



        if (SPUtils.getInstance().getInt(SpConfig.BANK_AGREEMENT, 0) == 0) {
            String agreement = getTxtFromAssets("bank_agreement.txt");
            dialog = new AlertDialog.Builder(this)
                    .setView(R.layout.dialog_bank_agreement)
                    .setCancelable(false)
                    .setText(R.id.tv_content, Html.fromHtml(agreement))
                    .setOnCheckedChangeListener(R.id.checkbox, (buttonView, isChecked) -> {
                        TextView positiveView = dialog.getView(R.id.tv_positive);
                        if (buttonView.isChecked()) {
                            positiveView.setEnabled(true);
                            return;
                        }
                        positiveView.setEnabled(false);
                    })
                    .setOnClickListener(R.id.tv_negative, v -> {
                        dialog.dismiss();
                        BankCardListActivity.this.finish();
                    })
                    .setOnClickListener(R.id.tv_positive, v -> {
                        SPUtils.getInstance().put(SpConfig.BANK_AGREEMENT, 1);
                        dialog.dismiss();
                    })
                    .show();
        }

    }

    private String getTxtFromAssets(String fileName) {
        String result = "";
        try {
            InputStream is = getAssets().open(fileName);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            result = new String(buffer, "utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }

    @Subscribe
    public void onEventUpdateBankcard(BankcardUpdateEvent event) {
        bankCardVm.bankcardList();
    }
}
