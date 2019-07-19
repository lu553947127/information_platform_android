package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectInfoAdapter;
import com.shuangduan.zcy.adapter.SelectorSecondAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.vm.ProjectListVm;
import com.shuangduan.zcy.vm.StageVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;

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

                break;
            case R.id.ll_stage:
                getStageData();
                break;
            case R.id.ll_type:
                break;
            case R.id.ll_time:
                showTimeDialog();
                break;
            case R.id.ll_subscribe:
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
    private SelectorFirstAdapter provinceAdapter;
    private SelectorSecondAdapter cityAdapter;
    private void showPopArea(final List<StageBean> data){
        if (popupWindowArea == null){
            popupWindowArea = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.dialog_area)
                    .setOutsideTouchable(false)
                    .setBackGroundLevel(1f)
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(292))
                    .setViewOnclickListener((view, layoutResId) -> {
                        rvProvince = view.findViewById(R.id.rv_province);
                        rvCity = view.findViewById(R.id.rv_city);
                        rvProvince.setLayoutManager(new LinearLayoutManager(this));
                        rvCity.setLayoutManager(new LinearLayoutManager(this));
                        provinceAdapter = new SelectorFirstAdapter(R.layout.item_province, data);
                        cityAdapter = new SelectorSecondAdapter(R.layout.item_city, null);
                        rvProvince.setAdapter(provinceAdapter);
                        rvCity.setAdapter(cityAdapter);

                        provinceAdapter.setOnItemClickListener((adapter, view1, position) -> {
                            stageVm.clickFirst(position);
                            stageVm.stageSecondLiveData.observe(ProjectInfoListActivity.this, stageBeans -> {
                                cityAdapter.setNewData(stageBeans);
                            });
                        });
                        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                stageVm.clickSecond(position);
                            }
                        });
                    })
                    .create();
        }
        if (!popupWindowArea.isShowing()){
            popupWindowArea.showAsDropDown(line,0,0);
            over.setVisibility(View.VISIBLE);
        }else {
            //点击类型是切换，直接刷新数据
            if (rvProvince != null && rvProvince.getAdapter() != null){
                provinceAdapter.setNewData(data);
            }
        }
    }

    private void getStageData(){
        stageVm.init();
        stageVm.stageFirstLiveData.observe(this, stageBeans -> {
            showPopArea(stageBeans);
        });
    }

    /**
     * 时间选择器
     */
    private void showTimeDialog(){
        CustomDatePicker customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {

            }
        }, "2010-01-01 00:00", "2040-12-31 00:00");
        customDatePicker.showSpecificTime(false);
        customDatePicker.show("2019-01-01 00:00");
    }

}
