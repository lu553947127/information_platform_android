package com.shuangduan.zcy.view.mine.demand;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.vm.DemandRelationshipVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class 我的需求，找关系， 我接单的详情
 * @time 2019/8/22 9:01
 * @change
 * @chang time
 * @class describe
 */
public class FindRelationshipAcceptDetailActivity extends BaseActivity {
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.tv_reject)
    TextView tvReject;
    @BindView(R.id.tv_reject_tip)
    TextView tvRejectTip;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_relationship_accept_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
        tvBarTitle.setText(getString(R.string.find_relationship_detail));

        DemandRelationshipVm demandRelationshipVm = ViewModelProviders.of(this).get(DemandRelationshipVm.class);
        demandRelationshipVm.relationships_reply_id = getIntent().getIntExtra(CustomConfig.DEMAND_ID, 0);
        demandRelationshipVm.relationshipAcceptDetailLiveData.observe(this, findRelationshipAcceptBean -> {
            tvTitle.setText(findRelationshipAcceptBean.getTitle());
            tvCommission.setText(String.format(getString(R.string.format_amount_bi), findRelationshipAcceptBean.getPrice()));
            tvTime.setText(String.format(getString(R.string.format_validity_period_less), findRelationshipAcceptBean.getStart_time(), findRelationshipAcceptBean.getEnd_time()));
            tvDes.setText(findRelationshipAcceptBean.getIntro());
            tvName.setText(findRelationshipAcceptBean.getReply_name());
            tvTel.setText(findRelationshipAcceptBean.getReply_tel());
            tvRemarks.setText(findRelationshipAcceptBean.getReply_intro());
            switch (findRelationshipAcceptBean.getStatus()) {
                case 0://待审核
                    ivStatus.setImageResource(R.drawable.icon_review);
                    tvReject.setVisibility(View.INVISIBLE);
                    tvRejectTip.setVisibility(View.INVISIBLE);
                    break;
                case 1://审核成功
                    ivStatus.setImageResource(R.drawable.icon_pass);
                    tvReject.setVisibility(View.INVISIBLE);
                    tvRejectTip.setVisibility(View.INVISIBLE);
                    break;
                case 2://被驳回
                    ivStatus.setImageResource(R.drawable.icon_reject);
                    tvReject.setVisibility(View.VISIBLE);
                    tvRejectTip.setVisibility(View.VISIBLE);
                    break;
            }
        });
        demandRelationshipVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        demandRelationshipVm.acceptDetail();
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
