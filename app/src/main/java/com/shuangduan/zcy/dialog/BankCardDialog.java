package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.BankCardDialogAdapter;
import com.shuangduan.zcy.model.bean.BankCardBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.dialog
 * @class describe  银行卡选择弹窗
 * @time 2019/8/6 15:41
 * @change
 * @chang time
 * @class describe
 */
public class BankCardDialog extends BaseDialog {
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<BankCardBean> list;

    public BankCardDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_bank_card;
    }

    @Override
    void initData() {
        DialogUtils.enterBottomAnim(this);
    }

    @Override
    void initEvent() {
        rv.setLayoutManager(new LinearLayoutManager(mActivity));
        rv.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        BankCardDialogAdapter bankCardDialogAdapter = new BankCardDialogAdapter(R.layout.item_bank_card_dialog, list);
        rv.setAdapter(bankCardDialogAdapter);
        bankCardDialogAdapter.setOnItemClickListener((adapter, view, position) -> {
            BankCardBean bankCardBean = bankCardDialogAdapter.getData().get(position);
            if (singleCallBack != null){
                singleCallBack.click(bankCardBean.getType_name(), position);
            }
        });
    }

    public BankCardDialog setList(List<BankCardBean> list) {
        if (this.list == null){
            this.list = new ArrayList<>();
        }
        this.list.addAll(list);
        return this;
    }

    @OnClick(R.id.tv_cancel)
    void onClick(){hideDialog();}
}
