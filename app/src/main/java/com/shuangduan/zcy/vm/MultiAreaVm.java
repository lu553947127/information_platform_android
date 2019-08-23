package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.ProjectFilterBean;
import com.shuangduan.zcy.model.bean.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/9 10:43
 * @change
 * @chang time
 * @class describe
 */
public class MultiAreaVm extends BaseViewModel {
    public MutableLiveData<List<ProvinceBean>> provinceLiveData;
    public MutableLiveData<List<CityBean>> cityLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int id;//分类id  0为一级
    private String cityResult = "";
    public boolean provinceInited = false;
    public int prePosition = 0;//上一个选中的位置
    public boolean selectAll = true;

    public MultiAreaVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        provinceLiveData = new MutableLiveData<>();
        cityLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        id = 0;
    }

    public void getProvince(){
        new ProjectRepository().getProvince(provinceLiveData, pageStateLiveData, userId);
    }

    public void getCity(){
        new ProjectRepository().getCity(cityLiveData, userId, id);
    }

    /**
     * 设置省份默认数据
     */
    public void setProvinceInit(){
        if (!provinceInited){
            provinceInited = true;
            List<ProvinceBean> provinceList = provinceLiveData.getValue();
            if (provinceList == null) return;
            for (ProvinceBean bean: provinceList) {
                //默认全选
                bean.setIsSelect(selectAll?1:0);
            }
            ProvinceBean provinceBean = new ProvinceBean();
            provinceBean.setName("全部");
            provinceBean.setId(0);
            provinceBean.setIsSelect(1);
            provinceBean.setIsCheck(1);

            List<CityBean> cityList = new ArrayList<>();
            CityBean childBean = new CityBean();
            childBean.setIsSelect(selectAll?1:0);
            childBean.setId(0);
            childBean.setName("全部");
            cityList.add(childBean);
            provinceBean.setCityList(cityList);
            provinceList.add(0, provinceBean);

            provinceLiveData.postValue(provinceList);
            cityLiveData.postValue(provinceList.get(0).getCityList());
        }
    }

    /**
     * 设置默认的城市状态
     */
    public void setCityInit(){
        List<CityBean> cityList = cityLiveData.getValue();
        if (cityList != null && cityList.size() > 0){
            //没有添加全部选项的添加
            if (!cityList.get(0).getName().equals("全部")){
                for (CityBean bean: cityList) {
                    //默认全选
                    bean.setIsSelect(selectAll?1:0);
                }

                CityBean cityBean = new CityBean();
                cityBean.setName("全部");
                cityBean.setId(0);
                cityBean.setIsSelect(selectAll?1:0);
                cityList.add(0, cityBean);
                cityLiveData.postValue(cityList);
            }
            //二级数据存储
            provinceLiveData.getValue().get(prePosition).setCityList(cityList);
        }
    }

    /**
     * 点击省分类
     */
    public void clickFirst(int position){
        List<ProvinceBean> provinceList = provinceLiveData.getValue();
        if (provinceList == null) return;
        if (provinceList.get(position).isCheck == 1) return;
        //当前点击的必选中
        provinceList.get(position).setIsSelect(1);
        //上一个默认未选中
        provinceList.get(prePosition).setIsSelect(0);
        //改变上一个选中效果，字体颜色
        List<CityBean> cityList = provinceList.get(prePosition).getCityList();
        if (cityList == null) return;
        if (cityList.get(0).getIsSelect() != 1){
            //非全选,遍历子类，判断是否修改字体效果
            for (CityBean bean: cityList) {
                if (bean.getIsSelect() == 1){
                    //有一个选中就改变选中效果
                    provinceList.get(prePosition).setIsSelect(1);
                    break;
                }
            }
        }else {
            //全选，直接改变选中效果
            provinceList.get(prePosition).setIsSelect(1);
        }
        //点击状态：未点击0，点击1
        provinceList.get(prePosition).setIsCheck(0);
        provinceList.get(position).setIsCheck(1);
        provinceLiveData.postValue(provinceList);
        //更新选中id, 更新二级列表
        id = provinceList.get(position).getId();
        if (provinceList.get(position).getCityList() == null){
            //如果不存在存储的子类
            getCity();
        }else {
            //存在子类
            cityLiveData.postValue(provinceList.get(position).getCityList());
        }
        prePosition = position;
    }

    /**
     * 点击二级分类
     */
    public void clickSecond(int i){
        List<CityBean> data = cityLiveData.getValue();
        if (i == 0) {
            LogUtils.i("***********", prePosition, data.get(0).getIsSelect());
            if (prePosition == 0){//一级全部的全部
                List<ProvinceBean> provinceList = provinceLiveData.getValue();
                if (data.get(0).getIsSelect() == 1){//更改为所有全部非选中
                    selectAll = false;
                    if (provinceList != null){
                        for (int j = 0; j < provinceList.size(); j++) {
                            ProvinceBean provinceData = provinceList.get(j);
                            provinceData.setIsSelect(j == 0?1:0);
                            List<CityBean> cityList = provinceData.getCityList();
                            if (cityList != null){
                                for (int k = 0; k < cityList.size(); k++) {
                                    cityList.get(k).setIsSelect(0);
                                }
                            }
                        }
                    }
                }else {//更改为所有全部选中
                    selectAll = true;
                    if (provinceList != null){
                        for (int j = 0; j < provinceList.size(); j++) {
                            ProvinceBean provinceData = provinceList.get(j);
                            List<CityBean> cityList = provinceData.getCityList();
                            provinceData.setIsSelect(1);
                            if (cityList != null){
                                for (int k = 0; k < cityList.size(); k++) {
                                    cityList.get(k).setIsSelect(1);
                                }
                            }
                        }
                    }
                }
                cityLiveData.postValue(provinceList.get(0).getCityList());
                provinceLiveData.postValue(provinceList);
            }else {//一级非全部的全部
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
                cityLiveData.postValue(data);
                changeFirstState();
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
            cityLiveData.postValue(data);
            changeFirstState();
        }
    }

    private void changeFirstState(){
        List<ProvinceBean> provinceData = provinceLiveData.getValue();

        //默认全选
        provinceData.get(0).setIsSelect(1);
        provinceData.get(0).getCityList().get(0).setIsSelect(1);
        for (int i = 0; i <provinceData.size(); i++) {
            //如果有省份没有被选中，则为非全选
            if (provinceData.get(i).isSelect != 1){
                provinceData.get(0).setIsSelect(0);
                provinceData.get(0).getCityList().get(0).setIsSelect(0);
                break;
            }
            //如果省份中的城市有没有被选中，则为非全选
            List<CityBean> cityList = provinceData.get(i).getCityList();
            if (cityList != null){
                if (cityList.get(0).getIsSelect() != 1){
                    provinceData.get(0).setIsSelect(0);
                    provinceData.get(0).getCityList().get(0).setIsSelect(0);
                    break;
                }
            }
        }

        if (provinceData.get(0).getCityList().get(0).getIsSelect() == 1) {
            selectAll = true;//只在全部选中的时候改变为true
        }

        provinceLiveData.postValue(provinceData);
    }

    public ProjectFilterBean getProjectFilterArea(){
        ProjectFilterBean projectFilterBean = new ProjectFilterBean();
        List<ProvinceBean> provinceList = provinceLiveData.getValue();
        List<Integer> provinceResult = new ArrayList<>();
        List<Integer> cityResult = new ArrayList<>();
        if (provinceList != null){
            for (int i = 0; i < provinceList.size(); i++) {
                if (i == 0 && provinceList.get(i).getCityList().get(0).getIsSelect() == 1){
                    //全选，返回null
                    return null;
                }else if (provinceList.get(i).isSelect == 1){
                    List<CityBean> cityList = provinceList.get(i).getCityList();
                    if (cityList != null){
                        for (int j = 0; j < cityList.size(); j++) {
                            if (j == 0){
                                if (cityList.get(j).getIsSelect() == 1){
                                    //省全部
                                    provinceResult.add(provinceList.get(i).getId());
                                    break;
                                }
                            }else if (cityList.get(j).isSelect == 1){
                                cityResult.add(cityList.get(j).getId());
                            }
                        }
                    }else {
                        //省全部
                        provinceResult.add(provinceList.get(i).getId());
                    }
                }
            }
            projectFilterBean.setProvince(provinceResult);
            projectFilterBean.setCity(cityResult);
            return projectFilterBean;
        }
        return null;
    }

    private StringBuilder stringBuilder;
    public List<Integer> getProvinceCityIds(){
        List<ProvinceBean> provinceList = provinceLiveData.getValue();
        stringBuilder = new StringBuilder();
        List<Integer> result = new ArrayList<>();
        if (provinceList != null){
            for (int i = 0; i < provinceList.size(); i++) {
                if (i == 0 && provinceList.get(i).getCityList().get(0).getIsSelect() == 1){
                    //全选，返回null
                    return null;
                }else if (provinceList.get(i).isSelect == 1){
                    List<CityBean> cityList = provinceList.get(i).getCityList();
                    if (cityList != null){
                        for (int j = 0; j < cityList.size(); j++) {
                            if (j == 0){
                                if (cityList.get(j).getIsSelect() == 1){
                                    //省全部
                                    result.add(provinceList.get(i).getId());
                                    if (stringBuilder.length() == 0){
                                        stringBuilder.append(provinceList.get(i).getName());
                                    }else {
                                        stringBuilder.append("、").append(provinceList.get(i).getName());
                                    }
                                    break;
                                }
                            }else if (cityList.get(j).isSelect == 1){
                                result.add(cityList.get(j).getId());
                                if (stringBuilder.length() == 0){
                                    stringBuilder.append(cityList.get(j).getName());
                                }else {
                                    stringBuilder.append("、").append(cityList.get(j).getName());
                                }
                            }
                        }
                    }else {
                        //省全部
                        result.add(provinceList.get(i).getId());
                        if (stringBuilder.length() == 0){
                            stringBuilder.append(provinceList.get(i).getName());
                        }else {
                            stringBuilder.append("、").append(provinceList.get(i).getName());
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }

    public String getStringResult(){
        return stringBuilder != null? stringBuilder.toString():"";
    }
}
