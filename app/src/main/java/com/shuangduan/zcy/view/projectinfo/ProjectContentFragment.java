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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ContactAdapter;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.ProjectContentBean;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

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

        tvDetail.setText("项目为政府规划用地，占地11230㎡，项目为政府规划用地，占地...");
        tvMaterial.setText("门窗玻璃、外墙装饰、防水防腐、油...");

        List<ProjectContentBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new ProjectContentBean());
        }
        rvContact.setLayoutManager(new LinearLayoutManager(mContext));
        rvContact.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ContactAdapter contactAdapter = new ContactAdapter(R.layout.item_contact, list);
        rvContact.setAdapter(contactAdapter);
    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_read_detail})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_read_detail:
                new CustomDialog(mActivity)
                        .setTip("查看此消息请支付1亿元")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {

                            }
                        })
                        .showDialog();
                break;
        }
    }
}
