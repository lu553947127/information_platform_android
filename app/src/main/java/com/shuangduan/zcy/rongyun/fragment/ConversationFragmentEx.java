package com.shuangduan.zcy.rongyun.fragment;

import android.content.Context;

import com.shuangduan.zcy.rongyun.adapter.MessageListAdapterEx;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.adapter.MessageListAdapter;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.rongyun.view
 * @ClassName: ConversationFragmentEx
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/16 10:12
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/16 10:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConversationFragmentEx extends ConversationFragment {

    @Override
    public MessageListAdapter onResolveAdapter(Context context) {
        return new MessageListAdapterEx(context);
    }
}
