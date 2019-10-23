package com.shuangduan.zcy.view.projectinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectInfoAdapter;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;
import com.shuangduan.zcy.adapter.SelectorSecondAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.ProjectFilterBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.TypeBean;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.view.release.ReleaseProjectActivity;
import com.shuangduan.zcy.view.search.SearchActivity;
import com.shuangduan.zcy.vm.MultiAreaVm;
import com.shuangduan.zcy.vm.MultiStageVm;
import com.shuangduan.zcy.vm.MultiTypeVm;
import com.shuangduan.zcy.vm.ProjectListVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  工程信息列表模式
 * @time 2019/7/12 16:27
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoListActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.tv_stage)
    TextView tvStage;
    @BindView(R.id.iv_stage)
    ImageView ivStage;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_time)
    ImageView ivTime;
    @BindView(R.id.tv_subscribe)
    TextView tvSubscribe;
    @BindView(R.id.iv_subscribe)
    ImageView ivSubscribe;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.over)
    View over;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    private ProjectListVm projectListVm;
    private MultiStageVm stageVm;
    private MultiAreaVm areaVm;
    private MultiTypeVm typeVm;
    private ProjectInfoAdapter projectInfoAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_info_list;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_project_info, R.string.see_all, this);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[0]);
        ivBarRight.setImageResource(R.drawable.icon_search);
        tvBarRight.setVisibility(View.GONE);
        projectListVm = ViewModelProviders.of(this).get(ProjectListVm.class);
        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);
        stageVm = ViewModelProviders.of(this).get(MultiStageVm.class);
        typeVm = ViewModelProviders.of(this).get(MultiTypeVm.class);
        initArea();
        initStage();
        initTypes();

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        projectInfoAdapter = new ProjectInfoAdapter(R.layout.item_project_info, null);
        rv.setAdapter(projectInfoAdapter);
        projectInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProjectInfoBean.ListBean bean = projectInfoAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, bean.getId());
            bundle.putInt(CustomConfig.LOCATION, 0);
            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                projectListVm.moreProjectList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                projectListVm.projectList();
            }
        });

        projectListVm.init();
        projectListVm.projectLiveData.observe(this, projectInfoBeans -> {
            if (projectInfoBeans.getPage() == 1) {
                projectInfoAdapter.setNewData(projectInfoBeans.getList());
                projectInfoAdapter.setEmptyView(emptyView);
            } else {
                projectInfoAdapter.addData(projectInfoBeans.getList());
            }
            setNoMore(projectInfoBeans.getPage(), projectInfoBeans.getCount());
        });

        //滑动监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                AnimationUtils.listScrollAnimation(ivRelease,dy);
            }
        });
    }

    private void setNoMore(int page, int count) {
        if (page == 1) {
            if (page * 10 >= count) {
                if (refresh.getState() == RefreshState.None) {
                    refresh.setNoMoreData(true);
                } else {
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                refresh.finishRefresh();
            }
        } else {
            if (page * 10 >= count) {
                refresh.finishLoadMoreWithNoMoreData();
            } else {
                refresh.finishLoadMore();
            }
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.ll_area, R.id.ll_stage, R.id.ll_type, R.id.ll_time, R.id.ll_subscribe, R.id.over, R.id.iv_release})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.ll_area:
                getAreaData();
                break;
            case R.id.ll_stage:
                getStageData();
                break;
            case R.id.ll_type:
                getTypesData();
                break;
            case R.id.ll_time:
                projectListVm.currentSelect = 4;
                showPopArea(null, null, null);
                break;
            case R.id.ll_subscribe:
                projectListVm.currentSelect = 5;
                showPopArea(null, null, null);
                break;
            case R.id.over:
                popDismiss();
                break;
            case R.id.iv_release:
                bundle.putInt(CustomConfig.RELEASE_TYPE, 0);
                ActivityUtils.startActivity(bundle, ReleaseProjectActivity.class);
                break;
        }
    }

    private CommonPopupWindow popupWindowArea;
    private RecyclerView rvProvince;
    private RecyclerView rvCity;
    private RecyclerView rvStage;
    private RecyclerView rvTypeFirst;

    private LinearLayout llArea;
    private LinearLayout llStage;
    private LinearLayout llType;
    private RelativeLayout rlTime;
    private FrameLayout flSubscription;
    private TextView tvTimeStart;
    private TextView tvTimeEnd;
    private CheckBox cbUnsubscribe;
    private CheckBox cbSubscribe;
    private SelectorFirstAdapter provinceAdapter;
    private SelectorSecondAdapter cityAdapter;
    private SelectorFirstAdapter stageFirstAdapter;
    private SelectorFirstAdapter typeFirstAdapter;
//    private SelectorSecondAdapter typeSecondAdapter;

    private void showPopArea(final List<ProvinceBean> dataArea, final List<StageBean> dataStage, final List<TypeBean> dataType) {
        if (popupWindowArea == null) {
            popupWindowArea = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.dialog_area)
                    .setOutsideTouchable(false)
                    .setBackGroundLevel(1f)
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setViewOnclickListener((view, layoutResId) -> {
                        llArea = view.findViewById(R.id.ll_area);
                        llStage = view.findViewById(R.id.ll_stage);
                        llType = view.findViewById(R.id.ll_type);
                        rlTime = view.findViewById(R.id.rl_time);
                        flSubscription = view.findViewById(R.id.fl_subscription);
                        tvTimeStart = view.findViewById(R.id.tv_time_start);
                        tvTimeEnd = view.findViewById(R.id.tv_time_end);
                        cbUnsubscribe = view.findViewById(R.id.cb_unsubscribe);
                        cbSubscribe = view.findViewById(R.id.cb_subscribe);
                        switch (projectListVm.currentSelect) {
                            case 1:
                                llArea.setVisibility(View.VISIBLE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.GONE);
                                tvArea.setTextColor(getResources().getColor(R.color.colorPrimary));
                                ivExpand.setImageResource(R.drawable.icon_bottom_blue);
                                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                                ivStage.setImageResource(R.drawable.icon_bottom);
                                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                                ivType.setImageResource(R.drawable.icon_bottom);
                                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                                ivTime.setImageResource(R.drawable.icon_bottom);
                                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                                break;
                            case 2:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.VISIBLE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.GONE);
                                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                                ivExpand.setImageResource(R.drawable.icon_bottom);
                                tvStage.setTextColor(getResources().getColor(R.color.colorPrimary));
                                ivStage.setImageResource(R.drawable.icon_bottom_blue);
                                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                                ivType.setImageResource(R.drawable.icon_bottom);
                                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                                ivTime.setImageResource(R.drawable.icon_bottom);
                                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                                break;
                            case 3:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.VISIBLE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.GONE);
                                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                                ivExpand.setImageResource(R.drawable.icon_bottom);
                                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                                ivStage.setImageResource(R.drawable.icon_bottom);
                                tvType.setTextColor(getResources().getColor(R.color.colorPrimary));
                                ivType.setImageResource(R.drawable.icon_bottom_blue);
                                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                                ivTime.setImageResource(R.drawable.icon_bottom);
                                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                                break;
                            case 4:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.VISIBLE);
                                flSubscription.setVisibility(View.GONE);
                                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                                ivExpand.setImageResource(R.drawable.icon_bottom);
                                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                                ivStage.setImageResource(R.drawable.icon_bottom);
                                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                                ivType.setImageResource(R.drawable.icon_bottom);
                                tvTime.setTextColor(getResources().getColor(R.color.colorPrimary));
                                ivTime.setImageResource(R.drawable.icon_bottom_blue);
                                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                                break;
                            case 5:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.VISIBLE);
                                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                                ivExpand.setImageResource(R.drawable.icon_bottom);
                                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                                ivStage.setImageResource(R.drawable.icon_bottom);
                                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                                ivType.setImageResource(R.drawable.icon_bottom);
                                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                                ivTime.setImageResource(R.drawable.icon_bottom);
                                tvSubscribe.setTextColor(getResources().getColor(R.color.colorPrimary));
                                ivSubscribe.setImageResource(R.drawable.icon_bottom_blue);
                                break;
                        }

                        rvProvince = view.findViewById(R.id.rv_province);
                        rvCity = view.findViewById(R.id.rv_city);
                        rvStage = view.findViewById(R.id.rv_stage);
                        rvTypeFirst = view.findViewById(R.id.rv_type_first);

                        rvProvince.setLayoutManager(new LinearLayoutManager(this));
                        rvCity.setLayoutManager(new LinearLayoutManager(this));
                        rvStage.setLayoutManager(new LinearLayoutManager(this));
                        rvTypeFirst.setLayoutManager(new LinearLayoutManager(this));

                        provinceAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
                        cityAdapter = new SelectorSecondAdapter(R.layout.item_city, null);
                        stageFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
                        typeFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);

                        rvProvince.setAdapter(provinceAdapter);
                        rvCity.setAdapter(cityAdapter);
                        rvStage.setAdapter(stageFirstAdapter);
                        rvTypeFirst.setAdapter(typeFirstAdapter);


                        //地区点击事件
                        provinceAdapter.setOnItemClickListener((adapter, view1, position) -> areaVm.clickFirst(position));
                        cityAdapter.setOnItemClickListener((adapter, view12, position) -> areaVm.clickRadioSecond(position));

                        //阶段点击事件
                        stageFirstAdapter.setOnItemClickListener((adapter, view1, position) -> stageVm.clickFirst(position));

                        //类型点击事件
                        typeFirstAdapter.setOnItemClickListener((adapter, view1, position) -> typeVm.clickFirst(position));

                        tvTimeStart.setOnClickListener(v -> {
                            showTimeDialog(tvTimeStart);
                        });
                        tvTimeEnd.setOnClickListener(v -> {
                            showTimeDialog(tvTimeEnd);
                        });

                        view.findViewById(R.id.tv_negative).setOnClickListener(v -> {
                            popDismiss();
                        });
                        view.findViewById(R.id.tv_positive).setOnClickListener(v -> {
                            switch (projectListVm.currentSelect) {
                                case 1:
                                    ProjectFilterBean projectFilterArea = areaVm.getProjectFilterArea();
                                    if (projectFilterArea != null) {
                                        projectListVm.province = projectFilterArea.getProvince();
                                        projectListVm.city = projectFilterArea.getCity();

                                        if (projectListVm.city.size() == 0) {
                                            ToastUtils.showShort("请选择城市");
                                            return;
                                        }
                                    } else {
                                        projectListVm.province = null;
                                        projectListVm.city = null;
                                    }
                                    break;
                                case 2:
                                    projectListVm.phases = stageVm.getStageId();
                                    break;
                                case 3:
                                    projectListVm.type = typeVm.getSecondIds();
                                    break;
                                case 4:
                                    projectListVm.stime = tvTimeStart.getText().toString();
                                    projectListVm.etime = tvTimeEnd.getText().toString();
                                    break;
                                case 5:
                                    if (cbSubscribe.isChecked() && cbUnsubscribe.isChecked()) {
                                        projectListVm.warrant_status = null;
                                        tvSubscribe.setText("认购");
                                    } else if (cbSubscribe.isChecked()) {
                                        projectListVm.warrant_status = "1";
                                        tvSubscribe.setText(R.string.subscribed);
                                    } else if (cbUnsubscribe.isChecked()) {
                                        projectListVm.warrant_status = "0";
                                        tvSubscribe.setText(R.string.unsubscribe);
                                    } else {
                                        projectListVm.warrant_status = null;
                                        tvSubscribe.setText("认购");
                                    }
                                    break;
                            }
                            projectListVm.projectList();
                            popDismiss();
                        });
                    })
                     .create();
        }
        switch (projectListVm.currentSelect) {
            case 1:
                provinceAdapter.setNewData(dataArea);
                llArea.setVisibility(View.VISIBLE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.GONE);
                tvArea.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivExpand.setImageResource(R.drawable.icon_bottom_blue);
                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                ivStage.setImageResource(R.drawable.icon_bottom);
                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                ivType.setImageResource(R.drawable.icon_bottom);
                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                ivTime.setImageResource(R.drawable.icon_bottom);
                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                break;
            case 2:
                stageFirstAdapter.setNewData(dataStage);
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.VISIBLE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.GONE);
                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                ivExpand.setImageResource(R.drawable.icon_bottom);
                tvStage.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivStage.setImageResource(R.drawable.icon_bottom_blue);
                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                ivType.setImageResource(R.drawable.icon_bottom);
                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                ivTime.setImageResource(R.drawable.icon_bottom);
                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                break;
            case 3:
                typeFirstAdapter.setNewData(dataType);
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.VISIBLE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.GONE);
                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                ivExpand.setImageResource(R.drawable.icon_bottom);
                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                ivStage.setImageResource(R.drawable.icon_bottom);
                tvType.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivType.setImageResource(R.drawable.icon_bottom_blue);
                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                ivTime.setImageResource(R.drawable.icon_bottom);
                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                break;
            case 4:
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.VISIBLE);
                flSubscription.setVisibility(View.GONE);
                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                ivExpand.setImageResource(R.drawable.icon_bottom);
                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                ivStage.setImageResource(R.drawable.icon_bottom);
                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                ivType.setImageResource(R.drawable.icon_bottom);
                tvTime.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivTime.setImageResource(R.drawable.icon_bottom_blue);
                tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
                ivSubscribe.setImageResource(R.drawable.icon_bottom);
                break;
            case 5:
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.VISIBLE);
                tvArea.setTextColor(getResources().getColor(R.color.colorTv));
                ivExpand.setImageResource(R.drawable.icon_bottom);
                tvStage.setTextColor(getResources().getColor(R.color.colorTv));
                ivStage.setImageResource(R.drawable.icon_bottom);
                tvType.setTextColor(getResources().getColor(R.color.colorTv));
                ivType.setImageResource(R.drawable.icon_bottom);
                tvTime.setTextColor(getResources().getColor(R.color.colorTv));
                ivTime.setImageResource(R.drawable.icon_bottom);
                tvSubscribe.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivSubscribe.setImageResource(R.drawable.icon_bottom_blue);
                break;
        }
        if (!popupWindowArea.isShowing()) {
            popupWindowArea.showAsDropDown(line, 0, 0);
            over.setVisibility(View.VISIBLE);
        }
    }

    private void popDismiss() {
        over.setVisibility(View.GONE);
        tvArea.setTextColor(getResources().getColor(R.color.colorTv));
        ivExpand.setImageResource(R.drawable.icon_bottom);
        tvStage.setTextColor(getResources().getColor(R.color.colorTv));
        ivStage.setImageResource(R.drawable.icon_bottom);
        tvType.setTextColor(getResources().getColor(R.color.colorTv));
        ivType.setImageResource(R.drawable.icon_bottom);
        tvTime.setTextColor(getResources().getColor(R.color.colorTv));
        ivTime.setImageResource(R.drawable.icon_bottom);
        tvSubscribe.setTextColor(getResources().getColor(R.color.colorTv));
        ivSubscribe.setImageResource(R.drawable.icon_bottom);
        if (popupWindowArea != null && popupWindowArea.isShowing())
            popupWindowArea.dismiss();
    }

    private void initStage() {
        stageVm.stageLiveData.observe(this, stageBeans -> {
            if (!stageVm.inited) {
                stageVm.setStageInit();
            } else {
                projectListVm.currentSelect = 2;
                showPopArea(null, stageBeans, null);
            }
        });

        stageVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    /**
     * 初始化阶段
     */
    private void getStageData() {
        if (stageVm.stageLiveData.getValue() == null) {
            stageVm.getStage();
        } else {
            projectListVm.currentSelect = 2;
            showPopArea(null, stageVm.stageLiveData.getValue(), null);
        }
    }

    /**
     * 初始化地区
     */
    private void initArea() {
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            if (!areaVm.provinceInited) {
                //首次加载，未添加全部，
                areaVm.setProjectProvinceInit();

            } else {
                projectListVm.currentSelect = 1;
                showPopArea(provinceBeans, null, null);
            }
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            areaVm.setProjectCityInit();
            if (cityBeans != null && cityBeans.size() > 0 && (cityBeans.get(0).getName().equals("全部") || cityBeans.get(0).getName().equals("全国"))) {
                //刷新市区
                cityAdapter.setNewData(cityBeans);
            }
        });
        areaVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    private void getAreaData() {
        if (areaVm.provinceLiveData.getValue() == null) {
            areaVm.getProvince();
        } else {
            projectListVm.currentSelect = 1;
            showPopArea(areaVm.provinceLiveData.getValue(), null, null);
        }
    }

    /**
     * 初始化分类
     */
    private void initTypes() {


        typeVm.typeFirstLiveData.observe(this, typeBeans -> {
            if (!typeVm.firstInited) {
                //首次加载，未添加全部，
                typeVm.setTypeFirstInit();
            } else {
                projectListVm.currentSelect = 3;
                showPopArea(null, null, typeBeans);
            }
        });

        typeVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    private void getTypesData() {
        if (typeVm.typeFirstLiveData.getValue() == null) {
            typeVm.getTypesFirst();
        } else {
            projectListVm.currentSelect = 3;
            showPopArea(null, null, typeVm.typeFirstLiveData.getValue());
        }
    }

    /**
     * 时间选择器
     */
    private void showTimeDialog(TextView tv) {
        CustomDatePicker customDatePicker = new CustomDatePicker(this, tv::setText, "yyyy-MM-dd", "2010-01-01", "2040-12-31");
        customDatePicker.showSpecificTime(false);
        customDatePicker.show(TimeUtils.getNowString());
    }

    @Override
    public void onEmptyClick() {
        projectListVm.province = null;
        projectListVm.city = null;
        projectListVm.phases = null;
        projectListVm.type = null;
        projectListVm.stime = null;
        projectListVm.etime = null;
        projectListVm.warrant_status = null;
        projectListVm.projectList();
    }
}
