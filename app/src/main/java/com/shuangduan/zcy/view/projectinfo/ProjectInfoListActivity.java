package com.shuangduan.zcy.view.projectinfo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectInfoAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.utils.AlphaUtils;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;
import com.shuangduan.zcy.weight.selectorview.SelectorChildView;
import com.shuangduan.zcy.weight.selectorview.SelectorParentView;

import java.io.IOException;
import java.io.InputStream;
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

    private List<ProvinceBean> data;
    private List<CityBean> dataCity;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_info_list;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[0]);

        List<ProjectInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(new ProjectInfoBean());
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ProjectInfoAdapter projectInfoAdapter = new ProjectInfoAdapter(R.layout.item_project_info, list);
        rv.setAdapter(projectInfoAdapter);
        projectInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActivityUtils.startActivity(ProjectDetailActivity.class);
            }
        });

        refresh.setEnableLoadMore(false);
        refresh.setEnableRefresh(false);
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
    private SelectorParentView rvProvince;
    private SelectorChildView rvCity;
    private void showPopArea(final List<ProvinceBean> data){
        if (popupWindowArea == null){
            popupWindowArea = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.dialog_area)
                    .setOutsideTouchable(false)
                    .setBackGroundLevel(1f)
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(292))
                    .setViewOnclickListener((view, layoutResId) -> {
                        rvProvince = view.findViewById(R.id.rv_province);
                        rvCity = view.findViewById(R.id.rv_city);
                        rvProvince.setData(data);
                        dataCity = rvProvince.setCityData(0);
                        rvCity.setData(dataCity);

                        rvProvince.setOnDataChangeListener(position -> {
                            rvCity.setData(rvProvince.setCityData(position));
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
                rvProvince.setData(data);
                rvProvince.getAdapter().notifyDataSetChanged();
            }
            if (rvCity != null && rvCity.getAdapter() != null){
                dataCity = rvProvince.setCityData(0);
                rvCity.setData(dataCity);
                rvCity.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void getAreaData(){
        try {
            InputStream is = getAssets().open("china_city_data");
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            LogUtils.i(result);
            Gson gson = new Gson();
            data = gson.fromJson(result, new TypeToken<ArrayList<ProvinceBean>>(){}.getType());

            //初始化数据默认选中第一个
            data.get(0).setIsSelect(1);

            showPopArea(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getStageData(){
        data = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            List<CityBean> list = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                CityBean cityBean = new CityBean(0, "子阶段" + j + ":" + (i + 1));
                list.add(cityBean);
            }
            ProvinceBean provinceBean = new ProvinceBean(0, "主阶段" + (j + 1), list);
            data.add(provinceBean);
        }

        //初始化数据默认选中第一个
        data.get(0).setIsSelect(1);

        showPopArea(data);
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
