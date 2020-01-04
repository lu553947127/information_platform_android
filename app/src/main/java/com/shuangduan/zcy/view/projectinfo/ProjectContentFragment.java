package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ContactAdapter;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.ProjectDetailBean;
import com.shuangduan.zcy.utils.PhoneFormatCheckUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  概况
 * @time 2019/7/15 15:30
 * @change
 * @chang time
 * @class describe
 */
public class ProjectContentFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {
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
    @BindView(R.id.tv_read_detail)
    TextView tvReadDetail;
    @BindView(R.id.ll_material)
    LinearLayout llMaterial;
    @BindView(R.id.iv_attn)
    ImageView ivAttn;
    private ContactAdapter contactAdapter;
    private ProjectDetailActivity activity;
    private ProjectDetailBean.DetailBean detail;

    public static ProjectContentFragment newInstance() {
        ProjectContentFragment fragment = new ProjectContentFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_content;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View v) {

        activity = (ProjectDetailActivity) mActivity;
        rvContact.setLayoutManager(new LinearLayoutManager(mContext));
        contactAdapter = new ContactAdapter(R.layout.item_contact, null, 0);
        contactAdapter.setEmptyView(R.layout.layout_loading_top, rvContact);
        contactAdapter.setOnItemChildClickListener(this);
        rvContact.setAdapter(contactAdapter);

        //基本信息设置
        activity.projectDetailVm.detailLiveData.observe(this, projectDetailBean -> {
            detail = projectDetailBean.getDetail();
            activity.projectDetailVm.titleLiveData.postValue(detail.getTitle());
            activity.projectDetailVm.locationLiveData.postValue(detail.getProvince() + detail.getCity() + detail.getAddress());
            activity.projectDetailVm.latitudeLiveData.postValue(new LatLng(detail.getLatitude(), detail.getLongitude()));
            activity.projectDetailVm.introLiveData.postValue(detail.getIntro());
            activity.projectDetailVm.materialLiveData.postValue(detail.getMaterials());
            activity.projectDetailVm.collectionLiveData.postValue(projectDetailBean.getDetail().getCollection());
            activity.projectDetailVm.subscribeLiveData.postValue(projectDetailBean.getDetail().getWarrant_status());

            detail = projectDetailBean.getDetail();
            tvUpdateTime.setText(String.format(getString(R.string.format_update), detail.getUpdate_time()));
            tvStage.setText(String.format(getString(R.string.format_stage), detail.getPhases()));
            tvType.setText(String.format(getString(R.string.format_type), detail.getType()));
            tvCycle.setText(String.format(getString(R.string.format_cycle), detail.getCycle()));

            tvAcreage.setText(detail.getAcreage().equals("未确定") ? String.format(getString(R.string.format_no_acreage), detail.getAcreage()) :
                    String.format(getString(R.string.format_acreage), detail.getAcreage()));

            tvPrice.setText(String.format(getString(R.string.format_valuation), detail.getValuation()));

            String introStr = detail.getIntro().trim();
            String materialStr = detail.getMaterials().trim();

            if (detail.getIs_pay() == 1) {
                tvDetail.setText(Html.fromHtml(introStr));
                tvMaterial.setText(materialStr);
            } else {
                if (!StringUtils.isEmpty(introStr)) {
                    tvDetail.setHighlightColor(getResources().getColor(R.color.color_6a5ff8));
                    SpannableString detailInfo = new SpannableString(Html.fromHtml(introStr) + " 查看详情");
                    detailInfo.setSpan(new Clickable(v1 -> addPayDialog()), Html.fromHtml(introStr).length(), detailInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvDetail.setText(detailInfo);
                    tvDetail.setMovementMethod(LinkMovementMethod.getInstance());
                }

                if (!StringUtils.isEmpty(materialStr)) {
                    tvMaterial.setHighlightColor(getResources().getColor(R.color.color_6a5ff8));
                    SpannableString materialInfo = new SpannableString(materialStr + " 查看详情");
                    materialInfo.setSpan(new Clickable(v1 -> addPayDialog()), materialStr.length(), materialInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvMaterial.setText(materialInfo);
                    tvMaterial.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
            llMaterial.setVisibility(StringUtils.isTrimEmpty(detail.getMaterials()) ? View.GONE : View.VISIBLE);
            setEmpty();
            contactAdapter.setIsPay(detail.getIs_pay());
            contactAdapter.setNewData(projectDetailBean.getContact());
        });
    }

    @Override
    protected void initDataFromService() {
        activity.projectDetailVm.getDetail();
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
                addPayDialog();
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ProjectDetailBean.ContactBean listBean = contactAdapter.getData().get(position);
        switch (view.getId()) {
            case R.id.tv_phone:
                if (detail.getIs_pay() == 1) {
                    new CustomDialog(mActivity)
                            .setTip("联系电话: " + listBean.getTel())
                            .setOk("打电话")
                            .setCallBack(new BaseDialog.CallBack() {

                                @Override
                                public void cancel() {

                                }

                                @Override
                                public void ok(String s) {
                                    PhoneFormatCheckUtils.getCallPhone(mActivity.getApplicationContext(), listBean.getTel());
                                }
                            }).showDialog();
                } else {
                    addPayDialog();
                }
                break;
        }
    }

    private void addPayDialog() {
        try {
            addDialog(new CustomDialog(activity)
                    .setTip(String.format(getString(R.string.format_pay_price_project), Objects.requireNonNull(activity.projectDetailVm.detailLiveData.getValue()).getDetail().getDetail_price()))
                    .setCallBack(new BaseDialog.CallBack() {
                        @Override
                        public void cancel() {
                        }

                        @Override
                        public void ok(String s) {
                            int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
                            if (status == 1) {
                                activity.goToPay();
                            } else {
                                //查询是否设置支付密码
                                activity.updatePwdPayVm.payPwdState();
                            }
                        }
                    })
                    .showDialog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看详情的点击事件
     */
    class Clickable extends ClickableSpan {

        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        /*** 重写父类点击事件*/

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        /*** 重写父类updateDrawState方法 我们可以给TextView设置字体颜色,背景颜色等等...*/
        @Override

        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.color_6a5ff8));
        }//可点击文字的颜色
    }
}
