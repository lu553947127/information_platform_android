package com.shuangduan.zcy.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ClassifyAdapter;
import com.shuangduan.zcy.adapter.DemandBuyerAdapter;
import com.shuangduan.zcy.adapter.FindRelationshipAdapter;
import com.shuangduan.zcy.adapter.FindSubstanceAdapter;
import com.shuangduan.zcy.adapter.HomeHeadlinesAdapter;
import com.shuangduan.zcy.adapter.XTabLayoutAdapter;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.UpdateManager;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;
import com.shuangduan.zcy.model.bean.VersionUpgradesBean;
import com.shuangduan.zcy.model.bean.ClassifyBean;
import com.shuangduan.zcy.model.bean.HomeBannerBean;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.model.bean.HomePushBean;
import com.shuangduan.zcy.model.bean.IMFriendApplyCountBean;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.VersionUtils;
import com.shuangduan.zcy.utils.image.GlideImageLoader;
import com.shuangduan.zcy.view.demand.DemandActivity;
import com.shuangduan.zcy.view.demand.DemandReleaseActivity;
import com.shuangduan.zcy.view.demand.FindBuyerDetailActivity;
import com.shuangduan.zcy.view.demand.FindRelationshipDetailActivity;
import com.shuangduan.zcy.view.demand.FindSubstanceDetailActivity;
import com.shuangduan.zcy.view.headlines.HeadlinesActivity;
import com.shuangduan.zcy.view.headlines.HeadlinesDetailActivity;
import com.shuangduan.zcy.view.material.MaterialActivity;
import com.shuangduan.zcy.view.income.MineIncomeActivity;
import com.shuangduan.zcy.view.mine.MineSubActivity;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoActivity;
import com.shuangduan.zcy.view.recruit.RecruitActivity;
import com.shuangduan.zcy.view.search.SearchActivity;
import com.shuangduan.zcy.view.supplier.SupplierActivity;
import com.shuangduan.zcy.vm.DemandBuyerVm;
import com.shuangduan.zcy.vm.DemandRelationshipVm;
import com.shuangduan.zcy.vm.DemandSubstanceVm;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.AutoScrollRecyclerView;
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
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static com.shuangduan.zcy.app.CustomConfig.DEMAND_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_BUYER_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_RELATIONSHIP_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_SUBSTANCE_TYPE;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class 首页
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
    private List<View> viewList=new ArrayList<>();
    private List<String> titleList=new ArrayList<>();
    private HomeHeadlinesAdapter headlinesAdapter;
    private RelativeLayout relativeLayout;
    private TextView number;
    private int count=0;
    private HomeVm homeVm;

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

    @SuppressLint("NewApi")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View v) {
//        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));

//        scrollView.setOnScrollChangeListener((AdaptationScrollView.OnScrollChangeListener) (v1, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (scrollY > 10) {
//                toolbar.setVisibility(View.VISIBLE);
//                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                toolbar.getBackground().setAlpha(250);
//            } else {
//                toolbar.setVisibility(View.GONE);
//                toolbar.setBackgroundColor(0);
//            }
//        });
        //滑动布局滑动监听
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {
            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(70);
            //设置折叠标题背景颜色
            private int color = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary)&0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    LogUtils.i(lastScrollY);
                    scrollY = Math.min(h, scrollY);
                    mScrollY_2 = scrollY > h ? h : scrollY;
                    toolbar.setAlpha(1f * mScrollY_2 / h);
                    toolbar.setBackgroundColor(((255 * mScrollY_2 / h) << 24) | color);
                }else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });
        refresh.setEnableLoadMore(false);
        refresh.setEnableRefresh(true);
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnRefreshListener(refreshLayout -> {
            homeVm.getInit();
            getAndroidVersionUpgrades();
            refreshLayout.finishRefresh(1000);
        });
        refresh.setEnableOverScrollDrag(true);
        refresh.setEnableOverScrollBounce(true);
        //监听下拉刷新滑动距离
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (offset>0){
                    rl_toolbar.setVisibility(View.GONE);
                }else {
                    rl_toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
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
                case ClassifyBean.YZGYS:
                    ActivityUtils.startActivity(SupplierActivity.class);
                    break;
            }
        });

        homeVm = ViewModelProviders.of(this).get(HomeVm.class);
        homeVm.pushLiveData.observe(this, homePushBeans -> {
            List<String> marquee = new ArrayList<>();
            for (HomePushBean bean: homePushBeans) {
                marquee.add(bean.getTitle());
            }
            marqueeView.setContent(marquee);
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
//                    showLoading();
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

            }

            @Override
            public void end() {

            }
        });
        homeVm.getInit();
        getBadgeViewInitView();
        getNeedData();
    }

    //需求资讯
    private AutoScrollRecyclerView recyclerView1;
    private AutoScrollRecyclerView recyclerView2;
    private AutoScrollRecyclerView recyclerView3;
    @SuppressLint("InflateParams")
    private void getNeedData() {
        view.getBackground().mutate().setAlpha(10);
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.fragment_demand_information, null,false);
        View view2 = inflater.inflate(R.layout.fragment_demand_information, null,false);
        View view3 = inflater.inflate(R.layout.fragment_demand_information, null,false);
        titleList.add("找关系");
        titleList.add("找物资");
        titleList.add("找买家");
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        XTabLayoutAdapter xTabLayoutAdapter = new XTabLayoutAdapter(viewList, titleList);
        viewPager.setAdapter(xTabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(materialIndicator);
        materialIndicator.setAdapter(Objects.requireNonNull(viewPager.getAdapter()));

        recyclerView1 = view1.findViewById(R.id.rv);
        recyclerView2 = view2.findViewById(R.id.rv);
        recyclerView3 = view3.findViewById(R.id.rv);
        ImageView iv_empty1=view1.findViewById(R.id.iv_icon);
        ImageView iv_empty2=view2.findViewById(R.id.iv_icon);
        ImageView iv_empty3=view3.findViewById(R.id.iv_icon);
        TextView tv_empty1=view1.findViewById(R.id.tv_tip);
        TextView tv_empty2=view2.findViewById(R.id.tv_tip);
        TextView tv_empty3=view3.findViewById(R.id.tv_tip);
        ConstraintLayout cl_empty1=view1.findViewById(R.id.cl_empty);
        ConstraintLayout cl_empty2=view2.findViewById(R.id.cl_empty);
        ConstraintLayout cl_empty3=view3.findViewById(R.id.cl_empty);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView3.setNestedScrollingEnabled(false);

        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView1.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        FindRelationshipAdapter relationshipAdapter = new FindRelationshipAdapter(R.layout.item_demand_relationship, null);
        recyclerView1.setAdapter(relationshipAdapter);

        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView2.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        FindSubstanceAdapter substanceAdapter = new FindSubstanceAdapter(R.layout.item_demand_substance, null);
        recyclerView2.setAdapter(substanceAdapter);

        recyclerView3.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView3.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        DemandBuyerAdapter buyerAdapter = new DemandBuyerAdapter(R.layout.item_demand_buyer, null);
        recyclerView3.setAdapter(buyerAdapter);

        relationshipAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandRelationshipBean.ListBean listBean = relationshipAdapter.getData().get(position%adapter.getData().size());
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindRelationshipDetailActivity.class);
        });

        substanceAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandSubstanceBean.ListBean listBean = substanceAdapter.getData().get(position%adapter.getData().size());
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindSubstanceDetailActivity.class);
        });

        buyerAdapter.setOnItemClickListener((adapter1, view, position) -> {
            DemandBuyerBean.ListBean listBean = buyerAdapter.getData().get(position%adapter1.getData().size());
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindBuyerDetailActivity.class);
        });

        DemandRelationshipVm demandRelationshipVm= ViewModelProviders.of(mActivity).get(DemandRelationshipVm.class);
        demandRelationshipVm.getRelationship();
        DemandSubstanceVm demandSubstanceVm = ViewModelProviders.of(mActivity).get(DemandSubstanceVm.class);
        demandSubstanceVm.getSubstance();
        DemandBuyerVm demandBuyerVm = ViewModelProviders.of(mActivity).get(DemandBuyerVm.class);
        demandBuyerVm.getBuyer();

        demandRelationshipVm.relationshipLiveData.observe(this, relationshipBean -> {
            if (relationshipBean.getList()!=null&&relationshipBean.getList().size()!=0){
                cl_empty1.setVisibility(View.GONE);
                if (relationshipBean.getPage() == 1) {
                    relationshipAdapter.setNewData(relationshipBean.getList());
                } else {
                    relationshipAdapter.addData(relationshipBean.getList());
                }
            }else {
                cl_empty1.setVisibility(View.VISIBLE);
                iv_empty1.setImageResource(R.drawable.icon_empty_project);
                tv_empty1.setText(R.string.empty_pull_strings_info);
            }
        });
        demandSubstanceVm.substanceLiveData.observe(this, demandSubstanceBean -> {
            if (demandSubstanceBean.getList()!=null&&demandSubstanceBean.getList().size()!=0){
                cl_empty2.setVisibility(View.GONE);
                if (demandSubstanceBean.getPage() == 1) {
                    substanceAdapter.setNewData(demandSubstanceBean.getList());
                }else {
                    substanceAdapter.addData(demandSubstanceBean.getList());
                }
            }else {
                cl_empty2.setVisibility(View.VISIBLE);
                iv_empty2.setImageResource(R.drawable.icon_empty_project);
                tv_empty2.setText(R.string.empty_substance_info);
            }
        });
        demandBuyerVm.buyerLiveData.observe(this, demandBuyerBean -> {
            if (demandBuyerBean.getList()!=null&&demandBuyerBean.getList().size()!=0){
                cl_empty3.setVisibility(View.GONE);
                if (demandBuyerBean.getPage() == 1) {
                    buyerAdapter.setNewData(demandBuyerBean.getList());
                }else {
                    buyerAdapter.addData(demandBuyerBean.getList());
                }
            }else {
                cl_empty3.setVisibility(View.VISIBLE);
                iv_empty3.setImageResource(R.drawable.icon_empty_project);
                tv_empty3.setText(R.string.empty_buyer_info);
            }
        });
    }

    @OnClick({R.id.tv_bar_title, R.id.tv_more, R.id.iv_subscribed,R.id.tv_bar_title_home,R.id.iv_subscribed_home,R.id.iv_my_income,R.id.rl_zgx,R.id.rl_zwz,R.id.rl_zmj,R.id.tv_more_need})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.tv_bar_title:
            case R.id.tv_bar_title_home://搜索
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.tv_more://基建头条查看更多
                ActivityUtils.startActivity(HeadlinesActivity.class);
                break;
            case R.id.iv_subscribed:
            case R.id.iv_subscribed_home://我的订阅
                ActivityUtils.startActivity(MineSubActivity.class);
                break;
            case R.id.iv_my_income://我的收益
                ActivityUtils.startActivity(MineIncomeActivity.class);
                break;
            case R.id.rl_zgx://发布找关系
                bundle.putString("type", "0");
                ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                break;
            case R.id.rl_zwz://发布找物资
                bundle.putString("type", "1");
                ActivityUtils.startActivity(bundle,DemandReleaseActivity.class);
                break;
            case R.id.rl_zmj://发布找买家
                bundle.putString("type", "2");
                ActivityUtils.startActivity(bundle,DemandReleaseActivity.class);
                break;
            case R.id.tv_more_need://需求资讯查看更多
                if(viewPager.getCurrentItem()==0){
                    bundle.putInt(DEMAND_TYPE,FIND_RELATIONSHIP_TYPE);//找关系列表
                    ActivityUtils.startActivity(bundle,DemandActivity.class);
                }else if (viewPager.getCurrentItem()==1){
                    bundle.putInt(DEMAND_TYPE,FIND_SUBSTANCE_TYPE);//找物资列表
                    ActivityUtils.startActivity(bundle, DemandActivity.class);
                }else if (viewPager.getCurrentItem()==2){
                    bundle.putInt(DEMAND_TYPE,FIND_BUYER_TYPE);//找买家列表
                    ActivityUtils.startActivity(bundle,DemandActivity.class);
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
        recyclerView1.startLine();
        recyclerView2.startLine();
        recyclerView3.startLine();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        recyclerView1.stop();
        recyclerView2.stop();
        recyclerView3.stop();
    }

    private List<ClassifyBean> getClassify(){
        List<ClassifyBean> list = new ArrayList<>();
        String[] classifys = getResources().getStringArray(R.array.classify);
        list.add(new ClassifyBean(R.drawable.classify_xxgc, classifys[0], 1));
        list.add(new ClassifyBean(R.drawable.classify_zcxx, classifys[1], 2));
        list.add(new ClassifyBean(R.drawable.classify_jjwz, classifys[2], 3));
        list.add(new ClassifyBean(R.drawable.classify_yzgys, classifys[3], 4));
        return list;
    }

    private void initBanner(ArrayList<String> list, ArrayList<String> titles){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    //设置底部消息提醒数字布局
    private void getBadgeViewInitView() {
        //底部标题栏右上角标设置
        //获取整个的NavigationView
        BottomNavigationView navigation = Objects.requireNonNull(getActivity()).findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，设置在标题栏的位置
        View tab = menuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(getContext()).inflate(R.layout.layout_apply_count, menuView, false);
        //添加到Tab上
        itemView.addView(badge);
        //显示角标数字
        relativeLayout = badge.findViewById(R.id.rl);
        //显示/隐藏整个视图
        number=badge.findViewById(R.id.number);
    }

    //好友申请数量
    private void getFriendApplyCount(int i) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.FRIEND_APPLY_COUNT)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            IMFriendApplyCountBean bean=new Gson().fromJson(response.body(), IMFriendApplyCountBean.class);
                            if (bean.getCode().equals("200")){
                                count=bean.getData().getCount();
                                //设置底部标签数量
                                int counts=i+count;
                                if (counts < 1) {
                                    relativeLayout.setVisibility(View.GONE);
                                } else if (counts < 100) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    number.setText(" " + counts + " ");
                                    number.setTextSize(11);
                                } else {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    number.setText("99+");
                                    number.setTextSize(9);
                                }
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    //版本升级
    private void getAndroidVersionUpgrades() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.VERSION_UPGRADE)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("version", VersionUtils.getVerName(Objects.requireNonNull(getActivity())))//版本号
//                .params("version", "1")//版本号
                .params("type", "1")//升级类型：1 安卓，2 ios
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            VersionUpgradesBean bean=new Gson().fromJson(response.body(), VersionUpgradesBean.class);
                            if (bean.getCode().equals("200")){
                                if (bean.getData().getStatus().equals("1")){
                                    //版本更新弹出框显示
                                    UpdateManager manager = new UpdateManager(getActivity(), bean);
                                    manager.showNoticeDialog();
                                }
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(i -> {
            LogUtils.i(i);
            // i 是未读数量
            getFriendApplyCount(i);
        }, Conversation.ConversationType.PRIVATE,Conversation.ConversationType.GROUP,Conversation.ConversationType.SYSTEM);
        getAndroidVersionUpgrades();
    }

    @Override
    protected void initDataFromService() {

    }
}
