package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.IMAddVm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  融云加好友
 * @time 2019/8/29 16:17
 * @change
 * @chang time
 * @class describe
 */
public class IMAddFriendActivity extends BaseActivity {
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.edt_msg)
    EditText edtMsg;
    private IMAddVm imAddVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_add_friend;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
        tvBarTitle.setText(getString(R.string.add_friends));

        IMFriendSearchBean.ListBean listBean = getIntent().getParcelableExtra(CustomConfig.FRIEND_DATA);
        if (listBean != null){
            tvName.setText(listBean.getUsername());
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(listBean.getImage())
                    .imageView(ivHeader)
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .build());
        }

        imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);
        assert listBean != null;
        imAddVm.receiverId = listBean.getId();
        imAddVm.applyOperateLiveData.observe(this, imFriendApplyOperationBean -> {

        });
        imAddVm.pageStateLiveData.observe(this, this::showPageState);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                imAddVm.apply(edtMsg.getText().toString());
                break;
        }
    }
}
