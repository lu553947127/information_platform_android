package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ReadHistoryAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.ProjectSubBean;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
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
public class ProjectHistoryFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    private ReadHistoryVm readHistoryVm;

    public static ProjectHistoryFragment newInstance() {

        Bundle args = new Bundle();

        ProjectHistoryFragment fragment = new ProjectHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_read_history;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ReadHistoryAdapter readHistoryAdapter = new ReadHistoryAdapter(R.layout.item_read_history, null);
        readHistoryAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(readHistoryAdapter);
        readHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            ReadHistoryBean listBean = readHistoryAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getId());
            bundle.putInt(CustomConfig.LOCATION, 0);
            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
        });

        readHistoryVm = ViewModelProviders.of(this).get(ReadHistoryVm.class);
        readHistoryVm.projectHistoryLiveData.observe(this, readHistoryBeans -> {
            isInited = true;
            readHistoryAdapter.setNewData(readHistoryBeans);
            readHistoryAdapter.setEmptyView(R.layout.layout_empty_top, rv);
        });
    }

    @Override
    protected void initDataFromService() {
        readHistoryVm.getProjectHistory();
    }
}
