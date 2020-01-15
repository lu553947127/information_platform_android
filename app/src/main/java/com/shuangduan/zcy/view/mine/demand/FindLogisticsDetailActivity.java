package com.shuangduan.zcy.view.mine.demand;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.view.demand.FindLogisticsActivity;
import com.shuangduan.zcy.vm.DemandReleaseVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine.demand
 * @ClassName: FindLogisticsDetailActivity
 * @Description: 个人中心找物流详情
 * @Author: 徐玉
 * @CreateDate: 2020/1/10 14:22
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public class FindLogisticsDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_state)
    ImageView ivState;

    @BindView(R.id.tv_material_name_title)
    TextView tvMaterialNameTitle;
    @BindView(R.id.tv_material_name)
    TextView tvMaterialName;

    @BindView(R.id.tv_material_num_title)
    TextView tvMaterialNumTitle;
    @BindView(R.id.tv_material_num)
    TextView tvMaterialNum;

    @BindView(R.id.tv_send_address_title)
    TextView tvSendAddressTitle;
    @BindView(R.id.tv_send_address)
    TextView tvSendAddress;

    @BindView(R.id.tv_receive_address_title)
    TextView tvReceiveAddressTitle;
    @BindView(R.id.tv_receive_address)
    TextView tvReceiveAddress;

    @BindView(R.id.tv_receive_time_title)
    TextView tvReceiveTimeTitle;
    @BindView(R.id.tv_receive_time)
    TextView tvReceiveTime;

    @BindView(R.id.tv_effective_time_title)
    TextView tvEffectiveTimeTitle;
    @BindView(R.id.tv_effective_time)
    TextView tvEffectiveTime;

    @BindView(R.id.tv_contact_title)
    TextView tvContactTitle;
    @BindView(R.id.tv_contact)
    TextView tvContact;

    @BindView(R.id.tv_contact_phone_title)
    TextView tvContactPhoneTitle;
    @BindView(R.id.tv_contact_phone)
    TextView tvContactPhone;

    @BindView(R.id.tv_remark_title)
    TextView tvRemarkTitle;
    @BindView(R.id.tv_remark)
    TextView tvRemark;

    @BindView(R.id.iv_cancel)
    ImageView ivCancel;


    private DemandReleaseVm vm;
    private int id;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_logistics_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.find_logistics_details));

        id = getIntent().getIntExtra("id", 0);

        vm = ViewModelProviders.of(this).get(DemandReleaseVm.class);

        vm.needInfoLiveData.observe(this, result -> {
            tvMaterialName.setText(result.materialName);
            tvMaterialNum.setText(result.materialCount + result.unit);
            tvSendAddress.setText(result.deliveryAddress);
            tvReceiveAddress.setText(result.receivingAddress);
            tvReceiveTime.setText(result.receivingTime);
            tvEffectiveTime.setText(result.startTime + "至" + result.endTime);
            tvContact.setText(result.personalName);
            tvContactPhone.setText(result.tel);
            if (!StringUtils.isTrimEmpty(result.remark)) {
                tvRemark.setText(result.remark);
            }

            if (result.state.equals("已取消")) {
                ivState.setImageResource(R.drawable.icon_cancel);

                setTextViewStyle(tvMaterialNameTitle, tvMaterialName, tvMaterialNumTitle, tvMaterialNum, tvSendAddressTitle, tvSendAddress,
                        tvReceiveAddressTitle, tvReceiveAddress, tvReceiveTimeTitle, tvReceiveTime, tvEffectiveTimeTitle, tvEffectiveTime,
                        tvContactTitle, tvContact, tvContactPhoneTitle, tvContactPhone, tvRemarkTitle, tvRemark);
            } else if (result.state.equals("失效")) {
                ivState.setImageResource(R.drawable.icon_invalid_new);

                setTextViewStyle(tvMaterialNameTitle, tvMaterialName, tvMaterialNumTitle, tvMaterialNum, tvSendAddressTitle, tvSendAddress,
                        tvReceiveAddressTitle, tvReceiveAddress, tvReceiveTimeTitle, tvReceiveTime, tvEffectiveTimeTitle, tvEffectiveTime,
                        tvContactTitle, tvContact, tvContactPhoneTitle, tvContactPhone, tvRemarkTitle, tvRemark);
            }

            ivCancel.setVisibility(result.operaStatus == 1 ? View.VISIBLE : View.INVISIBLE);

        });


        vm.liveData.observe(this, result -> {
            ToastUtils.showShort("取消找物流成功.");
            finish();
        });


        vm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        vm.logisticsDetail(id);
    }

    //修改页面TextView的字体颜色
    private void setTextViewStyle(TextView... views) {
        for (TextView view : views) {
            view.setTextColor(getResources().getColor(R.color.colorTvHint));
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_cancel})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_cancel:
                new CustomDialog(FindLogisticsDetailActivity.this)
                        .setTip(getString(R.string.cancel_find_logistics))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                vm.logisticsClose(id);
                            }
                        }).showDialog();
                break;
        }
    }
}
