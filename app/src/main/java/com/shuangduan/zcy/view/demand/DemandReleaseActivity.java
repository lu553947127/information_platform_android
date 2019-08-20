package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.DemandReleaseVm;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  发布需求
 * @time 2019/8/19 9:01
 * @change
 * @chang time
 * @class describe
 */
public class DemandReleaseActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_release_type)
    TextView tvReleaseType;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.fl_title)
    FrameLayout flTitle;
    @BindView(R.id.edt_commission)
    EditText edtCommission;
    @BindView(R.id.fl_commission)
    FrameLayout flCommission;
    @BindView(R.id.edt_material_name)
    EditText edtMaterialName;
    @BindView(R.id.fl_material_name)
    FrameLayout flMaterialName;
    @BindView(R.id.edt_demand_num)
    EditText edtDemandNum;
    @BindView(R.id.fl_demand_num)
    FrameLayout flDemandNum;
    @BindView(R.id.edt_demand_project)
    EditText edtDemandProject;
    @BindView(R.id.fl_demand_project)
    FrameLayout flDemandProject;
    @BindView(R.id.edt_project_address)
    EditText edtProjectAddress;
    @BindView(R.id.fl_project_address)
    FrameLayout flProjectAddress;
    @BindView(R.id.edt_price_accept)
    EditText edtPriceAccept;
    @BindView(R.id.fl_price_accept)
    FrameLayout flPriceAccept;
    @BindView(R.id.edt_supply_num)
    EditText edtSupplyNum;
    @BindView(R.id.fl_supply_num)
    FrameLayout flSupplyNum;
    @BindView(R.id.cb_lease)
    CheckBox cbLease;
    @BindView(R.id.cb_sell)
    CheckBox cbSell;
    @BindView(R.id.fl_supply_style)
    FrameLayout flSupplyStyle;
    @BindView(R.id.edt_supply_address)
    EditText edtSupplyAddress;
    @BindView(R.id.fl_supply_address)
    FrameLayout flSupplyAddress;
    @BindView(R.id.edt_supply_price)
    EditText edtSupplyPrice;
    @BindView(R.id.fl_supply_price)
    FrameLayout flSupplyPrice;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.edt_contacts_info)
    EditText edtContactsInfo;
    @BindView(R.id.fl_contacts_info)
    FrameLayout flContactsInfo;
    @BindView(R.id.edt_des)
    EditText edtDes;
    @BindView(R.id.fl_des)
    FrameLayout flDes;
    private DemandReleaseVm demandReleaseVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_demand_release;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvBarTitle.setText(getString(R.string.release));
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        demandReleaseVm = ViewModelProviders.of(this).get(DemandReleaseVm.class);
        tvTimeStart.setText( demandReleaseVm.startTime + " 至");
        demandReleaseVm.releaseLiveData.observe(this, demandReleaseBean -> {
            finish();
        });
        demandReleaseVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    private CommonPopupWindow popupWindow;
    @OnClick({R.id.iv_bar_back, R.id.tv_release_type, R.id.cb_lease, R.id.cb_sell, R.id.tv_time_end, R.id.tv_release})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_release_type:
                if (popupWindow == null){
                    popupWindow = new CommonPopupWindow.Builder(this)
                            .setView(R.layout.dialog_release_demand)
                            .setOutsideTouchable(true)
                            .setBackGroundLevel(0.8f)
                            .setWidthAndHeight(ConvertUtils.dp2px(260), ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setViewOnclickListener((popView, layoutResId) -> {
                                popView.findViewById(R.id.tv_find_relationship).setOnClickListener(l -> {
                                    tvReleaseType.setText(getString(R.string.find_relationship));
                                    demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_RELATIONSHIP;
                                    flTitle.setVisibility(View.VISIBLE);
                                    flCommission.setVisibility(View.VISIBLE);
                                    flDes.setVisibility(View.VISIBLE);
                                    flMaterialName.setVisibility(View.GONE);
                                    flDemandNum.setVisibility(View.GONE);
                                    flDemandProject.setVisibility(View.GONE);
                                    flProjectAddress.setVisibility(View.GONE);
                                    flPriceAccept.setVisibility(View.GONE);
                                    flContactsInfo.setVisibility(View.GONE);
                                    flSupplyNum.setVisibility(View.GONE);
                                    flSupplyStyle.setVisibility(View.GONE);
                                    flSupplyAddress.setVisibility(View.GONE);
                                    flSupplyPrice.setVisibility(View.GONE);
                                    popupWindow.dismiss();
                                });
                                popView.findViewById(R.id.tv_find_substance).setOnClickListener(l -> {
                                    tvReleaseType.setText(getString(R.string.find_substance));
                                    demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_SUBSTANCE;
                                    flTitle.setVisibility(View.GONE);
                                    flCommission.setVisibility(View.GONE);
                                    flDes.setVisibility(View.GONE);
                                    flMaterialName.setVisibility(View.VISIBLE);
                                    flDemandNum.setVisibility(View.VISIBLE);
                                    flDemandProject.setVisibility(View.VISIBLE);
                                    flProjectAddress.setVisibility(View.VISIBLE);
                                    flPriceAccept.setVisibility(View.VISIBLE);
                                    flContactsInfo.setVisibility(View.VISIBLE);
                                    flSupplyNum.setVisibility(View.GONE);
                                    flSupplyStyle.setVisibility(View.GONE);
                                    flSupplyAddress.setVisibility(View.GONE);
                                    flSupplyPrice.setVisibility(View.GONE);
                                    popupWindow.dismiss();
                                });
                                popView.findViewById(R.id.tv_find_buyer).setOnClickListener(l -> {
                                    tvReleaseType.setText(getString(R.string.find_buyer));
                                    demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_BUYER;
                                    flTitle.setVisibility(View.GONE);
                                    flCommission.setVisibility(View.GONE);
                                    flDes.setVisibility(View.GONE);
                                    flMaterialName.setVisibility(View.VISIBLE);
                                    flDemandNum.setVisibility(View.GONE);
                                    flDemandProject.setVisibility(View.GONE);
                                    flProjectAddress.setVisibility(View.GONE);
                                    flPriceAccept.setVisibility(View.GONE);
                                    flContactsInfo.setVisibility(View.VISIBLE);
                                    flSupplyNum.setVisibility(View.VISIBLE);
                                    flSupplyStyle.setVisibility(View.VISIBLE);
                                    flSupplyAddress.setVisibility(View.VISIBLE);
                                    flSupplyPrice.setVisibility(View.VISIBLE);
                                    popupWindow.dismiss();
                                });
                            })
                            .create();
                }
                if (!popupWindow.isShowing()){
                    WindowManager.LayoutParams attributes = getWindow().getAttributes();
                    attributes.alpha = 0.8f;
                    getWindow().setAttributes(attributes);
                    popupWindow.showAsDropDown(tvReleaseType);
                }
                break;
            case R.id.cb_lease:
                break;
            case R.id.cb_sell:
                break;
            case R.id.tv_time_end:
                showTimeDialog();
                break;
            case R.id.tv_release:
                switch (demandReleaseVm.releaseType){
                    case DemandReleaseVm.RELEASE_TYPE_RELATIONSHIP:
                        if (TextUtils.isEmpty(edtTitle.getText())){
                            ToastUtils.showShort(getString(R.string.hint_title));
                            return;
                        }
                        if (TextUtils.isEmpty(edtDes.getText())){
                            ToastUtils.showShort(getString(R.string.hint_des));
                            return;
                        }
                        if (TextUtils.isEmpty(edtCommission.getText())){
                            ToastUtils.showShort(getString(R.string.hint_commission));
                            return;
                        }
                        demandReleaseVm.releaseRelationShip(edtTitle.getText().toString(), edtDes.getText().toString(), edtCommission.getText().toString());
                        break;
                    case DemandReleaseVm.RELEASE_TYPE_SUBSTANCE:
                        demandReleaseVm.releaseSubstance();
                        break;
                    case DemandReleaseVm.RELEASE_TYPE_BUYER:
                        demandReleaseVm.releaseBuyer();
                        break;
                }
                break;
        }
    }

    /**
     * 时间选择器
     */
    private void showTimeDialog(){
        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            demandReleaseVm.endTime = time;
            tvTimeEnd.setText(time);
        }, "yyyy-MM-dd", demandReleaseVm.startTime, demandReleaseVm.selectEndTime);
        customDatePicker.showSpecificTime(false);
        customDatePicker.show(TimeUtils.getNowString());
    }
}
