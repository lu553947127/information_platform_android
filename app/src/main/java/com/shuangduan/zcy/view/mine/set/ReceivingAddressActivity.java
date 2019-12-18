package com.shuangduan.zcy.view.mine.set;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.MaterialPlaceOrderAdapter;
import com.shuangduan.zcy.adapter.ReceivingAddressAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.vm.AddressVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: ReceivingAddressActivity
 * @Description: 收货地址页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/15 15:14
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/15 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ReceivingAddressActivity extends BaseActivity implements OnItemMenuClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_recycler)
    SwipeRecyclerView swipeRecycler;

    @BindView(R.id.fl_empty)
    FrameLayout flEmpty;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_goto)
    TextView tvGoto;


    private AddressVm vm;

    //创建侧滑菜单
    private SwipeMenuCreator creator = (leftMenu, rightMenu, position) -> {
        SwipeMenuItem addItem = new SwipeMenuItem(this)
                .setBackgroundColorResource(R.color.color_CACACA)
                .setText("设为默认") // 文字。
                .setTextColor(Color.WHITE) // 文字颜色。
                .setTextSize(14) // 文字大小。
                .setHeight(DensityUtil.dp2px(110))
                .setWidth(DensityUtil.dp2px(80));// 宽度。
        rightMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

        SwipeMenuItem deleteItem = new SwipeMenuItem(this)
                .setBackgroundColorResource(R.color.color_FF0000)
                .setText("删除") // 文字。
                .setTextColor(Color.WHITE) // 文字颜色。
                .setTextSize(14) // 文字大小。
                .setHeight(DensityUtil.dp2px(110))
                .setWidth(DensityUtil.dp2px(80));
        rightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.
    };
    private ReceivingAddressAdapter adapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_receiving_address;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.user_receiving_address);

        vm = ViewModelProviders.of(this).get(AddressVm.class);

        swipeRecycler.setLayoutManager(new LinearLayoutManager(this));
        swipeRecycler.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this), DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        swipeRecycler.setSwipeMenuCreator(creator);
        swipeRecycler.setOnItemMenuClickListener(this);

        adapter = new ReceivingAddressAdapter(R.layout.adapter_receiving_address_item, null);
        adapter.setOnItemChildClickListener(this);
        swipeRecycler.setAdapter(adapter);

        ivIcon.setImageResource(R.drawable.icon_address_empty);
        tvTip.setText(R.string.address_empty_hint);
        tvGoto.setText(R.string.go_setting);
        tvGoto.setVisibility(View.VISIBLE);
        flEmpty.setVisibility(View.VISIBLE);

        vm.addressLiveData.observe(this, item -> {
            adapter.setNewData(item.list);
        });

        vm.defaultLiveData.observe(this, item -> {
            adapter.getItem(vm.position).state = 1;
            adapter.notifyItemChanged(vm.position, adapter.getItem(vm.position));
        });

        vm.deleteLiveData.observe(this, item -> {
            adapter.remove(vm.position);
        });

//        vm.addressList();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_goto})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_goto:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                ActivityUtils.startActivity(EditReceivingAddressActivity.class, bundle);
                break;
        }
    }


    @Override
    public void onItemClick(SwipeMenuBridge menuBridge, int position) {
        switch (menuBridge.getPosition()) {
//            case 0:
//                vm.setDefaultState();
//                break;
//            case 1:
//                vm.deleteAddress();
//                break;
        }
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_edit:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                ActivityUtils.startActivity(bundle, EditReceivingAddressActivity.class);
                break;
        }
    }
}
