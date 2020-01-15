package com.shuangduan.zcy.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.RankActiveAdapter;
import com.shuangduan.zcy.adapter.RankRecommendAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.bean.RankListBean;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.income.IncomePeopleActivity;
import com.shuangduan.zcy.vm.IncomePeopleVm;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name PeopleFragment
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class 榜单
 * @time 2019/7/5 13:29
 * @change
 * @chang time
 * @class describe
 */
@SuppressLint("SetTextI18n")
public class PeopleFragment extends BaseFragment {
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_third)
    TextView tvThird;
    @BindView(R.id.tv_first_name)
    TextView tvFirstName;
    @BindView(R.id.tv_second_name)
    TextView tvSecondName;
    @BindView(R.id.tv_third_name)
    TextView tvThirdName;
    @BindView(R.id.cv_first)
    CircleImageView cvFirst;
    @BindView(R.id.cv_second)
    CircleImageView cvSecond;
    @BindView(R.id.cv_third)
    CircleImageView cvThird;
    @BindView(R.id.rl_places)
    RelativeLayout rlPlaces;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.rv_active)
    RecyclerView recyclerViewActive;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private IncomePeopleVm incomePeopleVm;
    private RankActiveAdapter rankActiveAdapter;
    private RankRecommendAdapter rankRecommendAdapter;
    private List<RankListBean.ListBean> rankList = new ArrayList<>();
    private RankListBean rankListBean;
    private int type = 1;

    public static PeopleFragment newInstance() {
        Bundle args = new Bundle();
        PeopleFragment fragment = new PeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_people;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View view) {
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
        tvBarTitle.setText(getString(R.string.people));
        incomePeopleVm = ViewModelProviders.of(this).get(IncomePeopleVm.class);

        recyclerViewActive.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewActive.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        rankActiveAdapter = new RankActiveAdapter(R.layout.adapter_rank_active, null);
        rankActiveAdapter.setEmptyView(R.layout.layout_loading, recyclerViewActive);
        recyclerViewActive.setAdapter(rankActiveAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        rankRecommendAdapter = new RankRecommendAdapter(R.layout.adapter_rank_recomend, null);
        rankRecommendAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(rankRecommendAdapter);

        //获取榜单列表返回数据
        incomePeopleVm.rankListLiveData.observe(this,rankListBean -> {
            this.rankListBean = rankListBean;
            switch (type){
                case 1://活跃榜
                    getTopRank("条");
                    rankList.clear();
                    rankList.addAll(rankListBean.getList());
                    if (rankList.size()>7) rankList.subList(0, 3).clear();
                    rankActiveAdapter.setNewData(rankList);
                    break;
                case 2://推荐榜
                    getTopRank("人");
                    break;
                case 3://成交榜
                    rankRecommendAdapter.setNewData(rankListBean.getList());
                    break;
            }
        });
    }

    @Override
    protected void initDataFromService() {
        switch (type){
            case 1://活跃榜
                getActiveData();
                break;
            case 2://推荐榜
                getRecommendData();
                break;
            case 3://成交榜
                getDealData();
                break;
        }
    }

    @OnClick({R.id.tv_first,R.id.tv_second,R.id.tv_third,R.id.tv_first_degree, R.id.tv_second_degree, R.id.tv_three_degree})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.tv_first://活跃榜
                type = 1;
                getActiveData();
                break;
            case R.id.tv_second://推荐榜
                type = 2;
                getRecommendData();
                break;
            case R.id.tv_third://成交榜
                type = 3;
                getDealData();
                break;
            case R.id.tv_first_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.FIRST_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
            case R.id.tv_second_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.SECOND_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
            case R.id.tv_three_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.THREE_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
        }
    }

    //活跃榜
    private void getActiveData() {
        tvFirst.setTextColor(getResources().getColor(R.color.colorFFF));
        tvFirst.setBackgroundResource(R.drawable.selector_btn_confirm);
        tvSecond.setTextColor(getResources().getColor(R.color.color_666666));
        tvSecond.setBackgroundResource(R.drawable.selector_btn_confirm_gray);
        tvThird.setTextColor(getResources().getColor(R.color.color_666666));
        tvThird.setBackgroundResource(R.drawable.selector_btn_confirm_gray);
        rlPlaces.setVisibility(View.VISIBLE);
        llRecommend.setVisibility(View.GONE);
        recyclerViewActive.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        incomePeopleVm.honorList(type);
    }

    //推荐榜
    private void getRecommendData() {
        tvFirst.setTextColor(getResources().getColor(R.color.color_666666));
        tvFirst.setBackgroundResource(R.drawable.selector_btn_confirm_gray);
        tvSecond.setTextColor(getResources().getColor(R.color.colorFFF));
        tvSecond.setBackgroundResource(R.drawable.selector_btn_confirm);
        tvThird.setTextColor(getResources().getColor(R.color.color_666666));
        tvThird.setBackgroundResource(R.drawable.selector_btn_confirm_gray);
        rlPlaces.setVisibility(View.VISIBLE);
        llRecommend.setVisibility(View.VISIBLE);
        recyclerViewActive.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        incomePeopleVm.honorList(type);
    }

    //成交榜
    private void getDealData() {
        tvFirst.setTextColor(getResources().getColor(R.color.color_666666));
        tvFirst.setBackgroundResource(R.drawable.selector_btn_confirm_gray);
        tvSecond.setTextColor(getResources().getColor(R.color.color_666666));
        tvSecond.setBackgroundResource(R.drawable.selector_btn_confirm_gray);
        tvThird.setTextColor(getResources().getColor(R.color.colorFFF));
        tvThird.setBackgroundResource(R.drawable.selector_btn_confirm);
        rlPlaces.setVisibility(View.GONE);
        llRecommend.setVisibility(View.GONE);
        recyclerViewActive.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        incomePeopleVm.honorList(type);
    }

    //显示讲台数据
    private void getTopRank(String unit) {
        tvFirstName.setText(rankListBean.getList().get(0).getUsername()+"\n"+rankListBean.getList().get(0).getCount()+unit);
        tvSecondName.setText(rankListBean.getList().get(1).getUsername()+"\n"+rankListBean.getList().get(1).getCount()+unit);
        tvThirdName.setText(rankListBean.getList().get(2).getUsername()+"\n"+rankListBean.getList().get(2).getCount()+unit);

        getImageData(rankListBean.getList().get(0).getAvatar(),cvFirst);
        getImageData(rankListBean.getList().get(1).getAvatar(),cvSecond);
        getImageData(rankListBean.getList().get(2).getAvatar(),cvThird);
    }

    //头像显示
    private void getImageData(String image,CircleImageView circleImageView) {
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(image)
                .placeholder(R.drawable.icon_no_image)
                .errorPic(R.drawable.icon_no_image)
                .imageView(circleImageView)
                .build());
    }
}
