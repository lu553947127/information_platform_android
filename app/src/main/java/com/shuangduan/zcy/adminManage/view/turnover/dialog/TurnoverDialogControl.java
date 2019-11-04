package com.shuangduan.zcy.adminManage.view.turnover.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.vm.TurnoverAddVm;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.weight.datepicker.DatePickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.view.turnover.dialog$
 * @class TurnoverDialogControl$
 * @class describe
 * @time 2019/11/4 10:53
 * @change
 * @class 周转材料添加详情Dialog 控制器
 */
public class TurnoverDialogControl {

    private final BottomSheetDialogs dialog;
    private final View view;
    private final BaseActivity context;

    //项目名称
    private List<String> projectList;
    //项目ID
    private List<Integer> projectIdList;

    public TurnoverDialogControl(BaseActivity context, TurnoverAddVm vm) {
        this.context = context;
        //底部滑动对话框
        dialog = new BottomSheetDialogs(context);
        //设置自定view
        view = LayoutInflater.from(context).inflate(R.layout.dialog_add_turnover, null);
        //把布局添加进去
        dialog.setContentView(view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(vm);
    }

    private void initData(TurnoverAddVm vm) {
        projectList = new ArrayList<>();
        projectIdList = new ArrayList<>();
        vm.turnoverName.observe(context, turnoverName -> {
            for (TurnoverNameBean item : turnoverName) {
                projectList.add(item.name);
                projectIdList.add(item.id);
            }
        });

        vm.projectListData();
    }


    public void initView(int titleRes) {
        TextView tvTitle = view.findViewById(R.id.tv_dialog_item_title);

    }

    public void show() {
        dialog.show();
    }


}
