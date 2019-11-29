package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import com.shuangduan.zcy.vm.IMAddVm;
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
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
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
    private RelativeLayout relativeLayout;
    private TextView number;
    private IUnReadMessageObserver observer;
    private IMAddVm imAddVm;
    private HomeVm homeVm;
    private HomeNeedVm homeNeedVm;
    private DemandRelationshipVm demandRelationshipVm;
    private int manage_status;

    //顶部状态栏透明度和颜色
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

        homeVm = ViewModelProviders.of(this).get(HomeVm.class);
        homeNeedVm = ViewModelProviders.of(this).get(HomeNeedVm.class);
        imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);
        demandRelationshipVm = ViewModelProviders.of(mActivity).get(DemandRelationshipVm.class);

        getChangeLister();
        getBadgeViewInitView();

        homeNeedVm.HomeNeedVm(getActivity(), view, viewPager, tabLayout, materialIndicator, demandRelationshipVm);

        //快讯滚动标题返回数据
        homeVm.pushLiveData.observe(this, homePushBeans -> {
            List<String> marquee = new ArrayList<>();
            for (HomePushBean bean : homePushBeans) {
                marquee.add(bean.getTitle());
            }
            marqueeView.setContent(marquee);
        });

        //轮播图列表返回数据
        homeVm.bannerLiveData.observe(this, homeBannerBeans -> {
            ArrayList<String> pics = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (HomeBannerBean bean : homeBannerBeans) {
                pics.add(bean.getImages());
                titles.add("");
            }
            initBanner(pics, titles);
        });

        //基建头条列表返回数据
        homeVm.listLiveData.observe(this, homeListBean -> {
            rvHeadlines.setLayoutManager(new LinearLayoutManager(mContext));
            rvHeadlines.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
            HomeHeadlinesAdapter headlinesAdapter = new HomeHeadlinesAdapter(R.layout.item_headlines, homeListBean.getHeadlines());
            rvHeadlines.setAdapter(headlinesAdapter);
            headlinesAdapter.setOnItemClickListener((adapter, view, position) -> {
                HomeListBean.HeadlinesBean headlinesBean = headlinesAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.HEADLINES_ID, headlinesBean.getId());
                ActivityUtils.startActivity(bundle, HeadlinesDetailActivity.class);
            });
        });

        //版本升级返回数据
        homeVm.versionUpgradesLiveData.observe(this, versionUpgradesBean -> {
            if (versionUpgradesBean.getStatus().equals("1")) {
                //版本更新弹出框显示
                UpdateManager manager = new UpdateManager(getActivity(), versionUpgradesBean);
                manager.showNoticeDialog();
            }
        });

        //后台管理权限
        homeVm.supplierRoleLiveData.observe(this, supplierRoleBean -> {
            //获取用户身份 0普通用户 1普通供应商 2子公司 3集团 4子账号
            manage_status = supplierRoleBean.getManage_status();
        });

        //设置角标数量
        imAddVm.applyCountData.observe(this, friendApplyCountBean -> {
            //获取用户身份 0普通用户 1普通供应商 2子公司 3集团 4子账号
            switch (manage_status) {
                case 0://普通用户
                case 1://普通供应商
                    imAddVm.count = friendApplyCountBean.getCount() + friendApplyCountBean.getSubscribe();
                    break;
                case 2://子公司
                case 3://集团
                case 4://子公司子账号
                case 5://集团子账号
                    imAddVm.count =  friendApplyCountBean.getCount() + friendApplyCountBean.getSubscribe() + friendApplyCountBean.getMaterial();
                    break;
            }
            LogUtils.e(imAddVm.count);
            if (imAddVm.count < 1) {
                relativeLayout.setVisibility(View.GONE);
            } else if (imAddVm.count < 100) {
                number.setTextSize(11);
                relativeLayout.setVisibility(View.VISIBLE);
                number.setText(String.valueOf(imAddVm.count));
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
                number.setTextSize(9);
                number.setText("99+");
            }
        });
    }

    @OnClick({R.id.tv_bar_title, R.id.tv_more, R.id.tv_bar_title_home, R.id.iv_my_income, R.id.rl_zgx, R.id.rl_zwz, R.id.rl_zmj, R.id.tv_more_need})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_bar_title:
            case R.id.tv_bar_title_home://搜索
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.tv_more://基建头条查看更多
                ActivityUtils.startActivity(HeadlinesActivity.class);
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
                ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                break;
            case R.id.rl_zmj://发布找买家
                bundle.putString("type", "2");
                ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                break;
            case R.id.tv_more_need://需求资讯查看更多
                if (viewPager.getCurrentItem() == 0) {
                    bundle.putInt(DEMAND_TYPE, FIND_RELATIONSHIP_TYPE);//找关系列表
                    ActivityUtils.startActivity(bundle, DemandActivity.class);
                } else if (viewPager.getCurrentItem() == 1) {
                    bundle.putInt(DEMAND_TYPE, FIND_SUBSTANCE_TYPE);//找物资列表
                    ActivityUtils.startActivity(bundle, DemandActivity.class);
                } else if (viewPager.getCurrentItem() == 2) {
                    bundle.putInt(DEMAND_TYPE, FIND_BUYER_TYPE);//找买家列表
                    ActivityUtils.startActivity(bundle, DemandActivity.class);
                }
                break;
        }
    }

    //滑动监听和下拉属性监听事件
    private void getChangeLister() {
        //滑动布局滑动监听
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

                    sColor = ((255 * mScrollY_2 / h) << 24) | ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary) & 0x00ffffff;

                    toolbar.setAlpha(sAlpha);
                    //设置折叠标题背景颜色
                    toolbar.setBackgroundColor(sColor);

                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });
        refresh.setEnableLoadMore(false);
        refresh.setEnableRefresh(true);
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnRefreshListener(refreshLayout -> {
            homeVm.getInit(getActivity());
            demandRelationshipVm.getRelationship();
            refreshLayout.finishRefresh(1000);
        });
        refresh.setEnableOverScrollDrag(true);
        refresh.setEnableOverScrollBounce(true);
        //监听下拉刷新滑动距离
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

        //导航按钮初始化
        List<ClassifyBean> list = getClassify();
        rvClassify.setLayoutManager(new GridLayoutManager(mContext, 4));
        ClassifyAdapter classifyAdapter = new ClassifyAdapter(list);
        rvClassify.setAdapter(classifyAdapter);
        classifyAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (list.get(position).getType()) {
                case ClassifyBean.GCXX://工程信息
                    ActivityUtils.startActivity(ProjectInfoActivity.class);
                    break;
                case ClassifyBean.ZCXX://招采信息
                    ActivityUtils.startActivity(RecruitActivity.class);
                    break;
                case ClassifyBean.JJWZ://基建物资
                    ActivityUtils.startActivity(MaterialActivity.class);
                    break;
                case ClassifyBean.YZGYS://优质供应商
                    ActivityUtils.startActivity(SupplierActivity.class);
                    break;
            }
        });
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
        number = badge.findViewById(R.id.number);
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

    private void initBanner(ArrayList<String> list, ArrayList<String> titles) {
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

    @Override
    protected void initDataFromService() {
        homeVm.getInit(getActivity());
        observer = i -> {
            LogUtils.e(i);
            // i 是未读数量
            imAddVm.count = i;
            imAddVm.applyCount();
        };
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM);
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
    public void onDestroy() {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
        super.onDestroy();
    }




    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (sAlpha != 0 && sColor != 0) {
            toolbar.setAlpha(sAlpha);
            //设置折叠标题背景颜色
            toolbar.setBackgroundColor(sColor);
            toolbar.setVisibility(View.VISIBLE);
        }
    }

}
