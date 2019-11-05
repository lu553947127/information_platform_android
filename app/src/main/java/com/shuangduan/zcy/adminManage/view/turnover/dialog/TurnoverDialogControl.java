package com.shuangduan.zcy.adminManage.view.turnover.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.contrarywind.view.WheelView;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ArrayWheelAdapter;
import com.shuangduan.zcy.adminManage.bean.TurnoverNameBean;
import com.shuangduan.zcy.adminManage.bean.TurnoverTypeBean;
import com.shuangduan.zcy.adminManage.vm.TurnoverAddVm;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BottomSheetDialogs;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.weight.TurnoverSelectView;
import com.shuangduan.zcy.weight.WheelSlideView;

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


    //使用计划ID
    private List<Integer> planIdList;

    //使用计划名称
    private List<String> planList;

    private TurnoverSelectView tsProject;
    private TurnoverSelectView tsPlan;
    private TurnoverSelectView tsNum;
    private TurnoverSelectView tsStartTime;
    private TurnoverSelectView tsEnterTime;
    private TurnoverSelectView tsExitTime;
    private TurnoverSelectView tsAmortize;
    private TurnoverSelectView tsOriginal;
    private TurnoverSelectView tsValue;
    private TextView tvTitle;
    private WheelSlideView wheelView;

    public TurnoverDialogControl(BaseActivity context, TurnoverAddVm addVm) {
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
        initData(addVm);

    }

    private void initData(TurnoverAddVm addVm) {
        projectList = new ArrayList<>();
        projectIdList = new ArrayList<>();

        planIdList = new ArrayList<>();
        planList = new ArrayList<>();


        addVm.turnoverName.observe(context, turnoverName -> {
            for (TurnoverNameBean item : turnoverName) {
                projectList.add(item.name);
                projectIdList.add(item.id);
            }
        });

        addVm.projectListData();
    }


    public void initView() {
        LinearLayout llselector = view.findViewById(R.id.ll_selector);
        RelativeLayout llEdit = view.findViewById(R.id.ll_edit);

        tsProject = view.findViewById(R.id.ts_project);
        tsPlan = view.findViewById(R.id.ts_plan);
        tsNum = view.findViewById(R.id.ts_num);
        tsStartTime = view.findViewById(R.id.ts_start_time);
        tsEnterTime = view.findViewById(R.id.ts_enter_time);
        tsExitTime = view.findViewById(R.id.ts_exit_time);
        tsAmortize = view.findViewById(R.id.ts_amortize);
        tsOriginal = view.findViewById(R.id.ts_original);
        tsValue = view.findViewById(R.id.tv_value);

        tvTitle = view.findViewById(R.id.tv_dialog_item_title);
        wheelView = view.findViewById(R.id.wheel_view);

        wheelView.setDividerColor(0x000000);
        wheelView.setItemsVisibleCount(7);
        wheelView.setCurrentItem(1);
        wheelView.setCyclic(false);
        wheelView.setLineSpacingMultiplier(2.2f);

    }

    public void show(int position, int titleRes) {
        switch (position) {
            case 0:
                wheelView.setAdapter(new ArrayWheelAdapter(projectList));
                break;
            case 1:
                tsPlan.showLine(true);
                wheelView.setAdapter(new ArrayWheelAdapter(planList));
                break;
            case 2:
                break;
        }
        showView(position);
        tvTitle.setText(titleRes);
        dialog.show();
    }


    private void showView(int position) {
        tsProject.showLine(position == 0);
        tsPlan.showLine(position == 1);
        tsNum.showLine(position == 2);
        tsStartTime.showLine(position == 3);
        tsEnterTime.showLine(position == 4);
        tsExitTime.showLine(position == 5);
        tsAmortize.showLine(position == 6);
        tsOriginal.showLine(position == 7);
        tsValue.showLine(position == 8);
    }

    public List<Integer> getPlanIdList() {
        return planIdList;
    }

    public List<String> getPlanList() {
        return planList;
    }
}
