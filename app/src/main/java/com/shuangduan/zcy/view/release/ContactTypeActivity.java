package com.shuangduan.zcy.view.release;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ContactTypeAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.ContactTypeBean;
import com.shuangduan.zcy.model.event.ContactTypeEvent;
import com.shuangduan.zcy.vm.ReleaseVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class 发布信息 联系人类型选择
 * @time 2019/7/31 8:39
 * @change
 * @chang time
 * @class describe
 */
public class ContactTypeActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private ReleaseVm releaseVm;
    private ContactTypeAdapter contactTypeAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_contact_type;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.contact_type));
        tvBarRight.setText(R.string.save);

        releaseVm = ViewModelProviders.of(this).get(ReleaseVm.class);
        releaseVm.contactTypeLiveData.observe(this, contactTypeBeans -> {
            if (contactTypeAdapter == null){
                rv.setLayoutManager(new LinearLayoutManager(this));
                rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
                contactTypeAdapter = new ContactTypeAdapter(R.layout.item_contact_type, contactTypeBeans);
                rv.setAdapter(contactTypeAdapter);
                contactTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
                    releaseVm.clickContactType(position);
                });
            }else {
                contactTypeAdapter.setNewData(contactTypeBeans);
            }
        });
        releaseVm.getContactType();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                List<ContactTypeBean> value = releaseVm.contactTypeLiveData.getValue();
                if (value != null){
                    for (ContactTypeBean bean : value) {
                        if (bean.getIsSelect() == 1){
                            EventBus.getDefault().post(new ContactTypeEvent(bean));
                            finish();
                        }
                    }
                }
                break;
        }
    }
}
