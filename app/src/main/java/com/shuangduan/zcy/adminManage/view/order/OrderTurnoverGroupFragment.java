package com.shuangduan.zcy.adminManage.view.order;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view
 * @ClassName: OrderTurnoverGroupFragment
 * @Description: 周转材料订单管理列表(集团)
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 14:42
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 14:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderTurnoverGroupFragment extends BaseLazyFragment {

    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_grounding)
    AppCompatTextView tvGrounding;
    @BindView(R.id.tv_use_statue)
    AppCompatTextView tvUseStatue;
    @BindView(R.id.tv_depositing_place)
    AppCompatTextView tvDepositingPlace;

    public static OrderTurnoverGroupFragment newInstance() {
        Bundle args = new Bundle();
        OrderTurnoverGroupFragment fragment = new OrderTurnoverGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_turnover_group;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("材料名称");
        tvGrounding.setText("订单管理");
        tvUseStatue.setText("订单类型");
        tvDepositingPlace.setText("订单号");
    }

    @Override
    protected void initDataFromService() {

    }
}
