package com.shuangduan.zcy.view.mine.history;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ReadHistoryAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;
import com.shuangduan.zcy.view.recruit.RecruitActivity;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.vm.ReadHistoryVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 查看记录-招采信息
 * @time 2019/8/5 13:39
 * @change
 * @chang time
 * @class describe
 */
public class RecruitHistoryFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {
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
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_get_recruit_info, R.string.to_look_over, this);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ReadHistoryAdapter readHistoryAdapter = new ReadHistoryAdapter(R.layout.item_read_history, null);
        readHistoryAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(readHistoryAdapter);
        readHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            ReadHistoryBean listBean = readHistoryAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.RECRUIT_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, RecruitDetailActivity.class);
        });

        readHistoryVm = ViewModelProviders.of(this).get(ReadHistoryVm.class);
        readHistoryVm.recruitHistoryLiveData.observe(this, readHistoryBeans -> {
            isInited = true;
            readHistoryAdapter.setNewData(readHistoryBeans);
            readHistoryAdapter.setEmptyView(emptyView);
        });
    }

    @Override
    protected void initDataFromService() {
        readHistoryVm.getRecruitHistory();
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(RecruitActivity.class);
    }
}
