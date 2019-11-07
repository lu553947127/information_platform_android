package com.shuangduan.zcy.adminManage.view.turnover.dialog;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ArrayWheelAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.weight.TurnoverSelectView;
import com.shuangduan.zcy.weight.WheelSlideView;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;
import com.shuangduan.zcy.weight.datepicker.DatePickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.view.turnover.dialog$
 * @class TurnoverDialogControl$
 * @class describe
 * @time 2019/11/4 10:53
 * @change
 * @class 周转材料添加详情Dialog 控制器
 */
public class TurnoverDialogControl implements DialogInterface.OnDismissListener, View.OnClickListener {

    private final BottomSheetDialogs dialog;
    private final View view;
    private final BaseActivity context;

    private final TurnoverDetailListening listening;

    //项目名称
    private List<String> projectList;
    //项目ID
    private List<Integer> projectIdList;


    //使用计划ID
    private List<Integer> planIdList;

    //使用计划名称
    private List<String> planList;

    private TurnoverSelectView tsProject;
    private TurnoverSelectView tsPlan;
    private TurnoverSelectView tsNum;
    private TurnoverSelectView tsStartTime;
    private TurnoverSelectView tsEnterTime;
    private TurnoverSelectView tsExitTime;
    private TurnoverSelectView tsAmortize;
    private TurnoverSelectView tsOriginal;
    private TurnoverSelectView tsValue;
    private TextView tvTitle;
    private WheelSlideView wheelView;
    private RelativeLayout rlDate;


    //时间样式
    private String timeStyle = "yyyy-MM-dd";
    private Calendar selectedCalender, startCalendar, endCalendar;

    private DatePickerView year_pv, month_pv, day_pv, hour_pv, minute_pv;
    private TextView hour_text, minute_text;
    private TextView tvPositive;

    private int currentPosition;
    private ArrayWheelAdapter projectAdapter;
    private ArrayWheelAdapter lineAdapter;
    //当前滑动的角标值
    private int selectorIndex;


    private int unit_id, plan;
    private String use_count, start_date, entry_time, exit_time, accumulated_amortization, original_price, net_worth, unit, planStr;
    private SimpleDateFormat sdf;

    public TurnoverDialogControl(BaseActivity context, TurnoverVm vm, TurnoverDetailListening listening) {
        this.context = context;
        this.listening = listening;
        //底部滑动对话框
        dialog = new BottomSheetDialogs(context, R.style.BottomSheetStyle);
        //设置自定view
        view = LayoutInflater.from(context).inflate(R.layout.dialog_add_turnover, null);
        //把布局添加进去
        dialog.setContentView(view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(vm);

        initCalendar();
    }

    //初始化日历
    private void initCalendar() {
        selectedCalender = Calendar.getInstance();
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        sdf = new SimpleDateFormat(timeStyle, Locale.CHINA);
        try {
            startCalendar.setTime(Objects.requireNonNull(sdf.parse("2010-01-01")));
            endCalendar.setTime(Objects.requireNonNull(sdf.parse("2040-12-31")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initData(TurnoverVm vm) {
        projectList = new ArrayList<>();
        projectIdList = new ArrayList<>();

        planIdList = new ArrayList<>();
        planList = new ArrayList<>();

        vm.turnoverName.observe(context, turnoverName -> {
            for (TurnoverNameBean item : turnoverName) {
                projectList.add(item.name);
                projectIdList.add(item.id);
            }
        });

        vm.projectListData();
    }


    public void initView() {

        tsProject = view.findViewById(R.id.ts_project);
        tsPlan = view.findViewById(R.id.ts_plan);
        tsNum = view.findViewById(R.id.ts_num);
        tsStartTime = view.findViewById(R.id.ts_start_time);
        tsEnterTime = view.findViewById(R.id.ts_enter_time);
        tsExitTime = view.findViewById(R.id.ts_exit_time);
        tsAmortize = view.findViewById(R.id.ts_amortize);
        tsOriginal = view.findViewById(R.id.ts_original);
        tsValue = view.findViewById(R.id.tv_value);

        tvTitle = view.findViewById(R.id.tv_dialog_item_title);
        wheelView = view.findViewById(R.id.wheel_view);


        //时间
        rlDate = view.findViewById(R.id.rl_date);
        year_pv = view.findViewById(R.id.year_pv);
        month_pv = view.findViewById(R.id.month_pv);
        day_pv = view.findViewById(R.id.day_pv);
        hour_pv = view.findViewById(R.id.hour_pv);
        minute_pv = view.findViewById(R.id.minute_pv);
        hour_text = view.findViewById(R.id.hour_text);
        minute_text = view.findViewById(R.id.minute_text);

        tvPositive = view.findViewById(R.id.tv_positive);


        wheelView.setDividerColor(0x000000);

        wheelView.setCyclic(false);
        wheelView.setLineSpacingMultiplier(2.2f);

        tsProject.setOnClickListener(this);
        tsPlan.setOnClickListener(this);
        tsNum.setOnClickListener(this);
        tsStartTime.setOnClickListener(this);
        tsEnterTime.setOnClickListener(this);
        tsExitTime.setOnClickListener(this);
        tsAmortize.setOnClickListener(this);
        tsOriginal.setOnClickListener(this);
        tsValue.setOnClickListener(this);
        tvPositive.setOnClickListener(this);

        tsNum.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                use_count = s.length() > 0 ? s.toString() : "";
                tsNum.setValue(use_count);
            }
        });
        tsAmortize.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                accumulated_amortization = s.length() > 0 ? s.toString() : "";
                tsAmortize.setValue(accumulated_amortization);
            }
        });
        tsOriginal.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                original_price = s.length() > 0 ? s.toString() : "";
                tsOriginal.setValue(original_price);
            }
        });
        tsValue.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                net_worth = s.length() > 0 ? s.toString() : "";
                tsValue.setValue(net_worth);
            }
        });


        showSpecificTime(false);
        show(TimeUtils.getNowString());

        dialog.setOnDismissListener(this);

        projectAdapter = new ArrayWheelAdapter(projectList);
        lineAdapter = new ArrayWheelAdapter(planList);

        wheelView.setOnItemSelectedListener(index -> {
            this.selectorIndex = index;
        });
    }

    //获取编辑详情数据
    public void setDetail(int unit_id, String unit, int plan, String planStr, String use_count, String start_date, String entry_time, String exit_time,
                          String accumulated_amortization, String original_price, String net_worth) {
        this.unit_id = unit_id;
        this.unit = unit;
        this.plan = plan;
        this.planStr = planStr;
        this.use_count = use_count;
        this.start_date = start_date;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
        this.accumulated_amortization = accumulated_amortization;
        this.original_price = original_price;
        this.net_worth = net_worth;

        tsProject.setValue(unit);
        tsPlan.setValue(planStr);
        tsNum.getEditText().setText(use_count);
        tsStartTime.setValue(start_date);
        tsEnterTime.setValue(entry_time);
        tsExitTime.setValue(exit_time);
        tsAmortize.getEditText().setText(accumulated_amortization);
        tsOriginal.getEditText().setText(original_price);
        tsValue.getEditText().setText(net_worth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts_project:
                showView(0, R.string.admin_selector_material_project);
                break;
            case R.id.ts_plan:
                showView(1, R.string.admin_selector_material_plan);
                break;
            case R.id.ts_num:
                showView(2, R.string.admin_input_material_num);
                break;
            case R.id.ts_start_time:
                showView(3, R.string.admin_selector_material_start_time);
                break;
            case R.id.ts_enter_time:
                showView(4, R.string.admin_selector_material_enter_time);
                break;
            case R.id.ts_exit_time:
                showView(5, R.string.admin_selector_material_exit_time);
                break;
            case R.id.ts_amortize:
                showView(6, R.string.admin_input_material_amortize);
                break;
            case R.id.ts_original:
                showView(7, R.string.admin_input_material_original);
                break;
            case R.id.tv_value:
                showView(8, R.string.admin_input_material_value);
                break;
            case R.id.tv_positive:
                if (currentPosition < 9) {
                    next(++currentPosition);
                }
                break;
        }
    }

    private void next(int position) {
        switch (position) {
            case 0:
                showView(0, R.string.admin_selector_material_project);
                break;
            case 1:
                unit = projectList.get(selectorIndex);
                unit_id = projectIdList.get(selectorIndex);
                tsProject.setValue(unit);
                showView(1, R.string.admin_selector_material_plan);
                break;
            case 2:
                planStr = planList.get(selectorIndex);
                plan = planIdList.get(selectorIndex);
                tsPlan.setValue(planStr);
                showView(2, R.string.admin_input_material_num);
                break;
            case 3:
//                use_count = tsNum.getValue();
//                tsNum.setValue(use_count);
                showView(3, R.string.admin_selector_material_start_time);
                break;
            case 4:
                start_date = sdf.format(selectedCalender.getTime());
                tsStartTime.setValue(start_date);
                showView(4, R.string.admin_selector_material_enter_time);
                break;
            case 5:
                entry_time = sdf.format(selectedCalender.getTime());
                tsEnterTime.setValue(entry_time);
                showView(5, R.string.admin_selector_material_exit_time);
                break;
            case 6:
                exit_time = sdf.format(selectedCalender.getTime());
                tsExitTime.setValue(exit_time);
                showView(6, R.string.admin_input_material_amortize);
                break;
            case 7:
//                accumulated_amortization = tsAmortize.getValue();
//                tsAmortize.setValue(accumulated_amortization);
                showView(7, R.string.admin_input_material_original);
                break;
            case 8:
//                original_price = tsOriginal.getValue();
//                tsOriginal.setValue(original_price);
                showView(8, R.string.admin_input_material_value);
                break;
            case 9:
//                net_worth = tsValue.getValue();
//                tsValue.setValue(net_worth);
                dialog.dismiss();
                break;
        }
    }


    private void showView(int position, int titleRes) {
        this.currentPosition = position;

        tsNum.getEditText().setVisibility(position == 2 ? View.VISIBLE : View.GONE);
        tsAmortize.getEditText().setVisibility(position == 6 ? View.VISIBLE : View.GONE);
        tsOriginal.getEditText().setVisibility(position == 7 ? View.VISIBLE : View.GONE);
        tsValue.getEditText().setVisibility(position == 8 ? View.VISIBLE : View.GONE);

        switch (position) {
            case 0:
                if (KeyboardUtil.isSoftShowing(context)) {
                    KeyboardUtil.showORhideSoftKeyboard(context);
                }
                wheelView.setItemsVisibleCount(7);
//                wheelView.setCurrentItem(2);
//                selectorIndex = 1;
                wheelView.setAdapter(projectAdapter);
                break;
            case 1:
                if (KeyboardUtil.isSoftShowing(context)) {
                    KeyboardUtil.showORhideSoftKeyboard(context);
                }
                wheelView.setItemsVisibleCount(5);
//                wheelView.setCurrentItem(1);
                tsPlan.showLine(true);
//                selectorIndex = 1;
                wheelView.setAdapter(lineAdapter);
                break;
            case 3:
            case 4:
            case 5:
                if (KeyboardUtil.isSoftShowing(context)) {
                    KeyboardUtil.showORhideSoftKeyboard(context);
                }
                break;
            case 2:
                KeyboardUtil.showSoftInputFromWindow(context, tsNum.getEditText());
                break;
            case 6:
                KeyboardUtil.showSoftInputFromWindow(context, tsAmortize.getEditText());
                break;
            case 7:
                KeyboardUtil.showSoftInputFromWindow(context, tsOriginal.getEditText());
                break;
            case 8:
                KeyboardUtil.showSoftInputFromWindow(context, tsValue.getEditText());
                break;
        }

        tvTitle.setText(titleRes);
        bottomView(position);
        showLine(position);
    }


    public void showDialog(int position, int titleRes) {
        showView(position, titleRes);
        dialog.show();
    }

    private void bottomView(int position) {
        //所属项目/预计下一步使用使用计划显示 隐藏
        wheelView.setVisibility(position == 0 || position == 1 ? View.VISIBLE : View.GONE);
        //时间选择器显示隐藏
        rlDate.setVisibility(position == 3 || position == 4 || position == 5 ? View.VISIBLE : View.GONE);
    }

    private void showLine(int position) {
        tsProject.showLine(position == 0);
        tsPlan.showLine(position == 1);
        tsNum.showLine(position == 2);
        tsStartTime.showLine(position == 3);
        tsEnterTime.showLine(position == 4);
        tsExitTime.showLine(position == 5);
        tsAmortize.showLine(position == 6);
        tsOriginal.showLine(position == 7);
        tsValue.showLine(position == 8);
    }

    public List<Integer> getPlanIdList() {
        return planIdList;
    }

    public List<String> getPlanList() {
        return planList;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (KeyboardUtil.isSoftShowing(context)) {
            KeyboardUtil.showORhideSoftKeyboard(context);
        }
        listening.callInfo(unit_id, unit, plan, planStr, use_count, start_date, entry_time, exit_time, accumulated_amortization, original_price, net_worth);
    }


    public interface TurnoverDetailListening {
        void callInfo(int unit_id, String unit, int plan, String planStr, String use_count, String start_date, String entry_time,
                      String exit_time, String accumulated_amortization, String original_price, String net_worth);
    }


    private int scrollUnits = CustomDatePicker.SCROLL_TYPE.HOUR.value + CustomDatePicker.SCROLL_TYPE.MINUTE.value;

    private int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
    private boolean spanYear, spanMon, spanDay, spanHour, spanMin;

    private static final int MAX_MINUTE = 59;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int MIN_HOUR = 0;
    private static final int MAX_MONTH = 12;

    private ArrayList<String> year, month, day, hour, minute;

    public enum SCROLL_TYPE {
        HOUR(1),
        MINUTE(2);

        SCROLL_TYPE(int value) {
            this.value = value;
        }

        public int value;
    }


    public void show(String time) {
        initParameter();
        initTimer();
        addListener();
        setSelectedTime(time);
    }


    /**
     * 设置日期控件默认选中的时间
     */
    public void setSelectedTime(String time) {
        String[] str = time.split(" ");
        String[] dateStr = str[0].split("-");

        year_pv.setSelected(dateStr[0]);
        selectedCalender.set(Calendar.YEAR, Integer.parseInt(dateStr[0]));

        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                month.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
        }
        month_pv.setData(month);
        month_pv.setSelected(dateStr[1]);
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(dateStr[1]) - 1);
        executeAnimator(month_pv);

        day.clear();
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                day.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
        }
        day_pv.setData(day);
        day_pv.setSelected(dateStr[2]);
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr[2]));
        executeAnimator(day_pv);

        if (str.length == 2) {
            String[] timeStr = str[1].split(":");

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.HOUR.value) == CustomDatePicker.SCROLL_TYPE.HOUR.value) {
                hour.clear();
                int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
                if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                    for (int i = startHour; i <= MAX_HOUR; i++) {
                        hour.add(formatTimeUnit(i));
                    }
                } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                    for (int i = MIN_HOUR; i <= endHour; i++) {
                        hour.add(formatTimeUnit(i));
                    }
                } else {
                    for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
                        hour.add(formatTimeUnit(i));
                    }
                }
                hour_pv.setData(hour);
                hour_pv.setSelected(timeStr[0]);
                selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr[0]));
                executeAnimator(hour_pv);
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) == CustomDatePicker.SCROLL_TYPE.MINUTE.value) {
                minute.clear();
                int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
                int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);
                if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                    for (int i = startMinute; i <= MAX_MINUTE; i++) {
                        minute.add(formatTimeUnit(i));
                    }
                } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                    for (int i = MIN_MINUTE; i <= endMinute; i++) {
                        minute.add(formatTimeUnit(i));
                    }
                } else {
                    for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
                        minute.add(formatTimeUnit(i));
                    }
                }
                minute_pv.setData(minute);
                minute_pv.setSelected(timeStr[1]);
                selectedCalender.set(Calendar.MINUTE, Integer.parseInt(timeStr[1]));
                executeAnimator(minute_pv);
            }
        }
        executeScroll();

    }


    private void initTimer() {
        initArrayList();
        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
            }
            for (int i = startMonth; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.HOUR.value) != CustomDatePicker.SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) != CustomDatePicker.SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanMon) {
            year.add(String.valueOf(startYear));
            for (int i = startMonth; i <= endMonth; i++) {
                month.add(formatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.HOUR.value) != CustomDatePicker.SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) != CustomDatePicker.SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanDay) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            for (int i = startDay; i <= endDay; i++) {
                day.add(formatTimeUnit(i));
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.HOUR.value) != CustomDatePicker.SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) != CustomDatePicker.SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanHour) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            day.add(formatTimeUnit(startDay));

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.HOUR.value) != CustomDatePicker.SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= endHour; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) != CustomDatePicker.SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanMin) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            day.add(formatTimeUnit(startDay));
            hour.add(formatTimeUnit(startHour));

            if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) != CustomDatePicker.SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= endMinute; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        }
        loadComponent();
    }

    private void loadComponent() {
        year_pv.setData(year);
        month_pv.setData(month);
        day_pv.setData(day);
        hour_pv.setData(hour);
        minute_pv.setData(minute);
        year_pv.setSelected(0);
        month_pv.setSelected(0);
        day_pv.setSelected(0);
        hour_pv.setSelected(0);
        minute_pv.setSelected(0);
        executeScroll();
    }

    private void executeScroll() {
        year_pv.setCanScroll(year.size() > 1);
        month_pv.setCanScroll(month.size() > 1);
        day_pv.setCanScroll(day.size() > 1);
        hour_pv.setCanScroll(hour.size() > 1 && (scrollUnits & CustomDatePicker.SCROLL_TYPE.HOUR.value) == CustomDatePicker.SCROLL_TYPE.HOUR.value);
        minute_pv.setCanScroll(minute.size() > 1 && (scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) == CustomDatePicker.SCROLL_TYPE.MINUTE.value);
    }


    private void addListener() {
        year_pv.setOnSelectListener((text, position) -> {
            selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
            monthChange();
        });

        month_pv.setOnSelectListener((text, position) -> {
            selectedCalender.set(Calendar.DAY_OF_MONTH, 1);
            selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
            dayChange();
        });

        day_pv.setOnSelectListener((text, position) -> {
            selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
            hourChange();
        });

        hour_pv.setOnSelectListener((text, position) -> {
            selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
            minuteChange();
        });

        minute_pv.setOnSelectListener((text, position) -> selectedCalender.set(Calendar.MINUTE, Integer.parseInt(text)));
    }

    private void monthChange() {
        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                month.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month.get(0)) - 1);
        month_pv.setData(month);
        month_pv.setSelected(0);
        executeAnimator(month_pv);

        month_pv.postDelayed(this::dayChange, 100);

    }


    private void dayChange() {
        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                day.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
        }

        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.get(0)));
        day_pv.setData(day);
        day_pv.setSelected(0);
        executeAnimator(day_pv);

        day_pv.postDelayed(this::hourChange, 100);

    }

    private void hourChange() {
        if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.HOUR.value) == CustomDatePicker.SCROLL_TYPE.HOUR.value) {
            hour.clear();
            int selectedYear = selectedCalender.get(Calendar.YEAR);
            int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
            int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                for (int i = MIN_HOUR; i <= endHour; i++) {
                    hour.add(formatTimeUnit(i));
                }
            } else {
                for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.get(0)));
            hour_pv.setData(hour);
            hour_pv.setSelected(0);
            executeAnimator(hour_pv);
        }

        hour_pv.postDelayed(this::minuteChange, 100);
    }

    private void minuteChange() {
        if ((scrollUnits & CustomDatePicker.SCROLL_TYPE.MINUTE.value) == CustomDatePicker.SCROLL_TYPE.MINUTE.value) {
            minute.clear();
            int selectedYear = selectedCalender.get(Calendar.YEAR);
            int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
            int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
            int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                for (int i = MIN_MINUTE; i <= endMinute; i++) {
                    minute.add(formatTimeUnit(i));
                }
            } else {
                for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
            selectedCalender.set(Calendar.MINUTE, Integer.parseInt(minute.get(0)));
            minute_pv.setData(minute);
            minute_pv.setSelected(0);
            executeAnimator(minute_pv);
        }
        executeScroll();
    }

    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + unit : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (day == null) day = new ArrayList<>();
        if (hour == null) hour = new ArrayList<>();
        if (minute == null) minute = new ArrayList<>();
        year.clear();
        month.clear();
        day.clear();
        hour.clear();
        minute.clear();
    }

    private void initParameter() {
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH) + 1;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        startMinute = startCalendar.get(Calendar.MINUTE);
        endYear = endCalendar.get(Calendar.YEAR);
        endMonth = endCalendar.get(Calendar.MONTH) + 1;
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        endMinute = endCalendar.get(Calendar.MINUTE);
        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);
        spanHour = (!spanDay) && (startHour != endHour);
        spanMin = (!spanHour) && (startMinute != endMinute);
        selectedCalender.setTime(startCalendar.getTime());
    }

    /**
     * 设置日期控件是否显示时和分
     */
    public void showSpecificTime(boolean show) {
        if (show) {
            disScrollUnit();
            hour_pv.setVisibility(View.VISIBLE);
            hour_text.setVisibility(View.VISIBLE);
            minute_pv.setVisibility(View.VISIBLE);
            minute_text.setVisibility(View.VISIBLE);
        } else {
            disScrollUnit(SCROLL_TYPE.HOUR, SCROLL_TYPE.MINUTE);
            hour_pv.setVisibility(View.GONE);
            hour_text.setVisibility(View.GONE);
            minute_pv.setVisibility(View.GONE);
            minute_text.setVisibility(View.GONE);
        }
    }

    private int disScrollUnit(SCROLL_TYPE... scroll_types) {
        if (scroll_types == null || scroll_types.length == 0) {
            scrollUnits = SCROLL_TYPE.HOUR.value + SCROLL_TYPE.MINUTE.value;
        } else {
            for (SCROLL_TYPE scroll_type : scroll_types) {
                scrollUnits ^= scroll_type.value;
            }
        }
        return scrollUnits;
    }


    /**
     * 验证字符串是否是一个合法的日期格式
     */
    private boolean isValidDate(String date, String template) {
        boolean convertSuccess = true;
        // 指定日期格式
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.CHINA);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            format.setLenient(false);
            format.parse(date);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
