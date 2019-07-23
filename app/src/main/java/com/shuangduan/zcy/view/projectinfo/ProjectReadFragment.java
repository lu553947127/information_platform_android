package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusAdapter;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.bean.LocusBean;
import com.shuangduan.zcy.vm.ProjectDetailVm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  已查看
 * @time 2019/7/15 15:33
 * @change
 * @chang time
 * @class describe
 */
public class ProjectReadFragment extends BaseFragment {

    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.rv_locus)
    RecyclerView rvLocus;
    private ProjectDetailVm projectDetailVm;

    public static ProjectReadFragment newInstance() {

        Bundle args = new Bundle();

        ProjectReadFragment fragment = new ProjectReadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_read;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.getViewTrack();
        projectDetailVm.viewTrackLiveData.observe(this, viewTrackBeans -> {

        });

        tvDetail.setText("项目为政府规划用地，占地11230㎡，项目为政府规划用地，占地...");
        tvMaterial.setText("门窗玻璃、外墙装饰、防水防腐、油...");

        rvLocus.setLayoutManager(new LinearLayoutManager(mContext));
        LocusAdapter locusAdapter = new LocusAdapter(R.layout.item_locus, null){
            @Override
            public void readDetail(int position, String price) {

            }
        };
        rvLocus.setAdapter(locusAdapter);
    }

    @Override
    protected void initDataFromService() {

    }
}
