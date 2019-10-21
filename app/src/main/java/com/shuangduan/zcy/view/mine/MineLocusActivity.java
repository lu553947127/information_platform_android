package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MineLocusAcitivty$
 * @class describe
 * @time 2019/10/20 15:08
 * @change
 * @class describe
 */
public class MineLocusActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_visit)
    TextView tvVisit;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_locus;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }
}
