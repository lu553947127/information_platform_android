package com.shuangduan.zcy.view.mine;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ReadHistoryAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.vm.ReadHistoryVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe
 * @time 2019/8/5 13:39
 * @change
 * @chang time
 * @class describe
 */
public class RecruitHistoryFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    private ReadHistoryVm readHistoryVm;

    public static RecruitHistoryFragment newInstance() {

        Bundle args = new Bundle();

        RecruitHistoryFragment fragment = new RecruitHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_read_history;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ReadHistoryAdapter readHistoryAdapter = new ReadHistoryAdapter(R.layout.item_read_history, null);
        readHistoryAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(readHistoryAdapter);

        readHistoryVm = ViewModelProviders.of(this).get(ReadHistoryVm.class);
        readHistoryVm.readHistoryBeanMutableLiveData.observe(this, readHistoryBeans -> {
            isInited = true;
            readHistoryAdapter.setEmptyView(R.layout.layout_empty_top, rv);
            readHistoryAdapter.setNewData(readHistoryBeans);
        });
    }

    @Override
    protected void initDataFromService() {
        readHistoryVm.getRecruitHistory();
    }
}
