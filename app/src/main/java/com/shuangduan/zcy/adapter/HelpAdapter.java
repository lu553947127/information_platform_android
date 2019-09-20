package com.shuangduan.zcy.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.HelpBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: HelpAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/20 14:50
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/20 14:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HelpAdapter extends BaseQuickAdapter<HelpBean.DataBean.ListBean, BaseViewHolder> {

    private Context context;

    public HelpAdapter(Context context,int layoutResId,@Nullable List<HelpBean.DataBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_ask, item.getTitle());
        TextView tvContent = helper.getView(R.id.tv_answer);
        tvContent.setText(Html.fromHtml(item.getContent(), null, null));
    }

}
