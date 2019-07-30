package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
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
 * @class describe  工程信息列表类型分类
 * @time 2019/7/19 15:40
 * @change
 * @chang time
 * @class describe
 */
public class StageVm extends BaseViewModel {

    public MutableLiveData<List<StageBean>> stageFirstLiveData;
    public MutableLiveData<List<StageBean>> stageSecondLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int id;//分类id  0为一级
    public int positionFirstNow = 0;
    private String secondResult = "";
    private boolean firstInited = false;

    /**
     * 初始化
     */
    public void init(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        stageSecondLiveData = new MutableLiveData<>();
        stageFirstLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        id = 0;
        getStageFirst();
    }

    public void getStageFirst(){
        new ProjectRepository().projectStage(stageFirstLiveData, pageStateLiveData, userId);
    }

    /**
     * 设置省份默认数据
     */
    public void setProvinceInit(){
        if (!firstInited){
            //初始化省份数据，默认选中第一个省份
            List<StageBean> list = stageFirstLiveData.getValue();
            if (list != null && list.size() > 0){
                list.get(0).setIsSelect(1);
                stageFirstLiveData.postValue(list);
                firstInited = true;
            }
        }else {
            //省份被选定后才开始请求城市数据
            getCity(positionFirstNow);
        }
    }

    public void getCity(int position){
        id = stageFirstLiveData.getValue().get(position).getId();
        ProjectRepository projectRepository = new ProjectRepository();
//        stageSecondLiveData = projectRepository.projectStage(userId);
        // TODO: 2019/7/22 目前没有二级数据 ，有则采用上面注释的部分，逻辑同类型板块
        stageSecondLiveData = new MutableLiveData<>();
    }

    /**
     * 设置默认的城市状态
     */
    public void setCityInit(){
        List<StageBean> cityList = stageSecondLiveData.getValue();
        if (cityList != null){
            //没有添加全部选项的添加
            if (!cityList.get(0).getPhases_name().equals("全部")){
                StageBean stageBean = new StageBean();
                stageBean.setPhases_name("全部");
                stageBean.setId(0);
                stageBean.setIsSelect(0);
                cityList.add(0, stageBean);
                stageSecondLiveData.postValue(cityList);
            }
        }
    }

    /**
     * 点击一级分类
     */
    public void clickFirst(int position){
        if (positionFirstNow == position) return;
        List<StageBean> provinceList = stageFirstLiveData.getValue();
        //选中状态：未选中0，选中1
        provinceList.get(positionFirstNow).setIsSelect(0);
        provinceList.get(position).setIsSelect(1);
        stageFirstLiveData.postValue(provinceList);
        positionFirstNow = position;
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
        stageSecondLiveData.postValue(data);
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

    /**
     * 获取城市选择结果,id
     */
    public Integer[] getResult(){
        List<StageBean> dataCity = stageSecondLiveData.getValue();
        StringBuilder builder = new StringBuilder();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < dataCity.size(); i++) {
            if (dataCity.get(i).getIsSelect() == 1) {
                builder.append(dataCity.get(i).getPhases_name());
                list.add(dataCity.get(i).getId());
            }
        }
        Integer[] citys = list.toArray(new Integer[list.size()]);
        secondResult = builder.toString();
        return citys;
    }

    /**
     * 获取城市选择结果,字符串
     */
    public String getStringResult(){
        if (!StringUtils.isTrimEmpty(secondResult))
            return secondResult;
        List<StageBean> dataCity = stageSecondLiveData.getValue();
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < dataCity.size(); i++) {
            if (dataCity.get(i).getIsSelect() == 1) {
                builder.append(dataCity.get(i).getPhases_name());
            }
        }
        return builder.toString();
    }

    public int getFirstId(){
        return stageFirstLiveData.getValue().get(positionFirstNow).getId();
    }

    public String getFirstName(){
        return stageFirstLiveData.getValue().get(positionFirstNow).getPhases_name();
    }

}
