package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;
import com.shuangduan.zcy.model.bean.TypeBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.vm
 * @class describe  地区选择
 * @time 2019/7/9 17:35
 * @change
 * @chang time
 * @class describe
 */
public class AreaVm extends BaseViewModel {

    public MutableLiveData<List<ProvinceBean>> provinceLiveData;
    public MutableLiveData<List<CityBean>> cityLiveData;
    public MutableLiveData<String> pageStateLiveData;

    private int userId;
    public int id;//分类id  0为一级
    public int positionProvinceNow = 0;
    private String cityResult = "";
    private boolean pronvinceInited = false;

    /**
     * 初始化
     */
    public void init(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        provinceLiveData = new MutableLiveData<>();
        cityLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        id = 0;
        getProvince();
    }

    public void getProvince(){
        new ProjectRepository().getProvince(provinceLiveData, pageStateLiveData, userId);
    }

    /**
     * 设置省份默认数据
     */
    public void setProvinceInit(){
        if (!pronvinceInited){
            //初始化省份数据，默认选中第一个省份
            List<ProvinceBean> list = provinceLiveData.getValue();
            if (list != null && list.size() > 0){
                list.get(0).setIsSelect(1);
                provinceLiveData.postValue(list);
                pronvinceInited = true;
            }
        }else {
            //省份被选定后才开始请求城市数据
            getCity(positionProvinceNow);
        }
    }

    public void getCity(int position){
        id = provinceLiveData.getValue().get(position).getId();
        new ProjectRepository().getCity(cityLiveData, userId, id);
    }

    /**
     * 设置默认的城市状态
     */
    public void setCityInit(){
        List<CityBean> cityList = cityLiveData.getValue();
        if (cityList != null){
            //没有添加全部选项的添加
            if (!cityList.get(0).getName().equals("全部")){
                CityBean stageBean = new CityBean();
                stageBean.setName("全部");
                stageBean.setId(0);
                stageBean.setIsSelect(0);
                cityList.add(0, stageBean);
                cityLiveData.postValue(cityList);
            }
        }
    }

    /**
     * 点击一级分类
     */
    public void clickFirst(int position){
        if (positionProvinceNow == position) return;
        List<ProvinceBean> provinceList = provinceLiveData.getValue();
        //选中状态：未选中0，选中1
        provinceList.get(positionProvinceNow).setIsSelect(0);
        provinceList.get(position).setIsSelect(1);
        provinceLiveData.postValue(provinceList);
        positionProvinceNow = position;
    }

    /**
     * 点击二级分类
     */
    public void clickSecond(int i){
        List<CityBean> data = cityLiveData.getValue();
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
        cityLiveData.postValue(data);
    }

    /**
     * 单选的二级列表点击
     */
    public void clickSecondSingle(int i){
        List<CityBean> data = cityLiveData.getValue();
        //单一选项
        if (data.get(i).getIsSelect() == 1) return;
        //选中状态，之前就是未选中状态，需判断是否为全部选中，是则修改全部为选中状态
        //初始把“全部”更新为选中
        setSelectState(data, 0);
        data.get(i).setIsSelect(1);
        cityLiveData.postValue(data);
    }

    /**
     * 修改所有数据选中状态
     * @param i
     */
    public void setSelectState(List<CityBean> data, int i){
        for (int j = 0; j < data.size(); j++) {
            data.get(j).setIsSelect(i);
        }
    }

    /**
     * 获取城市选择结果,amount
     */
    public int[] getResult(){
        List<CityBean> dataCity = cityLiveData.getValue();
        int[] citys = null;
        if (dataCity != null){
            StringBuilder builder = new StringBuilder();
            List<Integer> idList = new ArrayList<>();
            for (int i = 1; i < dataCity.size(); i++) {
                if (dataCity.get(i).getIsSelect() == 1) {
                    if (builder.length() == 0){
                        builder.append(dataCity.get(i).getName());
                    }else {
                        builder.append("、").append(dataCity.get(i).getName());
                    }
                    idList.add(dataCity.get(i).getId());
                }
            }
            cityResult = builder.toString();
            citys = new int[idList.size()];
            for (int i = 0; i < idList.size(); i++) {
                citys[i] = idList.get(i);
            }
        }
        return citys;
    }

    /**
     * 获取城市选择结果,字符串
     */
    public String getStringResult(){
        if (!StringUtils.isTrimEmpty(cityResult))
            return cityResult;
        List<CityBean> dataCity = cityLiveData.getValue();
        StringBuilder builder = new StringBuilder();
        builder.append(provinceLiveData.getValue().get(positionProvinceNow).getName());
        for (int i = 1; i < dataCity.size(); i++) {
            if (dataCity.get(i).getIsSelect() == 1) {
                builder.append(dataCity.get(i).getName());
            }
        }
        return builder.toString();
    }

    public int getProvinceId(){
        return provinceLiveData.getValue().get(positionProvinceNow).getId();
    }

}
