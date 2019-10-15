package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.DEMAND_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_BUYER_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_RELATIONSHIP_TYPE;
import static com.shuangduan.zcy.app.CustomConfig.FIND_SUBSTANCE_TYPE;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.demand
 * @class describe  需求广场列表
 * @time 2019/8/16 17:13
 * @change
 * @chang time
 * @class describe
 */
public class DemandActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_demand;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        type = getIntent().getIntExtra(DEMAND_TYPE, FIND_RELATIONSHIP_TYPE);

        Fragment[] fragments = new Fragment[]{
                FindRelationshipFragment.newInstance(),
                FindSubstanceFragment.newInstance(),
                FindBuyerFragment.newInstance()
        };

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // 加载当前显示的Fragment
        transaction.replace(R.id.fl_content, fragments[type]);
        transaction.commit(); // 提交创建Fragment请求
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_release})
    void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_release:
                if (type == FIND_RELATIONSHIP_TYPE) {
                    bundle.putString("type", "0");
                    ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                } else if (type == FIND_SUBSTANCE_TYPE) {
                    bundle.putString("type", "1");
                    ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                } else if (type == FIND_BUYER_TYPE) {
                    bundle.putString("type", "2");
                    ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
                }
                break;
        }
    }
}
