package com.shuangduan.zcy.adminManage.view;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.TurnoverFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverHistoryAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverSecondAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.event.DeviceEvent;
import com.shuangduan.zcy.adminManage.event.OrderDeviceEvent;
import com.shuangduan.zcy.adminManage.event.OrderTurnoverEvent;
import com.shuangduan.zcy.adminManage.event.TurnoverEvent;
import com.shuangduan.zcy.adminManage.vm.DeviceVm;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.XEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.ADMIN_MANAGE_CONSTRUCTION;
import static com.shuangduan.zcy.app.CustomConfig.ADMIN_MANAGE_CONSTRUCTION_ORDER;
import static com.shuangduan.zcy.app.CustomConfig.ADMIN_MANAGE_EQIPMENT;
import static com.shuangduan.zcy.app.CustomConfig.ADMIN_MANAGE_EQIPMENT_ORDER;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view
 * @ClassName: SelectTypeActivity
 * @Description: 选择子公司/周转材料/设备 列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/29 14:45
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/29 14:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SelectTypeActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    XEditText etSearch;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.tv_history)
    AppCompatTextView tvHistory;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_type)
    AppCompatTextView tvType;
    @BindView(R.id.rv_type)
    RecyclerView rvType;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.tv_all)
    AppCompatTextView tvAll;
    @BindView(R.id.rv_all)
    RecyclerView rvAll;
    private TurnoverVm turnoverVm;
    private DeviceVm deviceVm;
    private TurnoverHistoryAdapter turnoverHistoryAdapter;
    private TurnoverFirstAdapter turnoverFirstAdapter;
    private TurnoverSecondAdapter turnoverSecondAdapter;
    private List<TurnoverCategoryBean> turnoverHistoryList = new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_select_type;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        //判断周转材料还是设备
        int type = getIntent().getIntExtra(CustomConfig.ADMIN_MANAGE_TYPE,0);
        switch (type){
            case ADMIN_MANAGE_CONSTRUCTION://选择周转材料
            case ADMIN_MANAGE_CONSTRUCTION_ORDER://选择订单 周转材料
                getTurnoverData();
                break;
            case ADMIN_MANAGE_EQIPMENT://选择设备
            case ADMIN_MANAGE_EQIPMENT_ORDER://选择订单 设备
                getDevice();
                break;
        }

        //历史浏览
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rvHistory.setLayoutManager(gridLayoutManager);

        turnoverHistoryAdapter = new TurnoverHistoryAdapter(R.layout.adapter_turnover_history, null);
        rvHistory.setAdapter(turnoverHistoryAdapter);
        turnoverHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverHistoryAdapter.getData().get(position);
            switch (type){
                case ADMIN_MANAGE_CONSTRUCTION://选择周转材料
                    EventBus.getDefault().post(new TurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT://选择设备
                    EventBus.getDefault().post(new DeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_CONSTRUCTION_ORDER://选择订单 周转材料
                    EventBus.getDefault().post(new OrderTurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT_ORDER://选择订单 设备
                    EventBus.getDefault().post(new OrderDeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
            }
            finish();
        });

        //分类
        rvType.setLayoutManager(new GridLayoutManager(this, 4));
        turnoverFirstAdapter = new TurnoverFirstAdapter(R.layout.adapter_turnover_first, null);
        rvType.setAdapter(turnoverFirstAdapter);
        turnoverFirstAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverFirstAdapter.getData().get(position);
            switch (type){
                case ADMIN_MANAGE_CONSTRUCTION://选择周转材料
                case ADMIN_MANAGE_CONSTRUCTION_ORDER://选择订单 周转材料
                    turnoverVm.constructionCategoryList("",listBean.getId());
                    break;
                case ADMIN_MANAGE_EQIPMENT://选择设备
                case ADMIN_MANAGE_EQIPMENT_ORDER://选择订单 设备
                    deviceVm.equipmentCategoryList("",listBean.getId());
                    break;
            }
            tvAll.setText(listBean.getCatname());
        });

        //全部
        rvAll.setLayoutManager(new LinearLayoutManager(this));
        rvAll.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        turnoverSecondAdapter = new TurnoverSecondAdapter(R.layout.adapter_turnover_second, null);
        rvAll.setAdapter(turnoverSecondAdapter);
        turnoverSecondAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverSecondAdapter.getData().get(position);
            switch (type){
                case ADMIN_MANAGE_CONSTRUCTION://选择周转材料
                    EventBus.getDefault().post(new TurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT://选择设备
                    EventBus.getDefault().post(new DeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_CONSTRUCTION_ORDER://选择订单 周转材料
                    EventBus.getDefault().post(new OrderTurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT_ORDER://选择订单 设备
                    EventBus.getDefault().post(new OrderDeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
            }
            finish();
        });

        //键盘搜索键
        etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                KeyboardUtil.closeKeyboard(this);
                // 搜索，进行自己要的操作...
                switch (type){
                    case ADMIN_MANAGE_CONSTRUCTION://选择周转材料
                    case ADMIN_MANAGE_CONSTRUCTION_ORDER://选择订单 周转材料
                        turnoverVm.constructionCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                    case ADMIN_MANAGE_EQIPMENT://选择设备
                    case ADMIN_MANAGE_EQIPMENT_ORDER://选择订单 设备
                        deviceVm.equipmentCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                }
                return true;
            }
            return false;
        });

        //搜索框监听
        etSearch.addTextChangedListener(new TextWatcherWrapper(){
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0){
                    llHistory.setVisibility(View.GONE);
                    llType.setVisibility(View.GONE);
                    tvAll.setVisibility(View.GONE);
                }else {
                    if (turnoverHistoryList.size()!=0){
                        llHistory.setVisibility(View.VISIBLE);
                    }else {
                        llHistory.setVisibility(View.GONE);
                    }
                    llType.setVisibility(View.VISIBLE);
                    tvAll.setVisibility(View.VISIBLE);
                }
                switch (type){
                    case ADMIN_MANAGE_CONSTRUCTION://选择周转材料
                    case ADMIN_MANAGE_CONSTRUCTION_ORDER://选择订单 周转材料
                        turnoverVm.constructionCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                    case ADMIN_MANAGE_EQIPMENT://选择设备
                    case ADMIN_MANAGE_EQIPMENT_ORDER://选择订单 设备
                        deviceVm.equipmentCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                }
            }
        });
    }

    //周转材料名称
    private void getTurnoverData() {

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_no_turnover, 0, R.color.colorBgDark,null);

        tvBarTitle.setText("选择材料");
        tvHistory.setText("历史浏览材料");
        tvType.setText("材料分类");
        tvAll.setText("全部材料");
        etSearch.setHint("请输入材料名称");
        llType.setVisibility(View.VISIBLE);
        turnoverVm = getViewModel(TurnoverVm.class);
        turnoverVm.constructionCategoryHistory();
        turnoverVm.constructionCategoryParent();
        turnoverVm.constructionCategoryList("",0);

        //周转材料历史列表数据
        turnoverVm.turnoverHistoryData.observe(this,turnoverHistoryBeans -> {
            turnoverHistoryList = turnoverHistoryBeans;
            if (turnoverHistoryList.size()!=0){
                llHistory.setVisibility(View.VISIBLE);
                turnoverHistoryAdapter.setNewData(turnoverHistoryList);
            }else {
                llHistory.setVisibility(View.GONE);
            }
        });

        //周转材料名称一级列表数据
        turnoverVm.turnoverFirstData.observe(this,turnoverCategoryBeans -> {
            turnoverFirstAdapter.setNewData(turnoverCategoryBeans);
            turnoverFirstAdapter.setIsType("turnover");
        });

        //周转材料名称二级列表数据
        turnoverVm.turnoverSecondData.observe(this,turnoverCategoryBeans -> {
            turnoverSecondAdapter.setNewData(turnoverCategoryBeans);
            turnoverSecondAdapter.setKeyword(Objects.requireNonNull(etSearch.getText()).toString());
            turnoverSecondAdapter.setEmptyView(emptyView);
        });
    }

    //设备名称
    private void getDevice() {

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_no_device, 0, R.color.colorBgDark,null);

        tvBarTitle.setText("选择设备");
        tvHistory.setText("历史浏览设备");
        tvType.setText("设备分类");
        tvAll.setText("全部设备");
        etSearch.setHint("请输入设备名称");
        llType.setVisibility(View.VISIBLE);
        deviceVm = getViewModel(DeviceVm.class);
        deviceVm.equipmentCategoryHistory();
        deviceVm.equipmentCategoryParent();
        deviceVm.equipmentCategoryList("",0);

        //设备历史列表数据
        deviceVm.deviceHistoryData.observe(this,turnoverHistoryBeans -> {
            turnoverHistoryList = turnoverHistoryBeans;
            if (turnoverHistoryList.size()!=0){
                llHistory.setVisibility(View.VISIBLE);
                turnoverHistoryAdapter.setNewData(turnoverHistoryList);
            }else {
                llHistory.setVisibility(View.GONE);
            }
        });

        //设备名称一级列表数据
        deviceVm.deviceFirstData.observe(this,turnoverCategoryBeans -> {
            turnoverFirstAdapter.setNewData(turnoverCategoryBeans);
            turnoverFirstAdapter.setIsType("device");
        });

        //设备名称二级列表数据
        deviceVm.deviceSecondData.observe(this,turnoverCategoryBeans -> {
            turnoverSecondAdapter.setNewData(turnoverCategoryBeans);
            turnoverSecondAdapter.setKeyword(Objects.requireNonNull(etSearch.getText()).toString());
            turnoverSecondAdapter.setEmptyView(emptyView);
        });
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }
}
