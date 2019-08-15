package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ClassifyAdapter;
import com.shuangduan.zcy.adapter.HomeHeadlinesAdapter;
import com.shuangduan.zcy.adapter.IncomeStatementAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.ClassifyBean;
import com.shuangduan.zcy.model.bean.HomeBannerBean;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.model.bean.HomePushBean;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.utils.image.GlideImageLoader;
import com.shuangduan.zcy.view.headlines.HeadlinesActivity;
import com.shuangduan.zcy.view.headlines.HeadlinesDetailActivity;
import com.shuangduan.zcy.view.material.MaterialActivity;
import com.shuangduan.zcy.view.income.MineIncomeActivity;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoActivity;
import com.shuangduan.zcy.view.recruit.RecruitActivity;
import com.shuangduan.zcy.view.release.ReleaseListActivity;
import com.shuangduan.zcy.view.search.SearchActivity;
import com.shuangduan.zcy.view.supplier.SupplierActivity;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.MarqueeListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class describe
 * @time 2019/7/5 13:26
 * @change
 * @chang time
 * @class describe
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.rv_income_statement)
    RecyclerView rvIncomeStatement;
    @BindView(R.id.rv_infrastructure_headlines)
    RecyclerView rvHeadlines;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marquee)
    MarqueeListView marqueeView;
    @BindView(R.id.tv_subscribe_state)
    TextView tvSubscribeState;
    IncomeStatementAdapter incomeStatementAdapter;
    HomeHeadlinesAdapter headlinesAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));

        List<ClassifyBean> list = getClassify();
        rvClassify.setLayoutManager(new GridLayoutManager(mContext, 4));
        ClassifyAdapter classifyAdapter = new ClassifyAdapter(list);
        rvClassify.setAdapter(classifyAdapter);
        classifyAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (list.get(position).getType()){
                case ClassifyBean.GCXX:
                    ActivityUtils.startActivity(ProjectInfoActivity.class);
                    break;
                case ClassifyBean.ZCXX:
                    ActivityUtils.startActivity(RecruitActivity.class);
                    break;
                case ClassifyBean.JJWZ:
                    ActivityUtils.startActivity(MaterialActivity.class);
                    break;
                case ClassifyBean.PQZX:
                    break;
                case ClassifyBean.YZGYS:
                    ActivityUtils.startActivity(SupplierActivity.class);
                    break;
                case ClassifyBean.WDSY:
                    ActivityUtils.startActivity(MineIncomeActivity.class);
                    break;
                case ClassifyBean.FBXX:
                    ActivityUtils.startActivity(ReleaseListActivity.class);
                    break;
                case ClassifyBean.JJTT:
                    break;
            }
        });

        HomeVm homeVm = ViewModelProviders.of(this).get(HomeVm.class);
        homeVm.pushLiveData.observe(this, homePushBeans -> {
            List<String> marquee = new ArrayList<>();
            for (HomePushBean bean: homePushBeans) {
                marquee.add(bean.getTitle());
            }
            marqueeView.setContent(marquee);
            if (homePushBeans != null && homePushBeans.size() > 0){
                tvSubscribeState.setText(homePushBeans.get(0).getWarrant_status() == 1?"已认购":"未认购");
            }
        });
        homeVm.bannerLiveData.observe(this, homeBannerBeans -> {
            ArrayList<String> pics = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (HomeBannerBean bean : homeBannerBeans) {
                pics.add(bean.getImages());
                titles.add("");
            }
            initBanner(pics, titles);
        });
        homeVm.listLiveData.observe(this, homeListBean -> {
            if (incomeStatementAdapter == null){
                rvIncomeStatement.setLayoutManager(new LinearLayoutManager(mContext));
                rvIncomeStatement.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
                incomeStatementAdapter = new IncomeStatementAdapter(R.layout.item_income_statement, homeListBean.getExplain());
                rvIncomeStatement.setAdapter(incomeStatementAdapter);
                incomeStatementAdapter.setOnItemClickListener((adapter, view, position) -> {

                });
            }else {
                incomeStatementAdapter.setNewData(homeListBean.getExplain());
            }

            if (headlinesAdapter == null){
                rvHeadlines.setLayoutManager(new LinearLayoutManager(mContext));
                rvHeadlines.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
                headlinesAdapter = new HomeHeadlinesAdapter(R.layout.item_headlines, homeListBean.getHeadlines());
                rvHeadlines.setAdapter(headlinesAdapter);
                headlinesAdapter.setOnItemClickListener((adapter, view, position) -> {
                    HomeListBean.HeadlinesBean headlinesBean = headlinesAdapter.getData().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.HEADLINES_ID, headlinesBean.getId());
                    ActivityUtils.startActivity(bundle, HeadlinesDetailActivity.class);
                });
            }else {
                headlinesAdapter.setNewData(homeListBean.getHeadlines());
            }
        });
        homeVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        //添加走马灯变化监听
        marqueeView.setLocationListener(new MarqueeListView.LocationListener() {
            @Override
            public void start(int position) {
                if (homeVm.pushLiveData.getValue() != null && homeVm.pushLiveData.getValue().size() > 0){
                    tvSubscribeState.setText(homeVm.pushLiveData.getValue().get(position).getWarrant_status() == 1?"已认购":"未认购");
                }
            }

            @Override
            public void end() {

            }
        });
        homeVm.getInit();
    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_bar_title, R.id.tv_more})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_bar_title:
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.tv_more:
                ActivityUtils.startActivity(HeadlinesActivity.class);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    private List<ClassifyBean> getClassify(){
        List<ClassifyBean> list = new ArrayList<>();
        String[] classifys = getResources().getStringArray(R.array.classify);
        list.add(new ClassifyBean(R.drawable.classify_xxgc, classifys[0], 1));
        list.add(new ClassifyBean(R.drawable.classify_zcxx, classifys[1], 2));
        list.add(new ClassifyBean(R.drawable.classify_jjwz, classifys[2], 3));
        list.add(new ClassifyBean(R.drawable.classify_pqzx, classifys[3], 4));
        list.add(new ClassifyBean(R.drawable.classify_gys, classifys[4], 5));
        list.add(new ClassifyBean(R.drawable.classify_wdsy, classifys[5], 6));
        list.add(new ClassifyBean(R.drawable.classify_fbxx, classifys[6], 7));
        list.add(new ClassifyBean(R.drawable.classify_jjtt, classifys[7], 8));
        return list;
    }

    private void initBanner(ArrayList<String> list, ArrayList<String> titles){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

}
