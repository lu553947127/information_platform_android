package com.shuangduan.zcy.view.recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.RechargeShowAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.RechargeShowBean;
import com.shuangduan.zcy.view.PayActivity;
import com.shuangduan.zcy.vm.RechargeVm;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recharge
 * @class describe  充值中心
 * @time 2019/8/13 14:19
 * @change
 * @chang time
 * @class describe
 */
public class RechargeActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.rv)
    RecyclerView rv;
    private RechargeVm rechargeVm;
    private RechargeShowAdapter rechargeShowAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recharge;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.recharge_center));
        tvBarRight.setText(getString(R.string.recharge_record));

        rechargeVm = ViewModelProviders.of(this).get(RechargeVm.class);
        rechargeVm.showLiveData.observe(this, rechargeShowBean -> {
            tvMobile.setText(rechargeShowBean.getTel());
            if (rechargeShowAdapter == null){
                rv.setLayoutManager(new GridLayoutManager(this, 3));
                rechargeShowAdapter = new RechargeShowAdapter(R.layout.item_recharge_show, rechargeShowBean.getList());
                rv.setAdapter(rechargeShowAdapter);
                rechargeShowAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (rechargeVm.positionLiveData.getValue() == position) return;
                    if (rechargeVm.positionLiveData.getValue() > -1){
                        List<RechargeShowBean.ListBean> data = rechargeShowAdapter.getData();
                        RechargeShowBean.ListBean listBean = data.get(rechargeVm.positionLiveData.getValue());
                        listBean.setIsSelect(0);
                        rechargeShowAdapter.notifyItemChanged(rechargeVm.positionLiveData.getValue());
                    }
                    rechargeVm.positionLiveData.postValue(position);
                });
            }else {
                rechargeShowAdapter.setNewData(rechargeShowBean.getList());
            }
        });
        rechargeVm.positionLiveData.observe(this, integer -> {
            if (integer > -1){
                RechargeShowBean.ListBean listBean = rechargeShowAdapter.getData().get(integer);
                listBean.setIsSelect(1);
                rechargeShowAdapter.notifyItemChanged(integer);
            }
        });
        rechargeVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        rechargeVm.getShowData();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                ActivityUtils.startActivity(RechargeRecordActivity.class);
                break;
            case R.id.tv_confirm:
                Integer position = rechargeVm.positionLiveData.getValue();
                if (position < 0){
                    ToastUtils.showShort(getString(R.string.select_recharge_amount));
                    return;
                }
                String price = rechargeShowAdapter.getData().get(position).getPrice();
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.RECHARGE_AMOUNT, price);
                ActivityUtils.startActivity(bundle, PayActivity.class);
                break;
        }
    }
}
