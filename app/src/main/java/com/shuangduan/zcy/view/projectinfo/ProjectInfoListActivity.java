package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectInfoAdapter;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;
import com.shuangduan.zcy.adapter.SelectorSecondAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.BaseSelectorBean;
import com.shuangduan.zcy.vm.AreaVm;
import com.shuangduan.zcy.vm.ProjectListVm;
import com.shuangduan.zcy.vm.StageVm;
import com.shuangduan.zcy.vm.TypesVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  工程信息列表模式
 * @time 2019/7/12 16:27
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoListActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
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

    private ProjectListVm projectListVm;
    private StageVm stageVm;
    private AreaVm areaVm;
    private TypesVm typeVm;
    private ProjectInfoAdapter projectInfoAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_info_list;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[0]);

        projectListVm = ViewModelProviders.of(this).get(ProjectListVm.class);
        stageVm = ViewModelProviders.of(this).get(StageVm.class);
        areaVm = ViewModelProviders.of(this).get(AreaVm.class);
        typeVm = ViewModelProviders.of(this).get(TypesVm.class);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        projectInfoAdapter = new ProjectInfoAdapter(R.layout.item_project_info, null);
        rv.setAdapter(projectInfoAdapter);
        projectInfoAdapter.setOnItemClickListener((adapter, view, position) -> ActivityUtils.startActivity(ProjectDetailActivity.class));

        refresh.setEnableRefresh(false);
        refresh.setOnLoadMoreListener(refreshLayout -> {
            projectListVm.page++;
            projectListVm.projectList();
        });

        projectListVm.init();
        projectListVm.projectLiveData.observe(this, projectInfoBeans -> {
            projectListVm.page = projectInfoBeans.getPage();
            if (projectListVm.page == 1){
                //初始数据
                projectInfoAdapter.setNewData(projectInfoBeans.getList());
            }else if (projectListVm.page > 1){
                //加载更多的数据
                projectInfoAdapter.addData(projectInfoBeans.getList());
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.ll_area, R.id.ll_stage, R.id.ll_type, R.id.ll_time, R.id.ll_subscribe, R.id.over})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
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
                showPopArea(null, 4);
                break;
            case R.id.ll_subscribe:
                showPopArea(null, 5);
                break;
            case R.id.over:
                if (popupWindowArea != null && popupWindowArea.isShowing()){
                    popupWindowArea.dismiss();
                }
                over.setVisibility(View.GONE);
                break;
        }
    }

    private CommonPopupWindow popupWindowArea;
    private RecyclerView rvProvince;
    private RecyclerView rvCity;
    private RecyclerView rvStageFirst;
    private RecyclerView rvStageSecond;
    private RecyclerView rvTypeFirst;
    private RecyclerView rvTypeSecond;
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
    private SelectorSecondAdapter stageSecondAdapter;
    private SelectorFirstAdapter typeFirstAdapter;
    private SelectorSecondAdapter typeSecondAdapter;
    private void showPopArea(final List<BaseSelectorBean> data, int popType){
        if (popupWindowArea == null){
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
                        switch (popType){
                            case 1:
                                llArea.setVisibility(View.VISIBLE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.GONE);
                                break;
                            case 2:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.VISIBLE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.GONE);
                                break;
                            case 3:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.VISIBLE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.GONE);
                                break;
                            case 4:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.VISIBLE);
                                flSubscription.setVisibility(View.GONE);
                                break;
                            case 5:
                                llArea.setVisibility(View.GONE);
                                llStage.setVisibility(View.GONE);
                                llType.setVisibility(View.GONE);
                                rlTime.setVisibility(View.GONE);
                                flSubscription.setVisibility(View.VISIBLE);
                                break;
                        }

                        rvProvince = view.findViewById(R.id.rv_province);
                        rvCity = view.findViewById(R.id.rv_city);
                        rvStageFirst = view.findViewById(R.id.rv_stage_first);
                        rvStageSecond = view.findViewById(R.id.rv_stage_second);
                        rvTypeFirst = view.findViewById(R.id.rv_type_first);
                        rvTypeSecond = view.findViewById(R.id.rv_type_second);
                        rvProvince.setLayoutManager(new LinearLayoutManager(this));
                        rvCity.setLayoutManager(new LinearLayoutManager(this));
                        rvStageFirst.setLayoutManager(new LinearLayoutManager(this));
                        rvStageSecond.setLayoutManager(new LinearLayoutManager(this));
                        rvTypeFirst.setLayoutManager(new LinearLayoutManager(this));
                        rvTypeSecond.setLayoutManager(new LinearLayoutManager(this));
                        provinceAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
                        cityAdapter = new SelectorSecondAdapter(R.layout.item_city, null);
                        stageFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
                        stageSecondAdapter = new SelectorSecondAdapter(R.layout.item_city, null);
                        typeFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
                        typeSecondAdapter = new SelectorSecondAdapter(R.layout.item_city, null);
                        rvProvince.setAdapter(provinceAdapter);
                        rvCity.setAdapter(cityAdapter);
                        rvStageFirst.setAdapter(stageFirstAdapter);
                        rvStageSecond.setAdapter(stageSecondAdapter);
                        rvTypeFirst.setAdapter(typeFirstAdapter);
                        rvTypeSecond.setAdapter(typeSecondAdapter);

                        //地区点击事件
                        provinceAdapter.setOnItemClickListener((adapter, view1, position) -> areaVm.clickFirst(position));
                        cityAdapter.setOnItemClickListener((adapter, view12, position) -> areaVm.clickSecond(position));

                        //阶段点击事件
                        stageFirstAdapter.setOnItemClickListener((adapter, view1, position) -> stageVm.clickFirst(position));
                        stageSecondAdapter.setOnItemClickListener((adapter, view12, position) -> stageVm.clickSecond(position));

                        //类型点击事件
                        typeFirstAdapter.setOnItemClickListener((adapter, view1, position) -> typeVm.clickFirst(position));
                        typeSecondAdapter.setOnItemClickListener((adapter, view12, position) -> typeVm.clickSecond(position));

                        tvTimeStart.setOnClickListener(v -> {
                            showTimeDialog(tvTimeStart);
                        });
                        tvTimeEnd.setOnClickListener(v -> {
                            showTimeDialog(tvTimeEnd);
                        });

                        findViewById(R.id.tv_negative).setOnClickListener(v -> popupWindowArea.dismiss());
                        findViewById(R.id.tv_positive).setOnClickListener(v -> {
                            switch (popType){
                                case 1:
                                    int[] cityResult = areaVm.getResult();
                                    if (cityResult == null){
                                        projectListVm.province = null;
                                        projectListVm.city = null;
                                    }else {
                                        projectListVm.city = cityResult;
                                        projectListVm.province = String.valueOf(areaVm.getProvinceId());
                                    }
                                    break;
                                case 2:
                                    projectListVm.phases = String.valueOf(stageVm.getFirstId());
                                    break;
                                case 3:
                                    int[] typeResult = typeVm.getResult();
                                    if (typeResult == null){
                                        projectListVm.type = null;
                                    }else {
                                        projectListVm.city = typeResult;
                                    }
                                    break;
                                case 4:
                                    projectListVm.stime = tvTimeStart.getText().toString();
                                    projectListVm.etime = tvTimeEnd.getText().toString();
                                    break;
                                case 5:
                                    if (cbSubscribe.isChecked() && cbUnsubscribe.isChecked()){
                                        projectListVm.warrant_status = null;
                                    }else if (cbSubscribe.isChecked()){
                                        projectListVm.warrant_status = "1";
                                    }else if (cbUnsubscribe.isChecked()){
                                        projectListVm.warrant_status = "0";
                                    }else {
                                        projectListVm.warrant_status = null;
                                    }
                                    break;
                            }
                            popupWindowArea.dismiss();
                        });
                    })
                    .create();
        }
        switch (popType){
            case 1:
                provinceAdapter.setNewData(data);
                llArea.setVisibility(View.VISIBLE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.GONE);
                break;
            case 2:
                stageFirstAdapter.setNewData(data);
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.VISIBLE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.GONE);
                break;
            case 3:
                typeFirstAdapter.setNewData(data);
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.VISIBLE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.GONE);
                break;
            case 4:
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.VISIBLE);
                flSubscription.setVisibility(View.GONE);
                break;
            case 5:
                llArea.setVisibility(View.GONE);
                llStage.setVisibility(View.GONE);
                llType.setVisibility(View.GONE);
                rlTime.setVisibility(View.GONE);
                flSubscription.setVisibility(View.VISIBLE);
                break;
        }
        if (!popupWindowArea.isShowing()){
            popupWindowArea.showAsDropDown(line,0,0);
            over.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化阶段
     */
    private void getStageData(){
        if (stageVm.stageFirstLiveData == null){
            stageVm.init();
            stageVm.stageFirstLiveData.observe(this, stageBeans -> {
                List<BaseSelectorBean> list = new ArrayList<>();
                list.addAll(stageBeans);
                showPopArea(list, 2);
                stageVm.setProvinceInit();
                stageVm.stageSecondLiveData.observe(this, stageBeans1 -> {
                    stageVm.setCityInit();
                    //刷新二级列表
                    stageSecondAdapter.setNewData(stageBeans1);
                });
            });

            stageVm.pageStateLiveData.observe(this, s -> {
                switch (s){
                    case PageState.PAGE_LOADING:
                        showLoading();
                        break;
                    default:
                        hideLoading();
                        break;
                }
            });
        }else {
            List<BaseSelectorBean> list = new ArrayList<>();
            list.addAll(stageVm.stageFirstLiveData.getValue());
            showPopArea(list, 2);
        }
    }

    /**
     * 初始化地区
     */
    private void getAreaData(){
        if (areaVm.provinceLiveData == null){
            areaVm.init();
            areaVm.provinceLiveData.observe(this, provinceBeans -> {
                List<BaseSelectorBean> list = new ArrayList<>();
                list.addAll(provinceBeans);
                showPopArea(list, 1);
                areaVm.setProvinceInit();
                areaVm.cityLiveData.observe(this, cityBeans -> {
                    areaVm.setCityInit();
                    //刷新市区
                    cityAdapter.setNewData(cityBeans);
                });
            });

            areaVm.pageStateLiveData.observe(this, s -> {
                switch (s){
                    case PageState.PAGE_LOADING:
                        showLoading();
                        break;
                    default:
                        hideLoading();
                        break;
                }
            });
        }else {
            List<BaseSelectorBean> list = new ArrayList<>();
            list.addAll(areaVm.provinceLiveData.getValue());
            showPopArea(list, 1);
        }
    }

    /**
     * 初始化分类
     */
    private void getTypesData(){
        if (typeVm.typeFirstLiveData == null){
            typeVm.init();
            typeVm.typeFirstLiveData.observe(this, typeBeans -> {
                List<BaseSelectorBean> list = new ArrayList<>();
                list.addAll(typeBeans);
                showPopArea(list, 3);
                typeVm.setTypeFirstInit();
                typeVm.typeSecondLiveData.observe(this, typeBeans1 -> {
                    typeVm.setTypeSecondInit();
                    //刷新二级列表
                    typeSecondAdapter.setNewData(typeBeans1);
                });
            });

            typeVm.pageStateLiveData.observe(this, s -> {
                switch (s){
                    case PageState.PAGE_LOADING:
                        showLoading();
                        break;
                    default:
                        hideLoading();
                        break;
                }
            });
        }else {
            List<BaseSelectorBean> list = new ArrayList<>();
            list.addAll(typeVm.typeFirstLiveData.getValue());
            showPopArea(list, 3);
        }
    }

    /**
     * 时间选择器
     */
    private void showTimeDialog(TextView tv){
        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            tv.setText(time);
        }, "2010-01-01 00:00", "2040-12-31 00:00");
        customDatePicker.showSpecificTime(false);
        customDatePicker.show(TimeUtils.getNowString());
    }

}
