package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.BankCardAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.event.BankcardUpdateEvent;
import com.shuangduan.zcy.vm.BankCardVm;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  银行卡列表
 * @time 2019/8/6 16:48
 * @change
 * @chang time
 * @class describe
 */
public class BankCardListActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private BankCardVm bankCardVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_bank_card_list;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.bank_card));

        View foot = LayoutInflater.from(this).inflate(R.layout.footer_back_card, null);
        foot.setOnClickListener(v -> {
            ActivityUtils.startActivity(BindBankCardActivity.class);
        });
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
        bankCardVm = ViewModelProviders.of(this).get(BankCardVm.class);
        bankCardVm.bankcardLiveData.observe(this, bankCardBeans -> {
            bankCardAdapter.setNewData(bankCardBeans);
            bankCardAdapter.removeAllFooterView();
            if (bankCardBeans != null && bankCardBeans.size() < 5){
                bankCardAdapter.addFooterView(foot);
            }
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
        bankCardVm.bankcardList();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}

    @Subscribe
    public void onEventUpdateBankcard(BankcardUpdateEvent event){
        bankCardVm.bankcardList();
    }
}
