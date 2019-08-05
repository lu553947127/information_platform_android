package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MineIncomeBean;
import com.shuangduan.zcy.vm.MineIncomeVm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_expected_return)
    TextView tvExpectedReturn;
    @BindView(R.id.tv_withdraw_income)
    TextView tvWithdrawIncome;
    @BindView(R.id.chart)
    LineChart chart;
    private MineIncomeVm mineIncomeVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_income;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.my_income));

        ArrayList<Entry> values = new ArrayList<>();
        LineDataSet set = new LineDataSet(values, "收益(万元)");
        chart.animateXY(1500, 1500);
        //关闭背景颜色
        chart.setDrawGridBackground(false);
        //线的颜色
        set.setColor(getResources().getColor(R.color.colorPrimary));
        //节点显示
        set.setDrawCircles(false);
        set.setDrawValues(false);
        //关闭简介
        chart.getDescription().setEnabled(false);
        //关闭手势
        chart.setTouchEnabled(false);
        //关闭x轴数值显示
        chart.getXAxis().setEnabled(false);
        //关闭右侧Y轴
        chart.getAxisRight().setEnabled(false);
        chart.setData(new LineData(set));

        mineIncomeVm = ViewModelProviders.of(this).get(MineIncomeVm.class);
        mineIncomeVm.incomeLiveData.observe(this, mineIncomeBean -> {
            MineIncomeBean.ProceedsBean proceeds = mineIncomeBean.getProceeds();
            tvExpectedReturn.setText(proceeds.getAll_funds());
            tvWithdrawIncome.setText(proceeds.getFunds());
            List<MineIncomeBean.ListBean> list = mineIncomeBean.getList();
            values.clear();
            for (MineIncomeBean.ListBean bean : list) {
//                values.add(new Entry(bean.getListtime(), bean.getPrice()));
            }
        });
        mineIncomeVm.pageStateLiveData.observe(this, s -> {
            switch (s){
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

    @OnClick({R.id.iv_bar_back, R.id.tv_read_detail})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_read_detail:
                break;
        }
    }
}
