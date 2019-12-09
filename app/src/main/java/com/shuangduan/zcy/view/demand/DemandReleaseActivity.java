package com.shuangduan.zcy.view.demand;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.dialog.UnitDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.view.mine.AuthenticationActivity;
import com.shuangduan.zcy.vm.DemandReleaseVm;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class 需求广场
 * @time 2019/8/19 9:01
 * @change
 * @chang time
 * @class describe
 */
public class DemandReleaseActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_release_type)
    TextView tvReleaseType;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.fl_title)
    FrameLayout flTitle;
    @BindView(R.id.edt_commission)
    EditText edtCommission;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
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
    @BindView(R.id.tv_demand_num)
    TextView tvDemandNum;
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
    @BindView(R.id.tv_supply_num)
    TextView tvSupplyNum;
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
    @BindView(R.id.ll_authentication)
    LinearLayout llAuthentication;
    @BindView(R.id.marquee)
    TextView tvMarquee;
    private DemandReleaseVm demandReleaseVm;
    private BottomSheetDialogs btn_dialog;
    int demand_num = 0;
    int supply_num = 0;
    private String type;
    private CustomDatePicker customDatePicker;
    private String tomorrow;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_demand_release;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n,SimpleDateFormat")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvBarTitle.setText("我要找关系");
        type = getIntent().getStringExtra("type");

        cbSell.setChecked(true);


        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrowDate = c.getTime();
        tomorrow = f.format(tomorrowDate);


        demandReleaseVm = ViewModelProviders.of(this).get(DemandReleaseVm.class);
        tvTimeStart.setText(demandReleaseVm.startTime + " 至");
        demandReleaseVm.releaseLiveData.observe(this, demandReleaseBean -> {
            if (demandReleaseVm.releaseType == DemandReleaseVm.RELEASE_TYPE_RELATIONSHIP) {
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.FIND_RELATIONSHIP_ID, demandReleaseBean.getId());
                ActivityUtils.startActivity(bundle, ReleaseRelationshipOrderActivity.class);
                finish();
            } else {
                ToastUtils.showShort(getString(R.string.release_success));
                finish();
            }
        });
        demandReleaseVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        KeyboardUtil.RemoveDecimalPoints(edtCommission);
        KeyboardUtil.RemoveDecimalPoints(edtPriceAccept);
        KeyboardUtil.RemoveDecimalPoints(edtSupplyPrice);

        if (type.equals("1")) {
            tvBarTitle.setText("我要找物资");
            tvReleaseType.setText(getString(R.string.find_substance));
            demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_SUBSTANCE;
            flTitle.setVisibility(View.GONE);
            flCommission.setVisibility(View.GONE);
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
            edtDes.setHint("请输入物资的详细介绍，如规格");
        } else if (type.equals("2")) {
            tvBarTitle.setText("我要找买家");
            tvReleaseType.setText(getString(R.string.find_buyer));
            demandReleaseVm.releaseType = DemandReleaseVm.RELEASE_TYPE_BUYER;
            flTitle.setVisibility(View.GONE);
            flCommission.setVisibility(View.GONE);
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
            edtDes.setHint("请输入物资的详细介绍，如规格");
        }

        //监听键盘
        edtCommission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("StringFormatMatches")
            @Override
            public void afterTextChanged(Editable editable) {
                if (null != editable) {
                    double money = Double.valueOf("".equals(editable.toString()) ? "0.00" : editable.toString());
                    if (money < 10) {
                        tvCommission.setVisibility(View.VISIBLE);
                    } else {
                        tvCommission.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_release_type, R.id.cb_lease, R.id.cb_sell, R.id.tv_time_end, R.id.tv_release, R.id.tv_demand_num, R.id.tv_supply_num, R.id.tv_authentication})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_release_type:
                getBottomWindow();
                btn_dialog.show();
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
                switch (demandReleaseVm.releaseType) {
                    case DemandReleaseVm.RELEASE_TYPE_RELATIONSHIP://找关系
                        if (TextUtils.isEmpty(edtTitle.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_title));
                            return;
                        }
                        if (TextUtils.isEmpty(edtDes.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_des));
                            return;
                        }
                        if (TextUtils.isEmpty(edtCommission.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_commission));
                            return;
                        }
                        if (Double.valueOf(edtCommission.getText().toString()) < 10) {
                            ToastUtils.showShort("佣金不能低于10元");
                            return;
                        }
                        demandReleaseVm.releaseRelationShip(edtTitle.getText().toString(), edtDes.getText().toString(), edtCommission.getText().toString());
                        break;
                    case DemandReleaseVm.RELEASE_TYPE_SUBSTANCE://找物资
                        if (TextUtils.isEmpty(edtMaterialName.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_material_name));
                            return;
                        }
                        if (TextUtils.isEmpty(edtDemandNum.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_demand_num));
                            return;
                        }
                        if (demand_num == 0) {
                            ToastUtils.showShort(getString(R.string.no_unit));
                            return;
                        }
                        if (TextUtils.isEmpty(edtDemandProject.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_demand_project));
                            return;
                        }
                        if (TextUtils.isEmpty(edtProjectAddress.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_project_address));
                            return;
                        }
                        if (TextUtils.isEmpty(edtOwner.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_owner));
                            return;
                        }
                        if (TextUtils.isEmpty(edtContactsInfo.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_contact));
                            return;
                        }
                        demandReleaseVm.releaseSubstance(edtMaterialName.getText().toString(), edtDemandNum.getText().toString(), edtDemandProject.getText().toString(),
                                edtProjectAddress.getText().toString(), edtPriceAccept.getText().toString(), edtContactsInfo.getText().toString(), edtOwner.getText().toString(), demand_num, edtDes.getText().toString());
                        break;
                    case DemandReleaseVm.RELEASE_TYPE_BUYER://找买家
                        if (TextUtils.isEmpty(edtMaterialName.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_material_name));
                            return;
                        }
                        if (TextUtils.isEmpty(edtSupplyNum.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_supply_num));
                            return;
                        }
                        if (supply_num == 0) {
                            ToastUtils.showShort(getString(R.string.no_unit));
                            return;
                        }
                        if (TextUtils.isEmpty(edtSupplyAddress.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_supply_address));
                            return;
                        }
                        if (TextUtils.isEmpty(edtOwner.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_owner));
                            return;
                        }
                        if (TextUtils.isEmpty(edtContactsInfo.getText())) {
                            ToastUtils.showShort(getString(R.string.hint_contact));
                            return;
                        }
                        demandReleaseVm.releaseBuyer(edtMaterialName.getText().toString(), edtSupplyNum.getText().toString(), edtSupplyAddress.getText().toString(),
                                edtSupplyPrice.getText().toString(), edtContactsInfo.getText().toString(), edtOwner.getText().toString(), supply_num, edtDes.getText().toString());
                        break;
                }
                break;
            case R.id.tv_demand_num:
                new UnitDialog(this, 2f, 5f).setSelected(0).setSingleCallBack((item, position) -> {
                    demand_num = position + 1;
                    tvDemandNum.setText(item);
                }).showDialog();
                break;
            case R.id.tv_supply_num:
                new UnitDialog(this, 2f, 5f).setSelected(0).setSingleCallBack((item, position) -> {
                    supply_num = position + 1;
                    tvSupplyNum.setText(item);
                }).showDialog();
                break;
            case R.id.tv_authentication:
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                bundle.putString(CustomConfig.AUTHENTICATION_TYPE, CustomConfig.ID_USER_INFO);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
        }
    }

    /**
     * 时间选择器
     */
    @SuppressLint("SimpleDateFormat")
    private void showTimeDialog() {
        String tomorrowDay = StringUtils.isTrimEmpty(tvTimeEnd.getText().toString()) ? tomorrow : tvTimeEnd.getText().toString();
        if (customDatePicker == null) {
            customDatePicker = new CustomDatePicker(this, time -> {
                int gapDay = DateUtils.getGapCount(demandReleaseVm.startTime, time);
                if (gapDay > 30) {
                    ToastUtils.showShort("有效期不能超过30天");
                    return;
                }
                demandReleaseVm.endTime = time;
                tvTimeEnd.setText(time);
            }, "yyyy-MM-dd", tomorrowDay, "2100-12-31");
            customDatePicker.showSpecificTime(false);
        }
        customDatePicker.show(tomorrowDay);
    }

    //底部弹出框
    private TextView tv_find_relationship;
    private TextView tv_find_substance;
    private TextView tv_find_buyer;
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
        tv_find_relationship = dialog_view.findViewById(R.id.tv_find_relationship);
        tv_find_substance = dialog_view.findViewById(R.id.tv_find_substance);
        tv_find_buyer = dialog_view.findViewById(R.id.tv_find_buyer);
        tv_find_relationship.setOnClickListener(view -> {
            tvReleaseType.setText(getString(R.string.find_relationship));
            type = "0";
            tv_find_relationship.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_find_substance.setTextColor(getResources().getColor(R.color.color_666666));
            tv_find_buyer.setTextColor(getResources().getColor(R.color.color_666666));
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
            type = "1";
            tv_find_relationship.setTextColor(getResources().getColor(R.color.color_666666));
            tv_find_substance.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_find_buyer.setTextColor(getResources().getColor(R.color.color_666666));
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
            type = "2";
            tv_find_relationship.setTextColor(getResources().getColor(R.color.color_666666));
            tv_find_substance.setTextColor(getResources().getColor(R.color.color_666666));
            tv_find_buyer.setTextColor(getResources().getColor(R.color.colorPrimary));
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

        //增加选择状态
        switch (type) {
            case "0":
                tv_find_relationship.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_find_substance.setTextColor(getResources().getColor(R.color.color_666666));
                tv_find_buyer.setTextColor(getResources().getColor(R.color.color_666666));
                break;
            case "1":
                tv_find_relationship.setTextColor(getResources().getColor(R.color.color_666666));
                tv_find_substance.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_find_buyer.setTextColor(getResources().getColor(R.color.color_666666));
                break;
            case "2":
                tv_find_relationship.setTextColor(getResources().getColor(R.color.color_666666));
                tv_find_substance.setTextColor(getResources().getColor(R.color.color_666666));
                tv_find_buyer.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    //身份认证滚动文字显示
    private void getAuthenticationData() {
        if (SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED) != 2) {
            llAuthentication.setVisibility(View.VISIBLE);
            String str = "已通过实名认证发布信息审核时间为<font color=\"#6a5ff8\">" + "2小时内" + "</font>，" +
                    "未实名认证审核时间则为<font color=\"#6a5ff8\">" + "24小时内" + "</font>，建议您先实名认证再发布，审核会更快哦~";
            tvMarquee.setText(Html.fromHtml(str));
            tvMarquee.setSelected(true);
        } else {
            llAuthentication.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAuthenticationData();
    }
}
