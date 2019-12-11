package com.shuangduan.zcy.view.material;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.MaterialPlaceOrderAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.bean.MaterialPlaceOrderBean;
import com.shuangduan.zcy.model.event.AddressEvent;
import com.shuangduan.zcy.model.event.MaterialDetailEvent;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.DigitUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.release.ReleaseAreaSelectActivity;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.weight.DataHolder;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import fj.edittextcount.lib.FJEditTextCount;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.material
 * @ClassName: MaterialPlaceOrderActivity
 * @Description: 物资提交预定页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 13:25
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 13:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("SetTextI18n")
public class MaterialPlaceOrderActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_images)
    ImageView ivImages;
    @BindView(R.id.tv_material_category)
    TextView tvMaterialCategory;
    @BindView(R.id.tv_guidance_price)
    TextView tvGuidancePrice;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_remark)
    FJEditTextCount etRemark;

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    @BindView(R.id.tv_supply_method)
    TextView tvSupplyMethod;

    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;

    @BindView(R.id.ll_lease)
    LinearLayout llLease;

    @BindView(R.id.et_num)
    EditText etNum;

    @BindView(R.id.tv_material_id)
    TextView tvMaterialId;


    private MaterialDetailVm materialDetailVm;
    int province, city, material_id, supplier_id, day;
    String unit, buyStock;


    private double num, price, guidance_price;
    private MaterialDetailBean materialDetail;
    //租期开始时间  ,租期结束时间
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
        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);


        KeyboardUtil.RemoveDecimalPoints(etNum);
        materialDetailVm.detailLiveData.observe(this, materialDetailBean -> {
            this.materialDetail = materialDetailBean;
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

            llLease.setVisibility(materialDetail.getMethod() == 1 ? View.VISIBLE : View.GONE);

            tvMaterialCategory.setText(materialDetailBean.getMaterialName());
            tvSupplyMethod.setText(materialDetailBean.getMethod() == 1 ? "出租" : "出售");


            guidance_price = Double.parseDouble(materialDetailBean.getGuidance_price());
            tvGuidancePrice.setText(materialDetailBean.getMethod() == 1 ?
                    String.format(getString(R.string.format_material_price), materialDetailBean.getGuidance_price(), "天") :
                    String.format(getString(R.string.format_material_price), materialDetailBean.getGuidance_price(), materialDetailBean.getUnit()));

            tvSpec.setText(materialDetailBean.getSpec());
            unit = materialDetailBean.getUnit();
            tvUnit.setText("单位：" + materialDetailBean.getUnit());
            tvCompany.setText(materialDetailBean.getCompany());
            material_id = materialDetailBean.getId();
            supplier_id = materialDetailBean.getSupplier_id();

            tvMaterialId.setText(materialDetailBean.getAddress());
        });

        //预定订单提交成功返回结果
        materialDetailVm.mutableLiveAddOrder.observe(this, materialAddBean -> {
            Bundle bundle = new Bundle();
            bundle.putString("order_id", materialAddBean.getOrder_id());
            bundle.putInt(CustomConfig.MATERIALS_TYPE, CustomConfig.FRP);
            ActivityUtils.startActivity(bundle, MaterialOrderSuccessActivity.class);
            finish();
        });


        etNum.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isTrimEmpty(s.toString())) {
                    num = 0;
                    return;
                }
                num = Double.valueOf(s.toString());
                if (materialDetail.getMethod() == 1) {
                    price = num * day * guidance_price;
                    tvNumber.setText("共租赁" + day + "天，共计");
                    tvPrice.setText(DigitUtils.doubleToString(price));
                } else {
                    price = num * guidance_price;
                    tvNumber.setText("共采购" + num + materialDetail.getUnit() + "，共计");
                    tvPrice.setText(DigitUtils.doubleToString(price));
                }
            }
        });

        materialDetailVm.getDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
    }


    @OnClick({R.id.iv_bar_back, R.id.tv_province, R.id.tv_submission, R.id.tv_time_start, R.id.tv_time_end})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_province://选择地区
                bundle.putInt(CustomConfig.PROJECT_ADDRESS, 2);
                ActivityUtils.startActivity(bundle, ReleaseAreaSelectActivity.class);
                break;
            case R.id.tv_submission://提交预订单

                if (TextUtils.isEmpty(etRealName.getText().toString())) {
                    ToastUtils.showShort("联系人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etTel.getText().toString())) {
                    ToastUtils.showShort("电话不能为空");
                    return;
                }

                if (TextUtils.isEmpty(etCompany.getText().toString())) {
                    ToastUtils.showShort("公司名称不能为空");
                    return;
                }

                if (city == 0) {
                    ToastUtils.showShort("收货省市不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etAddress.getText().toString())) {
                    ToastUtils.showShort("详细地址不能为空");
                    return;
                }

                buyStock = etNum.getText().toString();
                if (TextUtils.isEmpty(buyStock)) {
                    ToastUtils.showShort("数量为必填项，不能为空");
                    return;
                }

                if (price > 100000000) {
                    ToastUtils.showShort("订单金额过大，不支持线上交易");
                    return;
                }

                materialDetailVm.getAddMaterialOrder(material_id, etRealName.getText().toString(), etTel.getText().toString()
                        , etCompany.getText().toString(), province, city, etAddress.getText().toString(), etRemark.getText(), buyStock, leaseStartTime, leaseEndTime);
                break;
            case R.id.tv_time_start:
                showTimeDialog(tvTimeStart, 0);
                break;
            case R.id.tv_time_end:
                if (StringUtils.isEmpty(leaseStartTime)) {
                    ToastUtils.showShort("请先选择起始时间");
                    return;
                }
                showTimeDialog(tvTimeEnd, 1);
                break;
        }
    }


    /**
     * 时间选择器
     */
    private SimpleDateFormat sdf;
    private Calendar c;

    private void showTimeDialog(TextView tv, int type) {


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
            if (type == 0) leaseStartTime = time;
            else leaseEndTime = time;

            if (!StringUtils.isTrimEmpty(leaseStartTime) && !StringUtils.isTrimEmpty(leaseEndTime)) {
                day = DateUtils.getGapCount(leaseStartTime, leaseEndTime);

                if (day <= 0) {
                    ToastUtils.showShort("租赁结束时间不能小于开始时间");
                    tv.setText("");
                    return;
                }
                price = day * num * guidance_price;
                if (materialDetail.getMethod() == 1) {
                    tvNumber.setText("共租赁" + day + "天，共计");
                    tvPrice.setText(DigitUtils.doubleToString(price));
                }
            }
        }, "yyyy-MM-dd", TimeUtils.getNowString(), "2040-12-31");
        customDatePicker.showSpecificTime(false);
        if (type == 0 || StringUtils.isTrimEmpty(leaseStartTime)) {
            customDatePicker.show(TimeUtils.getNowString());
        } else {
            customDatePicker.show(sdf.format(c.getTime()));
        }
    }

    @Subscribe
    public void onEventAddressEvent(AddressEvent event) {
        province = event.getProvinceId();
        city = event.getCityId();
        tvProvince.setText(event.getProvince() + event.getCity());
        tvProvince.setTextColor(ContextCompat.getColor(this, R.color.colorTv));
    }


}
