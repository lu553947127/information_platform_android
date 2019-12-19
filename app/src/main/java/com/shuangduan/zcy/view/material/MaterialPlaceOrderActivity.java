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

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.DigitUtils;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.utils.PhoneUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.weight.CornerImageView;
import com.shuangduan.zcy.weight.XEditText;
import com.shuangduan.zcy.weight.datepicker.CustomDatePicker;

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

    int  material_id, supplier_id, day;
    String unit, buyStock;
    private double num, price, guidance_price;
    private MaterialDetailBean materialDetail;
    //租期开始时间  ,租期结束时间
    private String leaseStartTime, leaseEndTime;

    private BottomSheetDialogs btn_dialog;
    private MaterialDetailVm materialDetailVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_place_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        tvBarTitle.setText(getString(R.string.material_place_order));
        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);

        //获取基建物资详情
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
            tvGuidancePrice.setText(materialDetailBean.getMethod() == 1 ?
                    String.format(getString(R.string.format_material_price), materialDetailBean.getGuidance_price(), "天") :
                    String.format(getString(R.string.format_material_price), materialDetailBean.getGuidance_price(), materialDetailBean.getUnit()));
            tvSpec.setText("规格："+materialDetailBean.getSpec());
            llLease.setVisibility(materialDetail.getMethod() == 1 ? View.VISIBLE : View.GONE);
            tvUnit.setText(materialDetailBean.getUnit());
            tvSupplyMethod.setText(materialDetailBean.getMethod() == 1 ? "出租" : "出售");
            guidance_price = Double.parseDouble(materialDetailBean.getGuidance_price());
            unit = materialDetailBean.getUnit();
            getGuidancePrice(materialDetailVm.turnoverNum);
            material_id = materialDetailBean.getId();
            supplier_id = materialDetailBean.getSupplier_id();
        });

        //预定订单提交成功返回结果
        materialDetailVm.mutableLiveAddOrder.observe(this, materialAddBean -> {
            Bundle bundle = new Bundle();
            bundle.putString("order_id", materialAddBean.getOrder_id());
            bundle.putInt(CustomConfig.MATERIALS_TYPE, CustomConfig.FRP);
            ActivityUtils.startActivity(bundle, MaterialOrderSuccessActivity.class);
            finish();
        });


        materialDetailVm.getDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
    }


    @OnClick({R.id.iv_bar_back,R.id.tv_num_left,R.id.tv_num,R.id.tv_num_right, R.id.tv_submission, R.id.tv_time_start, R.id.tv_time_end})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_num_left://数量减少
                if (materialDetailVm.turnoverNum > 1){
                    materialDetailVm.turnoverNum = materialDetailVm.turnoverNum - 1;
                    tvNum.setText(String.valueOf(materialDetailVm.turnoverNum));
                    getGuidancePrice(materialDetailVm.turnoverNum);
                }else {
                    ToastUtils.showShort("亲，不能再减少了");
                }
                break;
            case R.id.tv_num://购买数量输入弹出窗
                getBottomSheetDialog();
                break;
            case R.id.tv_num_right://数量增加
                materialDetailVm.turnoverNum = materialDetailVm.turnoverNum + 1;
                tvNum.setText(String.valueOf(materialDetailVm.turnoverNum));
                getGuidancePrice(materialDetailVm.turnoverNum);
                break;
            case R.id.tv_submission://提交预订单

//                if (TextUtils.isEmpty(etRealName.getText().toString())) {
//                    ToastUtils.showShort("联系人不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(etTel.getText().toString())) {
//                    ToastUtils.showShort("电话不能为空");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(etCompany.getText().toString())) {
//                    ToastUtils.showShort("公司名称不能为空");
//                    return;
//                }
//
//                if (city == 0) {
//                    ToastUtils.showShort("收货省市不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(etAddress.getText().toString())) {
//                    ToastUtils.showShort("详细地址不能为空");
//                    return;
//                }
//
//                buyStock = etNum.getText().toString();
//                if (TextUtils.isEmpty(buyStock)) {
//                    ToastUtils.showShort("数量为必填项，不能为空");
//                    return;
//                }
//
//                if (price > 100000000) {
//                    ToastUtils.showShort("订单金额过大，不支持线上交易");
//                    return;
//                }
//
//                materialDetailVm.getAddMaterialOrder(material_id, etRealName.getText().toString(), etTel.getText().toString()
//                        , etCompany.getText().toString(), province, city, etAddress.getText().toString(), etRemark.getText(), buyStock, leaseStartTime, leaseEndTime);
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

    private void getGuidancePrice(long num) {
        if (materialDetail.getMethod() == 1) {
            price = num * day * guidance_price;
        } else {
            price = num * guidance_price;
        }
        tvNumber.setText("共采购" + num + materialDetail.getUnit() + "，共计");
        tvPrice.setText(DigitUtils.doubleToString(price));

        tvNumber2.setText("共" + num + materialDetail.getUnit() + " 小计");
        tvPrice2.setText(DigitUtils.doubleToString(price));
    }

    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog() {
        //底部滑动对话框
        btn_dialog = new BottomSheetDialogs(this, R.style.BottomSheetStyle);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(R.layout.dialog_search_edit, null);
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
        btn_dialog.setOnDismissListener(dialog -> {
            if (KeyboardUtil.isSoftShowing(this)){
                KeyboardUtil.showORhideSoftKeyboard(this);
            }
        });

        XEditText xEditText = dialog_view.findViewById(R.id.edit);
        TextView tvSearch = dialog_view.findViewById(R.id.tv_search);
        xEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        xEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        xEditText.setHint("请输入购买数量");
        tvSearch.setText("确定");
        xEditText.setText(String.valueOf(materialDetailVm.turnoverNum));
        View view = dialog_view.findViewById(R.id.view);
        if(PhoneUtils.isPhone()) {
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
        KeyboardUtil.showSoftInputFromWindow(this, xEditText);
        tvSearch.setOnClickListener(v -> {
            if (TextUtils.isEmpty(xEditText.getText().toString())) {
                ToastUtils.showShort(getString(R.string.no_mun));
                return;
            }
            if (Double.valueOf(xEditText.getText().toString()) == 0) {
                ToastUtils.showShort("数量不能为0");
                return;
            }
            materialDetailVm.turnoverNum = Long.parseLong(xEditText.getText().toString());
            tvNum.setText(String.valueOf(materialDetailVm.turnoverNum));
            getGuidancePrice(materialDetailVm.turnoverNum);
            btn_dialog.dismiss();
        });
        //EditTextView 搜索
        xEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(xEditText.getText().toString())) {
                    ToastUtils.showShort(getString(R.string.no_mun));
                }else if (Double.valueOf(xEditText.getText().toString()) == 0) {
                    ToastUtils.showShort("数量不能为0");
                }else {
                    //关闭软键盘
                    KeyboardUtil.closeKeyboard(this);
                    materialDetailVm.turnoverNum = Long.parseLong(xEditText.getText().toString());
                    tvNum.setText(String.valueOf(materialDetailVm.turnoverNum));
                    getGuidancePrice(materialDetailVm.turnoverNum);
                    btn_dialog.dismiss();
                }
                return true;
            }
            return false;
        });
        btn_dialog.show();
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
//                    tvNumber.setText("共租赁" + day + "天，共计");
                    tvNumber.setText("共采购" + num + materialDetail.getUnit() + "，共计");
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
}
