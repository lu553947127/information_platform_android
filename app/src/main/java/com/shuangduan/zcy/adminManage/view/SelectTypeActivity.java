package com.shuangduan.zcy.adminManage.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.TurnoverFirstAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverHistoryAdapter;
import com.shuangduan.zcy.adminManage.adapter.TurnoverSecondAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverHistoryBean;
import com.shuangduan.zcy.adminManage.event.TurnoverChildrenEvent;
import com.shuangduan.zcy.adminManage.event.TurnoverGroupEvent;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.shuangduan.zcy.weight.XEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;

import static com.shuangduan.zcy.app.CustomConfig.ADMIN_MANAGE_CONSTRUCTION;
import static com.shuangduan.zcy.app.CustomConfig.ADMIN_MANAGE_EQIPMENT;

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

        turnoverVm = ViewModelProviders.of(this).get(TurnoverVm.class);

        int type = getIntent().getIntExtra(CustomConfig.ADMIN_MANAGE_TYPE,0);
        int select_type= getIntent().getIntExtra(CustomConfig.SELECT_TYPE,0);
        if (type==ADMIN_MANAGE_CONSTRUCTION){
            tvBarTitle.setText("选择材料");
            tvHistory.setText("历史浏览材料");
            tvType.setText("材料分类");
            tvAll.setText("全部材料");
            llType.setVisibility(View.VISIBLE);
            turnoverVm.constructionCategoryHistory();
            turnoverVm.constructionCategoryParent();
            turnoverVm.constructionCategoryList("",0);
        }else if (type==ADMIN_MANAGE_EQIPMENT){
            tvBarTitle.setText("选择子设备");
            tvHistory.setText("历史浏览设备");
            tvType.setText("设备分类");
            tvAll.setText("全部设备");
            llType.setVisibility(View.VISIBLE);
        }

        rvHistory.setLayoutManager(new GridLayoutManager(this, 4));
        TurnoverHistoryAdapter turnoverHistoryAdapter = new TurnoverHistoryAdapter(R.layout.adapter_turnover_history, null);
        rvHistory.setAdapter(turnoverHistoryAdapter);
        turnoverHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverHistoryBean listBean = turnoverHistoryAdapter.getData().get(position);
            switch (select_type){
                case 1://周转材料集团
                    EventBus.getDefault().post(new TurnoverGroupEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case 2://周转材料子公司
                    EventBus.getDefault().post(new TurnoverChildrenEvent(listBean.getId(),listBean.getCatname()));
                    break;
            }
            finish();
        });

        //周转材料历史列表数据
        turnoverVm.turnoverHistoryData.observe(this,turnoverHistoryBeans -> {
            if (turnoverHistoryBeans.size()!=0){
                llHistory.setVisibility(View.VISIBLE);
                turnoverHistoryAdapter.setNewData(turnoverHistoryBeans);
            }else {
                llHistory.setVisibility(View.GONE);
            }
        });

        rvType.setLayoutManager(new GridLayoutManager(this, 4));
        TurnoverFirstAdapter turnoverFirstAdapter = new TurnoverFirstAdapter(R.layout.adapter_turnover_first, null);
        rvType.setAdapter(turnoverFirstAdapter);
        turnoverFirstAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverFirstAdapter.getData().get(position);
            turnoverVm.constructionCategoryList("",listBean.getId());
            tvAll.setText(listBean.getCatname());
        });

        turnoverVm.turnoverFirstData.observe(this,turnoverCategoryBeans -> {
            turnoverFirstAdapter.setNewData(turnoverCategoryBeans);
        });

        rvAll.setLayoutManager(new LinearLayoutManager(this));
        rvAll.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        TurnoverSecondAdapter turnoverSecondAdapter = new TurnoverSecondAdapter(R.layout.adapter_turnover_second, null);
        rvAll.setAdapter(turnoverSecondAdapter);
        turnoverSecondAdapter.setOnItemClickListener((adapter, view, position) -> {
            TurnoverCategoryBean listBean = turnoverSecondAdapter.getData().get(position);
            switch (select_type){
                case 1://周转材料集团
                    EventBus.getDefault().post(new TurnoverGroupEvent(listBean.getId(),listBean.getCatname()));
                    break;
                case 2://周转材料子公司
                    EventBus.getDefault().post(new TurnoverChildrenEvent(listBean.getId(),listBean.getCatname()));
                    break;
            }
            finish();
        });

        turnoverVm.turnoverSecondData.observe(this,turnoverCategoryBeans -> {
            if (turnoverCategoryBeans.size()!=0){
                turnoverSecondAdapter.setNewData(turnoverCategoryBeans);
                turnoverSecondAdapter.setKeyword(Objects.requireNonNull(etSearch.getText()).toString());
            }else {
                turnoverSecondAdapter.setEmptyView(R.layout.layout_empty_admin, rvAll);
            }
        });

        etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(etSearch.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(SelectTypeActivity.this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                turnoverVm.constructionCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
                return true;
            }
            return false;
        });
        etSearch.addTextChangedListener(new TextWatcherWrapper(){
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0){
                    llHistory.setVisibility(View.GONE);
                    llType.setVisibility(View.GONE);
                }else {
                    llHistory.setVisibility(View.VISIBLE);
                    llType.setVisibility(View.VISIBLE);
                }
                turnoverVm.constructionCategoryList(Objects.requireNonNull(etSearch.getText()).toString(),0);
            }
        });
    }
}
