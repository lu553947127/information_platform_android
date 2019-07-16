package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ConsumptionAdapter;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.bean.ConsumptionBean;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  消费列表
 * @time 2019/7/15 15:36
 * @change
 * @chang time
 * @class describe
 */
public class ProjectConsumptionFragment extends BaseFragment {
    @BindView(R.id.rv_consumption)
    RecyclerView rvConsumption;

    public static ProjectConsumptionFragment newInstance() {

        Bundle args = new Bundle();

        ProjectConsumptionFragment fragment = new ProjectConsumptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_consumption;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        List<ConsumptionBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new ConsumptionBean());
        }
        rvConsumption.setLayoutManager(new LinearLayoutManager(mContext));
        rvConsumption.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ConsumptionAdapter consumptionAdapter = new ConsumptionAdapter(R.layout.item_consumption, list);
        rvConsumption.setAdapter(consumptionAdapter);
    }

    @Override
    protected void initDataFromService() {

    }
}
