package com.shuangduan.zcy.adminManage.view.turnover.dialog;

import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ArrayWheelAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.utils.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.view.turnover.dialog$
 * @class TurnoverDialogControl$
 * @class describe
 * @time 2019/11/4 10:53
 * @change
 * @class 周转材料添加详情Dialog 控制器
 */
public class TurnoverDialogControl extends BaseAddInfoDialog implements DialogInterface.OnDismissListener, View.OnClickListener {

    private final TurnoverDetailListening listening;



    //使用计划ID
    private List<Integer> planIdList;

    //使用计划名称
    private List<String> planList;


    private int currentPosition;
    private ArrayWheelAdapter projectAdapter;
    private ArrayWheelAdapter lineAdapter;
    //当前滑动的角标值
    private int selectorIndex;

    private int unit_id, plan;
    private String use_count, start_date, entry_time, exit_time, accumulated_amortization, original_price, net_worth, unit, planStr;


    public TurnoverDialogControl(BaseActivity context, TurnoverVm vm, TurnoverDetailListening listening) {
        super(context,vm);
        this.listening = listening;
        initData(vm);
    }

    @Override
    public int layoutId() {
        return R.layout.dialog_add_turnover;
    }

    @Override
    public void initData(TurnoverVm vm) {
        super.initData(vm);
        planIdList = new ArrayList<>();
        planList = new ArrayList<>();
    }

    @Override
    public void initView() {
        super.initView();
        tsItemOne.setOnClickListener(this);
        tsItemTwo.setOnClickListener(this);
        tsItemThree.setOnClickListener(this);
        tsItemFour.setOnClickListener(this);
        tsItemFive.setOnClickListener(this);
        tsItemSix.setOnClickListener(this);
        tsItemSeven.setOnClickListener(this);
        tsItemEight.setOnClickListener(this);
        tsItemNine.setOnClickListener(this);
        tvPositive.setOnClickListener(this);


        tsItemThree.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                use_count = s.length() > 0 ? s.toString() : "";
                tsItemThree.setValue(use_count);
            }
        });
        tsItemSeven.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                accumulated_amortization = s.length() > 0 ? s.toString() : "";
                tsItemSeven.setValue(accumulated_amortization);
            }
        });
        tsItemEight.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                original_price = s.length() > 0 ? s.toString() : "";
                tsItemEight.setValue(original_price);
            }
        });
        tsItemNine.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                net_worth = s.length() > 0 ? s.toString() : "";
                tsItemNine.setValue(net_worth);
            }
        });

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

        tsItemOne.setValue(unit);
        tsItemTwo.setValue(planStr);
        tsItemThree.getEditText().setText(use_count);
        tsItemFour.setValue(start_date);
        tsItemFive.setValue(entry_time);
        tsItemSix.setValue(exit_time);
        tsItemSeven.getEditText().setText(accumulated_amortization);
        tsItemEight.getEditText().setText(original_price);
        tsItemNine.getEditText().setText(net_worth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts_item_one:
                showView(0, R.string.admin_selector_material_project);
                break;
            case R.id.ts_item_two:
                showView(1,  R.string.admin_selector_material_plan);
                break;
            case R.id.ts_item_three:
                showView(2, R.string.admin_input_material_num);
                break;
            case R.id.ts_item_four:
                showView(3,R.string.admin_selector_material_start_time);
                break;
            case R.id.ts_item_five:
                showView(4, R.string.admin_selector_material_enter_time);
                break;
            case R.id.ts_item_six:
                showView(5, R.string.admin_selector_material_exit_time);
                break;
            case R.id.ts_item_seven:
                showView(6, R.string.admin_input_material_amortize);
                break;
            case R.id.ts_item_eight:
                showView(7, R.string.admin_input_material_original);
                break;
            case R.id.tv_item_nine:
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
                tsItemOne.setValue(unit);
                showView(1,  R.string.admin_selector_material_plan);
                break;
            case 2:
                planStr = planList.get(selectorIndex);
                plan = planIdList.get(selectorIndex);
                tsItemTwo.setValue(planStr);
                showView(2,  R.string.admin_input_material_num);
                break;
            case 3:
                showView(3,R.string.admin_selector_material_start_time);
                break;
            case 4:
                start_date = sdf.format(selectedCalender.getTime());
                tsItemFour.setValue(start_date);
                showView(4, R.string.admin_selector_material_enter_time);
                break;
            case 5:
                entry_time = sdf.format(selectedCalender.getTime());
                tsItemFive.setValue(entry_time);
                showView(5,R.string.admin_selector_material_exit_time);
                break;
            case 6:
                exit_time = sdf.format(selectedCalender.getTime());
                tsItemSix.setValue(exit_time);
                showView(6, R.string.admin_input_material_amortize);
                break;
            case 7:
                showView(7,R.string.admin_input_material_original);
                break;
            case 8:
                showView(8, R.string.admin_input_material_value);
                break;
            case 9:
                dialog.dismiss();
                break;
        }
    }


    private void showView(int position, int titleRes) {
        this.currentPosition = position;

        tsItemThree.getEditText().setVisibility(position == 2 ? View.VISIBLE : View.GONE);
        tsItemSeven.getEditText().setVisibility(position == 6 ? View.VISIBLE : View.GONE);
        tsItemEight.getEditText().setVisibility(position == 7 ? View.VISIBLE : View.GONE);
        tsItemNine.getEditText().setVisibility(position == 8 ? View.VISIBLE : View.GONE);

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
                KeyboardUtil.showSoftInputFromWindow(context, tsItemThree.getEditText());
                break;
            case 6:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemSeven.getEditText());
                break;
            case 7:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemEight.getEditText());
                break;
            case 8:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemNine.getEditText());
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


}
