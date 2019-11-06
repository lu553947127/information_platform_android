package com.shuangduan.zcy.adminManage.view.device;

import android.os.Bundle;

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
import com.shuangduan.zcy.adminManage.adapter.DeviceAdapter;
import com.shuangduan.zcy.adminManage.bean.DeviceBean;
import com.shuangduan.zcy.adminManage.vm.DeviceVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.MultiAreaVm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: DeviceChildrenFragment
 * @Description: 设备管理子公司列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/24 17:12
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/24 17:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceChildrenFragment extends BaseLazyFragment {

    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_grounding)
    AppCompatTextView tvGrounding;
    @BindView(R.id.tv_use_statue)
    AppCompatTextView tvUseStatueTop;
    @BindView(R.id.tv_depositing_place)
    AppCompatTextView tvDepositingPlace;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private DeviceVm deviceVm;
    private MultiAreaVm areaVm;
    private DeviceAdapter deviceAdapter;
    private List<DeviceBean.ListBean> deviceList = new ArrayList<>();

    public static DeviceChildrenFragment newInstance() {
        Bundle args = new Bundle();
        DeviceChildrenFragment fragment = new DeviceChildrenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_device_children;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("设备名称");

        deviceVm = ViewModelProviders.of(this).get(DeviceVm.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        deviceAdapter = new DeviceAdapter(R.layout.item_device, null,SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_EDIT,0)
                , SPUtils.getInstance().getInt(CustomConfig.CONSTRUCTION_DELETE,0),1);
        recyclerView.setAdapter(deviceAdapter);

        deviceVm.deviceLiveData.observe(this,deviceBean -> {
            deviceList=deviceBean.getList();
            if (deviceBean.getPage() == 1) {
                deviceAdapter.setNewData(deviceList);
                deviceAdapter.setEmptyView(R.layout.layout_empty_admin, recyclerView);
            } else {
                deviceAdapter.addData(deviceAdapter.getData().size(), deviceList);
            }
            setNoMore(deviceBean.getPage(), deviceBean.getCount());
        });

        //获取省市数据
        areaVm = ViewModelProviders.of(this).get(MultiAreaVm.class);

        //下拉刷新和上拉加载
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                deviceVm.equipmentList(2,areaVm.id,areaVm.city_id);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                deviceVm.equipmentListMore(2,areaVm.id,areaVm.city_id);
            }
        });

        deviceVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @Override
    protected void initDataFromService() {
        deviceVm.equipmentList(2,areaVm.id,areaVm.city_id);
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
}
