package com.shuangduan.zcy.view.material;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
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
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.bean.MaterialPlaceOrderBean;
import com.shuangduan.zcy.model.event.AddressEvent;
import com.shuangduan.zcy.model.event.MaterialDetailEvent;
import com.shuangduan.zcy.utils.DateUtils;
import com.shuangduan.zcy.utils.DensityUtil;
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
import java.util.Date;
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
public class MaterialPlaceOrderActivity extends BaseActivity implements SwipeMenuCreator {

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
    @BindView(R.id.rv)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.tv_add)
    TextView tvAdd;
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

    private MaterialDetailVm materialDetailVm;
    private BottomSheetDialogs btn_dialog;
    int province, city, material_id, materialId, guidance_price, adapterPosition, supplier_id, day;
    String material_name, unit, science_num_id;
    List<MaterialPlaceOrderBean> list = new ArrayList<>();
    private List<String> list_address = new ArrayList<>();
    MaterialPlaceOrderAdapter materialDepositingPlaceAdapter;
    private long num, price;
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
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.material_place_order));
        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);
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

            tvMaterialCategory.setText(materialDetailBean.getMaterial_category());
            tvSupplyMethod.setText(materialDetailBean.getMethod() == 1 ? "出租" : "出售");


            guidance_price = materialDetailBean.getGuidance_price();
            tvGuidancePrice.setText(materialDetailBean.getMethod() == 1 ?
                    String.format(getString(R.string.format_material_price), String.valueOf(guidance_price), "天") :
                    String.format(getString(R.string.format_material_price), String.valueOf(guidance_price), materialDetailBean.getUnit()));

            tvSpec.setText(String.format(getString(R.string.format_spec), materialDetailBean.getSpec()));
            unit = materialDetailBean.getUnit();
            tvUnit.setText("单位：" + materialDetailBean.getUnit());
            tvCompany.setText("供应商：" + materialDetailBean.getCompany());
            material_id = materialDetailBean.getMaterial_id();
            supplier_id = materialDetailBean.getSupplier_id();
            tvAdd.setText(R.string.material_tv_add);

            materialDepositingPlaceAdapter = new MaterialPlaceOrderAdapter(this, list, materialDetail.getMethod());

            recyclerView.setAdapter(materialDepositingPlaceAdapter);

        });

        //数量、存放地保存成功返回结果
        materialDetailVm.mutableLiveData.observe(this, materialAddBean -> {
            try {
                num = num + Integer.valueOf(et_num.getText().toString());
                if (materialDetail.getMethod() == 1) {
                    price = price + Integer.valueOf(et_num.getText().toString()) * day * guidance_price;
                    tvNumber.setText("共租赁" + day + "天，共计");
                    tvPrice.setText(String.valueOf(price));
                } else {
                    price = price + Integer.valueOf(et_num.getText().toString()) * guidance_price;
                    tvNumber.setText("共采购" + num + "套，共计");
                    tvPrice.setText(String.valueOf(price));
                }

                materialDepositingPlaceAdapter.addData(list.size(),
                        Integer.valueOf(et_num.getText().toString()),
                        Integer.valueOf(materialAddBean.getId()),
                        materialId, material_name);

                for (int i = 0; i < list.size(); i++) {
                    list_address.add(String.valueOf(list.get(i).getId()));
                }
                removeDuplicate(list_address);
                science_num_id = KeyboardUtil.getListForString(list_address);
                btn_dialog.dismiss();

                tvAdd.setText(R.string.material_add);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.gravity = Gravity.CENTER_HORIZONTAL;
                param.setMargins(DensityUtil.dp2px(22), DensityUtil.dp2px(22), DensityUtil.dp2px(22), DensityUtil.dp2px(22));
                tvAdd.setLayoutParams(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //数量、存放地删除成功返回结果
        materialDetailVm.mutableLiveDataDel.observe(this, materialAddBean -> {
            try {
                num = num - list.get(adapterPosition).getNum();
                if (materialDetail.getMethod() == 1) {
                    price = price - list.get(adapterPosition).getNum() * Integer.valueOf(day) * guidance_price;
                    tvNumber.setText("共租赁" + num + "天，共计");
                    tvPrice.setText(String.valueOf(price));
                } else {
                    price = price - list.get(adapterPosition).getNum() * guidance_price;
                    tvNumber.setText("共采购" + num + "套，共计");
                    tvPrice.setText(String.valueOf(price));
                }

                materialDepositingPlaceAdapter.removeData(adapterPosition);

                list_address.remove(adapterPosition);
                list_address = removeDuplicate(list_address);
                science_num_id = KeyboardUtil.getListForString(list_address);

                //动态修改按钮样式
                if (materialDepositingPlaceAdapter.getItemCount() == 0) {
                    tvAdd.setText(materialDetail.getMethod() == 1 ? R.string.material_tv_lease_add : R.string.material_tv_add);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.gravity = Gravity.LEFT;
                    param.setMargins(DensityUtil.dp2px(22), DensityUtil.dp2px(22), DensityUtil.dp2px(22), DensityUtil.dp2px(22));
                    tvAdd.setLayoutParams(param);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //预定订单提交成功返回结果
        materialDetailVm.mutableLiveAddOrder.observe(this, materialAddBean -> {
            Bundle bundle = new Bundle();
            bundle.putString("order_id", materialAddBean.getOrder_id());
            bundle.putInt(CustomConfig.MATERIALS_TYPE, CustomConfig.FRP);
            ActivityUtils.startActivity(bundle, MaterialOrderSuccessActivity.class);
            finish();
        });

        recyclerView.setSwipeMenuCreator(this);
        recyclerView.setOnItemMenuClickListener(mItemMenuClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));

        materialDetailVm.getDetail(getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0));
    }

    //list集合用set去重
    public static List<String> removeDuplicate(List<String> list) {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }


    @OnClick({R.id.iv_bar_back, R.id.tv_province, R.id.tv_add, R.id.tv_submission, R.id.tv_time_start, R.id.tv_time_end})
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
            case R.id.tv_add://添加存放地

                if (materialDetail.getMethod() == 1) {
                    if (StringUtils.isTrimEmpty(leaseStartTime)) {
                        ToastUtils.showShort("请选择租赁开始时间");
                        return;
                    }
                    if (StringUtils.isTrimEmpty(leaseEndTime)) {
                        ToastUtils.showShort("请选择租赁结束时间");
                        return;
                    }

                    if (day <= 0) {
                        ToastUtils.showShort("开始时间必须小于结束时间");
                        return;
                    }
                }
                getAddDialog();
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
                if (TextUtils.isEmpty(science_num_id)) {
                    ToastUtils.showShort("数量、存放地为必填项，不能为空");
                    return;
                }
                materialDetailVm.getAddMaterialOrder(material_id, etRealName.getText().toString(), etTel.getText().toString()
                        , etCompany.getText().toString(), province, city, etAddress.getText().toString(), etRemark.getText(), science_num_id);
                break;
            case R.id.tv_time_start:
                showTimeDialog(tvTimeStart, 0);
                break;
            case R.id.tv_time_end:
                showTimeDialog(tvTimeEnd, 1);
                break;
        }
    }

    //添加存放地弹窗
    TextView tv_material_id;
    EditText et_num;

    private void getAddDialog() {
        //底部滑动对话框
        btn_dialog = new BottomSheetDialogs(this, R.style.BottomSheetStyle);
        //设置自定view
        View dialog_view = this.getLayoutInflater().inflate(R.layout.dialog_add_depositing_place, null);
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

//        dialog_view.findViewById(R.id.ll_lease).setVisibility(materialDetail.getMethod() == 1 ? View.VISIBLE : View.GONE);

        TextView tv_title = dialog_view.findViewById(R.id.tv_dialog_title);
        tv_title.setText("添加数量、存放地");

        TextView tv_save = dialog_view.findViewById(R.id.tv_save);
        et_num = dialog_view.findViewById(R.id.et_num);


        tv_material_id = dialog_view.findViewById(R.id.tv_material_id);
        tv_material_id.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.MATERIAL_ID, material_id);
            bundle.putInt(CustomConfig.SUPPLIER_ID, supplier_id);
            bundle.putInt(CustomConfig.IS_SHELF, materialDetail.getIsShelf());
            bundle.putInt(CustomConfig.METHOD_TYPE, materialDetail.getMethod());
            DataHolder.getInstance().setData("list", list);
            ActivityUtils.startActivity(bundle, DepositingPlaceActivity.class);
        });
        tv_save.setOnClickListener(v -> {
            if (TextUtils.isEmpty(et_num.getText().toString())) {
                ToastUtils.showShort(getString(R.string.no_mun));
                return;
            }
            if (Integer.valueOf(et_num.getText().toString()) == 0) {
                ToastUtils.showShort("数量不能为0");
                return;
            }
            if (materialId != 0) {
                for (int i = 0; i < list.size(); i++) {
                    String type_no = String.valueOf(list.get(i).getMaterialId());
                    if (type_no.equals(String.valueOf(materialId))) {
                        materialId = 0;
                        return;
                    }
                }
            }
            if (materialId == 0) {
                ToastUtils.showShort(getString(R.string.no_materialId));
                return;
            }


            BigInteger number = new BigInteger(et_num.getText().toString());
            materialDetailVm.getAddMaterial(materialId, number, materialDetail.getMethod(), leaseStartTime, leaseEndTime);
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
            if (sdf == null || c==null) {
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
                    tvPrice.setText(String.valueOf(price));
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

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventAddressEvent(AddressEvent event) {
        province = event.getProvinceId();
        city = event.getCityId();
        tvProvince.setText(event.getProvince() + event.getCity());
        tvProvince.setTextColor(ContextCompat.getColor(this, R.color.colorTv));
    }

    @Subscribe
    public void onEventUpdateMaterialDetail(MaterialDetailEvent event) {
        materialId = event.material_id;
        material_name = event.material_name;
        tv_material_id.setText(event.material_name);
        tv_material_id.setTextColor(ContextCompat.getColor(this, R.color.colorTv));
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
        SwipeMenuItem deleteItem = new SwipeMenuItem(this)
                .setBackground(R.drawable.circular_background_red)
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setWidth(getResources().getDimensionPixelSize(R.dimen.dp_55))
                .setHeight(getResources().getDimensionPixelSize(R.dimen.dp_110));
        swipeRightMenu.addMenuItem(deleteItem);
    }

    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在Item中的Position：
            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                //若menuPosition为删除按钮时
                if (menuPosition == 0) {
                    LogUtils.i(position);
                    LogUtils.i(list.get(position).getId());
                    materialDetailVm.getDelMaterial(list.get(position).getId());
                    adapterPosition = position;
                }
            }
        }
    };
}
