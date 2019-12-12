package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusReadAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.model.event.RefreshViewLocusEvent;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.weight.RichText;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
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
    RichText tvDetail;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv_locus)
    RecyclerView rvLocus;
    private ProjectDetailVm projectDetailVm;
    private LocusReadAdapter locusAdapter;

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
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rvLocus.setLayoutManager(new LinearLayoutManager(mContext));
        locusAdapter = new LocusReadAdapter(R.layout.item_locus, null);
        locusAdapter.setEmptyView(R.layout.layout_loading_top, rvLocus);
        rvLocus.setAdapter(locusAdapter);
        locusAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TrackBean.ListBean listBean = locusAdapter.getData().get(position);
            ArrayList<String> list = new ArrayList<>();
            for (TrackBean.ListBean.ImageBean img: listBean.getImage()) {
                list.add(img.getSource());
            }
            switch (view.getId()){
                case R.id.iv_pic_first:
                    PictureEnlargeUtils.getPictureEnlargeList(getActivity(),list,0);
                    break;
                case R.id.iv_pic_second:
                    PictureEnlargeUtils.getPictureEnlargeList(getActivity(),list,1);
                    break;
                case R.id.iv_pic_third:
                case R.id.tv_more:
                    PictureEnlargeUtils.getPictureEnlargeList(getActivity(),list,2);
                    break;
                case R.id.iv_mark:
                    if (listBean.getUser_id()!= SPUtils.getInstance().getInt(SpConfig.USER_ID)){
                        Bundle bundle = new Bundle();
                        bundle.putInt(CustomConfig.UID, listBean.getUser_id());
                        ActivityUtils.startActivity(bundle, LocusOwnerDetailActivity.class);
                    }
                    break;
            }
        });
        refresh.setOnLoadMoreListener(refreshLayout -> projectDetailVm.getMoreTract());

        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.introLiveData.observe(this, intro ->{
            tvDetail.setGlide(Glide.with(this));
            tvDetail.setHtml(intro);
        });
        projectDetailVm.materialLiveData.observe(this, materials -> {
            tvMaterial.setText(materials);
        });
        projectDetailVm.viewTrackLiveData.observe(this, trackBean -> {
            isInited = true;
            if (trackBean.getPage() == 1) {
                locusAdapter.setNewData(trackBean.getList());
                setEmpty();
            }else {
                locusAdapter.addData(trackBean.getList());
            }
            setNoMore(trackBean.getPage(), trackBean.getCount());
        });
    }

    private void setEmpty() {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_top, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_view_locus));
        locusAdapter.setEmptyView(empty);
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm.getViewTrack();
    }

    private void setNoMore(int page, int count){
        if (page == 1){
            if (page * 10 >= count){
                if (refresh.getState() == RefreshState.None){
                    refresh.setNoMoreData(true);
                }else {
                    refresh.finishRefreshWithNoMoreData();
                }
            }else {
                refresh.finishRefresh();
            }
        }else {
            if (page * 10 >= count){
                refresh.finishLoadMoreWithNoMoreData();
            }else {
                refresh.finishLoadMore();
            }
        }
    }

    @Subscribe
    public void onEventPayDone(RefreshViewLocusEvent event){
        projectDetailVm.getViewTrack();
    }
}
