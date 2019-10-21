package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ContactAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.vm.ProjectDetailVm;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MineProjectActivity$
 * @class describe
 * @time 2019/10/19 17:17
 * @change
 * @class describe
 */
public class MineProjectActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;

    @BindView(R.id.iv_state)
    ImageView ivState;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_sate)
    TextView tvSate;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_cycle)
    TextView tvCycle;
    @BindView(R.id.tv_acreage)
    TextView tvAcreage;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_party)
    TextView tvParty;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.rv_contact)
    RecyclerView rvContact;

    @BindView(R.id.cb_load)
    CheckBox cbLoad;
    @BindView(R.id.ll_material)
    LinearLayout llMaterial;

    private ProjectDetailVm projectDetailVm;

    private ContactAdapter contactAdapter;
    private List<ProjectDetailBean.ContactBean> contactList;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_project;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.project_project));

        rvContact.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(R.layout.item_contact, null, 1);
        contactAdapter.setEmptyView(R.layout.layout_loading_top, rvContact);

        rvContact.setAdapter(contactAdapter);

        cbLoad.setOnCheckedChangeListener(this);
        //基本信息设置
        projectDetailVm = ViewModelProviders.of(this).get(ProjectDetailVm.class);

        projectDetailVm.init(getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
        projectDetailVm.detailLiveData.observe(this, projectDetailBean -> {
            contactList = projectDetailBean.getContact();
            ProjectDetailBean.DetailBean detail = projectDetailBean.getDetail();
            tvTitle.setText(detail.getTitle());

            tvAddress.setText(detail.getProvince() + detail.getCity());
            tvDate.setText(getString(R.string.format_release_time, detail.getUpdate_time()));
            tvSate.setText(getString(R.string.format_project_stage, detail.getPhases()));
            tvType.setText(detail.getType());
            tvCycle.setText(getString(R.string.format_project_cycle, detail.getCycle()));
            tvAcreage.setText(getString(R.string.format_project_acreage, detail.getAcreage()));
            tvPrice.setText(getString(R.string.format_project_price, detail.getValuation()));
            tvParty.setText(detail.getParty());
            tvDetail.setText(detail.getIntro());
            tvMaterial.setText(detail.getMaterials());
            llMaterial.setVisibility(StringUtils.isTrimEmpty(detail.getMaterials()) ? View.GONE : View.VISIBLE);

            switch (detail.getStatus()) {
                case 0:
                    tvTitle.setEnabled(false);
                    tvTitle.setTextColor(getResources().getColor(R.color.color_646464));
                    ivState.setImageResource(R.drawable.icon_project_audit);
                    break;
                case 1:
                    tvTitle.setTextColor(getResources().getColor(R.color.text1));
                    ivState.setImageResource(R.drawable.icon_check_succeed);
                    break;
                case 2:
                    tvTitle.setEnabled(false);
                    tvTitle.setTextColor(getResources().getColor(R.color.color_646464));
                    ivState.setImageResource(R.drawable.icon_project_failure);
                    break;
            }

            setEmpty();

            if (contactList != null && contactList.size() > 0)
                contactAdapter.setNewData(contactList.subList(0, 1));

            if (projectDetailBean.getContact().size() > 1) {
                cbLoad.setVisibility(View.VISIBLE);
            } else {
                cbLoad.setVisibility(View.GONE);
            }
        });

        projectDetailVm.getMyProjectDateil();
    }


    private void setEmpty() {
        View empty = LayoutInflater.from(this).inflate(R.layout.layout_empty_top, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_contact));
        contactAdapter.setEmptyView(empty);
    }


    @OnClick({R.id.iv_bar_back, R.id.tv_title})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_title:
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.PROJECT_ID, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
                bundle.putInt(CustomConfig.LOCATION, 0);
                ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            contactAdapter.setNewData(contactList);
            cbLoad.setText(R.string.close_all);
        } else {
            contactAdapter.setNewData(contactList.subList(0, 1));
            cbLoad.setText(R.string.see_all);
        }
    }
}
