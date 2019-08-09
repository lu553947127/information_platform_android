package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.StageBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/9 11:12
 * @change
 * @chang time
 * @class describe
 */
public class MultiStageVm extends BaseViewModel {
    public MutableLiveData<List<StageBean>> stageLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public boolean inited = false;

    public MultiStageVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        stageLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getStage(){
        new ProjectRepository().projectStage(stageLiveData, pageStateLiveData, userId);
    }

    /**
     * 设置一级默认数据
     */
    public void setStageInit() {
        if (!inited) {
            inited = true;
            List<StageBean> list = stageLiveData.getValue();
            if (list == null) return;
            for (StageBean bean : list) {
                //默认全选
                bean.setIsSelect(1);
            }
            StageBean stageBean = new StageBean();
            stageBean.setPhases_name("全部");
            stageBean.setId(0);
            stageBean.setIsSelect(1);
            stageBean.setIsCheck(1);
            list.add(0, stageBean);

            stageLiveData.postValue(list);
        }
    }

    /**
     * 点击一级分类
     */
    public void clickFirst(int i){
        List<StageBean> data = stageLiveData.getValue();
        if (i == 0) {
            //选全部
            if (data.get(0).getIsSelect() == 1){
                //全部选中，修改全部为非选中状态
                for (int j = 0; j < data.size(); j++) {
                    data.get(j).setIsSelect(0);
                }
            }else {
                //非全部选中，修改全部为选中状态
                for (int j = 0; j < data.size(); j++) {
                    data.get(j).setIsSelect(1);
                }
            }
        }else {
            //单一选项
            data.get(i).setIsSelect(data.get(i).getIsSelect() == 1?0:1);
            if (data.get(i).getIsSelect() != 1){
                //未选中状态，之前就是选中状态，需判断是否为全部选中，是则修改全部为非选中状态
                if (data.get(0).getIsSelect() == 1){
                    //只有“全部”选项是选中状态才更新状态
                    data.get(0).setIsSelect(0);
                }
            }else {
                //选中状态，之前就是未选中状态，需判断是否为全部选中，是则修改全部为选中状态
                //初始把“全部”更新为选中
                data.get(0).setIsSelect(1);
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).getIsSelect() != 1){
                        //循环查询，出现未选中就更新第一个选项“全部”的状态
                        data.get(0).setIsSelect(0);
                        break;
                    }
                }
            }
        }

        stageLiveData.postValue(data);
    }
}
