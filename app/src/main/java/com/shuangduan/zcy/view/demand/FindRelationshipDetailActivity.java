package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.DemandRelationshipVm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  找关系详情
 * @time 2019/8/20 17:04
 * @change
 * @chang time
 * @class describe
 */
public class FindRelationshipDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_tel)
    EditText edtTel;
    @BindView(R.id.edt_remarks)
    EditText edtRemarks;
    private DemandRelationshipVm demandRelationshipVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_relationship_detail;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.find_relationship_detail));

        demandRelationshipVm = ViewModelProviders.of(this).get(DemandRelationshipVm.class);
        demandRelationshipVm.id = getIntent().getIntExtra(CustomConfig.DEMAND_ID, 0);
        demandRelationshipVm.replyLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.find_relationship_reply_success));
            edtRemarks.setText("");
            edtName.setText("");
            edtTel.setText("");
        });
        demandRelationshipVm.relationshipDetailLiveData.observe(this, relationshipDetailBean -> {
            tvTitle.setText(relationshipDetailBean.getTitle());
            tvCommission.setText(String.format(getString(R.string.format_amount_bi), relationshipDetailBean.getPrice()));
            tvTime.setText(String.format(getString(R.string.format_validity_period), relationshipDetailBean.getStart_time(), relationshipDetailBean.getEnd_time()));
            tvDes.setText(relationshipDetailBean.getIntro());
        });
        demandRelationshipVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        demandRelationshipVm.relationshipDetail();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(edtName.getText())){
                    ToastUtils.showShort(getString(R.string.hint_name_or_company));
                    return;
                }if (TextUtils.isEmpty(edtTel.getText())){
                    ToastUtils.showShort(getString(R.string.hint_contact));
                    return;
                }if (TextUtils.isEmpty(edtRemarks.getText())){
                    ToastUtils.showShort(getString(R.string.hint_relationship_remarks));
                    return;
                }
                demandRelationshipVm.reply(edtName.getText().toString(), edtTel.getText().toString(), edtRemarks.getText().toString());
                break;
        }
    }
}
