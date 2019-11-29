package com.shuangduan.zcy.vm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.DemandBuyerAdapter;
import com.shuangduan.zcy.adapter.FindRelationshipAdapter;
import com.shuangduan.zcy.adapter.FindSubstanceAdapter;
import com.shuangduan.zcy.adapter.XTabLayoutAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;
import com.shuangduan.zcy.view.demand.FindBuyerDetailActivity;
import com.shuangduan.zcy.view.demand.FindRelationshipDetailActivity;
import com.shuangduan.zcy.view.demand.FindSubstanceDetailActivity;
import com.shuangduan.zcy.weight.AutoScrollRecyclerView;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.MaterialIndicator;
import com.shuangduan.zcy.weight.XTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.vm
 * @ClassName: HomeNeedVm
 * @Description: 首页/需求资讯
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/29 13:47
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/29 13:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("InflateParams")
public class HomeNeedVm extends BaseViewModel {

    public AutoScrollRecyclerView recyclerView1;
    public AutoScrollRecyclerView recyclerView2;
    public AutoScrollRecyclerView recyclerView3;
    private List<View> viewList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public void HomeNeedVm(Context context, View views, ViewPager viewPager, XTabLayout tabLayout, MaterialIndicator materialIndicator
            , DemandRelationshipVm demandRelationshipVm) {
        if (titleList != null && titleList.size() > 0) {
            titleList.clear();
        }
        if (viewList != null && viewList.size() > 0) {
            viewList.clear();
        }
        views.getBackground().mutate().setAlpha(10);
        View view1 = LayoutInflater.from(context).inflate(R.layout.fragment_demand_information, null, false);
        View view2 = LayoutInflater.from(context).inflate(R.layout.fragment_demand_information, null, false);
        View view3 = LayoutInflater.from(context).inflate(R.layout.fragment_demand_information, null, false);
        titleList.add("找关系");
        titleList.add("找物资");
        titleList.add("找买家");
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        XTabLayoutAdapter xTabLayoutAdapter = new XTabLayoutAdapter(viewList, titleList);
        viewPager.setAdapter(xTabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(materialIndicator);
        materialIndicator.setAdapter(Objects.requireNonNull(viewPager.getAdapter()));

        recyclerView1 = view1.findViewById(R.id.rv);
        recyclerView2 = view2.findViewById(R.id.rv);
        recyclerView3 = view3.findViewById(R.id.rv);
        ImageView iv_empty1 = view1.findViewById(R.id.iv_icon);
        ImageView iv_empty2 = view2.findViewById(R.id.iv_icon);
        ImageView iv_empty3 = view3.findViewById(R.id.iv_icon);
        TextView tv_empty1 = view1.findViewById(R.id.tv_tip);
        TextView tv_empty2 = view2.findViewById(R.id.tv_tip);
        TextView tv_empty3 = view3.findViewById(R.id.tv_tip);
        ConstraintLayout cl_empty1 = view1.findViewById(R.id.cl_empty);
        ConstraintLayout cl_empty2 = view2.findViewById(R.id.cl_empty);
        ConstraintLayout cl_empty3 = view3.findViewById(R.id.cl_empty);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView3.setNestedScrollingEnabled(false);

        recyclerView1.setLayoutManager(new LinearLayoutManager(context));
        recyclerView1.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        FindRelationshipAdapter relationshipAdapter = new FindRelationshipAdapter(R.layout.item_demand_relationship, null);
        recyclerView1.setAdapter(relationshipAdapter);

        recyclerView2.setLayoutManager(new LinearLayoutManager(context));
        recyclerView2.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        FindSubstanceAdapter substanceAdapter = new FindSubstanceAdapter(R.layout.item_demand_substance, null);
        recyclerView2.setAdapter(substanceAdapter);

        recyclerView3.setLayoutManager(new LinearLayoutManager(context));
        recyclerView3.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        DemandBuyerAdapter buyerAdapter = new DemandBuyerAdapter(R.layout.item_demand_buyer, null);
        recyclerView3.setAdapter(buyerAdapter);

        relationshipAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandRelationshipBean.ListBean listBean = relationshipAdapter.getData().get(position % adapter.getData().size());
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindRelationshipDetailActivity.class);
        });

        substanceAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandSubstanceBean.ListBean listBean = substanceAdapter.getData().get(position % adapter.getData().size());
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindSubstanceDetailActivity.class);
        });

        buyerAdapter.setOnItemClickListener((adapter1, view, position) -> {
            DemandBuyerBean.ListBean listBean = buyerAdapter.getData().get(position % adapter1.getData().size());
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindBuyerDetailActivity.class);
        });

        demandRelationshipVm.getRelationship();
        DemandSubstanceVm demandSubstanceVm = ViewModelProviders.of((FragmentActivity) context).get(DemandSubstanceVm.class);
        demandSubstanceVm.getSubstance();
        DemandBuyerVm demandBuyerVm = ViewModelProviders.of((FragmentActivity) context).get(DemandBuyerVm.class);
        demandBuyerVm.getBuyer();

        demandRelationshipVm.relationshipLiveData.observe((LifecycleOwner) context, relationshipBean -> {
            if (relationshipBean.getList() != null && relationshipBean.getList().size() != 0) {
                cl_empty1.setVisibility(View.GONE);
                if (relationshipBean.getPage() == 1) {
                    relationshipAdapter.setNewData(relationshipBean.getList());
                } else {
                    relationshipAdapter.addData(relationshipBean.getList());
                }
            } else {
                cl_empty1.setVisibility(View.VISIBLE);
                iv_empty1.setImageResource(R.drawable.icon_empty_project);
                tv_empty1.setText(R.string.empty_pull_strings_info);
            }
        });
        demandSubstanceVm.substanceLiveData.observe((LifecycleOwner) context, demandSubstanceBean -> {
            if (demandSubstanceBean.getList() != null && demandSubstanceBean.getList().size() != 0) {
                cl_empty2.setVisibility(View.GONE);
                if (demandSubstanceBean.getPage() == 1) {
                    substanceAdapter.setNewData(demandSubstanceBean.getList());
                } else {
                    substanceAdapter.addData(demandSubstanceBean.getList());
                }
            } else {
                cl_empty2.setVisibility(View.VISIBLE);
                iv_empty2.setImageResource(R.drawable.icon_empty_project);
                tv_empty2.setText(R.string.empty_substance_info);
            }
        });
        demandBuyerVm.buyerLiveData.observe((LifecycleOwner) context, demandBuyerBean -> {
            if (demandBuyerBean.getList() != null && demandBuyerBean.getList().size() != 0) {
                cl_empty3.setVisibility(View.GONE);
                if (demandBuyerBean.getPage() == 1) {
                    buyerAdapter.setNewData(demandBuyerBean.getList());
                } else {
                    buyerAdapter.addData(demandBuyerBean.getList());
                }
            } else {
                cl_empty3.setVisibility(View.VISIBLE);
                iv_empty3.setImageResource(R.drawable.icon_empty_project);
                tv_empty3.setText(R.string.empty_buyer_info);
            }
        });
    }
}
