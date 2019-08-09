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
 * @class describe  工程信息列表类型分类
 * @time 2019/7/19 15:40
 * @change
 * @chang time
 * @class describe
 */
public class StageVm extends BaseViewModel {

    public MutableLiveData<List<StageBean>> stageFirstLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int positionFirstNow = 0;
    private boolean firstInited = false;

    /**
     * 初始化
     */
    public void init(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        stageFirstLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
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

    public int getFirstId(){
        return stageFirstLiveData.getValue().get(positionFirstNow).getId();
    }

    public String getFirstName(){
        return stageFirstLiveData.getValue().get(positionFirstNow).getPhases_name();
    }

}
