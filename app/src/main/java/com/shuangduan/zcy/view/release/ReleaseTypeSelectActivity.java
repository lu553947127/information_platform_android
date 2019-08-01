package com.shuangduan.zcy.view.release;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;
import com.shuangduan.zcy.adapter.SelectorSecondAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.BaseSelectorBean;
import com.shuangduan.zcy.model.bean.TypeBean;
import com.shuangduan.zcy.model.event.TypesEvent;
import com.shuangduan.zcy.vm.TypesVm;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.project_stage));
        tvBarRight.setText(getString(R.string.save));

        typesVm = ViewModelProviders.of(this).get(TypesVm.class);
        rvProvince.setLayoutManager(new LinearLayoutManager(this));
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        SelectorFirstAdapter typeFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
        SelectorSecondAdapter typeSecondAdapter = new SelectorSecondAdapter(R.layout.item_city, null);
        rvProvince.setAdapter(typeFirstAdapter);
        rvCity.setAdapter(typeSecondAdapter);
        //类型点击事件
        typeFirstAdapter.setOnItemClickListener((adapter, view1, position) -> typesVm.clickFirst(position));
        typeSecondAdapter.setOnItemClickListener((adapter, view12, position) -> typesVm.clickSecondSingle(position));

        typesVm.init();
        typesVm.typeFirstLiveData.observe(this, typeBeans -> {
            LogUtils.i(typeBeans.get(0).getCatname());
            typeFirstAdapter.setNewData(typeBeans);
            typesVm.setTypeFirstSingleInit();
        });
        typesVm.typeSecondLiveData.observe(this, typeBeans1 -> {
            //刷新二级列表
            typeSecondAdapter.setNewData(typeBeans1);
        });

        typesVm.pageStateLiveData.observe(this, s -> {
            switch (s){
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
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                String province = "";
                String city = "";
                int cityId = 0;
                List<TypeBean> types = typesVm.typeFirstLiveData.getValue();
                assert types != null;
                for (TypeBean bean : types) {
                    if (bean.isSelect == 1){
                        province = bean.getCatname();
                        List<TypeBean> citys = typesVm.typeSecondLiveData.getValue();
                        assert citys != null;
                        for (int i = 0; i < citys.size(); i++) {
                            if (citys.get(i).getIsSelect() == 1) {
                                city = citys.get(i).getCatname();
                                cityId = citys.get(i).getId();
                                EventBus.getDefault().post(new TypesEvent(province + city, cityId));
                                finish();
                                return;
                            }
                        }
                        break;
                    }
                }
                ToastUtils.showShort(getString(R.string.select_types_correct));
                break;
        }
    }
}
