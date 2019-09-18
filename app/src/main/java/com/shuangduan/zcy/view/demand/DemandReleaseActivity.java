package com.shuangduan.zcy.view.demand;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.DemandReleaseVm;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    @BindView(R.id.edt_owner)
    EditText edtOwner;
    @BindView(R.id.fl_owner)
    FrameLayout flOwner;
    @BindView(R.id.edt_des)
    EditText edtDes;
    @BindView(R.id.fl_des)
    FrameLayout flDes;
    private DemandReleaseVm demandReleaseVm;
    private BottomSheetDialogs btn_dialog;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_demand_release;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvBarTitle.setText(getString(R.string.release));
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        cbSell.setChecked(true);
        demandReleaseVm = ViewModelProviders.of(this).get(DemandReleaseVm.class);
        tvTimeStart.setText( demandReleaseVm.startTime + " 至");
        demandReleaseVm.releaseLiveData.observe(this, demandReleaseBean -> {
            if (demandReleaseVm.releaseType == DemandReleaseVm.RELEASE_TYPE_RELATIONSHIP){
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.FIND_RELATIONSHIP_ID, demandReleaseBean.getId());
                ActivityUtils.startActivity(bundle, ReleaseRelationshipOrderActivity.class);
                finish();
            }else {
                ToastUtils.showShort(getString(R.string.release_success));
                finish();
            }
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
                getBottomWindow();
                btn_dialog.show();
//                if (popupWindow == null){
//                    popupWindow = new CommonPopupWindow.Builder(this)
//                            .setView(R.layout.dialog_release_demand)
//                            .setOutsideTouchable(true)
//                            .setBackGroundLevel(0.8f)
//                            .setWidthAndHeight(ConvertUtils.dp2px(260), ViewGroup.LayoutParams.WRAP_CONTENT)
//                            .setViewOnclickListener((popView, layoutResId) -> {
//                                popView.findViewById(R.id.tv_find_relationship).setOnClickListener(l -> {
//                                    tvReleaseType.setText(getString(R.string.find_relationship));
//                                    demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_RELATIONSHIP;
//                                    flTitle.setVisibility(View.VISIBLE);
//                                    flCommission.setVisibility(View.VISIBLE);
//                                    flDes.setVisibility(View.VISIBLE);
//                                    flMaterialName.setVisibility(View.GONE);
//                                    flDemandNum.setVisibility(View.GONE);
//                                    flDemandProject.setVisibility(View.GONE);
//                                    flProjectAddress.setVisibility(View.GONE);
//                                    flPriceAccept.setVisibility(View.GONE);
//                                    flContactsInfo.setVisibility(View.GONE);
//                                    flOwner.setVisibility(View.GONE);
//                                    flSupplyNum.setVisibility(View.GONE);
//                                    flSupplyStyle.setVisibility(View.GONE);
//                                    flSupplyAddress.setVisibility(View.GONE);
//                                    flSupplyPrice.setVisibility(View.GONE);
//                                    popupWindow.dismiss();
//                                });
//                                popView.findViewById(R.id.tv_find_substance).setOnClickListener(l -> {
//                                    tvReleaseType.setText(getString(R.string.find_substance));
//                                    demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_SUBSTANCE;
//                                    flTitle.setVisibility(View.GONE);
//                                    flCommission.setVisibility(View.GONE);
//                                    flDes.setVisibility(View.GONE);
//                                    flMaterialName.setVisibility(View.VISIBLE);
//                                    flDemandNum.setVisibility(View.VISIBLE);
//                                    flDemandProject.setVisibility(View.VISIBLE);
//                                    flProjectAddress.setVisibility(View.VISIBLE);
//                                    flPriceAccept.setVisibility(View.VISIBLE);
//                                    flContactsInfo.setVisibility(View.VISIBLE);
//                                    flOwner.setVisibility(View.VISIBLE);
//                                    flSupplyNum.setVisibility(View.GONE);
//                                    flSupplyStyle.setVisibility(View.GONE);
//                                    flSupplyAddress.setVisibility(View.GONE);
//                                    flSupplyPrice.setVisibility(View.GONE);
//                                    popupWindow.dismiss();
//                                });
//                                popView.findViewById(R.id.tv_find_buyer).setOnClickListener(l -> {
//                                    tvReleaseType.setText(getString(R.string.find_buyer));
//                                    demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_BUYER;
//                                    flTitle.setVisibility(View.GONE);
//                                    flCommission.setVisibility(View.GONE);
//                                    flDes.setVisibility(View.GONE);
//                                    flMaterialName.setVisibility(View.VISIBLE);
//                                    flDemandNum.setVisibility(View.GONE);
//                                    flDemandProject.setVisibility(View.GONE);
//                                    flProjectAddress.setVisibility(View.GONE);
//                                    flPriceAccept.setVisibility(View.GONE);
//                                    flContactsInfo.setVisibility(View.VISIBLE);
//                                    flOwner.setVisibility(View.VISIBLE);
//                                    flSupplyNum.setVisibility(View.VISIBLE);
//                                    flSupplyStyle.setVisibility(View.VISIBLE);
//                                    flSupplyAddress.setVisibility(View.VISIBLE);
//                                    flSupplyPrice.setVisibility(View.VISIBLE);
//                                    popupWindow.dismiss();
//                                });
//                            })
//                            .create();
//                }
//                if (!popupWindow.isShowing()){
//                    WindowManager.LayoutParams attributes = getWindow().getAttributes();
//                    attributes.alpha = 0.8f;
//                    getWindow().setAttributes(attributes);
//                    popupWindow.showAsDropDown(tvReleaseType);
//                }
                break;
            case R.id.cb_lease:
                demandReleaseVm.way = 2;
                cbSell.setChecked(false);
                break;
            case R.id.cb_sell:
                demandReleaseVm.way = 1;
                cbLease.setChecked(false);
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
                        if (TextUtils.isEmpty(edtMaterialName.getText())){
                            ToastUtils.showShort(getString(R.string.hint_material_name));
                            return;
                        }
                        if (TextUtils.isEmpty(edtDemandNum.getText())){
                            ToastUtils.showShort(getString(R.string.hint_demand_num));
                            return;
                        }
                        if (TextUtils.isEmpty(edtDemandProject.getText())){
                            ToastUtils.showShort(getString(R.string.hint_demand_project));
                            return;
                        }
                        if (TextUtils.isEmpty(edtProjectAddress.getText())){
                            ToastUtils.showShort(getString(R.string.hint_project_address));
                            return;
                        }
                        if (TextUtils.isEmpty(edtPriceAccept.getText())){
                            ToastUtils.showShort(getString(R.string.hint_price_accept));
                            return;
                        }
                        if (TextUtils.isEmpty(edtOwner.getText())){
                            ToastUtils.showShort(getString(R.string.hint_owner));
                            return;
                        }
                        if (TextUtils.isEmpty(edtContactsInfo.getText())){
                            ToastUtils.showShort(getString(R.string.hint_contact));
                            return;
                        }
                        demandReleaseVm.releaseSubstance(edtMaterialName.getText().toString(), edtDemandNum.getText().toString(), edtDemandProject.getText().toString(),
                                edtProjectAddress.getText().toString(), edtPriceAccept.getText().toString(), edtContactsInfo.getText().toString(), edtOwner.getText().toString());
                        break;
                    case DemandReleaseVm.RELEASE_TYPE_BUYER:
                        if (TextUtils.isEmpty(edtMaterialName.getText())){
                            ToastUtils.showShort(getString(R.string.hint_material_name));
                            return;
                        }
                        if (TextUtils.isEmpty(edtSupplyNum.getText())){
                            ToastUtils.showShort(getString(R.string.hint_supply_num));
                            return;
                        }
                        if (TextUtils.isEmpty(edtSupplyAddress.getText())){
                            ToastUtils.showShort(getString(R.string.hint_supply_address));
                            return;
                        }
                        if (TextUtils.isEmpty(edtSupplyPrice.getText())){
                            ToastUtils.showShort(getString(R.string.hint_price_supply));
                            return;
                        }
                        if (TextUtils.isEmpty(edtOwner.getText())){
                            ToastUtils.showShort(getString(R.string.hint_owner));
                            return;
                        }
                        if (TextUtils.isEmpty(edtContactsInfo.getText())){
                            ToastUtils.showShort(getString(R.string.hint_contact));
                            return;
                        }
                        demandReleaseVm.releaseBuyer(edtMaterialName.getText().toString(), edtSupplyNum.getText().toString(), edtSupplyAddress.getText().toString(),
                                edtSupplyPrice.getText().toString(), edtContactsInfo.getText().toString(), edtOwner.getText().toString());
                        break;
                }
                break;
        }
    }

    /**
     * 时间选择器
     */
    @SuppressLint("SimpleDateFormat")
    private void showTimeDialog(){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = c.getTime();
        f.format(tomorrow);

        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            demandReleaseVm.endTime = time;
            tvTimeEnd.setText(time);
        }, "yyyy-MM-dd", f.format(tomorrow) , "2100-12-31");
        customDatePicker.showSpecificTime(false);
        customDatePicker.show(TimeUtils.getNowString());
    }

    //底部弹出框
    @SuppressLint("RestrictedApi")
    private void getBottomWindow() {
        //底部滑动对话框
        btn_dialog = new BottomSheetDialogs(this);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(R.layout.dialog_release_demand, null);
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
        TextView tv_find_relationship=dialog_view.findViewById(R.id.tv_find_relationship);
        TextView tv_find_substance=dialog_view.findViewById(R.id.tv_find_substance);
        TextView tv_find_buyer=dialog_view.findViewById(R.id.tv_find_buyer);
        tv_find_relationship.setOnClickListener(view -> {
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
            flOwner.setVisibility(View.GONE);
            flSupplyNum.setVisibility(View.GONE);
            flSupplyStyle.setVisibility(View.GONE);
            flSupplyAddress.setVisibility(View.GONE);
            flSupplyPrice.setVisibility(View.GONE);
            btn_dialog.cancel();
        });
        tv_find_substance.setOnClickListener(view -> {
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
            flOwner.setVisibility(View.VISIBLE);
            flSupplyNum.setVisibility(View.GONE);
            flSupplyStyle.setVisibility(View.GONE);
            flSupplyAddress.setVisibility(View.GONE);
            flSupplyPrice.setVisibility(View.GONE);
            btn_dialog.cancel();
        });
        tv_find_buyer.setOnClickListener(v -> {
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
            flOwner.setVisibility(View.VISIBLE);
            flSupplyNum.setVisibility(View.VISIBLE);
            flSupplyStyle.setVisibility(View.VISIBLE);
            flSupplyAddress.setVisibility(View.VISIBLE);
            flSupplyPrice.setVisibility(View.VISIBLE);
            btn_dialog.cancel();
        });
    }
}
