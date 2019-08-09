package com.shuangduan.zcy.view.projectinfo;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.LocusBean;
import com.shuangduan.zcy.model.bean.TrackBean;
import com.shuangduan.zcy.view.PhotoViewActivity;
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
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
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
        locusAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TrackBean.ListBean listBean = projectDetailVm.trackLiveData.getValue().getList().get(position);
            switch (view.getId()){
                case R.id.iv_pic_first:
                    showPic(listBean, 0, view);
                    break;
                case R.id.iv_pic_second:
                    showPic(listBean, 1, view);
                    break;
                case R.id.iv_pic_third:
                    showPic(listBean, 2, view);
                    break;
                case R.id.tv_more:
                    showPic(listBean, 2, view);
                    break;
            }
        });
        refresh.setOnLoadMoreListener(refreshLayout -> projectDetailVm.getMoreTract());

        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.introLiveData.observe(this, intro ->{
            tvDetail.setText(intro);
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
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_view_locus));
        locusAdapter.setEmptyView(empty);
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm.getViewTrack();
    }

    /**
     *  图片预览
     */
    private void showPic(TrackBean.ListBean item, int position, View view){
        ArrayList<String> list = new ArrayList<>();
        for (TrackBean.ListBean.ImageBean img: item.getImage()) {
            list.add(img.getSource());
        }

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putStringArrayList(CustomConfig.PHOTO_VIEW_URL_LIST, list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //共享shareElement这个View
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, "shareElement");
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class, activityOptionsCompat.toBundle());
        } else {
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class);
        }
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
}
