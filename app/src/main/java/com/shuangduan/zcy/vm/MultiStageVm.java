package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.StageBean;

import java.util.ArrayList;
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

    private int prePosition;

    public MultiStageVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        stageLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getStage() {
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
    public void clickFirst(int i) {
        List<StageBean> data = stageLiveData.getValue();

        if (i == 0) {
            //选全部
            if (data.get(0).getIsSelect() == 1) {
                //全部选中，修改全部为非选中状态
                for (int j = 0; j < data.size(); j++) {
                    data.get(j).setIsSelect(0);
                }
            } else {
                //非全部选中，修改全部为选中状态
                for (int j = 0; j < data.size(); j++) {
                    data.get(j).setIsSelect(1);
                }
            }
        } else {

            if (prePosition != 0) {
                data.get(prePosition).setIsSelect(0);
            }

            //单一选项
            for (int j = 0; j < data.size(); j++) {
                if (i == j) {
                    data.get(i).setIsSelect(1);
                    continue;
                }
                data.get(j).setIsSelect(0);
            }

        }
        if (i != 0) {
            prePosition = i;
        }

        stageLiveData.postValue(data);
    }

    public List<Integer> getStageId() {
        List<StageBean> value = stageLiveData.getValue();
        if (value == null || value.size() == 0 || value.get(0).getIsSelect() == 1) {
            return null;
        }
        List<Integer> stageId = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            if (value.get(i).getIsSelect() == 1) {
                stageId.add(value.get(i).getId());
            }
        }
        return stageId.size() != 0 ? stageId : null;
    }
}
