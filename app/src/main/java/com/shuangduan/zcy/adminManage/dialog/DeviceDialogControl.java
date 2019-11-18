package com.shuangduan.zcy.adminManage.dialog;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
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
public class DeviceDialogControl extends BaseAddInfoDialog implements DialogInterface.OnDismissListener, View.OnClickListener {

    private final DeviceDetailListening listening;

    private int currentPosition;
    private ArrayWheelAdapter projectAdapter;
    //当前滑动的角标值
    private int selectorIndex;

    private String start_date, brand, original_price, main_params, power, entry_time, exit_time, operator_name;

    @Override
    public int layoutId() {
        return R.layout.dialog_add_device;
    }


    public DeviceDialogControl(BaseActivity context, TurnoverVm vm, DeviceDetailListening listening) {
        super(context, vm);
        this.listening = listening;
        initData(vm);
    }

    @Override
    public void initData(TurnoverVm vm) {
        super.initData(vm);
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
                brand = s.length() > 0 ? s.toString() : "";
                tsItemThree.setValue(brand);
            }
        });
        tsItemFour.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                original_price = s.length() > 0 ? s.toString() : "";
                tsItemFour.setValue(original_price);
            }
        });
        tsItemFive.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                main_params = s.length() > 0 ? s.toString() : "";
                tsItemFive.setValue(main_params);
            }
        });
        tsItemSix.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                power = s.length() > 0 ? s.toString() : "";
                tsItemSix.setValue(power);
            }
        });

        tsItemNine.getEditText().addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                operator_name = s.length() > 0 ? s.toString() : "";
                tsItemNine.setValue(operator_name);
            }
        });

        dialog.setOnDismissListener(this);

        projectAdapter = new ArrayWheelAdapter(projectList);


        wheelView.setOnItemSelectedListener(index -> {
            this.selectorIndex = index;
        });
    }

    //获取编辑详情数据
    public void setDetail(String start_date, String brand, String original_price, String main_params, String power,
                          String entry_time, String exit_time, String operator_name) {
        this.start_date = start_date;
        this.brand = brand;
        this.original_price = original_price;
        this.main_params = main_params;
        this.power = power;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
        this.operator_name = operator_name;

        tsItemTwo.setValue(start_date);
        tsItemThree.getEditText().setText(brand);
        tsItemFour.getEditText().setText(original_price);
        tsItemFive.getEditText().setText(main_params);
        tsItemSix.getEditText().setText(power);
        tsItemSeven.setValue(entry_time);
        tsItemEight.setValue(exit_time);
        tsItemNine.getEditText().setText(operator_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts_item_one:
                showView(0, R.string.admin_selector_material_project);
                break;
            case R.id.ts_item_two:
                showView(1, R.string.admin_selector_material_start_time);
                break;
            case R.id.ts_item_three:
                showView(2, R.string.admin_input_material_brand);
                break;
            case R.id.ts_item_four:
                showView(3, R.string.admin_input_material_original_price);
                break;
            case R.id.ts_item_five:
                showView(4, R.string.admin_input_material_main_params);
                break;
            case R.id.ts_item_six:
                showView(5, R.string.admin_input_material_power);
                break;
            case R.id.ts_item_seven:
                showView(6, R.string.admin_input_device_material_entry_time);
                break;
            case R.id.ts_item_eight:
                showView(7, R.string.admin_input_device_material_exit_time);
                break;
            case R.id.tv_item_nine:
                showView(8, R.string.admin_input_device_material_operator_name);
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

                break;
            case 2:
                start_date = sdf.format(selectedCalender.getTime());
                tsItemTwo.setValue(start_date);
                showView(8, R.string.admin_input_device_material_operator_name);

//                showView(2, R.string.admin_input_material_brand);
                break;
            case 3:
                showView(1, R.string.admin_selector_material_start_time);

                break;
            case 4:
                showView(4, R.string.admin_input_material_main_params);
                break;
            case 5:
                showView(5, R.string.admin_input_material_power);
                break;
            case 6:
                showView(6, R.string.admin_input_device_material_entry_time);
                break;
            case 7:
                entry_time = sdf.format(selectedCalender.getTime());
                tsItemSeven.setValue(entry_time);
                showView(7, R.string.admin_input_device_material_exit_time);
                break;
            case 8:
                exit_time = sdf.format(selectedCalender.getTime());
                tsItemEight.setValue(exit_time);
                dialog.dismiss();
                break;
            case 9:
                showView(3, R.string.admin_input_material_original_price);
                break;
        }
    }


    private void showView(int position, int titleRes) {
        this.currentPosition = position;

        tsItemThree.getEditText().setVisibility(position == 2 ? View.VISIBLE : View.GONE);
        tsItemFour.getEditText().setVisibility(position == 3 ? View.VISIBLE : View.GONE);
        tsItemFive.getEditText().setVisibility(position == 4 ? View.VISIBLE : View.GONE);
        tsItemSix.getEditText().setVisibility(position == 5 ? View.VISIBLE : View.GONE);
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
                KeyboardUtil.showSoftInputFromWindow(context, tsItemFour.getEditText());
                break;
            case 4:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemFive.getEditText());
                tsItemFive.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 5:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemSix.getEditText());
//                tsItemSix.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 2:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemThree.getEditText());
                tsItemThree.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 1:
            case 6:
            case 7:
                if (KeyboardUtil.isSoftShowing(context)) {
                    KeyboardUtil.showORhideSoftKeyboard(context);
                }
                break;
            case 8:
                KeyboardUtil.showSoftInputFromWindow(context, tsItemNine.getEditText());
                tsItemNine.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
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
        wheelView.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        //时间选择器显示隐藏
        rlDate.setVisibility(position == 1 || position == 6 || position == 7 ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        if (KeyboardUtil.isSoftShowing(context)) {
            KeyboardUtil.showORhideSoftKeyboard(context);
        }
        listening.callInfo(brand, start_date, operator_name, original_price, main_params, power, entry_time, exit_time);
    }


    public interface DeviceDetailListening {
        void callInfo(String brand, String start_date, String operator_name, String original_price, String main_params,
                      String power, String entry_time, String exit_time);
    }
}
