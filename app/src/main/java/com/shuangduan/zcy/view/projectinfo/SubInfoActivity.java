package com.shuangduan.zcy.view.projectinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.ProjectSubViewBean;
import com.shuangduan.zcy.view.WebViewActivity;
import com.shuangduan.zcy.vm.GoToSubVm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
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
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
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
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.subscription_msg));
        tvBarRight.setVisibility(View.GONE);
        ivBarRight.setImageResource(R.drawable.icon_help_white);
        initChart();

        GoToSubVm goToSubVm = ViewModelProviders.of(this).get(GoToSubVm.class);
        goToSubVm.projectId = getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);
        goToSubVm.viewLiveData.observe(this, projectSubViewBean -> {
            ProjectSubViewBean.InfoBean info = projectSubViewBean.getInfo();
            tvSubPerson.setText(info.getUsername());
            tvSubTime.setText(info.getCreate_time());
            tvHoldTime.setText(String.format(getString(R.string.format_validity_period_less), info.getStart_time(), info.getEnd_time()));
            tvReadPeopleNum.setText(String.valueOf(info.getCount()));
            tvExpectedReturn.setText(info.getExpect_price()+"元");
            tvGenerateRevenue.setText(info.getIncome_price()+"元");

            List<ProjectSubViewBean.ListBean> list = projectSubViewBean.getList();
            values.clear();
            xShow = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ProjectSubViewBean.ListBean bean = list.get(i);
                values.add(new Entry(i + 1, bean.getValue()));
                xShow[i] = bean.getTime();
            }
            IndexAxisValueFormatter indexAxisValueFormatter = new IndexAxisValueFormatter(xShow);

            //设置X轴上每个竖线是否显示
            chart.getXAxis().setDrawGridLines(true);
            //设置x轴间距
            chart.getXAxis().setGranularity(1f);

            chart.getXAxis().setValueFormatter(indexAxisValueFormatter);
            chart.getXAxis().setLabelCount(list.size());
            chart.getXAxis().setAxisMaximum(list.size());
            chart.getAxisLeft().setAxisMinimum(0);
            chart.getXAxis().setDrawLabels(true);//绘制标签  指x轴上的对应数值

            chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "月";
                }
            });


            LineDataSet lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();


            lineDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.valueOf(value);
                }
            });

        });
        goToSubVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        goToSubVm.viewWarrant();
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                bundle.putString("register", "warrant");
                ActivityUtils.startActivity(bundle, WebViewActivity.class);
                break;
        }
    }

    private void initChart() {
        values = new ArrayList<>();
        LineDataSet set = new LineDataSet(values, "");

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        chart.animateXY(1500, 1500);
        chart.setNoDataText("没有数据啊");
        //关闭背景颜色
        chart.setDrawGridBackground(false);
        //线的颜色
        set.setColor(getResources().getColor(R.color.color_6a5ff8));
        //设置线宽
        set.setLineWidth(1f);
        //节点显示
        set.setDrawCircles(true);
        set.setDrawValues(true);
        //关闭简介
        chart.getDescription().setEnabled(false);
        //关闭手势
        chart.setTouchEnabled(true);
        //关闭x轴数值显示
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        chart.getXAxis().setDrawAxisLine(true);
        //关闭右侧Y轴
        chart.getAxisRight().setEnabled(false);
        chart.setData(new LineData(set));
    }

}
