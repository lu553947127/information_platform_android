package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.TransRecordAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.model.bean.TransRecordBean;
import com.shuangduan.zcy.model.bean.TransRecordFilterBean;
import com.shuangduan.zcy.model.bean.TransactionFlowTypeBean;
import com.shuangduan.zcy.vm.TransRecordVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  交易记录
 * @time 2019/8/16 8:41
 * @change
 * @chang time
 * @class describe
 */
public class TransRecordActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_in_out)
    TextView tvInOut;
    @BindView(R.id.iv_in_out)
    ImageView ivInOut;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.over)
    View over;
    private TransRecordAdapter transRecordAdapter;
    private TransRecordVm transRecordVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_trans_record;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getString(R.string.transaction_record));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        transRecordAdapter = new TransRecordAdapter(R.layout.item_trans_record, null);
        rv.setAdapter(transRecordAdapter);
        transRecordAdapter.setOnItemClickListener((adapter, view, position) -> {
            TransRecordBean.ListBean dataBean = transRecordAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.TRANS_RECORD_ID, dataBean.getId());
            ActivityUtils.startActivity(bundle, TransRecordDetailActivity.class);
        });

        transRecordVm = ViewModelProviders.of(this).get(TransRecordVm.class);
        transRecordVm.transRecordLiveData.observe(this, transRecordBean -> {
            if (transRecordBean.getPage() == 1) {
                transRecordAdapter.setNewData(transRecordBean.getList());
                transRecordAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                transRecordAdapter.addData(transRecordBean.getList());
            }
            setNoMore(transRecordBean.getPage(), transRecordBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                transRecordVm.getMoreRecord();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                transRecordVm.getRecord();
            }
        });

        transRecordVm.getRecord();

        transRecordVm.filterLiveData.observe(this, transRecordFilterBean -> {
            showPop();
        });
    }

    private void setNoMore(int page, int count){
        if (page == 1){
            if (page * 10 >= count){
                if (refresh.getState() == RefreshState.None){
                    refresh.setNoMoreData(true);
                }else {
                    refresh.finishRefreshWithNoMoreData();
                }
            }else {
                refresh.finishRefresh();
            }
        }else {
            if (page * 10 >= count){
                refresh.finishLoadMoreWithNoMoreData();
            }else {
                refresh.finishLoadMore();
            }
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.ll_type, R.id.ll_in_out, R.id.over})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.ll_type:
                transRecordVm.currentShow = 1;
                if (popupWindow == null){
                    transRecordVm.getFilter();
                }else {
                    showPop();
                }
                break;
            case R.id.ll_in_out:
                transRecordVm.currentShow = 2;
                if (popupWindow == null){
                    transRecordVm.getFilter();
                }else {
                    showPop();
                }
                break;
            case R.id.over:
                popDismiss();
                break;
        }
    }

    private void popDismiss() {
        popupWindow.dismiss();
        tvType.setTextColor(getResources().getColor(R.color.colorTv));
        ivType.setImageResource(R.drawable.icon_bottom);
        tvInOut.setTextColor(getResources().getColor(R.color.colorTv));
        ivInOut.setImageResource(R.drawable.icon_bottom);
        over.setVisibility(View.GONE);
    }

    private CommonPopupWindow popupWindow;
    private FlexboxLayout flType;
    private FlexboxLayout flInout;
    private void showPop(){
        if (popupWindow == null) {
            popupWindow = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.dialog_transaction_filter)
                    .setOutsideTouchable(false)
                    .setBackGroundLevel(1f)
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setViewOnclickListener((view, layoutResId) -> {
                        flType = view.findViewById(R.id.fl_type);
                        flInout = view.findViewById(R.id.fl_in_out);
                        TransRecordFilterBean value = transRecordVm.filterLiveData.getValue();
                        if (value != null){
                            List<TransRecordFilterBean.FlowTypeBean> flow_type = value.getFlow_type();
                            for (TransRecordFilterBean.FlowTypeBean bean : flow_type) {
                                TextView itemHot = (TextView) LayoutInflater.from(this).inflate(R.layout.item_box, flType, false);
                                itemHot.setText(bean.getName());
                                itemHot.setOnClickListener(l -> {
                                    itemHot.setSelected(bean.getIsSelect() != 1);
                                    bean.setIsSelect(bean.getIsSelect() == 1?0:1);
                                });
                                flType.addView(itemHot);
                            }
                            List<TransRecordFilterBean.TypeBean> type = value.getType();
                            for (TransRecordFilterBean.TypeBean bean : type) {
                                TextView itemHot = (TextView) LayoutInflater.from(this).inflate(R.layout.item_box, flInout, false);
                                itemHot.setText(bean.getName());
                                itemHot.setOnClickListener(l -> {
                                    itemHot.setSelected(bean.getIsSelect() != 1);
                                    bean.setIsSelect(bean.getIsSelect() == 1 ? 0 : 1);
                                });
                                flInout.addView(itemHot);
                            }
                        }

                        view.findViewById(R.id.tv_negative).setOnClickListener(l -> {
                            popDismiss();
                        });
                        view.findViewById(R.id.tv_positive).setOnClickListener(l -> {
                            if (value != null){
                                List<Integer> resultList = new ArrayList<>();
                                List<TransRecordFilterBean.FlowTypeBean> flow_type = value.getFlow_type();
                                for (TransRecordFilterBean.FlowTypeBean bean : flow_type) {
                                    if (bean.getIsSelect() == 1) {
                                        resultList.addAll(bean.getId());
                                    }
                                }
                                TransactionFlowTypeBean transactionFlowTypeBean = new TransactionFlowTypeBean();
                                transactionFlowTypeBean.setFlow_type(resultList);
                                transRecordVm.flowType = transactionFlowTypeBean;

                                List<TransRecordFilterBean.TypeBean> type = value.getType();
                                if (type != null) {
                                    if (type.get(0).getIsSelect() == 1 && type.get(1).getIsSelect() == 1){
                                        transRecordVm.type = 0;
                                    }else if (type.get(0).getIsSelect() == 1){
                                        transRecordVm.type = 1;
                                    }else if (type.get(1).getIsSelect() == 1){
                                        transRecordVm.type = 2;
                                    }else {
                                        transRecordVm.type = 0;
                                    }
                                }
                            }
                            transRecordVm.getRecord();
                            popDismiss();
                        });
                    }).create();
        }
        if (transRecordVm.currentShow == 1){
            flType.setVisibility(View.VISIBLE);
            flInout.setVisibility(View.GONE);
            tvType.setTextColor(getResources().getColor(R.color.colorPrimary));
            ivType.setImageResource(R.drawable.icon_bottom_blue);
            tvInOut.setTextColor(getResources().getColor(R.color.colorTv));
            ivInOut.setImageResource(R.drawable.icon_bottom);
        }if (transRecordVm.currentShow == 2){
            flType.setVisibility(View.GONE);
            flInout.setVisibility(View.VISIBLE);
            tvType.setTextColor(getResources().getColor(R.color.colorTv));
            ivType.setImageResource(R.drawable.icon_bottom);
            tvInOut.setTextColor(getResources().getColor(R.color.colorPrimary));
            ivInOut.setImageResource(R.drawable.icon_bottom_blue);
        }
        if (!popupWindow.isShowing()){
            popupWindow.showAsDropDown(line,0,0);
            over.setVisibility(View.VISIBLE);
        }
    }
}
