package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ClassifyAdapter;
import com.shuangduan.zcy.adapter.HomeHeadlinesAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.UpdateManager;
import com.shuangduan.zcy.model.bean.ClassifyBean;
import com.shuangduan.zcy.model.bean.HomeBannerBean;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.model.bean.HomePushBean;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.TimeUtils;
import com.shuangduan.zcy.utils.image.GlideImageLoader;
import com.shuangduan.zcy.view.demand.DemandActivity;
import com.shuangduan.zcy.view.demand.DemandReleaseActivity;
import com.shuangduan.zcy.view.headlines.HeadlinesActivity;
import com.shuangduan.zcy.view.headlines.HeadlinesDetailActivity;
import com.shuangduan.zcy.view.income.MineIncomeActivity;
import com.shuangduan.zcy.view.material.MaterialActivity;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoActivity;
import com.shuangduan.zcy.view.recruit.RecruitActivity;
import com.shuangduan.zcy.view.search.SearchActivity;
import com.shuangduan.zcy.view.supplier.SupplierActivity;
import com.shuangduan.zcy.vm.DemandRelationshipVm;
import com.shuangduan.zcy.vm.HomeNeedVm;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.MarqueeListView;
import com.shuangduan.zcy.weight.MaterialIndicator;
import com.shuangduan.zcy.weight.XTabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.DEMAND_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_BUYER_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_RELATIONSHIP_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_SUBSTANCE_TYPE;
import static com.shuangduan.zcy.utils.TimeUtils.THE_LANTERN_FESTIVAL;

/**
 * @author ?????? QQ:876885613
 * @name ZICAICloudPlatform
 * @class name???com.example.zicaicloudplatform.view.fragment
 * @class ??????
 * @time 2019/7/5 13:26
 * @change
 * @chang time
 * @class describe
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.scroll)
    AdaptationScrollView scrollView;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rl_toolbar_top)
    RelativeLayout rl_toolbar;
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv_infrastructure_headlines)
    RecyclerView rvHeadlines;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marquee)
    MarqueeListView marqueeView;
    @BindView(R.id.tv_subscribe_state)
    TextView tvSubscribeState;
    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.material_indicator)
    MaterialIndicator materialIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.iv_zzy)
    ImageView iv_zzy;
    @BindView(R.id.iv_zwz)
    ImageView iv_zwz;
    @BindView(R.id.iv_zmj)
    ImageView iv_zmj;
    private HomeVm homeVm;
    private HomeNeedVm homeNeedVm;
    private DemandRelationshipVm demandRelationshipVm;
    //?????????????????????????????????
    private static int sColor;
    private static float sAlpha;


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
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View v) {

        UpdateManager manager = UpdateManager.getInstance(getContext());


        homeVm = mActivity.getViewModel(HomeVm.class);

        homeNeedVm = mActivity.getViewModel(HomeNeedVm.class);
        demandRelationshipVm = mActivity.getViewModel(DemandRelationshipVm.class);


        getChangeLister();

        homeNeedVm.HomeNeedVm(mActivity, view, viewPager, tabLayout, materialIndicator, demandRelationshipVm);

        //??????????????????????????????
        homeVm.pushLiveData.observe(this, homePushBeans -> {
            LogUtils.e("pushLiveData");
            List<String> marquee = new ArrayList<>();
            for (HomePushBean bean : homePushBeans) {
                marquee.add(bean.getTitle());
            }
            marqueeView.setContent(marquee);
        });

        //???????????????????????????
        homeVm.bannerLiveData.observe(this, homeBannerBeans -> {
            ArrayList<String> pics = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (HomeBannerBean bean : homeBannerBeans) {
                pics.add(bean.getImages());
                titles.add("");
            }
            initBanner(pics, titles);
        });

        //??????????????????????????????
        rvHeadlines.setLayoutManager(new LinearLayoutManager(mContext));
        rvHeadlines.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        HomeHeadlinesAdapter headlinesAdapter = new HomeHeadlinesAdapter(R.layout.item_headlines, null);
        rvHeadlines.setAdapter(headlinesAdapter);
        headlinesAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomeListBean.HeadlinesBean headlinesBean = headlinesAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.HEADLINES_ID, headlinesBean.getId());
            ActivityUtils.startActivity(bundle, HeadlinesDetailActivity.class);
        });
        homeVm.listLiveData.observe(this, homeListBean -> {
            headlinesAdapter.setNewData(homeListBean.getHeadlines());
        });

        //????????????????????????
        homeVm.versionUpgradesLiveData.observe(this, versionUpgradesBean -> {
            if (versionUpgradesBean.getStatus().equals("1")) {
                //???????????????????????????
                manager.showNoticeDialog(versionUpgradesBean);
            }
        });

    }

    @OnClick({R.id.tv_bar_title, R.id.tv_more, R.id.tv_bar_title_home, R.id.iv_my_income, R.id.rl_zgx, R.id.rl_zwz, R.id.rl_zmj, R.id.tv_more_need})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_bar_title:
            case R.id.tv_bar_title_home://??????
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.tv_more://????????????????????????
                ActivityUtils.startActivity(HeadlinesActivity.class);
                break;
            case R.id.iv_my_income://????????????
                ActivityUtils.startActivity(MineIncomeActivity.class);
                break;
            case R.id.rl_zgx://???????????????
                bundle.putString("type", "0");
                ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                break;
            case R.id.rl_zwz://???????????????
                bundle.putString("type", "1");
                ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                break;
            case R.id.rl_zmj://???????????????
                bundle.putString("type", "2");
                ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                break;
            case R.id.tv_more_need://????????????????????????
                if (viewPager.getCurrentItem() == 0) {
                    bundle.putInt(DEMAND_TYPE, FIND_RELATIONSHIP_TYPE);//???????????????
                    ActivityUtils.startActivity(bundle, DemandActivity.class);
                } else if (viewPager.getCurrentItem() == 1) {
                    bundle.putInt(DEMAND_TYPE, FIND_SUBSTANCE_TYPE);//???????????????
                    ActivityUtils.startActivity(bundle, DemandActivity.class);
                } else if (viewPager.getCurrentItem() == 2) {
                    bundle.putInt(DEMAND_TYPE, FIND_BUYER_TYPE);//???????????????
                    ActivityUtils.startActivity(bundle, DemandActivity.class);
                }
                break;
        }
    }

    //???????????????????????????????????????
    private void getChangeLister() {
        //????????????????????????
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {

            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(70);

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    LogUtils.i(lastScrollY);
                    scrollY = Math.min(h, scrollY);
                    mScrollY_2 = scrollY > h ? h : scrollY;

                    sAlpha = 1f * mScrollY_2 / h;

                    //?????????????????????????????????
                    if (Integer.valueOf(TimeUtils.getYearMonthDayTime()) > THE_LANTERN_FESTIVAL) {
                        sColor = ((255 * mScrollY_2 / h) << 24) | ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary) & 0x00ffffff;
                    } else {
                        sColor = ((255 * mScrollY_2 / h) << 24) | ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_f41e13) & 0x00ffffff;
                    }

                    toolbar.setAlpha(sAlpha);
                    //??????????????????????????????
                    toolbar.setBackgroundColor(sColor);

                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });
        refresh.setEnableLoadMore(false);
        refresh.setEnableRefresh(true);
        //?????????????????????????????????
        if (Integer.valueOf(TimeUtils.getYearMonthDayTime()) > THE_LANTERN_FESTIVAL) {
            refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        } else {
            refresh.setPrimaryColorsId(R.color.color_f41e13, android.R.color.white);
        }

        refresh.setOnRefreshListener(refreshLayout -> {
            homeVm.getInit(mContext);
            demandRelationshipVm.getRelationship();
            refreshLayout.finishRefresh(1000);
        });
        refresh.setEnableOverScrollDrag(true);
        refresh.setEnableOverScrollBounce(true);
        //??????????????????????????????
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                try {
                    if (offset > 0) {
                        rl_toolbar.setVisibility(View.GONE);
                    } else {
                        rl_toolbar.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //?????????????????????
        List<ClassifyBean> list;
        //?????????????????????????????????
        if (Integer.valueOf(TimeUtils.getYearMonthDayTime()) > THE_LANTERN_FESTIVAL) {
            list = getClassify();
            iv_zzy.setBackgroundResource(R.drawable.classify_zgx);
            iv_zwz.setBackgroundResource(R.drawable.classify_zwz);
            iv_zmj.setBackgroundResource(R.drawable.classify_zmj);
        } else {
            list = getClassifyNewYear();
            iv_zzy.setBackgroundResource(R.drawable.classify_zgx_new);
            iv_zwz.setBackgroundResource(R.drawable.classify_zwz_new);
            iv_zmj.setBackgroundResource(R.drawable.classify_zmj_new);
        }
        rvClassify.setLayoutManager(new GridLayoutManager(mContext, 4));
        ClassifyAdapter classifyAdapter = new ClassifyAdapter(list);
        rvClassify.setAdapter(classifyAdapter);
        classifyAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (list.get(position).getType()) {
                case ClassifyBean.GCXX://????????????
                    ActivityUtils.startActivity(ProjectInfoActivity.class);
                    break;
                case ClassifyBean.ZCXX://????????????
                    ActivityUtils.startActivity(RecruitActivity.class);
                    break;
                case ClassifyBean.JJWZ://????????????
                    ActivityUtils.startActivity(MaterialActivity.class);
                    break;
                case ClassifyBean.YZGYS://???????????????
                    ActivityUtils.startActivity(SupplierActivity.class);
                    break;
            }
        });
    }

    private List<ClassifyBean> getClassify() {
        List<ClassifyBean> list = new ArrayList<>();
        String[] classifys = getResources().getStringArray(R.array.classify);
        list.add(new ClassifyBean(R.drawable.classify_xxgc, classifys[0], 1));
        list.add(new ClassifyBean(R.drawable.classify_zcxx, classifys[1], 2));
        list.add(new ClassifyBean(R.drawable.classify_jjwz, classifys[2], 3));
        list.add(new ClassifyBean(R.drawable.classify_yzgys, classifys[3], 4));
        return list;
    }

    private List<ClassifyBean> getClassifyNewYear() {
        List<ClassifyBean> list = new ArrayList<>();
        String[] classifys = getResources().getStringArray(R.array.classify);
        list.add(new ClassifyBean(R.drawable.classify_xxgc_new, classifys[0], 1));
        list.add(new ClassifyBean(R.drawable.classify_zcxx_new, classifys[1], 2));
        list.add(new ClassifyBean(R.drawable.classify_jjwz_new, classifys[2], 3));
        list.add(new ClassifyBean(R.drawable.classify_yzgys_new, classifys[3], 4));
        return list;
    }

    private void initBanner(ArrayList<String> list, ArrayList<String> titles) {
        //??????banner??????
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //?????????????????????
        banner.setImageLoader(new GlideImageLoader());
        //??????????????????
        banner.setImages(list);
        //??????banner????????????
        banner.setBannerAnimation(Transformer.Default);
        //????????????????????????banner???????????????title??????
        banner.setBannerTitles(titles);
        //??????????????????????????????true
        banner.isAutoPlay(true);
        //??????????????????
        banner.setDelayTime(4500);
        //???????????????????????????banner???????????????????????????
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //???????????????????????????
        banner.setOnBannerListener(position -> {
            //????????????????????????????????????
//            if (position == 0) {
//                Bundle bundle = new Bundle();
//                bundle.putString("register", "ali");
//                ActivityUtils.startActivity(bundle, WebViewActivity.class);
//            }
        });
        //banner?????????????????????????????????????????????
        banner.start();
    }

    @Override
    protected void initDataFromService() {
        homeVm.getInit(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
        homeNeedVm.recyclerView1.startLine();
        homeNeedVm.recyclerView2.startLine();
        homeNeedVm.recyclerView3.startLine();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        homeNeedVm.recyclerView1.stop();
        homeNeedVm.recyclerView2.stop();
        homeNeedVm.recyclerView3.stop();

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (sAlpha != 0 && sColor != 0) {
            toolbar.setAlpha(sAlpha);
            //??????????????????????????????
            toolbar.setBackgroundColor(sColor);
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        homeVm.pushLiveData.removeObservers(this);
        homeVm.bannerLiveData.removeObservers(this);
        homeVm.listLiveData.removeObservers(this);
        homeVm.versionUpgradesLiveData.removeObservers(this);


        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sColor = 0;
        sAlpha = 0;
    }
}
