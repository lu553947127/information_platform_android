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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.BankCardAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.BankCardVm;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        bankCardAdapter.addFooterView(foot);
        rv.setAdapter(bankCardAdapter);
        bankCardAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        bankCardVm = ViewModelProviders.of(this).get(BankCardVm.class);
        bankCardVm.bankcardLiveData.observe(this, bankCardBeans -> {
            bankCardAdapter.setNewData(bankCardBeans);
            if (bankCardBeans != null && bankCardBeans.size() >= 5){
                bankCardAdapter.removeAllFooterView();
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

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();};
}
