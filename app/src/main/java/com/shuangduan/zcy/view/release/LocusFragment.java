package com.shuangduan.zcy.view.release;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusMineAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  我发布的轨迹信息
 * @time 2019/7/16 20:24
 * @change
 * @chang time
 * @class describe
 */
public class LocusFragment extends BaseLazyFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    public static LocusFragment newInstance() {

        Bundle args = new Bundle();

        LocusFragment fragment = new LocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_info;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        refresh.setEnableRefresh(false);
        refresh.setEnableLoadMore(false);

        List<LocusMineBean> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(new LocusMineBean());
        }
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        LocusMineAdapter adapter = new LocusMineAdapter(R.layout.item_mine_locus, list);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initDataFromService() {

    }
}
