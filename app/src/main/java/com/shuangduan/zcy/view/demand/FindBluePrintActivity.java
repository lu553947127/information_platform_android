package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.vm.DemandReleaseVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class 需求资讯-找方案
 * @time
 * @change
 * @chang
 * @class
 */
public class FindBluePrintActivity extends BaseActivity {

    @BindView(R.id.scroll)
    AdaptationScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private DemandReleaseVm vm;

    private SimpleDateFormat f;
    private Calendar c;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_blue_print;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {
            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(230);
            //设置折叠标题背景颜色
            private int color = ContextCompat.getColor(FindBluePrintActivity.this, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    LogUtils.e(lastScrollY);
                    scrollY = Math.min(h, scrollY);
                    mScrollY_2 = scrollY > h ? h : scrollY;
                    toolbar.setAlpha(1f * mScrollY_2 / h);
                    toolbar.setBackgroundColor(((255 * mScrollY_2 / h) << 24) | color);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });

        f = new SimpleDateFormat("yyyy-MM-dd");
        c = Calendar.getInstance();


        vm = ViewModelProviders.of(this).get(DemandReleaseVm.class);

        vm.startTime = DateUtils.getTodayDate(c);

        tvStartTime.setText(vm.startTime);
    }


    @OnClick({R.id.iv_bar_back, R.id.iv_bar_back_new, R.id.tv_start_time, R.id.tv_end_time})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
            case R.id.iv_bar_back_new:
                finish();
                break;
            case R.id.tv_start_time://项目周期（起始时间）
                showTimeDialog(tvStartTime, 0, vm.startTime);
                break;
            case R.id.tv_end_time://项目周期（结束时间）
                if (TextUtils.isEmpty(vm.startTime)) {
                    ToastUtils.showShort("请先选择起始时间");
                    return;
                }
                try {
                    c.setTime(Objects.requireNonNull(f.parse(vm.startTime)));
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    String endTime = StringUtils.isTrimEmpty(vm.endTime) ? f.format(c.getTime()) : vm.endTime;
                    showTimeDialog(tvEndTime, 1, endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 时间选择器
     */
    private void showTimeDialog(TextView tv, int type, String showTime) {
        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {

            if (type == 0) {
                if (!StringUtils.isTrimEmpty(vm.endTime)) {
                    int count = DateUtils.getGapCount(time, vm.endTime);
                    if (count <= 0) {
                        ToastUtils.showShort("结束时间必须大于开始时间");
                        return;
                    }
                }
                vm.startTime = time;
            } else {
                vm.endTime = time;
            }
            tv.setText(time);
        }, "yyyy-MM-dd", showTime, "2040-12-31");
        customDatePicker.showSpecificTime(false);
        customDatePicker.show(showTime);
    }

}
