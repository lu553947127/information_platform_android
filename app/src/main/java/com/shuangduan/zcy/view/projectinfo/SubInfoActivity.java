package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.MineIncomeBean;
import com.shuangduan.zcy.model.bean.ProjectSubViewBean;
import com.shuangduan.zcy.vm.GoToSubVm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  认购信息
 * @time 2019/7/16 16:20
 * @change
 * @chang time
 * @class describe
 */
public class SubInfoActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_sub_person)
    TextView tvSubPerson;
    @BindView(R.id.tv_sub_time)
    TextView tvSubTime;
    @BindView(R.id.tv_hold_time)
    TextView tvHoldTime;
    @BindView(R.id.tv_read_people_num)
    TextView tvReadPeopleNum;
    @BindView(R.id.tv_expected_return)
    TextView tvExpectedReturn;
    @BindView(R.id.tv_generate_revenue)
    TextView tvGenerateRevenue;
    @BindView(R.id.chart)
    LineChart chart;
    private ArrayList<Entry> values;
    private String[] xShow;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_sub_info;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.subscription_msg));

        initChart();

        GoToSubVm goToSubVm = ViewModelProviders.of(this).get(GoToSubVm.class);
        goToSubVm.projectId = getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);
        goToSubVm.viewLiveData.observe(this, projectSubViewBean -> {
            ProjectSubViewBean.InfoBean info = projectSubViewBean.getInfo();
            tvSubPerson.setText(info.getUsername());
            tvSubTime.setText(info.getCreate_time());
            tvHoldTime.setText(String.format(getString(R.string.format_validity_period_less), info.getStart_time(), info.getEnd_time()));
            tvReadPeopleNum.setText(String.valueOf(info.getCount()));
            tvExpectedReturn.setText(info.getExpect_price());
            tvGenerateRevenue.setText(info.getIncome_price());

            List<ProjectSubViewBean.ListBean> list = projectSubViewBean.getList();
            values.clear();
            xShow = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ProjectSubViewBean.ListBean bean = list.get(i);
                values.add(new Entry(i, bean.getValue()));
                xShow[i] = bean.getTime();
            }
            IndexAxisValueFormatter indexAxisValueFormatter = new IndexAxisValueFormatter(xShow);
            chart.getXAxis().setValueFormatter(indexAxisValueFormatter);
            chart.getXAxis().setLabelCount(list.size());
            chart.getXAxis().setAxisMaximum(list.size());
            chart.getAxisLeft().setAxisMinimum(0);
            chart.getXAxis().setDrawLabels(true);//绘制标签  指x轴上的对应数值
            LineDataSet lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        });
        goToSubVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        goToSubVm.viewWarrant();
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
        }
    }

    private void initChart() {
        values = new ArrayList<>();
        LineDataSet set = new LineDataSet(values, "收益(元)");
        chart.animateXY(1500, 1500);
        chart.setNoDataText("没有数据啊");
        //关闭背景颜色
        chart.setDrawGridBackground(false);
        //线的颜色
        set.setColor(getResources().getColor(R.color.colorPrimary));
        //节点显示
        set.setDrawCircles(true);
        set.setDrawValues(true);
        //关闭简介
        chart.getDescription().setEnabled(false);
        //关闭手势
        chart.setTouchEnabled(false);
        //关闭x轴数值显示
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        chart.getXAxis().setDrawAxisLine(true);
        //关闭右侧Y轴
        chart.getAxisRight().setEnabled(false);
        chart.setData(new LineData(set));
    }

}
