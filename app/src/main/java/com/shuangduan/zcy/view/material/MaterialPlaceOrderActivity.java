package com.shuangduan.zcy.view.material;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.DigitUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.PhoneUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.set.ReceivingAddressActivity;
import com.shuangduan.zcy.vm.AddressVm;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.weight.CornerImageView;
import com.shuangduan.zcy.weight.XEditText;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.material
 * @ClassName: MaterialPlaceOrderActivity
 * @Description: ????????????????????????
 * @Author: ?????????
 * @CreateDate: 2019/9/25 13:25
 * @UpdateUser: ?????????
 * @UpdateDate: 2019/9/25 13:25
 * @UpdateRemark: ????????????
 * @Version: 1.0
 */
@SuppressLint("SetTextI18n")
public class MaterialPlaceOrderActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_address_empty)
    LinearLayout llAddressEmpty;
    @BindView(R.id.tv_real_name)
    AppCompatTextView tvRealName;
    @BindView(R.id.tv_tel)
    AppCompatTextView tvTel;
    @BindView(R.id.iv_is_default)
    AppCompatImageView ivIsDefault;
    @BindView(R.id.tv_real_company)
    AppCompatTextView tvRealCompany;
    @BindView(R.id.tv_address)
    AppCompatTextView tvAddress;
    @BindView(R.id.iv_company)
    CircleImageView ivCompany;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.iv_images)
    CornerImageView ivImages;
    @BindView(R.id.tv_material_category)
    TextView tvMaterialCategory;
    @BindView(R.id.tv_guidance_price)
    TextView tvGuidancePrice;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_turnover_num)
    TextView tvTurnoverNum;
    @BindView(R.id.ll_equipment_num)
    LinearLayout llEquipmentNum;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.ll_lease)
    LinearLayout llLease;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.rl_number)
    RelativeLayout rlNumber;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_supply_method)
    TextView tvSupplyMethod;
    @BindView(R.id.et_remark)
    XEditText etRemark;
    @BindView(R.id.tv_number2)
    TextView tvNumber2;
    @BindView(R.id.tv_price2)
    TextView tvPrice2;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;

    @BindView(R.id.tv_price_unit)
    TextView tvPriceUnit;


    private MaterialDetailVm materialDetailVm;
    private AddressVm addressVm;
    private int material_id, day, type;
    private double price, guidance_price;
    private MaterialDetailBean materialDetail;
    //?????? ,??????????????????  ,??????????????????
    private String leaseStartTime, leaseEndTime;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_place_order;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        tvBarTitle.setText(getString(R.string.material_place_order));
        materialDetailVm = getViewModel(MaterialDetailVm.class);
        addressVm = getViewModel(AddressVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);
        type = getIntent().getIntExtra("type", 0);

        switch (type) {
            case 1://????????????
                tvTurnoverNum.setVisibility(View.VISIBLE);
                materialDetailVm.getDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
                rlNumber.setVisibility(View.VISIBLE);

                break;
            case 2://??????
                llEquipmentNum.setVisibility(View.VISIBLE);
                materialDetailVm.getEquipmentDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
                rlNumber.setVisibility(View.GONE);
                break;
        }

        //??????????????????????????????????????????
        addressVm.addressLiveData.observe(this, item -> {
            if (item.list == null || item.list.size() == 0) {
                llAddress.setVisibility(View.GONE);
                llAddressEmpty.setVisibility(View.VISIBLE);
                materialDetailVm.address_id = 0;
            } else {
                if (materialDetailVm.address_id != 0) {
                    return;
                }
                llAddress.setVisibility(View.VISIBLE);
                llAddressEmpty.setVisibility(View.GONE);
                materialDetailVm.address_id = item.list.get(0).id;
                tvRealName.setText(item.list.get(0).name);
                ivIsDefault.setVisibility(item.list.get(0).state == 1 ? View.VISIBLE : View.GONE);
                tvTel.setText(item.list.get(0).phone);
                tvRealCompany.setText(item.list.get(0).company);
                tvAddress.setText(item.list.get(0).province + item.list.get(0).city + item.list.get(0).address);

                tvRealCompany.setVisibility(StringUtils.isTrimEmpty(item.list.get(0).company) ? View.GONE : View.VISIBLE);
            }
        });

        //????????????????????????
        materialDetailVm.detailLiveData.observe(this, materialDetailBean -> {
            this.materialDetail = materialDetailBean;

            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(materialDetailBean.getHeadimg())
                    .placeholder(R.drawable.no_banner)
                    .errorPic(R.drawable.no_banner)
                    .imageView(ivCompany)
                    .build());
            tvCompany.setText(materialDetailBean.getCompany());
            if (materialDetailBean.getImages() != null && materialDetailBean.getImages().size() != 0) {
                if (!TextUtils.isEmpty(materialDetailBean.getImages().get(0).getUrl())) {
                    ImageLoader.load(this, new ImageConfig.Builder()
                            .url(materialDetailBean.getImages().get(0).getUrl())
                            .placeholder(R.drawable.no_banner)
                            .errorPic(R.drawable.no_banner)
                            .imageView(ivImages)
                            .build());
                }
            }
            tvMaterialCategory.setText(materialDetailBean.getMaterialName());
            tvSpec.setText("?????????" + materialDetailBean.getSpec());
            tvSpec.setVisibility(StringUtils.isTrimEmpty(materialDetailBean.getSpec()) ? View.GONE : View.VISIBLE);
            llLease.setVisibility(materialDetail.getMethod() == 1 ? View.VISIBLE : View.GONE);
            tvUnit.setText(materialDetailBean.getUnit());
            tvSupplyMethod.setText(materialDetailBean.getMethod() == 1 ? "??????" : "??????");
            guidance_price = Double.parseDouble(materialDetailBean.getGuidance_price());

            switch (type) {
                case 1://????????????

                    if (materialDetailBean.getPriceType() == 2) {
                        tvGuidancePrice.setText("??????");
                    } else {
                        String unitPrice = materialDetailBean.getMethod() == 1 ?
                                String.format(getString(R.string.format_material_price), materialDetailBean.getGuidance_price(), "???") :
                                String.format(getString(R.string.format_material_price), materialDetailBean.getGuidance_price(), materialDetailBean.getUnit());
                        tvGuidancePrice.setText(unitPrice);
                    }

                    getGuidancePrice(materialDetailVm.buyStock);

                    break;
                case 2://??????
                    getGuidancePriceInt(materialDetailVm.buyStockEquipment);
                    tvGuidancePrice.setText(materialDetailBean.getMethod() == 1 ?
                            String.format(getString(R.string.format_material_price), materialDetailBean.getGuidance_price(), "???") :
                            String.format(getString(R.string.format_material_price_no_unit), materialDetailBean.getGuidance_price()));
                    break;
            }
            material_id = materialDetailBean.getId();
        });

        //????????????????????????????????????
        materialDetailVm.mutableLiveAddOrder.observe(this, materialAddBean -> {
            Bundle bundle = new Bundle();
            bundle.putString("order_id", materialAddBean.getOrder_id());
            switch (type) {
                case 1://????????????
                    bundle.putInt(CustomConfig.MATERIALS_TYPE, CustomConfig.FRP);
                    break;
                case 2://??????
                    bundle.putInt(CustomConfig.MATERIALS_TYPE, CustomConfig.EQUIPMENT);
                    break;
            }
            ActivityUtils.startActivity(bundle, MaterialOrderSuccessActivity.class);
            finish();
        });
    }


    @OnClick({R.id.iv_bar_back, R.id.ll_address, R.id.ll_address_empty, R.id.tv_num_left, R.id.tv_num, R.id.tv_turnover_num, R.id.tv_num_right, R.id.tv_submission, R.id.tv_time_start, R.id.tv_time_end})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.ll_address:
            case R.id.ll_address_empty:
                bundle.putInt("type", 1);
                ActivityUtils.startActivity(bundle, ReceivingAddressActivity.class);
                break;
            case R.id.tv_num_left://????????????
                if (materialDetail.getMethod() == 1 && day == 0) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                if (materialDetailVm.buyStockEquipment > 1) {
                    materialDetailVm.buyStockEquipment = materialDetailVm.buyStockEquipment - 1;
                    tvNum.setText(String.valueOf(materialDetailVm.buyStockEquipment));
                    getGuidancePriceInt(materialDetailVm.buyStockEquipment);
                } else {
                    ToastUtils.showShort("????????????????????????");
                }
                break;
            case R.id.tv_num://???????????????????????????
            case R.id.tv_turnover_num:
                getBottomSheetDialog();
                break;
            case R.id.tv_num_right://????????????
                if (materialDetail.getMethod() == 1 && day == 0) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                materialDetailVm.buyStockEquipment = materialDetailVm.buyStockEquipment + 1;
                tvNum.setText(String.valueOf(materialDetailVm.buyStockEquipment));
                getGuidancePriceInt(materialDetailVm.buyStockEquipment);
                break;
            case R.id.tv_submission://???????????????
                if (materialDetailVm.address_id == 0) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }

                switch (type) {
                    case 1://????????????
                        if (materialDetailVm.buyStock == 0) {
                            ToastUtils.showShort("????????????????????????");
                            return;
                        }
                        break;
                    case 2://??????
                        if (materialDetailVm.buyStockEquipment == 0) {
                            ToastUtils.showShort("????????????????????????");
                            return;
                        }
                        break;
                }

                if (materialDetail.getMethod() == 1 && StringUtils.isTrimEmpty(leaseStartTime)) {
                    ToastUtils.showShort("???????????????????????????");
                    return;
                }
                if (materialDetail.getMethod() == 1 && StringUtils.isTrimEmpty(leaseEndTime)) {
                    ToastUtils.showShort("???????????????????????????");
                    return;
                }

                if (materialDetail.getMethod() == 1 && day <= 0) {
                    ToastUtils.showShort("????????????????????????????????????");
                    return;
                }
                if (price > 100000000) {
                    ToastUtils.showShort("??????????????????????????????????????????");
                    return;
                }
                switch (type) {
                    case 1://????????????
                        materialDetailVm.getAddMaterialOrder(material_id, etRemark.getText().toString(), leaseStartTime, leaseEndTime);
                        break;
                    case 2://??????
                        materialDetailVm.getAddEquipmentOrder(material_id, etRemark.getText().toString(), leaseStartTime, leaseEndTime);
                        break;
                }
                break;
            case R.id.tv_time_start:
                showTimeDialog(tvTimeStart, 0);
                break;
            case R.id.tv_time_end:
                if (StringUtils.isEmpty(leaseStartTime)) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                showTimeDialog(tvTimeEnd, 1);
                break;
        }
    }

    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog() {
        //?????????????????????
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(this, R.style.BottomSheetStyle);
        //????????????view
        View dialog_view = this.getLayoutInflater().inflate(R.layout.dialog_search_edit, null);
        //?????????????????????
        btn_dialog.setContentView(dialog_view);
        //??????????????????????????????
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_dialog.setOnDismissListener(dialog -> {
            if (KeyboardUtil.isSoftShowing(this)) {
                KeyboardUtil.showORhideSoftKeyboard(this);
            }
        });

        XEditText xEditText = dialog_view.findViewById(R.id.edit);
        TextView tvSearch = dialog_view.findViewById(R.id.tv_search);
        xEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        switch (type) {
            case 1://????????????
                xEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                break;
            case 2://??????
                xEditText.setText(String.valueOf(materialDetailVm.buyStockEquipment));
                xEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                break;
        }

        xEditText.setHint("?????????????????????");
        tvSearch.setText("??????");
        View view = dialog_view.findViewById(R.id.view);
        PhoneUtils.isPhone(view);
        KeyboardUtil.showSoftInputFromWindow(this, xEditText);
        tvSearch.setOnClickListener(v -> {
            if (TextUtils.isEmpty(xEditText.getText().toString())) {
                ToastUtils.showShort(getString(R.string.no_mun));
                return;
            }
            if (!KeyboardUtil.isDouble(xEditText.getText().toString()) && !KeyboardUtil.isInteger(xEditText.getText().toString())) {
                ToastUtils.showShort("???????????????????????????");
                return;
            }
            if (Double.valueOf(xEditText.getText().toString()) == 0) {
                ToastUtils.showShort("???????????????0");
                return;
            }
            if (materialDetail.getMethod() == 1 && day == 0) {
                ToastUtils.showShort("????????????????????????");
                return;
            }

            switch (type) {
                case 1://????????????
                    materialDetailVm.buyStock = Double.parseDouble(xEditText.getText().toString());
                    getGuidancePrice(materialDetailVm.buyStock);
                    tvTurnoverNum.setText(String.valueOf(materialDetailVm.buyStock));
                    tvTurnoverNum.setTextColor(getResources().getColor(R.color.colorTv));
                    break;
                case 2://??????
                    materialDetailVm.buyStockEquipment = Long.parseLong(xEditText.getText().toString());
                    getGuidancePriceInt(materialDetailVm.buyStockEquipment);
                    break;
            }
            btn_dialog.dismiss();
        });
        //EditTextView ??????
        xEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(xEditText.getText().toString())) {
                    ToastUtils.showShort(getString(R.string.no_mun));
                } else if (Double.valueOf(xEditText.getText().toString()) == 0) {
                    ToastUtils.showShort("???????????????0");
                } else if (materialDetail.getMethod() == 1 && day == 0) {
                    ToastUtils.showShort("????????????????????????");
                } else {
                    //???????????????
                    KeyboardUtil.closeKeyboard(this);
                    switch (type) {
                        case 1://????????????
                            materialDetailVm.buyStock = Double.parseDouble(xEditText.getText().toString());
                            getGuidancePrice(materialDetailVm.buyStock);
                            tvTurnoverNum.setText(String.valueOf(materialDetailVm.buyStock));
                            tvTurnoverNum.setTextColor(getResources().getColor(R.color.colorTv));
                            break;
                        case 2://??????
                            materialDetailVm.buyStockEquipment = Long.parseLong(xEditText.getText().toString());
                            getGuidancePriceInt(materialDetailVm.buyStockEquipment);
                            break;
                    }
                    btn_dialog.dismiss();
                }
                return true;
            }
            return false;
        });
        btn_dialog.show();
    }

    /**
     * ???????????????
     */
    private SimpleDateFormat sdf;
    private Calendar c;

    @SuppressLint("SimpleDateFormat")
    private void showTimeDialog(TextView tv, int typeTime) {
        try {
            if (sdf == null || c == null) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                c = Calendar.getInstance();
            }
            c.setTime(Objects.requireNonNull(sdf.parse(leaseStartTime)));
            c.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        CustomDatePicker customDatePicker = new CustomDatePicker(this, time -> {
            tv.setText(time);
            tv.setTextColor(getResources().getColor(R.color.colorTv));
            if (typeTime == 0) leaseStartTime = time;
            else leaseEndTime = time;

            if (!StringUtils.isTrimEmpty(leaseStartTime) && !StringUtils.isTrimEmpty(leaseEndTime)) {
                day = DateUtils.getGapCount(leaseStartTime, leaseEndTime);

                if (day <= 0) {
                    ToastUtils.showShort("??????????????????????????????????????????");
                    tv.setText("????????????");
                    tv.setTextColor(getResources().getColor(R.color.color_DDDDDD));
                    return;
                }

                switch (type) {
                    case 1://????????????
                        getGuidancePrice(materialDetailVm.buyStock);
                        break;
                    case 2://??????
                        getGuidancePriceInt(materialDetailVm.buyStockEquipment);
                        break;
                }
            }
        }, "yyyy-MM-dd", TimeUtils.getNowString(), "2040-12-31");
        customDatePicker.showSpecificTime(false);
        if (typeTime == 0 || StringUtils.isTrimEmpty(leaseStartTime)) {
            customDatePicker.show(TimeUtils.getNowString());
        } else {
            customDatePicker.show(sdf.format(c.getTime()));
        }
    }

    //?????????????????????
    private void getGuidancePrice(double num) {

        if (materialDetail.getPriceType() == 2) {
            tvNumber.setText("?????????" + num + materialDetail.getUnit());
            tvNumber2.setText("???" + num + materialDetail.getUnit());
            tvPriceUnit.setVisibility(View.INVISIBLE);
            return;
        }

        if (materialDetail.getMethod() == 1) {
            price = num * day * guidance_price;
        } else {
            price = num * guidance_price;
        }
        tvNumber.setText("?????????" + num + materialDetail.getUnit() + "?????????");
        tvNumber2.setText(materialDetail.getMethod() == 1 ? "???" + day + "????????????" : "???" + num + materialDetail.getUnit() + "?????????");
        tvPrice.setText(DigitUtils.doubleToString(price));
        tvPrice2.setText(DigitUtils.doubleToString(price));
    }

    //?????????????????????
    private void getGuidancePriceInt(long num) {
        if (materialDetail.getMethod() == 1) {
            price = num * day * guidance_price;
        } else {
            price = num * guidance_price;
        }
        tvNum.setText(String.valueOf(num));
        tvNumber.setText("???????????????" + num + "?????????");
        tvNumber2.setText(materialDetail.getMethod() == 1 ? "???" + day + "????????????" : "??????");
        tvPrice.setText(DigitUtils.doubleToString(price));
        tvPrice2.setText(DigitUtils.doubleToString(price));
    }

    @Subscribe
    public void onEventAddressID(ReceivingAddressBean.Address item) {
        materialDetailVm.address_id = item.id;
        tvRealName.setText(item.name);
        ivIsDefault.setVisibility(item.state == 1 ? View.VISIBLE : View.GONE);
        tvTel.setText(item.phone);
        tvRealCompany.setText(item.company);
        tvAddress.setText(item.province + item.city + item.address);
        llAddress.setVisibility(View.VISIBLE);
        llAddressEmpty.setVisibility(View.GONE);
        tvRealCompany.setVisibility(StringUtils.isTrimEmpty(item.company) ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressVm.addressList();
    }
}
