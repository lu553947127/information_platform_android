package com.shuangduan.zcy.adminManage.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.GroundingAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.SelectorAreaSecondAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverAdapter;
import com.shuangduan.zcy.adminManage.adapter.UseStatueAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.vm.MultiAreaVm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: TurnoverGroupFragment
 * @Description: 周转材料集团列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 15:01
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 15:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverGroupFragment extends BaseLazyFragment {

    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_grounding)
    AppCompatTextView tvGrounding;
    @BindView(R.id.tv_use_statue)
    AppCompatTextView tvUseStatue;
    @BindView(R.id.tv_depositing_place)
    AppCompatTextView tvDepositingPlace;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private TurnoverVm turnoverVm;
    private MultiAreaVm areaVm;
    private TurnoverAdapter turnoverAdapter;

    public static TurnoverGroupFragment newInstance() {
        Bundle args = new Bundle();
        TurnoverGroupFragment fragment = new TurnoverGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_turnover_group;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("材料名称");

        if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_ADD,0) ==1){
            ivAdd.setVisibility(View.VISIBLE);
        }else {
            ivAdd.setVisibility(View.GONE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        turnoverAdapter = new TurnoverAdapter(R.layout.item_turnover, null,SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_EDIT,0)
                , SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_DELETE,0),0);
        recyclerView.setAdapter(turnoverAdapter);

        turnoverVm = ViewModelProviders.of(this).get(TurnoverVm.class);

        //获取周转材料列表数据
        turnoverVm.turnoverLiveData.observe(this,turnoverBean -> {
            if (turnoverBean.getPage() == 1) {
                turnoverAdapter.setNewData(turnoverBean.getList());
                turnoverAdapter.setEmptyView(R.layout.layout_empty_admin, recyclerView);
            } else {
                turnoverAdapter.addData(turnoverAdapter.getData().size(), turnoverBean.getList());
            }
            setNoMore(turnoverBean.getPage(), turnoverBean.getCount());
        });
        turnoverAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_DETAIL,0)==1){

            }
        });
        turnoverAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_edit://编辑
                    break;
                case R.id.tv_delete://删除
                    break;
            }
        });

        //获取筛选条件列表数据
        turnoverVm.turnoverTypeData.observe(this,turnoverTypeBean -> {
            switch (turnoverVm.type){
                case "grounding":
                    groundingList=turnoverTypeBean.getIs_shelf();
                    groundingList.add(0,new TurnoverTypeBean.IsShelfBean(0,"全部"));
                    groundingAdapter.setNewData(groundingList);
                    break;
                case "use_statue":
                    useStatueList=turnoverTypeBean.getUse_status();
                    useStatueList.add(0,new TurnoverTypeBean.UseStatusBean(0,"全部"));
                    useStatueAdapter.setNewData(useStatueList);
                    break;
            }
        });

        //获取省市数据
        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);
        areaVm.provinceLiveData.observe(this, provinceBeans -> {
            provinceList = provinceBeans;
            provinceList.add(0,new ProvinceBean(0,"全部"));
            provinceAdapter.setNewData(provinceList);
        });
        areaVm.cityLiveData.observe(this, cityBeans -> {
            cityList = cityBeans;
            cityList.add(0,new CityBean(0,"全部"));
            cityAdapter.setNewData(cityList);
        });

        //下拉刷新和上拉加载
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                turnoverVm.constructionList(1,areaVm.id,areaVm.city_id,0,0);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                turnoverVm.constructionListMore(1,areaVm.id,areaVm.city_id,0,0);
            }
        });
        //滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                AnimationUtils.listScrollAnimation(ivAdd,dy);
            }
        });
    }

    private void setNoMore(int page, int count) {
        if (page == 1) {
            if (page * 10 >= count) {
                if (refresh.getState() == RefreshState.None) {
                    refresh.setNoMoreData(true);
                } else {
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                refresh.finishRefresh();
            }
        } else {
            if (page * 10 >= count) {
                refresh.finishLoadMoreWithNoMoreData();
            } else {
                refresh.finishLoadMore();
            }
        }
    }

    @Override
    protected void initDataFromService() {
        turnoverVm.constructionList(1,areaVm.id,areaVm.city_id,0,0);
    }

    @SuppressLint("NewApi")
    @OnClick({R.id.tv_name,R.id.tv_grounding,R.id.tv_use_statue,R.id.tv_depositing_place})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_name://选择材料名称
                break;
            case R.id.tv_grounding://是否上架
                getBottomSheetDialog(R.layout.dialog_is_grounding,"grounding");
                getDrawableRightView(tvGrounding,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_use_statue://使用状态
                getBottomSheetDialog(R.layout.dialog_is_grounding,"use_statue");
                getDrawableRightView(tvUseStatue,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
            case R.id.tv_depositing_place://存放地点
                getBottomSheetDialog(R.layout.dialog_depositing_place,"depositing_place");
                getDrawableRightView(tvDepositingPlace,R.drawable.icon_pullup_arrow,R.color.color_5C54F4);
                break;
        }
    }

    private SelectorAreaFirstAdapter provinceAdapter;
    private SelectorAreaSecondAdapter cityAdapter;
    private List<ProvinceBean> provinceList = new ArrayList<>();
    private List<CityBean> cityList = new ArrayList<>();
    private GroundingAdapter groundingAdapter;
    private List<TurnoverTypeBean.IsShelfBean> groundingList =new ArrayList<>();
    private UseStatueAdapter useStatueAdapter;
    private List<TurnoverTypeBean.UseStatusBean> useStatueList =new ArrayList<>();
    @SuppressLint("RestrictedApi,InflateParams")
    private void getBottomSheetDialog(int layout, String type) {
        //底部滑动对话框
        BottomSheetDialogs btn_dialog = new BottomSheetDialogs(Objects.requireNonNull(getActivity()));
        //设置自定view
         View dialog_view = this.getLayoutInflater().inflate(layout, null);
        //把布局添加进去
        btn_dialog.setContentView(dialog_view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_dialog.setOnDismissListener(dialog -> {
            getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvGrounding,R.drawable.icon_pulldown_arrow,R.color.color_666666);
            getDrawableRightView(tvUseStatue,R.drawable.icon_pulldown_arrow,R.color.color_666666);
        });
        turnoverVm.type=type;
        switch (type){
            case "grounding":
                TextView tvGrounding=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvGrounding).setText("是否上架");
                RecyclerView rvGrounding = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvGrounding).setLayoutManager(new LinearLayoutManager(getActivity()));
                groundingAdapter = new GroundingAdapter(R.layout.adapter_selector_area_second, null);
                rvGrounding.setAdapter(groundingAdapter);
                groundingAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverVm.is_shelf=groundingList.get(position).getId();
                    turnoverVm.constructionList(1,areaVm.id,areaVm.city_id,0,0);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvGrounding,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                });
                turnoverVm.constructionSearch();
                if (turnoverVm.is_shelf!=0){
                    groundingAdapter.setIsSelect(turnoverVm.is_shelf);
                }
                break;
            case "use_statue":
                TextView tvUseStatue=btn_dialog.findViewById(R.id.tv_title);
                Objects.requireNonNull(tvUseStatue).setText("使用状态");
                RecyclerView rvUseStatue = btn_dialog.findViewById(R.id.rv);
                Objects.requireNonNull(rvUseStatue).setLayoutManager(new LinearLayoutManager(getActivity()));
                useStatueAdapter = new UseStatueAdapter(R.layout.adapter_selector_area_second, null);
                rvUseStatue.setAdapter(useStatueAdapter);
                useStatueAdapter.setOnItemClickListener((adapter, view, position) -> {
                    turnoverVm.use_status=useStatueList.get(position).getId();
                    turnoverVm.constructionList(1,areaVm.id,areaVm.city_id,0,0);
                    btn_dialog.dismiss();
                    getDrawableRightView(tvUseStatue,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                });
                turnoverVm.constructionSearch();
                if (turnoverVm.use_status!=0){
                    useStatueAdapter.setIsSelect(turnoverVm.use_status);
                }
                break;
            case "depositing_place":
                RecyclerView rvProvince = btn_dialog.findViewById(R.id.rv_province);
                RecyclerView rvCity = btn_dialog.findViewById(R.id.rv_city);
                Objects.requireNonNull(rvProvince).setLayoutManager(new LinearLayoutManager(getActivity()));
                Objects.requireNonNull(rvCity).setLayoutManager(new LinearLayoutManager(getActivity()));
                provinceAdapter = new SelectorAreaFirstAdapter(R.layout.adapter_selector_area_first, null);
                cityAdapter = new SelectorAreaSecondAdapter(R.layout.adapter_selector_area_second, null);
                rvProvince.setAdapter(provinceAdapter);
                rvCity.setAdapter(cityAdapter);
                provinceAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (provinceList.get(position).getId()!=0){
                        areaVm.id = provinceList.get(position).getId();
                        areaVm.cityResult = provinceList.get(position).getName();
                        areaVm.getCity();
                        provinceAdapter.setIsSelect(provinceList.get(position).getId());
                    }else {
                        areaVm.id=0;
                        areaVm.city_id=0;
                        turnoverVm.constructionList(1,areaVm.id,areaVm.city_id,0,0);
                        btn_dialog.dismiss();
                        getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    }
                });
                cityAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (cityList.get(position).getId()!=0){
                        areaVm.city_id = cityList.get(position).getId();
                        turnoverVm.constructionList(1,areaVm.id,areaVm.city_id,0,0);
                        cityAdapter.setIsSelect(cityList.get(position).getId());
                        btn_dialog.dismiss();
                        getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    }else {
                        areaVm.city_id=0;
                        turnoverVm.constructionList(1,areaVm.id,areaVm.city_id,0,0);
                        btn_dialog.dismiss();
                        getDrawableRightView(tvDepositingPlace,R.drawable.icon_pulldown_arrow,R.color.color_666666);
                    }
                });
                areaVm.getProvince();
                if (areaVm.id!=0){
                    provinceAdapter.setIsSelect(areaVm.id);
                    areaVm.getCity();
                }
                if (areaVm.city_id!=0){
                    cityAdapter.setIsSelect(areaVm.city_id);
                }
                break;
        }
        btn_dialog.show();
    }

    //给textView设置drawableRight图片
    private void getDrawableRightView(TextView textView,int icon,int color) {
        Drawable drawable = mContext.getResources().getDrawable(icon);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setTextColor(getResources().getColor(color));
    }
}
