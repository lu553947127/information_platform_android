package com.shuangduan.zcy.view.projectinfo;

import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  概况
 * @time 2019/7/15 15:30
 * @change
 * @chang time
 * @class describe
 */
public class ProjectContentFragment extends BaseFragment {

    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_principal)
    TextView tvPrincipal;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.tv_stage)
    TextView tvStage;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_cycle)
    TextView tvCycle;
    @BindView(R.id.tv_acreage)
    TextView tvAcreage;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.tv_read_contact)
    TextView tvReadContact;

    public static ProjectContentFragment newInstance() {

        Bundle args = new Bundle();

        ProjectContentFragment fragment = new ProjectContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_content;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        tvUpdateTime.setText(String.format(getString(R.string.format_update), "2019-05-17"));
        tvStage.setText(String.format(getString(R.string.format_stage), "勘察设计"));
        tvType.setText(String.format(getString(R.string.format_type), "商业住宅、商业综合/零售、社区服务"));
        tvCycle.setText(String.format(getString(R.string.format_cycle), "2019-05-01至2019-10-01"));
        tvAcreage.setText(String.format(getString(R.string.format_acreage), "11230㎡"));
        tvPrice.setText(String.format(getString(R.string.format_price), "3.8亿"));

        SpanUtils.with(tvDetail)
                .append("项目为政府规划用地，占地11230㎡，项目为政府规划用地，占地...")
                .append("查看详情").setForegroundColor(getResources().getColor(R.color.colorPrimary))
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showShort("详情");
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        //重写此方法屏蔽点击自带的样式，setForegroundColor才能生效
                    }
                }).create();
        SpanUtils.with(tvMaterial)
                .append("门窗玻璃、外墙装饰、防水防腐、油...")
                .append("查看详情").setForegroundColor(getResources().getColor(R.color.colorPrimary))
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showShort("详情");
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        //重写此方法屏蔽点击自带的样式，setForegroundColor才能生效
                    }
                }).create();
        tvUnit.setText(String.format(getString(R.string.format_unit), "滨州市*****单位"));
        tvPrincipal.setText(String.format(getString(R.string.format_principal), "王女士"));
        tvPhone.setText(String.format(getString(R.string.format_phone), "151****1232 "));
        tvAddress.setText(String.format(getString(R.string.format_address), "山东省济南市莱芜区"));
    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_read_contact})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_read_contact:
                break;
        }
    }
}
