package com.shuangduan.zcy.view.adminManage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.base.BaseLazyFragment;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: TurnoverGroupFragment
 * @Description: 周转材料集团列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 15:01
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 15:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverGroupFragment extends BaseLazyFragment {

    @BindView(R.id.tv_name)
    TextView tvName;

    public static TurnoverGroupFragment newInstance() {
        Bundle args = new Bundle();
        TurnoverGroupFragment fragment = new TurnoverGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_turnover_group;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("材料名称");
    }

    @Override
    protected void initDataFromService() {

    }
}
