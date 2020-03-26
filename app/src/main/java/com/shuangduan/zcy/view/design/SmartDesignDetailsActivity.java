package com.shuangduan.zcy.view.design;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.SmartDesignOrderBean;
import com.shuangduan.zcy.vm.SmartDesignVm;
import com.shuangduan.zcy.weight.FlowViewHorizontal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: SmartDesignDetailsActivity
 * @Description: 智能设计订单详情
 * @Author: 徐玉
 * @CreateDate: 2019/12/15 15:14
 * @UpdateUser: 徐玉
 * @UpdateDate: 2019/12/15 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SmartDesignDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_param_info)
    TextView tvParamInfo;
    @BindView(R.id.tv_phone_value)
    TextView tvPhoneValue;
    @BindView(R.id.tv_email_value)
    TextView tvEmailValue;
    @BindView(R.id.tv_order_number_value)
    TextView tvOrderNumberValue;

    @BindView(R.id.tv_order_date_value)
    TextView tvOrderDateValue;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;

    @BindView(R.id.fh_progress)
    FlowViewHorizontal flowViewHorizontal;

    @BindView(R.id.btn_event)
    Button btnEvent;

    private SmartDesignOrderBean.SmartDesignOrder orderDetails;
    private SmartDesignVm vm;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_smart_design_details;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.design_order_title);

        int id = getIntent().getIntExtra("id", 0);

        vm = getViewModel(SmartDesignVm.class);

        vm.orderDetailsLiveData.observe(this, result -> {
            this.orderDetails = result;
            tvName.setText(result.automateName);
            tvParamInfo.setText(result.automateDetail);
            tvPhoneValue.setText(result.mobile);
            tvEmailValue.setText(result.email);
            tvOrderNumberValue.setText(result.orderNo);
            tvOrderDateValue.setText(result.addTime);
            if (result.orderStatus == 2) {
                ivCancel.setVisibility(View.VISIBLE);
            }
            if (result.operaStatus == 1) {
                btnEvent.setText(R.string.cancel_order);
            } else if (result.operaStatus == 2) {
                btnEvent.setText(R.string.delete_order);
            } else {
                btnEvent.setVisibility(View.INVISIBLE);
            }

            vm.getPhases();
        });


        vm.phasesLiveData.observe(this, phases -> {
            List<String> list = new ArrayList<>();
            //获取当前名称字符串列表
            int index = 0;
            for (int i = 0; i < phases.size(); i++) {
                list.add(phases.get(i).phasesName);
                //获取当前订单状态的角标值
                if (orderDetails.phaseStatus == phases.get(i).id) {
                    index = i;
                }
            }
            //转换成数组
            String[] array = list.toArray(new String[list.size()]);

            flowViewHorizontal.setProgress(++index, list.size(), array);
        });

        vm.editLiveData.observe(this, edit -> {

            if (orderDetails.operaStatus == 1) {
                ToastUtils.showShort(R.string.cancel_order_succeed);
                vm.getSmartDesignDetail(id);
            } else if (orderDetails.operaStatus == 2) {
                ToastUtils.showShort(R.string.delete_order_succeed);
                setResult(200);
                finish();
            }
        });

        vm.getSmartDesignDetail(id);
    }


    @OnClick({R.id.iv_bar_back, R.id.btn_event})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.btn_event:
                new CustomDialog(this)
                        .setTip(orderDetails.operaStatus == 1 ? getString(R.string.affirm_cancel_order) : getString(R.string.affirm_delete_order))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                vm.editPost(orderDetails.id, orderDetails.operaStatus);
                            }
                        }).showDialog();
                break;
        }
    }
}
