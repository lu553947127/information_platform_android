package com.shuangduan.zcy.view.demand;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.DemandReleaseUnitAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.model.bean.UnitBean;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.vm.DemandReleaseVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.XEditText;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class 需求资讯-找基地
 * @time
 * @change
 * @chang
 * @class
 */
public class FindFoundationActivity extends BaseActivity {

    @BindView(R.id.scroll)
    AdaptationScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;


    @BindView(R.id.et_materials_name)
    XEditText etMaterialsName;
    @BindView(R.id.et_material_number)
    XEditText etMaterialNum;

    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.et_existing_address)
    XEditText etExistingAddress;
    @BindView(R.id.et_need_address)
    XEditText etNeedAddress;
    @BindView(R.id.et_distance)
    XEditText etDistance;


    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    @BindView(R.id.radio)
    RadioGroup radioGroup;

    @BindView(R.id.et_name)
    XEditText etName;
    @BindView(R.id.et_phone)
    XEditText etPhone;

    @BindView(R.id.et_param)
    XEditText etParam;

    private DemandReleaseVm vm;

    private SimpleDateFormat f;
    private Calendar c;

    //是否需要改制 1是 2否
    private int isReform;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_foundation;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        //滑动布局滑动监听
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {
            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(210);
            //设置折叠标题背景颜色
            private int color = ContextCompat.getColor(FindFoundationActivity.this, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
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

        isReform = 1;
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.cb_yes:
                    isReform = 1;
                    break;
                case R.id.cb_no:
                    isReform = 2;
                    break;
            }
        });


        f = new SimpleDateFormat("yyyy-MM-dd");
        c = Calendar.getInstance();


        vm = ViewModelProviders.of(this).get(DemandReleaseVm.class);

        vm.startTime = DateUtils.getTodayDate(c);

        tvStartTime.setText(vm.startTime);

        //获取数量单位返回数据
        vm.unitLiveData.observe(this, unitBean -> {
            unitList = unitBean;
        });


        //发布找基地成功
        vm.liveData.observe(this, result -> {
            ToastUtils.showShort("发布找基地需求成功.");
            finish();
        });


        vm.getUnit();
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_back_new, R.id.tv_unit, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_event})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
            case R.id.iv_bar_back_new:
                finish();
                break;
            case R.id.tv_unit:
                getBottomSheetDialog(R.layout.dialog_is_grounding);
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
            case R.id.tv_event:
                int materialsNum;
                String materialName = etMaterialsName.getTrimmedString();
                try {
                    materialsNum = Integer.valueOf(etMaterialNum.getTrimmedString());
                } catch (Exception e) {
                    materialsNum = 0;
                }

                String existingAddress = etExistingAddress.getTrimmedString();
                String needAddress = etNeedAddress.getTrimmedString();
                String distance = etDistance.getTrimmedString();
                String name = etName.getTrimmedString();
                String phone = etPhone.getTrimmedString();
                String remark = etParam.getTrimmedString();

                vm.baseAdd(materialName, materialsNum, unitId, existingAddress, needAddress, distance, isReform, name, phone, remark);
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


    private List<UnitBean> unitList = new ArrayList<>();
    private int unitId;

    private void getBottomSheetDialog(int layout) {
        //底部滑动对话框
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(this, R.style.BottomSheetStyle);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(layout, null);
        //把布局添加进去
        btn_dialog.setContentView(dialog_view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView tvUnits = btn_dialog.findViewById(R.id.tv_title);
        Objects.requireNonNull(tvUnits).setText("选择单位");
        RecyclerView rvUnit = btn_dialog.findViewById(R.id.rv);
        Objects.requireNonNull(rvUnit).setLayoutManager(new LinearLayoutManager(this));
        DemandReleaseUnitAdapter unitAdapter = new DemandReleaseUnitAdapter(R.layout.adapter_selector_area_second, unitList);
        rvUnit.setAdapter(unitAdapter);
        unitAdapter.setOnItemClickListener((adapter, view, position) -> {
            unitId = unitList.get(position).getUnit_id();
            tvUnit.setText(unitList.get(position).getUnit_name());
            unitAdapter.setIsSelect(unitId);
            btn_dialog.dismiss();
        });
        if (unitId != 0) {
            unitAdapter.setIsSelect(unitId);
        }

        btn_dialog.show();
    }
}
