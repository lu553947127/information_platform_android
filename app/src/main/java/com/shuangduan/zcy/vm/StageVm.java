package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

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
 * @time 2019/7/19 15:40
 * @change
 * @chang time
 * @class describe
 */
public class StageVm extends BaseViewModel {

    public MutableLiveData<List<StageBean>> stageFirstLiveData;
    public MutableLiveData<List<StageBean>> stageSecondLiveData;
    private int userId;
    public int id;//分类id  0为一级

    /**
     * 初始化
     */
    public void init(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        id = 0;
        projectTypes();
    }

    public void projectTypes(){
        ProjectRepository projectRepository = new ProjectRepository();
        if (id == 0){//一级分类请求
            stageFirstLiveData = projectRepository.projectStage(userId);
            List<StageBean> stageList = stageFirstLiveData.getValue();
            //初始化数据默认选中第一个
            if (stageList != null) {
                stageList.get(0).setIsSelect(1);
                stageFirstLiveData.postValue(stageList);
            }
        }else {//二级分类请求
            stageSecondLiveData = projectRepository.projectStage(userId);
            List<StageBean> stageList = stageSecondLiveData.getValue();
            if (stageList != null){
                //没有添加全部选项的添加
                if (!stageList.get(0).getCatname().equals("全部")){
                    StageBean stageBean = new StageBean();
                    stageBean.setCatname("全部");
                    stageBean.setId(0);
                    stageBean.setIsSelect(0);
                    stageList.add(0, stageBean);
                }
                stageSecondLiveData.postValue(stageList);
            }
        }
    }

    /**
     * 点击一级分类
     */
    public void clickFirst(int position){
        id = stageFirstLiveData.getValue().get(position).getId();
        projectTypes();
    }

    /**
     * 点击二级分类
     */
    public void clickSecond(int i){
        List<StageBean> data = stageSecondLiveData.getValue();
        /*//点击城市后，如果存在上一个选择的省份，则将上一个省份的选中城市清空
            if (positionProvincePrevious >= 0){
                setCitySelectState(positionProvincePrevious);
            }*/

        if (i == 0) {
            //选全部
            if (data.get(0).getIsSelect() == 1){
                //全部选中，修改全部为非选中状态
                setSelectState(data, 0);
            }else {
                //非全部选中，修改全部为选中状态
                setSelectState(data, 1);
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
    }

    /**
     * 修改所有数据选中状态
     * @param i
     */
    public void setSelectState(List<StageBean> data, int i){
        for (int j = 0; j < data.size(); j++) {
            data.get(j).setIsSelect(i);
        }
    }

}
