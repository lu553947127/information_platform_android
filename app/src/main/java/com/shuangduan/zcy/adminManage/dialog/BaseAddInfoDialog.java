package com.shuangduan.zcy.adminManage.dialog;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
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
 * @class BaseAddInfoDialog$
 * @class describe
 * @time 2019/11/8 10:59
 * @change
 * @class describe
 */
public abstract class BaseAddInfoDialog {

    public final BaseActivity context;

    public final BottomSheetDialogs dialog;
    private final View view;

    public TurnoverSelectView tsItemOne;
    public TurnoverSelectView tsItemTwo;
    public TurnoverSelectView tsItemThree;
    public TurnoverSelectView tsItemFour;
    public TurnoverSelectView tsItemFive;
    public TurnoverSelectView tsItemSix;
    public TurnoverSelectView tsItemSeven;
    public TurnoverSelectView tsItemEight;
    public TurnoverSelectView tsItemNine;
    public TextView tvTitle;
    public WheelSlideView wheelView;
    public RelativeLayout rlDate;
    private DatePickerView year_pv, month_pv, day_pv, hour_pv, minute_pv;
    private TextView hour_text, minute_text;
    public TextView tvPositive;

    //时间样式
    private String timeStyle = "yyyy-MM-dd";
    public Calendar selectedCalender, startCalendar, endCalendar;

    public SimpleDateFormat sdf;

    //项目名称
    public List<String> projectList;
    //项目ID
    public List<Integer> projectIdList;

    public abstract int layoutId();

    public BaseAddInfoDialog(BaseActivity context, TurnoverVm vm) {
        this.context = context;

        //底部滑动对话框
        dialog = new BottomSheetDialogs(context, R.style.BottomSheetStyle);
        //设置自定view
        view = LayoutInflater.from(context).inflate(layoutId(), null);
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

        initCalendar();

        initData(vm);
    }

    public void initData(TurnoverVm vm) {
        projectList = new ArrayList<>();
        projectIdList = new ArrayList<>();

        vm.turnoverName.observe(context, turnoverName -> {
            for (TurnoverNameBean item : turnoverName) {
                projectList.add(item.name);
                projectIdList.add(item.id);
            }
        });

        vm.projectListData();
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

    public void showLine(int position) {
        tsItemOne.showLine(position == 0);
        tsItemTwo.showLine(position == 1);
        tsItemThree.showLine(position == 2);
        tsItemFour.showLine(position == 3);
        tsItemFive.showLine(position == 4);
        tsItemSix.showLine(position == 5);
        tsItemSeven.showLine(position == 6);
        tsItemEight.showLine(position == 7);
        tsItemNine.showLine(position == 8);
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

    protected void initView() {
        tsItemOne = view.findViewById(R.id.ts_item_one);
        tsItemTwo = view.findViewById(R.id.ts_item_two);
        tsItemThree = view.findViewById(R.id.ts_item_three);
        tsItemFour = view.findViewById(R.id.ts_item_four);
        tsItemFive = view.findViewById(R.id.ts_item_five);
        tsItemSix = view.findViewById(R.id.ts_item_six);
        tsItemSeven = view.findViewById(R.id.ts_item_seven);
        tsItemEight = view.findViewById(R.id.ts_item_eight);
        tsItemNine = view.findViewById(R.id.tv_item_nine);

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

        showSpecificTime(false);
        show(TimeUtils.getNowString());

    }

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
