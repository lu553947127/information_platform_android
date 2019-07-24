package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ContactAdapter;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.view.PayActivity;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

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
    @BindView(R.id.rv_contact)
    RecyclerView rvContact;
    private ProjectDetailVm projectDetailVm;
    private ContactAdapter contactAdapter;

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

        rvContact.setLayoutManager(new LinearLayoutManager(mContext));
        rvContact.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        contactAdapter = new ContactAdapter(R.layout.item_contact, null);
        contactAdapter.setEmptyView(R.layout.layout_loading_top, rvContact);
        rvContact.setAdapter(contactAdapter);

        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm.getDetail();
        projectDetailVm.detailLiveData.observe(this, projectDetailBean -> {
            ProjectDetailBean.DetailBean detail = projectDetailBean.getDetail();
            projectDetailVm.titleLiveData.postValue(detail.getTitle());
            projectDetailVm.locationLiveData.postValue(detail.getProvince() + detail.getCity());
            projectDetailVm.introLiveData.postValue(detail.getIntro());
            projectDetailVm.materialLiveData.postValue(detail.getMaterials());
            projectDetailVm.collectionLiveData.postValue(projectDetailBean.getDetail().getCollection());
            projectDetailVm.subscribeLiveData.postValue(projectDetailBean.getDetail().getWarrant_status());

            tvUpdateTime.setText(String.format(getString(R.string.format_update), detail.getUpdate_time()));
            tvStage.setText(String.format(getString(R.string.format_stage), detail.getPhases()));
            tvType.setText(String.format(getString(R.string.format_type), detail.getType()));
            tvCycle.setText(String.format(getString(R.string.format_cycle), detail.getCycle()));
            tvAcreage.setText(String.format(getString(R.string.format_acreage), detail.getAcreage()));
            tvPrice.setText(String.format(getString(R.string.format_price), detail.getValuation()));
            tvDetail.setText(detail.getIntro());
            tvMaterial.setText(detail.getMaterials());

            setEmpty();
            contactAdapter.setNewData(projectDetailBean.getContact());
        });
    }

    private void setEmpty() {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_top, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_contact));
        contactAdapter.setEmptyView(empty);
    }

    @OnClick({R.id.tv_read_detail})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_read_detail:
                new CustomDialog(mActivity)
                        .setTip(String.format(getString(R.string.format_pay_price), projectDetailVm.detailLiveData.getValue().getDetail().getDetail_price()))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                ActivityUtils.startActivity(PayActivity.class);
                            }
                        })
                        .showDialog();
                break;
        }
    }
}
