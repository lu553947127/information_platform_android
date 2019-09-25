package com.shuangduan.zcy.view.material;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.MaterialDepositingPlaceAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MaterialPlaceOrderBean;
import com.shuangduan.zcy.model.event.MaterialDetailEvent;
import com.shuangduan.zcy.vm.MaterialDetailVm;
import com.shuangduan.zcy.weight.DataHolder;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.material
 * @ClassName: DepositingPlaceActivity
 * @Description: 存放地选择列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/24 19:03
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/24 19:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("Registered")
public class DepositingPlaceActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MaterialDetailVm materialDetailVm;
    List<MaterialPlaceOrderBean> list=new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_depositing_place;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.material_depositing_place));
        materialDetailVm = ViewModelProviders.of(this).get(MaterialDetailVm.class);
        materialDetailVm.id = getIntent().getIntExtra(CustomConfig.MATERIAL_ID, 0);
        this.list= (List<MaterialPlaceOrderBean>) DataHolder.getInstance().getData("list");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        MaterialDepositingPlaceAdapter materialDepositingPlaceAdapter = new MaterialDepositingPlaceAdapter(R.layout.adapter_material_depositing_place, null);
        materialDepositingPlaceAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(materialDepositingPlaceAdapter);

        materialDetailVm.depositingPlaceBeanMutableLiveData.observe(this,materialDepositingPlaceBean -> {
                    if (materialDepositingPlaceBean!=null&&materialDepositingPlaceBean.size()>0){
                        if (list!=null){
                            for (int i = 0; i < list.size(); i++) {
                                String type_no = String.valueOf(list.get(i).getId());
                                for (int j = 0; j < materialDepositingPlaceBean.size(); j++) {
                                    if (String.valueOf(materialDepositingPlaceBean.get(j).getId()).equals(type_no)) {
                                        materialDepositingPlaceBean.remove(j);
                                    }
                                }
                            }
                        }
                        materialDepositingPlaceAdapter.setNewData(materialDepositingPlaceBean);
                    }else {
                        materialDepositingPlaceAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
                    }
                    materialDepositingPlaceAdapter.setOnItemClickListener((adapter, view, position) -> {
                        LogUtils.i(materialDepositingPlaceBean.get(position).getId());
                        EventBus.getDefault().post(new MaterialDetailEvent(materialDepositingPlaceBean.get(position).getId(),materialDepositingPlaceBean.get(position).getAddress()));
                        finish();
                    });
                }
        );
        materialDetailVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        materialDetailVm.getAddressList();
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){ finish(); }
}
