package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusAdapter;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.base.BaseLazyFragment;
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
public class ProjectReadFragment extends BaseLazyFragment {

    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.rv_locus)
    RecyclerView rvLocus;
    private ProjectDetailVm projectDetailVm;
    private LocusAdapter locusAdapter;

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
        rvLocus.setLayoutManager(new LinearLayoutManager(mContext));
        locusAdapter = new LocusAdapter(R.layout.item_locus, null){
            @Override
            public void readDetail(int position, String price) {

            }
        };
        locusAdapter.setEmptyView(R.layout.layout_loading_top, rvLocus);
        rvLocus.setAdapter(locusAdapter);

        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.introLiveData.observe(this, intro ->{
            tvDetail.setText(intro);
        });
        projectDetailVm.materialLiveData.observe(this, materials -> {
            tvMaterial.setText(materials);
        });

    }

    private void setEmpty() {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_view_locus));
        locusAdapter.setEmptyView(empty);
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm.getViewTrack();
        projectDetailVm.viewTrackLiveData.observe(this, trackBean -> {
            setEmpty();
            locusAdapter.setNewData(trackBean.getList());
            isInited = true;
        });
    }
}
