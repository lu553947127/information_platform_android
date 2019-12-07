package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusImageAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.TrackDateilBean;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MineLocusAcitivty$
 * @class 我的工程 工程信息动态详情页
 * @time 2019/10/20 15:08
 * @change
 * @class describe
 */
public class MineLocusActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_visit)
    TextView tvVisit;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;

    @BindView(R.id.ll_reject)
    LinearLayout llReject;
    @BindView(R.id.tv_reject_intro)
    TextView tvRejectIntro;

    private ProjectDetailVm projectDetailVm;
    private TrackDateilBean item;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_locus;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.project_locus));
        projectDetailVm = ViewModelProviders.of(this).get(ProjectDetailVm.class);
        projectDetailVm.init(getIntent().getIntExtra(CustomConfig.DYNAMICS_ID, 0));
        projectDetailVm.getMyTrackDateil();

        projectDetailVm.trackDateilLiveDate.observe(this, item -> {
            this.item = item;
            tvTitle.setText(item.title);
            tvDate.setText(getString(R.string.format_release_time, item.createTime));
            tvVisit.setText(getString(R.string.format_locus_visit, item.name));
            tvTel.setText(getString(R.string.format_contacts_phone, item.tel));
            tvRemarks.setText(item.remarks);

            if (!StringUtils.isEmpty(item.rejectReason)) {
                llReject.setVisibility(View.VISIBLE);
                tvRejectIntro.setText(item.rejectReason);
            }

            rvImage.setLayoutManager(new GridLayoutManager(this, 3,RecyclerView.VERTICAL, false));
            rvImage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15_15));
            LocusImageAdapter adapter = new LocusImageAdapter(R.layout.item_locus_image, null);
            adapter.setOnItemChildClickListener(this);
            rvImage.setAdapter(adapter);
            adapter.setNewData(item.imageJson);


            switch (item.status) {
                case 0:
                    tvTitle.setTextColor(getResources().getColor(R.color.color_646464));
                    tvTitle.setEnabled(false);
                    ivState.setImageResource(R.drawable.icon_review);
                    break;
                case 1:
                    tvTitle.setTextColor(getResources().getColor(R.color.text1));
                    ivState.setImageResource(R.drawable.icon_pass);
                    break;
                case 2:
                    tvTitle.setEnabled(false);
                    tvTitle.setTextColor(getResources().getColor(R.color.color_646464));
                    ivState.setImageResource(R.drawable.icon_reject);
                    break;
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<String> images = new ArrayList<>();
        for(TrackDateilBean.ImageJson imageJson:item.imageJson){
            images.add(imageJson.source);
        }
        PictureEnlargeUtils.getPictureEnlargeList(this,images,position);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_title})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_title:
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.PROJECT_ID, item.projectId);
                bundle.putInt(CustomConfig.LOCATION, 1);
                ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
                break;
        }
    }
}
