package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ConsumptionAdapter;
import com.shuangduan.zcy.adapter.LocusAdapter;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.ConsumptionBean;
import com.shuangduan.zcy.vm.ProjectDetailVm;
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
public class ProjectConsumptionFragment extends BaseLazyFragment {
    @BindView(R.id.rv_consumption)
    RecyclerView rvConsumption;
    private ProjectDetailVm projectDetailVm;
    private ConsumptionAdapter consumptionAdapter;

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
        rvConsumption.setLayoutManager(new LinearLayoutManager(mContext));
        rvConsumption.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        consumptionAdapter = new ConsumptionAdapter(R.layout.item_consumption, null);
        consumptionAdapter.setEmptyView(R.layout.layout_loading_top, rvConsumption);
        rvConsumption.setAdapter(consumptionAdapter);
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.getConsume();
        projectDetailVm.consumeLiveData.observe(this, consumeBean -> {
            setEmpty(consumptionAdapter);
            consumptionAdapter.setNewData(consumeBean.getList());
            isInited = true;
        });
    }

    private void setEmpty(ConsumptionAdapter consumptionAdapter) {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_top, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_consume));
        consumptionAdapter.setEmptyView(empty);
    }

}
