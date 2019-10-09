package com.shuangduan.zcy.rongyun.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.rongyun.view
 * @ClassName: ConversationListAdapterEx
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/9 14:42
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/9 14:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConversationListAdapterEx extends ConversationListAdapter {

    public ConversationListAdapterEx(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        return super.newView(context, position, group);
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
        if (data != null) {
            //修改群聊消息显示为红点
            if (data.getConversationType().equals(Conversation.ConversationType.DISCUSSION))
                data.setUnreadType(UIConversation.UnreadRemindType.REMIND_ONLY);
        }
        super.bindView(v, position, data);
    }
}
