package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.LocusAdapter;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.bean.LocusBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  轨迹
 * @time 2019/7/15 15:32
 * @change
 * @chang time
 * @class describe
 */
public class ProjectLocusFragment extends BaseFragment {
    @BindView(R.id.tv_filter)
    TextView tvFilter;
    @BindView(R.id.rv_locus)
    RecyclerView rvLocus;

    public static ProjectLocusFragment newInstance() {

        Bundle args = new Bundle();

        ProjectLocusFragment fragment = new ProjectLocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_locus;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvFilter.setText(getString(R.string.release_by_me));

        List<LocusBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new LocusBean());
        }
        rvLocus.setLayoutManager(new LinearLayoutManager(mContext));
        LocusAdapter locusAdapter = new LocusAdapter(R.layout.item_locus, list){
            @Override
            public void readDetail() {
                new CustomDialog(mActivity)
                        .setTip("查看此消息请支付1亿元")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {

                            }
                        })
                        .showDialog();
            }
        };
        rvLocus.setAdapter(locusAdapter);

    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_filter})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                break;
        }
    }
}
