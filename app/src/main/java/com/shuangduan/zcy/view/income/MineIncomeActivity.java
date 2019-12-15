package com.shuangduan.zcy.view.income;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MineIncomeBean;
import com.shuangduan.zcy.view.mine.set.AboutOursActivity;
import com.shuangduan.zcy.vm.MineIncomeVm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的收益
 * @time 2019/8/5 16:11
 * @change
 * @chang time
 * @class describe
 */
public class MineIncomeActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_expected_return)
    TextView tvExpectedReturn;
    @BindView(R.id.tv_withdraw_income)
    TextView tvWithdrawIncome;
    @BindView(R.id.chart)
    LineChart chart;
    private MineIncomeVm mineIncomeVm;
    private ArrayList<Entry> values;
    private String[] xShow;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_income;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.my_income));
        tvBarRight.setText(getString(R.string.my_income_explain));
        initChart();

        mineIncomeVm = ViewModelProviders.of(this).get(MineIncomeVm.class);
        mineIncomeVm.incomeLiveData.observe(this, mineIncomeBean -> {
            MineIncomeBean.ProceedsBean proceeds = mineIncomeBean.getProceeds();
            tvExpectedReturn.setText(proceeds.getAll_funds() + "元");
            tvWithdrawIncome.setText(proceeds.getCoin() + "元");
            List<MineIncomeBean.ListBean> list = mineIncomeBean.getList();

            values.clear();
            xShow = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                MineIncomeBean.ListBean bean = list.get(i);
                values.add(new Entry(i + 1, bean.getPrice()));
                xShow[i] = bean.getListtime();
            }
            IndexAxisValueFormatter indexAxisValueFormatter = new IndexAxisValueFormatter(xShow);

            //设置X轴上每个竖线是否显示
            chart.getXAxis().setDrawGridLines(true);

            //设置x轴间距
            chart.getXAxis().setGranularity(1f);
            chart.getXAxis().setValueFormatter(indexAxisValueFormatter);
            chart.getXAxis().setLabelCount(list.size());
            chart.getXAxis().setAxisMaximum(list.size());
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
        mineIncomeVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        mineIncomeVm.myIncome();
    }

    private void initChart() {
        values = new ArrayList<>();
        LineDataSet set = new LineDataSet(values, "");

        Legend legend = chart.getLegend();
        legend .setEnabled(false);

        //设置不显示折线图左下角的标签
        chart.animateXY(1500, 1500);
        chart.setNoDataText("没有数据啊");
        //关闭背景颜色
        chart.setDrawGridBackground(false);
        //线的颜色
        set.setColor(getResources().getColor(R.color.color_6a5ff8));
        //节点显示
        set.setDrawCircles(true);
        set.setDrawValues(true);
        //设置线宽
        set.setLineWidth(1f);

        //关闭简介
        chart.getDescription().setEnabled(false);
        //关闭手势
        chart.setTouchEnabled(true);
        //关闭x轴数值显示
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        chart.getXAxis().setDrawAxisLine(true);
        chart.getAxisLeft().setAxisMinimum(0);

        //关闭右侧Y轴
        chart.getAxisRight().setEnabled(false);
        chart.setData(new LineData(set));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_read_detail, R.id.tv_bar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_read_detail:
                ActivityUtils.startActivity(IncomeClassifyActivity.class);
                break;
            case R.id.tv_bar_right:
                Bundle bundle = new Bundle();
                bundle.putString("income", "1");
                ActivityUtils.startActivity(bundle, AboutOursActivity.class);
                break;
        }
    }
}
