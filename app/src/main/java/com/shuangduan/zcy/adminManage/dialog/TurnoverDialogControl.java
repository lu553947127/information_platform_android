package com.shuangduan.zcy.adminManage.dialog;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ArrayWheelAdapter;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.utils.KeyboardUtil;

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
    private int currentPosition;
    private ArrayWheelAdapter projectAdapter;
    private String use_count, start_date, entry_time, exit_time, accumulated_amortization, original_price, net_worth, planStr;

    public TurnoverDialogControl(BaseActivity context, TurnoverVm vm, TurnoverDetailListening listening) {
        super(context, vm);
        this.listening = listening;
        initData(vm);
    }

    @Override
    public int layoutId() {
        return R.layout.dialog_add_turnover;
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
        tvCancel.setOnClickListener(this);


        tsItemTwo.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                planStr = s.length() > 0 ? s.toString() : "";
                tsItemTwo.setValue(planStr);
            }
        });

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
    }

    //获取编辑详情数据
    public void setDetail(String planStr, String use_count, String start_date, String entry_time, String exit_time,
                          String accumulated_amortization, String original_price, String net_worth) {

        this.planStr = planStr;
        this.use_count = use_count;
        this.start_date = start_date;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
        this.accumulated_amortization = accumulated_amortization;
        this.original_price = original_price;
        this.net_worth = net_worth;

        tsItemTwo.getEditText().setText(planStr);
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
            case R.id.ts_item_two:
                showView(1, R.string.admin_selector_material_plan);
                break;
            case R.id.ts_item_three:
                showView(2, R.string.admin_input_material_num);
                break;
            case R.id.ts_item_four:
                showView(3, R.string.admin_selector_material_start_time);
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
            case R.id.tv_cancel://清空时间
                clearData(currentPosition);
                break;
        }
    }

    private void clearData(int position) {
        switch (position) {
            case 1:
                planStr = "";
                tsItemTwo.setValue(planStr);
                break;
            case 3:
                start_date = "";
                tsItemFour.setValue(start_date);
                break;
            case 4:
                entry_time = "";
                tsItemFive.setValue(entry_time);
                break;
            case 5:
                exit_time = "";
                tsItemSix.setValue(exit_time);
                break;
        }
    }

    private void next(int position) {
        switch (position) {
            case 1:
                showView(1, R.string.admin_selector_material_plan);
                break;
            case 2:
                showView(6, R.string.admin_input_material_amortize);
                break;
            case 3:
                showView(1, R.string.admin_input_material_num);
                break;
            case 4:
                start_date = sdf.format(selectedCalender.getTime());
                tsItemFour.setValue(start_date);
                showView(4, R.string.admin_selector_material_enter_time);
                break;
            case 5:
                entry_time = sdf.format(selectedCalender.getTime());
                tsItemFive.setValue(entry_time);
                showView(5, R.string.admin_selector_material_exit_time);
                break;
            case 6:
                if (StringUtils.isEmpty(entry_time)) {
                    ToastUtils.showShort("请先选择材料进场时间");
                    return;
                }
                exit_time = sdf.format(selectedCalender.getTime());
                tsItemSix.setValue(exit_time);
                showView(7, R.string.admin_input_material_original);
                break;
            case 7:
                showView(3, R.string.admin_selector_material_start_time);
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

        tsItemTwo.getEditText().setVisibility(position == 1 ? View.VISIBLE : View.GONE);
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
                wheelView.setAdapter(projectAdapter);
                break;
            case 3:
            case 4:
            case 5:
                if (KeyboardUtil.isSoftShowing(context)) {
                    KeyboardUtil.showORhideSoftKeyboard(context);
                }
                break;
            case 1:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemTwo.getEditText());
                tsItemTwo.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
            case 2:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemThree.getEditText());
                KeyboardUtil.RemoveDecimalPoints(tsItemThree.getEditText());
                tsItemThree.getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                break;
            case 6:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemSeven.getEditText());
                KeyboardUtil.RemoveDecimalPoints(tsItemSeven.getEditText());
                tsItemSeven.getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                break;
            case 7:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemEight.getEditText());
                KeyboardUtil.RemoveDecimalPoints(tsItemEight.getEditText());
                tsItemEight.getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                break;
            case 8:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemNine.getEditText());
                KeyboardUtil.RemoveDecimalPoints(tsItemNine.getEditText());
                tsItemNine.getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789."));
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


    @Override
    public void onDismiss(DialogInterface dialog) {
        if (KeyboardUtil.isSoftShowing(context)) {
            KeyboardUtil.showORhideSoftKeyboard(context);
        }
        listening.callInfo(planStr, use_count, start_date, entry_time, exit_time, accumulated_amortization, original_price, net_worth);
    }

    public interface TurnoverDetailListening {
        void callInfo(String planStr, String use_count, String start_date, String entry_time,
                      String exit_time, String accumulated_amortization, String original_price, String net_worth);
    }
}
