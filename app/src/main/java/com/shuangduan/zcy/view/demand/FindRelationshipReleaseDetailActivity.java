package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.FindRelationshipReleaseBean;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.vm.DemandRelationshipVm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  我的需求，我发布的，详情
 * @time 2019/8/22 9:00
 * @change
 * @chang time
 * @class describe
 */
public class FindRelationshipReleaseDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.tv_ing)
    TextView tvIng;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.ll_find_relation_ship)
    LinearLayout llFindRelationShip;
    @BindView(R.id.marquee)
    TextView tvMarquee;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_relationship_release_detail;
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
        demandRelationshipVm.id = getIntent().getIntExtra(CustomConfig.DEMAND_ID, 0);
        demandRelationshipVm.relationshipReleaseDetailLiveData.observe(this, findRelationshipReleaseBean -> {
            tvTitle.setText(findRelationshipReleaseBean.getTitle());
            tvCommission.setText(String.format(getString(R.string.format_amount_bi), findRelationshipReleaseBean.getPrice()));
            tvTime.setText(String.format(getString(R.string.format_validity_period_less), findRelationshipReleaseBean.getStart_time(), findRelationshipReleaseBean.getEnd_time()));
            tvDes.setText(findRelationshipReleaseBean.getIntro());
            switch (findRelationshipReleaseBean.getReply_status()) {
                case 1://进行中
                    tvIng.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                    break;
                case 2://有回复
                    tvIng.setVisibility(View.GONE);
                    tvError.setVisibility(View.GONE);
                    FindRelationshipReleaseBean.ReplyBean reply = findRelationshipReleaseBean.getReply();
                    tvName.setText(reply.getName());
                    tvTel.setText(reply.getTel());
                    tvRemarks.setText(reply.getIntro());
                    break;
                case 3://过期
                    tvIng.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    break;
            }
        });
        demandRelationshipVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        demandRelationshipVm.releaseDetail();
        //提示 只显示一次
        if (SPUtils.getInstance().getInt("isFindRelationShip")==-1){
            SPUtils.getInstance().put("isFindRelationShip",1);
            llFindRelationShip.setVisibility(View.VISIBLE);
            tvMarquee.setSelected(true);
        }else {
            llFindRelationShip.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
