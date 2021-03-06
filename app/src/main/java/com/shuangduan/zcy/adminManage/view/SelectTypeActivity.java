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
 * @Description: ???????????????/????????????/?????? ??????
 * @Author: ?????????
 * @CreateDate: 2019/10/29 14:45
 * @UpdateUser: ?????????
 * @UpdateDate: 2019/10/29 14:45
 * @UpdateRemark: ????????????
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

        //??????????????????????????????
        int type = getIntent().getIntExtra(CustomConfig.ADMIN_MANAGE_TYPE,0);
        switch (type){
            case ADMIN_MANAGE_CONSTRUCTION://??????????????????
            case ADMIN_MANAGE_CONSTRUCTION_ORDER://???????????? ????????????
                getTurnoverData();
                break;
            case ADMIN_MANAGE_EQIPMENT://????????????
            case ADMIN_MANAGE_EQIPMENT_ORDER://???????????? ??????
                getDevice();
                break;
        }

        //????????????
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rvHistory.setLayoutManager(gridLayoutManager);

        turnoverHistoryAdapter = new TurnoverHistoryAdapter(R.layout.adapter_turnover_history, null);
        rvHistory.setAdapter(turnoverHistoryAdapter);
        turnoverHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverHistoryAdapter.getData().get(position);
            switch (type){
                case ADMIN_MANAGE_CONSTRUCTION://??????????????????
                    EventBus.getDefault().post(new TurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT://????????????
                    EventBus.getDefault().post(new DeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_CONSTRUCTION_ORDER://???????????? ????????????
                    EventBus.getDefault().post(new OrderTurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT_ORDER://???????????? ??????
                    EventBus.getDefault().post(new OrderDeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
            }
            finish();
        });

        //??????
        rvType.setLayoutManager(new GridLayoutManager(this, 4));
        turnoverFirstAdapter = new TurnoverFirstAdapter(R.layout.adapter_turnover_first, null);
        rvType.setAdapter(turnoverFirstAdapter);
        turnoverFirstAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverFirstAdapter.getData().get(position);
            switch (type){
                case ADMIN_MANAGE_CONSTRUCTION://??????????????????
                case ADMIN_MANAGE_CONSTRUCTION_ORDER://???????????? ????????????
                    turnoverVm.constructionCategoryList("",listBean.getId());
                    break;
                case ADMIN_MANAGE_EQIPMENT://????????????
                case ADMIN_MANAGE_EQIPMENT_ORDER://???????????? ??????
                    deviceVm.equipmentCategoryList("",listBean.getId());
                    break;
            }
            tvAll.setText(listBean.getCatname());
        });

        //??????
        rvAll.setLayoutManager(new LinearLayoutManager(this));
        rvAll.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        turnoverSecondAdapter = new TurnoverSecondAdapter(R.layout.adapter_turnover_second, null);
        rvAll.setAdapter(turnoverSecondAdapter);
        turnoverSecondAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverSecondAdapter.getData().get(position);
            switch (type){
                case ADMIN_MANAGE_CONSTRUCTION://??????????????????
                    EventBus.getDefault().post(new TurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT://????????????
                    EventBus.getDefault().post(new DeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_CONSTRUCTION_ORDER://???????????? ????????????
                    EventBus.getDefault().post(new OrderTurnoverEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case ADMIN_MANAGE_EQIPMENT_ORDER://???????????? ??????
                    EventBus.getDefault().post(new OrderDeviceEvent(listBean.getId(),listBean.getCatname()));
                    break;
            }
            finish();
        });

        //???????????????
        etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // ???????????????
                KeyboardUtil.closeKeyboard(this);
                // ?????????????????????????????????...
                switch (type){
                    case ADMIN_MANAGE_CONSTRUCTION://??????????????????
                    case ADMIN_MANAGE_CONSTRUCTION_ORDER://???????????? ????????????
                        turnoverVm.constructionCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                    case ADMIN_MANAGE_EQIPMENT://????????????
                    case ADMIN_MANAGE_EQIPMENT_ORDER://???????????? ??????
                        deviceVm.equipmentCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                }
                return true;
            }
            return false;
        });

        //???????????????
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
                    case ADMIN_MANAGE_CONSTRUCTION://??????????????????
                    case ADMIN_MANAGE_CONSTRUCTION_ORDER://???????????? ????????????
                        turnoverVm.constructionCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                    case ADMIN_MANAGE_EQIPMENT://????????????
                    case ADMIN_MANAGE_EQIPMENT_ORDER://???????????? ??????
                        deviceVm.equipmentCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                        break;
                }
            }
        });
    }

    //??????????????????
    private void getTurnoverData() {

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_no_turnover, 0, R.color.colorBgDark,null);

        tvBarTitle.setText("????????????");
        tvHistory.setText("??????????????????");
        tvType.setText("????????????");
        tvAll.setText("????????????");
        etSearch.setHint("?????????????????????");
        llType.setVisibility(View.VISIBLE);
        turnoverVm = getViewModel(TurnoverVm.class);
        turnoverVm.constructionCategoryHistory();
        turnoverVm.constructionCategoryParent();
        turnoverVm.constructionCategoryList("",0);

        //??????????????????????????????
        turnoverVm.turnoverHistoryData.observe(this,turnoverHistoryBeans -> {
            turnoverHistoryList = turnoverHistoryBeans;
            if (turnoverHistoryList.size()!=0){
                llHistory.setVisibility(View.VISIBLE);
                turnoverHistoryAdapter.setNewData(turnoverHistoryList);
            }else {
                llHistory.setVisibility(View.GONE);
            }
        });

        //????????????????????????????????????
        turnoverVm.turnoverFirstData.observe(this,turnoverCategoryBeans -> {
            turnoverFirstAdapter.setNewData(turnoverCategoryBeans);
            turnoverFirstAdapter.setIsType("turnover");
        });

        //????????????????????????????????????
        turnoverVm.turnoverSecondData.observe(this,turnoverCategoryBeans -> {
            turnoverSecondAdapter.setNewData(turnoverCategoryBeans);
            turnoverSecondAdapter.setKeyword(Objects.requireNonNull(etSearch.getText()).toString());
            turnoverSecondAdapter.setEmptyView(emptyView);
        });
    }

    //????????????
    private void getDevice() {

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_no_device, 0, R.color.colorBgDark,null);

        tvBarTitle.setText("????????????");
        tvHistory.setText("??????????????????");
        tvType.setText("????????????");
        tvAll.setText("????????????");
        etSearch.setHint("?????????????????????");
        llType.setVisibility(View.VISIBLE);
        deviceVm = getViewModel(DeviceVm.class);
        deviceVm.equipmentCategoryHistory();
        deviceVm.equipmentCategoryParent();
        deviceVm.equipmentCategoryList("",0);

        //????????????????????????
        deviceVm.deviceHistoryData.observe(this,turnoverHistoryBeans -> {
            turnoverHistoryList = turnoverHistoryBeans;
            if (turnoverHistoryList.size()!=0){
                llHistory.setVisibility(View.VISIBLE);
                turnoverHistoryAdapter.setNewData(turnoverHistoryList);
            }else {
                llHistory.setVisibility(View.GONE);
            }
        });

        //??????????????????????????????
        deviceVm.deviceFirstData.observe(this,turnoverCategoryBeans -> {
            turnoverFirstAdapter.setNewData(turnoverCategoryBeans);
            turnoverFirstAdapter.setIsType("device");
        });

        //??????????????????????????????
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
