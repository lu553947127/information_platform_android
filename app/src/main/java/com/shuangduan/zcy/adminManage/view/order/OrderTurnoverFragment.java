package com.shuangduan.zcy.adminManage.view.order;

import android.os.Bundle;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: OrderTurnoverFragment
 * @Description: 周转材料订单管理列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 13:30
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 13:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderTurnoverFragment extends BaseLazyFragment {


    public static OrderTurnoverFragment newInstance() {
        Bundle args = new Bundle();
        OrderTurnoverFragment fragment = new OrderTurnoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_turnover;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
    }

    @Override
    protected void initDataFromService() {

    }
}
