package com.shuangduan.zcy.view.release;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.TypeBean;
import com.shuangduan.zcy.model.event.TypesArrayEvent;
import com.shuangduan.zcy.vm.TypesVm;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  发布类型选择
 * @time 2019/7/30 14:02
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseTypeSelectActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_province)
    RecyclerView rvProvince;
    @BindView(R.id.rv_city)
    RecyclerView rvCity;
    private TypesVm typesVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_release_area_select;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.project_types));
        tvBarRight.setText(getString(R.string.save));

        rvCity.setVisibility(View.GONE);
        typesVm = ViewModelProviders.of(this).get(TypesVm.class);
        rvProvince.setLayoutManager(new LinearLayoutManager(this));
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        SelectorFirstAdapter typeFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);

        rvProvince.setAdapter(typeFirstAdapter);

        //类型点击事件
        typeFirstAdapter.setOnItemClickListener((adapter, view1, position) -> typesVm.clickFirst(position));


        typesVm.init();
        typesVm.typeFirstLiveData.observe(this, typeBeans -> {
            LogUtils.i(typeBeans.get(0).getCatname());
            typeFirstAdapter.setNewData(typeBeans);
//            typesVm.setTypeFirstSingleInit();
        });

        typesVm.pageStateLiveData.observe(this, s -> {
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

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                String province = "";

                List<Integer> typeId = new ArrayList<>();
                List<TypeBean> types = typesVm.typeFirstLiveData.getValue();

                if (types == null || types.size() <= 0) {
                    ToastUtils.showShort(getString(R.string.select_types_correct));
                    return;
                }

                for (int i = 0; i < types.size(); i++) {
                    if (types.get(i).isSelect == 1) {
                        province += types.get(i).getCatname() + " ";
                        typeId.add(types.get(i).getId());
                    }
                }

                EventBus.getDefault().post(new TypesArrayEvent(province, typeId));
                finish();

                break;
        }
    }
}
