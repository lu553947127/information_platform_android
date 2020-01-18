package com.shuangduan.zcy.view.mine.set;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ReceivingAddressAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.vm.AddressVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
public class ReceivingAddressActivity extends BaseActivity implements OnItemMenuClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_recycler)
    SwipeRecyclerView swipeRecycler;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
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
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setWidth(DensityUtil.dp2px(80));// 宽度。
        rightMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

        SwipeMenuItem deleteItem = new SwipeMenuItem(this)
                .setBackgroundColorResource(R.color.color_FF0000)
                .setText("删除") // 文字。
                .setTextColor(Color.WHITE) // 文字颜色。
                .setTextSize(14) // 文字大小。
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setWidth(DensityUtil.dp2px(80));
        rightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.
    };
    private ReceivingAddressAdapter mAdapter;
    private SwipeMenuBridge menuBridge;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_receiving_address;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.user_receiving_address);

        int type = getIntent().getIntExtra("type", 0);

        emptyView();

        vm = ViewModelProviders.of(this).get(AddressVm.class);

        swipeRecycler.setLayoutManager(new LinearLayoutManager(this));
        swipeRecycler.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this), DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        swipeRecycler.setSwipeMenuCreator(creator);
        swipeRecycler.setOnItemMenuClickListener(this);

        mAdapter = new ReceivingAddressAdapter(R.layout.adapter_receiving_address_item, null);
        mAdapter.setOnItemChildClickListener(this);
        swipeRecycler.setAdapter(mAdapter);

        if (type != 0) {
            mAdapter.setOnItemClickListener(this);
        }


        vm.addressLiveData.observe(this, item -> {
            if (item.list == null || item.list.size() == 0) {
                flEmpty.setVisibility(View.VISIBLE);
                flContent.setVisibility(View.INVISIBLE);
                return;
            }

            if (flEmpty.getVisibility() == View.VISIBLE) {
                flEmpty.setVisibility(View.INVISIBLE);
                flContent.setVisibility(View.VISIBLE);
            }
            mAdapter.setNewData(item.list);
        });

        vm.defaultLiveData.observe(this, item -> {
            menuBridge.closeMenu();
            vm.addressList();
        });

        vm.deleteLiveData.observe(this, item -> {
            menuBridge.closeMenu();
            mAdapter.remove(vm.position);
            if (mAdapter.getData().size() == 0) {
                flEmpty.setVisibility(View.VISIBLE);
                flContent.setVisibility(View.INVISIBLE);
            }
        });

        vm.addressList();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_goto, R.id.tv_new})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_new:
            case R.id.tv_goto://新建收货地址
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                ActivityUtils.startActivity(EditReceivingAddressActivity.class, bundle);
                break;
        }
    }

    //设置空页面
    private void emptyView() {
        ivIcon.setImageResource(R.drawable.icon_address_empty);
        tvTip.setText(R.string.address_empty_hint);
        tvGoto.setText(R.string.go_setting);
        tvGoto.setVisibility(View.VISIBLE);
    }

    //侧滑监听
    @Override
    public void onItemClick(SwipeMenuBridge menuBridge, int position) {
        int id = mAdapter.getItem(position).id;
        this.menuBridge = menuBridge;
        switch (menuBridge.getPosition()) {
            case 0://设为默认地址
                vm.setDefaultState(id);
                break;
            case 1://删除
                new CustomDialog(this)
                        .setTip(getString(R.string.delete_address))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                vm.position = position;
                                vm.deleteAddress(id);
                            }
                        }).showDialog();
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_edit:
                vm.position = position;
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putParcelable("address", mAdapter.getItem(position));
                ActivityUtils.startActivity(bundle, EditReceivingAddressActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EventBus.getDefault().post(mAdapter.getItem(position));
        finish();
    }

    @Subscribe
    public void updateAddress(MutableLiveData data) {
        vm.addressList();
    }

    //更新删除后的地址
    @Subscribe
    public void deleteAddress(ReceivingAddressBean.Address address) {
        mAdapter.remove(vm.position);
        if (mAdapter.getData().size() == 0) {
            flEmpty.setVisibility(View.VISIBLE);
            flContent.setVisibility(View.INVISIBLE);
        }
    }
}
